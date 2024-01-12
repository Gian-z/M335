package com.gzw.bmi.components;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class ServiceProvider<T extends Service> implements ServiceConnection {
    private T service;
    private boolean connected;

    private final Context context;
    private final Class<T> instanceClass;
    private final int flags;

    public ServiceProvider(Context context, Class<T> instanceClass, int flags) {
        this.context = context;
        this.instanceClass = instanceClass;
        this.flags = flags;
    }

    @Override
    public void onServiceConnected(ComponentName className,
                                   IBinder service) {

        TypedBinder<T> binder = (TypedBinder<T>) service;

        this.service = binder.getService();
        connected = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0) {
        connected = false;
    }

    public ServiceProvider<T> unbind(){
        context.unbindService(this);
        connected = false;
        return this;
    }

    public ServiceProvider<T> bind(){

        Intent intent = new Intent(context, instanceClass);
        context.bindService(intent, this, flags);
        return this;
    }

    public T getService() {
        return service;
    }

    public boolean isConnected() {
        return connected;
    }
}
