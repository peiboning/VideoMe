package com.pbn.org.news.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.pbn.org.news.skin.widget.SkinTextView;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/12/03
 */
public class KeyBoardTextView extends SkinTextView implements ValueAnimator.AnimatorUpdateListener {

    private ValueAnimator animator;
    private CharSequence mText;
    private BufferType mType;

    public KeyBoardTextView(Context context) {
        super(context);
    }

    public KeyBoardTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyBoardTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if(TextUtils.isEmpty(text) || type == null){
            super.setText(text, type);
            return;
        }
        checkAnimal();
        animator = ObjectAnimator.ofInt(new int[]{0, text.length()});
        animator.addUpdateListener(this);
        animator.setDuration(1000);
        mText = text;
        mType = type;
        super.setText("", type);
    }


    private void checkAnimal() {
        if(null != animator){
            animator.cancel();
            animator.removeUpdateListener(this);
        }
    }

    public void start(){
        if(!TextUtils.isEmpty(mText)){
            checkAnimal();
            if(null != animator){
                animator.addUpdateListener(this);
                animator.start();
            }
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        Log.e("KeyBoardTextView" , "onAnimationUpdate: " + value);
        if(value > 0 && value < mText.length()){
            SpannableString spannableString = new SpannableString(mText.toString());
            spannableString.setSpan(new ForegroundColorSpan(0), value , mText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            super.setText(spannableString, mType);
        }
    }
}
