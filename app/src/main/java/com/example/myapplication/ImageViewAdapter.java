package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.BaseAdapter;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
class ImageViewAdapter extends BaseAdapter {
        ArrayList<Bitmap> list;
    Context context;
        public ImageViewAdapter(Context context, ArrayList<Bitmap > list){
            this.list=list;
            this.context=context;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO 自动生成的方法存根
             convertView=View.inflate(context, R.layout.custom_list, null);
            ImageView face = (ImageView)convertView.findViewById(R.id.face);
            face.setImageBitmap(list.get(position));
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            // TODO 自动生成的方法存根
            return position;
        }

        @Override
        public Object getItem(int position) {
            // TODO 自动生成的方法存根
            return list.get(position);
        }

        @Override
        public int getCount() {
            // TODO 自动生成的方法存根
            return list.size();
        }


    }