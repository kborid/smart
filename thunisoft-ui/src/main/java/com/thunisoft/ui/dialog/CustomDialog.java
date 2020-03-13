package com.thunisoft.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thunisoft.common.tool.UIHandler;
import com.thunisoft.ui.R;
import com.thunisoft.ui.widget.NoScrollListView;

import java.util.Stack;

public class CustomDialog extends Dialog {
    private static Stack<CustomDialog> m_CustomDialogStack = new Stack<>();
    private TextView title;
    private RelativeLayout contextLayout;
    private TextView message;
    private TextView error;
    private LinearLayout buttonLayout;
    private Button button1;
    private Button button2;
    private View btn_layout_divider;
    private View btn_divider;
    private boolean isExitDialog = false;
    private boolean enableBackEvent = true;

    private LinearLayout rootLinear;

    private LinearLayout ll_title;
    private ImageView img_warning;

    //    private LinearLayout checkboxLayout;
//    private TextView checkTextView;
//    private ImageView checkbox;
    private boolean isDismiss = true;
    private static CustomDialog mDialog = null;
    private AlertParams P;

    public CustomDialog(Context context) {
        this(context, R.style.dialogStyle);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        P = new AlertParams(getContext());
        setContentView(R.layout.dialog_view);
        setCanceledOnTouchOutside(false);
        findViews();
    }

    public void setDismiss(boolean isDismiss) {
        this.isDismiss = isDismiss;
    }

    @Override
    public void show() {
        mDialog = this;
        m_CustomDialogStack.push(mDialog);
        super.show();
    }

    @Override
    public void dismiss() {
        if (isDismiss) {
            super.dismiss();
            if (!m_CustomDialogStack.isEmpty() && (mDialog != null)) {
                m_CustomDialogStack.pop();
            }
            mDialog = null;
        }
    }

    public static void dismissAll() {
        while (!m_CustomDialogStack.isEmpty()) {
            mDialog = m_CustomDialogStack.peek();
            mDialog.dismiss();
        }
    }

    public static boolean isDialogShow() {
        if (m_CustomDialogStack.isEmpty()) {
            return false;
        }
        return true;
    }

    public static CustomDialog hasDialog() {
        return mDialog;
    }

    public interface OnMobileMoveListener {
        void onMobileMove();
    }

    private void findViews() {

        rootLinear = (LinearLayout) findViewById(R.id.id_dialog_ll_root);

        ll_title = (LinearLayout) findViewById(R.id.id_ll_dialog_title);
        img_warning = (ImageView) findViewById(R.id.id_img_warning);
        title = (TextView) findViewById(R.id.title_view);
        contextLayout = (RelativeLayout) findViewById(R.id.content_layout);
        message = (TextView) findViewById(R.id.message_view);
        error = (TextView) findViewById(R.id.error_view);
        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        btn_divider = findViewById(R.id.btn_divider);
        btn_layout_divider = findViewById(R.id.btn_layout_divider);
//        checkboxLayout = (LinearLayout) findViewById(R.id.checkboxLayout);
//        checkTextView = (TextView) findViewById(R.id.checkbox_textview);
//        checkbox = (ImageView) findViewById(R.id.checkbox_imageview);
    }

