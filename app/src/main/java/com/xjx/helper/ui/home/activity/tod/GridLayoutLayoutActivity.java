package com.xjx.helper.ui.home.activity.tod;

import androidx.recyclerview.widget.RecyclerView;

import com.xjx.helper.R;
import com.xjx.helper.adapter.todo.GridLayoutLayoutAdapter;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.entity.todo.GridLayoutLayoutBean;
import com.xjx.helper.enums.PlaceholderStatus;
import com.xjx.helper.utils.ConvertUtil;
import com.xjx.helper.utils.recyeliview.RecycleUtil;
import com.xjx.helper.utils.recyeliview.RecycleViewGridLayoutDivider;

import java.util.ArrayList;
import java.util.List;

public class GridLayoutLayoutActivity extends CommonBaseTitleActivity {

    private RecyclerView mRvList;

    private List<GridLayoutLayoutBean> mList = new ArrayList<>();

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_gridlayout;
    }

    @Override
    protected void initData() {
        super.initData();

        SwitchLoadingStatus(PlaceholderStatus.NONE);
        setTitleContent("车主手册");

        mRvList = findViewById(R.id.rv_list);
        for (int i = 0; i < 12; i++) {
            GridLayoutLayoutBean bean = new GridLayoutLayoutBean();
            bean.setUrl("http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLATnroK8v8mN0o0yIkBEcIU2umnMfqIZMW3HwtFX8aL87r1RGGbDyQ6L7CoFsM496ibrxgpnzDpSsicSplfkS9ypluMicOMBAvVcc/132");
            mList.add(bean);
        }

        GridLayoutLayoutAdapter adapter = new GridLayoutLayoutAdapter(mContext);

        float toDp = ConvertUtil.toDp(10);
        int dp = (int) toDp;

        RecycleUtil
                .getInstance(mContext, mRvList)
                .setVertical()
                .setDataHeight()
                .setGridLayout(3)
                .setDivder(new RecycleViewGridLayoutDivider(dp, 0, 0, dp, dp, 3))
                .setAdapter(adapter);

        adapter.setList(mList);
    }
}
