package com.kenakatavnd.kenakatavndin;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class show_product_image extends AppCompatActivity {
    ImageView image;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_image);
        image=findViewById(R.id.showproduct_image);
        name=findViewById(R.id.showproduct_name);

        Picasso.get().load(Uri.parse(getIntent().getStringExtra("product_image")))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.error_image_icon)
                .into(image);

        name.setText(getIntent().getStringExtra("product_name"));

    }
}
