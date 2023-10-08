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







}
