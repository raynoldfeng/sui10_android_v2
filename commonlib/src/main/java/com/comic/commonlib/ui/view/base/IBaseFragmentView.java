package com.comic.commonlib.ui.view.base;

import com.comic.commonlib.ui.manager.RxLifeManager.RxFragmentLifeManager;

public interface IBaseFragmentView extends IBaseView{
    /**
     * 获取RxFragmentManager
     */
    RxFragmentLifeManager getRxFragmentLifeManager();
}
