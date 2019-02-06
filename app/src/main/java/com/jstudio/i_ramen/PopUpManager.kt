package com.jstudio.i_ramen

import android.content.Context
import android.view.View
import razerdp.basepopup.BasePopupWindow
import android.view.animation.Animation
import android.widget.CalendarView


class PopUpManager {

    class PopUpManagerTrophy(context: Context?) : BasePopupWindow(context) {

        override fun onCreateContentView(): View? {
            return createPopupById(R.layout.trophy_popup)
        }

        override fun onCreateShowAnimation(): Animation {
            return getDefaultScaleAnimation(true)
        }

        override fun onCreateDismissAnimation(): Animation {
            return getDefaultScaleAnimation(false)
        }

    }

    class PopUpManagerCalendar(context: Context?) : BasePopupWindow(context) {

        override fun onCreateContentView(): View? {
            return createPopupById(R.layout.calendar_popup)
        }

        override fun onCreateShowAnimation(): Animation {
            return getDefaultScaleAnimation(true)
        }

        override fun onCreateDismissAnimation(): Animation {
            return getDefaultScaleAnimation(false)
        }

        override fun setOnDismissListener(onDismissListener: OnDismissListener?): BasePopupWindow {
            return super.setOnDismissListener(onDismissListener)
        }

    }
}