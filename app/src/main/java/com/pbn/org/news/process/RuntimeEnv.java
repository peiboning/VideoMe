package com.pbn.org.news.process;

import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class RuntimeEnv {
    private static String PROCESS_SERVICE = "service";
    private static boolean isMainProcess;
    private static boolean isService;

    public static boolean isIsMainProcess() {
        return isMainProcess;
    }

    public static boolean isIsService() {
        return isService;
    }

    public static void JudgeProcess(){
        String processName = getProcessName();
        if(!TextUtils.isEmpty(processName)){
            if(processName.contains(":")){
                String name = processName.split(":")[1].trim();
                if(PROCESS_SERVICE.equals(name)){
                    isService = true;
                }
            }else{
                isMainProcess = true;
            }
        }
        Log.e("RuntimeEnv", "processName is " + processName + "  ,pid=" + Process.myPid());
    }

    private static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
