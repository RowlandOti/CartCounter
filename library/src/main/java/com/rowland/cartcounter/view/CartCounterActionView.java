package com.rowland.cartcounter.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rowland.cartcounter.R;

/**
 * Adapted into library by Rowland on 9/15/2017.*
 * Add Cart Counter View for the respective menu icon
 * https://github.com/slightfoot/android-notification-action-view
 */
public class CartCounterActionView extends RelativeLayout {

    private static final String ACTION_SET_STEP = CartCounterActionView.class.getCanonicalName() + ".ACTION_SET_STEP";
    private static final String EXTRA_COUNT = "extraCount";

    // Styleable attributes
    private final boolean isAnimateLayout;
    private final int layoutAnimationId;
    private final int textViewBackgroundColor;
    private final int textViewTextColor;

    private ImageView mIcon;
    private TextView mText;

    private Menu mMenu;
    private MenuItem mItemData;
    private int mCount;

    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int count = intent.getIntExtra(EXTRA_COUNT, 0);
            if (intent.getAction().equals(ACTION_SET_STEP)) {
                setCountStep(count);
            }
        }
    };

    public CartCounterActionView(Context context) {
        this(context, null);
    }

    public CartCounterActionView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.actionButtonStyle);
    }

    public CartCounterActionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CartCounterLayout, defStyle, 0);
        try {
            isAnimateLayout = a.getBoolean(R.styleable.CartCounterLayout_cc_is_animate_layout, true);
            layoutAnimationId = a.getResourceId(R.styleable.CartCounterLayout_cc_layout_animation, R.anim.bounce);
            textViewBackgroundColor = a.getColor(R.styleable.CartCounterLayout_cc_tv_background_color, R.attr.colorAccent);
            textViewTextColor = a.getColor(R.styleable.CartCounterLayout_cc_tv_text_color, getResources().getColor(android.R.color.white));
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIcon = (ImageView) findViewById(R.id.counterImageView);
        mText = (TextView) findViewById(R.id.counterTextView);

        mText.setVisibility(View.GONE);
        mText.setTextColor(textViewTextColor);
        mText.setBackgroundColor(textViewBackgroundColor);
    }

    public void setItemData(Menu menu, MenuItem itemData) {
        mMenu = menu;
        mItemData = itemData;
        if (mItemData != null) {
            setId(itemData.getItemId());
            mIcon.setImageDrawable(itemData.getIcon());
            setContentDescription(itemData.getTitleCondensed());
            setVisibility(itemData.isVisible() ? View.VISIBLE : View.GONE);
            setEnabled(itemData.isEnabled());
            setClickable(true);
            setLongClickable(true);
        }
    }

    public void setCount(int count) {
        mCount = count;
        mText.setText(mCount > 99 ? "99+" : String.valueOf(mCount));
        mText.setVisibility((mCount == 0) ? View.GONE : View.VISIBLE);
        if (isAnimateLayout) {
            animateBadge();
        }
    }

    public void setCountStep(int step) {
        setCount(Math.max(0, getCount() + step));
    }

    public int getCount() {
        return mCount;
    }

    private static Intent createNotificationIntent(Context context, String action, int count) {
        return new Intent(action).setPackage(context.getPackageName()).putExtra(EXTRA_COUNT, count);
    }

    public static void setCountStep(Context context, int step) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(createNotificationIntent(context, ACTION_SET_STEP, step));
    }

    private void animateBadge() {
        Animation bounce = AnimationUtils.loadAnimation(mIcon.getContext(), layoutAnimationId);
        this.startAnimation(bounce);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SET_STEP);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mUpdateReceiver, filter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mUpdateReceiver);
    }

    @Override
    public boolean performClick() {
        boolean performed = super.performClick();
        if (mItemData != null && !performed) {
            mMenu.performIdentifierAction(mItemData.getItemId(), 0);
            performed = true;
        }
        return performed;
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public boolean performLongClick() {
        boolean performed = super.performLongClick();
        if (mItemData != null && !performed) {
            final int[] screenPos = new int[2];
            final Rect displayFrame = new Rect();
            getLocationOnScreen(screenPos);
            getWindowVisibleDisplayFrame(displayFrame);

            final Context context = getContext();
            final int width = getWidth();
            final int height = getHeight();
            final int midy = screenPos[1] + height / 2;
            final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;

            Toast cheatSheet = Toast.makeText(context, mItemData.getTitle(), Toast.LENGTH_SHORT);
            if (midy < displayFrame.height()) {
                // Show along the top; follow action buttons
                cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT, screenWidth - screenPos[0] - width / 2, height);
            } else {
                // Show along the bottom center
                cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
            }
            cheatSheet.show();
            performed = true;
        }

        return performed;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.count = getCount();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCount(ss.count);
    }

    private static class SavedState extends BaseSavedState {
        int count;

        protected SavedState(Parcelable superState) {
            super(superState);
        }

        protected SavedState(Parcel source) {
            super(source);
            count = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(count);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}

