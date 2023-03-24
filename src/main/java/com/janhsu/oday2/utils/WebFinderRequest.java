package com.janhsu.oday2.utils;



import javax.net.ssl.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebFinderRequest {
    static int timeout = 8000;



    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    String pattern = "http|https:\\/\\/.*";
    Pattern r = Pattern.compile(pattern);
    public  HttpURLConnection getConnection(String urlNameString) throws Exception {

        Matcher matcher = r.matcher(urlNameString);
        if (!matcher.find()){
            urlNameString = "http://"+urlNameString;
        }
        URL realUrl = new URL(urlNameString);
        HttpURLConnection connection = null;
        trustAllHttpsCertificates();
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
        connection = (HttpURLConnection)realUrl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("Connection", "close");
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.setInstanceFollowRedirects(false);
        return connection;
    }



    public String sendGet(String url) {
        String resStatusCode = "";
        try {
            HttpURLConnection connection = getConnection(url);
            connection.connect();
            Map<String, List<String>> reqheaders = connection.getHeaderFields();
            Set<String> keys = reqheaders.keySet();
            for (String key : keys) {
                String val = connection.getHeaderField(key);
                if (key == null) {
                    if (val.contains("HTTP/")){
                        resStatusCode = val.substring(9,12);
                    }
                    continue;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resStatusCode;
    }
}
