package com.cloud.common.constants;

/**
 * @Classname CommonConstants
 * @Description 通用构造
 * @Author yulj
 * @Date: 2019/07/02 17:05
 */
public final class CommonConstants {

    private CommonConstants() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 0;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 1;

    /**
     * bean校验错误
     */
    public static final Integer BEAN_VALID_ERROR = 2;

    /**
     * 组装用户和部门tree,所用的userId累加值
     */
    public static final Integer USER_ID_ACCUMULATE = 100000000;

    /**
     * 默认父级菜单值
     */
    public static final String DEFAULT_MENU_PID = "0";

    /**
     * 用户默认角色id
     */
    public static final String DEFAULT_ROLE_ID = "0";

    /**
     * 超级管理员默认角色id
     */
    public static final String SUPER_ADMIN_ROLE_ID = "1";

    /**
     * 顶级部门id
     */
    public static final Integer ROOT_DEPT_ID = 0;

    /**
     * 微服务中心应用id
     */
    public static final String MICRO_SERVICE_CENTER_ID = "1";

    /**
     * 用户激活状态
     */
    public static final Integer USER_ACTIVE_STATUS = 1;

    /**
     * 顶部 banner 排序值
     */
    public static final Integer TOP_BANAER_SORT_NUM = 1;

    public static final String CACHE_USER_KEY = "common:allUser:data";

    public static final String CACHE_DEPT_KEY = "common:allDept:data";

    public static final String CACHE_TOKEN = "login-token:";

    public static final String KAPTCHA_CODE = "kaptcha:code";

    /**
     * 其他平台请求返回成功标记
     */
    public static final Integer OTHER_PLATFORM_RESPONSE_CODE_SUCCESS = 1;

    /**
     * LDAP配置默认id
     */
    public static final Integer DEFUALT_LDAP_CONFIG_ID = 1;

    /**
     * 移动应用平台api字符串
     */
    public static final String MOBILE_PLATFORM_API_STR = "?api=";

    /**
     * 数据集成平台api字符串
     */
    public static final String DATA_INTEGRATION_PLATFORM_API_STR = ".do";

    /**
     * 调用远程 API 超时时间
     */
    public static final Integer REQUEST_REMOTE_API_TIME_OUT = 3000;

    /**
     * 应用分类-平台
     */
    public static final Integer APP_CLASSIFICATION_OF_PLATFORM = 1;

    /**
     * 应用分类-平台
     */
    public static final String APP_CLASSIFICATION_OF_PLATFORM_VALUE = "平台";

    /**
     * 应用分类-应用
     */
    public static final Integer APP_CLASSIFICATION_OF_APP = 2;

    /**
     * 应用分类-平台
     */
    public static final String APP_CLASSIFICATION_OF_APP_VALUE = "应用";

    /**
     * 用户默认密码
     */
    public static final String DEFAULT_USER_PASSWORD = "P@ssw0rd";

}
