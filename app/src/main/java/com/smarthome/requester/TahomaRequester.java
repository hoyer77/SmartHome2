package com.smarthome.requester;

import static java.lang.Math.round;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarthome.HttpsTrustManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class TahomaRequester {

    private static final String AUTHORIZATION_URL = "https://ha101-1.overkiz.com";
    private static final String TAHOMA_PIN = "2013-4957-8385";
    private static final String TAHOMA_MAIL = "stefan@goetschmann.ch";
    private static final String TAHOMA_PWD = "Stefan77_";
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
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response"+
                                response.toString());
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
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
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response"+
                                response.toString());
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
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
