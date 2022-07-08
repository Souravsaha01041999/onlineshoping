package com.kenakatavnd.kenakatavndin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class uploadproduct extends AppCompatActivity {
    ImageView shop_image,load_image;
    boolean selectImage=false;
    Button submit;
    EditText prdname,prdprice,prddet,qun;
    byte imgdata[];
    RadioGroup rdg;
    SharedPreferences sp;
    String prd_id,type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadproduct);
        load_image=findViewById(R.id.upload_image_set);
        shop_image=findViewById(R.id.upload_image);
        prdname=findViewById(R.id.upload_prd_name);
        prdprice=findViewById(R.id.upload_prd_price);
        prddet=findViewById(R.id.upload_prd_detail);
        qun=findViewById(R.id.upload_prd_qun);
        submit=findViewById(R.id.upload_prd_submit);
        rdg=findViewById(R.id.upload_prd_type);

        sp= PreferenceManager.getDefaultSharedPreferences(uploadproduct.this);



        load_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),200);
            }
        });

        if (!sp.getString("shop_category","").equalsIgnoreCase("FASHION"))
        {
            rdg.setVisibility(View.GONE);
            type="n";
        }

        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.male:
                        type="m";
                        msg("Male");
                        break;
                    case R.id.female:
                        type="f";
                        msg("female");
                        break;
                    case R.id.child:
                        type="c";
                        msg("child");
                        break;
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((prdname.getText().toString().length()>0)&&(prdprice.getText().toString().length()>0)&&(prddet.getText().toString().length()>0)&&(qun.getText().toString().length()>0)&&(selectImage)&&(type.length()>0))
                {
                    prd_id="PE"+sp.getString("shop_id","")+new SimpleDateFormat("ddMMyyyy").format(new Date()) + new SimpleDateFormat("HHmmss").format(new Date());

                    if (!prdprice.getText().toString().contains("."))
                    {
                        prdprice.setText(prdprice.getText().toString()+".00");
                    }

                    msg("Please Wait...");
                    VolleyMultipart vm=new VolleyMultipart(Request.Method.POST, Links.UPLOAD_PRODUCT, new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            try {
                                String s = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                                if (s.equals("1")) {
                                    msg("Success");
                                    finish();
                                } else {
                                    msg("Sorry Try Again..!");
                                }
                            }
                            catch (UnsupportedEncodingException e)
                            {

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            msg("Error try again...!");
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>pems=new Hashtable<>();
                            pems.put("prd_id",prd_id);
                            pems.put("prd_name",prdname.getText().toString());
                            pems.put("price",prdprice.getText().toString());
                            pems.put("detail",prddet.getText().toString());
                            pems.put("type",type);
                            pems.put("shop_id",sp.getString("shop_id",""));
                            pems.put("cata",sp.getString("shop_category",""));
                            pems.put("qun",qun.getText().toString());
                            return pems;
                        }
                        @Override
                        protected Map<String, VolleyMultipart.DataPart> getByteData() throws AuthFailureError {
                            Map<String, VolleyMultipart.DataPart> params = new HashMap<>();
                            params.put("file",new DataPart(prd_id+".jpg",imgdata));
                            return params;
                        }
                    };
                    vm.setShouldCache(false);
                    RequestQueue vq= Volley.newRequestQueue(uploadproduct.this);
                    vq.add(vm);
                    vq.getCache().clear();
                }
                else {
                    msg("Please Enter All Data");
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==200)&&(resultCode==RESULT_OK))
        {
            selectImage=true;
                Bitmap bp=(Bitmap)data.getExtras().get("data");
                imgdata=getByte(bp);
                shop_image.setImageBitmap(bp);
        }
        else
        {
            msg("Please Take A Image");
        }
    }

    byte[] getByte(Bitmap bitmap)
    {
        ByteArrayOutputStream b=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,85,b);
        return b.toByteArray();
    }

    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
