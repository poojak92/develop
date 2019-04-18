package com.scarlett.Fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

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
    protected void init() {




    }


}