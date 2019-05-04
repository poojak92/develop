package com.scarlett.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.mobsandgeeks.saripaar.Validator;
import com.scarlett.Callback.communicators.IRetrofitCommunicator;
import com.scarlett.Presenter.activitypresenter.RegisterActivityPresenter;
import com.scarlett.R;
import com.scarlett.Ui.CustomeView.CustomButton;
import com.scarlett.Ui.CustomeView.CustomEditText;
import com.scarlett.Utils.CommanUtils;
import com.scarlett.Utils.DialogUtils;
import com.scarlett.activity.base.BaseBackstackManagerActivity;
import com.scarlett.pogo.Register.RegisterResponse;

import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseBackstackManagerActivity implements IRetrofitCommunicator {//}, Validator.ValidationListener {

    @BindView(R.id.et_first_name)
    CustomEditText mEtFirstName;

    @BindView(R.id.et_email_address)
    CustomEditText mEtEmailAddress;

    @BindView(R.id.et_mobileno)
    CustomEditText mEtMobileNo;

    @BindView(R.id.et_password)
    CustomEditText mEtPassword;


    @BindView(R.id.btn_register)
    CustomButton mBtnRegister;

    @BindView(R.id.rl_register)
    RelativeLayout mRlRegister;

    @BindView(R.id.cv_register)
    CardView mCvRegister;


    @BindView(R.id.cl_bottom_view)
    ConstraintLayout mClBottomView;

    private Validator mValidator;

    RegisterActivityPresenter registerActivityPresenter;

    CommanUtils mCommanUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        setTreeObserver();


        mBtnRegister.setEnabled(true);
        registerActivityPresenter = new RegisterActivityPresenter(this);
        mCommanUtils = new CommanUtils(this);
      //  mValidator = new Validator(this);
      //  mValidator.setValidationListener(this);
       /* addRules();
        mEtFirstName.addTextChangedListener(nameChangedListener);
        mEtEmailAddress.addTextChangedListener(emailChangedListener);
        mEtMobileNo.addTextChangedListener(mobileChangedListener);
        mEtPassword.addTextChangedListener(passwordChangedListener);*/


    }

   /* private TextWatcher nameChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mValidator.validate();
        }
    };

    private TextWatcher emailChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mValidator.validate();
        }
    };

    private TextWatcher mobileChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mValidator.validate();
        }
    };

    private TextWatcher passwordChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mValidator.validate();
        }
    };
*/



    public void setTreeObserver() {
        mRlRegister.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mRlRegister.getWindowVisibleDisplayFrame(r);
                final int screenHeight = mRlRegister.getRootView().getHeight();
                final int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) {
                    // Keyboard opened
                    mClBottomView.setVisibility(View.GONE);
                    mCvRegister.setContentPadding(0, 0, 0, mBtnRegister.getHeight() + 0);

                } else {
                    // Keyboard closed
                    mClBottomView.setVisibility(View.VISIBLE);
                    mCvRegister.setContentPadding(0, 0, 0, 0);
                }
            }
        });
    }

    @OnClick(R.id.btn_register)
    public void startActivity() {
        mEtFirstName.setText("ll");
        mEtEmailAddress.setText("l@llll.com");
        mEtMobileNo.setText("9988778630");
        mEtPassword.setText("12345678");
        HashMap hashMap = new HashMap();
        hashMap.put("name",mEtFirstName.getText().toString());
        hashMap.put("email",mEtEmailAddress.getText().toString());
        hashMap.put("mobile",mEtMobileNo.getText().toString());
        hashMap.put("password",mEtPassword.getText().toString());
        hashMap.put("action","rg");
        Log.d("map: ", Arrays.asList(hashMap).toString());


        if(hashMap.size()>0) {
            if (CommanUtils.isNetworkAvailable()) {
                DialogUtils.showProgress(RegisterActivity.this, "");
                registerActivityPresenter.doRegisterUser(hashMap);
            } else {
                DialogUtils.hideProgress();
                DialogUtils.showNoInternetConnectionDialog(RegisterActivity.this);
            }
        }else {
            CommanUtils.showToast(RegisterActivity.this,"Please fill all details");

        }

       // mRouter.startActivityClearTop(HomeActivity.class);
    }

   /* public void addRules(){
        mValidator.put(mEtFirstName,new MinLength(1,2,getString(R.string.enter_first_name)));
        mValidator.put(mEtEmailAddress, new EmailRules(2, ""));
        mValidator.put(mEtMobileNo,new MinLength(3,10,getString(R.string.enter_mobileno)));
        mValidator.put(mEtPassword,new MinLength(4,5,getString(R.string.enter_password)));

    }

    @Override
    public void onValidationSucceeded() {
       // if(mValidator.isValidating()) {
            mBtnRegister.setEnabled(true);
      //  }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        mBtnRegister.setEnabled(false);
    }*/

    @Override
    public void onSuccess() {
        CommanUtils.showToast(RegisterActivity.this,getResources().getString(R.string.register_success));
        mRouter.startActivity(LoginActivity.class);
    }

    @Override
    public void onError(String error) {
       // DialogUtils.showNetworkErrorDialog(this);
        CommanUtils.showToast(RegisterActivity.this,error);


    }


    @Override
    public BaseBackstackManagerActivity getParentActivity() {
        return RegisterActivity.this;
    }


}
