package com.scarlett.Ui.CustomeView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.scarlett.R;
import com.scarlett.Ui.Callback.ICustomDialogClickHandler;


/**
 * Created by rac on 2/1/19.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {

    private Button mBtnYes, mBtnNo;
    private ICustomDialogClickHandler mICustomDialogClickHandler;
    private TextView mTextViewMessage, mTextViewTitle;
    private String mMessage;
    private String mTitle = null;
    private boolean mShowNoButton = true;
    private Context mContext;
    private String sYesButtonText = "Yes";

    public CustomDialog(Context context, ICustomDialogClickHandler handler, String message) {
        super(context);
        mICustomDialogClickHandler = handler;
        mMessage = message;
        mContext = context;
    }

    public CustomDialog(Context context, String title, String message, String yesButtonText) {
        super(context);
        mTitle = title;
        mMessage = message;
        mContext = context;
        sYesButtonText = yesButtonText;
        mShowNoButton = false;
    }

    public CustomDialog(Context context, ICustomDialogClickHandler handler, String title, String message, boolean showNoButton) {
        super(context);
        mICustomDialogClickHandler = handler;
        mMessage = message;
        mTitle = title;
        mShowNoButton = showNoButton;
        mContext = context;
    }

    public void setNoButtonText(String text) {
        if (mBtnNo != null) {
            mBtnNo.setText(text);
        }
    }

    public void setYesButtonText(String text) {
        if (mBtnYes != null) {
            mBtnYes.setText(text);
        }
    }

    public void setMessageColor(int color) {
        if (mTextViewMessage != null) {
            mTextViewMessage.setTextColor(color);
        }
    }

    public void setMessageSpacing(float spacing) {
        if (mTextViewMessage != null) {
            mTextViewMessage.setLineSpacing(2f, spacing);
        }
    }

//    TODO need to create custom_dialogview and uncomment below part

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom);
        mBtnYes = (Button) findViewById(R.id.btn_yes);
        mBtnNo = (Button) findViewById(R.id.btn_no);
        mTextViewMessage = (TextView) findViewById(R.id.txt_message);
        mTextViewTitle = (TextView) findViewById(R.id.txt_title);

        mBtnYes.setOnClickListener(this);
        mBtnNo.setOnClickListener(this);
        if (!mShowNoButton)
            mBtnNo.setVisibility(View.GONE);
        if (mTitle != null)
            mTextViewTitle.setText(mTitle);
        else
            mTextViewTitle.setVisibility(View.GONE);

        mTextViewMessage.setText(mMessage);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        if(sYesButtonText != null){
            mBtnYes.setText(sYesButtonText);
        }

        // full screen(Width)
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                dismiss();
                if(mICustomDialogClickHandler != null) {
                    mICustomDialogClickHandler.onYesButtonClick();
                }
                break;
            case R.id.btn_no:
                dismiss();;
                if(mICustomDialogClickHandler != null) {
                    mICustomDialogClickHandler.onNoButtonClick();
                }
                break;
        }
        dismiss();
    }
}
