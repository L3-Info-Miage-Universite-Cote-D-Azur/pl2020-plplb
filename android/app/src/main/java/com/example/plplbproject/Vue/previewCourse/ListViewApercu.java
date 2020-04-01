package com.example.plplbproject.Vue.previewCourse;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


public class ListViewApercu extends ListView {

    public ListViewApercu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewApercu(Context context) {
        super(context);
    }

    public ListViewApercu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}