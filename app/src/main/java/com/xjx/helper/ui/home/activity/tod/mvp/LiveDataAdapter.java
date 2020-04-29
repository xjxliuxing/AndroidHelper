package com.xjx.helper.ui.home.activity.tod.mvp;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xjx.helper.R;
import com.xjx.helper.base.BaseRecycleAdapter;
import com.xjx.helper.base.BaseVH;

public class LiveDataAdapter extends BaseRecycleAdapter<LiveBean> {


    public LiveDataAdapter(Activity mContext) {
        super(mContext);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_live_data;
    }

    @Override
    protected void onBind(@NonNull BaseVH holder, LiveBean data, int position) {
        holder.setTextView(R.id.tv_name, data.getTitle());
        holder.setTextView(R.id.tv_number, data.getNumber() + "");

        TextView btn_add = holder.getTextView(R.id.btn_add);
        TextView tv_number = holder.getTextView(R.id.tv_number);
        TextView btn_remove = holder.getTextView(R.id.btn_remove);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = data.getNumber();
                int value = number + 1;
                tv_number.setText((value) + "");
                data.setNumber(value);
            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = data.getNumber();
                int value = number - 1;
                tv_number.setText((value) + "");
                data.setNumber(value);
            }
        });


    }
}
