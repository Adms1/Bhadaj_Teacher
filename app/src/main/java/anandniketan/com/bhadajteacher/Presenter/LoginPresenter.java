package anandniketan.com.bhadajteacher.Presenter;


import java.util.HashMap;

import anandniketan.com.bhadajteacher.View.LoginView;

/**
 * Created by Megha on 9/15/2017.
 */

public class LoginPresenter {
    LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }


    public boolean checkValidation(String username, String password) {
        // here is your validation
        if (username.length() > 2) {
            loginView.onUserInValid();
            return false;
        }

        if (password.length() > 3) {
            loginView.onPasswordInvalid();
            return false;
        }

        return true;
    }

    public String getApiResponse(HashMap<String, String> i) {
        // here wil be api calling code
        if (i.get("username").equalsIgnoreCase("admin")) {
            loginView.setOnSucess();
        } else {
            loginView.setOnFailure();
        }

        return "";
    }

}
