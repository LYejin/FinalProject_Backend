    package com.example.demo.dto;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.Getter;
    import lombok.NoArgsConstructor;


    import java.util.List;

    @Data
    @NoArgsConstructor
    public class CompanyDTO {
        private String CO_CD;           //회사코드 11
        private String USE_YN;          //사용여부11
        private String CO_NM;           //회사명 11
        private String CO_NMK;          //회사약칭11
        private String BUSINESS;        //업태11
        private String JONGMOK;         //종목11
        private String REG_NB;          //사업자번호11
        private String CEO_NM;          //대표자명 11
        private String HO_FAX;          //대표자팩스11
        private String CEO_TEL;         //대표자전화번호11
        private String PPL_NB;          //대표자주민번호11
        private String HO_ZIP;          //본점우편번호
        private String HO_ADDR;         //본점주소
        private String HO_ADDR1;        //본점번지
        private String CO_FG;           //회사구분11
        private String CO_NB;           //법인등록번호11
        private String EST_DT;          //설립일11
        private String OPEN_DT;         //개업일11
        private String CLOSE_DT;        //폐업일11
        private String PIC_FILE_ID;     //사진파일명 byte[]11
        private String ACCT_FG;         //회사계정유형  11

        //private MultipartFile PIC_FILE_ID; //byte처리 전 데이터


        private List<String> columnsToUpdate;



        public void setColumnsToUpdate(List<String> columnsToUpdate) {
            this.columnsToUpdate = columnsToUpdate;
        }
    }


