package com.example.demo.service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.dao.WorkplaceDao;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkplaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WorkplaceService {


    @Autowired
    private WorkplaceDao workplaceDao;

    //사업장목록
    public List<WorkplaceDTO> selectWorkplaceSearch(Map<String, String> map){
        System.out.println("서비스^^");
        return workplaceDao.selectWorkplaceSearch(map);
    }

    //사업장 정보
    public WorkplaceDTO selectWorkplaceInfoByDIVCD(String divCd){
        return workplaceDao.selectWorkplaceInfoByDIVCD(divCd);
    }

    //사업장추가
    public int insertWorkplace(WorkplaceDTO workplaceDTO){
        return workplaceDao.insertWorkplace(workplaceDTO);
    }

    //수정
    public int updateWorkplace(WorkplaceDTO workplaceDTO){
        return workplaceDao.updateWorkplace(workplaceDTO);
    }

    public int deleteWorkplace(String DIV_CD){
        return workplaceDao.deleteWorkplace(DIV_CD);
    }

}
