package com.sui10.suishi.common.ui.entity;

import android.os.Bundle;

public class FragClassifyEntity {
    protected int id;
    protected Class fragClass;
    protected Bundle bundle = new Bundle();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class getFragClass() {
        return fragClass;
    }

    public void setFragClass(Class fragClass) {
        this.fragClass = fragClass;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
