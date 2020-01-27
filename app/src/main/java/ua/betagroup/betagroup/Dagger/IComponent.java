package ua.betagroup.betagroup.Dagger;

import dagger.Component;
import ua.betagroup.betagroup.Cap.Cap;
import ua.betagroup.betagroup.CheckInfo.Checking;
import ua.betagroup.betagroup.WebView.MainActivity;

@UserScope
@Component(modules = {ModuleCap.class, ModuleMain.class, ModuleCheck.class})
public interface IComponent {
    void inject(MainActivity mainActivity);
    void inject(Cap cap);
    void inject(Checking checking);
}
