package com.example.demooverlay.view.activity.editImage;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demooverlay.R;
import com.example.demooverlay.databinding.FragmentMenuStickerBinding;

public class MenuStickerFragment extends Fragment {

    private FragmentMenuStickerBinding fragmentMenuStickerBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentMenuStickerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_sticker, container, false);
        return fragmentMenuStickerBinding.getRoot();
    }



}