package com.example.demo.dao;

import com.example.demo.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StradeDao {

    // 일반 거래처 프로시저
    int insertStradeAndGtradeProcedure(SGtradeDTO sgtradeDTO);

    // 일반 거래처 리스트
    List<SGtradeDTO> sgtradeSearchList(Map<String, String> map);

    // 금융 거래처 리스트
    List<SFtradeDTO> sftradeSearchList(Map<String, String> map);

    // 일반 거래처 디테일 정보
    SGtradeDTO sgtradeDetail(Map<String, String> map);

    // 금융 거래처 디테일 정보
    SFtradeDTO sftradeDetail(Map<String, String> map);


//    int sgtradeUpdate(SGtradeDTO sgtradeDTO);

    // 거래처 업데이트
    int stradeUpdate(SGFtradeDTO stradeDTO);

    // 일반 거래처 업데이트
    int gtradeUpdate(SGFtradeDTO sgftradeDTO);

    // 금융 거래처 업데이트
    int ftradeUpdate(SGFtradeDTO sgftradeDTO);

    // 거래처 Insert
    int stradeInsert(SGFtradeDTO sgftradeDTO);

    // 일반 거래처 Insert
    int gtradeInsert(SGFtradeDTO sgftradeDTO);

    // 금융 거래처 Insert
    int ftradeInsert(SGFtradeDTO sgftradeDTO);

    // 거래처 권한 Insert
    int stradeRollManageInsert(StradeRollManageDTO stradeRollManageDTO);

    // 거래처 권한 리스트
   List<StradeRollManageDTO> stradeRollManageSearchList(Map<String, String> map);

   // 거래처 권한 업데이트
    int stradeRollManageUpdate(StradeRollManageDTO stradeRollManageDTO);

    // 사원코드도움 리스트
    List<EmpCodeHelpDTO> empCodeHelpList(EmpCodeHelpListDTO empCodeHelpDTO);

    // 부서코드도움 리스트
    List<DepartmentDTO> deptCodeHelpList(@Param("CO_CD")String CO_CD, @Param("TR_CD")String TR_CD);

    // 거래처코드도움 리스트
    List<StradeCodeHelpDTO> stradeCodeHelpList(StradeCodeHelpSearchDTO stradeCodeHelpSearchDTO);

    // 부서코드도움 리스트
    List<DepartmentDTO> deptCodeHelpList(DeptCodeHelpListDTO deptCodeHelpListDTO);

    // 주류코드도움 리스트
    List<LiquorcodeHelpListDTO> liquorcodeHelpList(String VALUE);

    // 금융기관코드도움 리스트
    List<FinancecodeHelpListDTO> financecodeHelpList(String value);

    // 사원도움모달 check list 거래처 등록
    int stradeRollInEmpInsert(List<StradeRollManageDTO> list);

    // 부서도움모달 check list 거래처 등록
    int stradeRollInDeptInsert(List<StradeRollManageDTO> list);

    // 자금등록 내 거래처 사용여부
    int acashFix(@Param("TR_CD")String TR_CD);

    // 거래처 삭제
    int stradeDelete(StradeDeleteDTO stradeDeleteDTO);

    // 일반 거래처 삭제
    int gtradeDelete(StradeDeleteDTO stradeDeleteDTO);

    // 금융 거래처 삭제
    int ftradeDelete(StradeDeleteDTO stradeDeleteDTO);

    // 거래처 권한 삭제
    int stradeRollManageDelete(@Param("CO_CD")String CO_CD, @Param("TR_CD")String TR_CD, @Param("list")List<String> TRMG_SQ);

    // 거래처 관리 권한 전체 삭제
    int stradeRollManageTotalDelete(StradeDeleteDTO data);

    // 거래처 내 trCd
    String trCdInStrade(@Param("CO_CD")String CO_CD, @Param("TR_CD")String TR_CD);

    // 그리드 내 사원 정보
    String gridEmpCode(GridEmpCdDTO gridEmpCdDTO);

    // 그리드 내 부서 정보
    String gridDeptCd(GridDeptCdDTO gridDeptCdDTO);

    // 채번 기능
    String getStradeSeq(@Param("makeTrCd")String makeTrCd, @Param("ctrNb")String ctrNb, @Param("coCd")String CO_CD);

    // 그리드 내 부서 코드 사용 여부
    String gridUseDeptCd(GridDeptCdDTO gridDeptCdDTO);

    // 그리드 내 부서 코드 존재 여부
    String gridNotDeptCd(GridDeptCdDTO gridDeptCdDTO);

    // 그리드 내 사원 코드 사용 여부
    String gridUseEmpCd(GridEmpCdDTO gridEmpCdDTO);

    // 그리드 내 부서 코드 존재 여부
    String gridNotEmpCd(GridEmpCdDTO gridEmpCdDTO);

    //거래처코드 유효성
    String trCdVal(@Param("CO_CD")String CO_CD, @Param("TR_CD")String TR_CD);

    // 계좌번호 유효성
    String baNbTrVal(@Param("CO_CD")String CO_CD, @Param("BA_NB_TR")String BA_NB_TR);

    // 사업자등록번호 유효성
    String regNbVal(@Param("CO_CD")String CO_CD, @Param("REG_NB")String REG_NB);

    // 주민등록번호 유효성
    String pplNbVal(@Param("CO_CD")String CO_CD, @Param("PPL_NB")String PPL_NB);

    // 금융코드 정보 자동 입력
    List<FinancecodeDTO> financecodeInfo(@Param("FINANCE_CD")String FINANCE_CD);

    // 주류코드 정보 자동 입력
    List<LiquorcodeDTO> liqcodeInfo(@Param("LIQ_CD")String liqCd);
}
