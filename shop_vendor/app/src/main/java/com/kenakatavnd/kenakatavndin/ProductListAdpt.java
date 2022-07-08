package com.kenakatavnd.kenakatavndin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListAdpt extends RecyclerView.Adapter<ProductListAdpt.MyHolder>
{
    private List<ProductList>data;
    private Context context;

    ProductListAdpt(Context c)
    {
        this.context=c;
        data=new ArrayList<>();
    }

    void add(ProductList pl)
    {
        this.data.add(pl);
    }

    String  getTotal()
    {
        float temp=0.0f;

        for (ProductList pl2:data)
        {
            temp=temp+Float.parseFloat(pl2.getPrice())*Float.parseFloat(pl2.getQun());
        }

        return String.valueOf(temp);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_list_view, parent, false);
        return new ProductListAdpt.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.setIsRecyclable(false);

        holder.name.setText(data.get(position).getPrdname());
        holder.qun.setText(data.get(position).getQun());
        holder.price.setText(data.get(position).getPrice());
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SEND NEXT PAGE FOR SHOWING PRODUCT IMAGE
                context.startActivity(new Intent(context,show_product_image.class)
                        .putExtra("product_image",data.get(position).getImage())
                        .putExtra("product_name",data.get(position).getPrdname())
                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView name,qun,price;
        RelativeLayout click;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.productlist_price);
            qun=itemView.findViewById(R.id.productlist_qun);
            name=itemView.findViewById(R.id.productlist_name);
            click=itemView.findViewById(R.id.productlist_clk);
        }
    }
}
