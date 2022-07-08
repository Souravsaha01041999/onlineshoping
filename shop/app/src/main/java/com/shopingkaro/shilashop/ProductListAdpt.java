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

public class ProductListAdpt extends RecyclerView.Adapter<ProductListAdpt.MyHolder>
{
    interface ClickEventPrd
    {
        public void onClick(String prId,String qn);
    }
    List<ProductDet>products;
    private Context c;
    ClickEventPrd clep;
    ProductListAdpt(Context c,ClickEventPrd cpl)
    {
        this.clep=cpl;
        products=new ArrayList<>();
        this.c=c;
    }
    void add(ProductDet pdd)
    {
        this.products.add(pdd);
    }
    void clear()
    {
        this.products.clear();
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.product_list, parent, false);
        return new ProductListAdpt.MyHolder(v);
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
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.setIsRecyclable(false);
        Picasso.get().load(Uri.parse(products.get(position).getPrimage()))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.loadingmove)
                /*.resize(400,200)*/
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(holder.img);
        holder.price.setText("Rs: "+products.get(position).getPrprice());
        holder.prname.setText(products.get(position).getPrname());
        if (products.get(position).getPravl().equals("1")&&(!products.get(position).getQun().equals("0")))
        {
            holder.avl.setText("");
        }
        else {
            holder.avl.setText("out of stock");
        }
        holder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clep.onClick(products.get(position).getPrid(),products.get(position).getQun());
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout rv;
        ImageView img;
        TextView prname,avl,price;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            prname=itemView.findViewById(R.id.product_name);
            avl=itemView.findViewById(R.id.product_aval);
            price=itemView.findViewById(R.id.product_price);
            img=itemView.findViewById(R.id.product_image);
            rv=itemView.findViewById(R.id.prclk);
        }
    }
}
