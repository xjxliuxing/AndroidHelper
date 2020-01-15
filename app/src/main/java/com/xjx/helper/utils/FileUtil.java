package com.xjx.helper.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 从原有的一丰项目拿过来的
 * Created by erge 2019-10-25 13:24
 */
public class FileUtils {

    private static FileUtils fileUtils;

    public static FileUtils getInstance() {
        if (fileUtils == null) {
            fileUtils = new FileUtils();
        }
        return fileUtils;
    }

    /**
     * 检测sd卡是否可用
     */
    private boolean checkSdStatus() {
        //判断SDcard是否存在并且可读写
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存到SD卡
     *
     * @param filename
     * @param filecontent
     * @throws Exception
     */
    public void saveToSdContent(String filename, String filecontent) {
        boolean b = checkSdStatus();
        if (b) {
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            Environment.getExternalStorageDirectory();
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            FileOutputStream outStream = null;
            try {
                outStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                outStream.write(filecontent.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.showToast("Sd卡不可用");
        }
    }

    public String getSdContent(String filename) {
        String sTempOneLine = "";
        boolean b = checkSdStatus();
        if (b) {
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            try {
                FileInputStream inputStream = new FileInputStream(file);
                BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer tStringBuffer = new StringBuffer();

                while (true) {
                    try {
                        if (!((sTempOneLine = tBufferedReader.readLine()) != null)) {
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                    tStringBuffer.append(sTempOneLine);
                }
                return tStringBuffer.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            ToastUtil.showToast("Sd卡不可用");
            return null;
        }
    }

    /**
     * @return 获取SD卡的路径
     */

    /**
     * @param context
     * @return 获取Sd卡的路径，如果是在7.0之上就获取app中file目录下的文件，否则就获取sd卡中的路径
     */
    public String getSdPaht(Context context) {
        String path = "";
        int sdkInt = Build.VERSION.SDK_INT;

        if (sdkInt >= Build.VERSION_CODES.Q) {
            // 7.0之上使用其他方法代替
            path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        } else {
            boolean b = checkSdStatus();
            if (b) {
                path = Environment.getExternalStorageDirectory().getAbsolutePath();
            } else {
                path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            }
        }
        LogUtil.e("获取的路径为：" + path);
        return path;
    }

}
