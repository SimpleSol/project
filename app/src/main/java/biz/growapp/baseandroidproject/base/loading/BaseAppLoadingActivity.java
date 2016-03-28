package biz.growapp.baseandroidproject.base.loading;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import biz.growapp.baseandroidproject.helpers.ViewStateSwitcher;
import biz.growapp.baseandroidproject.base.BaseAppActivity;


public abstract class BaseAppLoadingActivity extends BaseAppActivity implements BaseLoadingView {
    protected ViewStateSwitcher stateSwitcher;
    private TextView tvErrorMessage;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initStateSwitcher();
    }

    private void initStateSwitcher() {
        stateSwitcher = ViewStateSwitcher.createStandardSwitcher(this, findViewById(getMainContainerId()));
        tvErrorMessage = ViewStateSwitcher.addStandardErrorView(stateSwitcher,
                // TODO: 20.03.2016 change this button name and default message
                "No internet", "Retry",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRetryButtonClick(v);
                    }
                });
        stateSwitcher.switchToLoading(false);
    }

    @IdRes
    public abstract int getMainContainerId();

    public abstract void onRetryButtonClick(View v);

    @Override
    public void addViewState(@ViewStateSwitcher.State String state, @NonNull View stateView) {
        stateSwitcher.addViewState(state, stateView);
    }

    @Override
    public TextView addTextState(@ViewStateSwitcher.State String state, String message) {
        return ViewStateSwitcher.addTextState(stateSwitcher, state, message);
    }

    @Override
    public void switchToState(@ViewStateSwitcher.State String state, boolean animate) {
        stateSwitcher.switchToState(state, animate);
    }

    @Override
    public void switchToMain(boolean animate) {
        stateSwitcher.switchToMain(animate);
    }

    @Override
    public void switchToLoading(boolean animate) {
        stateSwitcher.switchToLoading(animate);
    }

    @Override
    public void switchToError(boolean animate) {
        stateSwitcher.switchToError(animate);
    }

    @Override
    public void switchToError(boolean animate, String errorText) {
        tvErrorMessage.setText(errorText);
        switchToError(animate);
    }

    @Override
    public void switchToEmpty(boolean animate) {
        stateSwitcher.switchToEmpty(animate);
    }

    @Override
    public void switchToNotFound(boolean animate) {
        stateSwitcher.switchToNotFound(animate);
    }
}
