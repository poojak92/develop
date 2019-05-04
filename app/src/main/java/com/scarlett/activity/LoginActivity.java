package com.scarlett.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.scarlett.Callback.communicators.IRetrofitCommunicator;
import com.scarlett.Presenter.activitypresenter.LoginActivityPresenter;
import com.scarlett.R;
import com.scarlett.Ui.CustomeView.CustomEditText;
import com.scarlett.Utils.CommanUtils;
import com.scarlett.Utils.DialogUtils;
import com.scarlett.activity.base.BaseAcitivity;
import com.scarlett.activity.base.BaseBackstackManagerActivity;
import com.scarlett.helper.FontHelper;

import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseBackstackManagerActivity implements IRetrofitCommunicator {

    @BindView(R.id.tv_sign_up)
    TextView mTvSignup;

    @BindView(R.id.et_email_address)
    CustomEditText mEtEmailAddress;


    @BindView(R.id.et_password)
    CustomEditText mEtPassword;

    LoginActivityPresenter loginActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);

        loginActivityPresenter =new LoginActivityPresenter(this);

      //  SpannableString spannableStr = new SpannableString(getResources().getString(R.string.login_sign_up));
      //  ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
      //  spannableStr.setSpan(foregroundColorSpan, 10, 17, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      //  mTvSignup.setText(spannableStr);

        setLoginSpan();
    }

    @OnClick(R.id.btn_login)
    public void startActivity() {

        mEtEmailAddress.setText("l@llll.com");
        mEtPassword.setText("12345678");
        HashMap hashMap = new HashMap();
        hashMap.put("email",mEtEmailAddress.getText().toString());
        hashMap.put("password",mEtPassword.getText().toString());
        hashMap.put("action","lg");
        Log.d("map: ", Arrays.asList(hashMap).toString());


        if(hashMap.size()>0) {
            if (CommanUtils.isNetworkAvailable()) {
                DialogUtils.showProgress(LoginActivity.this, "");
                loginActivityPresenter.doLoginUser(hashMap);
            } else {
                DialogUtils.hideProgress();
                DialogUtils.showNoInternetConnectionDialog(LoginActivity.this);
            }
        }else {
            CommanUtils.showToast(LoginActivity.this,"Please fill all details");
        }
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

    @Override
    public void onSuccess() {
        mRouter.startActivity(HomeActivity.class);
    }

    @Override
    public void onError(String error) {
        CommanUtils.showToast(LoginActivity.this,error);

    }

    @Override
    public BaseBackstackManagerActivity getParentActivity() {
        return LoginActivity.this;
    }
}
