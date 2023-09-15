package com.example.demo.service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.dao.WorkplaceDao;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkplaceDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WorkplaceService {

    @Autowired
    private WorkplaceDao workplaceDao;

    // 사업장 목록
    public List<WorkplaceDTO> selectWorkplaceSearch(Map<String, String> map) {
        try {
            List<WorkplaceDTO> workplaces = workplaceDao.selectWorkplaceSearch(map);
            //log.info("Get Workplace List Service", workplaces);
            log.info("Get Workplace List Service");
            return workplaces;
        } catch (Exception e) {
            log.error("Error while fetching workplace search: ", e);
            return null;
        }
    }

    // 사업장 상세
    public WorkplaceDTO selectWorkplaceInfoByDIVCD(String divCd) {
        try {
            WorkplaceDTO workplaceInfo = workplaceDao.selectWorkplaceInfoByDIVCD(divCd);
            //log.info("Get Workplace Detail Service", divCd, workplaceInfo);
            log.info("Get Workplace Detail Service", divCd);
            return workplaceInfo;
        } catch (Exception e) {
            log.error("Error while fetching workplace info: ", e);
            return null;
        }
    }

    // 사업장 추가
    public int workplaceInsert(WorkplaceDTO workplaceDTO) {
        try {
            int insertResult = workplaceDao.insertWorkplace(workplaceDTO);
            //log.info("Insert Workplace Service", insertResult, workplaceDTO);
            log.info("Insert Workplace Service", insertResult);
            return insertResult;
        } catch (Exception e) {
            log.error("Error while inserting workplace: ", e);
            return 0;
        }
    }

    // 사업장 수정
    public int workplaceUpdate(WorkplaceDTO workplaceDTO) {
        try {
            int updateResult = workplaceDao.updateWorkplace(workplaceDTO);
            //log.info("Update Workplace Service", updateResult, workplaceDTO);
            log.info("Update Workplace Service", updateResult);
            return updateResult;
        } catch (Exception e) {
            log.error("Error while updating workplace: ", e);
            return 0;
        }
    }

    public int workplaceRemove(String DIV_CD) {
        try {
            int removeResult = workplaceDao.deleteWorkplace(DIV_CD);
            //log.info("Delete Workplace Service", DIV_CD, removeResult);
            log.info("Delete Workplace Service", DIV_CD);
            return removeResult;
        } catch (Exception e) {
            log.error("Error while removing workplace with DIV_CD: " + DIV_CD, e);
            return 0;
        }
    }
}
