package ua.betagroup.betagroup.CheckInfo;

import javax.inject.Inject;

import ua.betagroup.betagroup.Imvp;

public class CheckingPresenter implements Imvp.ICheckingPresenter {
    private Imvp.IModel model;
    private Imvp.ICheckingView checkingView;

    @Inject
    public CheckingPresenter(Imvp.IModel model) {
        this.model = model;
    }

    @Override
    public void attachView(Imvp.ICheckingView checkingView) {
        this.checkingView = checkingView;
    }

    @Override
    public void detachView() {
        checkingView = null;
    }

    @Override
    public void checkDevice() {

    }
}
