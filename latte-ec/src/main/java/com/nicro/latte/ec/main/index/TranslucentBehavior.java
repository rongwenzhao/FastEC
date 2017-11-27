package com.nicro.latte.ec.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.nicro.latte.ec.R;
import com.nicro.latte.ui.recycler.RgbValue;

/**
 * 自定义的CoordinatorLayout.Behavior。
 * 实现RecyclerView滑动时，上面的toolbar跟着改变。
 * 可参考 http://www.jianshu.com/p/5ffb37226e72/
 * Created by rongwenzhao on 2017/11/27.
 */

@SuppressWarnings("unused")
public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    //顶部距离
    private int mDistanceY = 0;
    //颜色变化速度。可根据需求更改
    private static final int SHOW_SPEED = 3;
    //定义变化的颜色
    private final RgbValue RGB_VALUE = RgbValue.create(255, 124, 2);//一种橙色，可按需调整。

    /**
     * 此构造方法必须要有，CoordinatorLayout通过反射调用的就是这个方法
     *
     * @param context
     * @param attrs
     */
    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 确定CoordinatorLayout的子视图与同级视图的依赖
     * 此处定义Toolbar依赖的是RecyclerView
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.rv_index;
    }

    /**
     * 返回true，我们接管它的事件
     * 监听RecyclerView的滑动事件
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    /**
     * 处理我们具体逻辑的方法：
     * 根据RecyclerView的滑动值，来动态调整toolbar的背景颜色
     *
     * @param coordinatorLayout
     * @param child
     * @param target            此处是android.support.v4.widget.SwipeRefreshLayout
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        //增加滑动距离
        mDistanceY += dy;
        //toolbar的高度
        final int targetHeight = child.getBottom();

        //当滑动时，并且距离小于 toolBar高度的时候，调整渐变色
        if (mDistanceY > 0 && mDistanceY <= targetHeight) {
            final float scale = (float) mDistanceY / targetHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int) alpha, RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        } else if (mDistanceY > targetHeight) {
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        }
    }
}
