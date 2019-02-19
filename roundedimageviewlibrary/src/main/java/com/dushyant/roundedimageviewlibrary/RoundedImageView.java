package com.dushyant.roundedimageviewlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;

public class RoundedImageView extends AppCompatImageView {

    private static final ScaleType[] scaleTypeArray = {ScaleType.CENTRE_CROP, ScaleType.FIT_XY};
    private static final CornerType[] cornerTypeArray = {CornerType.NO_CORNER,
            CornerType.TOP_RIGHT_CORNER,
            CornerType.BOTTOM_RIGHT_CORNER,
            CornerType.BOTTOM_LEFT_CORNER,
            CornerType.TOP_LEFT_CORNER};

    private float radiusInPx;
    private Paint paint;
    private Path path;
    private int imageResource;
    private Bitmap bitmap;
    private RectF viewBounds, scaleRect;
    private float screenWidth;
    private float screenHeight;
    private float requiredWidth;
    private float requiredHeight;
    private int x;
    private int y;
    private Context context;
    private ScaleType scaleType;
    private CornerType cornerType;
    private List<CornerType> cornerTypeList;
    private int canvasPaddingX = 0;
    private int canvasPaddingY = 0;
    private int imagePaddingX = 0;
    private int imagePaddingY = 0;
    private int borderColor;
    private boolean border;
    private float borderWidth;

    public RoundedImageView(Context context) {
        super(context);
        init(context, null);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attr) {
        this.context = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        viewBounds = new RectF();
        scaleRect = new RectF();
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        radiusInPx = dpToPx(20);
        cornerType = CornerType.NO_CORNER;
        cornerTypeList = new ArrayList<>();

        if (attr != null) {
            try {
                TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.RoundedImageView);
                borderColor = typedArray.getColor(R.styleable.RoundedImageView_borderColor, 0);
                imageResource = typedArray.getResourceId(R.styleable.RoundedImageView_src, 0);
                borderWidth = typedArray.getDimension(R.styleable.RoundedImageView_borderWidth, 0f);
                border = typedArray.getBoolean(R.styleable.RoundedImageView_border, true);
                scaleType = scaleTypeArray[typedArray.getInt(R.styleable.RoundedImageView_scaleType, 0)];

                radiusInPx = dpToPx(typedArray.getInt(R.styleable.RoundedImageView_cornerRadius, 0));
                cornerType = cornerTypeArray[typedArray.getInt(R.styleable.RoundedImageView_cornerType, -1)];
                int imagePadding = typedArray.getInt(R.styleable.RoundedImageView_imagePadding, 0);
                int canvasPadding = typedArray.getInt(R.styleable.RoundedImageView_canvasPadding, 0);

                this.imagePaddingX = imagePadding;
                this.imagePaddingY = imagePadding;
                this.canvasPaddingX = canvasPadding;
                this.canvasPaddingY = canvasPadding;
                typedArray.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
//        imageResource = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);

        if (imageResource != 0) {
            try {
                bitmap = BitmapFactory.decodeResource(context.getResources(), imageResource);
            } catch (OutOfMemoryError error) {
                bitmap = null;
                Log.e("Image Error: ", "Image is too large " + error.getMessage());
            } catch (Exception e) {
                Log.e("Image Error: ", e.getMessage());
            }
        }
    }

    private float dpToPx(int radiusDp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                radiusDp,
                getResources().getDisplayMetrics()
        ) * 3;
    }

