package com.example.demo.service;

import com.example.demo.dao.CompanyDao;
import com.example.demo.dao.EmployeeDao;
import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkplaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyDao companyDao;

    //검색
    public List<CompanyDTO> companySelectAll(){
        List<CompanyDTO> companyDTOS = null;
        try {
            companyDTOS = companyDao.companySelectAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return companyDTOS;
    }

    public  CompanyDTO companyDetail(String co_CD){
        CompanyDTO companyDTO = null;
        try {
            companyDTO = companyDao.companyDetail(co_CD);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return companyDTO;
    }

    //추가
    public void companyInsert(CompanyDTO companyDTO){

        int index = companyDTO.getPIC_FILE_ID().indexOf("data");
        String data = companyDTO.getPIC_FILE_ID().substring(index);

        if(index != -1){
            companyDTO.setPIC_FILE_ID(data);
        }

        System.out.println(index+"이미지확인"+data);

        try {
            companyDao.companyInsert(companyDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //갱신
    public void companyUpdate(CompanyDTO companyDTO){

        try {
            findByInputColumns(companyDTO);  //데이터 입력된 필드 확인해주는 함수
            companyDao.companyUpdate(companyDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //삭제
    public void companyRemove(String CO_CD){

        try {
            companyDao.companyRemove(CO_CD);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }



    public void findByInputColumns(CompanyDTO companyDTO){
        List<String> columnsToUpdate = new ArrayList<>();

        // DTO의 필드를 반복하며 널이 아닌 데이터가 담긴 필드를 columnsToUpdate 목록에 추가
        //getDeclaredFields() 함수는 해당 클래스에 선언된 모든 필드(멤버 변수)를 배열로 반환
        Field[] fields = companyDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 비공개(private) 필드에 접근 가능하도록 설정
            field.setAccessible(true);
            try {
                // 각 필드마다 실제로 담긴 값 가져오기
                Object value = field.get(companyDTO);

                // 값이 null이 아닌 경우 컬럼 이름 리스트에 추가
                if (value != null) {
                    columnsToUpdate.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                // 필드에 접근 예외가 발생하면 무시
            }
        }

        // columnsToUpdate 목록을 설정하고 DAO 메서드를 호출합니다
        companyDTO.setColumnsToUpdate(columnsToUpdate);
    }


}
