package com.pbn.org.news.utils.sp.inter;

import android.content.SharedPreferences;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/11/27
 */
public interface ISafeSharePreference extends SharedPreferences{
    public interface ISafeEditor extends  Editor{
        void beginTransaction();
        void endTransaction();
        void setTransactionSuccessful();
    }
}
