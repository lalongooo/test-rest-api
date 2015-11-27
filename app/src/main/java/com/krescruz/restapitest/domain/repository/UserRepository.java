package com.krescruz.restapitest.domain.repository;

import com.krescruz.restapitest.data.model.User;

import java.util.List;

import rx.Observable;

public interface UserRepository {

    Observable<List<User>> getUsers();

    Observable<User> addUser(User user);

    Observable<User> getUser(int id);

    Observable<User> updateUser(int userId, User user);

    Observable<String> deleteUser(int id);

}