package es.source.code.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by SamChen on 2017/11/5.
 */

public class HttpURLUtils {
    public static String getJsonData(InputStream inputStream){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int len = 0;
        String result = null;
        try {
            while ((len = inputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer,0,len);
            }
            inputStream.close();
            result = new String(byteArrayOutputStream.toByteArray(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
