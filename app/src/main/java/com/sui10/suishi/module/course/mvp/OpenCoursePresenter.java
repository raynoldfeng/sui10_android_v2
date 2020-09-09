package com.sui10.suishi.module.course.mvp;

import com.sui10.commonlib.base.constants.NetConstant;
import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.utils.ToastUtils;
import com.sui10.suishi.common.net.models.CourseModels;
import com.sui10.suishi.module.course.bean.Rsp.GetCourseTagsRsp;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OpenCoursePresenter extends BasePresenter<IOpenCourseView> implements IOpenCoursePresenter{

    private static final String TAG ="OpenCoursePresenter";

    @Override
    public void reqCourseTags() {
        addDisposable(CourseModels.getOpenCourseTags() .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetCourseTagsRsp>() {
                    @Override
                    public void accept(GetCourseTagsRsp getCourseTagsRsp) throws Exception {
                        if(getCourseTagsRsp.getCode() == NetConstant.RSP_CODE.OK){
                            if(getView() != null){
                                getView().onCourseTagsReqSuccess(getCourseTagsRsp.getCourseTagsList());
                            }
                        }else {
                            ToastUtils.showShort(getCourseTagsRsp.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogManager.e(TAG,"getOpenCourseTags exception : "+throwable.getMessage());
                    }
                }));
    }
}
