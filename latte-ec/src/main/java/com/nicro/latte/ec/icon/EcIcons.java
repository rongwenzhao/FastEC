package com.nicro.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by rongwenzhao on 2017/11/18.
 */

public enum EcIcons implements Icon {
    icon_scan('\ue50a'),
    icon_ali_pay('\ue62f');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
