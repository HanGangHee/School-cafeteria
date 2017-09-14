package com.example.administrator.schoolcafeteria;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-06-05.
 */

public class CafeteriaNewMenu extends AppCompatActivity {


    DBManager dbManager;
    ArrayList<NewMenu> newMenuData;
    String ctName;
    NewMenuAdapter newMenuAdapter;
    ListView newMenuList;
    TextView ct_name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeterianewmenu);

        dbManager = new DBManager(CafeteriaNewMenu.this, "NEWMENU", null,1);
        newMenuList = (ListView)findViewById(R.id.newMenuList);
        Intent intent = getIntent();
        ctName = (String)intent.getStringExtra("cafeteria");
        ct_name = (TextView)findViewById(R.id.ct_newname);
        ct_name.setText(ctName);

        setData(ctName);
        listMake();
        //각각의 카페테리아에 맞게 메뉴 리스트를 보여줘야 한다.
        //

    }

    private void setData(String ct_name){     //메뉴 정보를 쿼리문을 통해 받아 menu ata에 저장장
        String get = dbManager.PrintNewMenuData(ct_name);

        newMenuData=new ArrayList<NewMenu>();

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
                int Vote=jObject.getInt("vote");
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
                newMenuData.add(new NewMenu(menu_num,name,price,ct_name,Native,Kcal,Vote,image));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void listMake()  //메인엑티비티에 카페들의 정보를 나타냄
    {
        newMenuAdapter=new NewMenuAdapter(this,newMenuData);
        newMenuList.setAdapter(newMenuAdapter);

        newMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int menuNum = newMenuData.get(position).getMenu_num();
                Intent intent = new Intent(CafeteriaNewMenu.this,DetailNewMenuInfo.class);
                intent.putExtra("NewMenuData",newMenuData.get(position));
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
