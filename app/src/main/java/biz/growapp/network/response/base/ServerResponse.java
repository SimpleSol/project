package biz.growapp.network.response.base;

import android.support.annotation.Nullable;

public class ServerResponse<T> {
    private T data;

    @Nullable
    public T getData() {
        return data;
    }

}
