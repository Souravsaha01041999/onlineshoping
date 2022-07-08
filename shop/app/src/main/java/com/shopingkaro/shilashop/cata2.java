package com.shopingkaro.shilashop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class cata2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView img;
    RecyclerView rv;
    GridLayoutManager glm;
    CataAdpt caadpt;

    //Spinner sp;
    //ArrayAdapter adpt;
    //String catvals[]={"-SELECT-","FASHION","GROCERY","ACCESSORIES","ELECTRIC AND ELECTRONICS"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cata2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv=findViewById(R.id.cata_list);
        glm=new GridLayoutManager(cata2.this,2);
        caadpt=new CataAdpt(cata2.this, new CataAdpt.ClickEventCata() {
            @Override
            public void onClick(String cataaa) {
                startActivity(new Intent(cata2.this,home.class).putExtra("cata",cataaa));
            }
        });

        rv.setLayoutManager(glm);
        rv.setAdapter(caadpt);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //toggle.setHomeAsUpIndicator(R.drawable.menu_nav_my);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v=navigationView.getHeaderView(0);
        img=v.findViewById(R.id.drd_sld);

        /*sp=findViewById(R.id.cata_chose);
        adpt=new ArrayAdapter(cata2.this,R.layout.support_simple_spinner_dropdown_item,catvals);
        sp.setAdapter(adpt);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0)
                {
                    startActivity(new Intent(cata2.this,home.class).putExtra("cata",catvals[position]));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        Picasso.get().load(Uri.parse(Links.GET_DRD_SLD))
                .placeholder(R.drawable.loadingmove)
                .resize(400,200)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(img);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.cata2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.myorder_item) {
            // Handle the camera action
            startActivity(new Intent(cata2.this,myallorders.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
