package me.reezy.cosmo.popup

import android.view.ViewGroup
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import androidx.annotation.IntDef
import androidx.core.widget.PopupWindowCompat


class Popup(contentView: View? = null, width: Int = 0, height: Int = 0, focusable: Boolean = false) :
    PopupWindow(contentView, width, height, focusable) {

    @IntDef(VPosition.CENTER, VPosition.ABOVE, VPosition.BELOW, VPosition.ALIGN_TOP, VPosition.ALIGN_BOTTOM)
    @Retention(AnnotationRetention.SOURCE)
    annotation class VPosition {
        companion object {
            const val CENTER = 0
            const val ABOVE = 1
            const val BELOW = 2
            const val ALIGN_TOP = 3
            const val ALIGN_BOTTOM = 4
        }
    }

    @IntDef(HPosition.CENTER, HPosition.LEFT, HPosition.RIGHT, HPosition.ALIGN_LEFT, HPosition.ALIGN_RIGHT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class HPosition {
        companion object {
            const val CENTER = 0
            const val LEFT = 1
            const val RIGHT = 2
            const val ALIGN_LEFT = 3
            const val ALIGN_RIGHT = 4
        }
    }

    @JvmOverloads
    fun show(anchor: View, @VPosition vPos: Int, @HPosition hPos: Int, x: Int = 0, y: Int = 0, fitInScreen: Boolean = true) {
        var realX = x
        var realY = y
        isClippingEnabled = fitInScreen
        val contentView = contentView
        contentView.measure(spec(width), spec(height))
        val measuredW = contentView.measuredWidth
        val measuredH = contentView.measuredHeight
        if (!fitInScreen) {
            val location = IntArray(2)
            anchor.getLocationInWindow(location)
            realX += location[0]
            realY += location[1] + anchor.height
        }
        when (vPos) {
            VPosition.ABOVE -> realY -= measuredH + anchor.height
            VPosition.ALIGN_BOTTOM -> realY -= measuredH
            VPosition.CENTER -> realY -= anchor.height / 2 + measuredH / 2
            VPosition.ALIGN_TOP -> realY -= anchor.height
            VPosition.BELOW -> { }
        }
        when (hPos) {
            HPosition.LEFT -> realX -= measuredW
            HPosition.ALIGN_RIGHT -> realX -= measuredW - anchor.width
            HPosition.CENTER -> realX += anchor.width / 2 - measuredW / 2
            HPosition.ALIGN_LEFT -> { }
            HPosition.RIGHT -> realX += anchor.width
        }
        if (fitInScreen) {
            PopupWindowCompat.showAsDropDown(this, anchor, realX, realY, Gravity.NO_GRAVITY)
        } else {
            showAtLocation(anchor, Gravity.NO_GRAVITY, realX, realY)
        }
    }

    private fun spec(value: Int) = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(value), when (value) {
        ViewGroup.LayoutParams.WRAP_CONTENT -> View.MeasureSpec.UNSPECIFIED
        else -> View.MeasureSpec.EXACTLY
    })

}