    /**
     * Set the corner radius to use for the RoundedRectangle.
     */
    public void setCornerRadius(int radiusDp) {
        radiusInPx = dpToPx(radiusDp);
        System.out.println("Radius 3Px: " + radiusInPx);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        System.out.println("widthMeasureSpec: " + widthMeasureSpec);
        System.out.println("heightMeasureSpec " + heightMeasureSpec);

        screenWidth = getMeasuredWidth();
        screenHeight = getMeasuredHeight();
        if (bitmap != null && scaleType == ScaleType.CENTRE_CROP) {
            float ratioChange = 1;
            if (screenWidth != bitmap.getWidth()) {
                ratioChange = screenWidth / bitmap.getWidth();
            }
            if (ratioChange * bitmap.getHeight() < screenHeight) {
                ratioChange = screenHeight / bitmap.getHeight();
            }
            requiredHeight = bitmap.getHeight() * ratioChange;
            requiredWidth = bitmap.getWidth() * ratioChange;
            y = (int) ((requiredHeight / 2) - (screenHeight / 2));
            x = (int) ((requiredWidth / 2) - (screenWidth / 2));
            if (x > 0) x = -x;
            if (y > 0) y = -y;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!border) borderWidth = 0;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);

        if (borderColor != 0) {
            paint.setColor(borderColor);
        } else paint.setColor(Color.BLACK);

        System.out.println("Canvas Height: " + getWidth());
        System.out.println("Canvas Width : " + getHeight());
        viewBounds.set(0, 0, screenWidth, screenHeight);

        int totalWidth = getWidth();
        int totalHeight = getHeight();
        totalWidth -= canvasPaddingX;
        totalHeight -= canvasPaddingY;

        float finalX = totalWidth - radiusInPx;
        float finalY = totalHeight - radiusInPx;
        System.out.println("radiusInPx: " + radiusInPx + " FinalX: " + finalX + " FinalY: " + finalY);

        path.moveTo(radiusInPx + canvasPaddingX, canvasPaddingY);
        //top-right corner
        if (cornerType == CornerType.TOP_RIGHT_CORNER || cornerTypeList.contains(CornerType.TOP_RIGHT_CORNER)) {
            path.lineTo(totalWidth, canvasPaddingY);
        } else {
            path.lineTo(finalX, canvasPaddingY);
            path.quadTo(totalWidth, canvasPaddingY, totalWidth, totalHeight - finalY);
        }

        //bottom-right corner
        if (cornerType == CornerType.BOTTOM_RIGHT_CORNER || cornerTypeList.contains(CornerType.BOTTOM_RIGHT_CORNER)) {
            path.lineTo(totalWidth, totalHeight);
        } else {
            path.lineTo(totalWidth, finalY);
            path.quadTo(totalWidth, totalHeight, finalX, totalHeight);
        }

        //bottom-left corner
        if (cornerType == CornerType.BOTTOM_LEFT_CORNER || cornerTypeList.contains(CornerType.BOTTOM_LEFT_CORNER)) {
            path.lineTo(canvasPaddingX, totalHeight);
        } else {
            path.lineTo(totalWidth - finalX, totalHeight);
            path.quadTo(canvasPaddingX, totalHeight, canvasPaddingX, finalY);
        }

        //top-left corner
        if (cornerType == CornerType.TOP_LEFT_CORNER || cornerTypeList.contains(CornerType.TOP_LEFT_CORNER)) {
            path.lineTo(canvasPaddingX, canvasPaddingY);
        } else {
            path.lineTo(canvasPaddingX, totalHeight - finalY);
            path.quadTo(canvasPaddingX, canvasPaddingY, totalWidth - finalX, canvasPaddingY);
        }

        path.close();
        canvas.clipPath(path);

