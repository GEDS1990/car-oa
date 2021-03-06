package com.xiaofan.car.persistence.vo;

import com.xiaofan.car.persistence.dingding.enums.EmployeeStatusEnum;
import com.xiaofan.car.persistence.enumType.ShiftsNoEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * TODO: 请添加描述
 *
 * @author gongdaoshun
 * @date 2017/8/26
 * @since 1.0.0
 */
@Data
public class EmployeeVo {

    /**自增主键**/
    private Integer id;

    /**员工姓名**/
    private String employeeName;

    /**员工手机号码**/
    private String employeeMobile;

    /**员工号**/
    private String employeeNo;

    /**入职时间**/
    private String inductionTime;
    /**员工角色**/
    private String roleName;
    /**员工角色**/
    private Integer roleId;
    /**是否可用0:停用1:可用**/
    private Boolean isActive;
    /**员工状态：1：在职，2休假，3：离职**/
    private Integer employeeStatus;

    private String employeeStatusName;

    /** 排班设置 1：早班 2：晚班 0：无排班设置**/
    private Integer shiftsNo;

    private String shiftsNoName;

    public void setShiftsNo(Integer shiftsNo){
        this.shiftsNo = shiftsNo;
        this.shiftsNoName = ShiftsNoEnum.valuesOf(shiftsNo).getName();
    }
    public void setEmployeeStatus(Integer employeeStatus){
            this.employeeStatus = employeeStatus;
            this.employeeStatusName = EmployeeStatusEnum.valuesOf(employeeStatus).getName();
    }

    public void setEmployeeStatusName(String employeeStatusName){
        if(StringUtils.isBlank(this.employeeStatusName)){
            this.employeeStatusName = employeeStatusName;
        }
    }
}
