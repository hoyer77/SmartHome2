package com.smarthome.requester;

import static java.lang.Math.round;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.smarthome.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class HueRequester {
    private static final String QUERY_HUE = "http://goetschmann.internet-box.ch:19166/api/b2rsZLRMfjYw5lF-l4KsCQgyXewWzW3CCTNZ-yZU";
    Context appContext;

    public HueRequester(Context appContext) {
        this.appContext = appContext;
    }

    public interface VolleyResponseListenerHue {
        void onError(String message);

        void onResponse(String hueState);

        void onResponse(Integer hueState);

        void onResponse(Boolean hueState);

        void onResponse(float sliderStatus);
    }

    public void hueRequesterLightChange(VolleyResponseListenerHue volleyResponseListenerHue, String gruppe) {

        String url = QUERY_HUE;
        url += "/groups/"+gruppe+"/action";


        String finalUrl = url;
        hueRequesterLightState(new VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {

            }

            @Override
            public void onResponse(Boolean hueState) {
                boolean newState;
                if (hueState) {
                    newState = false;
                } else {
                    newState = true;
                }

                StringRequest request = new StringRequest(Request.Method.PUT, finalUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String status = response;
                        volleyResponseListenerHue.onResponse(status);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(appContext, "Test", Toast.LENGTH_SHORT).show();
                    }
                }
                )
                {
                    @Override
                    public byte[] getBody () throws AuthFailureError {
                        try {
                            // request body goes here
                            JSONObject jsonBody = new JSONObject();
                            try {
                                jsonBody.put("on", newState);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String requestBody = jsonBody.toString();
                            return requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", "utf-8");
                            return null;
                        }
                    }
                }

                        ;
                MySingleton.getInstance(appContext).addToRequestQueue(request);
            };

            @Override
            public void onError(String message) {
            }

            @Override
            public void onResponse(String hueState) {
            }

            @Override
            public void onResponse(Integer hueState) {
            }
        },gruppe);
    }



    public void hueRequesterLightState(VolleyResponseListenerHue volleyResponseListenerHue, String gruppe) {

        String url = QUERY_HUE;
        url += "/groups/"+gruppe;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean lightStatus;
                try {
                    JSONObject status = response.getJSONObject("state");
                    lightStatus = (Boolean) status.getBoolean("all_on");
                    volleyResponseListenerHue.onResponse(lightStatus);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        MySingleton.getInstance(appContext).addToRequestQueue(request);
    }

    public void hueRequesterSliderChange(VolleyResponseListenerHue volleyResponseListenerHue, String gruppe, Integer newValue) {

        String url = QUERY_HUE;
        url += "/groups/"+gruppe+"/action";
        String finalUrl = url;

        StringRequest request = new StringRequest(Request.Method.PUT, finalUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String status = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(appContext, "Test", Toast.LENGTH_SHORT).show();
            }
        }
        )
        {
            @Override
            public byte[] getBody () throws AuthFailureError {
                try {
                    // request body goes here
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("bri", newValue);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String requestBody = jsonBody.toString();
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", "utf-8");
                    return null;
                }
            }
        };
        MySingleton.getInstance(appContext).addToRequestQueue(request);
    }

    public void hueRequesterSceneChange(VolleyResponseListenerHue volleyResponseListenerHue, String gruppe, String newValue) {

        String url = QUERY_HUE;
        url += "/groups/"+gruppe+"/action";
        String finalUrl = url;

        StringRequest request = new StringRequest(Request.Method.PUT, finalUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String status = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(appContext, "Test", Toast.LENGTH_SHORT).show();
            }
        }
        )
        {
            @Override
            public byte[] getBody () throws AuthFailureError {
                try {
                    // request body goes here
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("scene", newValue);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String requestBody = jsonBody.toString();
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", "utf-8");
                    return null;
                }
            }
        };
        MySingleton.getInstance(appContext).addToRequestQueue(request);
    }

    public void hueRequesterSliderState(VolleyResponseListenerHue volleyResponseListenerHue, String gruppe) {

        String url = QUERY_HUE;
        url += "/groups/"+gruppe;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                float sliderStatus;
                try {
                    JSONObject status = response.getJSONObject("action");
                    Integer sliderInt = status.getInt("bri");
                    double percentPerBri = 0.3937;
                    percentPerBri = round(percentPerBri*sliderInt);
                    sliderStatus = (float) percentPerBri;
                    volleyResponseListenerHue.onResponse(sliderStatus);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        MySingleton.getInstance(appContext).addToRequestQueue(request);
    }
}
