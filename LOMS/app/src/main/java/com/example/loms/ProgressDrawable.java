package com.example.loms;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProgressDrawable extends Drawable {

    private final Paint paint;
    private int progress;

    public ProgressDrawable(int progress) {
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(10);
        this.paint.setColor(Color.BLUE);
        this.progress = progress;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        float sweepAngle = 360 * (progress / 100f);
        canvas.drawArc(bounds.left, bounds.top, bounds.right, bounds.bottom, -90, sweepAngle, false, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable android.graphics.ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return android.graphics.PixelFormat.TRANSLUCENT;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidateSelf(); // Redraw the Drawable when progress changes
    }
}
