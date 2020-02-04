package ua.betagroup.betagroup.CheckInfo;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import java.util.Locale;

import javax.inject.Inject;

import ua.betagroup.betagroup.ICallBack;
import ua.betagroup.betagroup.Imvp;

public class CheckingPresenter implements Imvp.ICheckingPresenter, ICallBack {
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
    public void checkDevice(String ip, ConnectivityManager connectivityManager, String model, String locale) {
        this.model.checkCurrentDevice(ip, connectivityManager, model, locale,  this);
    }

    @Override
    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        model.setSharedPreferences(sharedPreferences);
    }

    @Override
    public void openCap() {
        checkingView.openCap();
    }

    @Override
    public void openWebView() {
        checkingView.openWebView();
    }
}
