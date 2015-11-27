package com.krescruz.restapitest.domain.usecase;

import com.krescruz.restapitest.data.model.User;
import com.krescruz.restapitest.domain.executor.PostExecutionThread;
import com.krescruz.restapitest.domain.executor.ThreadExecutor;
import com.krescruz.restapitest.domain.repository.UserRepository;

import rx.Observable;

public class UseCaseAddUser extends UseCase {

    private final UserRepository mUserRepository;
    private User user;

    public UseCaseAddUser(UserRepository mUserRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mUserRepository = mUserRepository;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mUserRepository.addUser(user);
    }
}