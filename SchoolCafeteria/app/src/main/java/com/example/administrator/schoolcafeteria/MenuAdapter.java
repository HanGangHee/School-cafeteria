package com.example.administrator.schoolcafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-05-30.
 */

public class MenuAdapter extends BaseAdapter{   // 메뉴들에 대한 리스트를 띄어줄 때 사용하는 어댑터
    private Context context;
    private ArrayList<Menu> menuArrayList;
    private LayoutInflater inflater;

    public MenuAdapter(Context c, ArrayList<Menu> menuArrayList) {
        this.context = c;
        this.menuArrayList = menuArrayList;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return menuArrayList.size();
    }

    public Object getItem(int position) {
        return menuArrayList.get(position).getName();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.activity_menulist, parent, false);
        }
        //각 뷰의 id와 matching하여 view를 setting한다.
        ImageView menu_image = (ImageView)convertView.findViewById(R.id.menu_im);
        menu_image.setImageResource(menuArrayList.get(position).getImage());

        TextView menu_name = (TextView)convertView.findViewById(R.id.menu_name);
        menu_name.setText(menuArrayList.get(position).getName());

        TextView price= (TextView)convertView.findViewById(R.id.menu_price);
        price.setText(""+menuArrayList.get(position).getPrice());

        float avg = (float)(Math.round(menuArrayList.get(position).getScore_total()/menuArrayList.get(position).getCount()- 0.5)) + (float)0.5;
        RatingBar rating = (RatingBar)convertView.findViewById(R.id.menu_score);
        rating.setRating(avg);

        return convertView;
    }
}
