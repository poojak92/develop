package com.scarlett.SelectPhoto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.scarlett.R;
import com.scarlett.activity.CreateGalleryActivity;
import com.scarlett.activity.base.BaseToolbarActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * Created by deepshikha on 20/3/17.
 */

public class GalleryActivity extends BaseToolbarActivity {
    public static ArrayList<Model_images> al_images = new ArrayList<>();
    boolean boolean_folder;
    SelectPhoto_Adapter obj_adapter;
    GridView gv_folder;
    private static final int REQUEST_PERMISSIONS = 100;
    private int  CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/Scarlet";
    private static final int CustomGallerySelectId = 1;//Set Intent Id
    public static int selectpos;
    public static final String CustomGalleryIntentKey = "ImageArray";//Set Intent Key Value
    private static ArrayList<String> galleryImageUrls;

    @BindView(R.id.btn_next)
    Button mBtnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_selectphoto);
        super.onCreate(savedInstanceState);

        showLeftButton(R.drawable.ic_toolbar_back, new ILeftClickListener() {
            @Override
            public void onLeftButtonClicked() {
                onBackPressed();
            }
        });
        showTitle(getResources().getString(R.string.select_photo));

        gv_folder = (GridView)findViewById(R.id.gv_folder);

        gv_folder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              /*  if(!obj_adapter.getItem(i).getStr_folder().equals("0")) {
                    if(obj_adapter.getItem(i).getAl_imagepath().size() != 1) {
                        Log.d("int_pos",""+i);
                        selectpos = i;
                        startActivityForResult(new Intent(GalleryActivity.this, SelectPhotoActivity.class), CustomGallerySelectId);

                      *//*  Intent intent = new Intent(getApplicationContext(), SelectPhotoActivity.class);
                        intent.putExtra("value", i);
                        startActivity(intent);*//*
                      //  finish();
                    }else {
                        *//*ArrayList<String> selectedItems = new ArrayList<>();
                        selectedItems.add(obj_adapter.getItem(i).getStr_folder());

                        //Send back result to CreateGalleryActivity with selected images
                        Intent intent = new Intent();
                        intent.putExtra(CreateGalleryActivity.CustomGalleryIntentKey, selectedItems.toString());//Convert Array into string to pass data
                        Log.d("imagesArray1: ",selectedItems.toString());

                        setResult(RESULT_OK, intent);//Set result OK
                        finish();//finish activity*//*
                    }
                }else {
                    takePhotoFromCamera();
                }*/
            }
        });




        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(GalleryActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(GalleryActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(GalleryActivity.this,
                    Manifest.permission.CAMERA))) {

            } else {
                ActivityCompat.requestPermissions(GalleryActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.CAMERA},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
            fetchGalleryImages();
        }



    }
    public void showSelectButton() {
        ArrayList<String> selectedItems = obj_adapter.getCheckedItems();
        if (selectedItems.size() > 0) {
            mBtnNext.setText("Image selected: "+selectedItems.size());
            mBtnNext.setVisibility(View.VISIBLE);
        } else
            mBtnNext.setVisibility(View.GONE);

    }

    public void selectedPhoto(View view) {
        ArrayList<String> selectedItems = obj_adapter.getCheckedItems();

        //Send back result to CreateGalleryActivity with selected images
        Intent intent = new Intent();
        intent.putExtra(GalleryActivity.CustomGalleryIntentKey, selectedItems.toString());//Convert Array into string to pass data
        Log.d("imagesArray1: ",selectedItems.toString());

        setResult(RESULT_OK, intent);//Set result OK
        finish();//finish activity

    }


    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void fetchGalleryImages() {
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};//get all columns of type images
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;//order data by date
        Cursor imagecursor = managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");//get all data in Cursor by sorting in DESC order

        galleryImageUrls = new ArrayList<String>();//Init array


        //Loop to cursor count
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);//get column index
            galleryImageUrls.add(imagecursor.getString(dataColumnIndex));//get Image from column index
            System.out.println("Array path" + galleryImageUrls.get(i));
        }

        obj_adapter = new SelectPhoto_Adapter(GalleryActivity.this, galleryImageUrls, true);
        gv_folder.setAdapter(obj_adapter);
    }

   /* public ArrayList<Model_images> fn_imagespath() {

        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        Model_images obj_model1 = new Model_images();
        obj_model1.setStr_folder("0");
        al_images.add(obj_model1);


        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < al_images.size(); i++) {
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }
            if (boolean_folder) {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                Model_images obj_model = new Model_images();
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAl_imagepath(al_path);
                al_images.add(obj_model);
            }


        }


     *//*   for (int i = 0; i < al_images.size(); i++) {
            Log.e("FOLDER", al_images.get(i).getStr_folder());
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", al_images.get(i).getAl_imagepath().get(j));
            }
        }*//*
        obj_adapter = new Gallery_adapter(getApplicationContext(),al_images);
        gv_folder.setAdapter(obj_adapter);
        return al_images;
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fetchGalleryImages();                    } else {
                        Toast.makeText(GalleryActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case CustomGallerySelectId:
                String imagesArray = data.getStringExtra(CustomGalleryIntentKey);//get Intent data
                Log.d("imagesArray2: ",imagesArray);
                Intent intent = new Intent();
                intent.putExtra(CreateGalleryActivity.CustomGalleryIntentKey, imagesArray);//Convert Array into string to pass data
                Log.d("imagesArray3: ",imagesArray);

                setResult(RESULT_OK, intent);//Set result OK
                finish();//finish activity
                break;

            case RESULT_CANCELED:
                return;
        }
       if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            // imageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(GalleryActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }

    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}
