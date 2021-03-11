package com.xw.samlpe.bubbleseekbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xw.repo.BubbleSeekBar

/**
 * DemoFragment2
 *
 *
 * Created by woxingxiao on 2017-03-11.
 */
class DemoFragment5 : Fragment() {
    private var fadeStart: BubbleSeekBar? = null
    private var fadeEnd: BubbleSeekBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_demo_5, container, false)
        fadeStart = rootView.findViewById(R.id.bsbFadeStart)
        fadeEnd = rootView.findViewById(R.id.bsbFadeEnd)

        rootView.findViewById<View>(R.id.fadeMenu).setOnClickListener {

        }
        return rootView
    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        @JvmStatic
        fun newInstance(): DemoFragment5 {
            return DemoFragment5()
        }
    }
}