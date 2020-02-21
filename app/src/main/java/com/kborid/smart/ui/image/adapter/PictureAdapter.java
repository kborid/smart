package com.kborid.smart.ui.image.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kborid.smart.R;
import com.kborid.smart.helper.ImageResourceHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.thunisoft.ui.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class PictureAdapter extends BaseAdapter {

    public final static int TYPE_UNIVERSAL = 1;
    public final static int TYPE_PICASSO = 2;
    public final static int TYPE_GLIDE = 3;

    private List<String> mList = new ArrayList<>();
    private Context context;
    private int mWidth, mHeight;
    private int mTypeLoader;

    public PictureAdapter(Context context, int type) {
        this.context = context;
        this.mTypeLoader = type;
        mWidth = ScreenUtils.mScreenWidth / 3;
        mHeight = (int) ((float) mWidth / 3 * 4);
        mList = ImageResourceHelper.getImages();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new MyViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_image_item, null);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
            viewHolder.iv.getLayoutParams().height = mHeight;
            viewHolder.iv.setLayoutParams(viewHolder.iv.getLayoutParams());
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        final String url = mList.get(position);

        switch (mTypeLoader) {
            case TYPE_UNIVERSAL:
                ImageLoader.getInstance().displayImage(url, viewHolder.iv);
                break;
            case TYPE_PICASSO:
                Picasso.get().load(url).into(viewHolder.iv);
                break;
            case TYPE_GLIDE:
            default:
                Glide.with(context).load(url).into(viewHolder.iv);
                break;
        }
        return convertView;
    }

    class MyViewHolder {
        ImageView iv;
    }
}
