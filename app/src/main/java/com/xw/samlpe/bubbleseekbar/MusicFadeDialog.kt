package com.xw.samlpe.bubbleseekbar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.xw.repo.BubbleSeekBar
import kotlinx.android.synthetic.main.editor_music_fade.*

/**
 * 作者：黄思程  2020/12/10 15:22
 * 邮箱：huangsicheng@camera360.com
 * 功能：
 * 描述：用户登录弹窗
 * 出现位置：设置页面 和首页弹出
 */
class MusicFadeDialog : CoreDialogFragment() {
    override val windowAlpha = 0f

    private var mChangedResult: ((start: Float, end: Float, changed: Boolean) -> Unit)? = null
    private var mFadeChange: ((fadeData: Float, fadeStart: Boolean) -> Unit)? = null

    private var fadeStart = 0f
    private var fadeEnd = 0f
    private var changed = false

    override val layoutId = R.layout.editor_music_fade

    override fun getTheme() = R.style.Dialog_FullScreen

    override fun onInit(view: View, savedInstanceState: Bundle?) {
        val mAct = activity ?: return
        clMusicFade.bottomIn {
            bsbFadeStart.correctOffsetWhenContainerOnScrolling()
            bsbFadeEnd.correctOffsetWhenContainerOnScrolling()
        }

        bsbFadeStart.setProgress(fadeStart)
        bsbFadeEnd.setProgress(fadeEnd)

        bsbFadeStart.onProgressChangedListener =
            object : BubbleSeekBar.OnProgressChangedListenerAdapter() {
                override fun onProgressChanged(
                    bubbleSeekBar: BubbleSeekBar?,
                    progress: Int,
                    progressFloat: Float,
                    fromUser: Boolean
                ) {
                    super.onProgressChanged(bubbleSeekBar, progress, progressFloat, fromUser)
                    fadeStart = progressFloat
                    changed = fromUser
                    if (fromUser) mFadeChange?.invoke(fadeStart, true)
                }
            }

        bsbFadeEnd.onProgressChangedListener =
            object : BubbleSeekBar.OnProgressChangedListenerAdapter() {
                override fun onProgressChanged(
                    bubbleSeekBar: BubbleSeekBar?,
                    progress: Int,
                    progressFloat: Float,
                    fromUser: Boolean
                ) {
                    super.onProgressChanged(bubbleSeekBar, progress, progressFloat, fromUser)
                    fadeEnd = progressFloat
                    changed = fromUser

                    if (fromUser) mFadeChange?.invoke(fadeEnd, false)
                }
            }


        bottomFade.onClickCancel {
            dismiss()
        }

        bottomFade.onClickSure {
            mChangedResult?.invoke(fadeStart, fadeEnd, changed)
            dismiss()
        }

        clMusicFade.click { }
        flMusicFade.click {
            dismiss()
        }
    }

    fun fadeData(start: Float, end: Float): MusicFadeDialog {
        fadeStart = start
        fadeEnd = end

        return this
    }

    fun onFadeChange(onFadeChange: (changedFade: Float, fadeStart: Boolean) -> Unit): MusicFadeDialog {
        mFadeChange = onFadeChange

        return this
    }

    fun onResult(onResult: (start: Float, end: Float, changed: Boolean) -> Unit): MusicFadeDialog {
        mChangedResult = onResult
        return this
    }

    fun show(manager: FragmentManager) {
        this.show(manager, this::javaClass.name)
    }

    override fun dismiss() {
        clMusicFade.let {
            it.topOut()
            it.postDelayed({
                super.dismiss()
            }, outAnimTime)
        }
    }

    override fun onDestroyView() {
        this.mFadeChange = null
        this.mChangedResult = null
        super.onDestroyView()
    }

}