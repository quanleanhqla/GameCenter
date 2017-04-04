package com.example.quanla.quannet.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.quanla.quannet.App;

/**
 * Created by QuanLA on 3/18/2017.
 */

public class CanaroTextView extends TextView {
    public CanaroTextView(Context context) {
        this(context, null);
    }

    public CanaroTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanaroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(App.canaroExtraBold);
    }
}
