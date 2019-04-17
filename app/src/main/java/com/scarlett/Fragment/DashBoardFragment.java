package com.devere.lumina.fragment;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.devere.lumina.R;
import com.devere.lumina.activity.MPINActivity;
import com.devere.lumina.activity.base.BaseBackstackManagerActivity;
import com.devere.lumina.callbacks.communicators.INetworkCallCommunicator;
import com.devere.lumina.callbacks.communicators.fragmentcommunicators.FragmentPresenter;
import com.devere.lumina.callbacks.communicators.fragmentcommunicators.ILuminaCardCommunicator;
import com.devere.lumina.constants.AppConstants;
import com.devere.lumina.fragment.base.BaseFragment;
import com.devere.lumina.helper.Router;
import com.devere.lumina.model.apiresponse.BaseResponse;
import com.devere.lumina.model.singleton.MPINSingleton;
import com.devere.lumina.presenter.fragmentpresenter.LuminaCardPresenter;
import com.devere.lumina.constants.ApiRequestTag;
import com.devere.lumina.manager.ApiManager;
import com.devere.lumina.manager.BaseApiManager;
import com.devere.lumina.model.apiresponse.MPIN.UserMPIN;
import com.devere.lumina.roomdatabase.LuminaDatabase;
import com.devere.lumina.roomdatabase.entity.Settings;
import com.devere.lumina.roomdatabase.repositories.SettingRepository;
import com.devere.lumina.ui.callbacks.ICustomDialogClickHandler;
import com.devere.lumina.ui.customviews.CustomTextView;
import com.devere.lumina.utils.CommanUtils;
import com.devere.lumina.utils.DialogUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LuminaCardFragment extends BaseFragment implements View.OnClickListener,ILuminaCardCommunicator,INetworkCallCommunicator {
    public static String TAG = LuminaCardFragment.class.getName();

    @BindView(R.id.sw_activate_card)
    SwitchCompat mSwActivateCard;

    @BindView(R.id.sw_freeze_card)
    SwitchCompat mSwFreezeCard;

    SwitchCompat mSwitchedButton = null;

    @BindView(R.id.iv_card_security_right)
    ImageView ivCardSecurity;

    @BindView(R.id.iv_show_pin_right)
    ImageView ivShowPin;

    @BindView(R.id.cl_card_security)
    ConstraintLayout mShowCardSecurity;

    @BindView(R.id.cl_show_pin)
    ConstraintLayout mshowPin;

    @BindView(R.id.tv_activate_physical_card)
    CustomTextView mRequestActivatePhysicalcard;

    @BindView(R.id.tv_ready_to_use)
    CustomTextView mRequestActivateSubtitle;

    @BindView(R.id.cl_activate_request)
    ConstraintLayout mClActivateRequest;
    private boolean isCardRequested = false;
    private boolean isCardActivated = true;
    private LuminaDatabase luminaDatabase;

    private static final int REQUEST_PHYSICAL_CARD = 10;
    private static final int ACTIVATE_PHYSICAL_CARD = 11;
    private static final int FREEZE_CARD = 12;
    private LuminaCardPresenter luminaCardPresenter;
    boolean mIsSelected = false;
    String mPin = "";
    String mFreezValue ="";
    FragmentPresenter fragmentPresenter;
    String msg = "";
    public LuminaCardFragment() {
        // Required empty public constructor
    }

    public static LuminaCardFragment newInstance() {
        return new LuminaCardFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.fragment_lumina_card;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentPresenter = (FragmentPresenter) mParentActivity;
    }
    @Override
    protected void init() {
        mParentActivity.showWhiteTextToolbar(getString(R.string.app_name), getString(R.string.lumina_card_subtitle));
        luminaDatabase = LuminaDatabase.getInstance(mParentActivity);
        mSwActivateCard.setOnCheckedChangeListener(mCheckedChangeListener);
        mSwFreezeCard.setOnCheckedChangeListener(mCheckedChangeListener);
        ivCardSecurity.setOnClickListener(this);
        ivShowPin.setOnClickListener(this);
        mShowCardSecurity.setOnClickListener(this);
        mshowPin.setOnClickListener(this);
        luminaCardPresenter = new LuminaCardPresenter(this, this);
    }

    CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.sw_activate_card:
                    mSwitchedButton = mSwActivateCard;
                    msg = "";
                    if (!isCardRequested) {
                        if (isChecked) {
                            msg = getResources().getString(R.string.request_physical_card);
                            luminaCardPresenter.showRequestCardDeliveryAddress();
                        }

                    } else {
                        if (isChecked) {
                            msg = getResources().getString(R.string.activate_physical_card);
                            luminaCardPresenter.activatePhysicalCard();
                        }
                    }


                    break;

                case R.id.sw_freeze_card:
                    msg = "";
                    mSwitchedButton = mSwFreezeCard;
                    if (isChecked) {
                        msg = getResources().getString(R.string.freeze_card_confimation_message);
                    } else {
                        msg = getResources().getString(R.string.unfreeze_card_confimation_message);
                    }

                    showDialog(isChecked, msg, mSwFreezeCard);
                    break;

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_card_security_right:
                break;

            case R.id.cl_card_security:
                mParentActivity.doChangeFragment(CardSecurityFragment.newInstance(), CardSecurityFragment.TAG, true);
                break;

            case R.id.cl_show_pin:
                MPINSingleton.getInstance().setMpinCalledFor(AppConstants.MPIN.MPIN_REQUEST_AUTHENTICATE);
                new Router(mParentActivity).startActivityForResult(MPINActivity.class, AppConstants.MPIN.MPIN_REQUEST_AUTHENTICATE);

                break;
        }
    }

    //to change switch on/off
    private void setSwitchValue(SwitchCompat mSwitch, boolean status) {
        mSwitch.setOnCheckedChangeListener(null);
        mSwitch.setChecked(status);
        mSwitch.setOnCheckedChangeListener(mCheckedChangeListener);
    }


    //intially check freezeUnfreeze status
    private void checkFreezeUnfreezeStatus() {
        SettingRepository repository = SettingRepository.getInstance();
        Settings set = repository.get(AppConstants.CardSecurity.DataBaseCardSecurity.FREEZE_CARD, luminaDatabase);
        if (set == null) {
            setSwitchValue(mSwFreezeCard, false);
        } else {
            if (set.getValue().equals("True")) {
                setSwitchValue(mSwFreezeCard, true);
            } else {
                setSwitchValue(mSwFreezeCard, false);
            }
        }
    }

    //check card is requested or activated
    private void checkRequestActivate() {
        SettingRepository settingRepository = SettingRepository.getInstance();
        Settings set = settingRepository.get(AppConstants.CardSecurity.DataBaseCardSecurity.REQUEST_PHYSICAL_CARD, luminaDatabase);
        Settings set1 = settingRepository.get(AppConstants.CardSecurity.DataBaseCardSecurity.ACTIVATE_PHYSICAL_CARD, luminaDatabase);
        if (set == null && set1 == null) {
            mRequestActivatePhysicalcard.setText(R.string.physical_card_request);
            mRequestActivateSubtitle.setText(R.string.physical_card_sub_title);
        } else if (set.getValue().equals("True")) {
            isCardRequested = true;
            if (set1 != null && set1.getValue().equals("True")) {
                mClActivateRequest.setVisibility(View.GONE);
            } else {
                mRequestActivatePhysicalcard.setText(R.string.physical_card_title);
                mRequestActivateSubtitle.setText(R.string.physical_card_sub_title);
            }
        } else {
            mRequestActivatePhysicalcard.setText(R.string.physical_card_request);
            mRequestActivateSubtitle.setText(R.string.physical_card_sub_title);
        }
    }


    //show dialog on switch check
    private void showDialog(boolean status, String message, SwitchCompat switchCompat) {
        DialogUtils.showInvalidMessage(mParentActivity, message, getResources().getString(R.string.app_name), "Yes", new ICustomDialogClickHandler() {
            @Override
            public void onYesButtonClick() {

                if (switchCompat == mSwActivateCard) {
                    if (status) {
                        if (!isCardRequested) {
                            setValueInDb(REQUEST_PHYSICAL_CARD, "True");

                        } else {
                            setValueInDb(ACTIVATE_PHYSICAL_CARD, "True");
                        }
                    }
                } else if (switchCompat == mSwFreezeCard) {
                    if (status) {
                        mFreezValue = "True";
                        mIsSelected = true;
                        setValueInDb(FREEZE_CARD, "True");
                    } else {
                        mFreezValue = "False";
                        mIsSelected = false;
                        setValueInDb(FREEZE_CARD, "False");
                    }
                }
            }

            @Override
            public void onNoButtonClick() {
                setSwitchValue(switchCompat, !status);
            }
        }, true);
    }

    //set value in db
    private void setValueInDb(int flag, String value) {
        switch (flag) {
            case REQUEST_PHYSICAL_CARD:
                final SettingRepository repository = SettingRepository.getInstance();
                final Settings settings = repository.get(AppConstants.CardSecurity.DataBaseCardSecurity.REQUEST_PHYSICAL_CARD, luminaDatabase);
                if (settings == null) {
                    Settings se = new Settings(AppConstants.CardSecurity.DataBaseCardSecurity.REQUEST_PHYSICAL_CARD, value);
                    repository.insert(se, luminaDatabase);
                    isCardRequested = true;
                    mRequestActivatePhysicalcard.setText(R.string.physical_card_title);
                    mSwitchedButton.setOnCheckedChangeListener(null);
                    mSwitchedButton.setChecked(!mSwitchedButton.isChecked());
                    mSwitchedButton.setOnCheckedChangeListener(mCheckedChangeListener);
                }
                break;

            case ACTIVATE_PHYSICAL_CARD:
                final SettingRepository repository1 = SettingRepository.getInstance();
                final Settings settings1 = repository1.get(AppConstants.CardSecurity.DataBaseCardSecurity.ACTIVATE_PHYSICAL_CARD, luminaDatabase);
                Settings se = new Settings(AppConstants.CardSecurity.DataBaseCardSecurity.ACTIVATE_PHYSICAL_CARD, value);
                repository1.insert(se, luminaDatabase);
                mClActivateRequest.setVisibility(View.GONE);
                break;


            case FREEZE_CARD:
                luminaCardPresenter.doFreezCard(mIsSelected);
                break;
        }
    }


    @Override
    public BaseBackstackManagerActivity getParentActivity() {
        return mParentActivity;
    }

    @Override
    public void onPhysicalCardRequestSuccess() {

        setValueInDb(REQUEST_PHYSICAL_CARD, "True");

    }

    @Override
    public void onPhysicalCardRequestError(String msg) {

    }

    @Override
    public void setPhysicalSwitchStatusTo(boolean isChecked) {
        setSwitch(mSwActivateCard, isChecked);
    }

    @Override
    public void onActivateCardRequestSuccess() {
        if (!isCardRequested) {
            setValueInDb(REQUEST_PHYSICAL_CARD, "True");

        } else {
            setValueInDb(ACTIVATE_PHYSICAL_CARD, "True");
        }
    }

    @Override
    public void onActivateCardRequestError(String msg) {

    }

    @Override
    public void onSuccessNetworkCall() {
        InDBFreezeCard(mFreezValue);

    }

    @Override
    public void onErrorNetworkCall(String message) {
        setSwitchValue(mSwFreezeCard, !mIsSelected);
    }


    public void setSwitch(SwitchCompat switchCompat, boolean isChecked) {
        switchCompat.setOnCheckedChangeListener(null);
        switchCompat.setChecked(isChecked);
        switchCompat.setOnCheckedChangeListener(mCheckedChangeListener);
    }

    public void InDBFreezeCard(String value) {
        final SettingRepository setRepo = SettingRepository.getInstance();
        final Settings setFreeze = setRepo.get(AppConstants.CardSecurity.DataBaseCardSecurity.FREEZE_CARD, luminaDatabase);
        if (setFreeze == null) {
            Settings setF = new Settings(AppConstants.CardSecurity.DataBaseCardSecurity.FREEZE_CARD, value);
            setRepo.insert(setF, luminaDatabase);
        } else {
            setFreeze.setValue(value.equals("True") ? "True" : "False");
            setRepo.update(setFreeze, luminaDatabase);
        }
    }




    public interface IRequestCard {
        public void onCardRequestedSuccess();

        public void onCardError(String errorMessage);

    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentPresenter.passFragmentTag(LuminaCardFragment.TAG);
        checkRequestActivate();
        checkFreezeUnfreezeStatus();
    }
}