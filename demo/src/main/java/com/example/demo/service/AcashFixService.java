package com.example.demo.service;

import com.example.demo.dao.AcashFixDao;
import com.example.demo.dao.WorkplaceDao;
import com.example.demo.dto.AcashFixDTO;
import com.example.demo.dto.WorkplaceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AcashFixService {

    @Autowired
    private AcashFixDao acashFixDao;

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

    public int deleteAcashFix(Map<String, Object> map) { // Map의 value 타입을 Object로 변경
        try {
            int removeResult = acashFixDao.deleteAcashFix(map);
            log.info("Delete AcashFix Service", map);
            return removeResult;
        } catch (Exception e) {
            log.error("Error while removing AcashFix : " + e);
            return 0;
        }
    }



}
