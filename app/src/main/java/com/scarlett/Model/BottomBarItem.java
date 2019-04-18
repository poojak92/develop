package com.scarlett.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

public class BottomBarItem {


    private Drawable mActiveDrawable;
    private Drawable mInactiveDrawable;
    private Context mContext;

    public BottomBarItem(Context mContext) {
        this.mContext = mContext;
    }

    public Drawable getmActiveDrawable() {
        return mActiveDrawable;
    }

    public void setmActiveDrawable(Drawable mActiveDrawable) {
        this.mActiveDrawable = mActiveDrawable;
    }

    public Drawable getmInactiveDrawable() {
        return mInactiveDrawable;
    }

    public void setmInactiveDrawable(Drawable mInactiveDrawable) {
        this.mInactiveDrawable = mInactiveDrawable;
    }


    public void setDrawablesFromId(final int activeDrawableId,final int inactiveDrawableId){
       mActiveDrawable = ContextCompat.getDrawable(mContext,activeDrawableId);
       mInactiveDrawable = ContextCompat.getDrawable(mContext,inactiveDrawableId);
    }

}
