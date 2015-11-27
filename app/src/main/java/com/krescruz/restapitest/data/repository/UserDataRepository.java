package com.krescruz.restapitest.data.repository;

import com.krescruz.restapitest.data.datasource.herokuapi.APIRestClient;
import com.krescruz.restapitest.data.model.User;
import com.krescruz.restapitest.domain.repository.UserRepository;

import java.util.List;

import retrofit.client.Response;
import rx.Observable;
import rx.functions.Func1;

public class UserDataRepository implements UserRepository {

    /**
     * TODO: By default this app retrieves all data from the cloud,
     * it'd be awesome if data could be cached to improve the user experience
     */
    APIRestClient mAPIRestClient = new APIRestClient();


    public UserDataRepository() {
    }

    @Override
    public Observable<List<User>> getUsers() {
        return mAPIRestClient.get().getUsers();
    }

    @Override
    public Observable<User> addUser(User user) {
        return mAPIRestClient.get().addUser(user);
    }

    @Override
    public Observable<User> getUser(int id) {
        return mAPIRestClient.get().getUser(id);
    }

    @Override
    public Observable<User> updateUser(int userId, User user) {
        return mAPIRestClient.get().updateUser(userId, user);
    }

    @Override
    public Observable<String> deleteUser(int id) {
        return mAPIRestClient.get().deleteUser(id).flatMap(new Func1<Response, Observable<String>>() {
            @Override
            public Observable<String> call(Response mResponse) {
                return Observable.just(String.valueOf(mResponse.getStatus()));
            }
        });
    }
}
