package com.janhsu.oday2.utils;

import com.janhsu.oday2.entity.ConnResult;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShellCheckRequest {
    static int timeout = 8000;

    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyConfig.IP_ADDR, ProxyConfig.PORT_ADDR));

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

    public HttpURLConnection getConnection(String urlNameString) throws Exception {

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
        if (ProxyConfig.IS_PROXY.equals("true")){
            connection = (HttpURLConnection)realUrl.openConnection(proxy);
        }
        else {
            connection = (HttpURLConnection)realUrl.openConnection();
        }
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("Connection", "close");
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.setInstanceFollowRedirects(false);
        return connection;
    }

    String pattern1 = "/$";
    Pattern r1 = Pattern.compile(pattern1);
    public String getShellCheck(String url, String path) {
        Matcher matcher1 = r1.matcher(url);
        if (matcher1.find()){
            url = url.substring(0, url.length() - 1);//删除url后面的/
        }
        String resBody = "";
        String resHeaders = "";
        String resStatusCode = "";
        BufferedReader in = null;
        try {
            String urlNameString = "";
            urlNameString = url + path;
            HttpURLConnection connection = getConnection(urlNameString);
            connection.connect();
            Map<String, List<String>> reqheaders = connection.getHeaderFields();
            Set<String> keys = reqheaders.keySet();
            for (String key : keys) {
                String val = connection.getHeaderField(key);
                if (key == null) {
                    resHeaders = resHeaders + val + "\n";
                    if (val.contains("HTTP/")){
                        resStatusCode = val.substring(9,12);
                    }
                    continue;
                }
                resHeaders = resHeaders + key + ": " + val + "\n";
            }
            if (resStatusCode.equals("400")||resStatusCode.equals("500")){//400的情况会导致getInputStream()直接抛出异常，要用getErrorStream()
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }
            else {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            }
            String line;
            while ((line = in.readLine()) != null)
                resBody  = resBody + line;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return resBody;
    }
}
