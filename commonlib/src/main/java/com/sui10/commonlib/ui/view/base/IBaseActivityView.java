package com.sui10.commonlib.ui.view.base;

import com.sui10.commonlib.ui.manager.RxLifeManager.RxActivityLifeManager;

public interface IBaseActivityView extends IBaseView {
    /**
     * 获取RxActivityLifeManager
     */
    RxActivityLifeManager getRxActivityLifeManager();
}
