package com.sui10.commonlib.ui.view.base;

import com.sui10.commonlib.ui.manager.RxLifeManager.RxFragmentLifeManager;

public interface IBaseFragmentView extends IBaseView{
    /**
     * 获取RxFragmentManager
     */
    RxFragmentLifeManager getRxFragmentLifeManager();
}
