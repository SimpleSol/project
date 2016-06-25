package biz.growapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import biz.growapp.helpers.ClickHelper;
import butterknife.ButterKnife;

public abstract class BaseAppFragment extends Fragment implements OnBackPressedListener{
    protected final ClickHelper clickHelper = new ClickHelper();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }
}
