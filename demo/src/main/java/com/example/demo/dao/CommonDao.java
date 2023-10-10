package com.example.demo.dao;

import com.example.demo.dto.MainSidebarDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommonDao {
    // MainSidebar
    List<MainSidebarDTO> mainSidebarList(@Param(value = "ROLE") String ROLE);
}
