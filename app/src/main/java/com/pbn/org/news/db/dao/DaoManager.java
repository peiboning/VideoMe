package com.pbn.org.news.db.dao;

import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.greendao.gen.DaoMaster;
import com.pbn.org.news.greendao.gen.DaoSession;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class DaoManager {
    private static final String DB_NAME = "news_greendao.db";
    private static DaoManager sInstance = null;

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static DaoManager getInstance() {
        if (null == sInstance) {
            synchronized (DaoManager.class) {
                if (null == sInstance) {
                    sInstance = new DaoManager();
                }
            }
        }
        return sInstance;
    }

    private DaoManager() {
        init();
    }

    private void init() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(NewsApplication.getContext(), DB_NAME);
        mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }
}