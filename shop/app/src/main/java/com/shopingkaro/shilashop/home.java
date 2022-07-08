package com.shopingkaro.shilashop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;

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

public class home extends AppCompatActivity {
    String catagory="";
    RecyclerView rv;
    GridLayoutManager glm;
    ShopListAdpt sla,seaarchshoped;
    ProgressDialog pd;
    TextView tv;
    SearchView sv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rv=findViewById(R.id.home_shop_list);
        sv=findViewById(R.id.home_shop_search);

        glm=new GridLayoutManager(home.this,1);
        rv.setLayoutManager(glm);
        catagory=getIntent().getStringExtra("cata");
        tv=findViewById(R.id.home_show_cata);
        tv.setText(catagory);

        sv.setQueryHint(Html.fromHtml("<font color = #AEB1AD>" + getResources().getString(R.string.search_hint) + "</font>"));
        int id = sv.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView =sv.findViewById(id);
        textView.setTextColor(Color.BLACK);


        sla=new ShopListAdpt(home.this, new ShopListAdpt.ClickEvent() {
            @Override
            public void onClick(String shopId,String shopName) {
                //send product page vie shopid
                startActivity(new Intent(home.this,product.class).putExtra("shopid",shopId).putExtra("catagory",catagory).putExtra("shopename",shopName));
            }
        });
        seaarchshoped=new ShopListAdpt(home.this, new ShopListAdpt.ClickEvent() {
            @Override
            public void onClick(String shopId, String shopName) {
                startActivity(new Intent(home.this,product.class).putExtra("shopid",shopId).putExtra("catagory",catagory).putExtra("shopename",shopName));
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                seaarchshoped.clear();
                query=query.toUpperCase();
                for (ShopData sd:sla.shops)
                {
                    if (sd.getName().toUpperCase().contains(query))
                    {
                        seaarchshoped.add(sd);
                    }
                }
                rv.setAdapter(seaarchshoped);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                seaarchshoped.clear();
                newText=newText.toUpperCase();
                for (ShopData sd:sla.shops)
                {
                    if (sd.getName().toUpperCase().contains(newText))
                    {
                        seaarchshoped.add(sd);
                    }
                }
                rv.setAdapter(seaarchshoped);
                return false;
            }
        });

        sv.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                seaarchshoped.clear();
                rv.setAdapter(sla);
                return false;
            }
        });

        reload();
    }

    void reload()
    {
        pd= ProgressDialog.show(home.this,"Wait","Load All Shops...");
        sla.clear();
        rv.setAdapter(sla);
        StringRequest sr=new StringRequest(Request.Method.POST, Links.GET_SHOP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                response=response.trim();
                if(!response.equals("]"))
                {
                    try {
                        JSONArray ja = new JSONArray(response);
                        for(int i=0;i<ja.length();i++)
                        {
                            JSONObject jo=ja.getJSONObject(i);
                            sla.add(new ShopData(jo.getString("shop_id"),jo.getString("shop_name"),jo.getString("image")));
                        }
                        rv.setAdapter(sla);
                    }
                    catch (JSONException e)
                    {

                    }
                }
                else {
                    msg("Services not available");
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
                prms.put("cata",catagory);
                return prms;
            }
        };
        RequestQueue q= Volley.newRequestQueue(home.this);
        q.add(sr);
        q.getCache().clear();
    }
    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
