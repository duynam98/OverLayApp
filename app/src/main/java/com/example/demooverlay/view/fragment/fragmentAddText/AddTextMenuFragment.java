package com.example.demooverlay.view.fragment.fragmentAddText;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.demooverlay.R;
import com.example.demooverlay.databinding.FragmentAddTextMenuBinding;
import com.example.demooverlay.view.activity.editImage.EditImageActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTextMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTextMenuFragment extends Fragment {

    private FragmentAddTextMenuBinding binding;

    public static AddTextMenuFragment newInstance(String param1, String param2) {
        AddTextMenuFragment fragment = new AddTextMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_text_menu, container, false);
        addText();
        return binding.getRoot();
    }

    private void addText(){
        binding.ctlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditImageActivity) getActivity()).addTexttoImage();
            }
        });
    }

}