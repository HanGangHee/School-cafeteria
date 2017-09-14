package com.example.administrator.schoolcafeteria;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-06-04.
 */

public class DetailMenuInfo extends AppCompatActivity {

    Menu menuInfo;
    TextView menuName;
    TextView menuPrice;
    TextView menuKcal ;
    TextView menuNative;
    ImageView image;
    RatingBar menuRatingBar;
    DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_menu_data);
        menuName = (TextView)findViewById(R.id.detailMenuName);
        menuPrice = (TextView)findViewById(R.id.detailMenuPrice);
        menuKcal = (TextView)findViewById(R.id.detailMenuKcal);
        menuNative = (TextView)findViewById(R.id.detailMenuNative);
        image = (ImageView)findViewById(R.id.detailMenuImage);
        menuRatingBar = (RatingBar)findViewById(R.id.menuRatingBar);


        Intent intent = getIntent();
        menuInfo = (Menu)intent.getSerializableExtra("MenuData");
        dbManager = new DBManager(DetailMenuInfo.this, "MENU", null,1);

        menuName.setText(menuInfo.getName());
        menuPrice.setText(""+menuInfo.getPrice());
        menuKcal.setText(""+menuInfo.getKcal());
        menuNative.setText(menuInfo.getNative());
        image.setImageResource(menuInfo.getImage());
        float avg;
        avg = (float)(Math.round(menuInfo.getScore_total()/menuInfo.getCount()- 0.5)) + (float)0.5;
        menuRatingBar.setRating(avg);


        findViewById(R.id.evalMenuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evalDialog();
            }
        });


        findViewById(R.id.deleteMenuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });
        findViewById(R.id.modifyMenuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyDialog();
            }
        });

    }

    public void deleteDialog(){
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.delete_menu, null);

        //여기에 dialog에 들어갈 애들 추가
        //customTitle.setTextColor(Color.BLACK);
        final EditText password = (EditText)view.findViewById(R.id.password);

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailMenuInfo.this);
        builder.setView(view);
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str_password = password.getText().toString();

                // 추가하는 문장
                // 옵션 - && str_number != null && str_loc != null && str_addr != null && str_cartegory != null
                if(str_password.equals("admin")) // 카페이름을 입력하지 않으면, 추가되지 않도록
                {
                    String query = "DELETE FROM MENU WHERE MENU_ID = "+menuInfo.getMenu_num();
                    System.out.println(query);
                    dbManager.delete(query);
                    Intent deleteOut = new Intent();
                    deleteOut.putExtra("update", "ok");
                    DetailMenuInfo.this.setResult(RESULT_OK, deleteOut);
                    finish();
                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void modifyDialog(){
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.modify_menu, null);

        //여기에 dialog에 들어갈 애들 추가
        //customTitle.setTextColor(Color.BLACK);
        final EditText modifyMenuName = (EditText)view.findViewById(R.id.modifyMenuName);
        final EditText modifyMenuPrice = (EditText)view.findViewById(R.id.modifyMenuPrice);
        final EditText modifyMenuNative = (EditText)view.findViewById(R.id.modifyMenuNative);
        final EditText modifyMenuKcal = (EditText)view.findViewById(R.id.modifyMenuKcal);

        modifyMenuName.setText(menuInfo.getName());
        modifyMenuPrice.setText(""+menuInfo.getPrice());
        modifyMenuKcal.setText(""+menuInfo.getKcal());
        modifyMenuNative.setText(menuInfo.getNative());

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DetailMenuInfo.this);
        builder.setView(view);
        builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str_name = modifyMenuName.getText().toString();
                int price = Integer.parseInt(modifyMenuPrice.getText().toString());
                String str_native = modifyMenuNative.getText().toString();
                int kcal = Integer.parseInt(modifyMenuKcal.getText().toString());

                menuInfo.changeData(str_name,price,str_native,kcal);

                // 추가하는 문장!!!
                // 옵션 - && str_number != null && str_loc != null && str_addr != null && str_cartegory != null
                if(str_name != null) // 카페이름을 입력하지 않으면, 추가되지 않도록
                {
                    String[] values = {str_name, Integer.toString(price), str_native, Integer.toString(kcal)};
                    String query = "UPDATE MENU SET" + " MENU_NAME = '" + values[0] + "', PRICE = '" + values[1] + "', MENU_NATIVE = '" + values[2] + "', KCAL = " + values[3]+ " WHERE MENU_ID = " + menuInfo.getMenu_num() + ";";

                    dbManager.update(query);
                    Log.d("mks...", str_name + ""+price);

                    //다시 업로드 하도록 하는 코드 필요!!!!
                    menuName.setText(str_name);
                    menuPrice.setText(Integer.toString(price));
                    menuKcal.setText(Integer.toString(kcal));
                    menuNative.setText(str_native);




                    // main에서 바로 올라가도록!!
                    Intent edit = new Intent();
                    edit.putExtra("update", "ok");
                    DetailMenuInfo.this.setResult(RESULT_OK, edit);
                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void evalDialog(){
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.evaluate_menu, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(DetailMenuInfo.this);
        builder.setView(view);
        final RatingBar person_score= (RatingBar)view.findViewById(R.id.person_score);


        builder.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+person_score.getRating());
                double score_total = menuInfo.getScore_total() + person_score.getRating();
                int person_count = menuInfo.getCount()+1;
                menuInfo.changeData(score_total,person_count);

                String query = "UPDATE MENU SET" + " SCORE_TOTAL = " + score_total + ", PERSON_COUNT = " + person_count+ " WHERE MENU_ID = " + menuInfo.getMenu_num();
                System.out.println(query);
                dbManager.update(query);
                Log.d("mks...", score_total + " "+person_count);

                //다시 업로드 하도록 하는 코드 필요!!!!
                float avg;
                avg = (float)(Math.round(menuInfo.getScore_total()/menuInfo.getCount()- 0.5)) + (float)0.5;
                menuRatingBar.setRating(avg);

                // cafeteria에서 바로 올라가도록!!
                Intent edit = new Intent();
                edit.putExtra("update", "ok");
                DetailMenuInfo.this.setResult(RESULT_OK, edit);

            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
