package com.gzw.bmi.components;

import android.app.Service;
import android.os.Binder;

public abstract class TypedBinder<T extends Service>  extends Binder {
    public abstract T getService();
}
