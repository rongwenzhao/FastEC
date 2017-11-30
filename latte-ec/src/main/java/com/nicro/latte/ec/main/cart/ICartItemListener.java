package com.nicro.latte.ec.main.cart;

/**
 * adapter中，返回选中物品的总价格
 * Created by rongwenzhao on 2017/11/30.
 */

public interface ICartItemListener {
    void onItemClick(double itemTotalPrice);
}
