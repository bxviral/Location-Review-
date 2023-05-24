package com.example.locationreviewproject;

import static com.example.locationreviewproject.MyApplicationClass.a1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class FillingActivity extends AppCompatActivity {
    ImageView imgProf, imgSelect;
    EditText txtWriteDescription;
    Button btnSave, takePhoto, goGallery,realGm;
    BottomSheetDialog bsd;
    private static final int PICK_FROM_GALLERY = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    File finalFile;
    Uri tempUri;
    Bitmap bitmap;
    Intent intent5;
    ByteArrayOutputStream bytes;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling);
        imgProf = findViewById(R.id.imgProf);
        imgSelect = findViewById(R.id.imgSelect);
        takePhoto = findViewById(R.id.takePhoto);
        goGallery = findViewById(R.id.goGallery);
        btnSave = findViewById(R.id.btnSave);
        txtWriteDescription = findViewById(R.id.txtWriteDescription);
        realGm = findViewById(R.id.realGm);
        AddressData addressData = (AddressData) getIntent().getSerializableExtra("obj");

        realGm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?q=loc:" +addressData.getLatitude()  + "," +addressData.getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                a1.get(addressData.getIndex()).setDescription(String.valueOf(txtWriteDescription.getText()));
                a1.get(addressData.getIndex()).setPath(finalFile.getAbsolutePath());
                Pref.writeListInPref(FillingActivity.this,a1);

            }
        });
        if(a1.get(addressData.getIndex()).isSelected()){
            imgSelect.setImageResource(R.drawable.star);
        }
        else{
            imgSelect.setImageResource(R.drawable.unstar);
        }
        imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressData.isSelected()) {
                    imgSelect.setImageResource(R.drawable.unstar);
                    addressData.setSelected(false);
                    a1.get(addressData.getIndex()).setSelected(false);
                } else {
                    imgSelect.setImageResource(R.drawable.star);
                    addressData.setSelected(true);
                    a1.get(addressData.getIndex()).setSelected(true);
                }
            }
        });

        imgProf.setOnClickListener(view -> {
            bsd = new BottomSheetDialog(FillingActivity.this);
            View view1 = LayoutInflater.from(FillingActivity.this)
                    .inflate(R.layout.bottom_dialog_sheet, (LinearLayout) findViewById(R.id.sheet));
            bsd.setContentView(view1);
            bsd.show();

            takePhoto = bsd.findViewById(R.id.takePhoto);
            Objects.requireNonNull(takePhoto).setOnClickListener(view2 -> {
                if (checkPermission()) {
                    Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityIntent2.launch(iCamera);
                    bsd.dismiss();
                    Log.e("TAG", "Permission is granted: is called  ");
                    Log.e("TAG", "                                  ");
                } else {
                    requestPermission();
                    Log.e("TAG", "requestPermission : is called  ");
                    Log.e("TAG", "                                  ");
                }
            });

            goGallery = bsd.findViewById(R.id.goGallery);
            Objects.requireNonNull(goGallery).setOnClickListener(view22 -> {
                try {
                    if (ActivityCompat.checkSelfPermission(FillingActivity.this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(FillingActivity.this,
                                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // this will open the whole new gallery activity
                        startActivityIntent.launch(galleryIntent);
                        //startActivityForResult(galleryIntent,PICK_FROM_GALLERY);
//                        Intent iGALLERY = new Intent(MediaStore.ACTION_PICK_IMAGES); //this will open a gallery from bottom sheet dialog
//                        startActivityIntent.launch(iGALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bsd.dismiss();
            });
        });


    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Log.e("TAG", "Permission: not granted  ");
            Log.e("TAG", "                                  ");
            return false;
        }
        Log.e("TAG", "Permission: granted  ");
        Log.e("TAG", "                                  ");
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("TAG", "onRequestPermissionsResult is called  " + grantResults[0]);
        Log.e("TAG", "                                  ");
        Log.e("TAG", " request code " + requestCode);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("TAG", "PackageManager.PERMISSION_GRANTED of CAMERA is called  "
                            + PackageManager.PERMISSION_GRANTED);
                    Log.e("TAG", "                                  ");

                    Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityIntent2.launch(iCamera);
                    bsd.dismiss();

                } else {
                    showMessageOKCancel(
                            (dialog, which) -> {
                                Log.e("TAG", "Device settings is called ");
                                Log.e("TAG", "                                   ");
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            });
                }
                break;

            case PICK_FROM_GALLERY:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("TAG", "PICK_FROM_GALLERY is called");
                    Log.e("TAG", "                                   ");
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // this will open the whole new gallery activity
                    startActivityIntent.launch(galleryIntent);
                    //startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                }
                break;
        }
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) { //when onActivity result is called
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                Uri uri = data.getData();
//                imgProfilePicture.setImageURI(uri);
//            }
//        }
//    }


    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(FillingActivity.this)
                .setMessage("You need to allow access permissions")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    ActivityResultLauncher<Intent> startActivityIntent2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e("TAG", "startActivityIntent2 is called");
                    Log.e("TAG", "                                   ");

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        assert result.getData() != null;
                        Bundle bundle = result.getData().getExtras();

                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        imgProf.setImageBitmap(bitmap);

                        tempUri = getImageUri(getApplicationContext(), bitmap);
                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        finalFile = new File(getRealPathFromURI(tempUri));
                        Log.e("KKK", "finalFile: " + finalFile.getAbsolutePath());
                    }
                }
            });

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.e("TAG", "startActivityIntent is called");
                        Log.e("TAG", "                                   ");
                        Intent data = result.getData();
                        assert data != null;
                        Uri selectedImageUri = data.getData();

                        InputStream inputStream = null;
                        try {
                            inputStream = FillingActivity.this.getContentResolver().openInputStream(selectedImageUri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imgProf.setImageBitmap(bitmap);

                        tempUri = getImageUri(getApplicationContext(), bitmap);
                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        finalFile = new File(getRealPathFromURI(tempUri));
                        Log.e("KKK", "finalFile: " + finalFile.getAbsolutePath());
                    }
                }
            });

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "non", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

}


