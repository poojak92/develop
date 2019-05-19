package com.scarlett.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.scarlett.Model.Upload.UploadFile;
import com.scarlett.R;
import com.scarlett.SelectPhoto.GalleryActivity;
import com.scarlett.SelectPhoto.ShowSelectedPhoto_Adapter;
import com.scarlett.Utils.CommanUtils;
import com.scarlett.Utils.EqualSpacingItemDecoration;
import com.scarlett.activity.base.BaseToolbarActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateGalleryActivity extends BaseToolbarActivity {

    String TAG="CreateGalleryActivity= ";
    @BindView(R.id.rv_folder)
    RecyclerView mRvFolder;
    private static final int CustomGallerySelectId = 1;//Set Intent Id
    public static final String CustomGalleryIntentKey = "ImageArray";//Set Intent Key Value

    ShowSelectedPhoto_Adapter showSelectedPhoto_Adapter;
    private ArrayList<String> photodocPaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_creategallery);
        super.onCreate(savedInstanceState);

        showLeftButton(R.drawable.ic_toolbar_back, new ILeftClickListener() {
            @Override
            public void onLeftButtonClicked() {
                onBackPressed();
            }
        });
        showTitle(getResources().getString(R.string.text_create_gallery));


    }


    public void addPhotos(View view) {
        startActivityForResult(new Intent(CreateGalleryActivity.this, GalleryActivity.class), CustomGallerySelectId);

        // mRouter.startActivity(GalleryActivity.class);
    }

    protected void onActivityResult(int requestcode, int resultcode,
                                    Intent imagereturnintent) {
        super.onActivityResult(requestcode, resultcode, imagereturnintent);
        switch (requestcode) {
            case CustomGallerySelectId:
                if (resultcode == RESULT_OK) {
                    String imagesArray = imagereturnintent.getStringExtra(CustomGalleryIntentKey);//get Intent data
                    Log.d("imagesArray4: ", imagesArray);
                    //Convert string array into List by splitting by ',' and substring after '[' and before ']'
                    List<String> selectedImages = Arrays.asList(imagesArray.substring(1, imagesArray.length() - 1).split(", "));
                    loadGridView(new ArrayList<String>(selectedImages));//call load gridview method by passing converted list into arrayList
                }
                break;

        }
    }

    //Load GridView
    private void loadGridView(ArrayList<String> imagesArray) {
        photodocPaths.addAll(imagesArray);
        mRvFolder.setVisibility(View.VISIBLE);
        ArrayList<String> temp_imagesArray1 = new ArrayList<>();
        if (imagesArray.size() <= 2) {
            for (int i = 0; i < imagesArray.size(); i++) {
                temp_imagesArray1.add(imagesArray.get(i));
            }
        }
        if (imagesArray.size() > 2) {
            for (int i = 0; i < 3; i++) {
                temp_imagesArray1.add(imagesArray.get(i));
            }
        }
        showSelectedPhoto_Adapter = new ShowSelectedPhoto_Adapter(CreateGalleryActivity.this, temp_imagesArray1, imagesArray);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateGalleryActivity.this, 3);
        mRvFolder.addItemDecoration(new EqualSpacingItemDecoration(20));
        mRvFolder.addItemDecoration(new EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.HORIZONTAL));
        mRvFolder.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        mRvFolder.setAdapter(showSelectedPhoto_Adapter);
    }

    public void uploadImage(View view) {
       /* action:a_u_pt
        post_id:-1
        user_id:35
        post_title:pk test 1
        post_desc:testing  is done
        post_media_type:image
        post_type:r
        post_action:new*/
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("action", "a_u_pt");
        builder.addFormDataPart("post_id", "-1");
        builder.addFormDataPart("user_id", "35");
        builder.addFormDataPart("post_title", "image upload testing");
        builder.addFormDataPart("post_desc", "image upload testing desc");
        builder.addFormDataPart("post_media_type", "image");
        builder.addFormDataPart("post_type", "r");
        builder.addFormDataPart("post_action", "new");

        builder.setType(MultipartBody.FORM);

        if (photodocPaths.size() > 0) {
            for (int i = 0; i < photodocPaths.size(); i++) {
                File file = new File(photodocPaths.get(i));
                builder.addFormDataPart("images[]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
            // File file = new File(tempphotoPaths.get(0));
            // builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        MultipartBody requestBody = builder.build();
        UploadFile.get_UploadFile(requestBody, CreateGalleryActivity.this, TAG, new UploadFile.upload_response() {
            @Override
            public void temp_onResponse(boolean temp_responce) {
                if (temp_responce) {
                    new CommanUtils(CreateGalleryActivity.this).showToast(CreateGalleryActivity.this, "Post added successfully.");
                } else {
                    new CommanUtils(CreateGalleryActivity.this).showToast(CreateGalleryActivity.this, "Post added successfully.");
                }
            }
        });
    }
}
