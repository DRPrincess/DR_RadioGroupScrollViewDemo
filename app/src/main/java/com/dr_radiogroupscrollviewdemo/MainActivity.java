package com.dr_radiogroupscrollviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    RadioGroup mRadioGroup;
    MyScrollView mScrollView;
    LinearLayout llScrollViewContent;

    String[] components = {"商品简介", "评价列表", "详情", "推荐"};


    private View goodsView;
    private View commentView;
    private View detailView;
    private View recommendView;

    boolean isScrollFlag = false ;
    boolean isRadioFlag = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mScrollView = (MyScrollView) findViewById(R.id.scrollView);
        llScrollViewContent = (LinearLayout) findViewById(R.id.llScrollContent);


        initContentView();

        mRadioGroup.check(R.id.rbtnGoods);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //防止onCheckedChanged 被调用多次
                View viewById = mRadioGroup.findViewById(checkedId);
                if (!viewById.isPressed()){
                    return;
                }


                //拦截 scrollView 中的联动触发
                if(isScrollFlag){
                    isScrollFlag = isRadioFlag = false;
                    return;
                }

                isRadioFlag = true;
                int y = 0;
                switch (checkedId) {
                    case R.id.rbtnGoods:
                        y = goodsView.getTop();
                        break;
                    case R.id.rbtnComment:
                        y = commentView.getTop();
                        break;
                    case R.id.rbtnDetail:
                        y = detailView.getTop();
                        break;
                    case R.id.rbtnRecommend:
                        y = recommendView.getTop();
                        break;

                }

                mScrollView.smoothScrollTo(0, y);
            }
        });


        mScrollView.setOnScrollChangedListener(new MyScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {


                //拦截 radioGroup 选中的联动触发
                if(isRadioFlag ){
                    isRadioFlag = isScrollFlag = false;

                    return;
                }

                isScrollFlag = true;

                if (t < commentView.getTop() && mRadioGroup.getCheckedRadioButtonId() != R.id.rbtnGoods) {

                    mRadioGroup.check(R.id.rbtnGoods);

                } else if (t >= commentView.getTop() && t < detailView.getTop() && mRadioGroup.getCheckedRadioButtonId() != R.id.rbtnComment) {
                    mRadioGroup.check(R.id.rbtnComment);

                } else if (t >=  detailView.getTop() && t < recommendView.getTop() && mRadioGroup.getCheckedRadioButtonId() != R.id.rbtnDetail) {
                    mRadioGroup.check(R.id.rbtnDetail);

                } else if (t >=  recommendView.getTop() && mRadioGroup.getCheckedRadioButtonId() != R.id.rbtnRecommend) {
                    mRadioGroup.check(R.id.rbtnRecommend);
                }else{
                    isScrollFlag = false;
                }
            }
        });


    }

    private void initContentView() {
        for (String s : components) {

            View view = LayoutInflater.from(this).inflate(R.layout.component_item_layout, null);
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setText(s);

            int height = 1888;


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

            llScrollViewContent.addView(view, layoutParams);

        }


        goodsView = llScrollViewContent.getChildAt(0);
        commentView = llScrollViewContent.getChildAt(1);
        detailView = llScrollViewContent.getChildAt(2);
        recommendView = llScrollViewContent.getChildAt(3);
    }


}
