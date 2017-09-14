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
 * Created by Administrator on 2017-06-05.
 */


public class NewMenuAdapter extends BaseAdapter {   // 메뉴들에 대한 리스트를 띄어줄 때 사용하는 어댑터
    private Context context;
    private ArrayList<NewMenu> NewMenuList;
    private LayoutInflater inflater;

    public NewMenuAdapter(Context c, ArrayList<NewMenu> NewMenuList) {
        this.context = c;
        this.NewMenuList = NewMenuList;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return NewMenuList.size();
    }

    public Object getItem(int position) {
        return NewMenuList.get(position).getName();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.activity_newmenulist, parent, false);
        }
        //각 뷰의 id와 matching하여 view를 setting한다.
        ImageView menu_image = (ImageView)convertView.findViewById(R.id.newMenuIm);
        menu_image.setImageResource(NewMenuList.get(position).getImage());

        TextView menu_name = (TextView)convertView.findViewById(R.id.newMenuName);
        menu_name.setText(NewMenuList.get(position).getName());

        TextView price= (TextView)convertView.findViewById(R.id.newMenuPrice);
        price.setText(""+NewMenuList.get(position).getPrice());


        TextView vote= (TextView)convertView.findViewById(R.id.newMenuVote);
        vote.setText(""+NewMenuList.get(position).getVote());

        return convertView;
    }
}
