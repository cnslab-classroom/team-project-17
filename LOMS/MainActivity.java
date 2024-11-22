package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로그인 화면으로 이동
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    protected void onActivity(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LOGIN_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                //로그인 성공 시 GoalListActivity로 이동
                Intent intent = new Intent(this, GoalListActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "need login", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
