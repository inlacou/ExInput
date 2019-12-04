package com.inlacou.exinput.spinner

import android.content.res.Resources
import android.database.DataSetObserver
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.SpinnerAdapter
import androidx.appcompat.widget.ThemedSpinnerAdapter

/**
     * Creates a new ListAdapter wrapper for the specified adapter.
     *
     * @param [adapter] The SpinnerAdapter to transform into a ListAdapter.
     * @param [dropDownTheme] The theme against which to inflate drop-down views, may be {@null}
     * to use default theme.
     */
    internal class DropDownAdapter(
    private val adapter: SpinnerAdapter?,
    dropDownTheme: Resources.Theme?
    ) : ListAdapter, SpinnerAdapter {

        private val listAdapter: ListAdapter?

        init {
            listAdapter = when (val it = adapter) {
                is ListAdapter -> it
                else -> null
            }

            dropDownTheme?.let {
                when (adapter) {
                    is ThemedSpinnerAdapter -> {
                        if (adapter.dropDownViewTheme != it) {
                            adapter.dropDownViewTheme = it
                        }
                    }
                    else -> {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                            when (adapter) {
                                is android.widget.ThemedSpinnerAdapter -> {
                                    if (adapter.dropDownViewTheme == null) {
                                        adapter.dropDownViewTheme = it
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        override fun getCount(): Int {
            return adapter?.count ?: 0
        }

        override fun getItem(position: Int): Any? {
            return adapter?.let {
                if (position >= 0 && position < it.count) it.getItem(
                    position
                ) else null
            }
        }

        override fun getItemId(position: Int): Long {
            return adapter?.getItemId(position) ?: -1
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            return getDropDownView(position, convertView, parent)
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
            return adapter?.getDropDownView(position, convertView, parent)
        }

        override fun hasStableIds(): Boolean {
            return adapter?.hasStableIds() ?: false
        }

        override fun registerDataSetObserver(observer: DataSetObserver) {
            adapter?.registerDataSetObserver(observer)
        }

        override fun unregisterDataSetObserver(observer: DataSetObserver) {
            adapter?.unregisterDataSetObserver(observer)
        }

        /**
         * If the wrapped SpinnerAdapter is also a ListAdapter, delegate this call. Otherwise,
         * return true.
         */
        override fun areAllItemsEnabled(): Boolean {
            return listAdapter?.areAllItemsEnabled() ?: true
        }

        /**
         * If the wrapped SpinnerAdapter is also a ListAdapter, delegate this call. Otherwise,
         * return true.
         */
        override fun isEnabled(position: Int): Boolean {
            return listAdapter?.isEnabled(position) ?: true
        }

        override fun getItemViewType(position: Int): Int {
            return 0
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun isEmpty(): Boolean {
            return count == 0
        }
    }