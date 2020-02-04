package ua.betagroup.betagroup.WebView;

import javax.inject.Inject;

import ua.betagroup.betagroup.Imvp;

public class MainPresenter implements Imvp.IMainPresenter {
    private Imvp.IModel model;
    private Imvp.IMainView mainView;

    @Inject
    public MainPresenter(Imvp.IModel model) {
        this.model = model;
    }

    @Override
    public void attachView(Imvp.IMainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void detachView() {
        mainView = null;
    }

    @Override
    public String setUrl() {
        return model.returnUrl();
    }
}
