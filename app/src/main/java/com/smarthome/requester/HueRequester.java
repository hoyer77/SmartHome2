package com.smarthome.requester;

import static java.lang.Math.round;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.smarthome.Color;
import com.smarthome.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class HueRequester {
    private static final String QUERY_HUE = "http://goetschmann.internet-box.ch:19166/api/b2rsZLRMfjYw5lF-l4KsCQgyXewWzW3CCTNZ-yZU";
    Context appContext;

    final JSONObject[] waitResponse = new JSONObject[1];

    public HueRequester(Context appContext) {
        this.appContext = appContext;
    }

    public interface VolleyResponseListenerHue {
        void onError(String message);

        void onResponse(String hueState);

        void onResponse(Integer hueState);

        void onResponse(Boolean hueState);

        void onResponse(float sliderStatus);

        void onResponse(JSONArray lightxy) throws JSONException;
    }

    public interface ServerCallback {
        void onSuccess(JSONArray result);
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
            public void onResponse(JSONArray sliderStatus) {

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

    public void hueRequesterSliderChangeLight(VolleyResponseListenerHue volleyResponseListenerHue, String light, Integer newValue) {

        String url = QUERY_HUE;
        url += "/lights/"+light+"/state";
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

    public void hueRequesterSliderChangeLightColor(VolleyResponseListenerHue volleyResponseListenerHue, String light, double[] xy) {

        String url = QUERY_HUE;
        url += "/lights/"+light+"/state";
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
                    JSONArray jsonXY = new JSONArray();
                    jsonXY.put(xy[0]);
                    jsonXY.put(xy[1]);
                    try {
                        jsonBody.put("xy", jsonXY);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String requestBody = jsonBody.toString();
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException | JSONException uee) {
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

    public void hueRequestergetStateAndScene(VolleyResponseListenerHue volleyResponseListenerHue, String gruppe, Map<String, String> possibleScenes) {

        String url = QUERY_HUE;
        url += "/groups/"+gruppe;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean returnStatus = false;
                // Zuerst müssen wir den Status und die Lichter der Gruppe herausfinden
                try {
                    JSONObject status = response.getJSONObject("action");
                    returnStatus = status.getBoolean("on");
                    // Welche Lichter sind in dieser Gruppe
                    JSONArray lichter = response.getJSONArray("lights");
                    // Wenn das Licht angeschaltet ist, dann müssen wir alle Lichter überprüfen welche Scene sie haben könnten
                    if (returnStatus==true) {
                        boolean finalReturnStatus = returnStatus;
                        new HueRequester(appContext).hueRequestergetSceneToLight(new HueRequester.VolleyResponseListenerHue() {
                            @Override
                            public void onResponse(float sliderStatus) {
                                volleyResponseListenerHue.onResponse(finalReturnStatus);
                            }

                            @Override
                            public void onResponse(JSONArray lightxy) {
                                volleyResponseListenerHue.onResponse(finalReturnStatus);
                            }

                            @Override
                            public void onError(String message) {
                                volleyResponseListenerHue.onResponse(finalReturnStatus);
                            }

                            @Override
                            public void onResponse(String hueState) {
                                JSONArray stateScene = new JSONArray();
                                stateScene.put(finalReturnStatus);
                                stateScene.put(hueState);
                                try {
                                    volleyResponseListenerHue.onResponse(stateScene);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onResponse(Integer hueState) {
                                volleyResponseListenerHue.onResponse(finalReturnStatus);
                            }

                            @Override
                            public void onResponse(Boolean hueState) {
                                volleyResponseListenerHue.onResponse(finalReturnStatus);

                            }
                        }, lichter, possibleScenes);
                    }
                } catch (JSONException | IOException e) {
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

    public void hueRequestergetSceneToLight(VolleyResponseListenerHue volleyResponseListenerHue, JSONArray lights, Map<String, String> possibleScenes) throws JSONException, IOException {

        Integer lichtID;
        JSONArray lichtEinstellung = new JSONArray();
        final Integer[] readLicht = {0};

        // Wir lesen für jedes Licht die aktuelle Einstellung

        for (int i=0; i< lights.length();i++)
        {
          lichtID = lights.getInt(i);
            Integer finalLichtID = lichtID;
            new HueRequester(appContext).hueRequesterLightDetail(new HueRequester.VolleyResponseListenerHue() {
                @Override
                public void onResponse(float sliderStatus) {
                }

                @Override
                public void onResponse(JSONArray lightxy) throws JSONException {
                    lichtEinstellung.put(finalLichtID, lightxy);
                    readLicht[0]++;
                    if (readLicht[0] == lights.length())
                    {
                        // Wir haben alle Lichter gelesen Nun müssen wir alle möglichen Szenen anschauen um zu sehen welche Szene aktiv ist
                        new HueRequester(appContext).hueRequesterLightScene(new HueRequester.VolleyResponseListenerHue() {
                            @Override
                            public void onResponse(float sliderStatus) {
                            }

                            @Override
                            public void onResponse(JSONArray lightxy) throws JSONException {
                            }

                            @Override
                            public void onError(String message) {
                            }

                            @Override
                            public void onResponse(String hueState) {
                                volleyResponseListenerHue.onResponse(hueState);
                            }

                            @Override
                            public void onResponse(Integer hueState) {
                            }

                            @Override
                            public void onResponse(Boolean hueState) {
                            }
                        }, lichtEinstellung, possibleScenes);

                    }
                }

                @Override
                public void onError(String message) {
                }

                @Override
                public void onResponse(String hueState) {
                }

                @Override
                public void onResponse(Integer hueState) {
                }

                @Override
                public void onResponse(Boolean hueState) {
                }
            }, lichtID);

        }

    }

    public void hueRequesterLightDetail(VolleyResponseListenerHue volleyResponseListenerHue, Integer light) {

        String url = QUERY_HUE;
        url += "/lights/"+light;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject status = response.getJSONObject("state");
                    JSONArray lightxy = new JSONArray();
                    lightxy.put(status);
                    volleyResponseListenerHue.onResponse(lightxy);
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

    public void hueRequesterLightScene(VolleyResponseListenerHue volleyResponseListenerHue, JSONArray light, Map<String, String> possibleScenes) {

        String scene;
        final boolean[] gefunden = {false};
        final Integer[] allRead = {0};
        for (Map.Entry<String, String> entry : possibleScenes.entrySet()) {
            scene = entry.getValue();
            new HueRequester(appContext).hueRequesterSceneDetail(new HueRequester.VolleyResponseListenerHue() {
                @Override
                public void onResponse(float sliderStatus) {
                }

                @Override
                public void onResponse(JSONArray response) throws JSONException {
                    allRead[0]++;
                    //Wir vergleichen die Einstellungen der Szene mit den Lichtern
                    JSONObject sceneDetail = response.getJSONObject(1);
                    JSONArray lightID = sceneDetail.names();
                    boolean checkLight = true;
                    for (int i=0;i<lightID.length();i++)
                    {
                        JSONObject ligthDetail = sceneDetail.getJSONObject(lightID.getString(i));
                        if (ligthDetail.has("xy"))
                        {
                            // Wir vergleichen das xy mit dem xy des Lichtes
                            JSONArray lightCompare = light.getJSONArray(lightID.getInt(i));
                            JSONObject temp = lightCompare.getJSONObject(0);
                            if (temp.has("xy"))
                            {
                                JSONArray lightXY = temp.getJSONArray("xy");

                                if (!ligthDetail.getJSONArray("xy").equals(lightXY))
                                {
                                    checkLight = false;
                                }
                            } else {
                                checkLight = false;
                            }
                        } else {
                            Integer sceneBri = ligthDetail.getInt("bri");
                            Integer sceneCt = ligthDetail.getInt("ct");
                            JSONArray lightCompare = light.getJSONArray(lightID.getInt(i));
                            JSONObject temp = lightCompare.getJSONObject(0);

                            Integer lightBri = temp.getInt("bri");
                            Integer lightCt = temp.getInt("ct");
                            if (!(sceneBri.equals(lightBri)) || !(sceneCt.equals(lightCt) ))
                            {
                                checkLight = false;
                            }

                        }


                    }

                    if (checkLight==true)
                    {
                        String sceneName = response.getString(0);
                        gefunden[0] = true;
                        volleyResponseListenerHue.onResponse(sceneName);
                    }
                    // Wenn wir die passende Szene gefunden haben, dann geben wir den Namen zurück
                    if ((allRead[0] == possibleScenes.size()) && gefunden[0] ==false)
                    {
                        // Wenn wir alle Antworten haben aber nichts gefunden haben, dann senden wir ein "Manuell" zurück
                        volleyResponseListenerHue.onResponse("manuell");
                    }
                }

                @Override
                public void onError(String message) {
                }

                @Override
                public void onResponse(String hueState) {
                }

                @Override
                public void onResponse(Integer hueState) {
                }

                @Override
                public void onResponse(Boolean hueState) {
                }
            }, scene);

        }
    }

    public void hueRequesterSceneDetail(VolleyResponseListenerHue volleyResponseListenerHue, String scene) {

        String url = QUERY_HUE;
        url += "/scenes/"+scene;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject status = response.getJSONObject("lightstates");
                    String name = response.getString("name");
                    JSONArray antwort = new JSONArray();
                    antwort.put(name);
                    antwort.put(status);
                    volleyResponseListenerHue.onResponse(antwort);
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

    public void hueRequesterLightsDetail(VolleyResponseListenerHue volleyResponseListenerHue, String light) {

        String url = QUERY_HUE;
        url += "/lights/"+light;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Wenn wir die Lichter der Gruppe kennen, dann lesen wir das Detail des Lichtes aus und geben dies als Array zurück
                    JSONObject state = response.getJSONObject("state");
                    String bri = state.getString("bri");
                    String name = response.getString("name");
                    JSONArray antwort = new JSONArray();
                    antwort.put(bri);
                    antwort.put(name);
                    antwort.put(light);
                    volleyResponseListenerHue.onResponse(antwort);
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

    public void hueRequesterLightsGroup(VolleyResponseListenerHue volleyResponseListenerHue, String group) {

        String url = QUERY_HUE;
        url += "/groups/"+group;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Wenn wir die Lichter der Gruppe kennen, dann lesen wir das Detail des Lichtes aus und geben dies als Array zurück
                    JSONArray lights = response.getJSONArray("lights");
                    JSONArray antwort = new JSONArray();
                    final Integer[] getResponse = {0};
                    for (int i=0;i<lights.length();i++) {
                        String light = lights.getString(i);
                        // Nun rufen wir pro Licht das Detail auf
                        new HueRequester(appContext).hueRequesterLightsDetail(new HueRequester.VolleyResponseListenerHue() {
                            @Override
                            public void onResponse(float sliderStatus) {
                            }

                            @Override
                            public void onResponse(JSONArray lightsResponseDetail) throws JSONException {
                                JSONArray lightDetail = new JSONArray();
                                String bri = lightsResponseDetail.getString(0);
                                String name = lightsResponseDetail.getString(1);
                                String id = lightsResponseDetail.getString(2);
                                lightDetail.put(light);
                                lightDetail.put(bri);
                                lightDetail.put(name);
                                lightDetail.put(id);
                                // Wir hängen noch das Detail von der Helligkeit und den Namen daran
                                antwort.put(lightDetail);
                                getResponse[0]++;
                                if (getResponse[0] == lights.length()) {
                                    volleyResponseListenerHue.onResponse(antwort);
                                }
                            }

                            @Override
                            public void onError(String message) {
                            }

                            @Override
                            public void onResponse(String hueState) {
                            }

                            @Override
                            public void onResponse(Integer hueState) {
                            }

                            @Override
                            public void onResponse(Boolean hueState) {
                            }
                        }, light);
                    }
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
