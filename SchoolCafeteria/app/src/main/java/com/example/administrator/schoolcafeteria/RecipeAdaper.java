package com.example.administrator.schoolcafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-05-30.
 */

public class RecipeAdaper extends BaseAdapter{


    private Context context;
    private ArrayList<Recipe> recipeArrayList;
    private LayoutInflater inflater;

    public RecipeAdaper(Context c, ArrayList<Recipe> recipeArrayList) {
        this.context = c;
        this.recipeArrayList = recipeArrayList;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return recipeArrayList.size();
    }

    public Object getItem(int position) {
        return 1;//recipeArrayList.get(position).getName();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.activity_recipelist, parent, false);
        }
        //각 뷰의 id와 matching하여 view를 setting한다.
//        ImageView image = (ImageView)convertView.findViewById(R.id.);
        //image.setImageResource(menu.get(position).getImage());

        //TextView name = (TextView)convertView.findViewById(R.id);
       // name.setText(menu.get(position).getName());

  //      TextView price= (TextView)convertView.findViewById(R.id.);
     //   price.setText(menu.get(position).getPrice());

        return convertView;
    }
}
