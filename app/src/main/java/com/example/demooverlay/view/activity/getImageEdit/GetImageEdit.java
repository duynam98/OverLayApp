package com.example.demooverlay.view.activity.getImageEdit;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class GetImageEdit extends AsyncTask<Void, Void, ArrayList<String>> {

    private ArrayList<String> f = new ArrayList<>();
    private File[] listFile;
    private OnLoadImageEdit onLoadImageEdit;

    public void setOnLoadImageEdit(OnLoadImageEdit onLoadImageEdit) {
        this.onLoadImageEdit = onLoadImageEdit;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        File file = new File(Environment.getExternalStorageDirectory(), "overlay");
        if (file.isDirectory()) {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                f.add(listFile[i].getAbsolutePath());
            }
        }
        return f;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        if (onLoadImageEdit != null) {
            onLoadImageEdit.onLoadFinish(strings);
        }
    }

    public interface OnLoadImageEdit {
        void onLoadFinish(ArrayList<String> stringListImage);
    }

}
