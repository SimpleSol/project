package biz.growapp.helpers;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.SearchView;

import java.lang.ref.WeakReference;

public class BaseSearchListener implements SearchView.OnQueryTextListener{
    public static final long DEFAULT_DELAY_MS = 750;
    private static final int SEARCH_CHANGED_CODE = 100;

    public interface SearchEventListener {
        void doSearch(String query);
    }

    private final Handler handler;
    private final WeakReference<SearchEventListener> listener;
    private final long delayMs;

    public BaseSearchListener(@NonNull  SearchEventListener listener) {
        this(listener, DEFAULT_DELAY_MS);
    }

    public BaseSearchListener(@NonNull SearchEventListener listener, long delayMs) {
        this.listener = new WeakReference<>(listener);
        this.handler = new SearchHandler(this.listener);
        this.delayMs = delayMs;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        handler.removeMessages(SEARCH_CHANGED_CODE);
        final SearchEventListener eventListener = listener.get();
        if (eventListener != null) {
            eventListener.doSearch(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        handler.removeMessages(SEARCH_CHANGED_CODE);
        handler.sendMessageDelayed(handler.obtainMessage(SEARCH_CHANGED_CODE, newText), delayMs);
        return true;
    }

    static class SearchHandler extends Handler {
        private final WeakReference<SearchEventListener> listener;

        SearchHandler(WeakReference<SearchEventListener> listener) {
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            final SearchEventListener eventListener = listener.get();
            if (eventListener != null) {
                eventListener.doSearch((String) msg.obj);
            }
        }
    }
}
