package com.example.demooverlay.view.activity.getImageEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.demooverlay.R;
import com.example.demooverlay.databinding.ActivityMainBinding;
import com.example.demooverlay.view.fragment.home.ListImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetImageEdit.OnLoadImageEdit{

    private GetImageEdit getImageEdit;
    private ListImageAdapter listImageAdapter;
    private GridLayoutManager gridLayoutManager;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getImageEdit = new GetImageEdit();
        getImageEdit.setOnLoadImageEdit(this);
        getImageEdit.execute();
        initRecycleView();
    }

    private void initRecycleView(){
        listImageAdapter = new ListImageAdapter(this);
        gridLayoutManager = new GridLayoutManager(this, 4);
        activityMainBinding.rvListImage.setLayoutManager(gridLayoutManager);
        activityMainBinding.rvListImage.setAdapter(listImageAdapter);
    }

    @Override
    public void onLoadFinish(ArrayList<String> stringListImage) {
        listImageAdapter.setData(stringListImage);
    }
}