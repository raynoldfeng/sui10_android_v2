package com.comic.yoyo.base.config;

import com.comic.commonlib.config.Config;

public class AppConfig {
    //示例：
    public final static Config<String> privacyPolicyUrl = new Config<>(
            "https://www.baidu.com/",
            "https://www.baidu.com/"
    );
}
