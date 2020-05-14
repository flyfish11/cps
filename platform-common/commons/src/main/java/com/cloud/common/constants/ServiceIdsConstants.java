package com.cloud.common.constants;

/**
 * @Classname ServiceIdsConstants
 * @Description <h1>服务id通用构造</h1>
 * @Author yulj
 * @Date: 2020/04/15 1:27 上午
 */
public abstract class ServiceIdsConstants {

    private ServiceIdsConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String OAUTH_CENTER = "oauth-center";

    public static final String USER_CENTER = "user-center";

    public static final String GATEWAY_ZUUL = "gateway-zuul";

    public static final String APP_MANAGER_CENTER = "app-manager-center";

    public static final String SERVICE_CENTER = "service-center";

    public static final String FILE_CENTER = "file-center";

    public static final String LOG_CENTER = "log-center";

    public static final String WORK_FLOW = "work-flow";

}
