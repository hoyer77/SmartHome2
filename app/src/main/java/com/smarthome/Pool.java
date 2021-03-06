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
import com.smarthome.requester.ShellyRequester;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Pool extends Fragment {

    private Context context;

    private TextView poolScene_mauer;
    private TextView poolScene_massif;

    ImageView changeScene_mauer;
    Integer aktScene_mauer;
    CircleImageView lampe_mauer;
    CircleImageView onoff_mauer;
    CircleImageView lampe_pool;
    CircleImageView onoff_pool;
    ImageView changeScene_massif;
    Integer aktScene_massif;
    CircleImageView lampe_massif;
    CircleImageView onoff_massif;
    CircleImageView openAbdeckung;
    CircleImageView closeAbdeckung;
    CircleImageView stopAbdeckung;
    Integer statePoolLight;

    CircleImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pool,container,false);

        context = view.getContext();

        lampe_mauer = view.findViewById(R.id.pool_mauer_lampe);
        onoff_mauer = view.findViewById(R.id.pool_mauer_onoff);
        lampe_massif = view.findViewById(R.id.pool_massif_lampe);
        onoff_massif = view.findViewById(R.id.pool_massif_onoff);

        lampe_pool = view.findViewById(R.id.pool_lampe);
        onoff_pool = view.findViewById(R.id.pool_lampe_onoff);


        // Alle Szenen
        Map<String, String> buttonScenes_massif = new HashMap<>();
        buttonScenes_massif.put("Hell", "Zgeu6dS5zujOCFf");
        buttonScenes_massif.put("Weiss", "keMVkwzByMGXVgP");
        buttonScenes_massif.put("Energie", "NTG0q9KJCtBDxUX");
        buttonScenes_massif.put("Sonne", "HPguGiw5qWH-afC");
        buttonScenes_massif.put("Tropen", "pXxakauRONqXKbj");

        buttonScenes_massif.put("1", "Hell");
        buttonScenes_massif.put("2", "Weiss");
        buttonScenes_massif.put("3", "Energie");
        buttonScenes_massif.put("4", "Sonne");
        buttonScenes_massif.put("5", "Troppen");

        Map<String, String> possibleScenes_massif = new HashMap<>();
        possibleScenes_massif.put("Hell", "ReLoEqI9tDzEfhD");
        possibleScenes_massif.put("Weiss", "TclYvk-BorTvx6s");
        possibleScenes_massif.put("Energie", "7j-EQYrM2UXNSmk");
        possibleScenes_massif.put("Sonne", "HPguGiw5qWH-afC");
        possibleScenes_massif.put("Tropen", "pXxakauRONqXKbj");

        Map<String, String> buttonScenes_mauer = new HashMap<>();
        buttonScenes_mauer.put("Hell", "77-qmddnlkCicWx");
        buttonScenes_mauer.put("Weiss", "u87djJZRZ1sshU3");
        buttonScenes_mauer.put("Energie", "zPsXxZ7as9EIVqK");
        buttonScenes_mauer.put("Sonne", "7fmjmC5C-cOpQqO");
        buttonScenes_mauer.put("Tropen", "AS7BFvo0PkPK6qp");

        buttonScenes_mauer.put("1", "Hell");
        buttonScenes_mauer.put("2", "Weiss");
        buttonScenes_mauer.put("3", "Energie");
        buttonScenes_mauer.put("4", "Sonne");
        buttonScenes_mauer.put("5", "Troppen");

        Map<String, String> possibleScenes_mauer = new HashMap<>();
        possibleScenes_mauer.put("Hell", "77-qmddnlkCicWx");
        possibleScenes_mauer.put("Weiss", "u87djJZRZ1sshU3");
        possibleScenes_mauer.put("Energie", "zPsXxZ7as9EIVqK");
        possibleScenes_mauer.put("Sonne", "7fmjmC5C-cOpQqO");
        possibleScenes_mauer.put("Tropen", "AS7BFvo0PkPK6qp");

        poolScene_mauer = view.findViewById(R.id.akt_pool_mauer_szene);
        aktScene_mauer = 0;

        poolScene_massif = view.findViewById(R.id.akt_pool_massif_szene);
        aktScene_massif = 0;

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
                    poolScene_mauer.setText(cap);
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
        }, "7", possibleScenes_mauer);
        new HueRequester(context).hueRequestergetStateAndScene(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray stateScene) {
                try {
                    if (stateScene.getBoolean(0))
                    {
                        lampe_massif.setImageResource(R.drawable.lampe_an);
                        aktScene_massif = 1;
                    } else {
                        lampe_massif.setImageResource(R.drawable.lampe_aus);
                        aktScene_massif = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String cap = stateScene.getString(1).substring(0, 1).toUpperCase() + stateScene.getString(1).substring(1);
                    poolScene_massif.setText(cap);
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
        }, "4", possibleScenes_massif);

        new ShellyRequester(context).shellyRequester1PMState(new ShellyRequester.VolleyResponseListenerShelly() {

            @Override
            public void onResponse(JSONArray stateScene) {
            }

            @Override
            public void onError(String message) {
            }

            @Override
            public void onResponse(String lightState) {
                if (lightState.equals("false"))
                {
                    lampe_pool.setImageResource(R.drawable.lampe_aus);
                    statePoolLight = 0;
                } else {
                    lampe_pool.setImageResource(R.drawable.lampe_an);
                    statePoolLight = 1;
                }
            }

            @Override
            public void onResponse(Integer hueState) {
            }

        }, "NzE5YmJ1aWQ2B848287C2CA4826DAD98E249BC37CBC6BFBC4631C8C679DE900D3CBFB27EA76381D7ECE38ABE2D6", "e8db84a12cc8");

        changeScene_mauer = view.findViewById(R.id.pool_mauer_changeScene);
        changeScene_mauer.setOnClickListener(v -> {
            aktScene_mauer++;
            int maxScene = buttonScenes_mauer.size()/2;
            if (aktScene_mauer > maxScene)
            {
                aktScene_mauer = 0;
            }
            changeAktSceneMauer(aktScene_mauer, buttonScenes_mauer);
        });

        changeScene_massif = view.findViewById(R.id.pool_massif_changeScene);
        changeScene_massif.setOnClickListener(v -> {
            aktScene_massif++;
            int maxScene = buttonScenes_massif.size()/2;
            if (aktScene_massif > maxScene)
            {
                aktScene_massif = 0;
            }
            changeAktSceneMassif(aktScene_massif, buttonScenes_massif);
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
                    poolScene_mauer.setText("");
                    aktScene_mauer = 1;
                } else {
                    lampe_mauer.setImageResource(R.drawable.lampe_aus);
                    poolScene_mauer.setText("Aus");
                    aktScene_mauer = 0;
                }
            }

            @Override
            public void onResponse(Integer hueState) {
            }

            @Override
            public void onResponse(Boolean hueState) {
            }
        }, "7"));
        onoff_massif.setOnClickListener(v -> new HueRequester(context).hueRequesterLightChange(new HueRequester.VolleyResponseListenerHue() {
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
                    lampe_massif.setImageResource(R.drawable.lampe_an);
                    poolScene_massif.setText("");
                    aktScene_massif = 1;
                } else {
                    lampe_massif.setImageResource(R.drawable.lampe_aus);
                    poolScene_massif.setText("Aus");
                    aktScene_massif = 0;
                }
            }

            @Override
            public void onResponse(Integer hueState) {
            }

            @Override
            public void onResponse(Boolean hueState) {
            }
        }, "4"));
        onoff_pool.setOnClickListener(v -> new ShellyRequester(context).shellyRequester1PMLightSwitch(new ShellyRequester.VolleyResponseListenerShelly() {

            @Override
            public void onResponse(JSONArray lightxy) {

            }

            @Override
            public void onError(String message) {
            }

            @Override
            public void onResponse(String hueState) {
                if (hueState.contains("on"))
                {
                    lampe_pool.setImageResource(R.drawable.lampe_an);
                    statePoolLight = 1;
                } else {
                    lampe_pool.setImageResource(R.drawable.lampe_aus);
                    statePoolLight = 0;
                }
            }

            @Override
            public void onResponse(Integer hueState) {
            }
            }, "NzE5YmJ1aWQ2B848287C2CA4826DAD98E249BC37CBC6BFBC4631C8C679DE900D3CBFB27EA76381D7ECE38ABE2D6", "e8db84a12cc8", statePoolLight ));

        // Steuerung des zur??ck-Buttons
        back = view.findViewById(R.id.btn_back);
        back.setOnClickListener(v -> {
            FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.container, new Welcome()).commit();
        });

        RelativeLayout colorPoolMassif = view.findViewById(R.id.color_pool_massif);
        colorPoolMassif.setOnClickListener(v -> {
            FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.container, new Color(), "4").commit();
        });

        RelativeLayout colorPoolMauer = view.findViewById(R.id.color_pool_mauer);
        colorPoolMauer.setOnClickListener(v -> {
            FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.container, new Color(), "7").commit();
        });


        // Steuerung der Abdeckung

        openAbdeckung =  view.findViewById(R.id.pool_up);
        openAbdeckung.setOnClickListener((view1 -> new ShellyRequester(context).shellyRequester25(new ShellyRequester.VolleyResponseListenerShelly() {
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
        }, "NzE5YmJ1aWQ2B848287C2CA4826DAD98E249BC37CBC6BFBC4631C8C679DE900D3CBFB27EA76381D7ECE38ABE2D6", "e8db84aa1c55", "open")));

        stopAbdeckung = view.findViewById(R.id.pool_pause);
        stopAbdeckung.setOnClickListener((view12 -> {
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
        }));


        closeAbdeckung = view.findViewById(R.id.pool_down);
        closeAbdeckung.setOnClickListener((view13 -> {
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
        }));
        
        return view;
    }

    public void requestChangeSceneMauer (String gruppe, String scene)  {
        new HueRequester(context).hueRequesterSceneChange(gruppe, scene);

    }

    public void changeAktSceneMauer(Integer actualScene, Map<String, String> buttonScenes) {
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
            }, "7");
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
                }, "7");
            }
        }
        assert scene != null;
        String cap = scene.substring(0, 1).toUpperCase() + scene.substring(1);
        poolScene_mauer.setText(cap);
        requestChangeSceneMauer("7", sceneCode);
    }

    public void requestChangeSceneMassif (String gruppe, String scene)  {
        new HueRequester(context).hueRequesterSceneChange(gruppe, scene);

    }

    public void changeAktSceneMassif(Integer actualScene, Map<String, String> buttonScenes) {
        String scene = buttonScenes.get(Integer.toString(actualScene));
        String sceneCode = buttonScenes.get(scene);
        if (actualScene == 0)
        {
            scene = "Aus";
            lampe_massif.setImageResource(R.drawable.lampe_aus);
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
            }, "4");
        } else {
            lampe_massif.setImageResource(R.drawable.lampe_an);
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
                }, "4");
            }
        }
        assert scene != null;
        String cap = scene.substring(0, 1).toUpperCase() + scene.substring(1);
        poolScene_massif.setText(cap);
        requestChangeSceneMassif("4", sceneCode);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();

        assert view != null;
        context = view.getContext();


        // Alle Szenen
        Map<String, String> buttonScenes_massif = new HashMap<>();
        buttonScenes_massif.put("Hell", "Zgeu6dS5zujOCFf");
        buttonScenes_massif.put("Weiss", "keMVkwzByMGXVgP");
        buttonScenes_massif.put("Energie", "NTG0q9KJCtBDxUX");
        buttonScenes_massif.put("Sonne", "HPguGiw5qWH-afC");
        buttonScenes_massif.put("Tropen", "pXxakauRONqXKbj");

        buttonScenes_massif.put("1", "Hell");
        buttonScenes_massif.put("2", "Weiss");
        buttonScenes_massif.put("3", "Energie");
        buttonScenes_massif.put("4", "Sonne");
        buttonScenes_massif.put("5", "Troppen");

        Map<String, String> possibleScenes_massif = new HashMap<>();
        possibleScenes_massif.put("Hell", "ReLoEqI9tDzEfhD");
        possibleScenes_massif.put("Weiss", "TclYvk-BorTvx6s");
        possibleScenes_massif.put("Energie", "7j-EQYrM2UXNSmk");
        possibleScenes_massif.put("Sonne", "HPguGiw5qWH-afC");
        possibleScenes_massif.put("Tropen", "pXxakauRONqXKbj");

        Map<String, String> buttonScenes_mauer = new HashMap<>();
        buttonScenes_mauer.put("Hell", "77-qmddnlkCicWx");
        buttonScenes_mauer.put("Weiss", "u87djJZRZ1sshU3");
        buttonScenes_mauer.put("Energie", "zPsXxZ7as9EIVqK");
        buttonScenes_mauer.put("Sonne", "7fmjmC5C-cOpQqO");
        buttonScenes_mauer.put("Tropen", "AS7BFvo0PkPK6qp");

        buttonScenes_mauer.put("1", "Hell");
        buttonScenes_mauer.put("2", "Weiss");
        buttonScenes_mauer.put("3", "Energie");
        buttonScenes_mauer.put("4", "Sonne");
        buttonScenes_mauer.put("5", "Troppen");

        Map<String, String> possibleScenes_mauer = new HashMap<>();
        possibleScenes_mauer.put("Hell", "77-qmddnlkCicWx");
        possibleScenes_mauer.put("Weiss", "u87djJZRZ1sshU3");
        possibleScenes_mauer.put("Energie", "zPsXxZ7as9EIVqK");
        possibleScenes_mauer.put("Sonne", "7fmjmC5C-cOpQqO");
        possibleScenes_mauer.put("Tropen", "AS7BFvo0PkPK6qp");

        aktScene_mauer = 0;

        aktScene_massif = 0;

        new HueRequester(context).hueRequestergetStateAndScene(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray stateScene) {
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
                    poolScene_mauer.setText(cap);
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
        }, "7", possibleScenes_mauer);
        new HueRequester(context).hueRequestergetStateAndScene(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {
            }

            @Override
            public void onResponse(JSONArray stateScene) {
                try {
                    if (stateScene.getBoolean(0))
                    {
                        lampe_massif.setImageResource(R.drawable.lampe_an);
                        aktScene_massif = 1;
                    } else {
                        lampe_massif.setImageResource(R.drawable.lampe_aus);
                        aktScene_massif = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String cap = stateScene.getString(1).substring(0, 1).toUpperCase() + stateScene.getString(1).substring(1);
                    poolScene_massif.setText(cap);
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
        }, "4", possibleScenes_massif);


    }

}