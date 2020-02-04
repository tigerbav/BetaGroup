package ua.betagroup.betagroup;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;


public interface Imvp {
    interface IMainView {
    }
    interface IMainPresenter {
        void attachView(IMainView mainView);
        void detachView();
        String setUrl();
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
        void checkDevice(String ip, ConnectivityManager connectivityManager, String model, String locale);
        void setSharedPreferences(SharedPreferences sharedPreferences);
    }

    interface IModel{
        void checkCurrentDevice(String ip, ConnectivityManager connectivityManager, String model, String locale, ICallBack iCallBack);
        void setSharedPreferences(SharedPreferences sharedPreferences);
        String returnUrl();
    }
}
