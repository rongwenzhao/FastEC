package com.nicro.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * 从ReleaseOpenHelper中抽离出的数据库处理逻辑
 * 单例实现
 * Created by rongwenzhao on 2017/11/22.
 */

public class DatabaseManager {
    private DaoSession mDaoSession = null;
    private UserProfileDao mUserProfileDao = null;

    private DatabaseManager() {

    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    /**
     * 懒加载模式的单例：
     * 私有静态内部类Holder, 只有当有引用时, 该类才会被装载。
     * 意思是：加载DatabaseManager类的时候是不会加载Holder的。只有Holder被引用的时候才会加载Holder这个类。
     * 内部类被装载一次，static也只被装载一次。
     * 我觉得，此处Holder内部的INSTANCE修饰符用public会更不会使人迷途（私有静态内部类的私有静态变量，在本文中(当前上下文)可以访问）。
     */
    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    private void initDao(Context context) {
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "fast_ec.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mUserProfileDao = mDaoSession.getUserProfileDao();
    }

    public UserProfileDao getmUserProfileDao() {
        return mUserProfileDao;
    }

}
