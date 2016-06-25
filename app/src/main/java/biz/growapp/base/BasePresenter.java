package biz.growapp.base;

import java.util.HashSet;
import java.util.Set;

import biz.growapp.base.loading.BaseLoadingView;
import retrofit2.Call;

public class BasePresenter<ViewT extends BaseLoadingView> {
    private final Set<Call> requestQueue = new HashSet<>();
    private ViewT view;

    public ViewT getView() {
        return view;
    }

    public void setView(ViewT view) {
        this.view = view;
    }

    public void onViewCreated() {

    }

    public void onDestroyView() {
        view = null;
        clearRequestQueue();
    }

    protected void addRequest(Call call) {
        requestQueue.add(call);
    }

    protected void removeRequest(Call call) {
        requestQueue.remove(call);
    }

    protected void clearRequestQueue() {
        if (requestQueue.isEmpty()) {
            return;
        }
        for (Call call : requestQueue) {
            call.cancel();
        }
        requestQueue.clear();
    }
}
