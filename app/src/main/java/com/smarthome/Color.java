package com.smarthome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarthome.requester.HueRequester;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Color extends Fragment {
    private Context context;

    String fragmentNumber;
    ImageView back;

    RecyclerView recyclerView;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color,container,false);

        // Backway
        List<Fragment> allFragments = getActivity().getSupportFragmentManager().getFragments();
        fragmentNumber = allFragments.get(0).getTag();

        back = view.findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                switch(fragmentNumber)
                {
                    case "1": fr.replace(R.id.container, new Terrasse()).commit();
                        break;
                    case "2": fr.replace(R.id.container, new Whirlpool()).commit();
                        break;
                    case "4": fr.replace(R.id.container, new Pool()).commit();
                        break;
                    case "5": fr.replace(R.id.container, new Whirlpool()).commit();
                        break;
                    case "7": fr.replace(R.id.container, new Pool()).commit();
                        break;
                    default: fr.replace(R.id.container, new Welcome()).commit();
                    break;
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();

        ArrayList<String> lightName = new ArrayList<String>();
        ArrayList<String> lightProzent = new ArrayList<String>();
        ArrayList<String> lightId = new ArrayList<String>();

        context = view.getContext();

        new HueRequester(context).hueRequesterLightsGroup(new HueRequester.VolleyResponseListenerHue() {
            @Override
            public void onResponse(float sliderStatus) {

            }

            @Override
            public void onResponse(JSONArray lightsDetails) throws JSONException {
                for (int i=0;i<lightsDetails.length();i++)
                {
                    JSONArray lightDetail = lightsDetails.getJSONArray(i);
                    lightName.add(lightDetail.getString(2));
                    lightProzent.add(lightDetail.getString(1));
                    lightId.add(lightDetail.getString(3));
                }
                recyclerView = view.findViewById(R.id.color_recyclerview);

                recyclerView.removeAllViewsInLayout();

                MyAdapter myAdapter = new MyAdapter(context, lightProzent, lightName, lightId);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            }

            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(Integer hueState) {
            }

            @Override
            public void onResponse(Boolean hueState) {

            }

            @Override
            public void onResponse(String hueState) {
            }
        }, fragmentNumber);


    }

}