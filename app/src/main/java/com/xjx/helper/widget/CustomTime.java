package com.xjx.helper.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.xjx.helper.R;
import com.xjx.helper.utils.ConvertUtil;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.utils.ScreenUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class CustomTime {

    private static CustomTime customTime;
    private final int mHeight;
    private Dialog dialog;
    private Activity mContext;
    private View mRootView;

    private Calendar mStartDate;
    private Calendar mEndDate;

    private WheelView mWvYear, mWvMonth, mWvDay, mWvhours, mWvMinutes;

    // 用来组装数据的Set集合
    private HashSet<Integer> mYearSet = new HashSet<Integer>();
    private HashSet<Integer> mMonthSet = new HashSet<Integer>();
    private HashSet<Integer> mDaySet = new HashSet<Integer>();

    // 用来设置数据的List集合
    private ArrayList<Integer> mListYear = new ArrayList<>();
    private ArrayList<Integer> mListMonth = new ArrayList<>();
    private ArrayList<Integer> mListDay = new ArrayList<>();
    private ArrayList<Integer> mListHours = new ArrayList<>();
    private ArrayList<Integer> mListMinutes = new ArrayList<>();

    // 默认选中的角标
    private int mIndexYear;
    private int mIndexMonth;
    private int mIndexDay;
    private int mIndexHours;
    private int mIndexMinutes;

    // 默认选中的字段
    private Integer mSelectorYeay;
    private Integer mSelectorMonth;
    private Integer mSelectorDay;
    private Integer mSelectorHours;
    private Integer mSelectorMinutes;

    // 选择器字体的大小
    private int mTextSize = 18;
    private int mStartYear;
    private int mEndYear;
    private int mStartMonth;
    private int mEndMonth;
    private int mStartDay;
    private int mEndDay;

    {
        for (int i = 9; i < 18; i++) {
            mListHours.add(i);
        }
        for (int i = 1; i < 61; i++) {
            mListMinutes.add(i);
        }
    }

    public static CustomTime getInstance(Activity activity, Calendar start, Calendar endDate) {
        if (customTime == null) {
            customTime = new CustomTime(activity, start, endDate);
        }
        return customTime;
    }

    private CustomTime(Activity activity, Calendar start, Calendar endDate) {
        mContext = activity;
        this.mStartDate = start;
        this.mEndDate = endDate;

        // 年份
        mStartYear = mStartDate.get(Calendar.YEAR);
        mEndYear = mEndDate.get(Calendar.YEAR);

        // 月份
        mStartMonth = (mStartDate.get(Calendar.MONTH)) + 1;
        mEndMonth = (mEndDate.get(Calendar.MONTH)) + 1;

        // 日
        mStartDay = mStartDate.get(Calendar.DAY_OF_MONTH);
        mEndDay = mEndDate.get(Calendar.DAY_OF_MONTH);

        // 获取dialog的高度
        int screenHeight = ScreenUtil.getInstance().getScreenHeight();
        if (screenHeight > 0) {
            mHeight = screenHeight / 3;
        } else {
            mHeight = 1920 / 3;
        }

        initDialog();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void initDialog() {
        // 避免重复出现弹窗
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
        dialog = new Dialog(mContext, R.style.base_dialog);

        // 获取布局
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.pickerview_custom_time2, null, false);
        if (mRootView != null) {
            // 先移除，然后在添加，避免崩溃
            ViewParent parent = mRootView.getParent();
            if (parent != null) {
                ViewGroup parent1 = (ViewGroup) parent;
                parent1.removeAllViews();
            }

            dialog.setContentView(mRootView);

            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.getWindow().getAttributes().height = mHeight;
            dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

            // 关闭dialog的监听
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });

            mRootView.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });

            mRootView.findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectorListener != null) {
                        if (mSelectorYeay == null) {
                            mSelectorYeay = mStartYear;
                        }
                        if (mSelectorMonth == null) {
                            mSelectorMonth = mStartMonth;
                        }
                        if (mSelectorDay == null) {
                            mSelectorDay = mStartDay;
                        }
                        if (mSelectorHours == null) {
                            mSelectorHours = mListHours.get(0);
                        }
                        if (mSelectorMinutes == null) {
                            mSelectorMinutes = mListMinutes.get(0);
                        }

                        mSelectorListener.onSelectorItem(mSelectorYeay, mSelectorMonth, mSelectorDay, mSelectorHours, mSelectorMinutes);
                    }

                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });

            // 初始化数据
            setYearData();

            setMonthData();

            setDayData();

            setHours();

            setHours();

            setMinutes();
        }
    }

    /**
     * 设置年份的数据
     */
    private void setYearData() {
        if (mRootView != null) {
            // 获取年的对象
            mWvYear = mRootView.findViewById(R.id.wv_year);

            mWvYear.setGravity(Gravity.CENTER);
            // 添加年份的数据
            if (mStartYear == mEndYear) {
                mYearSet.add(mStartYear);
            } else {
                mYearSet.add(mStartYear);
                for (int i = mStartYear; i <= mEndYear; i++) {
                    mYearSet.add(i);
                }
                mYearSet.add(mEndYear);
            }
            LogUtil.e("---->s_>年份：" + mYearSet);
            // 转换集合
            mListYear = ConvertUtil.SetToList(mYearSet);
            Collections.sort(mListYear);
            // 设置数据
            mWvYear.setAdapter(new ArrayWheelAdapter(mListYear));

            // 设置当前的年份
            mWvYear.setCurrentItem(0);

            mWvYear.setIsOptions(true);
            mWvYear.setCyclic(false);

            mWvYear.setTextSize(mTextSize);

            mWvYear.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mIndexYear = index;
                    mSelectorYeay = mListYear.get(index);

                    LogUtil.e("选中的年份为：" + mSelectorYeay);

                    mIndexMonth = 0;
                    mMonthSet.clear();
                    mListMonth.clear();

                    setMonthData();

                    mIndexDay = 0;
                    mDaySet.clear();
                    mListDay.clear();
                    setDayData();

                    mIndexHours = 0;
                    setHours();

                    mIndexMinutes = 0;
                    setMinutes();
                }
            });
        }
    }

    private void setMonthData() {
        Integer month = 0;
        Integer year = 0;

        if (mRootView != null) {
            // 获取年的对象
            mWvMonth = mRootView.findViewById(R.id.wv_month);

            // 获取年份
            if ((mListYear == null) || (mListYear.size() <= 0)) {
                year = mStartYear;
            } else {
                year = mListYear.get(mIndexYear);
            }

            // 计算月份
            if ((mListMonth == null) || (mListMonth.size() <= 0)) {
                // 获取当前的月份
                month = mStartMonth;
            } else {
                month = mListMonth.get(mIndexMonth);
            }

            // 如果是同一年同一月，就只加入月份
            if ((year == mStartYear) && (year == mEndYear) && (month == mStartMonth) && (month == mEndMonth)) {
                mMonthSet.add(mStartMonth);

            } else {
                // 如果是同一年
                if ((year == mStartYear) && (year == mEndYear)) {
                    for (int i = mStartMonth; i <= mEndMonth; i++) {
                        mMonthSet.add(i);
                    }
                } else {
                    /**
                     * 不是同一年的话，分三种情况
                     * 1：头一年
                     * 2：最后一年
                     * 3：中间的年份
                     */
                    if (year == mStartYear) { // 头一年
                        for (int i = mStartMonth; i <= 12; i++) {
                            mMonthSet.add(i);
                        }
                    } else if (year == mEndYear) { // 最后一年
                        for (int i = 1; i < 12 - mEndMonth; i++) {
                            mMonthSet.add(i);
                        }
                    } else if ((year > mStartYear) && (year < mEndYear)) {
                        // 中间的年份
                        for (int i = 1; i < 12; i++) {
                            mMonthSet.add(i);
                        }
                    }
                }
            }

            LogUtil.e("---->s_>月份：" + mMonthSet);
            // 转换集合
            mListMonth = ConvertUtil.SetToList(mMonthSet);
            Collections.sort(mListMonth);
            // 设置数据
            mWvMonth.setAdapter(new ArrayWheelAdapter(mListMonth));

            // 设置当前的月份
            mWvMonth.setCurrentItem(0);

            mWvMonth.setIsOptions(true);
            mWvMonth.setCyclic(false);

            mWvMonth.setTextSize(mTextSize);

            mWvMonth.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mIndexMonth = index;
                    mSelectorMonth = mListMonth.get(index);
                    LogUtil.e("选中的月份为：" + mSelectorMonth);

                    mIndexDay = 0;
                    mDaySet.clear();
                    mListDay.clear();
                    setDayData();

                    mIndexHours = 0;
                    setHours();

                    mIndexMinutes = 0;
                    setMinutes();
                }
            });
        }
    }

    private void setDayData() {
        Integer year = 0;
        Integer month = 0;

        if (mRootView != null) {
            // 获取日的对象
            mWvDay = mRootView.findViewById(R.id.wv_day);

            // 计算某年中的某月有多少天
            if ((mListYear == null) || (mListYear.size() <= 0)) {
                year = mStartYear;
            } else {
                year = mListYear.get(mIndexYear);
            }
            if (mListMonth == null || mListMonth.size() <= 0) {
                month = mStartMonth;
            } else {
                month = mListMonth.get(mIndexMonth);
            }

            // 同一个月份
            if ((year == mStartYear) && (mEndYear == year) && (month == mStartMonth) && (month == mEndMonth)) {
                // 同一年和同一月，只计算天数

                // 当前的天数
                int day = mStartDate.get(Calendar.DAY_OF_MONTH);
                // 计算这个月份有多少天
                int actualMaximum = mStartDate.getActualMaximum(Calendar.DAY_OF_MONTH);

                for (int i = day; i <= actualMaximum; i++) {
                    mDaySet.add(i);
                }
            } else {
                /**
                 * 不是同一个月份
                 */

                // 1：最上面的月份,取从开始都月末的数据
                if ((year == mStartYear) && (month == mStartMonth)) {
                    // 计算这个月份有多少天
                    int actualMaximum = mStartDate.getActualMaximum(Calendar.DAY_OF_MONTH);

                    for (int i = mStartDay; i <= actualMaximum; i++) {
                        mDaySet.add(i);
                    }
                } else if ((year == mEndYear) && (month == mEndMonth)) {
                    // 最后的那个月，取从开始到结束的数据
                    for (int i = 1; i <= (mEndDay); i++) {
                        mDaySet.add(i);
                    }

                } else {
                    // 中间月份的天数

                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, month - 1);

                    int actualMaximum = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    for (int i = 1; i <= actualMaximum; i++) {
                        mDaySet.add(i);
                    }
                }

            }
            LogUtil.e("---->s_>日：" + mDaySet);
            // 转换集合
            mListDay = ConvertUtil.SetToList(mDaySet);
            Collections.sort(mListDay);
            // 设置数据
            mWvDay.setAdapter(new ArrayWheelAdapter(mListDay));

            // 设置当前的月份
            mWvDay.setCurrentItem(0);

            mWvDay.setIsOptions(true);
            mWvDay.setCyclic(false);

            mWvDay.setTextSize(mTextSize);

            mWvDay.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mIndexDay = index;
                    mSelectorDay = mListDay.get(index);
                    LogUtil.e("选中的日为：" + mSelectorDay);

                    mIndexHours = 0;
                    setHours();

                    mIndexMinutes = 0;
                    setMinutes();
                }
            });
        }
    }

    private void setHours() {
        if (mRootView != null) {
            // 获取日的对象
            mWvhours = mRootView.findViewById(R.id.wv_hours);

            LogUtil.e("---->s_>日：" + mListHours);

            // 设置数据
            mWvhours.setAdapter(new ArrayWheelAdapter(mListHours));

            // 设置当前的月份
            mWvhours.setCurrentItem(0);

            mWvhours.setIsOptions(true);
            mWvhours.setCyclic(false);

            mWvhours.setTextSize(mTextSize);

            mWvhours.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mIndexHours = index;
                    mSelectorHours = mListHours.get(index);
                    LogUtil.e("选中的日为：" + mSelectorHours);

                    mIndexMinutes = 0;
                    setMinutes();
                }
            });
        }
    }

    private void setMinutes() {

        if (mRootView != null) {
            // 获取日的对象
            mWvMinutes = mRootView.findViewById(R.id.wv_minutes);

            LogUtil.e("---->s_>日：" + mListMinutes);

            // 设置数据
            mWvMinutes.setAdapter(new ArrayWheelAdapter(mListMinutes));

            // 设置当前的月份
            mWvMinutes.setCurrentItem(0);

            mWvMinutes.setIsOptions(true);
            mWvMinutes.setCyclic(false);

            mWvMinutes.setTextSize(mTextSize);

            mWvMinutes.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mIndexMinutes = index;
                    mSelectorMinutes = mListMinutes.get(index);
                    LogUtil.e("选中的日为：" + mSelectorMinutes);
                }
            });
        }
    }

    /**
     * 展示dialog
     */
    public void show() {
        if ((dialog != null) && (mContext != null) && (!mContext.isFinishing()) && (!dialog.isShowing())) {
            dialog.show();
        }
    }

    private onSelectorListener mSelectorListener;

    public void setOnSelectorListener(onSelectorListener selectorListener) {
        mSelectorListener = selectorListener;
    }

    public interface onSelectorListener {
        void onSelectorItem(Integer year, Integer month, Integer day, Integer hours, Integer minutes);
    }
}
