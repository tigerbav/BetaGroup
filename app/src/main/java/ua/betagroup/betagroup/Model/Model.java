package ua.betagroup.betagroup.Model;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;



import ua.betagroup.betagroup.Constants;
import ua.betagroup.betagroup.ICallBack;
import ua.betagroup.betagroup.Imvp;

public class Model implements Imvp.IModel {
    private SharedPreferences sharedPreferences;
    private static String url = "";

    @Override
    public void checkCurrentDevice(final String ip, final ConnectivityManager connectivityManager, final String model, final String locale, final ICallBack iCallBack) {
        String savedURL = sharedPreferences.getString(Constants.URL, "");
        if (!savedURL.equals("")) {
            url = savedURL;
            add(ip, connectivityManager, model, locale, iCallBack);
        } else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("MyFirstClass");
            query.whereEqualTo("objectId", "JallwC8eNs");
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, com.parse.ParseException e) {
                    if (e == null) {
                        url = object.getString(Constants.URL);

                        if(url != null && url.equals("")){
                            iCallBack.openCap();
                            return;
                        }
                        else{
                            SharedPreferences.Editor myEditor = sharedPreferences.edit();
                            myEditor.putString(Constants.URL, url);
                            myEditor.commit();
                        }

                    }
                    add(ip, connectivityManager, model, locale, iCallBack);

                }
            });
        }
    }

    private void add(final String ip, ConnectivityManager connectivityManager, final String model, final String locale, final ICallBack iCallBack){
//        final String tz = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);

//        ParseObject conteiner = new ParseObject("MyFirstClass");
//        conteiner.put(Constants.MODEL, model);
//        conteiner.put(Constants.PROXY, false);
//        conteiner.put(Constants.IP, ip);
//        conteiner.put(Constants.TIME_ZONE, tz);
//        conteiner.put(Constants.COUNTRY, locale /*TimeZone.getDefault().getID()*/);
//        conteiner.put(Constants.URL, url);
//        for (int i = 0; i < tz.length(); i++) {
//            if(tz.charAt(i) == '+' || tz.charAt(i) == '-') {
//                temp = String.valueOf(tz.charAt(i + 1)) + tz.charAt(i + 2);
//            }
//        }
//        int time = Integer.parseInt(temp);
//        conteiner.saveInBackground();
        if(iCallBack != null)
            if(locale.equals("ua") ||
                    locale.equals("ch") ||
                    locale.equals("ru") ||
                    locale.equals("kz") ||
                    locale.equals("pl") ||
                    locale.equals("at") ||
                    locale.equals("be") ||
                    locale.equals("nl") ||
                    locale.equals("fr") ||
                    locale.equals("pt") ||
                    locale.equals("hu") ||
                    locale.equals("no") ||
                    locale.equals("fi") ||
                    locale.equals("it") ||
                    locale.equals("cz") ||
                    locale.equals("si") ||
                    locale.equals("sk") ||
                    locale.equals("lt") ||
                    locale.equals("lv") ||
                    locale.equals("de") ||
                    locale.equals("ro") ||
                    locale.equals("hr") ||
                    locale.equals("dk"))
                iCallBack.openWebView();
            else
                iCallBack.openCap();
    }


    @Override
    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public String returnUrl() {
        return url;
    }


}
