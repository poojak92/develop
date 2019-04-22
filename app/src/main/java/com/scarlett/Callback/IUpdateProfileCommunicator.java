package com.scarlett.Callback;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by rac on 13/2/19.
 */

public interface IUpdateProfileCommunicator {
    public void onImageCompressed(Bitmap compressedBitmap);
    public void onImageChanged();
    public void onImageNotChanged(String message);
    public void onIntentReady(Intent chooseIntent, int requestCode);
}
