package com.example.demo.service;

import com.example.demo.dao.ChangeHistoryDao;
import com.example.demo.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChangeHistoryService {

    @Autowired
    private ChangeHistoryDao changeHistoryDao;



    public String CH_NM_Select(String USERNAME){
        String CH_NM =null;
        try {
            CH_NM =changeHistoryDao.CH_NM_Select(USERNAME);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return CH_NM;
    }

    public Map<String, String> getCHD_TARGET(ChangeHistorySearchDTO changeHistorySearchDTO){
        Map<String, String> resultMap = null;
        try {
            resultMap  =changeHistoryDao.CHD_TARGET_select(changeHistorySearchDTO);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return resultMap;
    }

    public void changeHistoryInsert(ChangeHistoryDTO  changeHistoryDTO){
        try {
            log.info("이건 타냐(이력)?");
            changeHistoryDao.changeHistoryInset(changeHistoryDTO);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void changeHistoryDetailInset(Map<String, Object> params){

        try {
            System.out.println("상세이력 타냐?!!!!!!!!!!!"+params);
            changeHistoryDao.changeHistoryDetailInset(params);
            System.out.println("상세이력 실행 됨?!!!!!!!!!!!");
        }catch (Exception e){
            log.error("ChangeHistoryDetailList"+    e.getMessage());
        }
    }




    public List<ChangeHistoryDTO> getChangeHistoryList(String CH_CATEGORY) {
        List<ChangeHistoryDTO> changeHistorySearchDTOS = null;
        try {
            changeHistorySearchDTOS = changeHistoryDao.ChangeHistoryList(CH_CATEGORY);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return changeHistorySearchDTOS;
    }
    public List<ChangeHistoryDTO> ChangeHistorySearch(ChangeHistoryFindDTO changeHistoryFindDTO) {
        List<ChangeHistoryDTO> changeHistorySearchDTOS = null;
        try {
            if(changeHistoryFindDTO.getCH_NM().isEmpty()){
                changeHistoryFindDTO.setCH_NM(null);
            }
            if(changeHistoryFindDTO.getCHD_TARGET_NM().isEmpty()){
                changeHistoryFindDTO.setCHD_TARGET_NM(null);
            }
            findByInputColumnsSearch(changeHistoryFindDTO);
            log.info("변경 컬럼"+changeHistoryFindDTO);
            changeHistorySearchDTOS = changeHistoryDao.ChangeHistorySearch(changeHistoryFindDTO);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return changeHistorySearchDTOS;
    }
    public List<ChangeHistoryDetailDTO> ChangeHistoryDetailList(ChangeHistoryDTO changeHistoryDTO) {
        List<ChangeHistoryDetailDTO> changeHistoryDetailDTOS = new ArrayList<>();
        try {
            findByInputColumns(changeHistoryDTO);
            changeHistoryDetailDTOS = changeHistoryDao.ChangeHistoryDetailList(changeHistoryDTO);
            log.info("디테일정보:"+changeHistoryDetailDTOS);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return  changeHistoryDetailDTOS;
    }



    public void findByInputColumns(ChangeHistoryDTO changeHistoryDTO){
        List<String> columnsToUpdate = new ArrayList<>();

        // DTO의 필드를 반복하며 널이 아닌 데이터가 담긴 필드를 columnsToUpdate 목록에 추가
        //getDeclaredFields() 함수는 해당 클래스에 선언된 모든 필드(멤버 변수)를 배열로 반환
        Field[] fields = changeHistoryDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 비공개(private) 필드에 접근 가능하도록 설정
            field.setAccessible(true);
            try {
                // 각 필드마다 실제로 담긴 값 가져오기
                Object value = field.get(changeHistoryDTO);
                // 값이 null이 아닌 경우 컬럼 이름 리스트에 추가
                if ((value != null ) && !(field.getName().equals("startDate") || field.getName().equals("endDate"))) {
                    columnsToUpdate.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                // 필드에 접근 예외가 발생하면 무시
            }
        }

        // columnsToUpdate 목록을 설정하고 DAO 메서드를 호출합니다
        changeHistoryDTO.setColumnsToUpdate(columnsToUpdate);
    }

    public void findByInputColumnsSearch(ChangeHistoryFindDTO changeHistoryFindDTO){
        List<String> columnsToUpdate = new ArrayList<>();

        // DTO의 필드를 반복하며 널이 아닌 데이터가 담긴 필드를 columnsToUpdate 목록에 추가
        //getDeclaredFields() 함수는 해당 클래스에 선언된 모든 필드(멤버 변수)를 배열로 반환
        Field[] fields = changeHistoryFindDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 비공개(private) 필드에 접근 가능하도록 설정
            field.setAccessible(true);
            try {
                // 각 필드마다 실제로 담긴 값 가져오기
                Object value = field.get(changeHistoryFindDTO);
                // 값이 null이 아닌 경우 컬럼 이름 리스트에 추가
                if (value != null && !(field.getName().equals("startDate") || field.getName().equals("endDate"))) {
                    columnsToUpdate.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                // 필드에 접근 예외가 발생하면 무시
            }
        }

        // columnsToUpdate 목록을 설정하고 DAO 메서드를 호출합니다
        changeHistoryFindDTO.setColumnsToUpdate(columnsToUpdate);
    }







}
