package com.hlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hlibrary.widget.listener.AfterTextChanged;

import java.lang.reflect.Method;

/**
 * Created by linwenhui on 2017/1/3.
 */
public final class BinderUtil {

    private BinderUtil() {
    }

    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return new ColorDrawable(color);
    }

    //TODO View
    @BindingAdapter("android:backgroundLevel")
    public static void setViewLevel(View view, int level) {
        Drawable drawable = view.getBackground();
        if (drawable != null)
            drawable.setLevel(level);
    }

    //TODO TextView
    @BindingAdapter("android:backgroundLevel")
    public static void setTextViewLevel(TextView tv, int level) {
        setViewLevel(tv, level);
    }

    //TODO CheckedTextView
    @BindingAdapter("android:backgroundLevel")
    public static void setCheckedTextViewLevel(CheckedTextView tv, int level) {
        setViewLevel(tv, level);
    }

    //TODO ImageButton
    @BindingAdapter("android:backgroundLevel")
    public static void setButtonLevel(ImageButton tv, int level) {
        setViewLevel(tv, level);
    }

    //TODO ImageView
    @BindingAdapter("android:src")
    public static void setSrc(ImageView imgvw, Bitmap bg){
        imgvw.setImageBitmap(bg);
    }

    @BindingAdapter("android:selected")
    public static void setSelected(ImageView imgvw,boolean sel){
        imgvw.setSelected(sel);
    }

    //TODO Button
    @BindingAdapter("android:backgroundLevel")
    public static void setButtonLevel(Button tv, int level) {
        setViewLevel(tv, level);
    }

    //TODO RadioButton
    @BindingAdapter("android:backgroundLevel")
    public static void setRadioButtonLevel(RadioButton tv, int level) {
        setViewLevel(tv, level);
    }

    //TODO LinearLayout
    @BindingAdapter("android:backgroundLevel")
    public static void setLinearLayoutLevel(LinearLayout tv, int level) {
        setViewLevel(tv, level);
    }

    //TODO RelativeLayout
    @BindingAdapter("android:backgroundLevel")
    public static void setRelativeLayoutLevel(RelativeLayout tv, int level) {
        setViewLevel(tv, level);
    }

    //TODO RadioGroup
    @BindingAdapter("android:backgroundLevel")
    public static void setRadioGroupLevel(RadioGroup tv, int level) {
        setViewLevel(tv, level);
    }

    @BindingAdapter("android:onCheckedChange")
    public static void setOnCheckedChangeListener(RadioGroup radioGroup, RadioGroup.OnCheckedChangeListener listener) {
        radioGroup.setOnCheckedChangeListener(listener);
    }

    //TODO Toolbar
    @BindingAdapter("android:navigationOnClickListener")
    public static void navigationOnClickListener(Toolbar toolbar, View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            toolbar.setNavigationOnClickListener(onClickListener);
        }
    }

    @BindingAdapter("android:finish")
    public static void setFinish(Toolbar toolbar, boolean isFinish) {
        if (!isFinish) {
            return;
        }
        Context context = toolbar.getContext();
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
    }

    //TODO EditText
    @BindingAdapter("android:afterTextChanged")
    public static void afterTextChanged(final EditText editText, final AfterTextChanged textChange) {
        if (editText != null && textChange != null) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (textChange != null) {
                        textChange.afterTextChanged(editText, editable);
                    }
                }
            });
        }
    }

    @BindingAdapter("android:keepSelectionStart")
    public static void keepSelectionStart(final EditText edtxt, boolean keep) {
        if (keep && edtxt != null) {
            edtxt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (edtxt.getSelectionStart() == 0)
                        edtxt.setSelection(s.length());
                }
            });
        }
    }

    @BindingAdapter("android:closeInputMethod")
    public static void closeInputMethod(EditText edtxt, boolean closeInputMethod) {
        if (closeInputMethod) {
            if (android.os.Build.VERSION.SDK_INT <= 10) {
                edtxt.setInputType(InputType.TYPE_NULL);
            } else {
                if (edtxt.getContext() instanceof Activity) {
                    Method method;
                    Class<EditText> cls = EditText.class;
                    try {
                        method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                        method.setAccessible(true);
                        method.invoke(edtxt, false);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    try {
                        method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                        method.setAccessible(true);
                        method.invoke(edtxt, false);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }


                } else {
                    edtxt.setInputType(InputType.TYPE_NULL);
                }
            }
        }
    }

    //TODO TextInputEditText
    @BindingAdapter("android:afterTextChanged")
    public static void afterTextChanged(final TextInputEditText editText, final AfterTextChanged textChange) {
        if (editText != null && textChange != null) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (textChange != null) {
                        textChange.afterTextChanged(editText, editable);
                    }
                }
            });
        }
    }

    @BindingAdapter("android:keepSelectionStart")
    public static void keepSelectionStart(final TextInputEditText edtxt, boolean keep) {
        if (keep && edtxt != null) {
            edtxt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (edtxt.getSelectionStart() == 0)
                        edtxt.setSelection(s.length());
                }
            });
        }
    }

    @BindingAdapter("android:closeInputMethod")
    public static void closeInputMethod(TextInputEditText edtxt, boolean closeInputMethod) {
        if (closeInputMethod) {
            if (android.os.Build.VERSION.SDK_INT <= 10) {
                edtxt.setInputType(InputType.TYPE_NULL);
            } else {
                if (edtxt.getContext() instanceof Activity) {
                    Method method;
                    Class<EditText> cls = EditText.class;
                    try {
                        method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                        method.setAccessible(true);
                        method.invoke(edtxt, false);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    try {
                        method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                        method.setAccessible(true);
                        method.invoke(edtxt, false);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }


                } else {
                    edtxt.setInputType(InputType.TYPE_NULL);
                }
            }
        }
    }


}
