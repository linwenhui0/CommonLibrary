package com.hlibrary.utils

import android.app.Activity
import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.hlibrary.widget.listener.AfterTextChanged
import java.lang.reflect.Method

/**
 *
 * @author linwenhui
 * @date 2017/1/3
 */
object BinderUtil {
    @BindingConversion
    fun convertColorToDrawable(color: Int): ColorDrawable {
        return ColorDrawable(color)
    }

    /**
     * TODO View
     *
     * @param view
     * @param level
     */
    @BindingAdapter("android:backgroundLevel")
    fun setViewLevel(view: View, level: Int) {
        val drawable = view.background
        if (drawable != null) {
            drawable.level = level
        }
    }

    /**
     * TODO AppCompatTextView
     *
     * @param tv
     * @param level
     */
    @BindingAdapter("android:backgroundLevel")
    fun setTextViewLevel(tv: AppCompatTextView, level: Int) {
        setViewLevel(tv, level)
    }

    /**
     * TODO AppCompatCheckedTextView
     *
     * @param tv
     * @param level
     */
    @BindingAdapter("android:backgroundLevel")
    fun setCheckedTextViewLevel(tv: AppCompatCheckedTextView, level: Int) {
        setViewLevel(tv, level)
    }

    /**
     * TODO ImageButton
     *
     * @param tv
     * @param level
     */
    @BindingAdapter("android:backgroundLevel")
    fun setButtonLevel(tv: AppCompatImageButton, level: Int) {
        setViewLevel(tv, level)
    }

    /**
     * TODO ImageView
     *
     * @param imgvw
     * @param bg
     */
    @BindingAdapter("android:src")
    fun setSrc(imgvw: AppCompatImageView, bg: Bitmap?) {
        imgvw.setImageBitmap(bg)
    }

    @BindingAdapter("android:selected")
    fun setSelected(imgvw: AppCompatImageView, sel: Boolean) {
        imgvw.isSelected = sel
    }

    /**
     * TODO Button
     *
     * @param tv
     * @param level
     */
    @BindingAdapter("android:backgroundLevel")
    fun setButtonLevel(tv: AppCompatButton, level: Int) {
        setViewLevel(tv, level)
    }

    /**
     * TODO RadioButton
     *
     * @param tv
     * @param level
     */
    @BindingAdapter("android:backgroundLevel")
    fun setRadioButtonLevel(tv: AppCompatRadioButton, level: Int) {
        setViewLevel(tv, level)
    }

    /**
     * TODO LinearLayout
     *
     * @param tv
     * @param level
     */
    @BindingAdapter("android:backgroundLevel")
    fun setLinearLayoutLevel(tv: LinearLayout, level: Int) {
        setViewLevel(tv, level)
    }

    /**
     * TODO RelativeLayout
     *
     * @param tv
     * @param level
     */
    @BindingAdapter("android:backgroundLevel")
    fun setRelativeLayoutLevel(tv: RelativeLayout, level: Int) {
        setViewLevel(tv, level)
    }

    /**
     * TODO RadioGroup
     *
     * @param tv
     * @param level
     */
    @BindingAdapter("android:backgroundLevel")
    fun setRadioGroupLevel(tv: RadioGroup, level: Int) {
        setViewLevel(tv, level)
    }

    @BindingAdapter("android:onCheckedChange")
    fun setOnCheckedChangeListener(radioGroup: RadioGroup, listener: RadioGroup.OnCheckedChangeListener?) {
        radioGroup.setOnCheckedChangeListener(listener)
    }

    /**
     * TODO Toolbar
     *
     * @param toolbar
     * @param onClickListener
     */
    @BindingAdapter("android:navigationOnClickListener")
    fun navigationOnClickListener(toolbar: Toolbar, onClickListener: View.OnClickListener?) {
        if (onClickListener != null) {
            toolbar.setNavigationOnClickListener(onClickListener)
        }
    }

    @BindingAdapter("android:finish")
    fun setFinish(toolbar: Toolbar, isFinish: Boolean) {
        if (!isFinish) {
            return
        }
        val context = toolbar.context
        if (context is Activity) {
            toolbar.setNavigationOnClickListener { context.finish() }
        }
    }

    /**
     * ODO EditText
     *
     * @param editText
     * @param textChange
     */
    @BindingAdapter("android:afterTextChanged")
    fun afterTextChanged(editText: AppCompatEditText?, textChange: AfterTextChanged?) {
        if (editText != null && textChange != null) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    textChange.afterTextChanged(editText, editable)
                }
            })
        }
    }

    @BindingAdapter("android:keepSelectionStart")
    fun keepSelectionStart(edtxt: AppCompatEditText?, keep: Boolean) {
        if (keep && edtxt != null) {
            edtxt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (edtxt.selectionStart == 0) {
                        edtxt.setSelection(s.length)
                    }
                }
            })
        }
    }

    @BindingAdapter("android:closeInputMethod")
    fun closeInputMethod(edtxt: AppCompatEditText, closeInputMethod: Boolean) {
        if (closeInputMethod) {
            if (Build.VERSION.SDK_INT <= 10) {
                edtxt.inputType = InputType.TYPE_NULL
            } else {
                if (edtxt.context is Activity) {
                    var method: Method
                    val cls = EditText::class.java
                    try {
                        method = cls.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
                        method.isAccessible = true
                        method.invoke(edtxt, false)
                    } catch (e: Exception) { // TODO: handle exception
                    }
                    try {
                        method = cls.getMethod("setSoftInputShownOnFocus", Boolean::class.javaPrimitiveType)
                        method.isAccessible = true
                        method.invoke(edtxt, false)
                    } catch (e: Exception) { // TODO: handle exception
                    }
                } else {
                    edtxt.inputType = InputType.TYPE_NULL
                }
            }
        }
    }

    //TODO TextInputEditText
    @BindingAdapter("android:afterTextChanged")
    fun afterTextChanged(editText: TextInputEditText?, textChange: AfterTextChanged?) {
        if (editText != null && textChange != null) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    textChange.afterTextChanged(editText, editable)
                }
            })
        }
    }

    @BindingAdapter("android:keepSelectionStart")
    fun keepSelectionStart(edtxt: TextInputEditText?, keep: Boolean) {
        if (keep && edtxt != null) {
            edtxt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (edtxt.selectionStart == 0) {
                        edtxt.setSelection(s.length)
                    }
                }
            })
        }
    }

    @BindingAdapter("android:closeInputMethod")
    fun closeInputMethod(edtxt: TextInputEditText, closeInputMethod: Boolean) {
        if (closeInputMethod) {
            if (Build.VERSION.SDK_INT <= 10) {
                edtxt.inputType = InputType.TYPE_NULL
            } else {
                if (edtxt.context is Activity) {
                    var method: Method
                    val cls = EditText::class.java
                    try {
                        method = cls.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
                        method.isAccessible = true
                        method.invoke(edtxt, false)
                    } catch (e: Exception) { // TODO: handle exception
                    }
                    try {
                        method = cls.getMethod("setSoftInputShownOnFocus", Boolean::class.javaPrimitiveType)
                        method.isAccessible = true
                        method.invoke(edtxt, false)
                    } catch (e: Exception) { // TODO: handle exception
                    }
                } else {
                    edtxt.inputType = InputType.TYPE_NULL
                }
            }
        }
    }
}