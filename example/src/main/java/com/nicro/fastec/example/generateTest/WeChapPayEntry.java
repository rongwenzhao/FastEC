package com.nicro.fastec.example.generateTest;

import com.example.annotations.PayEntryGenerator;
import com.nicro.latte.wechat.template.WXPayEntryTemplate;

/**
 * Created by rongwenzhao on 2017/11/24.
 */

@PayEntryGenerator(
        packageName = "com.nicro.fastec.example",
        payEntryTemplate = WXPayEntryTemplate.class
)
public interface WeChapPayEntry {
}
