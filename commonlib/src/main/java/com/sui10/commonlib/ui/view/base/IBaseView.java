package com.sui10.commonlib.ui.view.base;

public interface IBaseView {
    /**
     * 显示正在加载
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 隐藏正在加载
     */
    void hideLoading();

    /**
     * 隐藏无数据
     */
    void hideEmpty();

    /**
     * 隐藏异常信息
     */
    void hideException();

    /**
     * 隐藏无网络
     */
    void hideNetWorkError();

    /**
     * 显示出错信息
     */
    void showError(String msg);

    /**
     * 显示异常信息
     */
    void showException(String msg);

    /**
     * 显示网络错误
     */
    void showNetError();

    /**
     * 显示无数据
     */
    void showNoData(String msg);
}
