package com.scarlett.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.scarlett.Model.ProfileItem;
import com.scarlett.R;
import com.scarlett.Utils.EqualSpacingItemDecoration;
import com.scarlett.activity.base.BaseToolbarActivity;
import com.scarlett.adapter.SearchCreatorAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class SearchCreaterActivity extends BaseToolbarActivity {


    @BindView(R.id.img_search)
    ImageView mImgSearch;

    @BindView(R.id.edt_search)
    EditText mEdtSearch;

    String[] Menu = {
            "Jennifer Aniston",
            "Angelina",
            "Monica",
            "Natalie",
            "Hilary",
            "Catherine"

    };

    int[] Menu_imageId = {
            R.drawable.ic_wallet,
            R.drawable.ic_recharge,
            R.drawable.ic_withdraw,
            R.drawable.ic_trans,
            R.drawable.ic_follower,
            R.drawable.ic_share1

    };

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    ProfileItem profileItem;
    ArrayList<ProfileItem> profileItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);


        showLeftButton(R.drawable.ic_toolbar_back, new BaseToolbarActivity.ILeftClickListener() {
            @Override
            public void onLeftButtonClicked() {
                onBackPressed();
            }
        });
        addTextChange();
        setProfileItem();

    }

    private void addTextChange() {
        mImgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));
        mImgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdtSearch.getText().length() > 0) {
                    mEdtSearch.setText("");
                }
            }
        });
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                   /* if(count !=0){
                        mImgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));
                    }else {
                        mImgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
                    }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void setProfileItem() {
        profileItems = new ArrayList<>();
        for (int i = 0; i < Menu.length; i++) {
           /* if (Menu[i].equals("Wallet Balance")) {
                profileItem = new ProfileItem(Menu[i], Menu_imageId[i], "200");
            } else {
                profileItem = new ProfileItem(Menu[i], Menu_imageId[i], "");
            }*/
            profileItem = new ProfileItem(Menu[i], Menu_imageId[i], "");

            profileItems.add(profileItem);
        }

        //creating recyclerview adapter
        SearchCreatorAdapter adapter = new SearchCreatorAdapter(this, profileItems);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.addItemDecoration(new EqualSpacingItemDecoration(16));
        mRecyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        mRecyclerView.setAdapter(adapter);
    }

}
