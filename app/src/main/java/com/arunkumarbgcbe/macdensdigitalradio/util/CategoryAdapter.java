package com.arunkumarbgcbe.macdensdigitalradio.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arunkumarbgcbe.macdensdigitalradio.R;

import java.util.List;

/**
 * Created by ArunKumar on 07-06-2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<String> categoryList;
    private int imgid;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Itemname;

        public ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.icon);
            Itemname = (TextView) view.findViewById(R.id.Itemname);

        }
    }


    public CategoryAdapter(List<String> moviesList) {
        this.categoryList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.imageView.setImageResource(imgid);
        holder.Itemname.setText(categoryList.get(position));

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
