package com.scarlett.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.scarlett.Callback.Fragment.FragmentPresenter;
import com.scarlett.Fragment.base.BaseFragment;
import com.scarlett.Model.ProfileItem;
import com.scarlett.R;
import com.scarlett.activity.base.BaseToolbarActivity;
import com.scarlett.adapter.ProfileAdapter;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends BaseFragment {
    public static String TAG = MyAccountFragment.class.getName();
    String[] Menu = {
            "Wallet",
            "Recharge",
            "Withdraw",
            "Transaction",
            "Followers",
            "Share",
            "About us",
            "Logout"
    };

    int[] Menu_imageId = {
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about
    };

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.img_back)
    ImageView mImgback;



    ProfileItem profileItem;
    ArrayList<ProfileItem> profileItems;

    FragmentPresenter fragmentPresenter;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.fragment_account;
    }


    @Override
    protected void init() {
        initTollbar();

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentPresenter = (FragmentPresenter) mParentActivity;
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
        ProfileAdapter adapter = new ProfileAdapter(mParentActivity, profileItems);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mParentActivity,4);
        mRecyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        mRecyclerView.setAdapter(adapter);
    }

    public void initTollbar()
    {
        mImgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentActivity.onBackPressed();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentPresenter.passFragmentTag(MyAccountFragment.TAG);
        setProfileItem();
    }
}