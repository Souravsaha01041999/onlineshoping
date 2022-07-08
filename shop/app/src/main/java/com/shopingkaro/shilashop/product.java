package com.shopingkaro.shilashop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class product extends AppCompatActivity {
    String shopid;
    String catagory;
    TextView shopename;
    String type;
    RadioGroup rdg;
    RadioButton all,child,male,female;
    ProgressDialog pd;
    RecyclerView rv;
    GridLayoutManager glm;
    ProductListAdpt pla,searchproduct;
    List<OrderListData>orders;
    String sendlist="";
    AlertDialog.Builder b;
    char ctrl='n';   // FROM DETAILS PAGE d FROM CART PAGE c n FOR NO USE o for order
    int position=0;
    Button send;
    ImageView cart;
    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        rdg=findViewById(R.id.product_type);
        all=findViewById(R.id.type_all);
        child=findViewById(R.id.type_child);
        male=findViewById(R.id.type_male);
        female=findViewById(R.id.type_female);
        send=findViewById(R.id.product_submit);
        cart=findViewById(R.id.product_cart);
        sv=findViewById(R.id.product_search);

        orders=new ArrayList<>();

        rv=findViewById(R.id.list_of_product);

        glm=new GridLayoutManager(product.this,1);
        rv.setLayoutManager(glm);
        sv.setQueryHint(Html.fromHtml("<font color = #AEB1AD>" + getResources().getString(R.string.search_hint) + "</font>"));
        int id = sv.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView =sv.findViewById(id);
        textView.setTextColor(Color.BLACK);
        pla=new ProductListAdpt(product.this, new ProductListAdpt.ClickEventPrd() {
            @Override
            public void onClick(String prId,String qn) {
                //GET ALL DET FROM ORDER LIST
                if (orders.size()>0) {
                    for (OrderListData od : orders) {
                        if (prId.equals(od.getPrdid())) {
                            ControlSystem.QUN = od.getPrdqun();
                            break;
                        } else {
                            ControlSystem.QUN = "0";
                        }
                    }
                }else {
                    ControlSystem.QUN = "0";
                }
                ctrl='d';
                startActivity(new Intent(product.this,productdetpage.class).putExtra("productid",prId).putExtra("productqun",qn));
            }
        });

        searchproduct=new ProductListAdpt(product.this, new ProductListAdpt.ClickEventPrd() {
            @Override
            public void onClick(String prId, String qn) {
                if (orders.size()>0) {
                    for (OrderListData od : orders) {
                        if (prId.equals(od.getPrdid())) {
                            ControlSystem.QUN = od.getPrdqun();
                            break;
                        } else {
                            ControlSystem.QUN = "0";
                        }
                    }
                }else {
                    ControlSystem.QUN = "0";
                }
                ctrl='d';
                startActivity(new Intent(product.this,productdetpage.class).putExtra("productid",prId).putExtra("productqun",qn));
            }
        });

        shopid=getIntent().getStringExtra("shopid");
        catagory=getIntent().getStringExtra("catagory");
        shopename=findViewById(R.id.product_shopname);
        shopename.setText(getIntent().getStringExtra("shopename"));
        if(catagory.equals("FASHION"))
        {
            type="a";
            all.toggle();
        }
        else
        {
            type="n";
            rdg.setVisibility(View.GONE);
        }

        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.type_all:
                        type="a";
                        msg("all");
                        break;
                    case R.id.type_child:
                        type="c";
                        msg("child");
                        break;
                    case R.id.type_male:
                        type="m";
                        msg("male");
                        break;
                    case R.id.type_female:
                        type="f";
                        msg("female");
                        break;
                }
                reload();
            }
        });

        reload();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SEND ALL LISTS TO CHECKOUT PAGE
                if (orders.size()>0) {
                    sendlist = "";
                    SqlCmd.CMD="";
                    for (OrderListData old2 : orders) {
                        sendlist = sendlist + old2.getPrdid() + "`" + old2.getPrdname() + "`" + old2.getPrdrice() + "`" + old2.getPrdqun() + "`"+old2.getImage()+"`";
                        SqlCmd.CMD=SqlCmd.CMD+"UPDATE `product` SET qun =qun - "+ old2.getPrdqun()+" WHERE prd_id='"+old2.getPrdid()+"'; ";
                    }
                    ctrl='o';
                    startActivity(new Intent(product.this, orderpage.class).putExtra("orders_list", sendlist).putExtra("orders_shopename", shopename.getText().toString()).putExtra("order_shopid", shopid));
                }
                else {
                    msg("Please add item");
                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orders.size()>0) {
                    for (OrderListData old : orders) {
                        CartControl.LISTS = CartControl.LISTS + old.getPrdid() + "`" + old.getPrdname() + "`" + old.getPrdrice() + "`" + old.getPrdqun() + "`";
                    }
                    ctrl = 'c';
                    startActivity(new Intent(product.this, cartpage.class));
                }
                else {
                    msg("Please select ay item");
                }
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchproduct.clear();
                query=query.toUpperCase();
                for (ProductDet pd:pla.products)
                {
                    if (pd.getPrname().toUpperCase().contains(query))
                    {
                        searchproduct.add(pd);
                    }
                }
                rv.setAdapter(searchproduct);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchproduct.clear();
                newText=newText.toUpperCase();
                for (ProductDet pd:pla.products)
                {
                    if (pd.getPrname().toUpperCase().contains(newText))
                    {
                        searchproduct.add(pd);
                    }
                }
                rv.setAdapter(searchproduct);
                return false;
            }
        });

        sv.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchproduct.clear();
                rv.setAdapter(pla);
                return false;
            }
        });
    }
    void reload()
    {
        pd= ProgressDialog.show(product.this,"Wait","Load All Items...");
        pla.clear();
        rv.setAdapter(pla);
        StringRequest sr=new StringRequest(Request.Method.POST, Links.GET_PRODUCT, new Response.Listener<String>() {
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
                            pla.add(new ProductDet(jo.getString("prd_id"),jo.getString("prd_name"),jo.getString("price"),jo.getString("image"),jo.getString("avl"),jo.getString("qun")));
                        }
                        rv.setAdapter(pla);
                    }
                    catch (JSONException e)
                    {

                    }
                }
                else {
                    msg("No Product Found");
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
                prms.put("sid",shopid);
                prms.put("cata",catagory);
                prms.put("type",type);
                return prms;
            }
        };
        RequestQueue q= Volley.newRequestQueue(product.this);
        q.add(sr);
        q.getCache().clear();
    }

    @Override
    public void onBackPressed() {
        if (orders.size()>0)
        {
            b=new AlertDialog.Builder(product.this);
            b.setMessage("Are you sure to cancel all items?");
            b.setTitle("Cancel");
            b.create();
            b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    orders.clear();
                    finish();
                }
            });
            b.setNegativeButton("No", null);
            b.setCancelable(true);
            b.show();
        }
        else
        {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ctrl=='d')
        {
            ctrl='n';
            //GET ALL DET AND ADD IN ORDER LIST
            if (ControlSystem.CMD.equals("a"))
            {
                orders.add(new OrderListData(ControlSystem.PID,ControlSystem.NAME,ControlSystem.PRICE,ControlSystem.QUN,ControlSystem.IMAGE));
            }
            else if (ControlSystem.CMD.equals("u"))
            {
                //UPDATE ORDER LIST
                for(OrderListData od:orders)
                {
                    if (od.getPrdid().equals(ControlSystem.PID))
                    {
                        od.setPrqun(ControlSystem.QUN);
                        break;
                    }
                }
            }else if(ControlSystem.CMD.equals("r"))
            {
                if (orders.size()>0) {
                    for (OrderListData od : orders) {
                        if (od.getPrdid().equals(ControlSystem.PID)) {
                            orders.remove(position);
                            break;
                        }
                        position++;
                    }
                    position = 0;
                }
            }
        }
        else if(ctrl=='c')
        {
            ctrl='n';
            int i=0;
            String temp,temp2;
            //UPDATE ORDER LISTS FROM CART PAGE
            if (CartControl.UPDATE=='y')
            {
                StringTokenizer st=new StringTokenizer(CartControl.LISTS,"`");
                while (st.hasMoreTokens())
                {
                    temp=st.nextToken();
                    temp2=st.nextToken();
                    for (i=0;i<orders.size();i++)
                    {
                        if (orders.get(i).getPrdid().equals(temp))
                        {
                            if(temp2.equals("0")) {
                                orders.remove(i);
                            }
                            else {
                                orders.get(i).setPrqun(temp2);
                                break;
                            }
                        }
                    }
                }
            }
            CartControl.UPDATE='n';
            CartControl.LISTS="";
        }
        else if(ctrl=='o')
        {
            ctrl='n';
            if (OrderSet.CTRL=='y')
            {
                orders.clear();
                OrderSet.CTRL='n';
            }
        }

    }

    void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
