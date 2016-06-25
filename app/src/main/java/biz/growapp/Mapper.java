package biz.growapp;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Mapper<F, T> {
    private final Function<F, T> convertFunction;

    protected Mapper(Function<F, T> function) {
        this.convertFunction = function;
    }

    @NonNull
    public static <F, T> Mapper<F, T> of(@NonNull Function<F, T> function) {
        return new Mapper<>(function);
    }

    @NonNull
    public List<T> from(@NonNull List<F> listFrom) {
        final ArrayList<T> listTo = new ArrayList<>(listFrom.size());
        for (F from : listFrom) {
            listTo.add(from(from));
        }
        return listTo;
    }

    @NonNull
    public T from(@NonNull F from) {
        return convertFunction.apply(from);
    }

    interface Function<F, T> {
        @NonNull
        T apply(@NonNull F from);
    }

}
