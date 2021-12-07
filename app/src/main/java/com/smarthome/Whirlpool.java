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

import java.util.HashMap;
import java.util.Map;

public class Whirlpool extends Fragment {

    private Context context;

    ImageButton back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_whirlpool,container,false);

        context = view.getContext();

        // Steuerung der Lichter Switches
        SwitchCompat sw = view.findViewById(R.id.switch_blumen);
        SwitchCompat sw_terrasse = view.findViewById(R.id.switch_terrasse);
        getStateStandard(sw,"2");
        getStateStandard(sw_terrasse,"5");
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HueRequester(context).hueRequesterLightChange(new HueRequester.VolleyResponseListenerHue() {
                    @Override
                    public void onResponse(float sliderStatus) {
                    }

                    @Override
                    public void onError(String message) {
                        getStateStandard(sw,"2");
                    }

                    @Override
                    public void onResponse(String hueState) {
                        getStateStandard(sw,"2");
                    }

                    @Override
                    public void onResponse(Integer hueState) {
                        getStateStandard(sw,"2");
                    }

                    @Override
                    public void onResponse(Boolean hueState) {
                        getStateStandard(sw,"2");
                    }
                }, "2");
            }
        });
        sw_terrasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HueRequester(context).hueRequesterLightChange(new HueRequester.VolleyResponseListenerHue() {
                    @Override
                    public void onResponse(float sliderStatus) {

                    }

                    @Override
                    public void onError(String message) {
                        getStateStandard(sw_terrasse,"5");
                    }

                    @Override
                    public void onResponse(String hueState) {
                        getStateStandard(sw_terrasse,"5");
                    }

                    @Override
                    public void onResponse(Integer hueState) {
                        getStateStandard(sw_terrasse,"5");
                    }

                    @Override
                    public void onResponse(Boolean hueState) {
                        getStateStandard(sw_terrasse,"5");
                    }
                }, "5");
            }
        });

        // Steuerung des Licht Slider
        Slider slider = view.findViewById(R.id.slider_whirlpool);
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
                }, "2", newValue );
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
                }, "5", newValue );
            }
        });

        // Szenen Steuerung Lichter

        ImageButton btnenergie= view.findViewById(R.id.szene_whirlpool_energie);
        ImageButton btnweiss= view.findViewById(R.id.szene_whirlpool_weiss);
        ImageButton btnhell= view.findViewById(R.id.szene_whirlpool_hell);
        ImageButton btnsonnenuntergang= view.findViewById(R.id.szene_whirlpool_sonnenuntergang);
        ImageButton btntropen= view.findViewById(R.id.szene_whirlpool_tropen);

        Map<String, String> buttonScenes = new HashMap<String, String>();
        buttonScenes.put("hell", "r4HyYTkLgg1Nptm");
        buttonScenes.put("hell1", "PRlfckSBi5HYQuq");
        buttonScenes.put("weiss", "BA4mXLbEP1qQz1w");
        buttonScenes.put("weiss1", "KupohEQtTJMYE2N");
        buttonScenes.put("energie", "6urcyZ95U-GjykM");
        buttonScenes.put("energie1", "MzlYTrex6FUzLmM");
        buttonScenes.put("sonne", "Xtb2fld5Q8TWXfk");
        buttonScenes.put("sonne1", "PgbnOxg0-oBqSQn");
        buttonScenes.put("tropen", "CXxpByzqPGU0Qap");
        buttonScenes.put("tropen1", "0-mYfWE4xTFqgzd");

        View.OnClickListener onClickListenerButtons = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton b = (ImageButton) v;
                Object buttonTag = b.getTag();
                String check = (String) buttonTag;
                /*  hell: 2131362281
                    weiss: 2131362284
                    energie: 2131362280
                    sonnenuntergang: 2131362282
                    tropen: 2131362283
                 */
                switch (check)
                {
                    case "hell":
                        requestChangeScene("2", buttonScenes.get("hell"));
                        requestChangeScene("5", buttonScenes.get("hell"));
                        requestChangeScene("2", buttonScenes.get("hell1"));
                        requestChangeScene("5", buttonScenes.get("hell1"));
                        break;
                    case "weiss":
                        requestChangeScene("2", buttonScenes.get("weiss"));
                        requestChangeScene("5", buttonScenes.get("weiss"));
                        requestChangeScene("2", buttonScenes.get("weiss1"));
                        requestChangeScene("5", buttonScenes.get("weiss1"));
                        break;
                    case "energie":
                        requestChangeScene("2", buttonScenes.get("energie"));
                        requestChangeScene("5", buttonScenes.get("energie"));
                        requestChangeScene("2", buttonScenes.get("energie1"));
                        requestChangeScene("5", buttonScenes.get("energie1"));
                        break;
                    case "sonne":
                        requestChangeScene("2", buttonScenes.get("sonne"));
                        requestChangeScene("5", buttonScenes.get("sonne"));
                        requestChangeScene("2", buttonScenes.get("sonne1"));
                        requestChangeScene("5", buttonScenes.get("sonne1"));
                        break;
                    case "tropen":
                        requestChangeScene("2", buttonScenes.get("tropen"));
                        requestChangeScene("5", buttonScenes.get("tropen"));
                        requestChangeScene("2", buttonScenes.get("tropen1"));
                        requestChangeScene("5", buttonScenes.get("tropen1"));
                        break;
                }
            }
        };

        btnenergie.setOnClickListener(onClickListenerButtons);
        btnweiss.setOnClickListener(onClickListenerButtons);
        btnhell.setOnClickListener(onClickListenerButtons);
        btnsonnenuntergang.setOnClickListener(onClickListenerButtons);
        btntropen.setOnClickListener(onClickListenerButtons);

        // Steuerung des zurück-Buttons
        back = (ImageButton) view.findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new Welcome()).commit();
            }
        });

        return view;
    }

    public void getStateStandard(SwitchCompat sw, String gruppe) {
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
        }, gruppe);
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
        }, "5");
    }

    public void requestChangeScene (String gruppe, String scene) {
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