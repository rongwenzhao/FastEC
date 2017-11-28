package com.nicro.latte.delegates.web.event;

import com.nicro.latte.util.logger.LatteLogger;

/**
 * 未定义的事件。在EventManager中获取事件为null时返回
 * Created by rongwenzhao on 2017/11/28.
 */

public class UndefinedEvent extends Event {
    @Override
    public String execute(String params) {
        LatteLogger.e("UndefinedEvent", params);
        return null;
    }
}
