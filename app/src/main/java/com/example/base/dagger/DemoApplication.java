package com.example.base.dagger;

import android.app.Application;
import android.content.Context;


import com.example.base.dagger.components.ApplicationComponent;
import com.example.base.dagger.components.DaggerApplicationComponent;
import com.example.base.dagger.modules.ApplicationModule;

import javax.inject.Inject;

public class DemoApplication extends Application {

    protected ApplicationComponent applicationComponent;

    @Inject
   DataManager dataManager;

    public static DemoApplication get(Context context) {
        return (DemoApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}