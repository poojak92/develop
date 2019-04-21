package com.scarlett.Fragment;

import android.support.v4.app.Fragment;

import com.scarlett.Fragment.base.BaseFragment;
import com.scarlett.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends BaseFragment {
    public static String TAG = VideoFragment.class.getName();


    public VideoFragment() {
        // Required empty public constructor
    }

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.fragment_video;
    }


    @Override
    protected void init() {
    }


}