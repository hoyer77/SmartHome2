package com.smarthome;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smarthome.requester.HueRequester;
import com.google.android.material.slider.Slider;
import com.smarthome.requester.TahomaRequester;
import com.smarthome.requester.TahomaTokenRequester;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Terrasse extends Fragment {

    private Context context;

    private TextView pergolaScene;

    ImageView back;
    ImageView changeScene;
    Integer aktScene;
    CircleImageView lampe;
    CircleImageView onoff;
    CircleImageView dachOpen;
    CircleImageView dachClose;
    CircleImageView dachStop;
    CircleImageView storenClose;
    CircleImageView storenOpen;
    CircleImageView storenStop;

    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terrasse,container,false);

        context = view.getContext();

        lampe = view.findViewById(R.id.lampe);
        onoff = view.findViewById(R.id.onoff);

        // Wir versuchen einen Tahoma-Token zu lösen
        new TahomaTokenRequester(context).getAuthorization(new TahomaTokenRequester.VolleyResponseListenerTahoma() {

            @Override
            public void onError(String message) {
            }

            @Override
            public void onResponse(String responseToken) {
                token = responseToken;
            }
        });

        // Alle Szenen
        Map<String, String> buttonScenes = new HashMap<String, String>();
        buttonScenes.put("Hell", "ReLoEqI9tDzEfhD");
        buttonScenes.put("Weiss", "TclYvk-BorTvx6s");
        buttonScenes.put("Energie", "7j-EQYrM2UXNSmk");
        buttonScenes.put("1", "Hell");
        buttonScenes.put("2", "Weiss");
        buttonScenes.put("3", "Energie");

        Map<String, String> possibleScenes = new HashMap<String, String>();
        possibleScenes.put("Hell", "ReLoEqI9tDzEfhD");
        possibleScenes.put("Weiss", "TclYvk-BorTvx6s");
        possibleScenes.put("Energie", "7j-EQYrM2UXNSmk");

        pergolaScene = view.findViewById(R.id.akt_szene_text);
        aktScene = 0;

        // Auslesen des Lichtstatus und der aktiven Szene
        new HueRequester(context).hueRequestergetStateAndScene(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray stateScene) {
                try {
                    if (stateScene.getBoolean(0))
                    {
                        lampe.setImageResource(R.drawable.lampe_an);
                        aktScene = 1;
                    } else {
                        lampe.setImageResource(R.drawable.lampe_aus);
                        aktScene = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String cap = stateScene.getString(1).substring(0, 1).toUpperCase() + stateScene.getString(1).substring(1);
                    pergolaScene.setText(cap);
                } catch (JSONException e) {
                    e.printStackTrace();
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
        }, "1", possibleScenes);

        changeScene = (ImageView) view.findViewById(R.id.changeScene);
        changeScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktScene++;
                Integer maxScene = buttonScenes.size()/2;
                if (aktScene > maxScene)
                {
                    aktScene = 0;
                }
                changeAktScene(aktScene, buttonScenes);
            }
        });

        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HueRequester(context).hueRequesterLightChange(new HueRequester.VolleyResponseListenerHue() {
                    @Override
                    public void onResponse(float sliderStatus) {
                    }

                    @Override
                    public void onResponse(JSONArray lightxy) {

                    }

                    @Override
                    public void onError(String message) {
                    }

                    @Override
                    public void onResponse(String hueState) {
                        if (hueState.contains("true"))
                        {
                            lampe.setImageResource(R.drawable.lampe_an);
                            pergolaScene.setText("");
                            aktScene = 1;
                        } else {
                            lampe.setImageResource(R.drawable.lampe_aus);
                            pergolaScene.setText("Aus");
                            aktScene = 0;
                        }
                    }

                    @Override
                    public void onResponse(Integer hueState) {
                    }

                    @Override
                    public void onResponse(Boolean hueState) {
                    }
                }, "1");
            }
        });

        // Steuerung des zurück-Buttons
        back = (ImageView) view.findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new Welcome()).commit();
            }
        });

        RelativeLayout colorTerrasse = view.findViewById(R.id.color_terrasse);
        colorTerrasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new Color(), "1").commit();
            }
        });

        // StorenSteuerung

        storenOpen = view.findViewById(R.id.storen_up);
        storenOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    if (token != "") {
                        new TahomaRequester(context).storenSteuerung("open", token);
                    } else {
                        new TahomaRequester(context).storenSteuerung("open", "6270d66e8e829d0013bf");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        storenClose = view.findViewById(R.id.storen_down);
        storenClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    if (token != "") {
                        new TahomaRequester(context).storenSteuerung("close", token);
                    } else {
                        new TahomaRequester(context).storenSteuerung("close", "6270d66e8e829d0013bf");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        storenStop = view.findViewById(R.id.storen_pause);
        storenStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    if (token != "") {
                        new TahomaRequester(context).storenSteuerung("stop", token);
                    } else {
                        new TahomaRequester(context).storenSteuerung("stop", "6270d66e8e829d0013bf");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        // DachSteuerung
        dachOpen = view.findViewById(R.id.dach_open);
        dachOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    if (token != "") {
                        new TahomaRequester(context).dachSteuerung("open", token);
                    } else {
                        new TahomaRequester(context).dachSteuerung("open", "6270d66e8e829d0013bf");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        dachClose = view.findViewById(R.id.dach_down);
        dachClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    if (token != "") {
                        new TahomaRequester(context).dachSteuerung("close", token);
                    } else {
                        new TahomaRequester(context).dachSteuerung("closr", "6270d66e8e829d0013bf");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        dachStop = view.findViewById(R.id.dach_pause);
        dachStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    if (token != "") {
                        new TahomaRequester(context).dachSteuerung("stop", token);
                    } else {
                        new TahomaRequester(context).dachSteuerung("stop", "6270d66e8e829d0013bf");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void getStateStandard(SwitchCompat sw) {
        // Wir müssen zuerst wissen was für einen Status die Lampe aktuell hat
        new HueRequester(context).hueRequesterLightState(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray lightxy) {

            }

            @Override
            public void onError(String message) {
                sw.setChecked(false);
            }

            @Override
            public void onResponse(Integer hueState) {
            }

            @Override
            public void onResponse(Boolean hueState) {
                sw.setChecked(hueState);
            }

            @Override
            public void onResponse(String hueState) {
            }
        }, "1");
    }

    public void  getStateSlider(Slider slider) {
        // Wir müssen zuerst wissen was für einen Status die Lampe aktuell hat
        new HueRequester(context).hueRequesterSliderState(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
                slider.setValue(sliderStatus);
            }

            @Override
            public void onResponse(JSONArray lightxy) {

            }

            @Override
            public void onError(String message) {
                float error = 0;
                slider.setValue(error);
            }

            @Override
            public void onResponse(Boolean hueState) {
            }

            @Override
            public void onResponse(String hueState) {
            }

            @Override
            public void onResponse(Integer hueState) {

            }
        }, "1");
    }

    public void requestChangeScene (String gruppe, String scene)  {
        new HueRequester(context).hueRequesterSceneChange(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray lightxy) {

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
        }, gruppe, scene);

    }

    public void changeAktScene(Integer actualScene, Map<String, String> buttonScenes)
    {
        String scene = buttonScenes.get(Integer.toString(actualScene));
        String sceneCode = buttonScenes.get(scene);
        if (actualScene == 0)
        {
            scene = "Aus";
            lampe.setImageResource(R.drawable.lampe_aus);
            new HueRequester(context).hueRequesterLightChange(new HueRequester.VolleyResponseListenerHue() {
                @Override
                public void onResponse(float sliderStatus) {
                }

                @Override
                public void onResponse(JSONArray lightxy) {

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
            }, "1");
        } else {
            lampe.setImageResource(R.drawable.lampe_an);
            if (actualScene == 1)
            {
                new HueRequester(context).hueRequesterLightChange(new HueRequester.VolleyResponseListenerHue() {
                    @Override
                    public void onResponse(float sliderStatus) {
                    }

                    @Override
                    public void onResponse(JSONArray lightxy) {

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
                }, "1");
            }
        }
        String cap = scene.substring(0, 1).toUpperCase() + scene.substring(1);
        pergolaScene.setText(cap);
        requestChangeScene("1", sceneCode);
    }

    public void onResume() {
        super.onResume();
        View view = getView();

        context = view.getContext();

        // Alle Szenen
        Map<String, String> buttonScenes = new HashMap<String, String>();
        buttonScenes.put("Hell", "ReLoEqI9tDzEfhD");
        buttonScenes.put("Weiss", "TclYvk-BorTvx6s");
        buttonScenes.put("Energie", "7j-EQYrM2UXNSmk");
        buttonScenes.put("1", "Hell");
        buttonScenes.put("2", "Weiss");
        buttonScenes.put("3", "Energie");

        Map<String, String> possibleScenes = new HashMap<String, String>();
        possibleScenes.put("Hell", "ReLoEqI9tDzEfhD");
        possibleScenes.put("Weiss", "TclYvk-BorTvx6s");
        possibleScenes.put("Energie", "7j-EQYrM2UXNSmk");

        aktScene = 0;

        // Auslesen des Lichtstatus und der aktiven Szene
        new HueRequester(context).hueRequestergetStateAndScene(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray stateScene) {
                try {
                    if (stateScene.getBoolean(0))
                    {
                        lampe.setImageResource(R.drawable.lampe_an);
                        aktScene = 1;
                    } else {
                        lampe.setImageResource(R.drawable.lampe_aus);
                        aktScene = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String cap = stateScene.getString(1).substring(0, 1).toUpperCase() + stateScene.getString(1).substring(1);
                    pergolaScene.setText(cap);
                } catch (JSONException e) {
                    e.printStackTrace();
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
        }, "1", possibleScenes);
    }
}