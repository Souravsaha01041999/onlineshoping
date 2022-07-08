package com.kenakatavnd.kenakatavndin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.util.StringTokenizer;

public class showorderdet extends AppCompatActivity {
    OrderData od;
    TextView orderid,name,address,phone,pin,date,total;
    RecyclerView list;
    RadioGroup status;
    ProductListAdpt prdadpt;
    GridLayoutManager glm;
    String stsend;
    boolean temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showorderdet);
        od=new OrderData(
                getIntent().getStringExtra("orderid"),
                getIntent().getStringExtra("list"),
                getIntent().getStringExtra("address"),
                getIntent().getStringExtra("phone"),
                getIntent().getStringExtra("pincode"),
                getIntent().getStringExtra("date"),
                getIntent().getStringExtra("status"),
                getIntent().getStringExtra("name")
        );

        name=findViewById(R.id.showorder_name);
        orderid=findViewById(R.id.showorder_orderid);
        address=findViewById(R.id.showorder_address);
        phone=findViewById(R.id.showorder_phone);
        pin=findViewById(R.id.showorder_pin);
        date=findViewById(R.id.showorder_date);
        list=findViewById(R.id.showorder_list);
        status=findViewById(R.id.showorder_holder_status);
        total=findViewById(R.id.showorder_total);

        name.setText("Name: "+od.getName());
        orderid.setText("Order ID:"+od.getOrderId());
        address.setText("Address\n"+od.getAddress());
        phone.setText("Phone: "+od.getPhone());
        pin.setText("pin: "+od.getPincode());
        date.setText("Date: "+od.getDate());

        if (!od.getStatus().equals("pending"))
        {
            status.setVisibility(View.GONE);
        }
        glm=new GridLayoutManager(showorderdet.this,1);
        list.setLayoutManager(glm);

        prdadpt=new ProductListAdpt(showorderdet.this);

        StringTokenizer stok=new StringTokenizer(od.getOrderlist(),"`");
        while (stok.hasMoreTokens())
        {
            prdadpt.add(new ProductList(stok.nextToken(),stok.nextToken(),stok.nextToken(),stok.nextToken(),stok.nextToken()));
        }
        list.setAdapter(prdadpt);

        total.setText("Total: "+prdadpt.getTotal());

        status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.showorder_cancel:
                        temp=false;
                        break;
                    case  R.id.showorder_delivered:
                        temp=true;
                        break;
                }
                AlertDialog.Builder b=new AlertDialog.Builder(showorderdet.this);
                b.setMessage("Are you to change status");
                b.setTitle("status");
                b.create();
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setStatus(temp);
                    }
                });
                b.setNegativeButton("No", null);
                b.setCancelable(false);
                b.show();
            }
        });

    }

    void setStatus(boolean sta)
    {
        if (sta)
            stsend="delivered";         //TRUE MEAN DELIVERED FALSE CANCEL
        else stsend="cancel";

        StringRequest sr=new StringRequest(Request.Method.POST, Links.SET_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                msg("Done");
                if (stsend.equals("delivered"))
                    ControlStatus.CTRL='d';
                else
                    ControlStatus.CTRL='c';
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
                prms.put("id",od.getOrderId());
                prms.put("status",stsend);
                return prms;
            }
        };
        RequestQueue q= Volley.newRequestQueue(showorderdet.this);
        q.add(sr);
        q.getCache().clear();

    }
    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
