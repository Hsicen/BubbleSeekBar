package com.xw.samlpe.bubbleseekbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xw.repo.BubbleSeekBar;

/**
 * DemoFragment2
 * <p>
 * Created by woxingxiao on 2017-03-11.
 */

public class DemoFragment5 extends Fragment {

    private BubbleSeekBar fadeStart;
    private BubbleSeekBar fadeEnd;

    public static DemoFragment5 newInstance() {
        return new DemoFragment5();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_demo_5, container, false);
        fadeStart = rootView.findViewById(R.id.bsbFadeStart);
        fadeEnd = rootView.findViewById(R.id.bsbFadeEnd);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        /*fadeStart.enableSecondShader();
        fadeEnd.enableSecondShader();*/
    }
}
