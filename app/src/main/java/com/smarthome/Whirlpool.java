package com.smarthome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.smarthome.requester.HueRequester;

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
        Map<String, String> buttonScenes_blumen = new HashMap<>();
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

        Map<String, String> possibleScenes_blumen = new HashMap<>();
        possibleScenes_blumen.put("hell", "r4HyYTkLgg1Nptm");
        possibleScenes_blumen.put("weiss", "BA4mXLbEP1qQz1w");
        possibleScenes_blumen.put("energie", "6urcyZ95U-GjykM");
        possibleScenes_blumen.put("sonne", "Xtb2fld5Q8TWXfk");
        possibleScenes_blumen.put("tropen", "CXxpByzqPGU0Qap");

        Map<String, String> buttonScenes_boden = new HashMap<>();
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

        Map<String, String> possibleScenes_boden = new HashMap<>();
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


        changeScene_mauer = view.findViewById(R.id.blumen_changeScene);
        changeScene_mauer.setOnClickListener(v -> {
            aktScene_mauer++;
            int maxScene = buttonScenes_blumen.size()/2;
            if (aktScene_mauer > maxScene)
            {
                aktScene_mauer = 0;
            }
            changeAktScene_blumen(aktScene_mauer, buttonScenes_blumen);
        });

        changeScene_boden = view.findViewById(R.id.boden_changeScene);
        changeScene_boden.setOnClickListener(v -> {
            aktScene_boden++;
            int maxScene = buttonScenes_boden.size()/2;
            if (aktScene_boden > maxScene)
            {
                aktScene_boden = 0;
            }
            changeAktScene_boden(aktScene_boden, buttonScenes_boden);
        });

        onoff_mauer.setOnClickListener(v -> new HueRequester(context).hueRequesterLightChange(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray lightxy) {

            }

            @Override
            public void onError(String message) {
            }

            @SuppressLint("SetTextI18n")
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
        }, "2"));
        onoff_boden.setOnClickListener(v -> new HueRequester(context).hueRequesterLightChange(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray lightxy) {

            }

            @Override
            public void onError(String message) {
            }

            @SuppressLint("SetTextI18n")
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
        }, "5"));

        RelativeLayout colorWhirlpoolBlumen = view.findViewById(R.id.color_whirlpool_blumen);
        colorWhirlpoolBlumen.setOnClickListener(v -> {
            FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.container, new Color(), "2").commit();
        });

        RelativeLayout colorWhirlpoolBoden = view.findViewById(R.id.color_whirlpool_boden_massif);
        colorWhirlpoolBoden.setOnClickListener(v -> {
            FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.container, new Color(), "5").commit();
        });

        // Steuerung des zurück-Buttons
        back = view.findViewById(R.id.btn_back);
        back.setOnClickListener(v -> {
            FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.container, new Welcome()).commit();
        });

        return view;
    }

    public void requestChangeScene (String gruppe, String scene) {
        new HueRequester(context).hueRequesterSceneChange(gruppe, scene);

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
        assert scene != null;
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
        assert scene != null;
        String cap = scene.substring(0, 1).toUpperCase() + scene.substring(1);
        whirlpoolSceneBoden.setText(cap);
        requestChangeScene("5", sceneCode);
    }

    public void onResume() {
        super.onResume();
        View view = getView();

        assert view != null;
        context = view.getContext();

        aktScene_mauer = 0;
        aktScene_boden = 0;

        // Alle Szenen
        Map<String, String> buttonScenes_blumen = new HashMap<>();
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

        Map<String, String> possibleScenes_blumen = new HashMap<>();
        possibleScenes_blumen.put("hell", "r4HyYTkLgg1Nptm");
        possibleScenes_blumen.put("weiss", "BA4mXLbEP1qQz1w");
        possibleScenes_blumen.put("energie", "6urcyZ95U-GjykM");
        possibleScenes_blumen.put("sonne", "Xtb2fld5Q8TWXfk");
        possibleScenes_blumen.put("tropen", "CXxpByzqPGU0Qap");

        Map<String, String> buttonScenes_boden = new HashMap<>();
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

        Map<String, String> possibleScenes_boden = new HashMap<>();
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