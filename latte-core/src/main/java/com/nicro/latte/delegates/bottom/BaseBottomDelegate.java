package com.nicro.latte.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.nicro.latte.R;
import com.nicro.latte.R2;
import com.nicro.latte.delegates.LatteDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 底层若干按钮的容器 delegate需要实现的类
 * Created by rongwenzhao on 2017/11/25.
 */

public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {

    //存储tab bean
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    //存储delegate bean
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    //存储映射的变量
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    //当前显示的delegate的index
    private int mCurrentDelegate = 0;
    //点击一个tab项，需要第一个显示的delegate index
    private int mIndexDelegate = 0;
    //tab点击后需要变成的颜色
    private int mClickedColor = Color.RED;

    //底部存放tab的LinearLayout容器，根据权重分配tab宽度
    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar = null;

    //强制子类去实现，去赋值
    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder);

    public abstract int setIndexDelegate();

    @ColorInt//说明返回的是color类型的int值
    public abstract int setClickedColor();

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在实现类中设置要显示的delegate的index
        mIndexDelegate = setIndexDelegate();
        if (setClickedColor() != 0) {
            //实现类中设置tab点击后的颜色
            mClickedColor = setClickedColor();
        }
        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);

        //初始化键-值 TAB_BEANS ITEM_DELEGATES项
        for (Map.Entry<BottomTabBean, BottomItemDelegate> entry : ITEMS.entrySet()) {
            final BottomTabBean key = entry.getKey();
            final BottomItemDelegate value = entry.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }
    }

    /**
     * onCreateView中调用的
     * 将初始化的TAB_BEANS和ITEM_DELEGATES展示到UI上
     *
     * @param savedInstanceState
     * @param rootView
     */
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        //1、初始化底边栏
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            //设置每个item的点击事件
            item.setTag(i);//设置tag，作为唯一标志，方便它的使用。onClick方法中有使用。
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean = TAB_BEANS.get(i);
            //初始化数据
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());

            if (i == mIndexDelegate) {
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }

        }

        //2、初始化上面显示的Fragment
        final SupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new SupportFragment[size]);
        //fragmentation框架加载多个根fragment的方法
        loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexDelegate, delegateArray);
    }

    /**
     * 将底边拦元素颜色全部重置。此处重置的颜色，也可以效仿mClickedColor的赋值方法，在子类中设置值
     */
    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        //上文设置进去的tag
        final int tag = (int) v.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        itemIcon.setTextColor(mClickedColor);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);

        //fragmentation提供的显示，隐藏fragment的方法。前面是需要显示的，后面是需要隐藏的。
        showHideFragment(ITEM_DELEGATES.get(tag), ITEM_DELEGATES.get(mCurrentDelegate));
        //当前显示的fragment的index变量mCurrentDelegate更新
        mCurrentDelegate = tag;
    }
}
