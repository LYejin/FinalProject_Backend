package com.example.demo;

import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.FunTypeDTO;
import com.example.demo.service.CompanyService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.FunTypeService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CompanyService companyService;

	@Autowired
	private FunTypeService funTypeService;

	//암호화, 복호화 기능 테스트
	//@Test
//	void encrypt() {
//		CompanyDTO companyDTO = new CompanyDTO(
//				"일반 의료기관",   // ACCT_FG
//				"2",              // BUSINESS
//				"아아아",          // CEO_NM
//				"02042938273",    // CEO_TEL
//				"",               // CLOSE_DT
//				null,             // COLUMNSTOUPDATE
//				"4594",           // CO_CD
//				"개인",            // CO_FG
//				"1101111755002",  // CO_NB
//				"(주)이카운트",    // CO_NM
//				"",               // CO_NMK
//				"",               // EST_DT
//				"경기 광주시 남종면 삼성길 7-40 (삼성리)",  // HO_ADDR
//				"",               // HO_ADDR1
//				"23038279724",    // HO_FAX
//				"12709",          // HO_ZIP
//				"2",              // JONGMOK
//				"",               // OPEN_DT
//				"",               // PIC_FILE_ID
//				"SGe2Tw3rye3G8/nXJX856A==",  // PPL_NB
//				"2208165848",     // REG_NB
//				"1"	              // USE_YN
//		);
//
//		companyService.companyDetail(companyDTO.getCO_CD());
//
//	}

//	@Test
//	void companySearch(){
//		CompanyDTO companyDTO = new CompanyDTO();
//		companyDTO.setCO_CD("1234");
//		companyDTO.setUSE_YN("1");
//
//		//companyService.companySearch(companyDTO);
//	}

	@Test
	void searchRow(){
		FunTypeDTO funTypeDTO = new FunTypeDTO();
		List<FunTypeDTO> funTypeDTOS = funTypeService.searchRow(funTypeDTO);
		log.info("안녕"+funTypeDTOS);
	}


}
