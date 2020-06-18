package com.example.demooverlay.view.fragment.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.demooverlay.utils.Constant;
import com.example.demooverlay.R;
import com.example.demooverlay.databinding.FragmentHomeBinding;
import com.example.demooverlay.view.activity.SplashActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements GetImage.onLoadImage, ListImageAdapter.ChooseImage {

    private FragmentHomeBinding fragmentHomeBinding;
    private GetImage getImage;
    private ListImageAdapter listImageAdapter;
    private GridLayoutManager gridLayoutManager;
    private static final int PICK_IMAGE = 1000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        initAdapter();
        getImage = new GetImage(getActivity().getApplicationContext());
        getImage.setOnLoadImage(this);
        initPremission();
        openStorage();
        return fragmentHomeBinding.getRoot();
    }

    private void openStorage(){
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        fragmentHomeBinding.gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(Intent.createChooser(intent, "Selection Image"), PICK_IMAGE);
            }
        });
    }

    private void initAdapter() {
        listImageAdapter = new ListImageAdapter(getContext());
        listImageAdapter.setChooseImage(this);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        fragmentHomeBinding.rvListImage.setLayoutManager(gridLayoutManager);
        fragmentHomeBinding.rvListImage.setAdapter(listImageAdapter);
    }

    private void initPremission() {
        if ((ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            getImage.execute();
        } else if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constant.REQUEST_CODE_PERMISSION);
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constant.REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImage.execute();
            } else {
                initPremission();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE){
            if (data != null){
                ((SplashActivity) getActivity()).addFragmentEditImage(data.getData().toString());
            }
        }
    }

    @Override
    public void onLoadImageFinish(ArrayList<String> arrayListImage) {
        listImageAdapter.setData(arrayListImage);
    }

    @Override
    public void getImageFinish(String patch) {
        ((SplashActivity) getActivity()).addFragmentEditImage(patch);
    }

}
