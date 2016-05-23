package com.williamchow.customedittext;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by William Chow on 4/30/2016.
 */
public class CleanableAmountEditText extends EditText {
    private Drawable mRightDrawable, mLeftDrawable;
    private boolean isHasFocus;

    public CleanableAmountEditText(Context context) {
        super(context);
        init();
    }

    public CleanableAmountEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CleanableAmountEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //getCompoundDrawables:
        //Returns drawables for the left, top, right, and bottom borders.
        Drawable[] rightDrawables = this.getCompoundDrawables();
        Drawable[] leftDrawables = this.getCompoundDrawables();

        //Obtain right Drawable
        //We arranged in a layout file in android:drawableRight
        mRightDrawable = rightDrawables[2];
        mLeftDrawable = leftDrawables[0];

        //Set the focus change monitoring
        this.setOnFocusChangeListener(new FocusChangeListenerImpl());
        //Set the EditText text change monitoring
        this.addTextChangedListener(new TextWatcherImpl());
        //Initialization of clean icon is not visible to the right
        setClearRightDrawableVisible(false);
        setClearLeftDrawableVisible(false);
    }


    /**
     * When the finger is lifted position in the clean icon in the region
     * We see this as the removal operation
     * getWidth():Get the width of the control
     * event.getX():Coordinate when lifting (change the coordinates are relative to the control itself.)
     * getTotalPaddingRight():The clean icon to the right edge of the left edge of the control distance
     * getPaddingRight():The clean icon to the right edge of the right edge of the control distance
     * Therefore:
     * getWidth() - getTotalPaddingRight()Express:
     * The left edge of the control to the clean icon on the left of the area
     * getWidth() - getPaddingRight()Express:
     * The icon on the left to the right edge of the control region of the clean
     * So between the two regions is just the clean icon in the region
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight())) &&
                        (event.getX() < (getWidth() - getPaddingRight()));
                if (isClean) {
                    setText("");

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
                    this.clearFocus();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private class FocusChangeListenerImpl implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isHasFocus = hasFocus;
            if (isHasFocus) {
                boolean isVisible = getText().toString().length() >= 1;
                setClearRightDrawableVisible(isVisible);
                setClearLeftDrawableVisible(isVisible);
            } else {
                setClearRightDrawableVisible(false);

                /**
                 * No Clear when No Focus
                 */
//                setClearLeftDrawableVisible(false);
            }
        }
    }

    //When judging whether the right display icon in the clean input end
    private class TextWatcherImpl implements TextWatcher {

        @Override
        public void afterTextChanged(Editable s) {
            boolean isVisible = getText().toString().length() >= 1;
            setClearRightDrawableVisible(isVisible);
            setClearLeftDrawableVisible(isVisible);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            Log.i("William", "Result :: " + getText().toString());
        }

    }

    //Show or hide the clean Icon
    protected void setClearRightDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible) {
            rightDrawable = mRightDrawable;
        } else {
            rightDrawable = null;
        }
        //Use the code sets the control left, top, right, and bottom Icon
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], rightDrawable, getCompoundDrawables()[3]);
    }

    protected void setClearLeftDrawableVisible(boolean isVisible){
        Drawable leftDrawable;
        if (isVisible) {
            leftDrawable = mLeftDrawable;
        } else {
            leftDrawable = null;
        }
        //Use the code sets the control left, top, right, and bottom Icon
        setCompoundDrawables(leftDrawable, getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
    }

    // Display an animated, to prompt the user to enter
//    public void setShakeAnimation() {
//        this.setAnimation(shakeAnimation(5));
//    }
//
//    //The number of duplicate CycleTimes animation
//    public Animation shakeAnimation(int CycleTimes) {
//        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
//        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
//        translateAnimation.setDuration(2000);
//        return translateAnimation;
//    }
}
