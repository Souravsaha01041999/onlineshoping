package com.kenakatavnd.kenakatavndin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;

public class changeprd extends AppCompatActivity {
    Switch avl;
    Button submit,delete_item;
    EditText price,qun,offer;
    String prd_id,prd_qun,prd_offer,prd_price,prd_avl;
    String temp2="";
    AlertDialog.Builder bld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeprd);
        avl=findViewById(R.id.chg_avl_swt_set);
        price=findViewById(R.id.chg_price);
        qun=findViewById(R.id.chg_qun);
        offer=findViewById(R.id.chg_offer);
        submit=findViewById(R.id.chg_submit);
        delete_item=findViewById(R.id.chg_delete);

        prd_id=getIntent().getStringExtra("id");
        prd_qun=getIntent().getStringExtra("qun");
        prd_offer=getIntent().getStringExtra("offer");
        prd_price=getIntent().getStringExtra("price");
        prd_avl=getIntent().getStringExtra("avl");
        if (prd_avl.equals("1")&&(Integer.parseInt(prd_qun)>0)) {
            avl.setChecked(true);
        }
        else {
            avl.setChecked(false);
        }

        price.setText(prd_price);
        qun.setText(prd_qun);
        if (!prd_offer.equals("n"))
        offer.setText(prd_offer);

        avl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                msg("Please Wait...");
                set_avl(isChecked);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((price.getText().toString().length()>0)&&(qun.getText().toString().length()>0))
                {
                    msg("Please Wait..");
                    if (!price.getText().toString().contains("."))
                    {
                        price.setText(price.getText().toString()+".00");
                    }

                    if (offer.getText().toString().length()>0)
                    {
                        temp2=offer.getText().toString();
                    }
                    else {
                        temp2="n";
                    }

                    StringRequest sr=new StringRequest(Request.Method.POST, Links.SET_OTHERS, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            msg("done");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            msg("Try again..!");
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> prms=new Hashtable<>();
                            prms.put("id",prd_id);
                            prms.put("price",price.getText().toString());
                            prms.put("qun",qun.getText().toString());
                            prms.put("offer", temp2);
                            return prms;
                        }
                    };
                    RequestQueue q= Volley.newRequestQueue(changeprd.this);
                    q.add(sr);
                    q.getCache().clear();

                }
                else
                {
                    msg("Please Enter All Details");
                }
            }
        });

        delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bld=new AlertDialog.Builder(changeprd.this);
                bld.setMessage("Are you sure to delete this product?");
                bld.setTitle("Deleting");
                bld.create();
                bld.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        msg("Please Wait...");
                        StringRequest sr=new StringRequest(Request.Method.POST, Links.DELETE_PRODUCT, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                msg("done");
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                msg("Try again..!");
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> prms=new Hashtable<>();
                                prms.put("id",prd_id);
                                return prms;
                            }
                        };
                        RequestQueue q= Volley.newRequestQueue(changeprd.this);
                        q.add(sr);
                        q.getCache().clear();
                    }
                });
                bld.setNegativeButton("No", null);
                bld.show();
            }
        });

    }
    void set_avl(boolean avl)
    {
        final char temp;
        if (avl)
            temp='1';
        else temp='0';

        StringRequest sr=new StringRequest(Request.Method.POST, Links.SET_AVL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                msg("done");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                msg("Try again..!");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prms=new Hashtable<>();
                prms.put("id",prd_id);
                prms.put("avl",String.valueOf(temp));
                return prms;
            }
        };
        RequestQueue q= Volley.newRequestQueue(changeprd.this);
        q.add(sr);
        q.getCache().clear();
    }
    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
