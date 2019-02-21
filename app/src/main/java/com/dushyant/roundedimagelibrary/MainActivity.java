package com.dushyant.roundedimagelibrary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dushyant.roundedimageviewlibrary.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoundedImageView customImageView = findViewById(R.id.image_view);
        customImageView.setImageResource(R.drawable.rdjimage);
        customImageView.setImageScaleType(RoundedImageView.ScaleType.CENTRE_CROP);
        customImageView.setCornerType(RoundedImageView.CornerType.BOTTOM_RIGHT_CORNER);
        customImageView.setCornerRadius(40);

//        Picasso.with(this).load("https://www.gstatic.com/webp/gallery/5.sm.jpg").into(customImageView);

//        Glide.with(this)
//                .load("https://www.w3schools.com/w3css/img_lights.jpg")
//                .asBitmap()
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        customImageView.setImageBitmap(resource);
//                    }
//                });

        customImageView.setImagePadding(20);
        customImageView.setCanvasPadding(50);

        customImageView.setBorder(true);
        customImageView.setBorderColor(Color.DKGRAY);
        customImageView.setBorderWidth(40);

        customImageView.setCornerRadius(30);

        List<RoundedImageView.CornerType> cornerTypes = new ArrayList<>();
        cornerTypes.add(RoundedImageView.CornerType.TOP_LEFT_CORNER);
        cornerTypes.add(RoundedImageView.CornerType.TOP_RIGHT_CORNER);
        customImageView.setCornerTypeList(cornerTypes);

    }
}
