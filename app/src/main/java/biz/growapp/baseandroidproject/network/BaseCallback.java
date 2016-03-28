package biz.growapp.baseandroidproject.network;


import android.support.annotation.Nullable;

import biz.growapp.baseandroidproject.network.response.base.ServerError;
import biz.growapp.baseandroidproject.network.response.base.ServerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<ServerResponse<T>> {

    @Override
    public void onResponse(Call<ServerResponse<T>> call, Response<ServerResponse<T>> response) {
        beforeResult();
        final ServerResponse<T> body = response.body();
        if (response.isSuccess() && body != null) {
            onSuccess(body.getData());
        } else {
            onError(RequestManager.parseError(response));
        }
        afterResult();
    }

    @Override
    public void onFailure(Call<ServerResponse<T>> call, Throwable t) {
        if (call.isCanceled()) {
            return;
        }
        beforeResult();
        onError(new ServerError(NetworkConst.ErrorCodes.UNKNOWN_ERROR, t.getMessage()));
        afterResult();
    }

    public abstract void onSuccess(@Nullable T response);

    public abstract void onError(ServerError error);

    public void beforeResult() {}

    public void afterResult() {}

}
