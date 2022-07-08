package com.shopingkaro.shilashop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import java.util.StringTokenizer;

public class cartpage extends AppCompatActivity {
    RecyclerView rv;
    GridLayoutManager glm;
    CartAdapt cadpt;
    String lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartpage);
        rv=findViewById(R.id.cartpage_list);
        glm=new GridLayoutManager(cartpage.this,1);
        rv.setLayoutManager(glm);
        lists=CartControl.LISTS;
        CartControl.LISTS="";
        cadpt=new CartAdapt(cartpage.this);
        StringTokenizer st=new StringTokenizer(lists,"`");  //SEPARATE BY `
        while (st.hasMoreTokens())
        {
            cadpt.add(new CartData(st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken()));
            //prdid,prdname,prdprice,prdqun IS THE SYNTAX OF LISTS IN CartControl
        }
        rv.setAdapter(cadpt);
    }

    @Override
    public void onBackPressed() {
        //SEND PRODUCTID, PRODUCTQUN SEPARATE BY `
        if (CartControl.UPDATE == 'y') {
            for (CartData cd : cadpt.datas) {
                CartControl.LISTS=CartControl.LISTS+cd.getPrdid()+"`"+cd.getPrdqun()+"`";
            }
        }
        finish();
    }
}
