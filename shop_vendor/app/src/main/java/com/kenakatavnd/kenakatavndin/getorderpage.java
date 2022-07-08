package com.kenakatavnd.kenakatavndin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class getorderpage extends AppCompatActivity {
    RecyclerView rv;
    TextView date;
    GridLayoutManager glm;
    GetOrderPageAdpt orderadpt;
    Calendar cal;
    String shopid,pass;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    TextView status_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getorderpage);

        date=findViewById(R.id.getorder_date);
        rv=findViewById(R.id.getorder_list);
        ControlStatus.CTRL='n';

        glm=new GridLayoutManager(getorderpage.this,1);
        rv.setLayoutManager(glm);
        orderadpt=new GetOrderPageAdpt(getorderpage.this, new GetOrderPageAdpt.OrderClickEvent() {
            @Override
            public void onClick(OrderData orderData,TextView status) {
                startActivity(new Intent(getorderpage.this,showorderdet.class)
                .putExtra("address",orderData.getAddress())
                .putExtra("date",orderData.getDate())
                .putExtra("name",orderData.getName())
                .putExtra("orderid",orderData.getOrderId())
                .putExtra("list",orderData.getOrderlist())
                .putExtra("phone",orderData.getPhone())
                .putExtra("pincode",orderData.getPincode())
                .putExtra("status",orderData.getStatus())
                );
                status_change=status;
            }
        });
        cal=Calendar.getInstance();
        sp= PreferenceManager.getDefaultSharedPreferences(getorderpage.this);

        shopid=sp.getString("shop_id","");
        pass=sp.getString("shop_pass","");

        date.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getorderpage.this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.format("%02d",dayOfMonth)+"/"+String.format("%02d",month+1)+"/"+String.valueOf(year));
                        reload();
                    }
                },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        reload();
    }
    void reload()
    {
        orderadpt.clear();
        rv.setAdapter(orderadpt);
        StringRequest sr=new StringRequest(Request.Method.POST, Links.GET_ORDERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response=response.trim();
                //SET IN ADAPTER
                if(!response.equals("]")&&(!response.equals("n"))) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            orderadpt.add(new OrderData(jo.getString("ordidtab"), jo.getString("orderlist"), jo.getString("address"), jo.getString("phone"), jo.getString("pincode"), jo.getString("date"), jo.getString("status"), jo.getString("cast_name")));
                        }
                        rv.setAdapter(orderadpt);
                    } catch (JSONException e) {

                    }
                }
                else if (response.equals("]"))
                {
                    msg("No Order...");
                }
                else if (response.equals("n"))
                {
                    //LOG OUT
                    spe=sp.edit();
                    spe.putString("shop_id","");
                    spe.putString("shop_name","");
                    spe.putString("shop_category","");
                    spe.putString("shop_image","");
                    spe.putString("shop_pass","");
                    spe.apply();
                    ControlApp.LOGIN=false;
                    msg("Log out");
                    finish();
                }
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
                prms.put("sp_id",shopid);
                prms.put("pass",pass);
                prms.put("date",date.getText().toString());
                return prms;
            }
        };
        RequestQueue q= Volley.newRequestQueue(getorderpage.this);
        q.add(sr);
        q.getCache().clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //CHECK STATUS AD SET
        switch (ControlStatus.CTRL)
        {
            case 'd':
                status_change.setText("delivered");
                status_change.setTextColor(Color.GREEN);
                break;
            case 'c':
                status_change.setText("cancel");
                status_change.setTextColor(Color.RED);
                break;
        }
        ControlStatus.CTRL='n';
    }

    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
