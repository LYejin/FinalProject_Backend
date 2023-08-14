package com.example.demo.service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.dao.WorkplaceDao;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkplaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkplaceService {


    @Autowired
    private WorkplaceDao workplaceDao;

    //검색
    public List<WorkplaceDTO> select(){
        return null;
    }

    //추가
    public void save(){

    }

    //갱신
    public void update(){

    }

    //삭제
    public void delete(){

    }

}
