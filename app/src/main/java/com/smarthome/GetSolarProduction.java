package com.smarthome;

import android.content.Context;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class GetSolarProduction {

    private static final String QUERY_SOLAR_PRODUCTION = "http://goetschmann.internet-box.ch:19155/solar_api/v1/GetInverterRealtimeData.cgi?Scope=Device&DataCollection=CommonInverterData&DeviceId=1";
    Context appContextSolar;

    public GetSolarProduction(Context appContext) {
        this.appContextSolar = appContext;
    }

    public interface VolleyResponseListener {
        void onError(String message, Integer savePower);

        void onResponse(Integer power, Integer solarPower);
    }

    public void GetSolarProduction(VolleyResponseListener volleyResponseListener, Integer savePower) throws IOException {
        // Instantiate the RequestQueue.
        String url = QUERY_SOLAR_PRODUCTION;
        JsonObjectRequest requestSolarProduction = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject solarInfo = response.getJSONObject("Body");
                    solarInfo = solarInfo.getJSONObject("Data");
                    // Es muss überprüft werden ob es einen Wert dazu gibt!
                    Integer solarPower;
                    if (solarInfo.has("PAC"))
                    {
                        solarInfo = solarInfo.getJSONObject("PAC");
                        solarPower = (Integer) solarInfo.get("Value");
                    } else {
                        solarPower = 0;
                    }
                    volleyResponseListener.onResponse(solarPower, savePower);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("0", savePower);
            }
        });
        MySingleton.getInstance(appContextSolar).addToRequestQueue(requestSolarProduction);
    }

}
