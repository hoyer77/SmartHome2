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

import com.google.android.material.slider.Slider;
import com.smarthome.requester.HueRequester;
import com.smarthome.requester.ShellyRequester;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class Pool extends Fragment {

    private Context context;

    ImageButton back;

    ImageButton openAbdeckung;
    ImageButton closeAbdeckung;
    ImageButton stopAbdeckung;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pool,container,false);

        context = view.getContext();

        // Steuerung der Lichter Switches
        SwitchCompat sw = view.findViewById(R.id.switch_poolwand);
        SwitchCompat sw_massiv = view.findViewById(R.id.switch_pool_blumen);
        getStateStandard(sw,"7");
        getStateStandard(sw_massiv,"4");
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HueRequester(context).hueRequesterLightChange(new HueRequester.VolleyResponseListenerHue() {
                    @Override
                    public void onResponse(float sliderStatus) {
                    }

                    @Override
                    public void onError(String message) {
                        getStateStandard(sw,"7");
                    }

                    @Override
                    public void onResponse(String hueState) {
                        getStateStandard(sw,"7");
                    }

                    @Override
                    public void onResponse(Integer hueState) {
                        getStateStandard(sw,"7");
                    }

                    @Override
                    public void onResponse(Boolean hueState) {
                        getStateStandard(sw,"7");
                    }
                }, "7");
            }
        });

        sw_massiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HueRequester(context).hueRequesterLightChange(new HueRequester.VolleyResponseListenerHue() {
                    @Override
                    public void onResponse(float sliderStatus) {
                    }

                    @Override
                    public void onError(String message) {
                        getStateStandard(sw,"4");
                    }

                    @Override
                    public void onResponse(String hueState) {
                        getStateStandard(sw,"4");
                    }

                    @Override
                    public void onResponse(Integer hueState) {
                        getStateStandard(sw,"4");
                    }

                    @Override
                    public void onResponse(Boolean hueState) {
                        getStateStandard(sw,"4");
                    }
                }, "4");
            }
        });


        // Steuerung des Licht Slider
        Slider slider = view.findViewById(R.id.slider_pool);
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
                }, "4", newValue );
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
                }, "7", newValue );
            }
        });

        // Szenen Steuerung Lichter

        ImageButton btnenergie= view.findViewById(R.id.szene_pool_energie);
        ImageButton btnweiss= view.findViewById(R.id.szene_pool_weiss);
        ImageButton btnhell= view.findViewById(R.id.szene_pool_hell);
        ImageButton btnsonnenuntergang= view.findViewById(R.id.szene_pool_sonnenuntergang);
        ImageButton btntropen= view.findViewById(R.id.szene_pool_tropen);

        Map<String, String> buttonScenes = new HashMap<String, String>();
        buttonScenes.put("hell", "r4HyYTkLgg1Nptm");
        buttonScenes.put("hell1", "77-qmddnlkCicWx");
        buttonScenes.put("weiss", "BA4mXLbEP1qQz1w");
        buttonScenes.put("weiss1", "u87djJZRZ1sshU3");
        buttonScenes.put("energie", "6urcyZ95U-GjykM");
        buttonScenes.put("energie1", "zPsXxZ7as9EIVqK");
        buttonScenes.put("sonne", "Xtb2fld5Q8TWXfk");
        buttonScenes.put("sonne1", "7fmjmC5C-cOpQqO");
        buttonScenes.put("tropen", "CXxpByzqPGU0Qap");
        buttonScenes.put("tropen1", "AS7BFvo0PkPK6qp");

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
                        requestChangeScene("4", buttonScenes.get("hell"));
                        requestChangeScene("7", buttonScenes.get("hell"));
                        requestChangeScene("4", buttonScenes.get("hell1"));
                        requestChangeScene("7", buttonScenes.get("hell1"));
                        break;
                    case "weiss":
                        requestChangeScene("4", buttonScenes.get("weiss"));
                        requestChangeScene("7", buttonScenes.get("weiss"));
                        requestChangeScene("4", buttonScenes.get("weiss1"));
                        requestChangeScene("7", buttonScenes.get("weiss1"));
                        break;
                    case "energie":
                        requestChangeScene("4", buttonScenes.get("energie"));
                        requestChangeScene("7", buttonScenes.get("energie"));
                        requestChangeScene("4", buttonScenes.get("energie1"));
                        requestChangeScene("7", buttonScenes.get("energie1"));
                        break;
                    case "sonne":
                        requestChangeScene("4", buttonScenes.get("sonne"));
                        requestChangeScene("7", buttonScenes.get("sonne"));
                        requestChangeScene("4", buttonScenes.get("sonne1"));
                        requestChangeScene("7", buttonScenes.get("sonne1"));
                        break;
                    case "tropen":
                        requestChangeScene("4", buttonScenes.get("tropen"));
                        requestChangeScene("7", buttonScenes.get("tropen"));
                        requestChangeScene("4", buttonScenes.get("tropen1"));
                        requestChangeScene("7", buttonScenes.get("tropen1"));
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

        // Steuerung der Abdeckung

        openAbdeckung = (ImageButton) view.findViewById(R.id.pool_up);
        openAbdeckung.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShellyRequester(context).shellyRequester25(new ShellyRequester.VolleyResponseListenerShelly() {
                    @Override
                    public void onError(String message) {
                    }

                    @Override
                    public void onResponse(Integer shellyPower) {
                    }

                    @Override
                    public void onResponse(JSONArray aktTemp) {

                    }

                    @Override
                    public void onResponse(String shellyPower) {

                    }
                }, "NzE5YmJ1aWQ2B848287C2CA4826DAD98E249BC37CBC6BFBC4631C8C679DE900D3CBFB27EA76381D7ECE38ABE2D6", "e8db84aa1c55", "open");
                System.out.println("Up");
            }
        }));

        stopAbdeckung = (ImageButton) view.findViewById(R.id.pool_stop);
        stopAbdeckung.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShellyRequester(context).shellyRequester25(new ShellyRequester.VolleyResponseListenerShelly() {
                    @Override
                    public void onError(String message) {
                    }

                    @Override
                    public void onResponse(Integer shellyPower) {
                    }

                    @Override
                    public void onResponse(JSONArray aktTemp) {

                    }

                    @Override
                    public void onResponse(String shellyPower) {

                    }
                }, "NzE5YmJ1aWQ2B848287C2CA4826DAD98E249BC37CBC6BFBC4631C8C679DE900D3CBFB27EA76381D7ECE38ABE2D6", "e8db84aa1c55", "stop");
                System.out.println("Up");
            }
        }));


        closeAbdeckung = (ImageButton) view.findViewById(R.id.pool_down);
        closeAbdeckung.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShellyRequester(context).shellyRequester25(new ShellyRequester.VolleyResponseListenerShelly() {
                    @Override
                    public void onError(String message) {
                    }

                    @Override
                    public void onResponse(Integer shellyPower) {
                    }

                    @Override
                    public void onResponse(JSONArray aktTemp) {

                    }

                    @Override
                    public void onResponse(String shellyPower) {

                    }
                }, "NzE5YmJ1aWQ2B848287C2CA4826DAD98E249BC37CBC6BFBC4631C8C679DE900D3CBFB27EA76381D7ECE38ABE2D6", "e8db84aa1c55", "close");
                System.out.println("Down");
            }
        }));


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
        }, "7");
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