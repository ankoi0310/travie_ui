package vn.edu.hcmuaf.fit.travie.core.common.di;

import dagger.Subcomponent;
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.infrastructure.annotation.ActivityScope;
import vn.edu.hcmuaf.fit.travie.home.ui.view.HomeFragment;
import vn.edu.hcmuaf.fit.travie.hotel.ui.view.ExploreFragment;
import vn.edu.hcmuaf.fit.travie.invoice.ui.view.HistoryFragment;
import vn.edu.hcmuaf.fit.travie.user.ui.view.ProfileFragment;

@ActivityScope
@Subcomponent
public interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        MainComponent create();
    }

    void inject(MainActivity mainActivity);
    void inject(HomeFragment homeFragment);
    void inject(ExploreFragment exploreFragment);
    void inject(HistoryFragment historyFragment);
    void inject(ProfileFragment profileFragment);
}
