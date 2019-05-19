package com.scarlett.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.scarlett.Callback.communicators.IRetrofitCommunicator;
import com.scarlett.Presenter.activitypresenter.LoginActivityPresenter;
import com.scarlett.R;
import com.scarlett.Ui.CustomeView.CustomButton;
import com.scarlett.Ui.CustomeView.CustomEditText;
import com.scarlett.Utils.CommanUtils;
import com.scarlett.Utils.DialogUtils;
import com.scarlett.activity.base.BaseBackstackManagerActivity;
import com.scarlett.constants.AppConstants;
import com.scarlett.helper.FontHelper;
import com.scarlett.manager.SharedPreferencesManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseBackstackManagerActivity implements IRetrofitCommunicator {

    @BindView(R.id.tv_sign_up)
    TextView mTvSignup;

    @BindView(R.id.et_email_address)
    CustomEditText mEtEmailAddress;


    @BindView(R.id.et_password)
    CustomEditText mEtPassword;

    @BindView(R.id.btn_fb)
    CustomButton mBtnFb;

    LoginActivityPresenter loginActivityPresenter;

    @BindView(R.id.fb_login_button)
    LoginButton fb_login_button;
    CallbackManager callbackManager;
    String Authenticationid, fbemail, fblastname, fbfirsrname, personName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        super.onCreate(savedInstanceState);
        getKeyHash(LoginActivity.this);
        loginActivityPresenter =new LoginActivityPresenter(this);

      //  SpannableString spannableStr = new SpannableString(getResources().getString(R.string.login_sign_up));
      //  ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
      //  spannableStr.setSpan(foregroundColorSpan, 10, 17, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      //  mTvSignup.setText(spannableStr);

        setLoginSpan();
        facebook_login();
    }

    @OnClick(R.id.btn_login)
    public void startActivity() {

      /*  mEtEmailAddress.setText("l@p.com");
        mEtPassword.setText("12345678");*/
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
           new CommanUtils(LoginActivity.this).showToast(LoginActivity.this,"Please fill all details");
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

        SharedPreferencesManager.storeBoolean(AppConstants.Login.USERLOGIN,true);
        mRouter.startActivityClearTop(HomeActivity.class,true,true);
        mEtEmailAddress.setText("");
        mEtPassword.setText("");
        DialogUtils.hideProgress();

    }

    @Override
    public void onError(String error) {
       new CommanUtils(LoginActivity.this).showToast(LoginActivity.this,error);

    }

    @Override
    public BaseBackstackManagerActivity getParentActivity() {
        return LoginActivity.this;
    }

    //facebooklogin
    public static String getKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            System.out.println("Package Name=" + context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                System.out.println("Key Hash=" + key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            System.out.println("Name not found" + e1.toString());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such an algorithm" + e.toString());
        } catch (Exception e) {
            System.out.println("Exception" + e.toString());
        }

        return key;
    }

    public void facebook_login() {
        fb_login_button.setReadPermissions("user_friends");
        fb_login_button.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        callbackManager = CallbackManager.Factory.create();
        mBtnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fb_login_button.performClick();

               /* if (InternetConnection.isNetworkAvailable(LoginActivity.this)) {
                    HideKeybord.hideKeyboard(LoginActivity.this);
                    if (Constants.fb_login_flag) {
                        PrefsHelper.getPrefsHelper().savePref(PrefsHelper.LOGIN, false);
                        PrefsHelper.getPrefsHelper().savePref(PrefsHelper.CUSTOMER_ID, "0");
                        LoginManager.getInstance().logOut();
                        Constants.fb_login_flag = false;
                    }

                    fb_login_button.performClick();
                } else {
                    HideKeybord.hideKeyboard(LoginActivity.this);
                    snackbarToast.ShowToast(rl_login, getResources().getString(R.string.internet_connection));

                }*/


            }
        });


        fb_login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("facebook", response.toString());

                                setProfileToView(object);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() { }

            @Override
            public void onError(FacebookException exception) {
                new CommanUtils(LoginActivity.this).showToast(LoginActivity.this, "error to Login Facebook");
            }
        });


    }

    private void setProfileToView(JSONObject jsonObject) {
        try {
            personName = (jsonObject.getString("name"));
            System.out.println("FacebookFullname" + personName);
            if (personName.split("\\w+").length > 1) {
                fblastname = personName.substring(personName.lastIndexOf(" ") + 1);
                fbfirsrname = personName.substring(0, personName.lastIndexOf(' '));
                System.out.println("FacebookFirstname info is :" + fbfirsrname);
                System.out.println("FacebookLastname info is :" + fblastname);
            } else {
                fbfirsrname = personName;
                System.out.println("Firstname info is1 :" + fbfirsrname);
            }
            String profile_image = String.valueOf((ProfilePictureView.NORMAL));
            System.out.println("FullnameImage: " + profile_image);

            Authenticationid = jsonObject.getString("id");
            System.out.println("FacebookAuthenticationid: " + Authenticationid);

            if (jsonObject.has("email")) {
                fbemail = jsonObject.getString("email");
            } else {
                fbemail = "";
            }
            System.out.println("FacebookEmail" + fbemail);
/*
            Constants.fb_login_flag=true;
            if (fbemail.equals("")) {
                getFacebookLogin_Withoutemail(Authenticationid);
            } else {
                if (InternetConnection.isNetworkAvailable(LoginActivity.this)) {
                    getFacebookLogin(fbfirsrname, fblastname, fbemail, Authenticationid);
                }
                else
                {
                    HideKeybord.hideKeyboard(LoginActivity.this);
                    snackbarToast.ShowToast(rl_login, getResources().getString(R.string.internet_connection));
                }
            }
*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
