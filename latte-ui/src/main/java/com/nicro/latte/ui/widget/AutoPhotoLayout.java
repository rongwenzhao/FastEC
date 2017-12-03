package com.nicro.latte.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.joanzapata.iconify.widget.IconTextView;
import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rongwenzhao on 2017/12/2.
 */

public class AutoPhotoLayout extends LinearLayoutCompat {

    //现在是第几个
    private int mCurrentNum = 0;
    //最大能存图片个数
    private int mMaxNum = 0;
    //每行最大图片数
    private int mMaxLineNum = 0;
    //加号
    private IconTextView mIconAdd = null;
    //公共的一些属性
    private LayoutParams mParams = null;
    //要删除的图片ID
    private int mDeleteId = 0;
    //我们选中的图片
    private AppCompatImageView mTargetImageView = null;
    //图片之间的距离变量
    private int mImageMargin = 0;
    //传入的delegate，方便我们操作图片
    private LatteDelegate mDelegate = null;
    //一行的图片存储变量
    private List<View> mLineViews = null;
    //点击需要刪除的图片弹出的对话框
    private AlertDialog mTargetDialog = null;
    //加号字体图标
    private static final String ICON_TEXT = "{fa-plus}";
    //icon的大小
    private float mIconSize = 0;
    //全部的view。里面存储每行的view的集合
    private static final List<List<View>> ALL_VIEWS = new ArrayList<>();
    //存储每行的高度
    private static final List<Integer> LINE_HEIGHTS = new ArrayList<>();

    //此处用Boolean值作为判断，保证onMeasure中需要大开销的代码部分(比如new对象)只执行一次。
    private boolean mIsOnceInitOnMeasure = false;
    //防止多次初始化onLayout中变量
    private boolean mHasInitOnLayout = false;


    public final void setDelegate(LatteDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public AutoPhotoLayout(Context context) {
        this(context, null);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义的属性的值
        final TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.camera_flow_layout);
        mMaxNum = typedArray.getInt(R.styleable.camera_flow_layout_max_couunt, 1);
        mMaxLineNum = typedArray.getInt(R.styleable.camera_flow_layout_line_count, 3);
        mImageMargin = typedArray.getInt(R.styleable.camera_flow_layout_item_margin, 0);
        mIconSize = typedArray.getDimension(R.styleable.camera_flow_layout_icon_size, 20);
        typedArray.recycle();
    }

