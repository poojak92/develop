package com.scarlett.SelectPhoto;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scarlett.R;

import java.util.ArrayList;

/**
 * Created by SONU on 31/10/15.
 */
public class ShowSelectedPhoto_Adapter  extends RecyclerView.Adapter<ShowSelectedPhoto_Adapter.ShowViewHolder> {
    private Context context;
    private ArrayList<String> imageUrls;

    public ShowSelectedPhoto_Adapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;

    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_showselected, null);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder showViewHolder, int i) {
        Log.d("Pos: ",""+i);
        Log.d("Pos:imagetotal size ",""+imageUrls.size());
        if(i==0 && i==1) {
            Log.d("Pos:imageUrls size3 ",""+imageUrls.size());
            Glide.with(context).load("file://" + imageUrls.get(i))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(showViewHolder.iv_logo);
            showViewHolder.textViewTitle.setVisibility(View.GONE);
        }else {
            Log.d("Pos:imageUrls size1 ",""+imageUrls.size());

            if(imageUrls.size()>3) {
                Log.d("Pos:imageUrls size2 ",""+imageUrls.size());

                int size = imageUrls.size() - 2;
                    showViewHolder.textViewTitle.setText("" + size);
                    Glide.with(context).load("file://" + imageUrls.get(i))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(showViewHolder.iv_logo);
                    showViewHolder.textViewTitle.setVisibility(View.VISIBLE);

            }else {
                Glide.with(context).load("file://" + imageUrls.get(i))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(showViewHolder.iv_logo);
                showViewHolder.textViewTitle.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }


    class ShowViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView iv_logo;
       // ConstraintLayout conProfile;
        public ShowViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            iv_logo = itemView.findViewById(R.id.iv_logo);
         //   conProfile = itemView.findViewById(R.id.conProfile);
        }
    }
}
