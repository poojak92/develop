package com.scarlett.helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rac on 3/1/19.
 */

public class Router {

    AppCompatActivity mParentActivity;

    // TODO  PLEASE WRITE ALL SCELETON FOR STARTING ACTIVTIES HERE AFTER
    // TODO PLEASE DO NOT CALL ANY ACTIVITIES WITH THIER NAMES IN THIS BLOCK.

    // Sceleton block start

    public Router(AppCompatActivity appCompatActivity) {
        this.mParentActivity = appCompatActivity;
    }

    public void startActivity(Class nextClassActivity) {
        mParentActivity.startActivity(new Intent(mParentActivity, nextClassActivity).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

    }

    // Checks if called for finishing calling activity only if so finishes calling actvity
    // If not checks if called for finishing all activities in the back stack if so finishes all activites in the back stack
    public void startActivity(Class nextClassActivity, boolean finishParent, boolean finishAffinity) {
        if (finishParent) {
            mParentActivity.finish();
        } else if (finishAffinity) {
            mParentActivity.finishAffinity();
        }
        startActivity(nextClassActivity);
    }


    // For starting activity with extras
    public void startActivity(Class nextClassActivity, Bundle extras) {
        Intent intent = returnIntent(nextClassActivity);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(nextClassActivity);
    }

    // Starts activity for result
    // eg capturing images
    public void startActivityForResult(Class nextClassActivity, int requestCode) {
        startActivityForResult(nextClassActivity, null, requestCode);
    }

    // Starts activity for result with extras
    public void startActivityForResult(Class nextClassActivity, Bundle extras, int requestCode) {
        Intent i = returnIntent(nextClassActivity);
        if (extras != null) {
            i.putExtras(extras);
        }
        mParentActivity.startActivityForResult(i, requestCode);
    }

    // Returns intent object
    public Intent returnIntent(Class nextClassActivity) {
        Intent intent = new Intent(mParentActivity, nextClassActivity);
        return intent;
    }

    // Sceleton block end

    // TODO  PLEASE WRITE ALL ACTIVITY NAVIGATION CODE HERE AFTER

    public void startLoginActivity() {
        //startAcivity(MainActivity1.class, false, true);

    }

    public void startMain2Activity() {
       // startActivity(Main2Activity.class);
    }


//Route to Services

    public void startService(Class nextClassService) {
        mParentActivity.startService(new Intent(mParentActivity, nextClassService));
    }

    public void startServiceWithExtras(Class nextClassService, Bundle bundle) {
        mParentActivity.startService(new Intent(mParentActivity, nextClassService).putExtras(bundle));
    }



}
