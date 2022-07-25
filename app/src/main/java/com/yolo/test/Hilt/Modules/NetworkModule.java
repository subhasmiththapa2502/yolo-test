package com.yolo.test.Hilt.Modules;

import com.yolo.test.common.Constants;
import com.yolo.test.data.remote.ApiClient;
import com.yolo.test.data.repository.AppRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    Retrofit providesRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    @Provides
    @Singleton
    ApiClient providesApi(Retrofit retrofit) {
        return retrofit.create(ApiClient.class);
    }


    @Provides
    @Singleton
    AppRepository appRepository(ApiClient apiClient) {
        return new AppRepository(apiClient);
    }


}
