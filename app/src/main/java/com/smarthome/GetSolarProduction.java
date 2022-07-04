package com.smarthome;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smarthome.requester.TigoTokenRequester;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getSolarProduction(VolleyResponseListener volleyResponseListener, Integer savePower) throws IOException {
        // Instantiate the RequestQueue.
        JsonObjectRequest requestSolarProduction = new JsonObjectRequest(Request.Method.GET, QUERY_SOLAR_PRODUCTION, null, response -> {
            try {
                JSONObject solarInfo = response.getJSONObject("Body");
                solarInfo = solarInfo.getJSONObject("Data");
                // Es muss überprüft werden ob es einen Wert dazu gibt!
                int solarPower;
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
        }, error ->
        {
            new TigoTokenRequester(appContextSolar).getTigoToken(new TigoTokenRequester.VolleyResponseListenerTigo() {
                @Override
                public void onError(String message) {
                    volleyResponseListener.onError("0", savePower);
                }

                @Override
                public void onResponse(String tigoPower) {
                    volleyResponseListener.onError(tigoPower, savePower);
                }
            });
        });
        MySingleton.getInstance(appContextSolar).addToRequestQueue(requestSolarProduction);
    }

}
