package ua.betagroup.betagroup.Dagger;

import dagger.Module;
import dagger.Provides;
import ua.betagroup.betagroup.CheckInfo.CheckingPresenter;
import ua.betagroup.betagroup.Imvp;
import ua.betagroup.betagroup.Model.Model;

@Module
public class ModuleCheck {
    @UserScope
    @Provides
    Imvp.ICheckingPresenter provideCheck(Imvp.IModel model) {
        return new CheckingPresenter(model);
    }
    @Provides
    @UserScope
    Imvp.IModel provideModel() {
        return new Model();
    }
}
