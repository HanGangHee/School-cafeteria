package com.example.administrator.schoolcafeteria;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-06-05.
 */


public class DetailNewMenuInfo extends AppCompatActivity {

    NewMenu menuInfo;
    TextView newMenuName;
    TextView newMenuPrice;
    TextView newMenuKcal ;
    TextView newMenuNative;
    ImageView newMenuimage;
    TextView newMenuVote;
    DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_new_menu_data);
        newMenuName = (TextView)findViewById(R.id.detailNewMenuName);
        newMenuPrice = (TextView)findViewById(R.id.detailNewMenuPrice);
        newMenuKcal = (TextView)findViewById(R.id.detailNewMenuKcal);
        newMenuNative = (TextView)findViewById(R.id.detailNewMenuNative);
        newMenuimage = (ImageView)findViewById(R.id.detailNewMenuImage);
        newMenuVote = (TextView) findViewById(R.id.detailNewMenuVote);


        Intent intent = getIntent();
        menuInfo = (NewMenu)intent.getSerializableExtra("NewMenuData");
        dbManager = new DBManager(DetailNewMenuInfo.this, "NEWMENU", null,1);

        newMenuName.setText(menuInfo.getName());
        newMenuPrice.setText(""+menuInfo.getPrice());
        newMenuKcal.setText(""+menuInfo.getKcal());
        newMenuNative.setText(menuInfo.getNative());
        newMenuimage.setImageResource(menuInfo.getImage());
        newMenuVote.setText(""+menuInfo.getVote());
        findViewById(R.id.voteMenuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voteDialog();
            }
        });


        findViewById(R.id.deleteNewMenuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });
        findViewById(R.id.modifyNewMenuBtn).setOnClickListener(new View.OnClickListener() {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailNewMenuInfo.this);
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
                    DetailNewMenuInfo.this.setResult(RESULT_OK, deleteOut);
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

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DetailNewMenuInfo.this);
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
                    String query = "UPDATE NEWMENU SET" + " NEWMENU_NAME = '" + values[0] + "', PRICE = '" + values[1] + "', NEWMENU_NATIVE = '" + values[2] + "', KCAL = " + values[3]+ " WHERE NEWMENU_ID = " + menuInfo.getMenu_num() + ";";

                    dbManager.update(query);
                    Log.d("mks...", str_name + ""+price);

                    //다시 업로드 하도록 하는 코드 필요!!!!
                    newMenuName.setText(str_name);
                    newMenuPrice.setText(Integer.toString(price));
                    newMenuKcal.setText(Integer.toString(kcal));
                    newMenuNative.setText(str_native);




                    // main에서 바로 올라가도록!!
                    Intent edit = new Intent();
                    edit.putExtra("update", "ok");
                    DetailNewMenuInfo.this.setResult(RESULT_OK, edit);
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

    public void voteDialog(){
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.vote_activity, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(DetailNewMenuInfo.this);
        builder.setView(view);

        builder.setPositiveButton("투표", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int Vote = menuInfo.getVote();
                menuInfo.setVote(Vote+1);

                String query = "UPDATE NEWMENU SET" + " VOTE_TOTAL = " +menuInfo.getVote()+ " WHERE NEWMENU_ID = " + menuInfo.getMenu_num() + ";";
                System.out.println(query);
                dbManager.update(query);
                //다시 업로드 하도록 하는 코드 필요!!!!
                newMenuVote.setText(""+menuInfo.getVote());
                // cafeteria에서 바로 올라가도록!!
                Intent edit = new Intent();
                edit.putExtra("update", "ok");
                DetailNewMenuInfo.this.setResult(RESULT_OK, edit);

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