    public void showTitleWarning() {
        if (img_warning != null) {
            img_warning.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置dialog主题颜色，默认#1989fe
     *
     * @param argb
     */
    public void setPrimaryColor(int argb) {
        GradientDrawable drawable = (GradientDrawable) getContext().getResources().getDrawable(R.drawable.shape_dialog_title);
        drawable.setColor(argb);
        ll_title.setBackground(drawable);
        button2.setTextColor(argb);
    }

    public LinearLayout getRootLinear() {
        if (null == rootLinear) {
            rootLinear = (LinearLayout) findViewById(R.id.id_dialog_ll_root);
        }
        return rootLinear;
    }

    @Override
    public boolean onSearchRequested() {
        return true;
    }

    /*private void setCheckBox(boolean isChecked) {
        if (isChecked) {
            checkbox.setImageResource(R.drawable.checked);
        } else {
            checkbox.setImageResource(R.drawable.check);
        }
    }*/

    private void apply(final AlertParams p) {
        /*设置标题title*/
        if (TextUtils.isEmpty(p.mTitle)) {
            ll_title.setVisibility(View.GONE);
        } else {
            title.setText(p.mTitle);
        }

        /*设置消息文本内容message*/
        if (TextUtils.isEmpty(p.mMessage)) {
            message.setVisibility(View.GONE);
        } else {
            message.setText(p.mMessage);
        }

        /*设置错误文本内容error info*/
        if (TextUtils.isEmpty(p.mError)) {
            error.setVisibility(View.GONE);
        } else {
            error.setText(p.mError);
        }

        /*设置按钮listener*/
        if (TextUtils.isEmpty(p.mPositiveButtonText)
                && TextUtils.isEmpty(p.mNegativeButtonText)) {
            btn_layout_divider.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.GONE);
        } else {
            if (TextUtils.isEmpty(p.mPositiveButtonText)) {
                button2.setVisibility(View.GONE);
                btn_divider.setVisibility(View.GONE);
            } else {
                button2.setText(p.mPositiveButtonText);
                button2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (p.mPositiveButtonListener != null) {
                            p.mPositiveButtonListener.onClick(
                                    CustomDialog.this, 0);
                        }
                    }

                });
            }