    /**
     * 添加新图片
     *
     * @param uri
     */
    public final void onCropTarget(Uri uri) {
        createNewImageView();
        Glide.with(mDelegate)
                .load(uri)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mTargetImageView);
    }

    private void createNewImageView() {
        mTargetImageView = new AppCompatImageView(getContext());
        mTargetImageView.setId(mCurrentNum);
        mTargetImageView.setLayoutParams(mParams);
        mTargetImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取要删除的图片ID
                mDeleteId = v.getId();
                mTargetDialog.show();
                final Window window = mTargetDialog.getWindow();
                if (window != null) {
                    window.setContentView(R.layout.dialog_auto_photo_panel);
                    window.setGravity(Gravity.BOTTOM);
                    window.setWindowAnimations(com.nicro.latte.R.style.anim_panel_up_from_bottom);
                    //设置背景透明
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    //设置属性
                    final WindowManager.LayoutParams params = window.getAttributes();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    params.dimAmount = 0.5f;//dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的 ，1.0f时候，背景全部变黑暗
                    window.setAttributes(params);

                    window.findViewById(R.id.photodialog_btn_delete).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //得到要删除的图片
                            final AppCompatImageView deleteImageView =
                                    findViewById(mDeleteId);
                            //设置图片删除的动画
                            final AlphaAnimation animation = new AlphaAnimation(1, 0);
                            animation.setDuration(500);
                            animation.setRepeatCount(0);
                            animation.setFillAfter(true);
                            animation.setStartOffset(0);//开始的等待时间，设为0就是不等待，立即执行
                            deleteImageView.setAnimation(animation);
                            animation.start();
                            AutoPhotoLayout.this.removeView(deleteImageView);
                            mCurrentNum -= 1;
                            //当数目达到上限时隐藏添加按钮，不足时显示
                            if (mCurrentNum < mMaxNum) {
                                mIconAdd.setVisibility(VISIBLE);
                            }
                            mTargetDialog.cancel();
                        }
                    });
                    window.findViewById(R.id.photodialog_btn_uncertain).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTargetDialog.cancel();
                        }
                    });
                    window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTargetDialog.cancel();
                        }
                    });
                }
            }
        });


        //添加子view的时候传入位置
        this.addView(mTargetImageView, mCurrentNum);
        mCurrentNum++;
        //当添加数目大于mMaxNum时，自动隐藏添加按钮
        if (mCurrentNum >= mMaxNum) {
            mIconAdd.setVisibility(GONE);
        }
    }


    /**
     * 直接重写自己的测量方法，不需要super.onMeasure()
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        final int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //该控件在被使用时，宽高设置的是wrap_content，所以设置的初始值为0.
        int width = 0;
        int height = 0;
        //记录每一行单位宽度与高度
        int lineWidth = 0;
        int lineHeight = 0;
        //得到内部元素个数
        int cCount = getChildCount();
        //循环我们的子元素
        for (int i = 0; i < cCount; i++) {
            final View child = getChildAt(i);
            //测量子view的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到LayoutParams
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //子view占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子view占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //一行占满之后，怎样换到下一行
            //换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //对比得到最大宽度
                width = Math.max(width, lineWidth);
                //换行之后，重置lineWidth
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                //没换行
                //叠加行宽
                lineWidth += childWidth;
                //得到最大的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //得到最后一个子view
            //设置该控件的宽高
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        //根据该自定义控件的layout_width与layout_height的设置值对应的mode，设置我们测绘得到的值
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );
        //设置一行所有图片的宽高
        final int imageSizeLen = sizeWidth / mMaxLineNum;
        //只初始化一次
        if (!mIsOnceInitOnMeasure) {
            mParams = new LayoutParams(imageSizeLen, imageSizeLen);
            mIsOnceInitOnMeasure = true;
        }
    }

    /**
     * 重写layout方法，不需要使用super.layout()调用父类的方法
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //布局之前，将之前的所有信息全部清除
        ALL_VIEWS.clear();
        LINE_HEIGHTS.clear();
        //获取当前viewGroup的宽度
        final int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        if (!mHasInitOnLayout) {
            mLineViews = new ArrayList<>();
            mHasInitOnLayout = true;
        }
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final View child = getChildAt(i);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            final int childWidth = child.getMeasuredWidth();//自己本身，不加margin所占的宽度
            final int childHeight = child.getMeasuredHeight();//自己本身，不加margin所占的高度
            //如果需要换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin >
                    width - getPaddingLeft() - getPaddingRight()) {
                //记录lineHeight
                LINE_HEIGHTS.add(lineHeight);
                //记录当前一行的views
                ALL_VIEWS.add(mLineViews);
                //重置宽和高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                //重置view集合
                //mLineViews.clear();
                mLineViews = new ArrayList<>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            mLineViews.add(child);
        }

        //处理最后一行?????
        LINE_HEIGHTS.add(lineHeight);
        ALL_VIEWS.add(mLineViews);

        //设置子view位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        //行数
        final int lineNum = ALL_VIEWS.size();
        for (int i = 0; i < lineNum; i++) {
            //当前行所有的view
            mLineViews = ALL_VIEWS.get(i);
            lineHeight = LINE_HEIGHTS.get(i);
            final int size = mLineViews.size();
            for (int j = 0; j < size; j++) {
                final View child = mLineViews.get(j);
                //判断child的状态
                if (child.getVisibility() == GONE) {
                    continue;
                }
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                //设置子view的边距
                final int lc = left + lp.leftMargin;
                final int tc = top + lp.topMargin;
                final int rc = lc + child.getMeasuredWidth() - mImageMargin;
                final int bc = tc + child.getMeasuredHeight();
                //为子view进行布局
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
        mIconAdd.setLayoutParams(mParams);
        mHasInitOnLayout = false;//此处需要变为false
    }

    /**
     * 初始化添加按钮
     */

    private void initAddIcon() {
        mIconAdd = new IconTextView(getContext());
        mIconAdd.setText(ICON_TEXT);
        mIconAdd.setGravity(Gravity.CENTER);
        mIconAdd.setTextSize(mIconSize);
        mIconAdd.setBackgroundResource(R.drawable.boder_text);
        mIconAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.startCameraWithCheck();
            }
        });
        this.addView(mIconAdd);

    }

    /**
     * 必须在onFinishInflate()中调用初始化AddIcon的方法，不然添加的按钮会无效的
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initAddIcon();
        mTargetDialog = new AlertDialog.Builder(getContext()).create();
    }
}
