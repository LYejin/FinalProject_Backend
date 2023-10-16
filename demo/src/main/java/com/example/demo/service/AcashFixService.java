package com.example.demo.service;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.dao.AcashFixDao;
import com.example.demo.dao.WorkplaceDao;
import com.example.demo.dto.AcashFixDTO;
import com.example.demo.dto.WorkplaceDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AcashFixService {

    @Autowired
    private AcashFixDao acashFixDao;
    @Autowired
    private HttpServletRequest request;

    // 사업장 목록
    public List<AcashFixDTO> selectAcashFixSearch(Map<String, String> map) {
        try {
            List<AcashFixDTO> acashFixList = acashFixDao.selectAcashFixSearch(map);
            //log.info("Get AcashFix List Service", acashFixList);
            log.info("Get AcashFix List Service");
            return acashFixList;
        } catch (Exception e) {
            log.error("Error while fetching AcashFix search: ", e);
            return null;
        }
    }


    // 고정자금 등록
    public int AcashFixInsert(AcashFixDTO acashFixDTO) {
        try {
            int insertResult = acashFixDao.insertAcashFix(acashFixDTO);
            //log.info("Insert AcashFix Service", insertResult, acashFixDTO);
            log.info("Insert AcashFix Service", insertResult);
            return insertResult;
        } catch (Exception e) {
            log.error("Error while inserting workplace: ", e);
            return 0;
        }
    }

    // 고정자금 수정
    public int AcashFixUpdate(AcashFixDTO acashFixDTO) {
        try {
            int updateResult = acashFixDao.updateAcashFix(acashFixDTO);
            //log.info("Update AcashFix Service", updateResult, acashFixDTO);
            log.info("Update AcashFix Service", updateResult);
            return updateResult;
        } catch (Exception e) {
            log.error("Error while updating workplace: ", e);
            return 0;
        }
    }

    //고정자금 삭제
    public int deleteAcashFix(Map<String, Object> map) {
        try {
            int removeResult = acashFixDao.deleteAcashFix(map);
            log.info("Delete AcashFix Service", map);
            return removeResult;
        } catch (Exception e) {
            log.error("Error while removing AcashFix : " + e);
            return 0;
        }
    }

    //연간 고정자금 금액
    public List<Map<String, Object>> getYearlyAmounts(Map<String, Object> params) {
        try {
            List<Map<String, Object>> results = acashFixDao.selectYearlyAmounts(params);
            log.info("Fetched yearly amounts: {}", results);

            Map<Integer, Double> totalAmountMap = new HashMap<>();
            Map<Integer, Integer> paymentCountMap = new HashMap<>();  // 각 연도별 지급 횟수를 카운트하는 맵

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            for (Map<String, Object> row : results) {
                System.out.println("Row data: " + row);

                if (row.get("FR_DT") == null || row.get("TO_DT") == null) {
                    continue;
                }

                Date frDate = sdf.parse((String) row.get("FR_DT"));
                Date toDate = sdf.parse((String) row.get("TO_DT"));

                Calendar frDt = Calendar.getInstance();
                frDt.setTime(frDate);

                Calendar toDt = Calendar.getInstance();
                toDt.setTime(toDate);

                int dealDd = Integer.parseInt((String) row.get("DEAL_DD"));
                Integer dealPd = row.get("DEAL_PD") != null ? Integer.parseInt((String) row.get("DEAL_PD")) : null;
                double cashAm = Double.parseDouble(row.get("CASH_AM").toString());

                Calendar paymentDate = Calendar.getInstance();
                paymentDate.setTime(frDt.getTime());

                if (paymentDate.get(Calendar.DAY_OF_MONTH) > dealDd) {
                    paymentDate.add(Calendar.MONTH, 1);
                }
                paymentDate.set(Calendar.DAY_OF_MONTH, dealDd);

                while (!paymentDate.getTime().after(toDt.getTime())) {
                    System.out.println("Current Payment Date: " + paymentDate.getTime());

                    int paymentYear = paymentDate.get(Calendar.YEAR);

                    if (!paymentDate.getTime().before(frDt.getTime()) && !paymentDate.getTime().after(toDt.getTime())) {
                        totalAmountMap.merge(paymentYear, cashAm, Double::sum);
                        paymentCountMap.merge(paymentYear, 1, Integer::sum);  // 지급 횟수 추가
                    }
                    System.out.println("Yearly Amount Map: " + totalAmountMap);

                    if (dealPd != null) {
                        paymentDate.add(Calendar.MONTH, dealPd);
                    } else {
                        break;
                    }
                }
            }

            List<Map<String, Object>> finalResults = new ArrayList<>();
            for (int i = ((Integer) params.get("inputYear")) - 4; i <= (Integer) params.get("inputYear"); i++) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("YEAR", i);
                resultMap.put("TOTAL_AMOUNT", totalAmountMap.getOrDefault(i, 0.0));
                resultMap.put("PAYMENT_COUNT", paymentCountMap.getOrDefault(i, 0));  // 지급 횟수 추가
                finalResults.add(resultMap);
            }

            return finalResults;
        } catch (Exception e) {
            log.error("Error while fetching yearly amounts : ", e);
            return null;
        }
    }


    //분기별 금액합계
    public List<Map<String, Object>> getQuarterlyAmounts(Map<String, Object> params) {
        try {
            List<Map<String, Object>> results = acashFixDao.selectQuarterlyAmounts(params);
            log.info("Fetched quarterly amounts: {}", results);

            int inputYear = (Integer) params.get("inputYear");

            Map<Integer, Double> quarterlyAmountMap = new HashMap<>();
            Map<Integer, Map<String, Double>> cashCdAmountMap = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            for (Map<String, Object> row : results) {
                System.out.println("Processing row data: " + row);

                if (row.get("FR_DT") == null || row.get("TO_DT") == null) { // Check if FR_DT and TO_DT are not null
                    continue;
                }

                Date frDt = sdf.parse((String) row.get("FR_DT"));
                Date toDt = sdf.parse((String) row.get("TO_DT"));

                if ((frDt.getYear() + 1900) > inputYear) {  // 시작날짜가 inputYear 이후면 skip
                    continue;
                }
                if ((toDt.getYear() + 1900) < inputYear) {  // 종료날짜가 inputYear 이전이면 skip
                    continue;
                }

                if ((frDt.getYear() + 1900) < inputYear) {
                    frDt = sdf.parse(inputYear + "0101");
                }

                if ((toDt.getYear() + 1900) > inputYear) {
                    toDt = sdf.parse(inputYear + "1231");
                }

                int dealDd = Integer.parseInt((String) row.get("DEAL_DD"));
                Integer dealPd = row.get("DEAL_PD") != null ? Integer.parseInt((String) row.get("DEAL_PD")) : null;
                double cashAm = Double.parseDouble(row.get("CASH_AM").toString());

                Calendar paymentDate = Calendar.getInstance();
                paymentDate.setTime(frDt);
                if (paymentDate.get(Calendar.DAY_OF_MONTH) > dealDd) {
                    paymentDate.add(Calendar.MONTH, 1);
                }
                paymentDate.set(Calendar.DAY_OF_MONTH, dealDd);

                while (!paymentDate.getTime().after(toDt)) {
                    System.out.println("Payment Date: " + paymentDate.getTime());
                    int quarter = (paymentDate.get(Calendar.MONTH) / 3) + 1;
                    System.out.println("Current Quarter: " + quarter);
                    System.out.println("Adding Amount: " + cashAm);

                    if (!paymentDate.getTime().before(frDt) && !paymentDate.getTime().after(toDt)) {
                        quarterlyAmountMap.merge(quarter, cashAm, Double::sum);
                        cashCdAmountMap
                                .computeIfAbsent(quarter, k -> new HashMap<>())
                                .merge((String) row.get("CASH_CD"), cashAm, Double::sum);
                    }

                    System.out.println("Quarterly Amount Map after processing current row: " + quarterlyAmountMap);

                    if (dealPd != null) {
                        paymentDate.add(Calendar.MONTH, dealPd);
                    } else {
                        break;
                    }
                }
                System.out.println("Finished processing row data: " + row);
                String cashCd = (String) row.get("CASH_CD");
            }

            List<Map<String, Object>> finalResults = new ArrayList<>();

            for (int i = 1; i <= 4; i++) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("QUARTER", i);
                resultMap.put("TOTAL_AMOUNT", quarterlyAmountMap.getOrDefault(i, 0.0));

                Map<String, Double> quarterCashCdAmounts = cashCdAmountMap.getOrDefault(i, new HashMap<>());
                List<Map.Entry<String, Double>> sortedEntries = quarterCashCdAmounts.entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                        .collect(Collectors.toList());

                double etcAmount = quarterlyAmountMap.getOrDefault(i, 0.0);
                int topCount = 0;  // 상위 3개 항목 카운트

                for (Map.Entry<String, Double> entry : sortedEntries) {
                    if (topCount < 3) {
                        String cashCd = entry.getKey();
                        String cashName = results.stream()
                                .filter(row -> cashCd.equals(row.get("CASH_CD")))
                                .map(row -> (String) row.get("CASH_NM"))
                                .findFirst()
                                .orElse(cashCd);  // 찾을 수 없는 경우 CASH_CD 반환
                        resultMap.put(cashName, entry.getValue());
                        etcAmount -= entry.getValue();
                        topCount++;
                    } else {
                        break;
                    }
                }

                resultMap.put("ETC", etcAmount);
                finalResults.add(resultMap);
            }

            return finalResults;
        } catch (Exception e) {
            log.error("Error while fetching quarterly amounts : ", e);
            return null;
        }
    }

    //월별 금액합계
    public List<Map<String, Object>> getMonthlyAmounts(Map<String, Object> params) {
        List<Map<String, Object>> fetchedData = acashFixDao.selectMonthlyAmounts(params);
        Map<Integer, Double> monthlyAmountMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        for (Map<String, Object> row : fetchedData) {
            try {
                Date frDt = sdf.parse((String) row.get("FR_DT"));
                Date toDt = sdf.parse((String) row.get("TO_DT"));
                int dealDd = Integer.parseInt((String) row.get("DEAL_DD"));
                Integer dealPd = row.get("DEAL_PD") != null ? Integer.parseInt((String) row.get("DEAL_PD")) : null;
                double cashAm = Double.parseDouble(row.get("CASH_AM").toString());

                Calendar paymentDate = Calendar.getInstance();
                paymentDate.setTime(frDt);
                if (paymentDate.get(Calendar.DAY_OF_MONTH) > dealDd) {
                    paymentDate.add(Calendar.MONTH, 1);
                }
                paymentDate.set(Calendar.DAY_OF_MONTH, dealDd);

                while (!paymentDate.getTime().after(toDt)) {
                    int year = paymentDate.get(Calendar.YEAR);
                    int month = paymentDate.get(Calendar.MONTH) + 1;

                    if (year == Integer.parseInt(params.get("inputYear").toString())) {
                        monthlyAmountMap.merge(month, cashAm, Double::sum);
                    }

                    if (dealPd != null) {
                        paymentDate.add(Calendar.MONTH, dealPd);
                    } else {
                        break;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // 1월부터 12월까지의 데이터를 초기화 (데이터가 없는 월은 0으로 설정)
        for (int i = 1; i <= 12; i++) {
            monthlyAmountMap.putIfAbsent(i, 0.0);
        }

        return monthlyAmountMap.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> monthData = new HashMap<>();
                    monthData.put("MONTH", entry.getKey());
                    monthData.put("TOTAL_AMOUNT", entry.getValue());
                    return monthData;
                })
                .collect(Collectors.toList());
    }

//    public List<Map<String, Object>> getMonthlyAmounts(Map<String, Object> params) {
//        List<Map<String, Object>> fetchedData = acashFixDao.selectMonthlyAmounts(params);
//        Map<Integer, Double> monthlyAmountMap = new HashMap<>();
//        Map<Integer, Map<String, Double>> monthlyCashCdAmountMap = new HashMap<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//
//        for (int i = 1; i <= 12; i++) {
//            monthlyAmountMap.put(i, 0.0);
//            monthlyCashCdAmountMap.put(i, new HashMap<>());
//        }
//
//        for (Map<String, Object> row : fetchedData) {
//            try {
//                Date frDt = sdf.parse((String) row.get("FR_DT"));
//                Date toDt = sdf.parse((String) row.get("TO_DT"));
//                int dealDd = Integer.parseInt((String) row.get("DEAL_DD"));
//                Integer dealPd = row.get("DEAL_PD") != null ? Integer.parseInt((String) row.get("DEAL_PD")) : null;
//                double cashAm = Double.parseDouble(row.get("CASH_AM").toString());
//
//                Calendar paymentDate = Calendar.getInstance();
//                paymentDate.setTime(frDt);
//                if (paymentDate.get(Calendar.DAY_OF_MONTH) > dealDd) {
//                    paymentDate.add(Calendar.MONTH, 1);
//                }
//                paymentDate.set(Calendar.DAY_OF_MONTH, dealDd);
//
//                while (!paymentDate.getTime().after(toDt)) {
//                    int year = paymentDate.get(Calendar.YEAR);
//                    int month = paymentDate.get(Calendar.MONTH) + 1;
//
//                    if (year == Integer.parseInt(params.get("inputYear").toString())) {
//                        monthlyAmountMap.merge(month, cashAm, Double::sum);
//
//                        String cashCd = (String) row.get("CASH_CD");
//                        monthlyCashCdAmountMap
//                                .computeIfAbsent(month, k -> new HashMap<>())
//                                .merge(cashCd, cashAm, Double::sum);
//                    }
//
//                    if (dealPd != null) {
//                        paymentDate.add(Calendar.MONTH, dealPd);
//                    } else {
//                        break;
//                    }
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return monthlyAmountMap.entrySet().stream()
//                .map(entry -> {
//                    Map<String, Object> monthData = new HashMap<>();
//                    int month = entry.getKey();
//                    monthData.put("MONTH", month);
//                    monthData.put("TOTAL_AMOUNT", entry.getValue());
//
//                    // Get top 3 CASH_CDs for this month
//                    Map<String, Double> topCashCdMap = monthlyCashCdAmountMap.get(month).entrySet().stream()
//                            .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
//                            .limit(3)
//                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//
//                    for (Map.Entry<String, Double> topEntry : topCashCdMap.entrySet()) {
//                        String cashCd = topEntry.getKey();
//                        double totalAmount = topEntry.getValue();
//                        String cashName = fetchedData.stream()
//                                .filter(row -> cashCd.equals(row.get("CASH_CD")))
//                                .map(row -> (String) row.get("CASH_NM"))
//                                .findFirst()
//                                .orElse(cashCd);
//                        monthData.put(cashName, totalAmount);
//                    }
//
//                    return monthData;
//                })
//                .collect(Collectors.toList());
//    }



    //일자별 금액합계
    public List<Map<String, Object>> getDailyAmounts(Map<String, Object> params) {
        List<Map<String, Object>> fetchedData = acashFixDao.selectDailyAmounts(params);

        Map<Integer, Double> dailyAmountMap = new TreeMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // inputYear와 inputMonth 값 얻기
        int inputYear = Integer.parseInt(params.get("inputYear").toString());
        int inputMonth = Integer.parseInt(params.get("inputMonth").toString());

        // 기본값 초기화
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, inputYear);
        calendar.set(Calendar.MONTH, inputMonth - 1);  // Java의 Calendar 클래스는 월을 0부터 시작하므로 1을 뺍니다.
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= lastDayOfMonth; i++) {
            dailyAmountMap.put(i, 0.0);
        }



        System.out.println("Total number of rows to process: " + fetchedData.size());  // 데이터의 총 개수 출력

        for (Map<String, Object> row : fetchedData) {
            if (row.get("FR_DT") == null || row.get("TO_DT") == null) continue;

            System.out.println("Processing Row Data: " + row.toString());  // 현재 처리 중인 데이터 출력

            try {
                Date frDt = sdf.parse((String) row.get("FR_DT"));
                Date toDt = sdf.parse((String) row.get("TO_DT"));
                Integer dealDd = Integer.parseInt((String) row.get("DEAL_DD"));
                Integer dealPd = row.get("DEAL_PD") != null ? Integer.parseInt((String) row.get("DEAL_PD")) : null;
                double cashAm = Double.parseDouble(row.get("CASH_AM").toString());

                Calendar paymentDate = Calendar.getInstance();
                paymentDate.setTime(frDt);
//                int inputYear = Integer.parseInt(params.get("inputYear").toString());
//                int inputMonth = Integer.parseInt(params.get("inputMonth").toString()) - 1;

                while (!paymentDate.after(toDt)) {
                    System.out.println("Current paymentDate: " + sdf.format(paymentDate.getTime()));
                    if (paymentDate.get(Calendar.YEAR) == inputYear && paymentDate.get(Calendar.MONTH) == (inputMonth - 1) && paymentDate.get(Calendar.DAY_OF_MONTH) == dealDd) {
                        dailyAmountMap.merge(paymentDate.get(Calendar.DAY_OF_MONTH), cashAm, Double::sum);
                    }

                    if (dealPd != null) {
                        if (dealPd == 1) {
                            paymentDate.add(Calendar.DAY_OF_MONTH, dealPd);
                        } else if (dealPd > 1) {  // 월별로 주기를 증가시키는 로직
                            paymentDate.add(Calendar.MONTH, dealPd);
                        }
                    } else {
                        paymentDate.add(Calendar.MONTH, 1);  // 지급 주기가 없는 경우, 다음 달로 이동
                        int lastDayOfNextMonth = paymentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
                        if (dealDd > lastDayOfNextMonth) {
                            paymentDate.set(Calendar.DAY_OF_MONTH, lastDayOfNextMonth);
                        } else {
                            paymentDate.set(Calendar.DAY_OF_MONTH, dealDd);
                        }
                    }

                    if (paymentDate.get(Calendar.YEAR) > inputYear || (paymentDate.get(Calendar.YEAR) == inputYear && paymentDate.get(Calendar.MONTH) >= inputMonth)) {
                        break;
                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return dailyAmountMap.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> dayData = new HashMap<>();
                    dayData.put("DAY", entry.getKey());
                    dayData.put("TOTAL_AMOUNT", entry.getValue());
                    return dayData;
                })
                .collect(Collectors.toList());
    }



}
