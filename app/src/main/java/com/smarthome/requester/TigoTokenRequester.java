package com.smarthome.requester;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarthome.GetSolarProduction;
import com.smarthome.HttpsTrustManager;
import com.smarthome.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TigoTokenRequester {
    private static final String QUERY_SOLAR_PRODUCTION = "https://api2.tigoenergy.com/api/v3/data/summary?system_id=40804";

    Context appContext;

    public TigoTokenRequester(Context appContext) {
        this.appContext = appContext;
    }

    public interface VolleyResponseListenerTigo {
        void onError(String message);

        void onResponse(String token);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getTigoToken(VolleyResponseListenerTigo volleyResponseListenerTigo) {



        String username = "stefan@goetschmann.ch";
        String password = "Stefan77_";
        String url1 = "https://api2.tigoenergy.com";
        String path = "/api/v3/users/login";

        //cURL Command: curl -u admin:admin -X POST -F cmd="lockPage" -F path="/content/geometrixx/en/toolbar/contacts" -F "_charset_"="utf-8" http://localhost:4502/bin/wcmcommand

        //Equivalent command conversion for Java execution
        String[] command = { "curl", "--get", "-H", "Accept: application/json", "-u", username + ":" + password, url1+path };
        ProcessBuilder process = new ProcessBuilder(command);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            String result = builder.toString();
            JSONObject array = new JSONObject(result);
            JSONObject authArray = new JSONObject(String.valueOf(array.getJSONObject("user")));
            String auth = authArray.getString("auth");
            // Hier gehen wir mit dem auth weiter um die Daten auszulesen

            new TigoTokenRequester(appContext).getSolarProduction(new VolleyResponseListenerTigo() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(String tigoenergy) {
                    try {
                        JSONObject answer = new JSONObject(tigoenergy);
                        JSONObject tigoEngery = answer.getJSONObject("summary");
                        String lastPower = String.valueOf(tigoEngery.getInt("last_power_dc"));
                        if (lastPower == "1")
                        {
                            lastPower = "0";
                        }
                        volleyResponseListenerTigo.onResponse(lastPower);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, auth);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void getSolarProduction(VolleyResponseListenerTigo volleyResponseListenerTigo, String auth) {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, QUERY_SOLAR_PRODUCTION, null,
                response -> volleyResponseListenerTigo.onResponse(response.toString()),
                System.out::println)
        {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer "+auth);
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(appContext);
        HttpsTrustManager.allowAllSSL();
        requestQueue.add(request);
    }

}
