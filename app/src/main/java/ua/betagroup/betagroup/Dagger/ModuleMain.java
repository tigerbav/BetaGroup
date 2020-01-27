package ua.betagroup.betagroup.Dagger;

import dagger.Module;
import dagger.Provides;
import ua.betagroup.betagroup.Imvp;
import ua.betagroup.betagroup.WebView.MainPresenter;

@Module
public class ModuleMain {
    @Provides
    @UserScope
    Imvp.IMainPresenter provideMain(Imvp.IModel model) {
        return new MainPresenter(model);
    }
}
