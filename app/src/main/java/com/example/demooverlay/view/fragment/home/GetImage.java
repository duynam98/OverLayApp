package com.example.demooverlay.view.fragment.home;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;

public class GetImage extends AsyncTask<Void, Void, ArrayList<String>> {

    private Context context;

    public onLoadImage onLoadImage;

    public void setOnLoadImage(onLoadImage onLoadImage){
        this.onLoadImage = onLoadImage;
    }

    public GetImage(Context context) {
        this.context = context;
    }

    //get image from device
    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        Cursor cursor;
        int column_index_data;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = context.getContentResolver().query(uri, projection, null,
                null, null);
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }

    @Override
    protected void onPostExecute(ArrayList<String> arrayList) {
        if (onLoadImage != null){
            onLoadImage.onLoadImageFinish(arrayList);
        }
    }

    public interface onLoadImage{
        void onLoadImageFinish(ArrayList<String> arrayListImage);
    }

}
