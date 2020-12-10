package com.kborid.library.adapter;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kborid.library.anim.AlphaInAnimation;
import com.kborid.library.anim.BaseAnimation;
import com.kborid.library.listener.IDataIO;
import com.kborid.library.listener.OnItemClickListener;
import com.kborid.library.listener.SimpleDataIOImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class CommRVAdapter<T> extends RecyclerView.Adapter<RViewHolder> {
    private final static Logger logger = LoggerFactory.getLogger(CommRVAdapter.class);

    private Context mContext;
    private int mLayoutId;

    // 动画
    private int mLastPosition = -1;
    private boolean mOpenAnimationEnable = true;
    private int mDuration = 300;
    private Interpolator mInterpolator = new LinearInterpolator();
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();

    // 接口
    private OnItemClickListener<T> mOnItemClickListener;
    private IDataIO<T> mDataIO;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public CommRVAdapter(Context context, int layoutId, List<T> mDatas) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mDataIO = new SimpleDataIOImpl<>(mDatas, this);
    }

    public CommRVAdapter(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        logger.info("执行onCreateViewHolder()方法~~");
        RViewHolder viewHolder = RViewHolder.get(mContext, null, parent, mLayoutId, -1);
        setItemListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        logger.info("执行onBindViewHolder()方法~~，holder：{}，position：{}", holder, position);
        holder.updatePosition(position);
        //添加动画
        addAnimation(holder);
        convert(holder, position, mDataIO.get(position));
    }

    protected abstract void convert(RViewHolder viewHolder, int position, T t);

    private int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    @Override
    public long getItemId(int position) {
        return mDataIO.get(position).hashCode() ^ position >>> 16;
    }

    private boolean isEnabled(int viewType) {
        return true;
    }

    private void setItemListener(final ViewGroup parent, final RViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener((v) -> {
            if (mOnItemClickListener != null) {
                int position = getPosition(viewHolder);
                mOnItemClickListener.onItemClick(parent, v, mDataIO.get(position), position);
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(v -> {
            if (mOnItemClickListener != null) {
                int position = getPosition(viewHolder);
                return mOnItemClickListener.onItemLongClick(parent, v, mDataIO.get(position), position);
            }
            return false;
        });
    }

    /**
     * add animation when you want to show time
     *
     * @param holder
     */
    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mSelectAnimation != null) {
                    animation = mSelectAnimation;
                }
                if (null != animation) {
                    for (Animator anim : animation.getAnimators(holder.itemView)) {
                        startAnim(anim, holder.getLayoutPosition());
                        Log.d("animline", mLastPosition + "");
                    }
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    /**
     * set anim to start when loading
     *
     * @param anim
     * @param index
     */
    private void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    /**
     * 设置动画
     *
     * @param animation ObjectAnimator
     */
    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mSelectAnimation = animation;
    }

    /**
     * 关闭动画
     */
    public void closeLoadAnimation() {
        this.mOpenAnimationEnable = false;
    }


    @Override
    public int getItemCount() {
        return mDataIO.getSize();
    }

    public IDataIO<T> getDataIO() {
        return mDataIO;
    }
}
