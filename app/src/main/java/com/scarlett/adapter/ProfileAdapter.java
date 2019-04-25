package com.scarlett.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scarlett.Model.ProfileItem;
import com.scarlett.R;

import java.util.ArrayList;
import java.util.List;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
   private Context mCtx;

    //we are storing all the products in a list
    private List<ProfileItem> mProfileItems;
    IonSelectionListener mIonSelectionListener;

    //getting the context and product list with constructor
    public ProfileAdapter(ArrayList<ProfileItem> mProfileItems , IonSelectionListener ionSelectionListener) {
        this.mProfileItems = mProfileItems;
        this.mIonSelectionListener= ionSelectionListener;
        this.mCtx = mIonSelectionListener.getParentActivity();

    }


    public interface IonSelectionListener {
        public void onItemSelected(ProfileItem profileItem,int selectedIndex);
        public AppCompatActivity getParentActivity();

    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_profile, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        //getting the product of the specified position
        final ProfileItem profileItem = mProfileItems.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(profileItem.getTitle());
        holder.iv_logo.setImageResource(mProfileItems.get(position).getIcon());

        holder.conProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIonSelectionListener.onItemSelected(profileItem,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProfileItems.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView iv_logo;
        ConstraintLayout conProfile;
        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            iv_logo = itemView.findViewById(R.id.iv_logo);
            conProfile = itemView.findViewById(R.id.conProfile);
        }
    }
}
