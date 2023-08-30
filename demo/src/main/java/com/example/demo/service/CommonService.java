package com.example.demo.service;

import com.example.demo.dao.CompanyDao;
import com.example.demo.dto.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private  CompanyService companyService;


}
