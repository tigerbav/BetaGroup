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
    }

    interface ICheckingView {

    }
    interface ICheckingPresenter {
        void attachView(ICheckingView capView);
        void detachView();
        void checkDevice();
    }

    interface IModel{
        void checkDevice();
    }
}
