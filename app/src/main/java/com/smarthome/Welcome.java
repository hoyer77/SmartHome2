package com.smarthome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.display.DisplayManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthome.requester.ShellyRequester;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Welcome extends Fragment {

    private TextView solarProduction;
    private TextView aktConsumption;
    private TextView aktCar;
    private TextView outdoor_temp;
    private TextView pool_temp;
    private ProgressBar progress_solar;
    private ProgressBar progress_consumption;
    private ProgressBar progress_auto;
    private ProgressBar progress_outdoor_temp;
    private ProgressBar progress_pool_temp;

    private String solarPower;
    private String consumptionPower;
    private String carPower;
    private String outdoorTemp;
    private String poolTemp;

    private TextView uhrzeit;

    private Context context;

    private ScheduledExecutorService executor;

    @Override
    public void onResume() {
        super.onResume();
        DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        boolean screenOn = false;
        for (Display display : dm.getDisplays()) {
            if (display.getState() != Display.STATE_OFF) {
                screenOn = true;
            }
        }
        if (screenOn)
        {
            if (executor.isTerminated()) {
                executor = Executors.newScheduledThreadPool(1);
                executor.scheduleAtFixedRate(welcomeUpdate, 0, 30, TimeUnit.SECONDS);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        context = view.getContext();

        solarProduction = view.findViewById(R.id.akt_szene_text);
        aktConsumption = view.findViewById(R.id.gesamtverbrauch);
        aktCar = view.findViewById(R.id.autoverbrauch);
        pool_temp = view.findViewById(R.id.pool_temp_text);
        outdoor_temp = view.findViewById(R.id.outdoor_temp_text);
        progress_solar = view.findViewById(R.id.solar_progress_bar);
        progress_consumption = view.findViewById(R.id.consumption_progress_bar);
        progress_auto = view.findViewById(R.id.car_progress_bar);
        progress_outdoor_temp = view.findViewById(R.id.outdoor_temp_progress_bar);
        progress_pool_temp = view.findViewById(R.id.pool_temp_progress_bar);

        uhrzeit = view.findViewById((R.id.fragment_text_terrasse));


        ImageView btn_terrasse = view.findViewById(R.id.btn_terrasse);
        btn_terrasse.setOnClickListener(view1 -> {
            if (getActivity() != null) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new Terrasse()).commit();
            }
        });

        ImageView btn_whirlpool = view.findViewById(R.id.btn_whirlpool);
        btn_whirlpool.setOnClickListener(view12 -> {
            if (getActivity() != null) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new Whirlpool()).commit();
            }
        });

        ImageView btn_pool = view.findViewById(R.id.btn_pool);
        btn_pool.setOnClickListener(view13 -> {
            if (getActivity() != null) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new Pool()).commit();
            }
        });

        ImageView btn_cam = view.findViewById(R.id.btn_camera);
        btn_cam.setOnClickListener(view14 -> {
            PackageManager pm;
            pm = context.getPackageManager();
            Intent appIntent;
            String packageName = "com.mcu.reolink";
            try {
                appIntent = pm.getLaunchIntentForPackage(packageName);
                startActivity(appIntent);
            } catch (Exception e) {
                Toast.makeText(context, "App konnte nicht geöffnet werden", Toast.LENGTH_LONG).show();
            }
        });


        ImageView btn_music = view.findViewById(R.id.btn_music);
        btn_music.setOnClickListener(view14 -> {
            PackageManager pm;
            pm = context.getPackageManager();
            Intent appIntent;
            String packageName = "uk.org.ngo.squeezer";
            try {
                appIntent = pm.getLaunchIntentForPackage(packageName);
                startActivity(appIntent);
            } catch (Exception e) {
                Toast.makeText(context, "App konnte nicht geöffnet werden", Toast.LENGTH_LONG).show();
            }
        });


        Handler mHandler= new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // do your stuff here, called every second
                String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                uhrzeit.setText(currentDateTimeString);
                mHandler.postDelayed(this, 1000);
            }
        };
        // start it with:
        mHandler.post(runnable);

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(welcomeUpdate, 0, 30, TimeUnit.SECONDS);

        return view;

    }

    final Runnable welcomeUpdate = () -> {
        DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        boolean screenOn = false;
        for (Display display : dm.getDisplays()) {
            if (display.getState() != Display.STATE_OFF) {
                screenOn = true;
            }
        }
        if (screenOn) {
            getSolarPower();
            getAllConsumption();
            getCarConsumption();
            getOutDoorTemp();
        } else {
            // Wenn wir den executor nicht explizit stoppen wenn der Bildschirm ausgeschaltet ist, dann verbraucht die App im Hintergrund zuviel Strom.
            executor.shutdown();
        }
    };


    @SuppressLint("SetTextI18n")
    public void changeTextProductionwH (String mText)
    {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("de","CH"));
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat)nf;
        Integer formatText = Integer.valueOf(mText);
        String transform = df.format(formatText);
        progress_solar.setVisibility(View.GONE);
        solarProduction.setText(transform + " Wh");
    }
    @SuppressLint("SetTextI18n")
    public void changeTextConsumptionwH (String mText)
    {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("de","CH"));
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat)nf;
        Integer formatText = Integer.valueOf(mText);
        String transform = df.format(formatText);
        progress_consumption.setVisibility(View.GONE);
        aktConsumption.setText(transform + " Wh");
    }
    @SuppressLint("SetTextI18n")
    public void changeTextAutowH (String mText)
    {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("de","CH"));
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat)nf;
        Integer formatText = Integer.valueOf(mText);
        String transform = df.format(formatText);
        progress_auto.setVisibility(View.GONE);
        aktCar.setText(transform + " Wh");
    }

    @SuppressLint("SetTextI18n")
    public void changeTextOutdoorTemp (String mText)
    {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("de","CH"));
        nf.setMaximumFractionDigits(2);
        progress_outdoor_temp.setVisibility(View.GONE);
        outdoor_temp.setText(mText + " °");
    }

    @SuppressLint("SetTextI18n")
    public void changeTextPoolTemp (String mText)
    {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("de","CH"));
        nf.setMaximumFractionDigits(2);
        progress_pool_temp.setVisibility(View.GONE);
        pool_temp.setText(mText + " °");
    }

    public void getSolarPower() {
        // Instantiate the RequestQueue.
        //RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        final GetSolarProduction getSolarProduction = new GetSolarProduction(context);

        try {
            getSolarProduction.getSolarProduction(new GetSolarProduction.VolleyResponseListener() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onError(String message, Integer savePower) {
                    solarPower = message;
                    changeTextProductionwH(solarPower);
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Integer power, Integer response) {
                    solarPower = power.toString();
                    changeTextProductionwH(solarPower);
                }

            },0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAllConsumption() {
        new ShellyRequester(context).shellyRequesterEM3(new ShellyRequester.VolleyResponseListenerShelly() {
            @Override
            public void onError(String message) {
                consumptionPower = message;
                changeTextConsumptionwH(consumptionPower);
            }

            @Override
            public void onResponse(Integer shellyPower) {
                consumptionPower = shellyPower.toString();
                changeTextConsumptionwH(consumptionPower);
            }

            @Override
            public void onResponse(JSONArray aktTemp) {

            }

            @Override
            public void onResponse(String shellyPower) {
                changeTextConsumptionwH(shellyPower);
            }
        }, "NzE5YmJ1aWQ2B848287C2CA4826DAD98E249BC37CBC6BFBC4631C8C679DE900D3CBFB27EA76381D7ECE38ABE2D6", "8caab561f7e1");
    }

    public void getCarConsumption() {
        new ShellyRequester(context).shellyRequester1PM(new ShellyRequester.VolleyResponseListenerShelly() {
            @Override
            public void onError(String message) {
                carPower = message;
                changeTextAutowH(carPower);
            }

            @Override
            public void onResponse(Integer shellyPower) {
                if (shellyPower == null)
                {
                    shellyPower = 0;
                }
                carPower = shellyPower.toString();
                changeTextAutowH(carPower);
            }

            @Override
            public void onResponse(JSONArray aktTemp) {

            }

            @Override
            public void onResponse(String shellyPower) {
                changeTextAutowH(shellyPower);
            }
        }, "NzE5YmJ1aWQ2B848287C2CA4826DAD98E249BC37CBC6BFBC4631C8C679DE900D3CBFB27EA76381D7ECE38ABE2D6", "E8db84d2d395");
    }

    public void getOutDoorTemp() {
        new ShellyRequester(context).shellyRequester1PMTemp(new ShellyRequester.VolleyResponseListenerShelly() {
            @Override
            public void onError(String message) {
                outdoorTemp = message;
                changeTextOutdoorTemp(outdoorTemp);
            }

            @Override
            public void onResponse(Integer shellyPower) {

            }

            @Override
            public void onResponse(JSONArray aktTemp) {
                try {
                   String hardware = aktTemp.getJSONObject(0).getString("hwID");
                   poolTemp = "";
                   outdoorTemp = "";
                    if (hardware.equals("28e8d776e0013c26")) {
                        if (aktTemp.length() > 0) {
                            outdoorTemp = String.valueOf(aktTemp.getJSONObject(0).getDouble("tC"));
                        }
                        if (aktTemp.length() > 1) {
                            poolTemp = String.valueOf(aktTemp.getJSONObject(1).getDouble("tC"));
                        }
                    } else {
                        if (aktTemp.length() > 0) {
                            outdoorTemp = String.valueOf(aktTemp.getJSONObject(1).getDouble("tC"));
                        }
                        if (aktTemp.length() > 1) {
                            poolTemp = String.valueOf(aktTemp.getJSONObject(0).getDouble("tC"));
                        }
                    }
                    changeTextOutdoorTemp(outdoorTemp);
                    changeTextPoolTemp(poolTemp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onResponse(String shellyPower) {

            }
        }, "NzE5YmJ1aWQ2B848287C2CA4826DAD98E249BC37CBC6BFBC4631C8C679DE900D3CBFB27EA76381D7ECE38ABE2D6", "e8db84d2e9b5");
    }

}