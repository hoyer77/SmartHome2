package com.smarthome.requester;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.smarthome.GetSolarProduction;
import com.smarthome.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShellyRequester {

    private static final String QUERY_CONSUMPTION = "https://shelly-28-eu.shelly.cloud/device/status";
    Context appContext;

    public ShellyRequester(Context appContext) {
        this.appContext = appContext;
    }

    public interface VolleyResponseListenerShelly {
        void onError(String message);

        void onResponse(String shellyPower);

        void onResponse(Integer shellyPower);
    }

    public void shellyRequesterEM3(VolleyResponseListenerShelly volleyResponseListenerShelly, String auth_key, String id) {

        String url = QUERY_CONSUMPTION;
        final Integer[] savePower = new Integer[1];

        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    JSONArray jsonArr = new JSONArray();
                    @Override
                    public void onResponse(String response) {
                        try {
                            Integer power = 0;
                            Integer test;
                            JSONObject aktConsumption = new JSONObject(response);
                            aktConsumption = aktConsumption.getJSONObject("data");
                            aktConsumption = aktConsumption.getJSONObject("device_status");
                            JSONArray emeters = aktConsumption.getJSONArray("emeters");
                            for(int i = 0 ; i < emeters.length() ; i++){
                                test = (int) emeters.getJSONObject(i).getDouble("power");
                                power += test;
                            }
                            savePower[0] = Integer.parseInt(String.valueOf(power));

                            // Aktuelle Solarproduktion abfragen
                            final GetSolarProduction getSolarProduction = new GetSolarProduction(appContext);
                            final String[] solarPower = new String[1];
                            try {
                                getSolarProduction.GetSolarProduction(new GetSolarProduction.VolleyResponseListener() {
                                    @Override
                                    public void onError(String message, Integer savePower) {
                                        volleyResponseListenerShelly.onResponse(savePower);
                                    }


                                    @Override
                                    public void onResponse(Integer power, Integer response) {
                                        solarPower[0] = response.toString();
                                        response += power;
                                        volleyResponseListenerShelly.onResponse(response);
                                    }
                                    }, savePower[0]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }catch (JSONException err){
                            volleyResponseListenerShelly.onError("no Connect to catch");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListenerShelly.onError("Something wrong");
            }
        })

        {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("auth_key", auth_key);
                params.put("id", id);
                return params;
            }

        };
        MySingleton.getInstance(appContext).addToRequestQueue(request);
    }
    public void shellyRequester1PM(VolleyResponseListenerShelly volleyResponseListenerShelly, String auth_key, String id) {

        String url = QUERY_CONSUMPTION;

        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    JSONArray jsonArr = new JSONArray();
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject aktConsumption = new JSONObject(response);
                            aktConsumption = aktConsumption.getJSONObject("data");
                            aktConsumption = aktConsumption.getJSONObject("device_status");
                            JSONArray meter = aktConsumption.getJSONArray("meters");
                            Integer power = (int) Math.round(meter.getJSONObject(0).getDouble("power"));
                            volleyResponseListenerShelly.onResponse(power);
                        }catch (JSONException err){
                            volleyResponseListenerShelly.onResponse("no Connect to catch");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListenerShelly.onError("Something wrong");
            }
        })

        {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("auth_key", auth_key);
                params.put("id", id);
                return params;
            }
        };
        MySingleton.getInstance(appContext).addToRequestQueue(request);
    }
}
