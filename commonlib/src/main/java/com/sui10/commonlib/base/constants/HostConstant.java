package com.sui10.commonlib.base.constants;

import com.sui10.commonlib.base.config.TestEnvironment;

public class HostConstant {
    /**
     * 只有通过Retrofit请求的接口域名定义在这里
     * 网页加载的URL必须定义在AppConfig类
     */

    @TestEnvironment("http://test.sui10.com:9001")
    public static final String LOGIN_SERVER_URL = "http://prod.sui10.com:9001";

    @TestEnvironment("https://test.sui10.com:9106")
    public static final String COURSE_SERVER_URL = "https://prod.sui10.com:9106";
}
