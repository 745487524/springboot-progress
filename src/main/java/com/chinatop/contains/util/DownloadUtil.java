package com.chinatop.contains.util;


import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUtil {

    private static String filePath = "D:/git/Git-2.22.0-64-bit.exe";
    private static String downloadURL = "https://github.com/git-for-windows/git/releases/download/v2.22.0.windows.1/Git-2.22.0-64-bit.exe";

    private static void downloadURL() {
        HttpURLConnection conn = null;
        File file = new File(filePath);
        InputStream is = null;
        FileOutputStream fos = null;
        ByteOutputStream btos = null;
        System.out.println("建立连接……");
        try {
            URL url = new URL(downloadURL);
            conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            int length;
            byte[] buffer = new byte[1024];
            fos = new FileOutputStream(file);
            btos = new ByteOutputStream();
            while ((length = is.read(buffer)) != -1) {
                btos.write(buffer, 0, length);
                System.out.println("下载进度：" + btos.getCount());
            }
            byte[] byteBuffer = btos.getBytes();
            fos.write(byteBuffer);
            System.out.println("执行完成！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (btos != null) {
                    btos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        downloadURL();
    }
}
