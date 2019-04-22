package com.scarlett.Utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.scarlett.R;
import com.scarlett.Ui.Callback.ICustomDialogClickHandler;
import com.scarlett.Ui.CustomeView.CustomDialog;


// Class to handle all custom dialogs and progress bars actions
public class DialogUtils {

    private static Context mContext;
    private static ProgressDialog progressDialog;

    /// ******************  TODO - PLEASE WRITE CUSTOM DIALOG RELATED STUFFS HERE AFTER


    public DialogUtils(Context context) {
        mContext = context;
    }

   /* // For showing dialog for api response caught in onErrorResponse of volley request.
    public static void showNetworkErrorDialog(final AppCompatActivity context, final String yesButtonText, final ICustomDialogClickHandler iCustomDialogClickHandler){
        String title = context.getString(R.string.default_dialog_header_title);
        String message = context.getString(R.string.network_error_message);
        CustomDialog customDialog = new CustomDialog(context,iCustomDialogClickHandler,title,message,false);
        customDialog.show();
        customDialog.setYesButtonText(yesButtonText);
    }

    public static void showNetworkErrorDialog(final AppCompatActivity context){
        String title = context.getString(R.string.default_dialog_header_title);
        String message = context.getString(R.string.network_error_message);
        CustomDialog customDialog = new CustomDialog(context,null,title,message,false);
        customDialog.show();
        customDialog.setYesButtonText(context.getString(R.string.dialog_button_ok));
    }

    public static void showNoInternetConnectionDialog(final AppCompatActivity context){
        String title = context.getString(R.string.default_dialog_header_title);
        String message = context.getString(R.string.network_error_message);
        CustomDialog customDialog = new CustomDialog(context, new ICustomDialogClickHandler() {
            @Override
            public void onYesButtonClick() {
                context.finishAffinity();
            }

            @Override
            public void onNoButtonClick() {

            }
        }, title, message, false);
        customDialog.show();
    }*/

    public static void showInvalidMessage(Context activity,String msg, String header,String buttonText,ICustomDialogClickHandler handler,boolean showNoButton){
        final CustomDialog customDialog = new CustomDialog(activity,handler,header,msg,showNoButton);
        customDialog.show();
        customDialog.setYesButtonText(buttonText);
        customDialog.setCancelable(false);
    }

    public static void showInvalidMessage(AppCompatActivity activity, String title, String message, String buttonText){
        final CustomDialog customDialog = new CustomDialog(activity,title,message,buttonText);
        customDialog.show();
    }


  /*  public void showConfirmationDialog(String message,ICustomDialogClickHandler iCustomDialogClickHandler){
        String title = mContext.getString(R.string.dialog_title_lumina);
        String yesButtonText = mContext.getString(R.string.dialog_button_yes);
        String noButtonText = mContext.getString(R.string.dialog_button_cancel);
        final CustomDialog confirmationDialog = new CustomDialog(mContext,iCustomDialogClickHandler,title,message,true);
        confirmationDialog.show();
        confirmationDialog.setCancelable(false);
        confirmationDialog.setYesButtonText(yesButtonText);
        confirmationDialog.setNoButtonText(noButtonText);
    }*/


   /* public static void showActivateDialog(AppCompatActivity activity,String msg, String header,String buttonText,ICustomDialogClickHandler handler,boolean showNoButton){
        final PhysicalCardDialog customDialog = new PhysicalCardDialog(activity);
        customDialog.show();
        customDialog.setYesButtonText(buttonText);
        customDialog.setCancelable(false);
    }*/


    /// ******************  TODO - WRITE PROGRESS BAR RELATED STUFFS HERE AFTER

   /* public static void createProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (mContext == null) {
            ExceptionManager.logException(new NullValueException("In class "+DialogUtils.class.getName()+" mContext is null in method createProgressDialog() while creating progress dialog"));
            return;
        }

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    public static void showProgress(final Context context,final String message) {
        mContext = context;
        createProgressDialog();

        if(progressDialog == null){
            ExceptionManager.logException(new NullValueException("In class "+DialogUtils.class.getName()+" progressDialog is null in method showProgress()"));
            return;
        }

        if (progressDialog.isShowing() == false) {
            try {
                progressDialog.setMessage(message);
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    public static void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing() == true) {
            progressDialog.dismiss();
        }
        progressDialog = null;
        mContext = null;
    }


}
