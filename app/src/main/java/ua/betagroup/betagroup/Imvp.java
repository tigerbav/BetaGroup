package ua.betagroup.betagroup;

import android.net.ConnectivityManager;

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
        void openCap();
        void openWebView();
    }
    interface ICheckingPresenter {
        void attachView(ICheckingView capView);
        void detachView();
        void checkDevice(String ip, ConnectivityManager connectivityManager, String model);
    }

    interface IModel{
        void checkCurrentDevice(String ip, ConnectivityManager connectivityManager, String model, ICallBack iCallBack);
    }
}
