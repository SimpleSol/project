package biz.growapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import biz.growapp.R;
import biz.growapp.helpers.ClickHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseAppActivity extends AppCompatActivity {
    private static final String TAG = BaseAppActivity.class.getSimpleName();
    protected final ClickHelper clickHelper = new ClickHelper();

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    private boolean isCommitAllowed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCommitAllowed = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        isCommitAllowed = false;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResumeFragments() {
        isCommitAllowed = true;
        super.onResumeFragments();
    }

    public boolean isCommitAllowed() {
        return isCommitAllowed;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (hasToolbar()) {
            initToolbar();
        }
    }

    protected boolean hasToolbar() {
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    protected void initToolbar() {
        if (toolbar == null) {
            return;
        }
        setSupportActionBar(toolbar);
        final ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayUseLogoEnabled(false);
    }

    @Nullable
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private final List<OnBackPressedListener> backPressedListeners = new ArrayList<>();

    public final void addBackPressListener(OnBackPressedListener listener) {
        backPressedListeners.add(listener);
    }

    public final void removeOnBackPressListener(OnBackPressedListener listener) {
        backPressedListeners.remove(listener);
    }

    @Override
    public void onBackPressed() {
        if (backPressedListeners.isEmpty()) {
            super.onBackPressed();
        } else {
            for (OnBackPressedListener listener : backPressedListeners) {
                if (!listener.onBackPressed()) {
                    super.onBackPressed();
                }
            }
        }
    }
}
