package com.example.demooverlay.view.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demooverlay.R;
import com.example.demooverlay.databinding.ItemMenuBinding;
import com.example.demooverlay.model.MenuHome;
import com.example.demooverlay.view.fragment.home.MenuHolder;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MenuHome> menuHomeList;
    private Context context;
    private OnClick onClick;

    public void setOnClick(OnClick onClick){
        this.onClick = onClick;
    }

    public MenuAdapter(Context context) {
        this.context = context;
        menuHomeList = new ArrayList<>();
        setData();
    }

    public void setData(){
        menuHomeList.add(new MenuHome(R.drawable.ic_text, context.getResources().getString(R.string.menu_edit_text)));
        menuHomeList.add(new MenuHome(R.drawable.ic_overlay, context.getResources().getString(R.string.menu_edit_overlay)));
        menuHomeList.add(new MenuHome(R.drawable.ic_stickers, context.getResources().getString(R.string.menu_edit_stickers)));
        menuHomeList.add(new MenuHome(R.drawable.ic_text, context.getResources().getString(R.string.menu_edit_text)));
        menuHomeList.add(new MenuHome(R.drawable.ic_text, context.getResources().getString(R.string.menu_edit_text)));
        menuHomeList.add(new MenuHome(R.drawable.ic_text, context.getResources().getString(R.string.menu_edit_text)));
        menuHomeList.add(new MenuHome(R.drawable.ic_text, context.getResources().getString(R.string.menu_edit_text)));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemMenuBinding itemMenuBinding = DataBindingUtil.inflate(inflater, R.layout.item_menu, parent, false);
        return new MenuHolder(itemMenuBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MenuHolder menuHolder = (MenuHolder) holder;
        menuHolder.setData(menuHomeList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick != null){
                    onClick.OnClickMenu(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (menuHomeList == null){
            return 0;
        }
        return menuHomeList.size();
    }

    public interface OnClick{
        void OnClickMenu(int position);
    }

}
