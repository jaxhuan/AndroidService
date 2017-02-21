package com.skyzone.androidservice.Net;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.skyzone.androidservice.R;

import java.util.List;

/**
 * Created by Skyzone on 2/21/2017.
 */

public class LoadImageAdapter extends BaseAdapter {

    List<String> mStrings;
    Context mContext;

    public LoadImageAdapter(List<String> strings, Context context) {
        mStrings = strings;
        mContext = context;
    }

    @Override
    public int getCount() {
        return null == mStrings ? 0 : mStrings.size();
    }

    @Override
    public Object getItem(int position) {
        return mStrings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.load_image_list_view, null);
            mHolder = new ViewHolder();
            mHolder.image = (ImageView) convertView.findViewById(R.id.load_image_list_view_image);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        AsycNetutil.loadImage(mStrings.get(position), new AsycNetutil.CallBack() {
            @Override
            public void onResponse(String response) {
                // do nothing
            }

            @Override
            public void onImageLoad(Bitmap bitmap) {
                mHolder.image.setImageBitmap(bitmap);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        ImageView image;
    }
}
