package com.inlacou.exinput.spinner

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.text.InputType
import android.util.AttributeSet
import android.view.*
import android.view.View.OnFocusChangeListener
import android.view.accessibility.AccessibilityEvent
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import androidx.customview.view.AbsSavedState
import com.google.android.material.textfield.TextInputEditText
import com.inlacou.exinput.R
import com.inlacou.exinput.spinner.popups.BottomSheetPopup
import com.inlacou.exinput.spinner.popups.DialogPopup
import com.inlacou.exinput.spinner.popups.DropDownAdapter
import com.inlacou.exinput.spinner.popups.DropdownPopup
import timber.log.Timber
import java.util.*

/**
 * [TextInputEditText] as spinner.
 *
 * @see [TextInputEditText]
 * original @author Tiago Pereira (tiagomiguelmoreirapereira@gmail.com)
 * current @author Inlacou (inlacou@gmail.com)
 */
open class MaterialSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    mode: Mode = Mode.MODE_DROPDOWN
) : TextInputEditText(context, attrs) {

    private val colorStateList: ColorStateList

    /**
     * The view that will display the available list of choices.
     */
    private val popup: SpinnerPopup

    /**
     * Extended [android.widget.Adapter] that is the bridge between this Spinner and its data.
     */
    var adapter: SpinnerAdapter? = null
        set(value) {
            field = DropDownAdapter(value, context.theme).also {
                popup.setAdapter(it)
            }
        }

    /**
     * The listener that receives notifications when an item is selected.
     */
    var onItemSelectedListener: OnItemSelectedListener? = null

    /**
     * The listener that receives notifications when an item is clicked.
     */
    var onItemClickListener: OnItemClickListener? = null

    /**
     * The layout direction of this view.
     * {@link #LAYOUT_DIRECTION_RTL} if the layout direction is RTL.
     * {@link #LAYOUT_DIRECTION_LTR} if the layout direction is not RTL.
     */
    private var direction = if (isLayoutRtl()) ViewCompat.LAYOUT_DIRECTION_RTL else ViewCompat.LAYOUT_DIRECTION_LTR

    /**
     * The currently selected item.
     */
    var selected = -1
        set(value) {
            field = value
            adapter?.apply {
                if (value in 0 until count) {
                    setText(
                        when (val item = getItem(value)) {
                            is CharSequence -> item
                            else -> item.toString()
                        }
                    )
                    onItemSelectedListener?.onItemSelected(
                        this@MaterialSpinner,
                        null,
                        value,
                        getItemId(value)
                    )
                } else {
                    setText("")
                    onItemSelectedListener?.onNothingSelected(this@MaterialSpinner)
                }
            }
        }
    /**
     * Sets the [prompt] to display when the dialog is shown.
     *
     * @return The prompt to display when the dialog is shown.
     */
    var prompt: CharSequence?
        set(value) {
            popup.setPromptText(value)
        }
        get() = popup.getPrompt()

    /**
     * @return The data corresponding to the currently selected item, or null if there is nothing
     * selected.
     */
    val selectedItem get() = popup.getItem(selected)

    /**
     * @return The id corresponding to the currently selected item, or {@link #INVALID_ROW_ID} if
     * nothing is selected.
     */
    val selectedItemId get() = popup.getItemId(selected)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.MaterialSpinner).run {
            getInt(R.styleable.MaterialSpinner_android_gravity, -1).let {
                if (it > -1) {
                    gravity = it
                    gravity = it
                } else {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        @SuppressLint("RtlHardcoded")
                        if (isLayoutRtl()) {
                            gravity = Gravity.RIGHT
                            gravity = Gravity.RIGHT
                        } else {
                            gravity = Gravity.LEFT
                            gravity = Gravity.LEFT
                        }
                    }
                }
            }
            isEnabled = getBoolean(R.styleable.MaterialSpinner_android_enabled, isEnabled)
            isFocusable = getBoolean(R.styleable.MaterialSpinner_android_focusable, isFocusable)
            isFocusableInTouchMode = getBoolean(R.styleable.MaterialSpinner_android_focusableInTouchMode, isFocusableInTouchMode)
            getColorStateList(R.styleable.MaterialSpinner_android_textColor)?.let { setTextColor(it) }
            getDimensionPixelSize(R.styleable.MaterialSpinner_android_textSize, -1).let { if (it > 0) textSize = it.toFloat() }
            getText(R.styleable.MaterialSpinner_android_text)?.let {
                // Allow text in debug mode for preview purposes.
                if (isInEditMode) {
                    setText(it)
                } else {
                    throw RuntimeException(
                        "Don't set text directly." +
                                "You probably want setSelection instead."
                    )
                }
            }
            popup = when (Mode.values()[getInt(R.styleable.MaterialSpinner_spinnerMode, mode.ordinal)]) {
                Mode.MODE_DIALOG -> DialogPopup(
                    context,
                    getString(R.styleable.MaterialSpinner_android_prompt),
                    this@MaterialSpinner
                )
                Mode.MODE_BOTTOM_SHEET -> BottomSheetPopup(
                    context,
                    getString(R.styleable.MaterialSpinner_android_prompt),
                    this@MaterialSpinner
                )
                else -> DropdownPopup(
                    context,
                    attrs,
                    this@MaterialSpinner
                )
            }

            // Create the color state list.
            //noinspection Recycle
            colorStateList = context.obtainStyledAttributes(attrs, intArrayOf(
                R.attr.colorControlActivated,
                R.attr.colorControlNormal
            )).run {
                val activated = getColor(0, 0)
                @SuppressLint("ResourceType")
                val normal = getColor(1, 0)
                recycle()
                ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_pressed),
                        intArrayOf(android.R.attr.state_focused),
                        intArrayOf()
                    ), intArrayOf(activated, activated, normal)
                )
            }
            // Set the arrow and properly tint it.
            getContext().getDrawableCompat(
                getResourceId(
                    R.styleable.MaterialSpinner_android_src,
                    getResourceId(
                        R.styleable.MaterialSpinner_srcCompat,
                        R.drawable.ic_spinner_drawable
                    )
                ), getContext().theme
            ).let {
                setDrawable(it)
            }
            recycle()
        }

        popup.setOnDismissListener(object :
            SpinnerPopup.OnDismissListener {
            override fun onDismiss() {
                clearFocus()
            }
        })

        // Disable input.
        maxLines = 1
        inputType = InputType.TYPE_NULL

        setOnClickListener {
            Timber.d("inlakou | onClickListener | call .show")
            popup.show(selected)
        }

        onFocusChangeListener.let {
            Timber.d("inlakou | onFocusChangeListener | let")
            onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                Timber.d("inlakou | onFocusChangeListener | fired | hasFocus: $hasFocus")
                v.handler.post {
                    Timber.d("inlakou | onFocusChangeListener | handler | fired")
                    if (hasFocus) {
                        v.performClick()
                    }
                }
            }
        }
    }

    fun setDrawable(drawable: Drawable?, applyTint: Boolean = true) {
        val delta = (paddingBottom - paddingTop) / 2
        drawable?.let { DrawableCompat.wrap(drawable) }?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            if (applyTint) {
                DrawableCompat.setTintList(this, colorStateList)
                DrawableCompat.setTintMode(this, PorterDuff.Mode.SRC_IN)
            }
        }.let {
            if (isLayoutRtl()) {
                Pair(it, null)
            } else {
                Pair(null, it)
            }
        }.let { (left, right) ->
            setCompoundDrawablesWithIntrinsicBounds(left, null, right, null)
            compoundDrawables.forEach {
                it?.run {
                    bounds = copyBounds().apply {
                        top += delta
                        bottom += delta
                    }
                }
            }
        }
    }

    /**
     * @see [android.view.View.onRtlPropertiesChanged]
     */
    override fun onRtlPropertiesChanged(layoutDirection: Int) {
        if (direction != layoutDirection) {
            direction = layoutDirection
            compoundDrawables.let {
                setCompoundDrawablesWithIntrinsicBounds(it[2], null, it[0], null)
            }
        }
        super.onRtlPropertiesChanged(layoutDirection)
    }

    override fun setError(errorText: CharSequence?) {
        super.setError(errorText)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            findViewById<TextView>(R.id.textinput_error)?.let { errorView ->
                errorView.gravity = gravity
                when (val p = errorView.parent) {
                    is View -> {
                        p.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    }
                }
            }
        }
    }

    /**
     * Call the OnItemClickListener, if it is defined.
     * Performs all normal actions associated with clicking: reporting accessibility event, playing
     * a sound, etc.
     *
     * @param [view] The view within the adapter that was clicked.
     * @param [position] The position of the view in the adapter.
     * @param [id] The row id of the item that was clicked.
     * @return True if there was an assigned OnItemClickListener that was called, false otherwise is
     * returned.
     */
    fun performItemClick(view: View?, position: Int, id: Long): Boolean {
        return run {
            onItemClickListener?.let {
                playSoundEffect(SoundEffectConstants.CLICK)
                it.onItemClick(this@MaterialSpinner, view, position, id)
                true
            } ?: false
        }.also {
            view?.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED)
        }
    }

    /**
     * Sets the prompt to display when the dialog is shown.
     *
     * @param [promptId] the resource ID of the prompt to display when the dialog is shown.
     */
    fun setPromptId(promptId: Int) {
        prompt = context.getText(promptId)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            this.selection = this@MaterialSpinner.selected
            this.isShowingPopup = this@MaterialSpinner.popup.isShowing()
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        when (state) {
            is SavedState -> {
                super.onRestoreInstanceState(state.superState)
                selected = state.selection
                if (state.isShowingPopup) {
                    viewTreeObserver?.addOnGlobalLayoutListener(object :
                        ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            Timber.d("inlakou | onGlobalLayout")
                            if (!popup.isShowing()) {
                                requestFocus()
                            }
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                                viewTreeObserver?.removeOnGlobalLayoutListener(this)
                            } else {
                                viewTreeObserver?.removeGlobalOnLayoutListener(this)
                            }
                        }
                    })
                }
            }
            else -> super.onRestoreInstanceState(state)
        }
    }

    /**
     * Returns if this view layout should be in a RTL direction.
     * @return True if is RTL, false otherwise .
     */
    private fun isLayoutRtl(): Boolean {
        return Locale.getDefault().isLayoutRtl()
    }

    /**
     * Returns if this Locale direction is RTL.
     * @return True if is RTL, false otherwise .
     */
    private fun Locale.isLayoutRtl(): Boolean {
        return TextUtilsCompat.getLayoutDirectionFromLocale(this) == ViewCompat.LAYOUT_DIRECTION_RTL
    }

    /**
     * @see [android.support.v4.content.res.ResourcesCompat.getDrawable]
     */
    private fun Context.getDrawableCompat(
        @DrawableRes id: Int,
        theme: Resources.Theme?
    ): Drawable? {
        return resources.getDrawableCompat(id, theme)
    }

    /**
     * @see [android.support.v4.content.res.ResourcesCompat.getDrawable]
     */
    @Throws(Resources.NotFoundException::class)
    private fun Resources.getDrawableCompat(
        @DrawableRes id: Int,
        theme: Resources.Theme?
    ): Drawable? {
        return ResourcesCompat.getDrawable(this, id, theme)?.let { DrawableCompat.wrap(it) }
    }

    /**
     * Interface for a callback to be invoked when an item in this view has been selected.
     */
    interface OnItemSelectedListener {
        /**
         * Callback method to be invoked when an item in this view has been selected.
         * This callback is invoked only when the newly selected position is different from the
         * previously selected position or if there was no selected item.
         * Implementers can call getItemAtPosition(position) if they need to access the data
         * associated with the selected item.
         *
         * @param [parent] The View where the selection happened.
         * @param [view] The view within the Adapter that was clicked.
         * @param [position] The position of the view in the adapter.
         * @param [id] The row id of the item that is selected.
         */
        fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long)

        /**
         * Callback method to be invoked when the selection disappears from this view.
         * The selection can disappear for instance when touch is activated or when the adapter
         * becomes empty.
         *
         * @param [parent] The View that now contains no selected item.
         */
        fun onNothingSelected(parent: MaterialSpinner)
    }

    /**
     * Interface definition for a callback to be invoked when an item in this View has been clicked.
     */
    interface OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this View has been clicked.
         * Implementers can call getItemAtPosition(position) if they need to access the data
         * associated with the selected item.
         *
         * @param [parent] The View where the click happened.
         * @param [view] The view within the adapter that was clicked (this will be a view provided
         * by the adapter).
         * @param [position] The position of the view in the adapter.
         * @param [id] The row id of the item that was clicked.
         */
        fun onItemClick(parent: MaterialSpinner, view: View?, position: Int, id: Long)
    }

    internal class SavedState : AbsSavedState {
        var selection: Int = -1
        var isShowingPopup: Boolean = false

        constructor(superState: Parcelable) : super(superState)

        constructor(source: Parcel, loader: ClassLoader?) : super(source, loader) {
            selection = source.readInt()
            isShowingPopup = source.readByte().toInt() != 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(selection)
            dest.writeByte((if (isShowingPopup) 1 else 0).toByte())
        }

        override fun toString(): String {
            return ("MaterialSpinner.SavedState{" +
                    Integer.toHexString(System.identityHashCode(this)) +
                    " selection=" +
                    selection +
                    ", isShowingPopup=" +
                    isShowingPopup +
                    "}")
        }

        companion object CREATOR : Parcelable.ClassLoaderCreator<SavedState> {

            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel, null)
            }

            override fun createFromParcel(parcel: Parcel, loader: ClassLoader): SavedState {
                return SavedState(parcel, loader)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

    enum class Mode {
        /**
         * Use a dialog window for selecting spinner options.
         */
        MODE_DIALOG,
        /**
         * Use a dropdown anchored to the Spinner for selecting spinner options.
         */
        MODE_DROPDOWN,
        /**
         * Use a bottom sheet dialog window for selecting spinner options.
         */
        MODE_BOTTOM_SHEET
    }

}