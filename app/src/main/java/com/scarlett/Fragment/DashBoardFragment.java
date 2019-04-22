package com.scarlett.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

import com.scarlett.Callback.Fragment.FragmentPresenter;
import com.scarlett.Fragment.base.BaseFragment;
import com.scarlett.R;
import com.scarlett.Ui.CustomeView.CustomButton;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends BaseFragment {
    public static String TAG = DashBoardFragment.class.getName();

    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    FragmentPresenter fragmentPresenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentPresenter = (FragmentPresenter) mParentActivity;
    }

    public DashBoardFragment() {
        // Required empty public constructor
    }

    public static DashBoardFragment newInstance() {
        return new DashBoardFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.fragment_dashboard;
    }


    @Override
    protected void init() { }

    @Override
    public void onResume() {
        super.onResume();
        fragmentPresenter.passFragmentTag(DashBoardFragment.TAG);
    }


}