package com.shopingkaro.shilashop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

public class orderpage extends AppCompatActivity {
    RecyclerView rv;
    TextView total;
    Button submit;
    EditText name,phone,pin,address;
    GridLayoutManager glm;
    String lists;
    FinalOrderAdpt orderlst;
    String shopename,shopeid;
    float totalvalue;
    String orderid;
    String date;
    ProgressDialog pd;

    SharedPreferences sp;
    SharedPreferences.Editor spe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderpage);

        sp= PreferenceManager.getDefaultSharedPreferences(orderpage.this);


        name=findViewById(R.id.order_name);
        address=findViewById(R.id.order_address);
        pin=findViewById(R.id.order_pin);
        phone=findViewById(R.id.order_mob);

        //cast_name, cast_address, cast_phone, cast_pin

        name.setText(sp.getString("cast_name",""));
        address.setText(sp.getString("cast_address",""));
        pin.setText(sp.getString("cast_pin",""));
        phone.setText(sp.getString("cast_phone",""));

        submit=findViewById(R.id.order_seubmit);
        total=findViewById(R.id.order_total);
        rv=findViewById(R.id.order_list);
        glm=new GridLayoutManager(orderpage.this,1);
        rv.setLayoutManager(glm);
        orderlst=new FinalOrderAdpt(orderpage.this,false);
        lists=getIntent().getStringExtra("orders_list");
        shopename=getIntent().getStringExtra("orders_shopename");
        shopeid=getIntent().getStringExtra("order_shopid");

        StringTokenizer st=new StringTokenizer(lists,"`");
        while (st.hasMoreTokens())
        {
            orderlst.add(new FinalOrderList(st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken()));
        }
        rv.setAdapter(orderlst);

        totalvalue=orderlst.getTotal();
        total.setText("Total: "+String.format("%.2f",totalvalue));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd=ProgressDialog.show(orderpage.this,"Please Wait","Sending...");
                if ((name.getText().toString().length()>0)&&(address.getText().toString().length()>0)&&(phone.getText().toString().length()>0)&&(pin.getText().toString().length()>0)) {
                    orderid = new SimpleDateFormat("ddMMyyyy").format(new Date()) + new SimpleDateFormat("HHmmss").format(new Date()) + String.valueOf((int) Math.random() * 50);
                    date=new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    setdata();
                    StringRequest sr=new StringRequest(Request.Method.POST, Links.GET_ORDER_PLACED, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            response=response.trim();
                            if (response.equals("1"))
                            {
                                AlertDialog.Builder b=new AlertDialog.Builder(orderpage.this);
                                b.setMessage("OrderId:-"+orderid+"\nWe will call you...");
                                b.setTitle("Success");
                                b.create();
                                b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        OrderSet.CTRL='y';
                                        SqlCmd.CMD="";
                                        finish();
                                    }
                                });
                                b.setCancelable(false);
                                b.show();
                            }
                            else {
                                msg("SORRY TRY AGAIN");
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
                            prms.put("ordid",orderid);
                            prms.put("shopid",shopeid);
                            prms.put("list",lists);
                            prms.put("cstname",name.getText().toString());
                            prms.put("addr",address.getText().toString());
                            prms.put("phone",phone.getText().toString());
                            prms.put("pin",pin.getText().toString());
                            prms.put("date",date);
                            prms.put("cmd",SqlCmd.CMD);
                            return prms;
                        }
                    };
                    RequestQueue q= Volley.newRequestQueue(orderpage.this);
                    q.add(sr);
                    q.getCache().clear();
                }
                else {
                    msg("Give all details !");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder b=new AlertDialog.Builder(orderpage.this);
        b.setMessage("Are you sure to back with out placed order");
        b.setTitle("order has not placed");
        b.create();
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OrderSet.CTRL='n';
                finish();
            }
        });
        b.setNegativeButton("No", null);
        b.setCancelable(false);
        b.show();
    }

    void setdata()
    {
        spe=sp.edit();
        spe.putString("cast_name",name.getText().toString());
        spe.putString("cast_address",address.getText().toString());
        spe.putString("cast_pin",pin.getText().toString());
        spe.putString("cast_phone",phone.getText().toString());
        spe.apply();
    }
    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
