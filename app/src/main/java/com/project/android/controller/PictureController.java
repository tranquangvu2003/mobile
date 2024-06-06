package com.project.android.controller;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

public class PictureController {
    FirebaseStorage storage =FirebaseStorage.getInstance();
    StorageReference rfr = storage.getReferenceFromUrl("gs://quanlyquancom-aac55.appspot.com");
    ImageView imageView;
    String path;
    String linkPath;

    public PictureController() {
    }

    public PictureController(ImageView imageView) {
        this.imageView = imageView;
    }

    public PictureController(String path) {
        this.path = path;
    }

    public UploadTask uploadFileFromCamera(){
        final String[] _linkPath = {""};
        boolean rs = false;
        Calendar cl = Calendar.getInstance();
        StorageReference mountainsRef = rfr.child("Image"+cl.getTimeInMillis()+".PNG");
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        return uploadTask;
    }
    public UploadTask uploadFileFromFile(){
        Calendar cl = Calendar.getInstance();
        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = rfr.child(file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);
        return uploadTask;
    }
}
