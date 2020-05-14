package com.cloud.model.activiti;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用接入申请表(ApplicationAccessRequest)实体类
 *
 * @author makejava
 * @since 2019-08-16 15:35:55
 */
public class ApplicationAccessRequest implements Serializable {
    private static final long serialVersionUID = -71208513739264611L;
    /**
     * 主键id
     */
    private String id;
    
    /**
     * 申请单位/部门
     */
    private String requestDeptid;
    
    /**
     * 应用名称
     */
    private String applicationName;
    
    /**
     * 应用当前版本号
     */
    private String applicationVersion;
    
    /**
     * 分类
     */
    private Integer applicationClassification;
    
    /**
     * 企业应用可信域名
     */
    private String redirectDomain;
    
    /**
     * 应用主页
     */
    private String homeUrl;
    
    /**
     * 运行环境要求
     */
    private String operatingEnv;
    
    /**
     * 开发模式
     */
    private String devModel;
    
    /**
     * 是否外网访问（0否，1是）
     */
    private Integer networkAccess;
    
    /**
     * 预估用户数量
     */
    private Integer estimatedUserNumber;
    
    /**
     * 计划上线时间
     */
    private Object plannedOnlinetime;
    
    /**
     * 试运行开始时间
     */
    private Object testrunStarttime;
    
    /**
     * 试运行结束时间
     */
    private Object testrunEndtime;
    
    /**
     * 应用使用范围（部门）
     */
    private String responsibleDept;
    
    /**
     * 应用简介
     */
    private String description;
    
    /**
     * 应用管理员所属单位/部门
     */
    private String aplicationAdminDeptid;
    
    /**
     * 应用管理员id
     */
    private String applicationAdmin;
    
    /**
     * 应用管理员电话
     */
    private String applicationAdminMobile;
    
    /**
     * 应用管理员邮箱
     */
    private String applicationAdminEmail;
    
    /**
     * 应用管理员OA账号
     */
    private String applicationAdminOaacount;
    
    /**
     * 应用接入申请附件url
     */
    private String applicationAccessRequestFileurl;
    
    /**
     * 网关申请原因
     */
    private String networkRequestReason;
    
    /**
     * 网关使用期限（0短期，1长期）
     */
    private Integer networkUsePeriod;
    
    /**
     * 使用期限开始时间
     */
    private Object useperiodStarttime;
    
    /**
     * 使用期限结束时间
     */
    private Object useperiodEndtime;
    
    /**
     * 网关申请备注
     */
    private String networkRemarks;
    
    /**
     * 外网访问接入申请附件url
     */
    private String networkAccessRequestFileurl;
    
    /**
     * 网络信息安全承诺书附件url
     */
    private String networkinfoSecurityFileurl;
    
    /**
     * 申请时间
     */
    private Object requesttime;
    
    /**
     * 创建时间
     */
    private Date createtime;
    
    /**
     * 发起人
     */
    private String initiator;
    


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestDeptid() {
        return requestDeptid;
    }

