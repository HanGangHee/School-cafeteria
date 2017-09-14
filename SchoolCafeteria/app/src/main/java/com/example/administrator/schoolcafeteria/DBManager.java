package com.example.administrator.schoolcafeteria;

/**
 * Created by Administrator on 2017-05-30.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016-11-25.
 */
public class DBManager extends SQLiteOpenHelper {

    private final String[][] menuData = {
            //이름        가격     식당이름   원산지 칼로리 total_score  person_count  image
            {"돼지국밥","3400","STUDENT","국내산","700","250","70","m1"},
            {"일식돈까스","3900","STUDENT","호주","680","100","30","m2"},
            {"김치새우볶음밥","3400","STUDENT","국내산","720","250","100","m3"},
            {"콘치즈만두그라탕","4000","STUDENT","국내산","800","350","100","m4"},
            {"잔치국수","3600","STUDENT","국내산","730","450","100","m5"},
            {"햄치즈파니니","3800","STUDENT","국내산","760","200","100","m6"},
            {"즉석라면","1700","STUDENT","국내산","800","350","100","m7"},
            {"치즈돈가스","4000","STUDENT","브라질","820","500","100","m8"},
            {"참치마요","3000","STUDENT","국내산","840","350","100","m9"},
            {"육개장","3400","STUDENT","국내산","790","450","100","m10"},
            {"삼선볶음밥","3000","STUDENT","국내산","810","550","120","m11"}
    };
    private final String[][] newMenuData = {
            {"깻잎제육덮밥","3500","STUDENT","국내산","700","250","n1"},
            {"돈육김치찌개","3900","STUDENT","호주","680","100","n2"},
            {"함밥스테이크","4000","STUDENT","국내산","810","130","n3"},
            {"닭갈비볶음밥","5000","STUDENT","국내산","720","143","n4"},
            {"날치알밥","5000","STUDENT","국내산","780","30","n5"},
            {"참치김치찌개","3400","STUDENT","국내산","820","132","n6"},
            {"철판순대야채볶음","3600","STUDENT","국내산","810","90","n7"},
            {"돈코츠라멘","4000","STUDENT","국내산","840","51","n8"},
            {"부대찌개","3800","STUDENT","브라질","760","73","n9"},
            {"만두탕수","3800","STUDENT","국내산","720","65","n10"},
            {"제육야채볶음밥","3500","STUDENT","국내산","790","83","n11"}
    };



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS MENU" +
                "(MENU_ID integer primary key autoincrement," +
                "MENU_NAME text not null," +
                " PRICE integer, " +
                "CT_NAME text not null," +
                " MENU_NATIVE text not null," +
                "KCAL integer," +
                "SCORE_TOTAL real,"+
                "PERSON_COUNT integer,"+
                " IMAGE text);");

        db.execSQL("CREATE TABLE IF NOT EXISTS NEWMENU" +
                "(NEWMENU_ID integer primary key autoincrement," +
                "NEWMENU_NAME text not null," +
                " PRICE integer, " +
                "CT_NAME text not null," +
                " NEWMENU_NATIVE text not null," +
                "KCAL integer," +
                "VOTE_TOTAL integer,"+
                " IMAGE text);");

        for(String[] s: menuData){
            String query = "MENU (MENU_NAME,PRICE,CT_NAME,MENU_NATIVE,KCAL,SCORE_TOTAL,PERSON_COUNT,IMAGE) " + convertString(s);
            insert(query, db);
        }
        for(String[] s: newMenuData){
            String query = "NEWMENU (NEWMENU_NAME,PRICE,CT_NAME,NEWMENU_NATIVE,KCAL,VOTE_TOTAL,IMAGE) " + convertString(s);
            insert(query, db);
        }

    }

    public DBManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public String convertString(String[] input){  //insert 하기위해 문자열을  sql 형식에 맞게 변환
        String output = "values (";

        for(String s: input){
            try{
                Double.parseDouble(s);
                output += s;
            }
            catch (Exception e) {
                output += "'" + s + "'";
            }
            if(!s.equals(input[input.length - 1]))
                output += ",";
        }
        output += ");";

        return output;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String _query, SQLiteDatabase db) {
        //insert into 테이블명 values(속성, 속성)
        System.out.println(_query);
        db.execSQL("insert into " + _query);
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }


    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public String PrintMenuData(String ct) {
        SQLiteDatabase db = getReadableDatabase();
        String str = "[";
        //select * from 테이블명;
        //select 속성,속성...from 테이블명;
        Cursor cursor = db.rawQuery("select * from MENU "+ "where CT_NAME="+"'"+ ct +"'", null);
            while (cursor.moveToNext()) {
                // 파일전송 포맷 json            //이름        가격     식당이름   원산지 칼로리 total_score  person_count  image
                str += "{"
                        + "'number':'"
                        + cursor.getInt(0)         //메뉴번호
                        + "','name':'"
                        + cursor.getString(1)       //메뉴이름
                        + "','price':'"
                        + cursor.getInt(2)       //가격
                        + "','ctName':'"
                        + cursor.getString(3)         //식당이름
                        + "','Native':'"
                        + cursor.getString(4)           //원산지
                        + "','Kcal':'"
                        + cursor.getInt(5)         //칼로리
                        + "','total_score':'"
                        + cursor.getDouble(6)         //total_score
                        + "','count':'"
                        + cursor.getInt(7)         //person_count
                        + "','menuImage':'"
                        + cursor.getString(8)         //menu image
                        + "'}";
                if (cursor.isLast())
                    ;
                else
                    str += " ,";
            }
        str += "]";
        System.out.println(str);
        return str;
    }
    public String PrintNewMenuData(String ct) {
        SQLiteDatabase db = getReadableDatabase();
        String str = "[";
        //select * from 테이블명;
        //select 속성,속성...from 테이블명;
        Cursor cursor = db.rawQuery("select * from NEWMENU "+ "where CT_NAME="+"'"+ ct +"'", null);
        while (cursor.moveToNext()) {
            // 파일전송 포맷 json            //이름        가격     식당이름   원산지 칼로리 total_score  person_count  image
            str += "{"
                    + "'number':'"
                    + cursor.getInt(0)         //메뉴번호
                    + "','name':'"
                    + cursor.getString(1)       //메뉴이름
                    + "','price':'"
                    + cursor.getInt(2)       //가격
                    + "','ctName':'"
                    + cursor.getString(3)         //식당이름
                    + "','Native':'"
                    + cursor.getString(4)           //원산지
                    + "','Kcal':'"
                    + cursor.getInt(5)         //칼로리
                    + "','vote':'"
                    + cursor.getInt(6)         //person_count
                    + "','menuImage':'"
                    + cursor.getString(7)         //menu image
                    + "'}";
            if (cursor.isLast())
                ;
            else
                str += " ,";
        }
        str += "]";
        System.out.println(str);
        return str;
    }

}
