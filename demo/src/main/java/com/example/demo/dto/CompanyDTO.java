package com.example.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CompanyDTO {
    private String CO_CD;           //회사코드
    private String USE_YN;          //사용여부
    private String CO_NM;           //회사명
    private String CO_NMK;          //회사약칭
    private String BUSINESS;        //업태
    private String JONGMOK;         //종목
    private String REG_NB;          //사업자번호
    private String CEO_NM;          //대표자명
    private String HO_FAX;          //대표자팩스
    private String CEO_TEL;         //대표자전화번호
    private String PPL_NB;          //대표자주민번호
    private String HO_ZIP;          //본점우편번호
    private String HO_ADDR;         //본점주소
    private String HO_ADDR1;        //본점번지
    private String CO_FG;           //회사구분
    private String CO_NB;           //법인드록번호
    private String EST_DT;          //설립일
    private String OPEN_DT;         //개업일
    private String CLOSE_DT;        //폐업일
    private String PIC_FILE_ID;     //사진파일명 byte[]
    private String ACCT_FG;         //회사계정유형

    //private MultipartFile PIC_FILE_ID; //byte처리 전 데이터



}
