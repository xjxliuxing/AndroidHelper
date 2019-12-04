package com.xjx.helper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.xjx.helper.global.App;
import com.xjx.helper.utils.FragmentUtil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(App.getInstance().getApplicationContext(), options);


        EaseConversationListFragment listFragment =new EaseConversationListFragment();

        FragmentUtil.replace(this,R.id.fl_content,listFragment);
    }

    private void initView() {
//        final View viewById = findViewById(R.id.iv_test2);
//        viewById.post(new Runnable() {
//            @Override
//            public void run() {
//
//                int width = viewById.getWidth();
//                Log.e("XJX", "width:Value:" + width);
//
//                int height = viewById.getHeight();
//                Log.e("XJX", "height:Value:" + height);
//
//                int paddingLeft = viewById.getPaddingLeft();
//                int paddingTop = viewById.getPaddingTop();
//
//                Log.e("XJX", "paddingLeft:Value:" + paddingLeft);
//                Log.e("XJX", "paddingTop:Value:" + paddingTop);
//
//                ViewGroup viewById1 = findViewById(R.id.rl_content);
//                int paddingLeft1 = viewById1.getPaddingLeft();
//
//                Log.e("XJX", "paddingLeft---Group---:Value:" + paddingLeft1);
//            }
//        });

    }
}
