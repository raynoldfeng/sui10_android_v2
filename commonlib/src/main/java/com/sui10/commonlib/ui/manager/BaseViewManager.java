package com.sui10.commonlib.ui.manager;

import android.app.Activity;

import com.sui10.commonlib.utils.CommonUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

 /**
 * Activity管理类；保存所有activity的实例
 */

public class BaseViewManager {
    private static final String TAG = "AppBaseViewManager";
    private static volatile BaseViewManager mInstance;
    private LinkedHashMap<String, Activity> mActivities;

    private BaseViewManager() {
        mActivities = new LinkedHashMap<>();
    }

    public static BaseViewManager getInstance() {
        if (null == mInstance) {
            synchronized (BaseViewManager.class) {
                if (null == mInstance) {
                    mInstance = new BaseViewManager();
                }
            }
        }
        return mInstance;
    }

    private  <K, V> Map.Entry<K, V> getHead(LinkedHashMap<K, V> map) {
        return map.entrySet().iterator().next();
    }

    private  <K, V> Map.Entry<K, V> getTail(LinkedHashMap<K, V> map) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        Map.Entry<K, V> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
        }
        return tail;
    }
    public Activity getHeadElement(){
        return getHead(mActivities).getValue();
    }

    public Activity getTailElement() {
        Map.Entry<String,Activity> entry = getTail(mActivities);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    public LinkedHashMap<String,Activity> getActivities() {
        return mActivities;
    }

    public int size() {
        return mActivities.size();
    }


    public synchronized void addActivity(String activityName,Activity activity) {
        mActivities.put(activityName,activity);
    }

    public synchronized void removeActivity(String activityName) {
        if (mActivities.get(activityName)!=null) {
            mActivities.remove(activityName);
        }
    }

    public synchronized void clear() {
        Set<String> keySet = mActivities.keySet();
        if (keySet != null) {
            for (Iterator<String> itr = keySet.iterator(); itr.hasNext(); ) {
                String key = itr.next();
                if (!CommonUtils.isEmpty(key)) {
                    Activity activity = mActivities.get(key);
                    activity.finish();
                    itr.remove();
                }
            }
        }
    }

    public synchronized void clearResource(){
        if (mActivities!=null && mActivities.size()<=0){
            mActivities.clear();
            mActivities = null;
            mInstance = null;
        }
    }
}
