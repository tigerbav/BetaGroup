package ua.betagroup.betagroup.Cap;

import javax.inject.Inject;

import ua.betagroup.betagroup.Imvp;

public class CapPresenter implements Imvp.ICapPresenter {
    private Imvp.IModel model;
    private Imvp.ICapView capView;

    @Inject
    public CapPresenter(Imvp.IModel model) {
        this.model = model;
    }

    @Override
    public void attachView(Imvp.ICapView capView) {
        this.capView = capView;
    }

    @Override
    public void detachView() {
        capView = null;
    }

    @Override
    public void startTimer() {
        model.startGame();
    }
}
