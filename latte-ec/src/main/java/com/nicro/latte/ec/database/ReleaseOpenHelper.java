package com.nicro.latte.ec.database;

import android.content.Context;

/**
 * 注意，继承类DaoMaster.OpenHelper为GreenDao自动生成的
 * 所以，要先定义实体类，生成自动代码后，再编写此处代码
 * Created by rongwenzhao on 2017/11/22.
 */

public class ReleaseOpenHelper extends DaoMaster.OpenHelper {
    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }
}
