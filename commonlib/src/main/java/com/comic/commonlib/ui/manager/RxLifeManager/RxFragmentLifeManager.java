package com.comic.commonlib.ui.manager.RxLifeManager;

import android.os.Bundle;

import androidx.annotation.CheckResult;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.subjects.BehaviorSubject;

public class RxFragmentLifeManager implements LifecycleProvider<FragmentEvent> {

    private static volatile RxFragmentLifeManager mInstance;
    private BehaviorSubject<FragmentEvent> lifecycleSubject;


    private RxFragmentLifeManager(){
        lifecycleSubject  = BehaviorSubject.create();
    }

    public static RxFragmentLifeManager getInstance(){

        mInstance = new RxFragmentLifeManager();
        return mInstance;
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }


    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
    return RxLifecycleAndroid.bindFragment(lifecycleSubject);
     }


    public void onAttach(){
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    public void onCreateView(){
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    public void onStart() {
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    public void onResume() {
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
    }

    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
    }

    public void onDestroyView(){
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
    }

    public void onDestroy() {
      lifecycleSubject.onNext(FragmentEvent.DESTROY);
    }

    public void onDetach(){
        lifecycleSubject.onNext(FragmentEvent.DETACH);
    }

}

