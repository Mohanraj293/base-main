package com.example.base.dagger.components;

import android.app.Application;
import android.content.Context;


import com.example.base.dagger.DataManager;
import com.example.base.dagger.DbHelper;
import com.example.base.dagger.DemoApplication;
import com.example.base.dagger.SharedPrefsHelper;
import com.example.base.dagger.annnotations.ApplicationContext;
import com.example.base.dagger.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(DemoApplication demoApplication);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    SharedPrefsHelper getPreferenceHelper();

    DbHelper getDbHelper();

}