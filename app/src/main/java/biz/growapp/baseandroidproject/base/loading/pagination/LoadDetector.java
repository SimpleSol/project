package biz.growapp.baseandroidproject.base.loading.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import biz.growapp.baseandroidproject.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class LoadDetector {
    public static final int DEFAULT_ITEM_THRESHOLD = 5;

    protected RecyclerView.LayoutManager layoutManager;
    protected PaginationAdapter adapter;

    // True if we are still waiting for the last set of data to load.
    private volatile boolean isLoading;
    // The minimum amount of items to have below your current scroll position before isLoading more.
    private final int itemThreshold;
    private final int pageSize;

    private RecyclerView.OnScrollListener scrollListener;
    private volatile boolean isProgressItemVisible;

    protected LoadDetector(int pageSize) {
        this(DEFAULT_ITEM_THRESHOLD, pageSize);
    }

    protected LoadDetector(int itemThreshold, int pageSize) {
        this.itemThreshold = itemThreshold;
        this.pageSize = pageSize;
    }

    /**
     * Need to call from {@link PaginationAdapter#onAttachedToRecyclerView(android.support.v7.widget.RecyclerView)}
     *
     * @param recyclerView recyclerView
     */
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.layoutManager = recyclerView.getLayoutManager();
        // TODO: 09.03.16 maybe create LoadManager - and pass into Loader and LoadDetector
        final RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (!(adapter instanceof PaginationAdapter)) {
            throw new RuntimeException("Adapter must extend PaginationAdapter");
        }
        this.adapter = (PaginationAdapter) adapter;
        scrollListener = getScrollListener();
        recyclerView.addOnScrollListener(scrollListener);
    }

    /**
     * Need to cal from {@link PaginationAdapter#onDetachedFromRecyclerView(android.support.v7.widget.RecyclerView)}
     */
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(scrollListener);
        this.scrollListener = null;
        this.layoutManager = null;
        this.adapter = null;
    }

    public abstract void enableProgressItem(boolean isEnable);

    public abstract RecyclerView.OnScrollListener getScrollListener();

    public final void setLoadingState(boolean isLoading) {
        this.isLoading = isLoading;
        if (isProgressItemVisible != isLoading) {
            enableProgressItem(isLoading);
            this.isProgressItemVisible = isLoading;
        }
    }

    /**
     * Get current loading state
     *
     * @return <tt>true</tt>, if we are still waiting for the last set of data to load, <tt>false</tt> - otherwise
     */
    public final boolean getLoadingState() {
        return isLoading;
    }

    public final boolean isProgressItemVisible() {
        return isProgressItemVisible;
    }

    public final int getPageSize() {
        return pageSize;
    }

    public int getItemThreshold() {
        return itemThreshold;
    }

    public RecyclerView.ViewHolder createProgressViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ProgressViewHolder(inflater.inflate(R.layout.loading_footer, parent, false));
    }

    /**
     * Check position for equals progress item position
     *
     * @param position item position
     * @return <tt>true</tt>, if progressItem position equals passed position and <tt>false</tt> - otherwise
     */
    public abstract boolean isProgressItemPosition(int position);

    /**
     * Check if items fit screen
     *
     * @param loadedItemsCount loaded items count
     * @return <tt>true</tt>, if items don't fit screen and need downloading, <tt>false</tt> - otherwise
     */
    public abstract boolean isItemsNotFitScreen(int loadedItemsCount);

    public abstract int getCorrectItemPosition(int position);

    /**
     * Default progress view holder
     */
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.progressBar)
        ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
