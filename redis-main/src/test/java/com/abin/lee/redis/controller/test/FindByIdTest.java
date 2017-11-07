package com.abin.lee.redis.controller.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.IOException;

public class FindByIdTest {
    public static String httpURL = "http://localhost:9000/redis/findById/4";

    @Test
    public void testDynamicFindById() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(httpURL);

//        httpGet.setHeader("Content-Type", "application/Json");//
//        httpGet.setHeader("accept-Type", "application/json");//

        HttpResponse httpResponse = null;
        String result = "";
        try {
            httpResponse = httpClient.execute(httpGet);
            BufferedInputStream buffer = new BufferedInputStream(
                    httpResponse.getEntity().getContent());
            byte[] bytes = new byte[1024];
            int line = 0;
            StringBuilder builder = new StringBuilder();
            while ((line = buffer.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, line, "UTF-8"));
            }
            result = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpGet.isAborted()) {
                httpGet.abort();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("result=" + result);
    }

}
