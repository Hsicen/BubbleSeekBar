package com.xw.samlpe.bubbleseekbar

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.lang.ref.WeakReference


/**
 * 作者：黄思程  2020/10/13 18:45
 * 邮箱：huangsicheng@camera360.com
 * 功能：
 * 描述：弹窗Dialog
 */
abstract class CoreDialogFragment : DialogFragment() {

    /** 布局文件id. */
    @get: LayoutRes
    protected abstract val layoutId: Int

    /** 根布局. */
    protected var root: View? = null
    open val inAnimTime = 300L
    open val outAnimTime = 300L
    open val windowAlpha = 0.2f  //设置window背景

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //去掉默认的白色边框
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (root != null) {
            (root?.parent as? ViewGroup)?.removeView(root)
        } else {
            root = inflater.inflate(layoutId, container, false)
        }

        hideSystemUI(root)

        return root
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        fixDialogFragmentLeak()
        return super.onGetLayoutInflater(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInit(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        //设置window透明度
        val current = dialog?.window ?: return
        val wp = current.attributes
        wp.dimAmount = windowAlpha
        current.attributes = wp
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI(root)
    }

    @CallSuper
    override fun onDestroyView() {
        dialog?.setOnCancelListener(null)
        dialog?.setOnDismissListener(null)
        super.onDestroyView()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    /*** 初始化.*/
    abstract fun onInit(view: View, savedInstanceState: Bundle?)

    /**
     * LiveData 扩展.
     * @receiver LiveData<T>
     * @param block (data: T) -> Unit
     */
    infix fun <T> LiveData<T>.observe(block: (data: T) -> Unit) =
        this.observe(this@CoreDialogFragment, Observer { block.invoke(it) })

    //底部入场动画
    fun View.bottomIn(endAction: (() -> Unit)? = null) {
        this.post {
            this.translationY = this.height.toFloat()
            this.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(inAnimTime)
                .setInterpolator(FastOutSlowInInterpolator())
                .withEndAction { endAction?.invoke() }
                .start()
        }
    }

    //离场动画
    fun View.topOut(endAction: (() -> Unit)? = null) {
        this.post {
            this.animate()
                .translationY(this.height.toFloat())
                .setDuration(outAnimTime)
                .setInterpolator(FastOutSlowInInterpolator())
                .withEndAction { endAction?.invoke() }
                .start()
        }
    }

    private fun hideSystemUI(view: View?) {
        view?.let {
            var flags = it.systemUiVisibility
            flags = flags or (
                // 无论使用哪种全屏模式，都必须含有下面两个 flag
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
                    // 防止布局随着系统栏的隐藏和显示调整大小
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    // 沉浸模式: 适用于用户将与屏幕进行大量互动的应用,当用户需要调出系统栏时,他们可从隐藏系统栏的任一边滑动
                    or View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
            it.systemUiVisibility = flags
        }
    }

    private fun fixDialogFragmentLeak() {
        try {
            val clazz = DialogFragment::class.java
            val onCancelListener = clazz.getDeclaredField("mOnCancelListener")
            onCancelListener.isAccessible = true
            onCancelListener.set(this, FixLeakOnCancelListener(this))
            val onDismissListener = clazz.getDeclaredField("mOnDismissListener")
            onDismissListener.isAccessible = true
            onDismissListener.set(this, FixLeakOnDismissListener(this))
        } catch (e: Exception) {
        }
    }

    class FixLeakOnCancelListener(dialogFragment: DialogFragment) :
        DialogInterface.OnCancelListener {

        private val fragment = WeakReference(dialogFragment)

        override fun onCancel(dialog: DialogInterface?) {
            dialog?.let {
                fragment.get()?.onCancel(it)
            }
        }
    }

    class FixLeakOnDismissListener(dialogFragment: DialogFragment) :
        DialogInterface.OnDismissListener {

        private val fragment = WeakReference(dialogFragment)

        override fun onDismiss(dialog: DialogInterface?) {
            dialog?.let { fragment.get()?.onDismiss(it) }
        }

    }
}
