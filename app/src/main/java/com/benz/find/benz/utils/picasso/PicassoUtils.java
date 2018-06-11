package com.benz.find.benz.utils.picasso;

import android.content.Context;
import android.graphics.Bitmap;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.Builder;
import com.squareup.picasso.Transformation;

import java.util.Map;

public class PicassoUtils {

    private static Map<String, Picasso> mPicassoMap;
    static volatile Picasso singleton = null;
    static volatile LruCache lrucache;

    public static final String EMPTYURL = "notValidPicassoUrl";

    public static Picasso with(Context context) {
        if (singleton == null) {
            synchronized (Picasso.class) {
                if (singleton == null) {
                    lrucache = new LruCache(context);
                    singleton = new Builder(context).memoryCache(lrucache).build();
                }
            }
        }
        return singleton;
    }

    public static LruCache cache(Context context) {
        if (lrucache == null) {
            with(context);
        }
        return lrucache;
    }

    public static class RatioTransformation implements Transformation {
        private int[] point = new int[2];

        public RatioTransformation(int[] screenwh) {
            point[0] = screenwh[0];
            point[1] = screenwh[1];
        }

        @Override
        public Bitmap transform(Bitmap source) {

            int[] size = SizeUtils.getResize(point, source.getWidth(), source.getHeight());
            Bitmap result = Bitmap.createScaledBitmap(source, size[0], size[1], false);
            if (result != source) {
                // Same bitmap is returned if sizes are the same
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "transformation" + " resize" + point[0] + "#" + point[1];
        }
    }
}
