//package com.benz.find.benz.utils.picasso;
//
//import android.annotation.TargetApi;
//import android.app.ActivityManager;
//import android.content.Context;
//
//import com.squareup.picasso.LruCache;
//
//import static android.content.Context.ACTIVITY_SERVICE;
//import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
//import static android.os.Build.VERSION.SDK_INT;
//import static android.os.Build.VERSION_CODES.HONEYCOMB;
//
//public class BigLruCache extends LruCache {
//    public BigLruCache(Context context) {
//        this(calculateMemoryCacheSize(context));
//    }
//
//    public BigLruCache(Context context, float percent){
//        this(calculateMemoryCacheSize(context,percent));
//    }
//
//    public BigLruCache(int maxSize) {
//        super(maxSize);
//    }
//
//    @SuppressWarnings("unchecked")
//    static <T> T getService(Context context, String service) {
//        return (T) context.getSystemService(service);
//    }
//
//    private static final float DEFAULT_PERCENT = 1/7f;
//
//    public static int calculateMemoryCacheSize(Context context){
//        return calculateMemoryCacheSize(context,DEFAULT_PERCENT);
//    }
//
//    public static int calculateMemoryCacheSize(Context context,float percent) {
//        if(percent <0||percent>1){
//            percent = 1/7f;
//        }
//        ActivityManager am = getService(context, ACTIVITY_SERVICE);
//        boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
//        int memoryClass = am.getMemoryClass();
//        if (largeHeap && SDK_INT >= HONEYCOMB) {
//            memoryClass = ActivityManagerHoneycomb.getLargeMemoryClass(am);
//        }
//        // Target ~15% of the available heap.
//        return (int)( 1024 * 1024 * memoryClass *percent );
//    }
//
//    @TargetApi(HONEYCOMB)
//    private static class ActivityManagerHoneycomb {
//        static int getLargeMemoryClass(ActivityManager activityManager) {
//            return activityManager.getLargeMemoryClass();
//        }
//    }
//
//}
