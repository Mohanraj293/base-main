package com.example.base.dagger.modules;

import android.app.Activity;
import android.content.Context;

import com.example.base.dagger.annnotations.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    public Context provideContext() {
        return mActivity;
    }

    @Provides
    public Activity provideActivity() {
        return mActivity;
    }
}