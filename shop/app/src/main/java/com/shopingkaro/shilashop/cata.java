package com.shopingkaro.shilashop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class cata extends AppCompatActivity {
    //Button f,g,a,e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cata);
        /*f=findViewById(R.id.FASHION);
        g=findViewById(R.id.GROCERY);
        a=findViewById(R.id.ACCESSORIES);
        e=findViewById(R.id.cataeae);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cata.this,home.class).putExtra("cata",f.getText().toString()));
            }
        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cata.this,home.class).putExtra("cata",g.getText().toString()));
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cata.this,home.class).putExtra("cata",a.getText().toString()));
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cata.this,home.class).putExtra("cata",e.getText().toString()));
            }
        });
        findViewById(R.id.myorders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cata.this,myallorders.class));
            }
        });*/
    }
}
