package com.scarlett.Presenter;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.scarlett.Callback.IUpdateProfileCommunicator;
import com.scarlett.Fragment.MyAccountFragment;
import com.scarlett.Utils.imageutils.Compressor;
import com.scarlett.Utils.imageutils.ImageUtil;
import com.scarlett.activity.base.BaseBackstackManagerActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserProfilePresenter {

    BaseBackstackManagerActivity mRegisterActivity;
   // CommanUtils commanUtils;
    protected int mRequiredWidth = 600;
    protected byte[] imageBytes;
    IUpdateProfileCommunicator iUpdateProfileCommunicator;
    protected Uri outputFileUri;
    String base64String;

    public UserProfilePresenter(BaseBackstackManagerActivity mRegisterActivity, IUpdateProfileCommunicator iUpdateProfileCommunicator) {
        this.mRegisterActivity = mRegisterActivity;
        this.iUpdateProfileCommunicator = iUpdateProfileCommunicator;
    }

    public void doImageProcess(Intent data) {
        Uri selectedImageUri = ImageUtil.getSelectedImageUri(data, outputFileUri);
        // compress and save image
        if (selectedImageUri != null) {
            BitmapFactory.Options opt = ImageUtil.getBitmapOptions(mRegisterActivity, selectedImageUri);

            int imageHeight = opt.outHeight;
            int imageWidth = opt.outWidth;

            int requitedHeight = ((imageHeight * mRequiredWidth) / imageWidth);

            // resize image to 600 px in width.
            Bitmap createCompressedImage = new Compressor.Builder(mRegisterActivity)
                    .setMaxWidth(mRequiredWidth)
                    .setMaxHeight(requitedHeight)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .build()
                    .compressToBitmap(selectedImageUri);

            int MAX_SIZE = 200000; // 500 KB
            int StreamLen = 200001;
            int Quality = 85;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            while (StreamLen > MAX_SIZE) {
                Quality += -5;
                byteArrayOutputStream = new ByteArrayOutputStream();
                createCompressedImage.compress(Bitmap.CompressFormat.JPEG, Quality, byteArrayOutputStream);
                byte[] image = byteArrayOutputStream.toByteArray();
                StreamLen = image.length;
            }

            // get compressed image..
            imageBytes = byteArrayOutputStream.toByteArray();

            final Bitmap compressedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            mRegisterActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  //  DialogUtils.hideProgress();
                    iUpdateProfileCommunicator.onImageCompressed(compressedImage);

                }
            });

        }
    }


    public void openImageIntent() {
        // Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "devere" + File.separator);
        root.mkdirs();
        final String fname = "img_" + System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);
        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = mRegisterActivity.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }
        final Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        chooserIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        iUpdateProfileCommunicator.onIntentReady(chooserIntent, MyAccountFragment.YOUR_SELECT_PICTURE_REQUEST_CODE);
        // TODO: 13/2/19  //LifecycleHandler.sendSignal = false;
    }


}
