package com.alan.common.utils.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;

import com.alan.common.AndroidTools;
import com.alan.common.FileUtils;
import com.alan.common.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


/**
 * @author Alan
 * 时 间：2019-12-04
 * 简 述：<功能简述>
 */
public class BitmapUtils {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static Bitmap blurBitmap(Context context, Bitmap image, float blurRadius, int outWidth, int outHeight) {
        // 将缩小后的图片做为预渲染的图片
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, outWidth, outHeight, false);
        // 创建一张渲染后的输出图片
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        // 设置渲染的模糊程度, 25f是最大模糊度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurScript.setRadius(blurRadius);
        }
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);
        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    public static Bitmap getBitmapFromPath(String pathName, Context activity) {
        try {
            int[] screenSize = AndroidTools.getScreenSize(activity);
            int screenWidth = screenSize[0];
            int screenHeight = screenSize[1];
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bitmap;
            if (options.outWidth > screenWidth
                    && options.outHeight > screenHeight) {
                options.inSampleSize = computeSampleSize(options, -1,
                        screenWidth * screenHeight);
            }
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(pathName, options);
            return bitmap;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    private static int computeSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    // 计算图片的缩放�??
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        boolean isOptionWidthLarger = options.outWidth > options.outHeight;
        boolean isReqWidthLarger = reqWidth > reqHeight;
        final int height = isOptionWidthLarger ? options.outHeight
                : options.outWidth;
        final int width = isOptionWidthLarger ? options.outWidth
                : options.outHeight;
        int reqW = isReqWidthLarger ? reqWidth : reqHeight;
        int reqH = isReqWidthLarger ? reqHeight : reqWidth;
        int inSampleSize = 1;

        if (height > reqH || width > reqW) {
            final int heightRatio = Math.round((float) height / (float) reqH);
            final int widthRatio = Math.round((float) width / (float) reqW);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }


    /**
     * 设置渲染的模糊程度, 25f是最大模糊度
     *
     * @param sentBitmap
     * @param radius
     * @param scale
     * @return
     */
    public static Bitmap doBlur(Bitmap sentBitmap, int radius, float scale) {
        if (null == sentBitmap || sentBitmap.isRecycled()) {
            return null;
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            return blurBitmap(context, sentBitmap, radius, (int) (sentBitmap.getWidth() * scale), (int) (sentBitmap.getHeight() * scale));
//        }

        // 将缩小后的图片做为预渲染的图片
        Bitmap bitmap = Bitmap.createScaledBitmap(sentBitmap, (int) (sentBitmap.getWidth() * scale), (int) (sentBitmap.getHeight() * scale), false);
        // 创建一张渲染后的输出图片
        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

    public static void recycle(Bitmap... bitmap) {
        if(null==bitmap){
            return;
        }

        for(Bitmap b:bitmap){
            if (null != b&&!b.isRecycled()) {
                b.recycle();
                b = null;
            }
        }


    }

    public static void drawCover(Bitmap bitmap, @ColorInt int color) {
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color, PorterDuff.Mode.DARKEN);

    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 获取 drawable 长宽
        int width = drawable.getIntrinsicWidth();
        int heigh = drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, width, heigh);
        // 获取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 创建bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, heigh, config);
        // 创建bitmap画布
        Canvas canvas = new Canvas(bitmap);
        // 将drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        c.translate(-v.getScrollX(), -v.getScrollY());
        v.draw(c);
        return screenshot;
    }

    public static BitmapFactory.Options getBitmapOption(Context context, int res) {
        int[] screenSize = AndroidTools.getScreenSize(context);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), res);
        if (options.outWidth > screenSize[0] && options.outHeight > screenSize[1]) {
            options.inSampleSize = computeSampleSize(options, -1, screenSize[0] * screenSize[1]);
        }
        options.inJustDecodeBounds = false;
        return options;
    }

    public static int[] getBitmapSize(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    public static boolean save(Bitmap bitmap, String path) {
        if (bitmap == null || TextUtils.isEmpty(path)) {
            return false;
        }
        OutputStream out = null;
        try {
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }

            out = new FileOutputStream(path);
            Bitmap.CompressFormat format = null;
            String ext = FileUtils.getFileExt(path);
            if (ext != null) {
                if (ext.equals("jpg")) {
                    format = Bitmap.CompressFormat.JPEG;
                } else if (ext.equals("png")) {
                    format = Bitmap.CompressFormat.PNG;
                }
            }
            if (format == null) {
                format = Bitmap.CompressFormat.PNG;
            }
            return bitmap.compress(format, 100, out);
        } catch (Exception e) {
            Logger.error(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    /**
     * @param maxSize //图片允许最大空间   单位：KB
     */
    public static Bitmap zip(Bitmap bitMap, double maxSize) {
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                    bitMap.getHeight() / Math.sqrt(i));
        }
        return bitMap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     */
    private static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                    double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
    }

}