        if (bitmap != null) {
            if (scaleType == ScaleType.CENTRE_CROP) {
                scaleRect.set(x + imagePaddingX, y + imagePaddingY, (x + requiredWidth) - imagePaddingX,
                        (y + requiredHeight) - imagePaddingY);
                canvas.clipRect(scaleRect);
                canvas.drawBitmap(bitmap, null, scaleRect, paint);
                System.out.println("Scale Rect");
            } else {
                canvas.drawBitmap(bitmap, null, viewBounds, paint);
            }

            canvas.drawPath(path, paint);
        }
    }

    /**
     * @param cornerType of the image
     *                   :
     *                   NO_CORNER,
     *                   TOP_LEFT_CORNER,
     *                   TOP_RIGHT_CORNER,
     *                   BOTTOM_LEFT_CORNER,
     *                   BOTTOM_RIGHT_CORNER
     */
    public void setCornerType(CornerType cornerType) {
        this.cornerType = cornerType;
        invalidate();
    }

    /**
     * @param resId is drawable resource Id of image
     */
    public void setImageSource(@DrawableRes int resId) {
        this.imageResource = resId;
        if (imageResource != 0) {
            try {
                bitmap = BitmapFactory.decodeResource(context.getResources(), imageResource);
            } catch (OutOfMemoryError error) {
                bitmap = null;
                Log.e("Image Error: ", "Image is too large " + error.getMessage());
            } catch (Exception e) {
                Log.e("Image Error: ", e.getMessage());
            }
        }
        invalidate();
    }

    /**
     * @param bitmap to set bitmap resource in imageview
     */
    public void setImageBitmap(Bitmap bitmap) {
        this.imageResource = 0;
        try {
            this.bitmap = bitmap;
        } catch (OutOfMemoryError error) {
            this.bitmap = bitmap;
            Log.e("Image Error: ", "Image is too large " + error.getMessage());
        } catch (Exception e) {
            Log.e("Image Error: ", e.getMessage());
        }
        invalidate();
    }

    /**
     * @param scaleType of the image
     *                  :
     *                  CENTER_CROP
     *                  AND FIT_XY
     */
    public void setImageScaleType(@NonNull ScaleType scaleType) {
        this.scaleType = scaleType;
        invalidate();
    }

    /**
     * @param cornerTypeList to add multiple corners in ImageView
     */
    public void setCornerTypeList(List<CornerType> cornerTypeList) {
        this.cornerTypeList.clear();
        this.cornerTypeList = cornerTypeList;
        invalidate();
    }

    /**
     * @param padding to set padding  in canvas
     */
    public void setCanvasPadding(int padding) {
        this.canvasPaddingX = padding;
        this.canvasPaddingY = padding;
        invalidate();
    }

    /**
     * @param paddingX to set padding from X-axis in canvas
     */
    public void setCanvasPaddingX(int paddingX) {
        this.canvasPaddingX = paddingX;
        invalidate();
    }

    /**
     * @param paddingY to set padding from Y-axis in canvas
     */
    public void setCanvasPaddingY(int paddingY) {
        this.canvasPaddingY = paddingY;
        invalidate();
    }

    /**
     * @param padding to set padding in image
     */
    public void setImagePadding(int padding) {
        this.imagePaddingX = padding;
        this.imagePaddingY = padding;
        invalidate();
    }

    /**
     * @param paddingX to set padding from X-axis in image
     */
    public void setImagePaddingX(int paddingX) {
        this.imagePaddingX = paddingX;
        invalidate();
    }

    /**
     * @param paddingY to set padding from Y-axis in image
     */
    public void setImagePaddingY(int paddingY) {
        this.imagePaddingY = paddingY;
        invalidate();
    }

    /**
     * @param border of the Polygon
     */
    public void setBorder(@NonNull boolean border) {
        this.border = border;
        invalidate();
    }

    /**
     * @param borderColor of the Polygon
     */
    public void setBorderColor(@NonNull int borderColor) {
        this.borderColor = borderColor;
        invalidate();
    }

    /**
     * @param borderWidth of the Polygon
     */
    public void setBorderWidth(@NonNull float borderWidth) {
        this.borderWidth = borderWidth;
        invalidate();
    }

    public enum ScaleType {
        CENTRE_CROP(0),
        FIT_XY(1);
        final int value;

        ScaleType(int value) {
            this.value = value;
        }
    }

    public enum CornerType {
        NO_CORNER(-1),
        TOP_RIGHT_CORNER(1),
        BOTTOM_RIGHT_CORNER(2),
        BOTTOM_LEFT_CORNER(3),
        TOP_LEFT_CORNER(4);

        final int value;

        CornerType(int value) {
            this.value = value;
        }
    }
}

