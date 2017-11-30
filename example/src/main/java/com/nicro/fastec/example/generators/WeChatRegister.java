package com.nicro.fastec.example.generators;

import com.example.annotations.AppRegisterGenerator;
import com.nicro.latte.wechat.template.AppRegisterTemplate;

/**
 * Created by rongwenzhao on 2017/11/24.
 */

@AppRegisterGenerator(
        packageName = "com.nicro.fastec.example",
        registerTemplate = AppRegisterTemplate.class
)
public interface WeChatRegister {
}
