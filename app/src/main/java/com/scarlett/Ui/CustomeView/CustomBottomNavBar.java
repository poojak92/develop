package com.scarlett.Ui.CustomeView;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scarlett.Model.BottomBarItem;
import com.scarlett.R;
import com.scarlett.Utils.BottomBarSingleton;
import com.scarlett.constants.AppConstants;


public class CustomBottomNavBar extends ConstraintLayout {


    private Context mContext;

    private int activeTextColor;
    private int inactiveTextColor;
    private ConstraintLayout mClParent;
    private String prevSelectedTag;
    private final String DEFAULT_SELECTED_WALLET_TAG = AppConstants.BottomBar.ViewTags.VIEW_TAG_HOME;
    private final String TEXT_VIEW_TAG = "_TEXT_VIEW";
    private IOnBottomBarItemClickListener mIOnBottomBarItemClickListener;


    public CustomBottomNavBar(Context context) {
        this(context,null,0);
    }

    public CustomBottomNavBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomBottomNavBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }


    public interface IOnBottomBarItemClickListener{
        public void onBottomBarItemClicked(final String clickedTag);
    }

    public void init(){
        activeTextColor = R.color.red;
        inactiveTextColor = R.color.hint_color;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CustomBottomNavBar view = (CustomBottomNavBar) inflater.inflate(R.layout.layout_bottom_bar, this);
        mClParent = view.findViewById(R.id.cl_parent);

        int childs = mClParent.getChildCount();

        for(int i = 0 ; i < childs ; i++){
            mClParent.getChildAt(i).setOnClickListener(onClickListener);
        }

        ImageView ivDefaultSelectedWallet = mClParent.findViewWithTag(DEFAULT_SELECTED_WALLET_TAG);
        String textViewTag = DEFAULT_SELECTED_WALLET_TAG + TEXT_VIEW_TAG;
        TextView tvDefaultSelectedTextView = mClParent.findViewWithTag(textViewTag);

        BottomBarItem defaultItem = BottomBarSingleton.getInstance(mContext).getBottomBarItemMap().get(DEFAULT_SELECTED_WALLET_TAG);
        ivDefaultSelectedWallet.setImageDrawable(defaultItem.getmActiveDrawable());
        tvDefaultSelectedTextView.setTextColor(ContextCompat.getColor(mContext,activeTextColor));

        ivDefaultSelectedWallet.setEnabled(false);
        tvDefaultSelectedTextView.setEnabled(false);
        prevSelectedTag = DEFAULT_SELECTED_WALLET_TAG;

    }


    public OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            String imageTag = prevSelectedTag;
            String textTag = prevSelectedTag+"_TEXT_VIEW";

            if(prevSelectedTag != null){
                imageTag = prevSelectedTag;
                textTag = prevSelectedTag + TEXT_VIEW_TAG;
                BottomBarItem prevBottomBarItem = BottomBarSingleton.getInstance(mContext).getBottomBarItemMap().get(imageTag);

                ImageView imageView = mClParent.findViewWithTag(imageTag);
                imageView.setImageDrawable(prevBottomBarItem.getmInactiveDrawable());
                TextView textView = mClParent.findViewWithTag(textTag);
                textView.setTextColor(ContextCompat.getColor(mContext,inactiveTextColor));

                imageView.setEnabled(true);
                textView.setEnabled(true);
            }

            if(tag.contains(TEXT_VIEW_TAG)) {
                textTag = tag;
                imageTag = tag.replace(TEXT_VIEW_TAG, "");
            }else {
                imageTag = tag;
                textTag = tag + TEXT_VIEW_TAG;
            }

            BottomBarItem selectedBottomBarItem = BottomBarSingleton.getInstance(mContext).getBottomBarItemMap().get(imageTag);

            ImageView imageView = mClParent.findViewWithTag(imageTag);
            imageView.setImageDrawable(selectedBottomBarItem.getmActiveDrawable());
            TextView textView = mClParent.findViewWithTag(textTag);
            textView.setTextColor(ContextCompat.getColor(mContext,activeTextColor));

            imageView.setEnabled(false);
            textView.setEnabled(false);

            prevSelectedTag = imageTag;

            if(mIOnBottomBarItemClickListener != null){
                mIOnBottomBarItemClickListener.onBottomBarItemClicked(prevSelectedTag);
            }

        }
    };


    public void setmIOnBottomBarItemClickListener(IOnBottomBarItemClickListener mIOnBottomBarItemClickListener) {
        this.mIOnBottomBarItemClickListener = mIOnBottomBarItemClickListener;
    }
}
