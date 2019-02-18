# RoundedImageView-Library
Rounded ImageView Android Library

[![API](https://img.shields.io/badge/API-9%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=9)  [![](https://jitpack.io/v/DushyantMainwal/ShapeShifter.svg)](https://jitpack.io/#DushyantMainwal/ShapeShifter) [![Android Arsenal]


# Usage
Step 1. Add the JitPack repository to your build file
```groovy
allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
```
Step 2. Add the dependency
```groovy
dependencies {
  compile 'com.github.DushyantMainwal:RoundedImageView-Library:0.1.0'
 }
 ```
 
 # Implementation
###XML Implementation:
```xml
 <com.dushyant.roundedimageviewlibrary.RoundedImageView
            android:id="@+id/image_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:scaleType="centerCrop"
            app:border="true"
            app:borderColor="#089"
            app:borderWidth="10dp"
            app:canvasPadding="10"
            app:imagePadding="10"
            app:cornerRadius="30"
            app:src="@drawable/rdjimage"
            app:cornerType="bottomLeft"/>
        
```

###Java Implementation:
```java
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
```

#Licence
```
Copyright (c) 2016 Dushyant Mainwal

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
