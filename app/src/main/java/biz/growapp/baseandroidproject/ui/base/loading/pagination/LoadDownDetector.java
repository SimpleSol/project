package biz.growapp.baseandroidproject.ui.base.loading.pagination;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class LoadDownDetector extends LoadDetector {

    public LoadDownDetector(int pageSize) {
        super(pageSize);
    }

    public LoadDownDetector(int itemThreshold, int pageSize) {
        super(itemThreshold, pageSize);
    }

    @Override
    public void enableProgressItem(boolean isEnable) {
        final int itemCount = adapter.getItemCount();
        if (isEnable) {
            adapter.notifyItemInserted(itemCount);
        } else {
            adapter.notifyItemRemoved(itemCount - 1);
        }
    }

    @Override
    public RecyclerView.OnScrollListener getScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy <= 0) {
                    //scroll up, all visible items already loaded
                    return;
                }
                final int loadedItemsCount = adapter.getItemCount();
                final int lastVisibleItem = findLastVisibleItemPosition(layoutManager);
                if (!getLoadingState() && loadedItemsCount <= (lastVisibleItem + getItemThreshold())) {
                    adapter.loadItems(loadedItemsCount);
                }
            }
        };
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
        return isProgressItemVisible()
                ? position >= adapter.getItemCount() - 1
                : position >= adapter.getItemCount();
    }

    @Override
    public boolean isItemsNotFitScreen(int loadedItemsCount) {
        if (loadedItemsCount < getPageSize()) {
            // loaded empty list, reached the end of data
            return false;
        }
        // TODO: 02.02.16 implement another way to check event when items filled all recyclerView height
        final int lastVisibleItemPosition = findLastVisibleItemPosition(layoutManager);
        final int totalItemsCount = adapter.getItemCount();
        return (lastVisibleItemPosition == RecyclerView.NO_POSITION ? 0 : lastVisibleItemPosition) + loadedItemsCount >= totalItemsCount;
    }

    @Override
    public int getCorrectItemPosition(int position) {
        return position;
    }
}
