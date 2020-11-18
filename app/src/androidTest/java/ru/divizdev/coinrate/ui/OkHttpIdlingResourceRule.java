package ru.divizdev.coinrate.ui;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

class OkHttpIdlingResourceRule implements TestRule {

    OkHttpClient  instance = new OkHttpClient.Builder()
            .readTimeout(1,TimeUnit.SECONDS)
          .connectTimeout(1, TimeUnit.SECONDS)
          .build();

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                IdlingResource idlingResource = OkHttp3IdlingResource.create(
                        "okhttp", instance);
                Espresso.registerIdlingResources(idlingResource);

                base.evaluate();

                Espresso.unregisterIdlingResources(idlingResource);
            }
        };
    }
}
