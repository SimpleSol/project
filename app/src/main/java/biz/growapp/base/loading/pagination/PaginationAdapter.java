package biz.growapp.base.loading.pagination;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import biz.growapp.R;
import biz.growapp.base.AdapterDelegate;
import biz.growapp.base.DelegationAdapter;

public class PaginationAdapter<T> extends DelegationAdapter<T> {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({Direction.TO_END, Direction.TO_START})
    public @interface Direction {
        String TO_START = "start";
        String TO_END = "end";
    }

    public interface Loader {
        void onLoadMore(int offset);
    }

    private final Loader loader;
    private final LoadDetector loadDetector;
    private ProgressBarAdapter<T> progressBarAdapter;
    private boolean isLastPage;

    public PaginationAdapter(Loader loader) {
        this(loader, Direction.TO_END);
    }

    public PaginationAdapter(Loader loader, @NonNull @Direction String direction) {
        this(loader, direction, LoadDetector.DEFAULT_ITEM_THRESHOLD);
    }

    public PaginationAdapter(Loader loader, @NonNull @Direction String direction, int itemThreshold) {
        super();
        this.loader = loader;
        if (direction.equals(Direction.TO_END)) {
            this.loadDetector = new LoadFromEndDetector(itemThreshold);
        } else {
            this.loadDetector = new LoadFromStartDetector(itemThreshold);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        progressBarAdapter = new ProgressBarAdapter<>(recyclerView.getContext());
        getManager().addDelegate(progressBarAdapter);
        loadDetector.onAttachedToRecyclerView(recyclerView, this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        loadDetector.onDetachedFromRecyclerView(recyclerView);
        getManager().removeDelegate(progressBarAdapter);
        progressBarAdapter = null;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    void loadItems(int itemsCount) {
        if (loader != null && !isLastPage) {
            loadDetector.setLoadingState(true);
            loader.onLoadMore(itemsCount);
        }
    }

    @Override
    public void addAll(List<T> items, int startPosition) {
        final boolean fromLoader = loadDetector.isLoading() || getItemCount() == 0;
        if (fromLoader) {
            isLastPage = items.isEmpty();
            if (getItemCount() != 0) {
                loadDetector.setLoadingState(false);
                // TODO: DO 22.09.2016 fix this and loadUP
                startPosition--;
                if (startPosition < 0) {
                    startPosition = 0;
                }
            }
        }
        super.addAll(items, startPosition);
        if (fromLoader && !isLastPage && loadDetector.isItemsNotFitScreen(items.size())) {
            loadItems(getItemCount());
        }
    }

    @Override
    public void clear() {
        super.clear();
        isLastPage = false;
    }

    static class ProgressBarAdapter<T> implements AdapterDelegate<T> {
        private final LayoutInflater inflater;

        static class Progress {
        }

        ProgressBarAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public boolean isForViewType(@NonNull List<T> items, int position) {
            return items.get(position) instanceof Progress;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
            return new ProgressViewHolder(inflater.inflate(R.layout.loading_footer, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull List<T> items, int position) {
        }

        static class ProgressViewHolder extends RecyclerView.ViewHolder {
            ProgressBar progressBar;

            ProgressViewHolder(View itemView) {
                super(itemView);
                progressBar = (ProgressBar) itemView;
            }
        }
    }
}
