package ua.betagroup.betagroup.Dagger;

import dagger.Module;
import dagger.Provides;
import ua.betagroup.betagroup.Cap.CapPresenter;
import ua.betagroup.betagroup.Imvp;

@Module
public class ModuleCap {
    @Provides
    @UserScope
    Imvp.ICapPresenter provideCap(Imvp.IModel model) {
        return new CapPresenter(model);
    }

}
