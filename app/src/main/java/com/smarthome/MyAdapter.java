package com.smarthome;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.Slider;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.smarthome.requester.HueRequester;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<String> bezeichnung;
    ArrayList<String> prozent;
    ArrayList<String> id;
    Context context;


    public MyAdapter(Context ct, ArrayList<String> lightProzent, ArrayList<String> lightName,ArrayList<String> lightId)
    {
        context = ct;
        bezeichnung = lightName;
        prozent = lightProzent;
        id = lightId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();

                new ColorPickerDialog.Builder(context)
                        .setTitle("ColorPicker Dialog")
                        .setPreferenceName("MyColorPickerDialog")
                        .setPositiveButton(context.getString(R.string.confirm),
                                new ColorEnvelopeListener() {
                                    @Override
                                    public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                        int[] argb = envelope.getArgb();
                                        double[] xy = getRGBtoXY(argb[1], argb[2], argb[3]);
                                        MyViewHolder colorHolder = holder;
                                        Integer lichid = colorHolder.slider.getId();
                                        String lichtnummer = String.valueOf(lichid);
                                        new HueRequester(context).hueRequesterSliderChangeLightColor(new HueRequester.VolleyResponseListenerHue() {
                                            @Override
                                            public void onError(String message) {
                                                System.out.println("Wechseln der Textanzeige-prozent");
                                            }

                                            @Override
                                            public void onResponse(String hueState) {
                                                System.out.println("Wechseln der Textanzeige-prozent");
                                            }

                                            @Override
                                            public void onResponse(Integer hueState) {
                                                System.out.println("Wechseln der Textanzeige-prozent");
                                            }

                                            @Override
                                            public void onResponse(Boolean hueState) {
                                                System.out.println("Wechseln der Textanzeige-prozent");
                                            }

                                            @Override
                                            public void onResponse(float sliderStatus) {

                                            }

                                            @Override
                                            public void onResponse(JSONArray lightxy) throws JSONException {

                                            }
                                        }, lichtnummer, xy );
                                    }
                                })
                        .setNegativeButton(context.getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                        .attachAlphaSlideBar(true) // the default value is true.
                        .attachBrightnessSlideBar(true)  // the default value is true.
                        .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                        .show();

            }
        });

        holder.lampe.setText("Lampe " + bezeichnung.get(position).toUpperCase());
        long proz = Math.round(0.39370*Integer.parseInt(prozent.get(position)));
        holder.prozent.setText(proz+" %");
        holder.prozent.setTag("prozent_"+id.get((position)));
        holder.slider.setValue(proz);
        holder.slider.setId(Integer.parseInt(id.get(position)));
        holder.slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                float lightValue = value;
                lightValue = (float) (lightValue*2.54);
                Integer newValue = (int) lightValue;
                Integer lichid = slider.getId();
                String lichtnummer = String.valueOf(lichid);
                new HueRequester(context).hueRequesterSliderChangeLight(new HueRequester.VolleyResponseListenerHue() {
                    @Override
                    public void onError(String message) {
                        System.out.println("Wechseln der Textanzeige-prozent");
                    }

                    @Override
                    public void onResponse(String hueState) {
                        System.out.println("Wechseln der Textanzeige-prozent");
                    }

                    @Override
                    public void onResponse(Integer hueState) {
                        System.out.println("Wechseln der Textanzeige-prozent");
                    }

                    @Override
                    public void onResponse(Boolean hueState) {
                        System.out.println("Wechseln der Textanzeige-prozent");
                    }

                    @Override
                    public void onResponse(float sliderStatus) {

                    }

                    @Override
                    public void onResponse(JSONArray lightxy) throws JSONException {

                    }
                }, lichtnummer, newValue );
                MyViewHolder sliderHolder = holder;
                Integer proz = (int)value;
                sliderHolder.prozent.setText(proz+" %");
            }
        });
    }

    @Override
    public int getItemCount() {
        return bezeichnung.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView lampe;
        TextView prozent;
        Slider slider;
        ImageView colorPicker;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lampe = itemView.findViewById(R.id.akt_lampe);
            prozent = itemView.findViewById(R.id.akt_prozent);
            slider = itemView.findViewById(R.id.color_slider);
            colorPicker = itemView.findViewById(R.id.light_colorpicker);

        }
    }

    public double[] getRGBtoXY(float r, float g, float b) {
        // For the hue bulb the corners of the triangle are:
        // -Red: 0.675, 0.322
        // -Green: 0.4091, 0.518
        // -Blue: 0.167, 0.04
        double[] normalizedToOne = new double[3];
        float cred, cgreen, cblue;
        cred = r;
        cgreen = g;
        cblue = b;
        normalizedToOne[0] = (cred / 255);
        normalizedToOne[1] = (cgreen / 255);
        normalizedToOne[2] = (cblue / 255);
        float red, green, blue;
        // Make red more vivid
        if (normalizedToOne[0] > 0.04045) {
            red = (float) Math.pow(
                    (normalizedToOne[0] + 0.055) / (1.0 + 0.055), 2.4);
        } else {
            red = (float) (normalizedToOne[0] / 12.92);
        }
        // Make green more vivid
        if (normalizedToOne[1] > 0.04045) {
            green = (float) Math.pow((normalizedToOne[1] + 0.055)
                    / (1.0 + 0.055), 2.4);
        } else {
            green = (float) (normalizedToOne[1] / 12.92);
        }
        // Make blue more vivid
        if (normalizedToOne[2] > 0.04045) {
            blue = (float) Math.pow((normalizedToOne[2] + 0.055)
                    / (1.0 + 0.055), 2.4);
        } else {
            blue = (float) (normalizedToOne[2] / 12.92);
        }
        float X = (float) (red * 0.649926 + green * 0.103455 + blue * 0.197109);
        float Y = (float) (red * 0.234327 + green * 0.743075 + blue * 0.022598);
        float Z = (float) (red * 0.0000000 + green * 0.053077 + blue * 1.035763);
        float x = X / (X + Y + Z);
        float y = Y / (X + Y + Z);
        double[] xy = new double[2];
        xy[0] = x;
        xy[1] = y;
        return xy;
    }
}
