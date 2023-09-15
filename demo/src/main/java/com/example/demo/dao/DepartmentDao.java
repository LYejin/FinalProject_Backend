package com.example.demo.dao;

import com.example.demo.dto.DepartmentDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DepartmentDao {
    List<DepartmentDTO> getDepartmentHierarchy(String CO_CD);
}
