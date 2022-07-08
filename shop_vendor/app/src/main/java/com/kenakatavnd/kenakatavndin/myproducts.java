package com.kenakatavnd.kenakatavndin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class myproducts extends AppCompatActivity {
    RecyclerView rv;
    MyProductAdpt padpt;
    GridLayoutManager glm;
    String pass,uid;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myproducts);
        rv=findViewById(R.id.myproducts_list);
        glm=new GridLayoutManager(myproducts.this,1);
        rv.setLayoutManager(glm);

        sp= PreferenceManager.getDefaultSharedPreferences(myproducts.this);
        pass=sp.getString("shop_pass","");
        uid=sp.getString("shop_id","");

        padpt=new MyProductAdpt(myproducts.this);

        StringRequest sr=new StringRequest(Request.Method.POST, Links.GET_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response=response.trim();
                if (response.equals("n"))
                {
                    msg("Log out");
                    spe=sp.edit();
                    spe.putString("shop_id","");
                    spe.putString("shop_name","");
                    spe.putString("shop_category","");
                    spe.putString("shop_image","");
                    spe.putString("shop_pass","");
                    spe.apply();
                    ControlApp.LOGIN=false;
                    startActivity(new Intent(myproducts.this,MainActivity.class));
                    finish();
                }
                else if(!response.equals("]"))
                {
                        try {
                            JSONArray ja = new JSONArray(response);
                            for(int i=0;i<ja.length();i++)
                            {
                                JSONObject jo=ja.getJSONObject(i);
                                padpt.add(new ProductData(jo.getString("prd_id"),jo.getString("prd_name"),jo.getString("price"),jo.getString("image"),jo.getString("avl"),jo.getString("offer"),jo.getString("qun")));
                            }
                            rv.setAdapter(padpt);
                        }
                        catch (JSONException e)
                        {

                        }
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
            prms.put("id",uid);
            prms.put("pass",pass);
            return prms;
        }
    };
        RequestQueue q= Volley.newRequestQueue(myproducts.this);
        q.add(sr);
        q.getCache().clear();

    }

    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
