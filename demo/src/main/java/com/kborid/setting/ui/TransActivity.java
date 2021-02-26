package com.kborid.setting.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import butterknife.BindView;
import com.kborid.demo.t_realm.entity.User;
import com.kborid.setting.R;
import com.thunisoft.common.base.BaseSimpleActivity;
import io.realm.Realm;
import io.realm.RealmResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransActivity extends BaseSimpleActivity {

    private static final Logger logger = LoggerFactory.getLogger(TransActivity.class);

    @BindView(R.id.tv_tt)
    TextView mTVtt;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_trans;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

        mTVtt.setOnClickListener(v -> System.out.println("click发生"));

        mTVtt.setOnTouchListener((v, event) -> {
            System.out.println("touch发生");
            return false;
        });
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
