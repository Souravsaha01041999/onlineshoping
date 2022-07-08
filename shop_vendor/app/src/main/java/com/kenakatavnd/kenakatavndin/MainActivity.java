package com.kenakatavnd.kenakatavndin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    Button submit;
    EditText uid,pass;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit=findViewById(R.id.login_submit);
        uid=findViewById(R.id.login_uid);
        pass=findViewById(R.id.login_pass);


        sp= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (sp.getString("shop_id","").length()>0)
        {
            ControlApp.LOGIN=true;
            startActivity(new Intent(MainActivity.this,home.class));
            finish();
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((uid.getText().toString().length()>0)&&(pass.getText().toString().length()>0))
                {
                    msg("Please Wait..!");
                    StringRequest sr=new StringRequest(Request.Method.POST, Links.LOGIN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            response=response.trim();
                            if(response.equals("0"))
                            {
                                msg("User Not Found..!");
                                pass.setText("");
                            }
                            else if(response.equals("1")){
                                msg("Password not match..!");
                                pass.setText("");
                            }
                            else {
                                spe=sp.edit();
                                try {
                                    JSONArray ja = new JSONArray(response);
                                    for(int i=0;i<ja.length();i++) {
                                        JSONObject jo = ja.getJSONObject(i);
                                        spe.putString("shop_id",jo.getString("shop_id"));
                                        spe.putString("shop_name",jo.getString("shop_name"));
                                        spe.putString("shop_category",jo.getString("category"));
                                        spe.putString("shop_image",jo.getString("image"));
                                        spe.putString("shop_pass",pass.getText().toString());
                                        spe.apply();
                                        ControlApp.LOGIN=true;

                                        startActivity(new Intent(MainActivity.this,home.class));
                                        finish();
                                    }
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
                            prms.put("id",uid.getText().toString());
                            prms.put("pass",pass.getText().toString());
                            return prms;
                        }
                    };
                    RequestQueue q= Volley.newRequestQueue(MainActivity.this);
                    q.add(sr);
                    q.getCache().clear();
                }
                else {
                    msg("Please Enter User id or password");
                }
            }
        });
    }

    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
