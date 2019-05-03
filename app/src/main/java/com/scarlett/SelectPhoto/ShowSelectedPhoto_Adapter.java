package com.scarlett.SelectPhoto;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ArrayList<String> imageUrls,temp_imagesArray1;

    public ShowSelectedPhoto_Adapter(Context context, ArrayList<String> temp_imagesArray1, ArrayList<String> imageUrls) {
        this.context = context;
        this.temp_imagesArray1=temp_imagesArray1;
        this.imageUrls = imageUrls;
        Log.d("Pos:imagetotal size ",""+imageUrls.size());
        Log.d("Pos:temp_imagessize ",""+temp_imagesArray1.size());
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


        if(i==0 || i==1) {
            Glide.with(context).load("file://" + temp_imagesArray1.get(i))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(showViewHolder.iv_logo);
            showViewHolder.textViewTitle.setVisibility(View.GONE);
        }else if(i>=2){
            int size = imageUrls.size() - 2;
            Log.d("Pos:imagetotal size1 ",""+size);

            int temp_size=temp_imagesArray1.size()-1;
            Log.d("Pos:temp_size1 ",""+size);
            if(temp_size==i) {
                showViewHolder.textViewTitle.setText("" + size+"+");
                Glide.with(context).load("file://" + temp_imagesArray1.get(temp_size))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(showViewHolder.iv_logo);


                showViewHolder.textViewTitle.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    public int getItemCount() {
        return temp_imagesArray1.size();
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
