package ua.betagroup.betagroup.Model;

import android.net.ConnectivityManager;

import com.parse.ParseObject;

import java.util.TimeZone;

import ua.betagroup.betagroup.Constants;
import ua.betagroup.betagroup.ICallBack;
import ua.betagroup.betagroup.Imvp;

public class Model implements Imvp.IModel {
    @Override
    public void checkCurrentDevice(String ip, ConnectivityManager connectivityManager, String model, ICallBack iCallBack) {
        String tz = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT), temp = "";

        ParseObject conteiner = new ParseObject("MyFirstClass");
//        conteiner.put(Constants.MODEL, model);
//        conteiner.put(Constants.PROXY, true);
//        conteiner.put(Constants.IP, ip);
//        conteiner.put(Constants.TIME_ZONE, tz);
//        conteiner.put(Constants.COUNTRY, TimeZone.getDefault().getID());
        for (int i = 0; i < tz.length(); i++) {
            if(tz.charAt(i) == '+' || tz.charAt(i) == '-') {
                temp = String.valueOf(tz.charAt(i + 1)) + tz.charAt(i + 2);
            }
        }
        int time = Integer.parseInt(temp);
//        conteiner.saveInBackground();
        if(iCallBack != null)
            if(time >= 2 && time < 13)
                iCallBack.openWebView();
            else
                iCallBack.openCap();
    }
}
