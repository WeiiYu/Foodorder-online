package com.foodie.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodie.app.R;

import java.util.List;


public class ViewPagerIndicator extends HorizontalScrollView {

    private Context mContext;

    private List<String> mTitles;
    private ViewPager mViewPager;

    private LinearLayout llTabRoot;
    private RelativeLayout rlRoot;
    private View vLine;
    private RelativeLayout.LayoutParams lineLayoutParams;

    private int mTabVisibleCount;
    private int mSizeText;
    private int mColorTextNormal;
    private int mColorTextHighlight;
    private int mColorLine;
    private int mHeightLine;

    private static final int COUNT_DEFAULT_TAB = 4;
    private static final int SIZE_TEXT = 16;
    private static final int COLOR_TEXT_NORMAL = Color.parseColor("#000000");
    private static final int COLOR_TEXT_HIGHLIGHT = Color.parseColor("#FFFFFF");
    private static final int COLOR_LINE = Color.parseColor("#000000");
    private static final int HEIGHT_LINE = 2;

    public ViewPagerIndicator(Context context) {
        super(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = attributes.getInt(R.styleable.ViewPagerIndicator_tab_visible_count, COUNT_DEFAULT_TAB);
        if (mTabVisibleCount < 0) {
            mTabVisibleCount = COUNT_DEFAULT_TAB;
        }
        mSizeText = attributes.getInt(R.styleable.ViewPagerIndicator_tab_text_size, SIZE_TEXT);
        mColorTextNormal = attributes.getInt(R.styleable.ViewPagerIndicator_tab_text_normal_color, COLOR_TEXT_NORMAL);
        mColorTextHighlight = attributes.getInt(R.styleable.ViewPagerIndicator_tab_text_highlight_color, COLOR_TEXT_HIGHLIGHT);
        mColorLine = attributes.getInt(R.styleable.ViewPagerIndicator_tab_line_color, COLOR_LINE);
        mHeightLine = attributes.getInt(R.styleable.ViewPagerIndicator_tab_line_height, HEIGHT_LINE);
        attributes.recycle();

        initViews();
    }

    private void initViews() {
        setHorizontalScrollBarEnabled(false);

        rlRoot = new RelativeLayout(mContext);
        rlRoot.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        this.addView(rlRoot);
        llTabRoot = new LinearLayout(mContext);
        llTabRoot.setOrientation(LinearLayout.HORIZONTAL);
        llTabRoot.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        rlRoot.addView(llTabRoot);

        vLine = new View(mContext);
        lineLayoutParams = new RelativeLayout.LayoutParams(getScreenWidth() / mTabVisibleCount, DpToPx(mHeightLine));
        lineLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        vLine.setLayoutParams(lineLayoutParams);
        vLine.setBackgroundColor(mColorLine);
        rlRoot.addView(vLine);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = llTabRoot.getChildCount();
        if (childCount == 0) {
            return;
        }

        for (int i = 0; i < childCount; i++) {
            View view = llTabRoot.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(params);
        }

        lineLayoutParams.width = getScreenWidth() / mTabVisibleCount;
        vLine.setLayoutParams(lineLayoutParams);
    }

    public void setVisibleTabCount(int count) {
        mTabVisibleCount = count;
        onFinishInflate();
    }

    public void setTabItemTitles(List<String> titles) {
        if (titles != null && titles.size() > 0) {
            llTabRoot.removeAllViews();
            mTitles = titles;
            for (String title : mTitles) {
                llTabRoot.addView(generateTextView(title));
            }
            setItemClickEvent();
        }
    }

    private View generateTextView(String title) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getScreenWidth() / mTabVisibleCount, LayoutParams.MATCH_PARENT);

        TextView textView = new TextView(getContext());
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mSizeText);
        textView.setTextColor(mColorTextNormal);
        textView.setLayoutParams(params);
        return textView;
    }


    public void scroll(int position, float positionOffset) {
        int tabWidth = getWidth() / mTabVisibleCount;
        if (position >= (mTabVisibleCount - 2) && positionOffset > 0 && llTabRoot.getChildCount() > mTabVisibleCount) {
            if (mTabVisibleCount != 1) {
                scrollTo((int) ((position - (mTabVisibleCount - 2) + positionOffset) * tabWidth), 0);
            } else {
                scrollTo((int) ((position + positionOffset) * tabWidth), 0);
            }
        }

        lineLayoutParams.leftMargin = (int) ((position + positionOffset) * tabWidth);
        vLine.setLayoutParams(lineLayoutParams);
    }

    public void setViewPager(ViewPager viewpager, int position) {
        mViewPager = viewpager;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                highLightTextView(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(position);
        highLightTextView(position);
    }

    private void highLightTextView(int position) {
        resetTextViewColor();
        View view = llTabRoot.getChildAt(position);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(mColorTextHighlight);
        }
    }

    private void resetTextViewColor() {
        for (int i = 0; i < llTabRoot.getChildCount(); i++) {
            View view = llTabRoot.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(mColorTextNormal);
            }
        }
    }

    private void setItemClickEvent() {
        int childCount = llTabRoot.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int j = i;
            View view = llTabRoot.getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private int DpToPx(double dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
