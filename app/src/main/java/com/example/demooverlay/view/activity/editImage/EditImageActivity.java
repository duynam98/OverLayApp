package com.example.demooverlay.view.activity.editImage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demooverlay.R;
import com.example.demooverlay.databinding.ActivityEditImageBinding;
import com.example.demooverlay.ui.BubbleTextView;
import com.example.demooverlay.ui.StickerView;
import com.example.demooverlay.utils.Constant;
import com.example.demooverlay.view.activity.getImageEdit.MainActivity;
import com.example.demooverlay.view.fragment.MenuAdapter;
import com.example.demooverlay.view.fragment.fragmentAddText.AddTextMenuFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

public class EditImageActivity extends AppCompatActivity implements MenuAdapter.OnClick, MenuStickerAdapter.OnClickSticker {

    private ActivityEditImageBinding activityEditImageBinding;
    private String patch;
    private MenuAdapter menuAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MenuStickerAdapter menuStickerAdapter;
    //stickerView
    private ArrayList<View> mViews;
    private StickerView mCurrentTView;
    private FragmentTransaction fragmentTransaction;
    private AddTextMenuFragment addTextMenuFragment;
    private BubbleTextView mCurrentEditTextView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditImageBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_image);
        patch = getIntent().getStringExtra(Constant.PATCH_IMAGE);
        Glide.with(this).load(patch).fitCenter().into(activityEditImageBinding.imgContainer);
        initMenuAdapter();
        init();
        initRecycleViewSticker();
        activityEditImageBinding.imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentTView != null) {
                    mCurrentTView.setInEdit(false);
                }
                saveImage();
                Intent intent = new Intent(EditImageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        doneAddText();
    }

    private void init() {
        mViews = new ArrayList<>();
        addTextMenuFragment = new AddTextMenuFragment();
    }

    private void initMenuAdapter() {
        menuAdapter = new MenuAdapter(this);
        menuAdapter.setOnClick(this);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        activityEditImageBinding.rvmenuEdit.setLayoutManager(linearLayoutManager);
        activityEditImageBinding.rvmenuEdit.setAdapter(menuAdapter);
    }

    private void initRecycleViewSticker() {
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        menuStickerAdapter = new MenuStickerAdapter(this);
        menuStickerAdapter.setOnClickSticker(this);
        activityEditImageBinding.rvListSticker.setLayoutManager(linearLayoutManager);
        activityEditImageBinding.rvListSticker.setAdapter(menuStickerAdapter);
    }

    @Override
    public void OnClickMenu(int position) {
        switch (position) {
            case 0:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame_menu, addTextMenuFragment).addToBackStack(null);
                //activityEditImageBinding.rvListSticker.setVisibility(View.GONE);
                fragmentTransaction.commit();
            case 2:
                //activityEditImageBinding.rvmenuEdit.setVisibility(View.GONE);
                //activityEditImageBinding.rvListSticker.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void chooseStickerFinish(String name) {
        addStickertoImage(Constant.PATCH_STICKER + "/" + name);
    }

    public void addStickertoImage(String patch) {
        final StickerView stickerView = new StickerView(this);
        Bitmap bitmap = getBitmapFromAsset(patch);
        stickerView.setBitmap(bitmap);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(stickerView);
                activityEditImageBinding.rootView.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                mCurrentTView.setInEdit(false);
                mCurrentTView = stickerView;
                mCurrentTView.setInEdit(true);
            }

            @Override
            public void onTop(StickerView stickerView) {
                int position = mViews.indexOf(stickerView);
                if (position == mViews.size() - 1) {
                    return;
                }
                StickerView stickerTemp = (StickerView) mViews.remove(position);
                mViews.add(mViews.size(), stickerTemp);
            }

            @Override
            public void onTransparency() {
                activityEditImageBinding.rvListSticker.setVisibility(View.GONE);
                activityEditImageBinding.rvmenuEdit.setVisibility(View.GONE);
                activityEditImageBinding.layoutTransparency.setVisibility(View.VISIBLE);
                setTransparncyStickerView(stickerView);
            }

        });
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        activityEditImageBinding.rootView.addView(stickerView, lp);
        mViews.add(stickerView);
        setCurrentTView(stickerView);
        cancelTransparency(stickerView);
    }

    public void addTexttoImage() {
        final BubbleTextView bubbleTextView = new BubbleTextView(this,
                Color.RED, 0);
        bubbleTextView.setImageResource(R.mipmap.none_image);
        bubbleTextView.setOperationListener(new BubbleTextView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(bubbleTextView);
                activityEditImageBinding.rootView.removeView(bubbleTextView);
            }

            @Override
            public void onEdit(BubbleTextView bubbleTextView) {
                if (mCurrentTView != null) {
                    mCurrentTView.setInEdit(false);
                }
                mCurrentEditTextView.setInEdit(false);
                mCurrentEditTextView = bubbleTextView;
                mCurrentEditTextView.setInEdit(true);
            }

            @Override
            public void onClick(BubbleTextView bubbleTextView) {
                activityEditImageBinding.ctlIputEdt.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTop(BubbleTextView bubbleTextView) {
                int position = mViews.indexOf(bubbleTextView);
                if (position == mViews.size() - 1) {
                    return;
                }
                BubbleTextView textView = (BubbleTextView) mViews.remove(position);
                mViews.add(mViews.size(), textView);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        activityEditImageBinding.rootView.addView(bubbleTextView, lp);
        mViews.add(bubbleTextView);
        setCurrentEdit(bubbleTextView);
    }

    private void setCurrentEdit(BubbleTextView bubbleTextView) {
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        if (mCurrentTView != null) {
            mCurrentTView.setInEdit(false);
        }
        mCurrentEditTextView = bubbleTextView;
        mCurrentEditTextView.setInEdit(true);

    }

    private void setTransparncyStickerView(final StickerView stickerView) {
        activityEditImageBinding.sbTransparency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                activityEditImageBinding.tvTransparency.setText(i + "%");
                stickerView.setOpacity((int) (255 - Math.round(seekBar.getProgress() * 2.55)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setCurrentTView(StickerView stickerView) {
        if (mCurrentTView != null) {
            mCurrentTView.setInEdit(false);
        }
        mCurrentTView = stickerView;
        mCurrentTView.setInEdit(true);
    }

    private Bitmap getBitmapFromAsset(String patch) {
        Bitmap bitmap = null;
        try {
            InputStream ims = getAssets().open(patch);
            bitmap = BitmapFactory.decodeStream(ims);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void saveImage() {
        Bitmap bitmap = Bitmap.createBitmap(activityEditImageBinding.rootView.getWidth(), activityEditImageBinding.rootView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        activityEditImageBinding.rootView.draw(canvas);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        saveImageFile(bitmap);
    }

    public void saveImageFile(Bitmap bitmap) {
        String patch = Environment.getExternalStorageDirectory().toString();
        File myFile = new File(patch + "/overlay");
        if (!myFile.exists()) {
            myFile.mkdirs();
        }
        String file_name = UUID.randomUUID().toString() + ".jpg";
        File file = new File(myFile, file_name);
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void doneAddText() {
        activityEditImageBinding.imgDoneAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setText(activityEditImageBinding.edtAddText.getText().toString());
                    activityEditImageBinding.ctlIputEdt.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }

    private void cancelTransparency(final StickerView stickerView) {
        activityEditImageBinding.imgCancleTransparency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stickerView.setAlpha((float) 1.0);
                activityEditImageBinding.layoutTransparency.setVisibility(View.GONE);
                //activityEditImageBinding.rvListSticker.setVisibility(View.VISIBLE);
                activityEditImageBinding.sbTransparency.setProgress(0);
            }
        });

        activityEditImageBinding.imgDoneTransparency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTransparncyStickerView(stickerView);
                //activityEditImageBinding.layoutTransparency.setVisibility(View.GONE);
                //activityEditImageBinding.rvListSticker.setVisibility(View.VISIBLE);
            }
        });
    }


}