    public void setRequestDeptid(String requestDeptid) {
        this.requestDeptid = requestDeptid;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public Integer getApplicationClassification() {
        return applicationClassification;
    }

    public void setApplicationClassification(Integer applicationClassification) {
        this.applicationClassification = applicationClassification;
    }

    public String getRedirectDomain() {
        return redirectDomain;
    }

    public void setRedirectDomain(String redirectDomain) {
        this.redirectDomain = redirectDomain;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getOperatingEnv() {
        return operatingEnv;
    }

    public void setOperatingEnv(String operatingEnv) {
        this.operatingEnv = operatingEnv;
    }

    public String getDevModel() {
        return devModel;
    }

    public void setDevModel(String devModel) {
        this.devModel = devModel;
    }

    public Integer getNetworkAccess() {
        return networkAccess;
    }

    public void setNetworkAccess(Integer networkAccess) {
        this.networkAccess = networkAccess;
    }

    public Integer getEstimatedUserNumber() {
        return estimatedUserNumber;
    }

    public void setEstimatedUserNumber(Integer estimatedUserNumber) {
        this.estimatedUserNumber = estimatedUserNumber;
    }

    public Object getPlannedOnlinetime() {
        return plannedOnlinetime;
    }

    public void setPlannedOnlinetime(Object plannedOnlinetime) {
        this.plannedOnlinetime = plannedOnlinetime;
    }

    public Object getTestrunStarttime() {
        return testrunStarttime;
    }

    public void setTestrunStarttime(Object testrunStarttime) {
        this.testrunStarttime = testrunStarttime;
    }

    public Object getTestrunEndtime() {
        return testrunEndtime;
    }

    public void setTestrunEndtime(Object testrunEndtime) {
        this.testrunEndtime = testrunEndtime;
    }

    public String getResponsibleDept() {
        return responsibleDept;
    }

    public void setResponsibleDept(String responsibleDept) {
        this.responsibleDept = responsibleDept;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAplicationAdminDeptid() {
        return aplicationAdminDeptid;
    }

    public void setAplicationAdminDeptid(String aplicationAdminDeptid) {
        this.aplicationAdminDeptid = aplicationAdminDeptid;
    }

    public String getApplicationAdmin() {
        return applicationAdmin;
    }

    public void setApplicationAdmin(String applicationAdmin) {
        this.applicationAdmin = applicationAdmin;
    }

    public String getApplicationAdminMobile() {
        return applicationAdminMobile;
    }

    public void setApplicationAdminMobile(String applicationAdminMobile) {
        this.applicationAdminMobile = applicationAdminMobile;
    }

    public String getApplicationAdminEmail() {
        return applicationAdminEmail;
    }

    public void setApplicationAdminEmail(String applicationAdminEmail) {
        this.applicationAdminEmail = applicationAdminEmail;
    }

    public String getApplicationAdminOaacount() {
        return applicationAdminOaacount;
    }

    public void setApplicationAdminOaacount(String applicationAdminOaacount) {
        this.applicationAdminOaacount = applicationAdminOaacount;
    }

    public String getApplicationAccessRequestFileurl() {
        return applicationAccessRequestFileurl;
    }

    public void setApplicationAccessRequestFileurl(String applicationAccessRequestFileurl) {
        this.applicationAccessRequestFileurl = applicationAccessRequestFileurl;
    }

    public String getNetworkRequestReason() {
        return networkRequestReason;
    }

    public void setNetworkRequestReason(String networkRequestReason) {
        this.networkRequestReason = networkRequestReason;
    }

    public Integer getNetworkUsePeriod() {
        return networkUsePeriod;
    }

    public void setNetworkUsePeriod(Integer networkUsePeriod) {
        this.networkUsePeriod = networkUsePeriod;
    }

    public Object getUseperiodStarttime() {
        return useperiodStarttime;
    }

    public void setUseperiodStarttime(Object useperiodStarttime) {
        this.useperiodStarttime = useperiodStarttime;
    }

    public Object getUseperiodEndtime() {
        return useperiodEndtime;
    }

    public void setUseperiodEndtime(Object useperiodEndtime) {
        this.useperiodEndtime = useperiodEndtime;
    }

    public String getNetworkRemarks() {
        return networkRemarks;
    }

    public void setNetworkRemarks(String networkRemarks) {
        this.networkRemarks = networkRemarks;
    }

    public String getNetworkAccessRequestFileurl() {
        return networkAccessRequestFileurl;
    }

    public void setNetworkAccessRequestFileurl(String networkAccessRequestFileurl) {
        this.networkAccessRequestFileurl = networkAccessRequestFileurl;
    }

    public String getNetworkinfoSecurityFileurl() {
        return networkinfoSecurityFileurl;
    }

    public void setNetworkinfoSecurityFileurl(String networkinfoSecurityFileurl) {
        this.networkinfoSecurityFileurl = networkinfoSecurityFileurl;
    }

    public Object getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(Object requesttime) {
        this.requesttime = requesttime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

}