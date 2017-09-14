package com.example.administrator.schoolcafeteria;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-05-30.
 */

public class Cafeteria extends AppCompatActivity {


    DBManager dbManager;
    ArrayList<Menu>menuData;
    String ctName;
    MenuAdapter menuAdapter;
    ListView menuList;
    TextView ct_name;
    Button  newMenuBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeteria);

        dbManager = new DBManager(Cafeteria.this, "MENU", null,1);
        menuList = (ListView)findViewById(R.id.menuList);
        Intent intent = getIntent();
        ctName = (String)intent.getStringExtra("ct_name");
        ct_name = (TextView)findViewById(R.id.ct_name);
        newMenuBtn = (Button)findViewById(R.id.newMenuBtn);
        ct_name.setText(ctName);
        setCafeteriaActivity(ctName);

        newMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Cafeteria.this, CafeteriaNewMenu.class);
                it.putExtra("cafeteria",ctName);
                startActivity(it);
            }
        });
        //각각의 카페테리아에 맞게 메뉴 리스트를 보여줘야 한다.
        //

    }
    public void setCafeteriaActivity(String ct){
        switch(ct){
            case "STUDENT":
                setData(ctName);
                listMake();
                break;
            case "DORMITORY":
                setData(ctName);
                listMake();
                break;
            case "GENERAL":
                setData(ctName);
                listMake();
                break;
            case "FACULTY":
                setData(ctName);
                listMake();
                break;
        }
    }
    private void setData(String ct_name){     //메뉴 정보를 쿼리문을 통해 받아 menu ata에 저장장
        String get = dbManager.PrintMenuData(ct_name);

        menuData=new ArrayList<Menu>();

        try{
            JSONArray jarray = new JSONArray(get);     //메뉴에 대한 정보를 select문을 써서 json형식으로 받아온다.

            for(int i=0; i < jarray.length(); i++)
            {
                JSONObject jObject = jarray.getJSONObject(i);
                int menu_num = jObject.getInt("number");
                String name = jObject.getString("name");
                int price = jObject.getInt("price");
                String ctName=jObject.getString("ctName");
                String Native=jObject.getString("Native");
                int Kcal = jObject.getInt("Kcal");
                Double total_score = jObject.getDouble("total_score");
                int count=jObject.getInt("count");
                String menuImage = jObject.getString("menuImage");

                int image;
                String Package = getPackageName();
                if(!menuImage.equals("")){
                    image =  getResources().getIdentifier(menuImage,"drawable",Package);
                }
                else
                {
                    image =  getResources().getIdentifier("dump","drawable",Package);
                }
                System.out.println("image: "+image+" getResources "+R.drawable.dump);
                menuData.add(new Menu(menu_num,name,price,ct_name,Native,Kcal,total_score,count,image));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void listMake()  //메인엑티비티에 카페들의 정보를 나타냄
    {
        menuAdapter=new MenuAdapter(this,menuData);
        menuList.setAdapter(menuAdapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int menuNum = menuData.get(position).getMenu_num();
                Intent intent = new Intent(Cafeteria.this,DetailMenuInfo.class);
                intent.putExtra("MenuData",menuData.get(position));
                startActivityForResult(intent,10);
            }
        });
    }

    @Override   // delete를 했을 때 바로 적용할 수 있게 한다.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 10){

            if(resultCode == RESULT_OK && data!= null){

                String result = data.getStringExtra("update");

                if(result.equals("ok"))
                {
                    System.out.println("onActivityResult!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    setData(ctName);
                    listMake();
                }
            }
        }

    }


}
