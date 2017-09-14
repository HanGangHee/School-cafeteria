package com.example.administrator.schoolcafeteria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class University extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);
    }

    public void onClick(View view) { // intent로 어떤 것이 눌렸는지 알려준다.
        Intent intent = new Intent(University.this, Cafeteria.class);
        switch (view.getId()){
            case R.id.dormitory_ct:
                intent.putExtra("ct_name","DORMITORY");
                startActivity(intent);
                break;
            case R.id.student_ct:
                intent.putExtra("ct_name","STUDENT");
                startActivity(intent);
                break;
            case R.id.faculty_ct:
                intent.putExtra("ct_name","FACULTY");
                startActivity(intent);
                break;
            case R.id.general_ct:
                intent.putExtra("ct_name","GENERAL");
                startActivity(intent);
                break;
        }
    }
}
