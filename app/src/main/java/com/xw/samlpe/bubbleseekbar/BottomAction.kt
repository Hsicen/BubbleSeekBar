package com.xw.samlpe.bubbleseekbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.include_bottom_action_comm.view.*

class BottomAction @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var title: String = ""

    init {
        LayoutInflater.from(context).inflate(R.layout.include_bottom_action_comm, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomAction)
        title = typedArray.getString(R.styleable.BottomAction_bottom_title) ?: ""
        typedArray.recycle()
        setTitle(title)
    }

    fun setTitle(title: String) {
        this.title = title
        tvEditBottomTitle.text = title
    }

    fun onClickCancel(action: () -> Unit) {
        ivEditBottomCancel.clickThrottle {
            action()
        }
    }

    fun onClickSure(action: () -> Unit) {
        ivEditBottomSure.clickThrottle { action() }
    }
}