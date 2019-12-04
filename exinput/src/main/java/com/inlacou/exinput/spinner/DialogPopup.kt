package com.inlacou.exinput.spinner

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.widget.ListAdapter

internal class DialogPopup(
    val context: Context,
    private var prompt: CharSequence? = null,
    private val spinner: MaterialSpinner
) : DialogInterface.OnClickListener, SpinnerPopup {

    private var popup: AlertDialog? = null
    private var adapter: ListAdapter? = null
    private var listener: SpinnerPopup.OnDismissListener? = null

    override fun setAdapter(adapter: ListAdapter?) {
        this.adapter = adapter
    }

    override fun setPromptText(hintText: CharSequence?) {
        prompt = hintText
    }

    override fun getPrompt(): CharSequence? {
        return prompt
    }

    override fun show(position: Int) {
        if (adapter == null) {
            return
        }

        popup = adapter?.let { adapter ->
            AlertDialog.Builder(context).let { builder ->
                prompt?.let { prompt ->
                    builder.setTitle(prompt)
                }
                builder.setSingleChoiceItems(adapter, position, this).create().apply {
                    popup?.listView?.let {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                            it.textDirection = spinner.textDirection
                            it.textAlignment = spinner.textAlignment
                        }
                    }
                    setOnDismissListener { listener?.onDismiss() }
                }
            }.also {
                it.show()
            }
        }
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        spinner.selected = which
        spinner.onItemClickListener?.let {
            spinner.performItemClick(null, which, adapter?.getItemId(which) ?: 0L)
        }
        popup?.dismiss()
    }

    override fun setOnDismissListener(listener: SpinnerPopup.OnDismissListener?) {
        this.listener = listener
    }

    override fun getItem(position: Int): Any? {
        return adapter?.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return adapter?.getItemId(position) ?: -1
    }

    override fun isShowing() = popup?.isShowing == true
}