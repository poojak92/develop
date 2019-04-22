package com.scarlett.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.scarlett.Callback.Fragment.FragmentPresenter;
import com.scarlett.Fragment.base.BaseFragment;
import com.scarlett.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends BaseFragment {
    public static String TAG = VideoFragment.class.getName();
    FragmentPresenter fragmentPresenter;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentPresenter = (FragmentPresenter) mParentActivity;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentPresenter.passFragmentTag(VideoFragment.TAG);
    }
}