package com.scarlett.Fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scarlett.Fragment.base.BaseFragment;
import com.scarlett.Model.ProfileItem;
import com.scarlett.R;
import com.scarlett.adapter.ProfileAdapter;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends BaseFragment {
    public static String TAG = GalleryFragment.class.getName();


    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.fragment_gallery;
    }


    @Override
    protected void init() {
    }


}