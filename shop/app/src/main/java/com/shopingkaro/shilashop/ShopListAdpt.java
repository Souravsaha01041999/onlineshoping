package com.shopingkaro.shilashop;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopListAdpt extends RecyclerView.Adapter<ShopListAdpt.MyHolder>
{
    interface ClickEvent
    {
        public void onClick(String shopId,String shopName);
    }
    private ClickEvent ce;
    private Context cc;
    List<ShopData> shops;
    ShopListAdpt(Context c,ClickEvent ce)
    {
        this.ce=ce;
        this.cc=c;
        this.shops=new ArrayList<>();
    }
    void add(ShopData sd)
    {
        shops.add(sd);
    }
    void clear()
    {
        shops.clear();
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(cc).inflate(R.layout.shope_list, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.setIsRecyclable(false);
        Picasso.get().load(Uri.parse(shops.get(position).getImage()))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.loadingmove)
                /*.resize(400,200)*/
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(holder.img);
        holder.name.setText(shops.get(position).getName());
        holder.rlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ce.onClick(shops.get(position).getId(),shops.get(position).getName());
            }
        });
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name;
        RelativeLayout rlt;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            rlt=itemView.findViewById(R.id.shope_clk);
            img=itemView.findViewById(R.id.shope_image);
            name=itemView.findViewById(R.id.shope_name);
        }
    }
}
