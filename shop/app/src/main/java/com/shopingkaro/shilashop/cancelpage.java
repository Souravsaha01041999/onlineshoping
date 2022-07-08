package com.shopingkaro.shilashop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class cancelpage extends AppCompatActivity {
    String order_id,shop_name,list,status;
    Button submit;
    TextView orderid,shopname,total;
    RecyclerView rv;
    GridLayoutManager glm;
    FinalOrderAdpt fnl;
    float totalval=0;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelpage);
        order_id=getIntent().getStringExtra("orderid");
        shop_name=getIntent().getStringExtra("name");
        list=getIntent().getStringExtra("list");
        status=getIntent().getStringExtra("status");
        orderid=findViewById(R.id.cancel_orderid);
        shopname=findViewById(R.id.cancel_name);
        total=findViewById(R.id.cancel_total);
        submit=findViewById(R.id.cancel_submit);
        rv=findViewById(R.id.cancel_list);

        glm=new GridLayoutManager(cancelpage.this,1);
        rv.setLayoutManager(glm);
        fnl=new FinalOrderAdpt(cancelpage.this,true);

        fnl.setOnRemoveListener(new FinalOrderAdpt.ClickEvent() {
            @Override
            public void onClick(final String prdid, String prdname, String prdprice,final String prdqun, String img) {

                //SET IN SERVER
                String temp=prdid+"`"+prdname+"`"+prdprice+"`"+prdqun+"`"+img+"`";
                list=list.replace(temp,"");
                //NOT UPDATE LIST
                pd=ProgressDialog.show(cancelpage.this,"Please Wait","Removing...");
                StringRequest sr=new StringRequest(Request.Method.POST, Links.SET_REMOVE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        rv.setAdapter(fnl);
                        totalval=fnl.getTotal();
                        total.setText("Total: "+String.format("%.2f",totalval));
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
                        prms.put("ordid",order_id);
                        prms.put("list",list);
                        prms.put("prdid",prdid);
                        prms.put("qun",prdqun);
                        return prms;
                    }
                };
                RequestQueue q= Volley.newRequestQueue(cancelpage.this);
                q.add(sr);
                q.getCache().clear();
            }
        });
        if (status.equals("cancel")||status.equals("delivered"))
        {
            submit.setVisibility(View.GONE);
        }
        shopname.setText(shop_name);
        orderid.setText(order_id);

        StringTokenizer st=new StringTokenizer(list,"`");
        while (st.hasMoreTokens())
        {
            fnl.add(new FinalOrderList(st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken()));
        }
        rv.setAdapter(fnl);

        totalval=fnl.getTotal();
        total.setText("Total: "+String.format("%.2f",totalval));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd= ProgressDialog.show(cancelpage.this,"Please Wait","Canceling...");
                StringRequest sr=new StringRequest(Request.Method.POST, Links.GET_CANCEL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        AlertDialog.Builder b=new AlertDialog.Builder(cancelpage.this);
                        b.setMessage("OrderId:- "+orderid.getText().toString()+"\nCanceled");
                        b.setTitle("Done");
                        b.create();
                        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        b.setCancelable(false);
                        b.show();
                        ControlCancel.CTRL='y';
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
                        prms.put("orid",order_id);
                        return prms;
                    }
                };
                RequestQueue q= Volley.newRequestQueue(cancelpage.this);
                q.add(sr);
                q.getCache().clear();
            }
        });
    }
    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
