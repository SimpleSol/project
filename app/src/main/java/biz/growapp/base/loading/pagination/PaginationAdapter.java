package biz.growapp.base.loading.pagination;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.List;

import biz.growapp.base.BaseAdapter;


public abstract class PaginationAdapter<ModelT, ViewHolderT extends RecyclerView.ViewHolder> extends BaseAdapter<ModelT, ViewHolderT> {

    public static final int TYPE_PROGRESS = -1;
    public static final int TYPE_ITEM = 0;

    private boolean isLastPage;

    public interface Loader {
        void onLoadMore(int offset);
    }

    private final WeakReference<Loader> loader;
    @NonNull
    private final LoadDetector loadDetector;

    public PaginationAdapter(Context context, Loader loader, int pageSize) {
        this(context, loader, new LoadDownDetector(pageSize));
    }

    public PaginationAdapter(Context context, Loader loader, @NonNull LoadDetector detector) {
        super(context);
        this.loader = new WeakReference<>(loader);
        this.loadDetector = detector;
        setHasStableIds(true);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        loadDetector.onAttachedToRecyclerView(recyclerView);
    }

    void loadItems(int itemsCount) {
        loadDetector.setLoadingState(true);
        final Loader loader = this.loader.get();
        if (loader != null) {
            loader.onLoadMore(itemsCount);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        loadDetector.onDetachedFromRecyclerView(recyclerView);
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ViewHolderT onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PROGRESS) {
            return (ViewHolderT) loadDetector.createProgressViewHolder(inflater, parent);
        } else {
            return _onCreateViewHolder(parent, viewType);
        }
    }

    protected abstract ViewHolderT _onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(ViewHolderT holder, final int position) {
        if (holder.getItemViewType() != TYPE_PROGRESS) {
            _onBindViewHolder(holder, loadDetector.getCorrectItemPosition(position));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolderT holder, int position, List<Object> payloads) {
        if (holder.getItemViewType() != TYPE_PROGRESS) {
            if (payloads.isEmpty()) {
                _onBindViewHolder(holder, loadDetector.getCorrectItemPosition(position));
            } else {
                _onBindViewHolder(holder, loadDetector.getCorrectItemPosition(position), payloads);
            }
        }
    }

    /**
     * Called only if payloads not empty
     * @param holder
     * @param position
     * @param payloads
     */
    protected void _onBindViewHolder(ViewHolderT holder, final int position, final List<Object> payloads) {
        _onBindViewHolder(holder, position);
    }

    protected abstract void _onBindViewHolder(ViewHolderT holder, final int position);

    @Override
    public int getItemCount() {
        return loadDetector.isProgressItemVisible()
                ? items.size() + 1
                : items.size();
    }

    @Override
    public long getItemId(int position) {
        if (loadDetector.isProgressItemPosition(position)) {
            return RecyclerView.NO_ID;
        }
        return _getItemId(loadDetector.getCorrectItemPosition(position));
    }

    protected abstract long _getItemId(int position);

    @Override
    public int getItemViewType(int position) {
        if (position == RecyclerView.NO_POSITION) {
            Log.d("PaginationAdapter", "position == -1");
            return TYPE_PROGRESS;
        }
        return loadDetector.isProgressItemPosition(position)
                ? TYPE_PROGRESS
                : _getItemViewType(loadDetector.getCorrectItemPosition(position));
    }

    protected abstract int _getItemViewType(int position);

    public boolean isLastPage() {
        return isLastPage;
    }

    public void stopLoading(List<ModelT> loadedItems) {
        stopLoading(loadedItems, getItemCount());
        isLastPage = loadedItems.isEmpty();
    }

    @Override
    public void clear() {
        super.clear();
        isLastPage = false;
    }

    public void stopLoading(List<ModelT> loadedItems, int startPosition) {
        if (loadDetector.isProgressItemVisible()) { // TODO: 09.03.16 we really need this check?
            startPosition--;
            if (startPosition < 0) {
                startPosition = 0;
            }
        }
        loadDetector.setLoadingState(false);
        addAll(loadedItems, startPosition);
        if (loadDetector.isItemsNotFitScreen(loadedItems.size())) {
            loadItems(getItemCount());
        }
    }

}
