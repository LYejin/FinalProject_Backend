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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            for (Map<String, Object> row : results) {
                System.out.println("Row data: " + row);  // 출력 1: 현재 행의 데이터 출력

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
                    System.out.println("Current Payment Date: " + paymentDate.getTime());  // 출력 2: 현재 지불 날짜 출력

                    int paymentYear = paymentDate.get(Calendar.YEAR);

                    if (!paymentDate.getTime().before(frDt.getTime()) && !paymentDate.getTime().after(toDt.getTime())) {
                        totalAmountMap.merge(paymentYear, cashAm, Double::sum);
                    }
                    System.out.println("Yearly Amount Map: " + totalAmountMap);  // 출력 3: 연도별 합계 출력

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

            Map<Integer, Double> quarterlyAmountMap = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            for (Map<String, Object> row : results) {
                System.out.println("Processing row data: " + row);

                if (row.get("FR_DT") == null || row.get("TO_DT") == null) { // Check if FR_DT and TO_DT are not null
                    continue;
                }

                Date frDt = sdf.parse((String) row.get("FR_DT"));
                Date toDt = sdf.parse((String) row.get("TO_DT"));

                if ((frDt.getYear() + 1900) > 2023) {  // 시작날짜가 2023년 이후면 skip
                    continue;
                }
                if ((toDt.getYear() + 1900) < 2023) {  // 종료날짜가 2023년 이전이면 skip
                    continue;
                }

                // 2023년 이전으로 시작하는 데이터는 2023년 1월 1일로 조정
                if ((frDt.getYear() + 1900) < 2023) {
                    frDt = sdf.parse("20230101");
                }

                // 2023년 이후로 종료하는 데이터는 2023년 12월 31일로 조정
                if ((toDt.getYear() + 1900) > 2023) {
                    toDt = sdf.parse("20231231");
                }

                int dealDd = Integer.parseInt((String) row.get("DEAL_DD"));
                Integer dealPd = row.get("DEAL_PD") != null ? Integer.parseInt((String) row.get("DEAL_PD")) : null;
                double cashAm = Double.parseDouble(row.get("CASH_AM").toString());

                Calendar paymentDate = Calendar.getInstance();
                paymentDate.setTime(frDt);
                if (paymentDate.get(Calendar.DAY_OF_MONTH) >= dealDd) {
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
                    }

                    System.out.println("Quarterly Amount Map after processing current row: " + quarterlyAmountMap);

                    if (dealPd != null) {
                        paymentDate.add(Calendar.MONTH, dealPd);
                    } else {
                        break;
                    }
                }
                System.out.println("Finished processing row data: " + row);
            }

            List<Map<String, Object>> finalResults = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("QUARTER", i);
                resultMap.put("TOTAL_AMOUNT", quarterlyAmountMap.getOrDefault(i, 0.0));
                finalResults.add(resultMap);
            }

            return finalResults;
        } catch (Exception e) {
            log.error("Error while fetching quarterly amounts : ", e);
            return null;
        }
    }





}
