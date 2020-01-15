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
 * 获取文件的路径
 */
public class FileUtil {

    private static FileUtil fileUtils;

    public static FileUtil getInstance() {
        if (fileUtils == null) {
            fileUtils = new FileUtil();
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
     * @param context
     * @return 获取Sd卡的路径，因为7.0之后SD卡的路径可能会被拒绝访问，所以分为两种不同的情况去获取
     * 1:7.0之上的方式：获取的路径为App内部的路径，会随着App的删除而被删除掉，具体路径为：/storage/emulated/0/Android/data/com.xjx.helper.debug/files/Download
     * 如果需要使用，则在mainfast.xml 中application下面加入：android:requestLegacyExternalStorage="true"
     * <p>
     * 2:7.0以下的方式：获取的是SD卡真实的路径，不会随着App的删除而被删除掉，具体路径为：/storage/emulated/0
     */
    public String getSdPaht(Context context) {
        String path = "";
        int sdkInt = Build.VERSION.SDK_INT;

        if (sdkInt >= Build.VERSION_CODES.Q) {
            // 7.0之上使用其他方法代替
            path = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        } else {
            boolean b = checkSdStatus();
            if (b) {
                path = Environment.getExternalStorageDirectory().getAbsolutePath();
            } else {
                path = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            }
        }

        //path = Environment.getExternalStorageDirectory().getAbsolutePath();

        // 创建父类文件目录
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }

        LogUtil.e("获取的路径为：" + path);
        return path;
    }

}
