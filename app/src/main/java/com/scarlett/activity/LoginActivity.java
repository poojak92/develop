package com.scarlett.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.scarlett.R;
import com.scarlett.activity.base.BaseAcitivity;
import com.scarlett.helper.FontHelper;

import butterknife.BindView;

public class LoginActivity extends BaseAcitivity {

    @BindView(R.id.tv_sign_up)
    TextView mTvSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);


      //  SpannableString spannableStr = new SpannableString(getResources().getString(R.string.login_sign_up));
      //  ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
      //  spannableStr.setSpan(foregroundColorSpan, 10, 17, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      //  mTvSignup.setText(spannableStr);

        setLoginSpan();
    }

    public void startActivity(View view) {

        mRouter.startActivityClearTop(RegisterActivity.class);
    }

    public void setLoginSpan(){

        String singUpText = getResources().getString(R.string.login_sign_up);
        int signUpIndex = singUpText.indexOf("Sign up");

        SpannableStringBuilder spannableString = new SpannableStringBuilder(singUpText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                mRouter.startActivityClearTop(RegisterActivity.class);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ContextCompat.getColor(LoginActivity.this,R.color.colorPrimary));
                ds.setTypeface(FontHelper.getGothamMedium(LoginActivity.this));
                ds.setUnderlineText(false);
            }
        };

        spannableString.setSpan(clickableSpan, signUpIndex, singUpText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mTvSignup.setText(spannableString);
        mTvSignup.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
