package com.dushyant.roundedimagelibrary;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dushyant.roundedimageviewlibrary.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        RoundedImageView customImageView = findViewById(R.id.image_view);
        customImageView.setImageSource(R.drawable.rdjimage);
        customImageView.setImageScaleType(RoundedImageView.ScaleType.CENTRE_CROP);
        customImageView.setCornerType(RoundedImageView.CornerType.BOTTOM_RIGHT_CORNER);
        customImageView.setCornerRadius(40);

        customImageView.setImagePadding(20);
        customImageView.setCanvasPadding(50);

        customImageView.setBorder(true);
        customImageView.setBorderColor(Color.DKGRAY);
        customImageView.setBorderWidth(40);

        List<RoundedImageView.CornerType> cornerTypes = new ArrayList<>();
        cornerTypes.add(RoundedImageView.CornerType.TOP_LEFT_CORNER);
        cornerTypes.add(RoundedImageView.CornerType.TOP_RIGHT_CORNER);
        customImageView.setCornerTypeList(cornerTypes);

    }
}
