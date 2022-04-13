package com.smarthome;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.smarthome.requester.ShellyRequester;

import org.json.JSONArray;

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
}