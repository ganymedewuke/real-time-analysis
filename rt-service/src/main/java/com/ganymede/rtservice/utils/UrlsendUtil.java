package com.ganymede.rtservice.utils;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlsendUtil {
    public static void sendMessage(String address,String message){
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setAllowUserInteraction(true);
            connection.setDefaultUseCaches(false);
            connection.setReadTimeout(6 * 1000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            BufferedOutputStream out = new BufferedOutputStream(outputStream);
            out.write(message.getBytes());
            out.flush();


            String temp = "";
            InputStream in = connection.getInputStream();
            byte[] bytes = new byte[1024];

            while (in.read(bytes, 0, 1024) != -1) {
                temp += new String(bytes);
            }
//            System.out.println(connection.getResponseCode());
//            System.out.println(temp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
