package com.cloud.common.enums;

/**
 * 结果返回枚举
 *
 * @author yulj
 * @create: 2019/05/08 16:53
 */
public enum ResultEnum implements AbstractBaseExceptionEnum {

    PARAM_ERROR(1, "请求参数错误"),

    CREATE_REPO_ERROR(2, "创建git远程仓库错误"),
    CANT_DELETE_ERROR(3, "该项目中存在关联服务，不能删除！"),
    JENKINS_NOJOB_ERROR(4, "没有在jenkins中创建对应的任务和配置!"),
    JENKINS_RUN_ERROR(5, "Jenkins没有启动或者启动不正常"),
    PROJECT_ALREDY_EXISTS(6, "该项目名称已经存在！"),
    SERVICEPORT_ALREDY_EXISTS(7, "该端口已被占用！"),
    SERVICE_ALREDY_EXISTS(8, "该服务名称已经存在"),

    FILE_READING_ERROR(9, "FILE_READING_ERROR!"),
    FILE_NOT_FOUND(10, "FILE_NOT_FOUND!"),
    APPLICATION_EXISTS(11, "APP应用已存在"),
    APPLICATION_NOT_EXISTS(12, "应用不存在"),

    /**
     * 登录相关
     */
    BAD_CREDENTIALS(13, "用户名或密码错误！"),
    ACCOUNT_DISABLED(14, "账号当前不可用！"),
    ACCOUNT_LOCKED(15, "账号已被锁定！"),

    ADD_OPERATE_EXCEPTION(16, "新增操作异常"),
    UPDATE_OPERATE_EXCEPTION(17, "更新操作异常"),
    DELETE_OPERATE_EXCEPTION(18, "删除操作异常"),

    EXIST_SAME_RECORD(19, "已存在相同的记录"),
    CAN_NOT_FIND_RECORD(20, "找不到数据记录"),

    REGISTER_LDAP_USER_ERROR(21, "注册用户到LDAP失败，请联系系统管理员"),
    LOGOUT_LDAP_USER_ERROR(21, "用户注销到LDAP失败，请联系系统管理员"),
    UPDATE_LDAP_USER_ERROR(22, "更新用户到LDAP失败，请联系系统管理员"),

    BANEER_MOVE_UP_FAIL(23, "baneer上移失败"),
    BANEER_MOVE_DOWN_FAIL(24, "baneer下移失败"),
    GET_CURRENT_USER_INFO_ERROR(25, "获取当前登录用户信息失败"),
    LOGIN_FAIL(26, "登录失败，请联系系统管理员"),
    LDAP_CONNECT_ERROR(27, "LDAP连接失败"),
    SERVICE_PROVISIONAL_UNUSABLE(28, "服务暂不可用"),
    NEED_TO_AUTHORIZE_ACCOUNT(29, "请联系管理员进行账号授权"),

    ADD_OPERATE_SUCCESS(200, "新增成功"),
    UPDATE_OPERATE_SUCCESS(201, "更新成功"),
    DELETE_OPERATE_SUCCESS(202, "删除成功"),
    RESET_OPERATE_SUCCESS(203, "重置成功"),
    BANEER_MOVE_SUCCESS(204, "baneer移动成功"),
    TOKEN_REMOVE_SUCCESS(205, "token移除成功"),
    LDAP_AUTHENTICATE_SUCCESS(206, "登录成功"),
    LDAP_CONNECT_SUCCESS(207, "LDAP连接成功"),
    APP_USER_CHECK_SUCCESS(208, "验证成功");

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
