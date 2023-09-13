package com.example.demo.service;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.dto.DepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    public List<DepartmentDTO> getDepartmentHierarchy(String DIV_CD) {
        return departmentDao.getDepartmentHierarchy(DIV_CD);
    }
}
