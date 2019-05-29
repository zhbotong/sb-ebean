package cc.shallow.sbebean.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "pe")
@Data
@NoArgsConstructor
@AllArgsConstructor
class Pe {


    Integer peId;

    Integer dmId; //患者id

    Integer svId; //访视表id

    Integer dncid; //录入人员id

    Integer siteid; //医院id

    LocalDateTime testdat; //检查日期

    String height; //身高

    String weight; //体重

    String bmi; //BMI

    String waistline; //腰围

    String sbp; //收缩压

    String dbp; //舒张压

    String sbpu; //收缩压单位

    String dbpu; //舒张压单位

    String delStatus; //删除状态 1是 0否

    LocalDateTime createDate;

    LocalDateTime updateDate;

}

