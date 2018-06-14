package com.benz.find.utils.picasso;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.WindowManager;

import com.benz.find.MyApplication;


/**
 * Created by somehui on 15/9/16.
 */
public class SizeUtils {

    public static final int[] LIMIT_SIZE = {2048, 2048};

    public static int[] getResize(WindowManager manager, int width, int height) {
        int[] resize = getScreenSize(manager);
        resize[0] = (int) (resize[0] * 2f / 3f);
        resize[1] = (int) (resize[1] * 2f / 3f);
        return getResize(resize, width, height);
    }

    public static int[] checkSize(int[] resize, float percent) {
        int size = (int) (60 * 1024 * 1024 * percent);
        try {
            if (MyApplication.getAppContext().getResources().getDisplayMetrics().heightPixels > 2000) {
                size *= 1.5;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] result = new int[2];
        if (resize[0] * resize[1] * 4 > size) {
            double scaleV2 = Math.sqrt(size / (resize[0] * resize[1] * 4.0));
            result[0] = (int) (resize[0] * scaleV2);
            result[1] = (int) (resize[1] * scaleV2);
            return result;
        } else {
            return resize;
        }
    }

    public static int[] getResize(int[] screenwh, int width, int height) {
        int[] result = new int[2];
        if (width <= 0 || height <= 0) {
            return result;
        }

        float scale;
        if (width * screenwh[1] < screenwh[0] * height) {
            scale = (float) screenwh[1] / (float) height;
        } else {
            scale = (float) screenwh[0] / (float) width;
        }

        if (scale > 1) {
            scale = 1;
        }

        result[0] = (int) (width * scale);
        result[1] = (int) (height * scale);

        return result;
    }


    public static int[] getImageWidthAndHeight(String path) {
        int[] info = new int[2];
        if (path == null)
            return info;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        info[0] = options.outWidth;
        info[1] = options.outHeight;
        return info;
    }

    public static int[] getScreenSize(WindowManager manager) {
        int[] point = new int[2];
        Point p = new Point();
        manager.getDefaultDisplay().getSize(p);
        point[0] = p.x;
        point[1] = p.y;
        return point;
    }

    public static int getScreenWidth() {
        return MyApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return MyApplication.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int calSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth) {
        int sample = 1;
        int width = options.outWidth;
        int height = options.outHeight;
        while (reqHeight < height || reqWidth < width) {
//            sample = sample << 2;
            sample <<= 1;
            height >>= 1;
            width >>= 1;
        }
        return sample;
    }

    public static int[] getScale(int[] screenwh, int width, int height) {
        int[] result = new int[2];
        if (width <= 0 || height <= 0) {
            return result;
        }

        float scale;
        if (width * screenwh[1] < screenwh[0] * height) {
            scale = (float) screenwh[1] / (float) height;
        } else {
            scale = (float) screenwh[0] / (float) width;
        }

        result[0] = (int) (width * scale);
        result[1] = (int) (height * scale);

        return result;
    }
}
