package com.app.volleyecom;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    List<MyObject> myObjectList;

    public MyAdapter(List<MyObject> myObjectList) {
        this.myObjectList = myObjectList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        MyObject object = myObjectList.get(position);

        // Debugging: Log the URL
        Log.d("ImageURL", "URL of photo: " + object.getImage());
        holder.title.setText(object.getTitle());
        holder.desc.setText(object.getDescription());
        holder.price.setText(String.valueOf(object.getPrice()));
        // Load image with Picasso
        Picasso.get()
                .load(object.getImage())
                .into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        return myObjectList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView title;
        TextView desc;
        TextView price;
        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title  = itemView.findViewById(R.id.title);
            desc  = itemView.findViewById(R.id.desc);
            price  = itemView.findViewById(R.id.price);
            imageView  = itemView.findViewById(R.id.item_image);
        }

    }
}
