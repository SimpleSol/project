package biz.growapp.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<ModelT, ViewHolderT extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolderT> {
    protected final LayoutInflater inflater;
    protected final List<ModelT> items;
    protected final WeakReference<Context> context;

    public BaseAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = new WeakReference<>(context);
        this.items = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Nullable
    public ModelT getItem(int position) {
        // TODO: 15.03.2016 or throw runtime exception?
        return position < 0 || position >= items.size() ? null : items.get(position);
    }

    public void add(@NonNull final ModelT item) {
        add(item, items.size());
    }

    public void add(@NonNull final ModelT item, int startPosition) {
        if (startPosition > items.size()) {
            startPosition = items.size();
        }
        final int initialSize = this.items.size();
        items.add(startPosition, item);
        // FIXME: 30.11.2015 [WA] https://code.google.com/p/android/issues/detail?id=77846 https://code.google.com/p/android/issues/detail?id=77232
        //http://stackoverflow.com/questions/30220771/recyclerview-inconsistency-detected-invalid-item-position
        //http://stackoverflow.com/questions/26827222/how-to-change-contents-of-recyclerview-while-scrolling
        if (initialSize == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemInserted(startPosition);
        }
    }

    @Nullable
    public ModelT replace(@NonNull final ModelT item, int position) {
        if (position > items.size()) {
            position = items.size();
        }
        final ModelT oldItem = items.remove(position);
        items.add(position, item);
        notifyItemChanged(position);
        return oldItem;
    }

    public void addAll(final List<ModelT> items) {
        addAll(items, this.items.size());
    }

    public void addAll(final List<ModelT> items, int startPosition) {
        if (items == null || items.size() == 0) {
            return;
        }
        final int initialSize = this.items.size();
        this.items.addAll(startPosition, items);
        // FIXME: 30.11.2015 [WA] https://code.google.com/p/android/issues/detail?id=77846 https://code.google.com/p/android/issues/detail?id=77232
        //http://stackoverflow.com/questions/30220771/recyclerview-inconsistency-detected-invalid-item-position
        //http://stackoverflow.com/questions/26827222/how-to-change-contents-of-recyclerview-while-scrolling
        if (initialSize == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(startPosition, items.size());
        }
    }

    public void clear() {
        final int size = items.size();
        items.clear();
        notifyItemRangeRemoved(0, size);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @NonNull
    public List<ModelT> getItems() {
        return items;
    }

    @Nullable
    public ModelT remove(int position) {
        if (position < 0 || position >= items.size()) {
            // TODO: 15.03.2016 or throw runtime exception?
            return null;
        }
        final ModelT removed = items.remove(position);
        // FIXME: 30.11.2015 [WA] https://code.google.com/p/android/issues/detail?id=77846 https://code.google.com/p/android/issues/detail?id=77232
        //http://stackoverflow.com/questions/30220771/recyclerview-inconsistency-detected-invalid-item-position
        //http://stackoverflow.com/questions/26827222/how-to-change-contents-of-recyclerview-while-scrolling
        if (items.isEmpty()) {
            notifyDataSetChanged();
        } else {
            notifyItemRemoved(position);
        }
        return removed;
    }

}
