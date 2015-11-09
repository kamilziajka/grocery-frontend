package com.kazet.grocery;

import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BackendClient {
    private static final String HOST = "http://10.0.2.2:8080";
    private static final String LOGIN_POSTFIX = "/login";
    private static final String GROCERIES_POSTFIX = "/groceries";

    private HttpClient client;

    private static BackendClient instance;

    public static BackendClient getInstance() {
        if (instance == null) {
            instance = new BackendClient();
        }

        return instance;
    }

    private BackendClient() {
        HttpParams params = new BasicHttpParams();
        client = new DefaultHttpClient(params);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    Boolean login(String name, String password) {
        int status = 400;

        try {
            JSONObject body = new JSONObject();

            body.put("name", name);
            body.put("password", password);

            StringEntity stringEntity = new StringEntity(body.toString());

            HttpPost post = new HttpPost(HOST + LOGIN_POSTFIX);

            post.setEntity(stringEntity);
            post.setHeader("Content-Type", "application/json");

            HttpResponse response = client.execute(post);

            response.getEntity().consumeContent();

            status = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status >= 200 && status < 300;
    }

    List<Item> getGroceryList() {
        List<Item> items = new ArrayList<Item>();

        try {
            HttpGet get = new HttpGet(HOST + GROCERIES_POSTFIX);

            HttpResponse response = client.execute(get);

            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                InputStreamReader streamReader = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(streamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String jsonData = stringBuilder.toString();

                JSONObject jsonObject = new JSONObject(jsonData);

                for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
                    String name = (String) iterator.next();
                    Integer quantity = jsonObject.getInt(name);

                    items.add(new Item(name, quantity));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    void update(Item item) {
        try {
            HttpPut put = new HttpPut(HOST + GROCERIES_POSTFIX + '/' + item.getName() + '/' + item.getQuantity());

            HttpResponse response = client.execute(put);

            response.getEntity().consumeContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void delete(Item item) {
        try {
            HttpDelete put = new HttpDelete(HOST + GROCERIES_POSTFIX + '/' + item.getName());

            HttpResponse response = client.execute(put);

            response.getEntity().consumeContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
