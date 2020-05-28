package com.cloud.platformuser.listener;

import com.cloud.platformuser.service.DeptService;
import com.cloud.platformuser.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Classname CacheCommonUserDataListener
 * @Description 缓存用户通用数据(用户及部门数据)
 * @Author yulj
 * @Date: 2020/04/26 10:09 上午
 */
@Component
@Order(100)
@Slf4j
public class CacheCommonUserDataListener implements CommandLineRunner {

    @Autowired
    private SysUserService userService;

    @Autowired
    private DeptService deptService;

    @Override
    public void run(String... args) {
        int cacheUserResult = this.userService.cacheAllUserData();
        log.info("共缓存用户数据条数{}", cacheUserResult);

        int cacheDeptResult = this.deptService.cacheAllDeptData();
        log.info("共缓存部门数据条数{}", cacheDeptResult);
    }

}
