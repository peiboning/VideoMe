package com.pbn.org.news.utils.sp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import android.util.Log;

import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.utils.sp.inter.ISafeSharePreference;

import java.nio.file.AtomicMoveNotSupportedException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/11/27
 */
public class SafeSharePreference implements ISafeSharePreference{

    private static HandlerThread sThread;
    private static Handler sHandler;

    static {
        sThread = new HandlerThread("SafeSharePreference");
        sThread.start();
        sHandler = new Handler(sThread.getLooper());
    }

    private static ArrayMap<String, SafeSharePreference> arrayMap;
    private SharedPreferences mInternalSP;
    private String name;
    private SafeSharePreference(String name){
        this.name = name;
        mInternalSP = NewsApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    @SuppressLint("NewApi")
    public static SafeSharePreference getSafeSharePreference(String name){
        if(null == arrayMap){
            arrayMap = new ArrayMap<String, SafeSharePreference>();
        }

        SafeSharePreference sp = arrayMap.get(name);
        if(null == sp){
            sp = new SafeSharePreference(name);
            arrayMap.put(name, sp);
        }
        return sp;
    }


    @Override
    public Map<String, ?> getAll() {
        return mInternalSP.getAll();
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        return mInternalSP.getString(key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        return mInternalSP.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return mInternalSP.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return mInternalSP.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return mInternalSP.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return mInternalSP.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return mInternalSP.contains(key);
    }

    @Override
    public Editor edit() {
        return new SafeEditor(mInternalSP.edit());
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mInternalSP.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mInternalSP.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public class SafeEditor implements ISafeEditor{
        private Editor editor;
        private AtomicBoolean isUseTransaction;
        private SafeEditor(Editor editor){
            this.editor = editor;
            isUseTransaction = new AtomicBoolean(false);
        }
        @Override
        public Editor putString(String key, @Nullable String value) {
            return editor.putString(key, value);
        }

        @Override
        public Editor putStringSet(String key, @Nullable Set<String> values) {
            return editor.putStringSet(key, values);
        }

        @Override
        public Editor putInt(String key, int value) {
            return editor.putInt(key, value);
        }

        @Override
        public Editor putLong(String key, long value) {
            return editor.putLong(key, value);
        }

        @Override
        public Editor putFloat(String key, float value) {
            return editor.putFloat(key, value);
        }

        @Override
        public Editor putBoolean(String key, boolean value) {
            return editor.putBoolean(key, value);
        }

        @Override
        public Editor remove(String key) {
            return editor.remove(key);
        }

        @Override
        public Editor clear() {
            return editor.clear();
        }

        @Override
        public boolean commit() {
            if(!isUseTransaction.get()){
                sHandler.post(new SafeCommitTask(editor));
                return true;
            }
            return false;
        }

        @Override
        public void apply() {
            if(!isUseTransaction.get()){
                sHandler.post(new SafeCommitTask(editor));
            }
        }

        @Override
        public void beginTransaction() {
            if(isUseTransaction.get()){
                throw new RuntimeException("now is in transaction,can not start a new transaction");
            }
            isUseTransaction.set(true);
        }

        @Override
        public void endTransaction() {
            if(!isUseTransaction.get()){
                throw new RuntimeException("you must call beginTransaction() first");
            }
            isUseTransaction.set(false);
        }

        @Override
        public void setTransactionSuccessful() {
            sHandler.post(new SafeCommitTask(editor));
        }
    }
}
