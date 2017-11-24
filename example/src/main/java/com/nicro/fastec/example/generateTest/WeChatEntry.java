package com.nicro.fastec.example.generateTest;

import com.example.annotations.EntryGenerator;
import com.nicro.latte.wechat.template.WXEntryTemplate;

/**
 * Created by rongwenzhao on 2017/11/24.
 */
@EntryGenerator(
        packageName = "com.nicro.fastec.example",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
