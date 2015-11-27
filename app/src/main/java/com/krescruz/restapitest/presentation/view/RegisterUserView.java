package com.krescruz.restapitest.presentation.view;

import com.krescruz.restapitest.data.model.User;

/**
 * Created by lalongooo on 27/11/15.
 */
public interface RegisterUserView extends LoadDataView {

    void userRegistered(User user);

}