package com.kborid.setting.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;
import butterknife.BindView;
import com.kborid.demo.t_realm.entity.User;
import com.kborid.setting.R;
import com.thunisoft.common.base.BaseSimpleActivity;
import io.realm.Realm;
import io.realm.RealmResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransActivity extends BaseSimpleActivity {

    private final static Logger logger = LoggerFactory.getLogger(TransActivity.class);

    @BindView(R.id.tv_tt)
    TextView mTVtt;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_trans;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                logger.info("【Name = {}】", name);
                int n = attrs.getAttributeCount();
                for (int i = 0; i < n; i++) {
                    logger.info("【Attr = {}】:{}", attrs.getAttributeName(i), attrs.getAttributeValue(i));
                }
                if ("ImageView".equals(name)) {
                    AppCompatDelegate delegate = getDelegate();
                    ImageView view = (ImageView) delegate.createView(parent, name, context, attrs);
                    view.setImageResource(R.mipmap.logo_small);
                    return view;
                }
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });
        super.onCreate(savedInstanceState);

        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    private Handler hOuter = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void initDataAndEvent(@Nullable Bundle bundle) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> {
                User user = r.createObject(User.class);
                user.setName("呆未厶");
                user.setAge(10);
                user.setAddress("山东省威海市");
            });

            RealmResults<User> results = realm.where(User.class).findAll();
            System.out.println(results.size());
        } catch (Exception e) {
            logger.error("插入数据库失败", e);
        }

        mTVtt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click发生");
            }
        });

        mTVtt.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("touch发生");
                return false;
            }
        });

        Button btn = new Button(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View view) {
//        ContentResolver resolver = getContentResolver();
//        try {
//            resolver.openFileDescriptor(Uri.parse("content://tt/t.txt"), "r");
//        } catch (FileNotFoundException e) {
//            logger.error("文件不存在", e);
//        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        logger.info("dispatchTouchEvent({})", ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("DOWN事件");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MOVE事件");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("UP事件");
                break;
            default:
                break;
        }
        return false;
    }
}
