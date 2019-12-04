package com.inlacou.exinput.spinner.popups

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.ListView
import androidx.appcompat.widget.ListPopupWindow
import com.inlacou.exinput.spinner.MaterialSpinner
import com.inlacou.exinput.spinner.SpinnerPopup

/**
 * A PopupWindow that anchors itself to a host view and displays a list of choices.
 */
@SuppressLint("RestrictedApi")
internal class DropdownPopup
    (context: Context, attrs: AttributeSet?, private val spinner: MaterialSpinner)
    : ListPopupWindow(context, attrs), SpinnerPopup {

    init {
        inputMethodMode = INPUT_METHOD_NOT_NEEDED
        anchorView = spinner
        isModal = true
        promptPosition = POSITION_PROMPT_ABOVE
        setOverlapAnchor(false)

        setOnItemClickListener { parent, v, position, id ->
            spinner.selected = position
            spinner.onItemClickListener?.let {
                spinner.performItemClick(
                    v,
                    position,
                    spinner.adapter?.getItemId(position) ?: 0L
                )
            }
            dismiss()
        }
    }

    override fun show(position: Int) {
        super.show()
        listView?.let {
            it.choiceMode = ListView.CHOICE_MODE_SINGLE
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                it.textDirection = spinner.textDirection
                it.textAlignment = spinner.textAlignment
            }
        }
        setSelection(position)
    }

    override fun setOnDismissListener(listener: SpinnerPopup.OnDismissListener?) {
        super.setOnDismissListener {
            listener?.onDismiss()
        }
    }

    override fun setPromptText(hintText: CharSequence?) = Unit

    override fun getPrompt(): CharSequence? {
        return null
    }

    override fun getItem(position: Int): Any? {
        return spinner.adapter?.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return spinner.adapter?.getItemId(position) ?: -1
    }
}