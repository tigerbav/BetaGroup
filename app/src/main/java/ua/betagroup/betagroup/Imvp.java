package ua.betagroup.betagroup;

public interface Imvp {
    interface IMainView {

    }
    interface IMainPresenter {
        void attachView(IMainView mainView);
        void detachView();
    }

    interface ICapView {

    }
    interface ICapPresenter {
        void attachView(ICapView capView);
        void detachView();
        void startTimer();
    }

    interface IModel{
        void startGame();
    }
}
