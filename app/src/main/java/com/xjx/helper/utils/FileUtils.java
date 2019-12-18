package com.xjx.helper.utils;

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

}
