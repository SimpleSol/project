package biz.growapp.baseandroidproject.ui.base.loading.pagination;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class LoadUpDetector extends LoadDetector {
    private static final int HEADER_POSITION = 0;

    public LoadUpDetector(int pageSize) {
        super(pageSize);
    }

    public LoadUpDetector(int itemThreshold, int pageSize) {
        super(itemThreshold, pageSize);
    }

    @Override
    public void enableProgressItem(boolean isEnable) {
        if (isEnable) {
            adapter.notifyItemInserted(HEADER_POSITION);
        } else {
            adapter.notifyItemRemoved(HEADER_POSITION);
        }
    }

    @Override
    public RecyclerView.OnScrollListener getScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy >= 0) {
                    //scroll down, all visible items already loaded
                    return;
                }
                final int loadedItemsCount = adapter.getItemCount();
                final int firstVisibleItem = findFirstVisibleItemPosition(layoutManager);
                if (!getLoadingState() && firstVisibleItem <= getItemThreshold()) {
//                if (!getLoadingState() && loadedItemsCount <= (firstVisibleItem + getItemThreshold())) {
                    adapter.loadItems(0);
                }
            }
        };
    }

    private int findFirstVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else {
            int[] pos = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
            // TODO: 09.03.16 refactor this to find min
            return pos[0];
        }
    }

    // TODO: 11.09.15 refactor this
    public int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else {
            int[] pos = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            // TODO: 09.03.16 refactor this to find max
            return pos[pos.length - 1];
        }
    }

    @Override
    public boolean isProgressItemPosition(int position) {
        return getLoadingState() && position == HEADER_POSITION;
    }

    @Override
    public boolean isItemsNotFitScreen(int loadedItemsCount) {
        return false;
        /*
        if (loadedItemsCount < getPageSize()) {
            // loaded empty list, reached the end of data
            return false;
        }
        // TODO: 02.02.16 implement another way to check event when items filled all recyclerView height
        final int lastVisibleItemPosition = findLastVisibleItemPosition(layoutManager);
        final int totalItemsCount = adapter.getItemCount();
        return (lastVisibleItemPosition == RecyclerView.NO_POSITION ? 0 : lastVisibleItemPosition) + loadedItemsCount >= totalItemsCount;
        */
    }

    @Override
    public int getCorrectItemPosition(int position) {
        return isProgressItemVisible() ? position - 1 : position;
    }
}
