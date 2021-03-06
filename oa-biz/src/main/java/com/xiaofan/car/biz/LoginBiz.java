package com.xiaofan.car.biz;

import com.xiaofan.car.persistence.model.Employee;

/**
 * 登录相关的业务逻辑
 *
 * @author gongdaoshun
 * @date 2017/8/25
 * @since 1.0.0
 */
public interface LoginBiz {

    /**
     * 根据工号和密码校验登陆
     * @param employeeNo
     * @param password
     * @return
     */
    public Employee verificationForLogin(String employeeNo, String password);

}
