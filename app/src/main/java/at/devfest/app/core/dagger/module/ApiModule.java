package at.devfest.app.core.dagger.module;

import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import at.devfest.app.BuildConfig;
import at.devfest.app.data.network.DevFestService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public final class ApiModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client, Moshi moshi) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    DevFestService provideDevFestService(Retrofit retrofit) {
        return retrofit.create(DevFestService.class);
    }
}