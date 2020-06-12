package com.comic.commonlib.ui.view.base;

import com.comic.commonlib.ui.manager.RxLifeManager.RxActivityLifeManager;

public interface IBaseActivityView extends IBaseView {
    /**
     * 获取RxActivityLifeManager
     */
    RxActivityLifeManager getRxActivityLifeManager();
}
