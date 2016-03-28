package biz.growapp.baseandroidproject.network;

import android.content.Context;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import biz.growapp.baseandroidproject.BuildConfig;
import biz.growapp.baseandroidproject.network.cookie.PersistentCookieStore;
import biz.growapp.baseandroidproject.network.response.base.ServerError;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {
    private static Retrofit retrofit;
    private static Converter<ResponseBody, ServerError> errorConverter;

    public static void init(Context context) {
        final OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

        builder.connectTimeout(NetworkConst.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NetworkConst.READ_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(new JavaNetCookieJar(
                                new CookieManager(
                                        new PersistentCookieStore(context.getApplicationContext()),
                                        CookiePolicy.ACCEPT_ALL)
                        )
                );
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        //pass there custom headers for every requests
                        .build();
                return chain.proceed(request);
            }
        });

        if (BuildConfig.DEBUG) {
            //add http logging only for debug-builds
            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        final OkHttpClient okHttpClient = builder.build();

        //noinspection ConstantConditions
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.IS_DEV_SERVER ? NetworkConst.DEV_URL : NetworkConst.PROD_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        errorConverter = retrofit.responseBodyConverter(ServerError.class, new Annotation[0]);
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static ServerError parseError(retrofit2.Response<?> response) {
        try {
            return errorConverter.convert(response.errorBody());
        } catch (IOException e) {
            return new ServerError(NetworkConst.ErrorCodes.UNKNOWN_ERROR, NetworkConst.ErrorCodes.UNKNOWN_ERROR);
        }
    }
}
