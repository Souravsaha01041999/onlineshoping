package com.shopingkaro.shilashop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapt extends RecyclerView.Adapter<CartAdapt.MyHolder>
{
    List<CartData>datas;
    private Context context;
    CartAdapt(Context c)
    {
        datas=new ArrayList<>();
        this.context=c;
    }

    void add(CartData cd)
    {
        this.datas.add(cd);
    }
    void clear()
    {
        this.datas.clear();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cartlists, parent, false);
        return new CartAdapt.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.prdname.setText(datas.get(position).getPrdname());
        holder.prdqun.setText(datas.get(position).getPrdqun());
        holder.prdprice.setText(datas.get(position).getPrdprice());
        holder.decre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(datas.get(position).getPrdqun())>0)
                datas.get(position).setPrqun(String.valueOf(Integer.parseInt(datas.get(position).getPrdqun())-1));
                holder.prdqun.setText(datas.get(position).getPrdqun());
                CartControl.UPDATE='y';
            }
        });

        holder.incre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(datas.get(position).getPrdqun()) < 3) {
                    datas.get(position).setPrqun(String.valueOf(Integer.parseInt(datas.get(position).getPrdqun()) + 1));

                    holder.prdqun.setText(datas.get(position).getPrdqun());
                    CartControl.UPDATE = 'y';
                }
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
        return datas.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView prdname,prdprice,prdqun;
        Button incre,decre;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            prdname=itemView.findViewById(R.id.cart_name);
            prdprice=itemView.findViewById(R.id.cart_price);
            prdqun=itemView.findViewById(R.id.cart_quan);
            incre=itemView.findViewById(R.id.cart_incre);
            decre=itemView.findViewById(R.id.cart_dec);
        }
    }
}
