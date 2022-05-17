package com.smarthome.requester;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.smarthome.HttpsTrustManager;
import com.smarthome.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TahomaTokenRequester {

    private static final String AUTHORIZATION_URL = "https://ha101-1.overkiz.com";
    private static final String TAHOMA_PIN = "2013-4957-8385";
    private static final String TAHOMA_MAIL = "stefan@goetschmann.ch";
    private static final String TAHOMA_PWD = "Stefan77_";
    Context appContext;

    public TahomaTokenRequester(Context appContext) {
        this.appContext = appContext;
    }

    public interface VolleyResponseListenerTahoma {
        void onError(String message);

        void onResponse(String token);
    }

    public void getAuthorization(VolleyResponseListenerTahoma volleyResponseListenerTahoma) {

        String url = AUTHORIZATION_URL+"/enduser-mobile-web/enduserAPI/login";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    JSONObject json = null;
                    JSONObject checkSuccess = null;
                    boolean check = false;
                    String JSessionID = "";
                    try {
                        json = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (json != null) {
                            checkSuccess = new JSONObject((String) json.get("data"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (checkSuccess != null) {
                            check = (boolean) checkSuccess.get("success");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (check)
                    {
                        // Wir müssen die SessionID herauslesen
                        try {
                            JSessionID = (String) json.get("header");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String[] strArray = JSessionID.split(";", 2);
                        String transform = strArray[0];
                        strArray = transform.split("=", 2);
                        JSessionID = strArray[1];
                    }
                    new TahomaTokenRequester(appContext).getToken(new VolleyResponseListenerTahoma() {

                        @Override
                        public void onError(String message) {
                        }

                        @Override
                        public void onResponse(String responseToken) {
                            volleyResponseListenerTahoma.onResponse(responseToken);
                        }
                    }, JSessionID);
                },
                error -> volleyResponseListenerTahoma.onError(""))
        {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("userId",TAHOMA_MAIL);
                params.put("userPassword",TAHOMA_PWD);
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                String responseData;
                try {
                    parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

                    JSONObject allResponse = new JSONObject();
                    allResponse.put("data", parsed);
                    if (response.headers != null) {
                        allResponse.put("header", response.headers.get("Set-Cookie"));
                    }
                    responseData = allResponse.toString(2);

                } catch (UnsupportedEncodingException | JSONException e) {
                    responseData = new String(response.data);

                }

                return Response.success(responseData, HttpHeaderParser.parseCacheHeaders(response));
            }

        };

        HttpsTrustManager.allowAllSSL();
        MySingleton.getInstance(appContext).addToRequestQueue(request);
    }

    public void getToken(VolleyResponseListenerTahoma volleyResponseListenerTahoma, String JSessionID) {

        String url = AUTHORIZATION_URL+"/enduser-mobile-web/enduserAPI/config/"+TAHOMA_PIN+"/local/tokens/generate";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            String token = null;
            try {
                token = (String) response.get("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String finalToken = token;
            new TahomaTokenRequester(appContext).registrationToken(new VolleyResponseListenerTahoma() {

                @Override
                public void onError(String message) {
                }

                @Override
                public void onResponse(String responseToken) {
                    volleyResponseListenerTahoma.onResponse(finalToken);

                }
            }, JSessionID, token);
        }, error -> {
        }
        )

        {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Cookie", "JSESSIONID="+JSessionID);
                return headers;
            }
        };

        HttpsTrustManager.allowAllSSL();
        MySingleton.getInstance(appContext).addToRequestQueue(request);
    }

    public void registrationToken(VolleyResponseListenerTahoma volleyResponseListenerTahoma, String JSessionID, String token) {

        String url = AUTHORIZATION_URL+"/enduser-mobile-web/enduserAPI/config/"+TAHOMA_PIN+"/local/tokens";

        Map<String, String> postParam= new HashMap<>();
        postParam.put("label", token);
        postParam.put("token", token);
        postParam.put("scope", "devmode");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(postParam), response -> volleyResponseListenerTahoma.onResponse(response.toString()), error -> volleyResponseListenerTahoma.onError("")
        )
        {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Cookie", "JSESSIONID="+JSessionID);
                return headers;
            }
        };

        HttpsTrustManager.allowAllSSL();
        MySingleton.getInstance(appContext).addToRequestQueue(request);
    }
}