            if (TextUtils.isEmpty(p.mNegativeButtonText)) {
                button1.setVisibility(View.GONE);
                btn_divider.setVisibility(View.GONE);
            } else {
                button1.setText(p.mNegativeButtonText);
                button1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (p.mNegativeButtonListener != null) {
                            p.mNegativeButtonListener.onClick(
                                    CustomDialog.this, 0);
                        }
                        if (!TextUtils.isEmpty(p.mPositiveButtonText)) {
                            p.recovery();
                        }
                    }

                });
            }
        }

        /*设置不再询问checkbox*/
        /*if (p.mCheckText == null) {
            checkboxLayout.setVisibility(View.GONE);
        } else {
            checkTextView.setText(p.mCheckText);
            setCheckBox(p.mCheckboxChecked);
            checkbox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (p.mCheckboxChecked) {
                        p.mCheckboxChecked = false;
                        setCheckBox(false);
                    } else {
                        p.mCheckboxChecked = true;
                        setCheckBox(true);
                    }

                }
            });

            checkboxLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (p.mCheckboxChecked) {
                        p.mCheckboxChecked = false;
                        setCheckBox(false);
                    } else {
                        p.mCheckboxChecked = true;
                        setCheckBox(true);
                    }

                }
            });
        }*/

        if (p.mItems != null) {
            View listView = createListView(p);
            if (listView != null) {
                contextLayout.addView(listView);
            }
        }
    }

    /**
     * 设置单选、多选listView
     *
     * @param p
     * @return
     */
    private View createListView(final AlertParams p) {
        final NoScrollListView listView = new NoScrollListView(getContext());
        listView.setSelector(R.color.transparent);
        listView.setDivider(new ColorDrawable(getContext().getResources().getColor(R.color.common_divider))); // item line
        listView.setDividerHeight(1);
        ArrayAdapter<CharSequence> adapter;
        if (p.mIsMultiChoice) {
            adapter = new ArrayAdapter<CharSequence>(getContext(),
                    R.layout.dialog_item_select_multichoice, R.id.text1,
                    p.mItems) {
                @Override
                public View getView(int position, View convertView,
                                    ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    if (p.mCheckedItems != null) {
                        if (p.mCheckedItems[position]) {
                            listView.setItemChecked(position, true);
                        } else {
                            listView.setItemChecked(position, false);

                        }
                    }

                    TextView txt = (TextView) view.findViewById(R.id.text1);
                    if (p.mEnabledItems != null) {
                        if (p.mEnabledItems[position]) {
                            txt.setEnabled(true);
                        } else {
                            txt.setEnabled(false);
                        }
                    }

                    return view;
                }

                @Override
                public boolean isEnabled(int position) {
                    if (P.mEnabledItems != null) {
                        return P.mEnabledItems[position];
                    } else {
                        return super.isEnabled(position);
                    }
                }
            };
        } else {
            final int layout = p.mIsSingleChoice ? R.layout.dialog_item_select_singlechoice
                    : R.layout.dialog_item_select_listchoice;
            adapter = new ArrayAdapter<CharSequence>(getContext(), layout,
                    R.id.text1, p.mItems) {
                @Override
                public boolean isEnabled(int position) {
                    if (P.mEnabledItems != null) {
                        return P.mEnabledItems[position];
                    } else {
                        return super.isEnabled(position);
                    }
                }
            };
            UIHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (p.mCheckedItem > -1) {
                        listView.setItemChecked(p.mCheckedItem, true);
                        listView.setSelection(p.mCheckedItem);
                    }
                }
            });
        }

        listView.setAdapter(adapter);

        if (p.mOnClickListener != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    p.mOnClickListener.onClick(CustomDialog.this, position);
                    if (!p.mIsSingleChoice) {
                        dismiss();
                    } else {
                        p.mCheckedItem = position;
                    }
                }
            });
        } else if (p.mOnCheckboxClickListener != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    if (p.mCheckedItems != null) {
                        p.mCheckedItems[position] = listView
                                .isItemChecked(position);
                    }
                    p.mOnCheckboxClickListener.onClick(CustomDialog.this,
                            position, listView.isItemChecked(position));
                }
            });
        }

        if (p.mIsSingleChoice) {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        } else if (p.mIsMultiChoice) {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }
        return listView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        apply(P);
    }

    public boolean[] getCheckedItems() {
        return P.mCheckedItems;
    }

    private static final int KEYCODE_ESCAPE = 111;

    @Override
    public boolean dispatchKeyEvent(KeyEvent ev) {
        if (!enableBackEvent) {
            if (ev.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                return true;
            }
        }

        if (ev.getKeyCode() == KEYCODE_ESCAPE) {
            ev = new KeyEvent(ev.getDownTime(), ev.getEventTime(),
                    ev.getAction(), KeyEvent.KEYCODE_BACK, ev.getRepeatCount());
        }
        if (!isExitDialog) {
            if (ev.getDeviceId() > 0
                    && ev.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if (ev.getAction() == KeyEvent.ACTION_DOWN) {
                    return true;
                } else {
                    if (button2.getVisibility() == View.VISIBLE) {
                        button2.performClick();
                        return true;
                    } else if (button1.getVisibility() == View.VISIBLE) {
                        button1.performClick();
                        return true;
                    }
                }
            }
        }
        return super.dispatchKeyEvent(ev);
    }

    /**
     * for multichoices list;
     */
    public void setCheckedItems(boolean[] checkItems) {
        P.mCheckedItems = checkItems.clone();
    }

    public CharSequence getTitle() {
        return P.mTitle;
    }

    public void setTitle(String title) {
        P.mTitle = title;
    }

    public void setTitle(int titleId) {
        P.mTitle = P.mContext.getText(titleId);
    }

    public CharSequence getMessage() {
        return P.mMessage;
    }

    public void setMessage(String message) {
        P.mMessage = message;
    }

    public void setMessage(CharSequence message) {
        P.mMessage = message;
    }

    public void setMessage(int messageId) {
        P.mMessage = P.mContext.getText(messageId);
    }

    public void setErrorInfo(String error) {
        P.mError = error;
    }

    public void setIsExitDialog(boolean isExit) {
        isExitDialog = isExit;
    }

    public void setEnableBackEvent(boolean enable) {
        enableBackEvent = enable;
    }

    public void setOnKeyListener(OnKeyListener mOnKeyListener) {
        P.mOnKeyListener = mOnKeyListener;
        super.setOnKeyListener(mOnKeyListener);
    }

    public void setOnDismissListener(
            OnDismissListener dismissListener) {
        P.mOnDismissListener = dismissListener;
        super.setOnDismissListener(dismissListener);
    }

    public void setOnCancelListener(
            OnCancelListener cancelListener) {
        P.mOnCancelListener = cancelListener;
        super.setOnCancelListener(cancelListener);
    }

    public void setPositiveButton(int textId, final OnClickListener listener) {
        P.mPositiveButtonText = P.mContext.getText(textId);
        P.mPositiveButtonListener = listener;
    }

    public void setPositiveButton(CharSequence text,
                                  final OnClickListener listener) {
        P.mPositiveButtonText = text;
        P.mPositiveButtonListener = listener;
    }

    public void setNegativeButton(int textId, final OnClickListener listener) {
        P.mNegativeButtonText = P.mContext.getText(textId);
        P.mNegativeButtonListener = listener;
    }

    public void setNegativeButton(CharSequence text,
                                  final OnClickListener listener) {
        P.mNegativeButtonText = text;
        P.mNegativeButtonListener = listener;
    }

    public void setItems(CharSequence[] items, final OnClickListener listener) {
        P.mItems = items;
        P.mOnClickListener = listener;
    }

    public void setMultiChoiceItems(CharSequence[] items,
                                    boolean[] checkedItems, final OnMultiChoiceClickListener listener) {
        P.mItems = items;
        P.mOnCheckboxClickListener = listener;
        P.mCheckedItems = checkedItems;
        P.mIsMultiChoice = true;
    }

    public void setSingleChoiceItems(CharSequence[] items, int checkedItem,
                                     final OnClickListener listener) {
        P.mItems = items;
        P.mOnClickListener = listener;
        P.mCheckedItem = checkedItem;
        P.mIsSingleChoice = true;
    }

    public void setItemEnable(boolean[] enabledItems) {
        P.mEnabledItems = enabledItems;
    }

    public void setCheckBox(CharSequence text, boolean isChecked) {
        P.mCheckText = text;
        P.mCheckboxChecked = isChecked;
    }

    public void setCheckBox(int textid, boolean isChecked) {
        P.mCheckText = P.mContext.getText(textid);
        P.mCheckboxChecked = isChecked;
    }

    public boolean isChecked() {
        return P.mCheckboxChecked;
    }

    public void addView(View view) {
        contextLayout.addView(view);
        contextLayout.setGravity(Gravity.CENTER);
    }

    public void hideButton() {
        if (buttonLayout == null)
            return;
        buttonLayout.setVisibility(View.GONE);
    }

    /* package */static class AlertParams {
        public boolean isShowing;
        public final Context mContext;
        public DialogInterface mDialog;

        public CharSequence mTitle;
        public CharSequence mMessage;
        public CharSequence mError;
        public CharSequence mPositiveButtonText;
        public OnClickListener mPositiveButtonListener;
        public CharSequence mNegativeButtonText;
        public OnClickListener mNegativeButtonListener;
        public OnCancelListener mOnCancelListener;
        public OnKeyListener mOnKeyListener;
        public OnDismissListener mOnDismissListener;
        public CharSequence[] mItems;
        public OnClickListener mOnClickListener;
        public boolean[] mCheckedItems;
        public boolean[] mEnabledItems;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public int mCheckedItem = -1;
        public OnMultiChoiceClickListener mOnCheckboxClickListener;
        public boolean mCheckboxChecked;
        public CharSequence mCheckText;

        public AlertParams(Context context) {
            isShowing = false;
            mContext = context;
        }

        private boolean[] mCheckedItemsForTemp;
        private int mCheckedItemForTemp = -1;
        private boolean mCheckboxCheckedForTemp;

        public void store() {
            if (mCheckedItems != null) {
                mCheckedItemsForTemp = mCheckedItems.clone();
            }
            mCheckedItemForTemp = mCheckedItem;
            mCheckboxCheckedForTemp = mCheckboxChecked;
        }

        public void recovery() {
            if (mCheckedItemsForTemp != null) {
                mCheckedItems = mCheckedItemsForTemp.clone();
            }
            mCheckedItem = mCheckedItemForTemp;
            mCheckboxChecked = mCheckboxCheckedForTemp;
        }
    }
}
