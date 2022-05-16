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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smarthome.requester.HueRequester;
import com.google.android.material.slider.Slider;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Whirlpool extends Fragment {

    private Context context;

    private TextView whirlpoolSceneMauer;
    private TextView whirlpoolSceneBoden;

    ImageView changeScene_mauer;
    Integer aktScene_mauer;
    CircleImageView lampe_mauer;
    CircleImageView onoff_mauer;
    ImageView changeScene_boden;
    Integer aktScene_boden;
    CircleImageView lampe_boden;
    CircleImageView onoff_boden;

    ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_whirlpool,container,false);

        context = view.getContext();

        lampe_mauer = view.findViewById(R.id.lampe_mauer);
        onoff_mauer = view.findViewById(R.id.blumen_onoff);

        lampe_boden = view.findViewById(R.id.lampe_boden);
        onoff_boden = view.findViewById(R.id.boden_onoff);

        aktScene_mauer = 0;
        aktScene_boden = 0;

        // Alle Szenen
        Map<String, String> buttonScenes_blumen = new HashMap<String, String>();
        buttonScenes_blumen.put("hell", "r4HyYTkLgg1Nptm");
        buttonScenes_blumen.put("weiss", "BA4mXLbEP1qQz1w");
        buttonScenes_blumen.put("energie", "6urcyZ95U-GjykM");
        buttonScenes_blumen.put("sonne", "Xtb2fld5Q8TWXfk");
        buttonScenes_blumen.put("tropen", "CXxpByzqPGU0Qap");
        buttonScenes_blumen.put("1", "hell");
        buttonScenes_blumen.put("2", "weiss");
        buttonScenes_blumen.put("3", "energie");
        buttonScenes_blumen.put("4", "sonne");
        buttonScenes_blumen.put("5", "tropen");

        Map<String, String> possibleScenes_blumen = new HashMap<String, String>();
        possibleScenes_blumen.put("hell", "r4HyYTkLgg1Nptm");
        possibleScenes_blumen.put("weiss", "BA4mXLbEP1qQz1w");
        possibleScenes_blumen.put("energie", "6urcyZ95U-GjykM");
        possibleScenes_blumen.put("sonne", "Xtb2fld5Q8TWXfk");
        possibleScenes_blumen.put("tropen", "CXxpByzqPGU0Qap");

        Map<String, String> buttonScenes_boden = new HashMap<String, String>();
        buttonScenes_boden.put("hell", "PRlfckSBi5HYQuq");
        buttonScenes_boden.put("weiss", "KupohEQtTJMYE2N");
        buttonScenes_boden.put("energie", "MzlYTrex6FUzLmM");
        buttonScenes_boden.put("sonne", "PgbnOxg0-oBqSQn");
        buttonScenes_boden.put("tropen", "0-mYfWE4xTFqgzd");
        buttonScenes_boden.put("1", "hell");
        buttonScenes_boden.put("2", "weiss");
        buttonScenes_boden.put("3", "energie");
        buttonScenes_boden.put("4", "sonne");
        buttonScenes_boden.put("5", "tropen");

        Map<String, String> possibleScenes_boden = new HashMap<String, String>();
        possibleScenes_boden.put("hell", "PRlfckSBi5HYQuq");
        possibleScenes_boden.put("weiss", "KupohEQtTJMYE2N");
        possibleScenes_boden.put("energie", "MzlYTrex6FUzLmM");
        possibleScenes_boden.put("sonne", "PgbnOxg0-oBqSQn");
        possibleScenes_boden.put("tropen", "0-mYfWE4xTFqgzd");

        whirlpoolSceneMauer = view.findViewById(R.id.akt_szene_mauer);
        whirlpoolSceneBoden = view.findViewById(R.id.akt_szene_boden);


        // Auslesen des Lichtstatus und der aktiven Szene
        new HueRequester(context).hueRequestergetStateAndScene(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray stateScene) {
                System.out.println("test");
                try {
                    if (stateScene.getBoolean(0))
                    {
                        lampe_mauer.setImageResource(R.drawable.lampe_an);
                        aktScene_mauer = 1;
                    } else {
                        lampe_mauer.setImageResource(R.drawable.lampe_aus);
                        aktScene_mauer = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String cap = stateScene.getString(1).substring(0, 1).toUpperCase() + stateScene.getString(1).substring(1);
                    whirlpoolSceneMauer.setText(cap);
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
        }, "2", possibleScenes_blumen);
        new HueRequester(context).hueRequestergetStateAndScene(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray stateScene) {
                try {
                    if (stateScene.getBoolean(0))
                    {
                        lampe_boden.setImageResource(R.drawable.lampe_an);
                        aktScene_boden = 1;
                    } else {
                        lampe_boden.setImageResource(R.drawable.lampe_aus);
                        aktScene_boden = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String cap = stateScene.getString(1).substring(0, 1).toUpperCase() + stateScene.getString(1).substring(1);
                    whirlpoolSceneBoden.setText(cap);
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
        }, "5", possibleScenes_boden);


        changeScene_mauer = (ImageView) view.findViewById(R.id.blumen_changeScene);
        changeScene_mauer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktScene_mauer++;
                Integer maxScene = buttonScenes_blumen.size()/2;
                if (aktScene_mauer > maxScene)
                {
                    aktScene_mauer = 0;
                }
                changeAktScene_blumen(aktScene_mauer, buttonScenes_blumen);
            }
        });

        changeScene_boden = (ImageView) view.findViewById(R.id.boden_changeScene);
        changeScene_boden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktScene_boden++;
                Integer maxScene = buttonScenes_boden.size()/2;
                if (aktScene_boden > maxScene)
                {
                    aktScene_boden = 0;
                }
                changeAktScene_boden(aktScene_boden, buttonScenes_boden);
            }
        });

        onoff_mauer.setOnClickListener(new View.OnClickListener() {
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
                            lampe_mauer.setImageResource(R.drawable.lampe_an);
                            whirlpoolSceneMauer.setText("");
                            aktScene_mauer = 1;
                        } else {
                            lampe_mauer.setImageResource(R.drawable.lampe_aus);
                            whirlpoolSceneMauer.setText("Aus");
                            aktScene_mauer = 0;
                        }
                    }

                    @Override
                    public void onResponse(Integer hueState) {
                    }

                    @Override
                    public void onResponse(Boolean hueState) {
                    }
                }, "2");
            }
        });
        onoff_boden.setOnClickListener(new View.OnClickListener() {
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
                            lampe_boden.setImageResource(R.drawable.lampe_an);
                            whirlpoolSceneBoden.setText("");
                            aktScene_boden = 1;
                        } else {
                            lampe_boden.setImageResource(R.drawable.lampe_aus);
                            whirlpoolSceneBoden.setText("Aus");
                            aktScene_boden = 0;
                        }
                    }

                    @Override
                    public void onResponse(Integer hueState) {
                    }

                    @Override
                    public void onResponse(Boolean hueState) {
                    }
                }, "5");
            }
        });

        RelativeLayout colorWhirlpoolBlumen = view.findViewById(R.id.color_whirlpool_blumen);
        colorWhirlpoolBlumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new Color(), "2").commit();
            }
        });

        RelativeLayout colorWhirlpoolBoden = view.findViewById(R.id.color_whirlpool_boden_massif);
        colorWhirlpoolBoden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new Color(), "5").commit();
            }
        });
        /*
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
                    public void onResponse(JSONArray lightxy) {

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
                    public void onResponse(JSONArray lightxy) {

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

                    @Override
                    public void onResponse(JSONArray lightxy) {

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

                    @Override
                    public void onResponse(JSONArray lightxy) {

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
*/
        // Steuerung des zurück-Buttons
        back = view.findViewById(R.id.btn_back);
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
        }, "5");
    }

    public void requestChangeScene (String gruppe, String scene) {
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

    public void changeAktScene_blumen(Integer actualScene, Map<String, String> buttonScenes)
    {
        String scene = buttonScenes.get(Integer.toString(actualScene));
        String sceneCode = buttonScenes.get(scene);
        if (actualScene == 0)
        {
            scene = "Aus";
            lampe_mauer.setImageResource(R.drawable.lampe_aus);
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
            }, "2");
        } else {
            lampe_mauer.setImageResource(R.drawable.lampe_an);
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
                }, "2");
            }
        }
        String cap = scene.substring(0, 1).toUpperCase() + scene.substring(1);
        whirlpoolSceneMauer.setText(cap);
        requestChangeScene("2", sceneCode);
    }

    public void changeAktScene_boden(Integer actualScene, Map<String, String> buttonScenes)
    {
        String scene = buttonScenes.get(Integer.toString(actualScene));
        String sceneCode = buttonScenes.get(scene);
        if (actualScene == 0)
        {
            scene = "Aus";
            lampe_boden.setImageResource(R.drawable.lampe_aus);
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
            }, "5");
        } else {
            lampe_boden.setImageResource(R.drawable.lampe_an);
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
                }, "5");
            }
        }
        String cap = scene.substring(0, 1).toUpperCase() + scene.substring(1);
        whirlpoolSceneBoden.setText(cap);
        requestChangeScene("5", sceneCode);
    }

    public void onResume() {
        super.onResume();
        View view = getView();

        context = view.getContext();

        aktScene_mauer = 0;
        aktScene_boden = 0;

        // Alle Szenen
        Map<String, String> buttonScenes_blumen = new HashMap<String, String>();
        buttonScenes_blumen.put("hell", "r4HyYTkLgg1Nptm");
        buttonScenes_blumen.put("weiss", "BA4mXLbEP1qQz1w");
        buttonScenes_blumen.put("energie", "6urcyZ95U-GjykM");
        buttonScenes_blumen.put("sonne", "Xtb2fld5Q8TWXfk");
        buttonScenes_blumen.put("tropen", "CXxpByzqPGU0Qap");
        buttonScenes_blumen.put("1", "hell");
        buttonScenes_blumen.put("2", "weiss");
        buttonScenes_blumen.put("3", "energie");
        buttonScenes_blumen.put("4", "sonne");
        buttonScenes_blumen.put("5", "tropen");

        Map<String, String> possibleScenes_blumen = new HashMap<String, String>();
        possibleScenes_blumen.put("hell", "r4HyYTkLgg1Nptm");
        possibleScenes_blumen.put("weiss", "BA4mXLbEP1qQz1w");
        possibleScenes_blumen.put("energie", "6urcyZ95U-GjykM");
        possibleScenes_blumen.put("sonne", "Xtb2fld5Q8TWXfk");
        possibleScenes_blumen.put("tropen", "CXxpByzqPGU0Qap");

        Map<String, String> buttonScenes_boden = new HashMap<String, String>();
        buttonScenes_boden.put("hell", "PRlfckSBi5HYQuq");
        buttonScenes_boden.put("weiss", "KupohEQtTJMYE2N");
        buttonScenes_boden.put("energie", "MzlYTrex6FUzLmM");
        buttonScenes_boden.put("sonne", "PgbnOxg0-oBqSQn");
        buttonScenes_boden.put("tropen", "0-mYfWE4xTFqgzd");
        buttonScenes_boden.put("1", "hell");
        buttonScenes_boden.put("2", "weiss");
        buttonScenes_boden.put("3", "energie");
        buttonScenes_boden.put("4", "sonne");
        buttonScenes_boden.put("5", "tropen");

        Map<String, String> possibleScenes_boden = new HashMap<String, String>();
        possibleScenes_boden.put("hell", "PRlfckSBi5HYQuq");
        possibleScenes_boden.put("weiss", "KupohEQtTJMYE2N");
        possibleScenes_boden.put("energie", "MzlYTrex6FUzLmM");
        possibleScenes_boden.put("sonne", "PgbnOxg0-oBqSQn");
        possibleScenes_boden.put("tropen", "0-mYfWE4xTFqgzd");

        // Auslesen des Lichtstatus und der aktiven Szene
        new HueRequester(context).hueRequestergetStateAndScene(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray stateScene) {
                System.out.println("test");
                try {
                    if (stateScene.getBoolean(0))
                    {
                        lampe_mauer.setImageResource(R.drawable.lampe_an);
                        aktScene_mauer = 1;
                    } else {
                        lampe_mauer.setImageResource(R.drawable.lampe_aus);
                        aktScene_mauer = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String cap = stateScene.getString(1).substring(0, 1).toUpperCase() + stateScene.getString(1).substring(1);
                    whirlpoolSceneMauer.setText(cap);
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
        }, "2", possibleScenes_blumen);
        new HueRequester(context).hueRequestergetStateAndScene(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray stateScene) {
                try {
                    if (stateScene.getBoolean(0))
                    {
                        lampe_boden.setImageResource(R.drawable.lampe_an);
                        aktScene_boden = 1;
                    } else {
                        lampe_boden.setImageResource(R.drawable.lampe_aus);
                        aktScene_boden = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String cap = stateScene.getString(1).substring(0, 1).toUpperCase() + stateScene.getString(1).substring(1);
                    whirlpoolSceneBoden.setText(cap);
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
        }, "5", possibleScenes_boden);
    }
}