package ua.betagroup.betagroup.Dagger;

import dagger.Component;
import ua.betagroup.betagroup.Cap.Cap;
import ua.betagroup.betagroup.WebView.MainActivity;

@UserScope
@Component(modules = {ModuleCap.class, ModuleMain.class})
public interface IComponent {
    void inject(MainActivity mainActivity);
    void inject(Cap cap);
}
