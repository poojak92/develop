package com.scarlett.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.scarlett.Callback.Fragment.FragmentPresenter;
import com.scarlett.Callback.IUpdateProfileCommunicator;
import com.scarlett.Callback.IPermissionCommunicator;
import com.scarlett.Fragment.base.BaseFragment;
import com.scarlett.Manager.PermissionManager;
import com.scarlett.Model.ProfileItem;
import com.scarlett.Presenter.UserProfilePresenter;
import com.scarlett.R;
import com.scarlett.activity.AboutUsActivity;
import com.scarlett.activity.FollwersActivity;
import com.scarlett.activity.MyEarningActivity;
import com.scarlett.activity.RechargeActivity;
import com.scarlett.activity.TransactionActivity;
import com.scarlett.activity.WalletActivity;
import com.scarlett.activity.WithdrawActivity;
import com.scarlett.adapter.ProfileAdapter;
import com.scarlett.constants.AppConstants;

import java.util.ArrayList;

import butterknife.BindView;

import static com.scarlett.constants.AppConstants.RequestCodes.CAMERA_PERMISSION;
import static com.scarlett.constants.AppConstants.RequestCodes.PERMISSIONS_CAMERA_AND_STORAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends BaseFragment implements IUpdateProfileCommunicator, IPermissionCommunicator,ProfileAdapter.IonSelectionListener {
    public static String TAG = MyAccountFragment.class.getName();
    String[] Menu = {
            "Wallet",
            "My earn",
            "Recharge",
            "Withdraw",
            "Transaction",
            "Followers",
            "Share",
            "About us",
            "Logout"
    };

    int[] Menu_imageId = {
            R.drawable.ic_wallet,
            R.drawable.ic_earn,
            R.drawable.ic_recharge,
            R.drawable.ic_withdraw,
            R.drawable.ic_trans,
            R.drawable.ic_follower,
            R.drawable.ic_share1,
            R.drawable.ic_about,
            R.drawable.ic_logout
    };

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.img_back)
    ImageView mImgback;

    @BindView(R.id.iv_thumbProfile)
    ImageView mIvthumbProfile;

    private UserProfilePresenter userProfilePresenter;

    ProfileItem profileItem;
    ArrayList<ProfileItem> profileItems;

    FragmentPresenter fragmentPresenter;

    public static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 10;


    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.fragment_account;
    }

    Uri outputFileUri;
    ProfileAdapter profileAdapter;
    @Override
    protected void init() {
        initTollbar();
        userProfilePresenter = new UserProfilePresenter(mParentActivity,this);
        onClick();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentPresenter = (FragmentPresenter) mParentActivity;
    }

    public void setProfileItem() {
        profileItems = new ArrayList<>();
        for (int i = 0; i < Menu.length; i++) {
           /* if (Menu[i].equals("Wallet Balance")) {
                profileItem = new ProfileItem(Menu[i], Menu_imageId[i], "200");
            } else {
                profileItem = new ProfileItem(Menu[i], Menu_imageId[i], "");
            }*/
            profileItem = new ProfileItem(Menu[i], Menu_imageId[i], "");

            profileItems.add(profileItem);
        }

        //creating recyclerview adapter
        profileAdapter = new ProfileAdapter(profileItems,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mParentActivity,4);
        mRecyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        mRecyclerView.setAdapter(profileAdapter);
    }

    public void initTollbar() {
        mImgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentActivity.onBackPressed();
            }
        });
    }
    public void onClick(){
        mIvthumbProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    private void checkPermission() {
        int Permission1 = PermissionManager.isPermission(mParentActivity, Manifest.permission.CAMERA);
        int Permission2 = PermissionManager.isPermission(mParentActivity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (Permission1 == AppConstants.Permission.GRANTED && Permission2 == AppConstants.Permission.GRANTED) {
           userProfilePresenter.openImageIntent();
        } else if (Permission1 == AppConstants.Permission.DENIED && Permission2 == AppConstants.Permission.DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_CAMERA_AND_STORAGE);
        } else if (Permission1 == AppConstants.Permission.PERMISSION_NEVER_ASK) {

        }
    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? outputFileUri : data.getData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {

        switch (requestCode) {
            case YOUR_SELECT_PICTURE_REQUEST_CODE:
                // TODO: 13/2/19   //  LifecycleHandler.sendSignal = true;
                if (resultCode == getActivity().RESULT_OK) {
                    if (getPickImageResultUri(data) != null) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                userProfilePresenter.doImageProcess(data);

                            }
                        });
                    } else {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                userProfilePresenter.doImageProcess(null);

                            }
                        });
                    }
                    break;


                }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentPresenter.passFragmentTag(MyAccountFragment.TAG);
        setProfileItem();
    }

    @Override
    public void onImageCompressed(Bitmap compressedBitmap) {
        mIvthumbProfile.setImageBitmap(compressedBitmap);
    }

    @Override
    public void onImageChanged() {

    }

    @Override
    public void onImageNotChanged(String message) {

    }

    @Override
    public void onIntentReady(Intent chooseIntent, int requestCode) {
        startActivityForResult(chooseIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_CAMERA_AND_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (PermissionManager.isPermission(mParentActivity, Manifest.permission.CAMERA) == 0) {
                    userProfilePresenter.openImageIntent();
                }
            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                boolean isDenied = PermissionManager.isRequestPermissionRationale(mParentActivity, Manifest.permission.CAMERA);
                String permissionRequiredMessage = mParentActivity.getString(R.string.camera_profile_pic_permission_alert);
                int flag = AppConstants.Permission.DENIED;
                if (isDenied) {
                    flag = AppConstants.Permission.DENIED;
                } else {
                    flag = AppConstants.Permission.PERMISSION_NEVER_ASK;
                }
                PermissionManager.showPermissionRequiredMessage(this, flag, permissionRequiredMessage, mParentActivity, CAMERA_PERMISSION, permissions);
            }
        }
    }

    @Override
    public void onRequestForPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
    }

    @Override
    public void onItemSelected(ProfileItem profileItem, int selectedIndex) {

        String item_name = profileItem.getTitle();
        if(item_name.equals("Wallet")){
            mParentActivity.getRouter().startActivity(WalletActivity.class);
        }else  if(item_name.equals("My earn")){
            mParentActivity.getRouter().startActivity(MyEarningActivity.class);
        }else  if(item_name.equals("Recharge")){
            mParentActivity.getRouter().startActivity(RechargeActivity.class);
        }else  if(item_name.equals("Withdraw")){
            mParentActivity.getRouter().startActivity(WithdrawActivity.class);
        }else  if(item_name.equals("Transaction")){
            mParentActivity.getRouter().startActivity(TransactionActivity.class);
        }else  if(item_name.equals("Followers")){
            mParentActivity.getRouter().startActivity(FollwersActivity.class);
        }else  if(item_name.equals("Share")){
            share();
        }else  if(item_name.equals("About us")){
            mParentActivity.getRouter().startActivity(AboutUsActivity.class);
        }else  if(item_name.equals("Logout")){

        }

    }

    @Override
    public AppCompatActivity getParentActivity() {
        return mParentActivity;
    }
    private void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }
}