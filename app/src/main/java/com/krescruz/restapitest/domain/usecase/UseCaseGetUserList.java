package com.krescruz.restapitest.domain.usecase;

import com.krescruz.restapitest.domain.executor.PostExecutionThread;
import com.krescruz.restapitest.domain.executor.ThreadExecutor;
import com.krescruz.restapitest.domain.repository.UserRepository;

import rx.Observable;

public class UseCaseGetUserList extends UseCase {

    private final UserRepository mUserRepository;

    public UseCaseGetUserList(UserRepository mUserRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mUserRepository = mUserRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mUserRepository.getUsers();
    }
}
