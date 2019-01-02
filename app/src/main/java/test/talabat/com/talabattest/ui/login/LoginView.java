package test.talabat.com.talabattest.ui.login;

import test.talabat.com.talabattest.data.db.model.User;
import test.talabat.com.talabattest.interfaces.BaseView;

public interface LoginView extends BaseView {
    void onSuccess(User user);
}
