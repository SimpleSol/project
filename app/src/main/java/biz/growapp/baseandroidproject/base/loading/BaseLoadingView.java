package biz.growapp.baseandroidproject.base.loading;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import biz.growapp.baseandroidproject.helpers.ViewStateSwitcher;


public interface BaseLoadingView {

    @IdRes
    int getMainContainerId();

    void onRetryButtonClick(View view);

    void addViewState(@ViewStateSwitcher.State String state, @NonNull View stateView);

    TextView addTextState(@ViewStateSwitcher.State String state, String message);

    void switchToState(@ViewStateSwitcher.State String state, boolean animate);

    void switchToMain(boolean animate);

    void switchToLoading(boolean animate);

    void switchToError(boolean animate);

    void switchToError(boolean animate, String errorText);

    void switchToEmpty(boolean animate);

    void switchToNotFound(boolean animate);
}
