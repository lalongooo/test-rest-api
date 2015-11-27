package com.krescruz.restapitest.presentation.view;

import com.krescruz.restapitest.data.model.User;

import java.util.List;

public interface LoadUserListView extends LoadDataView {

    void showUsers(List<User> users);

}