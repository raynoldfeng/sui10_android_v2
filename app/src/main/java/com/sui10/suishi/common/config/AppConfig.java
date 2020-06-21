package com.sui10.suishi.common.config;

import com.sui10.commonlib.base.config.Config;

public class AppConfig {
    //示例：
    public final static Config<String> privacyPolicyUrl = new Config<>(
            "https://www.baidu.com/",
            "https://www.baidu.com/"
    );
}
