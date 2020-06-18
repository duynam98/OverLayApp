package com.example.demooverlay.view.fragment.home;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demooverlay.databinding.ItemMenuBinding;
import com.example.demooverlay.model.MenuHome;

public class MenuHolder extends RecyclerView.ViewHolder {

    public ObservableField<String> title;
    public ItemMenuBinding itemMenuBinding;

    public MenuHolder(@NonNull ItemMenuBinding itemView) {
        super(itemView.getRoot());
        title = new ObservableField<>();
        itemMenuBinding = itemView;
    }

    public void setData(MenuHome menuHome) {
        if (itemMenuBinding.getMenuHolder() == null){
            itemMenuBinding.setMenuHolder(this);
        }
        title.set(menuHome.getTitle());
        Glide.with(itemMenuBinding.imgMenu.getContext()).load(menuHome.getImage()).into(itemMenuBinding.imgMenu);
    }

}
