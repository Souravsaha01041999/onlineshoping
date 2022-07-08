package com.shopingkaro.shilashop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CataAdpt extends RecyclerView.Adapter<CataAdpt.MyHolder>
{
    interface ClickEventCata
    {
        public void onClick(String cata);
    }

    private Context context;
    String catvals[]={"ACCESSORIES","ELECTRIC AND ELECTRONICS","FASHION","GROCERY"};
    int imgs[]={R.drawable.ac2,R.drawable.el2,R.drawable.fa2,R.drawable.gr2};
    private ClickEventCata clk;
    CataAdpt(Context c,ClickEventCata cec)
    {
        this.context=c;
        this.clk=cec;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cata_design, parent, false);
        return new CataAdpt.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.img.setImageResource(imgs[position]);
        holder.text.setText(catvals[position]);
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clk.onClick(catvals[position]);
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
        return catvals.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView text;
        RelativeLayout rl;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.cata_design_img);
            text=itemView.findViewById(R.id.cata_design_name);
            rl=itemView.findViewById(R.id.cata_design_clk);
        }
    }
}
