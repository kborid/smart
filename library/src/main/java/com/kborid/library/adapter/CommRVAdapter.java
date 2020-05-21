package com.kborid.library.adapter;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kborid.library.animation.AlphaInAnimation;
import com.kborid.library.animation.BaseAnimation;

import java.util.ArrayList;
import java.util.List;

public abstract class CommRVAdapter<T> extends RecyclerView.Adapter<ViewHolderHelper> implements DataIO<T> {
    private Context mContext;
    private int mLayoutId;
    private List<T> mDatas = new ArrayList<>();

    private OnItemClickListener<T> mOnItemClickListener;


    //动画
    private int mLastPosition = -1;
    private boolean mOpenAnimationEnable = true;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public CommRVAdapter(Context context, int layoutId, List<T> mDatas) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mDatas = mDatas;
    }

    public CommRVAdapter(Context context, int layoutId) {
        this.mContext = context;
        this.mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public ViewHolderHelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderHelper viewHolder = ViewHolderHelper.get(mContext, null, parent, mLayoutId, -1);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderHelper holder, int position) {
        holder.updatePosition(position);
        //添加动画
        addAnimation(holder);
        convert(holder, mDatas.get(position));
    }

    protected abstract void convert(ViewHolderHelper helper, T t);

    private int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    private boolean isEnabled(int viewType) {
        return true;
    }


    private void setListener(final ViewGroup parent, final ViewHolderHelper viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener((v) -> {
            if (mOnItemClickListener != null) {
                int position = getPosition(viewHolder);
                mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(v -> {
            if (mOnItemClickListener != null) {
                int position = getPosition(viewHolder);
                return mOnItemClickListener.onItemLongClick(parent, v, mDatas.get(position), position);
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
        return mDatas.size();
    }

    @Override
    public void add(T element) {
        mDatas.add(element);
        notifyDataSetChanged();
    }

    @Override
    public void addAt(int position, T element) {
        mDatas.add(position, element);
        notifyDataSetChanged();
    }

    @Override
    public void addAll(List<T> elements) {
        mDatas.addAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public void addAllAt(int position, List<T> elements) {
        mDatas.addAll(position, elements);
        notifyDataSetChanged();
    }

    @Override
    public void remove(T element) {
        mDatas.remove(element);
        notifyDataSetChanged();
    }

    @Override
    public void removeAt(int index) {
        mDatas.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public void removeAll(List<T> elements) {
        mDatas.removeAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        if (mDatas != null && mDatas.size() > 0) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void replace(T oldElem, T newElem) {
        replaceAt(mDatas.indexOf(oldElem), newElem);
    }

    @Override
    public void replaceAt(int index, T element) {
        mDatas.set(index, element);
        notifyDataSetChanged();
    }

    @Override
    public void replaceAll(List<T> elements) {
        if (mDatas.size() > 0) {
            mDatas.clear();
        }
        mDatas.addAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public T get(int position) {
        if (position >= mDatas.size())
            return null;
        return mDatas.get(position);
    }

    @Override
    public List<T> getAll() {
        return mDatas;
    }

    @Override
    public int getSize() {
        return mDatas.size();
    }

    @Override
    public boolean contains(T element) {
        return mDatas.contains(element);
    }

    @Override
    public void set(List<T> elements) {
        mDatas = elements;
        notifyDataSetChanged();
    }
}
