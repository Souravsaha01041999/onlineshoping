package com.shopingkaro.shilashop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class productdetpage extends AppCompatActivity {
    String productid;
    TextView showname,showdet,showprice,showoffer,showavl,showqun;
    ImageView img;
    ProgressDialog pd;
    Button incre,decre,add,remove;
    int count=0,prvqun=0;
    String image="";
    boolean ctrl=true;
    AlertDialog.Builder b;
    String price;
    int availqun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetpage);
        productid=getIntent().getStringExtra("productid");
        availqun=Integer.parseInt(getIntent().getStringExtra("productqun"));


        img=findViewById(R.id.prdet_image);
        showname=findViewById(R.id.prdet_name);
        showdet=findViewById(R.id.prdet_det);
        showprice=findViewById(R.id.prdet_price);
        showoffer=findViewById(R.id.prdet_offer);
        showavl=findViewById(R.id.prdet_avl);
        incre=findViewById(R.id.prdet_add_item);
        decre=findViewById(R.id.prdet_remove_item);
        showqun=findViewById(R.id.prdet_show_item);
        add=findViewById(R.id.prdet_add);
        remove=findViewById(R.id.prdet_remove);

        reload();
        if (!ControlSystem.QUN.equals("0"))
        {
            ctrl=false;
            count=Integer.parseInt(ControlSystem.QUN);
            prvqun=count;
            showqun.setText(ControlSystem.QUN);
        }

        incre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHECK AVAILABEL OR NOT
                if ((count<3)&&(count<availqun)) {
                    if (!showavl.getText().toString().equals("out of stock")) {
                        count++;
                        showqun.setText(String.valueOf(count));
                    } else {
                        msg("out of stock");
                    }
                }
            }
        });
        decre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count>1) {
                    count--;
                    showqun.setText(String.valueOf(count));
                }
                else {
                    msg("For cancel click on remove");
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!showqun.getText().toString().equals("0")) {
                    if (ctrl) {
                        ControlSystem.QUN = String.valueOf(count);
                        ControlSystem.PID = productid;
                        ControlSystem.PRICE = price;
                        ControlSystem.IMAGE=image;
                        ControlSystem.NAME = showname.getText().toString();
                        ControlSystem.CMD = "a";
                        finish();
                    } else {
                        back();
                        finish();
                    }
                }
                else {
                    ControlSystem.QUN = String.valueOf("1");
                    ControlSystem.PID = productid;
                    ControlSystem.PRICE = price;
                    ControlSystem.IMAGE=image;
                    ControlSystem.NAME = showname.getText().toString();
                    ControlSystem.CMD = "a";
                    finish();
                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!showqun.getText().toString().equals("0")) {
                    count = 0;
                    ControlSystem.QUN = "0";
                    ControlSystem.PID = productid;
                    ControlSystem.CMD = "r";
                }
                finish();
            }
        });

    }
    void reload()
    {
        pd= ProgressDialog.show(productdetpage.this,"Wait","Load All Details...");
        StringRequest sr=new StringRequest(Request.Method.POST, Links.GET_PRODUCT_DET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                response=response.trim();
                if(!response.equals("]"))
                {
                    try {
                        JSONArray ja = new JSONArray(response);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            showname.setText(jo.getString("prd_name"));
                            if ((jo.getString("avl").equals("0"))||(jo.getString("qun").equals("0"))) {
                                showavl.setText("out of stock");
                            }
                            if (!jo.getString("offer").equals("n")) {
                                showoffer.setText(jo.getString("offer"));
                            }
                            showdet.setText(jo.getString("detail"));
                            price=jo.getString("price");
                            showprice.setText("Rs: "+price);
                            image=jo.getString("image");
                            Picasso.get().load(Uri.parse(jo.getString("image")))
                                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .placeholder(R.drawable.loadingmove)
                                    .error(R.drawable.ic_error_outline_black_24dp)
                                    .into(img);
                        }
                    }
                    catch(JSONException e)
                            {
                                msg("dead");
                            }
                }
                else {
                    msg("Error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                msg("Try again..!");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prms=new Hashtable<>();
                prms.put("pid",productid);
                return prms;
            }
        };
        RequestQueue q= Volley.newRequestQueue(productdetpage.this);
        q.add(sr);
        q.getCache().clear();
    }
    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    void back()
    {
        if(count!=prvqun)
        {
            if (count!=0) {
                ControlSystem.QUN = String.valueOf(count);
                ControlSystem.PID = productid;
                ControlSystem.CMD = "u";
            }
        }
        else {
            ControlSystem.CMD="n";
        }
    }

    @Override
    public void onBackPressed() {
        if ((prvqun == 0) && (count != 0)) {
            //ASK FOR ADD
            b=new AlertDialog.Builder(productdetpage.this);
            b.setMessage("Add This Item?");
            b.setTitle("confirm");
            b.create();
            b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ControlSystem.QUN = String.valueOf(count);
                    ControlSystem.PID = productid;
                    ControlSystem.PRICE=price;
                    ControlSystem.IMAGE=image;
                    ControlSystem.NAME=showname.getText().toString();
                    ControlSystem.CMD = "a";
                    finish();
                }
            });
            b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ControlSystem.CMD="n";
                        finish();
                    }
                });
                b.setCancelable(false);
                b.show();
        }
        else {
            back();
            finish();
        }
    }
}
