package com.shopingkaro.shilashop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnn;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnn=findViewById(R.id.turms_next_btn);
        sp= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);


        if (sp.getString("shopingappagree","").length()>0)
        {
            //OPEN NEXT PAGE
            startActivity(new Intent(MainActivity.this,cata2.class));
            finish();
        }
        else
        {
            btnn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spe=sp.edit();
                    spe.putString("shopingappagree","done");
                    spe.apply();
                    //OPEN NEXT PAGE
                    startActivity(new Intent(MainActivity.this,cata2.class));
                    finish();
                }
            });
        }
    }
}
