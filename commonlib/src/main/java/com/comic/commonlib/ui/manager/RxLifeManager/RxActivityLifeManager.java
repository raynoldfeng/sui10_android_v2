package com.comic.commonlib.ui.manager.RxLifeManager;

import android.os.Bundle;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

 /**
 * 该类主要用来绑定RxJava与Activity的生命周期
 * 主要作用:防止内存泄漏
 */
public class RxActivityLifeManager implements LifecycleProvider<ActivityEvent> {

    private static volatile RxActivityLifeManager mInstance;
    private BehaviorSubject<ActivityEvent> lifecycleSubject;


    private RxActivityLifeManager(){
        lifecycleSubject  = BehaviorSubject.create();
    }

    public static RxActivityLifeManager getInstance(){
        mInstance = new RxActivityLifeManager();
        return mInstance;
    }
    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        lifecycleSubject.onNext(ActivityEvent.CREATE);
    }

    public void onStart() {
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    public void onResume() {
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    public void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    public void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
    }

    public void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
    }

}

