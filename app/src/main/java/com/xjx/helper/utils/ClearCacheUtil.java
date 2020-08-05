package com.xjx.helper.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2019/5/8.
 * 清除缓存的工具类
 */
@SuppressLint("NewApi")
public class ClearCacheUtil {

    private static ClearCacheUtil clearCacheUtil;
    private Context mContext;

    public static ClearCacheUtil getInstance(Context context) {
        if (clearCacheUtil == null) {
            clearCacheUtil = new ClearCacheUtil(context);
        }

        return clearCacheUtil;
    }

    public ClearCacheUtil() {
    }

    public ClearCacheUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 获取缓存大小
     *
     * @return
     * @throws Exception
     */
    public String getTotalCacheSize() throws Exception {

        long cacheSize = getFolderSize(mContext.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(mContext.getExternalCacheDir());
        }

        cacheSize += getFolderSize(new File("/data/data/" + mContext.getPackageName() + "/databases"));
        cacheSize += getFolderSize(mContext.getFilesDir());

        return getFormatSize(cacheSize);
    }

    // 获取文件
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    private String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /*********************************  删除的操作 ******************************************************/

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     */
    private void cleanSharedPreference() {
        deleteFilesByDirectory(new File("/data/data/"
                + mContext.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
     **/
    private void cleanDatabases() {
        boolean b2 = deleteDir(new File("/data/data/" + mContext.getPackageName() + "/databases"));
        LogUtil.e("b2:" + b2);
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     */
    private void cleanFiles() {
        boolean b3 = deleteDir(mContext.getFilesDir());
        LogUtil.e("b3:" + b3);
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     */
    private void cleanExternalCache() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            boolean b4 = deleteDir(mContext.getExternalCacheDir());
            LogUtil.e("b4:" + b4);
        }
    }

    /**
     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
     *
     * @param filePath
     */
    private void cleanCustomCache(String filePath) {
        deleteDir(new File(filePath));
    }

    /**
     * * 清除本应用所有的数据 * *
     */
    public void cleanApplicationData() {

        boolean b = deleteDir(mContext.getCacheDir());
        LogUtil.e("b:" + b);

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            boolean b1 = deleteDir(mContext.getExternalCacheDir());
            LogUtil.e("b1:" + b1);
        }

        cleanDatabases();
        cleanFiles();
        cleanExternalCache();

    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists()) {
            for (File item : directory.listFiles()) {
                boolean delete = item.delete();
                LogUtil.e("删除成功---> ? " + delete);
            }
        } else {
            LogUtil.e("文件对象为空！");
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
