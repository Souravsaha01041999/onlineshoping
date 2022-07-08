package com.shopingkaro.shilashop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class myallorders extends AppCompatActivity {
    RecyclerView rv;
    TextView phone,adptstatus;
    SharedPreferences sp;
    GridLayoutManager glm;
    ProgressDialog pd;
    MyOrderListAdpt myorderadpt;
    int poss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myallorders);

        rv=findViewById(R.id.myorder_order_list);
        phone=findViewById(R.id.myorders_phone);

        glm=new GridLayoutManager(myallorders.this,1);
        rv.setLayoutManager(glm);
        sp= PreferenceManager.getDefaultSharedPreferences(myallorders.this);

        phone.setText(sp.getString("cast_phone","No Phone Number"));

        myorderadpt=new MyOrderListAdpt(myallorders.this, new MyOrderListAdpt.MyOrderClickEvent() {
            @Override
            public void onClick(String orderids, String list, String status,String name,int pos,TextView adstatus) {
                startActivity(new Intent(myallorders.this,cancelpage.class).putExtra("orderid",orderids).putExtra("list",list).putExtra("status",status).putExtra("name",name));
                adptstatus=adstatus;
                poss=pos;
            }
        });
        reload();
    }
    void reload()
    {
        if (!phone.getText().toString().equals("No Phone Number"))
        {
            pd=ProgressDialog.show(myallorders.this,"Please Wait","Loading...");
            StringRequest sr=new StringRequest(Request.Method.POST, Links.GET_MY_ORDERS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pd.dismiss();
                    response=response.trim();
                    if (!response.equals("]"))
                    {
                        if(!response.equals("]"))
                        {
                            try {
                                JSONArray ja = new JSONArray(response);
                                for(int i=0;i<ja.length();i++)
                                {
                                    JSONObject jo=ja.getJSONObject(i);
                                    myorderadpt.add(new MyOrderData(jo.getString("ordidtab"),jo.getString("shop_name"),jo.getString("status"),jo.getString("orderlist"),jo.getString("date")));
                                }
                                rv.setAdapter(myorderadpt);
                            }
                            catch (JSONException e)
                            {

                            }
                        }
                        else {
                            msg("No Order Found");
                        }
                    }
                    else {
                        msg("No order on this number");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.dismiss();
                    msg("Sorry Try Again!");
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> prms=new Hashtable<>();
                    prms.put("phno",phone.getText().toString());
                    return prms;
                }
            };
            RequestQueue q= Volley.newRequestQueue(myallorders.this);
            q.add(sr);
            q.getCache().clear();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ControlCancel.CTRL=='y')
        {
            ControlCancel.CTRL='n';
            myorderadpt.datas.get(poss).updateStatus("cancel");
            adptstatus.setText("cancel");
        }
    }

    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
