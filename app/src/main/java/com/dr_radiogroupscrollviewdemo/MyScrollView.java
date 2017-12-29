package com.dr_radiogroupscrollviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DuanRui on 2017/12/29.
 */

public class MyScrollView extends ScrollView {

    OnScrollChangedListener mOnScrollChangedListener;


    //自定义接口暴露 onScrollChanged 方法
    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        mOnScrollChangedListener = onScrollChangedListener;
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if(mOnScrollChangedListener != null){
            mOnScrollChangedListener.onScrollChanged(l,t,oldl,oldt);
        }
    }


    public interface OnScrollChangedListener {

        void onScrollChanged(int l, int t, int oldl, int oldt) ;
    }
}
