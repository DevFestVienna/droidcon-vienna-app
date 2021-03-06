package at.devfest.app.ui.drawer;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

public interface DrawerMvp {

    interface View {
        boolean isNavigationDrawerOpen();

        void closeNavigationDrawer();

        void updateToolbarTitle(@StringRes int resId);

        void showFragment(Fragment fragment);

        void selectDrawerMenuItem(@IdRes int id);

        void hideTabLayout();

        void showDrawerMenuItem(@IdRes int id, boolean show);

        Context getContext();

        DrawerMvp.Presenter getPresenter();
    }

    interface Presenter {
        void onNavigationItemSelected(@IdRes int itemId);

        void onShow();
    }
}
