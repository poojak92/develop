package com.scarlett.Manager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;


import com.scarlett.Callback.IPermissionCommunicator;
import com.scarlett.R;
import com.scarlett.Ui.Callback.ICustomDialogClickHandler;
import com.scarlett.Utils.DialogUtils;
import com.scarlett.constants.AppConstants;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static com.scarlett.constants.AppConstants.Permission.DENIED;
import static com.scarlett.constants.AppConstants.Permission.PERMISSION_NEVER_ASK;


/**
 * Created by rac on 1/1/19.
 */
// class to handle run-time permissions
public class PermissionManager {


    //Getting Permission status
    public static int isPermission(AppCompatActivity activity, String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            return DENIED;
        }
        return AppConstants.Permission.GRANTED;
    }

    //if Permission status is denied, request Permission to user in
    public static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        requestPermissions(activity, permissions, requestCode);
    }

    /* shouldShowRequestPermissionRationale() function which returns true if the app has requested
     this permission previously and the user denied the request. If the user turned down the permission
      request in the past and chose the Don't ask again option, this method returns false.
     */
    //to see if user checked Never Ask again(method returns false only if the user selected Never ask again)
    public static boolean isRequestPermissionRationale(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }


    // Show dialog to user if user denies the permission or click on never ask again
    public static void showPermissionRequiredMessage(final IPermissionCommunicator permissionCommunicator, final int flag, final String message, final AppCompatActivity activity, final int requestCode, final String[] permissions) {
        DialogUtils dialogUtils = new DialogUtils(activity);
        dialogUtils.showInvalidMessage(activity, message, activity.getString(R.string.permission_request_header), activity.getString(R.string.dialog_button_ok), new ICustomDialogClickHandler() {
            @Override
            public void onYesButtonClick() {
                switch (flag) {
                    case PERMISSION_NEVER_ASK:
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                        intent.setData(uri);
                        activity.startActivity(intent);
                        break;
                    case DENIED:
                        permissionCommunicator.onRequestForPermission();
                        break;
                }

            }

            @Override
            public void onNoButtonClick() {

            }
        }, true);

    }
}
