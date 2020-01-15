package com.alan.common;

import android.os.Build;

import java.util.UUID;

public class UUIDTools {


    public static String createBoundID() {

        StringBuilder sb = new StringBuilder();
        sb.append("35");
        sb.append(Build.BOARD.length() % 10);
        sb.append(Build.BRAND.length() % 10);
        sb.append(Build.CPU_ABI.length() % 10);
        sb.append(Build.DISPLAY.length() % 10);
        sb.append(Build.ID.length() % 10);
        sb.append(Build.MANUFACTURER.length() % 10);
        sb.append(Build.MODEL.length() % 10);
        sb.append(Build.PRODUCT.length() % 10);
        sb.append(Build.TAGS.length() % 10);
        sb.append(Build.TYPE.length() % 10);
        sb.append(Build.USER.length() % 10);
        try {
            String serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(sb.toString().hashCode(), serial.hashCode()).toString();
        } catch (IllegalAccessException e) {
            Logger.error(e);
        } catch (NoSuchFieldException e) {
            Logger.error(e);
        }
        return UUID.randomUUID().toString().replace("-", "") + "1";
    }

}
