package biz.growapp.baseandroidproject.ui.base.loading;

public class BasePresenter<ViewT extends BaseLoadingView> {
    private ViewT view;

    public ViewT getView() {
        return view;
    }

    public void setView(ViewT view) {
        this.view = view;
    }

}
