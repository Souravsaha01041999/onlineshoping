package com.shopingkaro.shilashop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class flashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StringRequest sr=new StringRequest(Request.Method.POST, Links.GET_CTRL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response=response.trim();
                        if (response.equals("ON"))
                        {
                            startActivity(new Intent(flashscreen.this,MainActivity.class));
                            finish();
                        }
                        else {
                            AlertDialog.Builder b=new AlertDialog.Builder(flashscreen.this);
                            b.setMessage("Services Is Under Maintenance");
                            b.setTitle("Error");
                            b.create();
                            b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            b.setCancelable(false);
                            b.show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        msg("Error Try Again");
                    }
                });
                RequestQueue q= Volley.newRequestQueue(flashscreen.this);
                q.add(sr);
                q.getCache().clear();
            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {

    }
    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
