package com.smarthome.requester;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.smarthome.HttpsTrustManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TahomaRequester {

    Context appContext;

    public TahomaRequester(Context appContext) {
        this.appContext = appContext;
    }

    public void storenSteuerung(String command, String token) throws JSONException, IOException {

        String url = "https://goetschmann.internet-box.ch:8443/enduser-mobile-web/1/enduserAPI/exec/apply";

        String json = "{\n" +
                "  \"label\": \"test\",\n" +
                "  \"actions\": [\n" +
                "    {\n" +
                "      \"deviceURL\": \"rts://2013-4957-8385/16725194\",\n" +
                "      \"commands\": \n" +
                "      [\n" +
                "        { \"name\": \""+command+"\" }  \n" +
                "        ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JSONObject jsonObject = new JSONObject(json) ;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> System.out.println("Response"+
                        response.toString()),
                System.out::println)
        {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer "+token);
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(appContext);
        HttpsTrustManager.allowAllSSL();
        requestQueue.add(request);
    }

    public void dachSteuerung(String command, String token) throws JSONException, IOException {

        String url = "https://goetschmann.internet-box.ch:8443/enduser-mobile-web/1/enduserAPI/exec/apply";

        String json = "{\n" +
                "  \"label\": \"test\",\n" +
                "  \"actions\": [\n" +
                "    {\n" +
                "      \"deviceURL\": \"rts://2013-4957-8385/16730853\",\n" +
                "      \"commands\": \n" +
                "      [\n" +
                "        { \"name\": \""+command+"\" }  \n" +
                "        ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JSONObject jsonObject = new JSONObject(json) ;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> System.out.println("Response"+
                        response.toString()),
                System.out::println)
        {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer "+token);
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(appContext);
        HttpsTrustManager.allowAllSSL();
        requestQueue.add(request);
    }
}
