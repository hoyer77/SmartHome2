package com.smarthome;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.util.VLCVideoLayout;


public class Cam extends Fragment {

    ImageButton back;

    @SuppressLint("AuthLeak")
    final String url1 = "rtsp://guest:Stefan77_@goetschmann.internet-box.ch:19554/h264Preview_01_sub";

    ImageButton btnPlayPause;
    private LibVLC libVlc;
    private MediaPlayer mediaPlayer;
    private VLCVideoLayout videoLayout;

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cam,container,false);


        // Steuerung des zurück-Buttons
        back = view.findViewById(R.id.btn_back);
        back.setOnClickListener(v -> {
            FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.container, new Welcome()).commit();
        });

        btnPlayPause = view.findViewById((R.id.btn_play_pause));
        View.OnClickListener onClickListenerButtons = v -> {

            libVlc = new LibVLC(view.getContext());
            mediaPlayer = new MediaPlayer(libVlc);
            videoLayout = view.findViewById(R.id.videoLayout);

            mediaPlayer.attachViews(videoLayout, null, false, false);

            Media media = new Media(libVlc, Uri.parse(url1));
            media.setHWDecoderEnabled(true, false);
            media.addOption(":network-caching=600");

            mediaPlayer.setMedia(media);
            media.release();
            mediaPlayer.play();
            btnPlayPause.setVisibility(View.GONE);
        };
        btnPlayPause.setOnClickListener(onClickListenerButtons);

        return view;
    }
}