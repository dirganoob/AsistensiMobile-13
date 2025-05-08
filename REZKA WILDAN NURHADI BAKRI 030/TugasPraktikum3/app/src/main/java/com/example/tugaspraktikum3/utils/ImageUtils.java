package com.example.tugaspraktikum3.utils;

import android.graphics.Bitmap;
import android.util.SparseArray;

public class ImageUtils {
    private static final SparseArray<Bitmap> uploadedImages = new SparseArray<>();

    public static void addUploadedImage(int postId, Bitmap bitmap) {
        uploadedImages.put(postId, bitmap);
    }

    public static boolean hasUploadedImage(int postId) {
        return uploadedImages.get(postId) != null;
    }

    public static Bitmap getUploadedImage(int postId) {
        return uploadedImages.get(postId);
    }
}