package com.kborid.smart.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kborid.smart.R;
import com.thunisoft.common.util.ToastUtils;
import com.thunisoft.ui.util.ScreenUtils;

public class CodeInputTextLayout extends LinearLayout {

    private static int DISPLAY_COUNT = 6;

    private LinearLayout displayLayout;
    private EditText etInput;

    private String[] values = new String[DISPLAY_COUNT];

    public CodeInputTextLayout(Context context) {
        this(context, null);
    }

    public CodeInputTextLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeInputTextLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_code_text, this);
        displayLayout = (LinearLayout) findViewById(R.id.displayLayout);
        etInput = (EditText) findViewById(R.id.et_input);
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTextValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == DISPLAY_COUNT) {
                    ToastUtils.showToast("input completed");
                }
            }
        });
        refreshDisplayLayout();
    }

    private void setTextValue(String s) {
        for (int i = 0; i < values.length; i++) {
            try {
                values[i] = String.valueOf(s.charAt(i));
            } catch (Exception e) {
                values[i] = "";
            }
        }
        for (int i = 0; i < values.length; i++) {
            TextView textView = (TextView) displayLayout.getChildAt(i);
            textView.setText(String.valueOf(values[i]));
        }
    }

    public void setDisplayCount(int count) {
        if (count != DISPLAY_COUNT) {
            DISPLAY_COUNT = count;
            refreshDisplayLayout();
        }
    }

    private void refreshDisplayLayout() {
        etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DISPLAY_COUNT)});
        displayLayout.removeAllViews();
        for (int i = 0; i < DISPLAY_COUNT; i++) {
            TextView textView = new TextView(getContext());
            textView.setTextSize(40);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.shape_code_text);
            textView.setFocusable(false);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.height = ScreenUtils.dp2px(60);
            lp.width = ScreenUtils.dp2px(60);
            lp.gravity = Gravity.CENTER;
            lp.setMargins(ScreenUtils.dp2px(5), ScreenUtils.dp2px(5), ScreenUtils.dp2px(5), ScreenUtils.dp2px(5));
            displayLayout.addView(textView, lp);
        }
    }
}
