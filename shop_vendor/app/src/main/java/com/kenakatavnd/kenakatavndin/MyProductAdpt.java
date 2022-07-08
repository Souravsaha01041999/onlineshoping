package com.kenakatavnd.kenakatavndin;

import android.content.Context;
import android.content.Intent;
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

public class MyProductAdpt extends RecyclerView.Adapter<MyProductAdpt.MyHolder>
{
    private List<ProductData>datas;
    private Context context;

    MyProductAdpt(Context c)
    {
        this.context=c;
        datas=new ArrayList<>();
    }
    void add(ProductData pd)
    {
        this.datas.add(pd);
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.myproduct_view, parent, false);
        return new MyProductAdpt.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.setIsRecyclable(false);

        holder.qun.setText(datas.get(position).getQun());
        if (!datas.get(position).getOffer().equals("n"))
        {
            holder.offer.setText(datas.get(position).getOffer());
        }
        else {
            holder.offer.setText("");
        }
        if (datas.get(position).getAvl().equals("1"))
        {
            holder.avl.setText("Available");
        }
        else {
            holder.avl.setText("Not Available");
        }

        holder.id.setText(datas.get(position).getPrd_id());
        holder.price.setText(datas.get(position).getPrice());
        holder.name.setText(datas.get(position).getPrd_name());

        Picasso.get().load(Uri.parse(datas.get(position).getImage()))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.error_image_icon)
                .into(holder.img);

        holder.clk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,changeprd.class)
                        .putExtra("id",datas.get(position).getPrd_id())
                        .putExtra("avl",datas.get(position).getAvl())
                        .putExtra("price",datas.get(position).getPrice())
                        .putExtra("qun",datas.get(position).getQun())
                        .putExtra("offer",datas.get(position).getOffer())
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
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
        RelativeLayout clk;
        ImageView img;
        TextView name,price,id,avl,offer,qun;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.myproduct_list_image);
            name=itemView.findViewById(R.id.myproduct_list_name);
            price=itemView.findViewById(R.id.myproduct_list_price);
            id=itemView.findViewById(R.id.myproduct_list_id);
            avl=itemView.findViewById(R.id.myproduct_list_avl);
            offer=itemView.findViewById(R.id.myproduct_list_offer);
            qun=itemView.findViewById(R.id.myproduct_list_qun);
            clk=itemView.findViewById(R.id.myproduct_list_clk);
        }
    }
}
