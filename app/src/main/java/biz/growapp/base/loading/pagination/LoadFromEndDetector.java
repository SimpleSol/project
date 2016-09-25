package biz.growapp.base.loading.pagination;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

final class LoadFromEndDetector extends LoadDetector {

    LoadFromEndDetector(int itemThreshold) {
        super(itemThreshold);
    }

    @Override
    public void enableProgressItem(boolean isEnable) {
        if (isEnable) {
            adapter.add(progressItem);
        } else {
            adapter.remove(adapter.getItemCount() - 1);
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
                if (!isLoading() && loadedItemsCount <= (lastVisibleItem + getItemThreshold())) {
                    adapter.loadItems(loadedItemsCount);
                }
            }
        };
    }

    // TODO: 11.09.15 refactor this
    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else {
            int[] pos = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            // TODO: 09.03.16 refactor this to find max
            return pos[pos.length - 1];
        }
    }

}
