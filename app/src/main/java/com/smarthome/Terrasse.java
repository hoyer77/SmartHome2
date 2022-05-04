package com.smarthome;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.smarthome.requester.HueRequester;
import com.google.android.material.slider.Slider;
import com.smarthome.requester.TahomaRequester;
import com.smarthome.requester.TahomaTokenRequester;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Terrasse extends Fragment {

    private Context context;

    ImageButton back;
    ImageButton dachOpen;
    ImageButton dachClose;
    ImageButton dachStop;
    ImageButton storenClose;
    ImageButton storenOpen;
    ImageButton storenStop;

    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terrasse,container,false);

        context = view.getContext();

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


        // Szene-Einstellungen
        ImageButton btnenergie= view.findViewById(R.id.pergola_dach_zu);
        ImageButton btnweiss= view.findViewById(R.id.szene_terrasse_weiss);
        ImageButton btnhell= view.findViewById(R.id.szene_terrasse_hell);

        Map<String, String> buttonScenes = new HashMap<String, String>();
        buttonScenes.put("hell", "ReLoEqI9tDzEfhD");
        buttonScenes.put("weiss", "TclYvk-BorTvx6s");
        buttonScenes.put("energie", "7j-EQYrM2UXNSmk");

        View.OnClickListener onClickListenerButtons = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton b = (ImageButton) v;
                Object buttonTag = b.getTag();
                String check = (String) buttonTag;
                /*  hell: 2131362203
                    weiss: 2131362204
                    energie: 2131362202
                 */
                switch (check)
                {
                    case "hell":
                        requestChangeScene("1", buttonScenes.get("hell"));
                        break;
                    case "weiss":
                        requestChangeScene("1", buttonScenes.get("weiss"));
                        break;
                    case "energie":
                        requestChangeScene("1", buttonScenes.get("energie"));
                        break;
                }
            }
        };

        btnenergie.setOnClickListener(onClickListenerButtons);
        btnweiss.setOnClickListener(onClickListenerButtons);
        btnhell.setOnClickListener(onClickListenerButtons);


        // Licht Switch konfigurieren und ausführen lassen
        SwitchCompat sw = view.findViewById(R.id.switch_terrasse);
        getStateStandard(sw);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HueRequester(context).hueRequesterLightChange(new HueRequester.VolleyResponseListenerHue() {
                    @Override
                    public void onResponse(float sliderStatus) {
                    }

                    @Override
                    public void onError(String message) {
                        getStateStandard(sw);
                    }

                    @Override
                    public void onResponse(String hueState) {
                        getStateStandard(sw);
                    }

                    @Override
                    public void onResponse(Integer hueState) {
                        getStateStandard(sw);
                    }

                    @Override
                    public void onResponse(Boolean hueState) {
                        getStateStandard(sw);
                    }
                }, "1");
            }
        });

        // Licht Slider konfigurieren und ausführen lassen
        Slider slider = view.findViewById(R.id.slider_terrasse);
        getStateSlider(slider);
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                float lightValue = value;
                lightValue = (float) (lightValue*2.54);
                Integer newValue = (int) lightValue;
                new HueRequester(context).hueRequesterSliderChange(new HueRequester.VolleyResponseListenerHue() {
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

                    @Override
                    public void onResponse(float sliderStatus) {

                    }
                }, "1", newValue );
            }
        });


        // Steuerung des zurück-Buttons
        back = (ImageButton) view.findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new Welcome()).commit();
            }
        });

        // StorenSteuerung
        storenOpen = (ImageButton) view.findViewById(R.id.storen_up);
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

        storenClose = (ImageButton) view.findViewById(R.id.storen_down);
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

        storenStop = (ImageButton) view.findViewById(R.id.storen_stop);
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
        dachOpen = (ImageButton) view.findViewById(R.id.pergola_dach_offen);
        dachOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    if (token != "") {
                        new TahomaRequester(context).dachSteuerung("close", token);
                    } else {
                        new TahomaRequester(context).dachSteuerung("close", "6270d66e8e829d0013bf");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        dachClose = (ImageButton) view.findViewById(R.id.pergola_dach_zu);
        dachClose.setOnClickListener(new View.OnClickListener() {

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

        dachStop = (ImageButton) view.findViewById(R.id.pergola_dach_stop);
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

}