package com.example.demo.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private String empCd; // 사원계정
    private String coCd; // 회사코드
    private String divCd; // 사업장코드
    private String empYn; // 사용여부
    private String useFg; // 조회권한
    private String username; // 아이디
    private String password; // 비밀번호
    private String korNm; // 이름
    private String emailAdd; // 메일ID
    private String tel; // 전화번호
    private String genderFg; // 성별
    private String joinDt; // 입사일
    private String rtrDt; // 퇴사일
    private String rsrgAdd; // 주소지
    private byte[] picFileId; // 사진파일명
    private String enrlFg; // 재직구분
    private String personalMail; // 개인메일ID
    private String personalMailCp; // 개인메일회사
    private String salaryMail; // 급여메일ID
    private String salaryMailCp; // 급여메일소속
    private String homeTel; // 전화번호(집)
    private String zipcode; // 우편번호
    private String addr; // 주소
    private String addrNum; // 번지
}