//
//Activity 2
//
//public class MainActivity2 extends AppCompatActivity implements ActivityToActivity {
//    ImageView imageView, imgProfilePicture2;
//    Button btnTakePhoto, btnTakeGallery, btnBack;
//    File finalFile;
//    Uri tempUri;
//    Bitmap bitmap;
//    Intent intent5;
//    ByteArrayOutputStream bytes;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//        imageView = findViewById(R.id.imgProfilePicture);
//        imgProfilePicture2 = findViewById(R.id.imgProfilePicture2);
//        btnTakePhoto = findViewById(R.id.btnTakePhoto);
//        btnTakeGallery = findViewById(R.id.btnTakeGallery);
//        btnTakePhoto.setOnClickListener(view -> {
//            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityIntent.launch(intent1);
//        });
//        btnTakeGallery.setOnClickListener(view -> {
//            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // this will open the whole new gallery activity
//            startActivityIntentGallery.launch(galleryIntent);
//        });
//        btnBack = findViewById(R.id.btnBack);
//
//        btnBack.setOnClickListener(view -> {
//
//            intent5 = new Intent(MainActivity2.this, MainActivity.class);
//            intent5.putExtra("path", finalFile.getAbsolutePath());
//            intent5.putExtra("byteArray", bytes.toByteArray());
//            intent5.putExtra("bitmapImage", bitmap);
//
//
//            startActivity(intent5);
//
//        });
//        //Package persistent Data folder mein folder create karan vaaste
////        File file = new File("/data/data/"+this.getPackageName()
////                +"/shared_prefs/"+this.getPackageName()+"ok.xml");
////        if(file.exists())
////            Log.e("SSS", "exist");
////        else{
////            file.mkdirs();
////            Log.e("SSS", "not exist");
////        }
////       File file3 = new File(getApplicationInfo().dataDir,"Creation_2"+"/inside_Creation");
////        Log.e("SSS", "path3 is : "+getApplicationInfo().dataDir );
////        if(file3.exists()){
////            Toast.makeText(this, "exist", Toast.LENGTH_SHORT).show();
////        }
////        else{
////            Log.e("SSS", "path3 is : "+Environment.getExternalStorageDirectory().getAbsolutePath() );
////            file3.mkdirs();
////        }
////
////        // inside mobile folders ke liye
////        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "VIRAL_MAIN5");
////        File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "VIRAL_MAIN_DCIM");
////        Log.e("SSS", "path is : "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() );
////        if(file.exists()){
////            Toast.makeText(this, "exist", Toast.LENGTH_SHORT).show();
////        }
////        else{
////            Log.e("SSS", "path1 is : "+Environment.getExternalStorageDirectory().getAbsolutePath() );
////            file.mkdirs();
////            file2.mkdirs();
////        }
////
////        if(file2.exists()){
////            Toast.makeText(this, "exist", Toast.LENGTH_SHORT).show();
////        }
////        else{
////            Log.e("SSS", "path2 is : "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() );
////            file2.mkdirs();
////        }
//
//
//    }
//
//    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        assert result.getData() != null;
//                        Bundle bundle = result.getData().getExtras();
//
//                        bitmap = (Bitmap) bundle.get("data");
//                        imageView.setImageBitmap(bitmap);
//
//                        tempUri = getImageUri(getApplicationContext(), bitmap);
//                        // CALL THIS METHOD TO GET THE ACTUAL PATH
//                        finalFile = new File(getRealPathFromURI(tempUri));
//                        Log.e("KKK", "finalFile: " + finalFile.getAbsolutePath());
//
////                        //first get the path
////                        String path = Saveme(bitmap, "image_name.jpg");
////                        //set it in your gallery
////                        phone(bitmap, "my image", "my image test for gallery save");
////                        Log.e("JJJ", "onActivityResult: " + "*my image(3).jpg");
////                        File imgFile = new  File(path);
////                        if(imgFile.exists()){
////                            Log.e("JJJ", "imgFile.exists() "+ imgFile.exists());
////                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////
////                            ImageView myImage = (ImageView) findViewById(R.id.imgProfilePicture2);
////
////                            myImage.setImageBitmap(myBitmap);
////                        }
//                    }
//                }
//            }
//    );
//    ActivityResultLauncher<Intent> startActivityIntentGallery = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        assert data != null;
//                        Uri selectedImageUri = data.getData();
//                        InputStream inputStream = null;
//                        try {
//                            inputStream = getContentResolver().openInputStream(selectedImageUri);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                        imgProfilePicture2.setImageBitmap(bitmap);
//                        tempUri = getImageUri(getApplicationContext(), bitmap);
//                        File finalFile2 = new File(getRealPathFromURI(tempUri));
//                        Log.e("PPP", "onActivityResult:  "+finalFile2 );
//                    }
//                }
//            }
//    );
//
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "non", null);
//        return Uri.parse(path);
//    }
//
//    public String getRealPathFromURI(Uri uri) {
//        String path = "";
//        if (getContentResolver() != null) {
//            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//            if (cursor != null) {
//                cursor.moveToFirst();
//                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                path = cursor.getString(idx);
//                cursor.close();
//            }
//        }
//        return path;
//    }
//
//
//    @Override
//    public void DataSend(String name) {
//        Log.e("MMM", "DataSend: " + name);
//
//    }
//}
//
//
//
//    }
//}