package com.kenakatavnd.kenakatavndin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    ImageView shop_image_show;
    String shop_image_data,shop_name_data,shop_id_data,shop_category_data;
    TextView shop_name_show,shop_category_show,shop_id_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sp= PreferenceManager.getDefaultSharedPreferences(home.this);
        shop_category_data=sp.getString("shop_category","");
        shop_id_data=sp.getString("shop_id","");
        shop_image_data=sp.getString("shop_image","");
        shop_name_data=sp.getString("shop_name","");

        shop_image_show=findViewById(R.id.home_shop_image);
        shop_category_show=findViewById(R.id.home_shop_cata);
        shop_name_show=findViewById(R.id.home_shop_name);
        shop_id_show=findViewById(R.id.home_shop_id);

        shop_id_show.setText("ID: "+shop_id_data);
        shop_name_show.setText("Shop Name: "+shop_name_data);
        shop_category_show.setText("Category: "+shop_category_data);

        Picasso.get().load(Uri.parse(shop_image_data))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.error_image_icon)
                .into(shop_image_show);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //WRITE CODE FOR DRAWER TO NEW PAGE
        if (id==R.id.item_upload_product)
        {
            startActivity(new Intent(home.this,uploadproduct.class).putExtra("shop_id",shop_id_data));
        }
        else if(id==R.id.item_show_product)
        {
            startActivity(new Intent(home.this,myproducts.class));
        }
        else if (id==R.id.item_my_order)
        {
            startActivity(new Intent(home.this,getorderpage.class));
        }
        else if (id==R.id.item_log_out)
        {
            spe=sp.edit();
            spe.putString("shop_id","");
            spe.putString("shop_name","");
            spe.putString("shop_category","");
            spe.putString("shop_image","");
            spe.putString("shop_pass","");
            spe.apply();
            startActivity(new Intent(home.this,MainActivity.class));
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        if (!ControlApp.LOGIN)
        {
            startActivity(new Intent(home.this,MainActivity.class));
            finish();
        }
        super.onResume();
    }
}
