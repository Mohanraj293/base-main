package com.example.base.dagger.components;

import com.example.base.MainActivity;
import com.example.base.dagger.annnotations.PerActivity;
import com.example.base.dagger.modules.ActivityModule;


import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}