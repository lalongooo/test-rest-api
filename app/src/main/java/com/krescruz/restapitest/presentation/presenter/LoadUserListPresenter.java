package com.krescruz.restapitest.presentation.presenter;

import com.krescruz.restapitest.data.model.User;
import com.krescruz.restapitest.domain.usecase.DefaultSubscriber;
import com.krescruz.restapitest.domain.usecase.UseCaseGetUserList;
import com.krescruz.restapitest.presentation.view.LoadUserListView;

import java.util.List;

public class LoadUserListPresenter implements Presenter {

    private final UseCaseGetUserList mUseCaseGetUserList;
    private final LoadUserListView mLoadUserListView;

    public LoadUserListPresenter(LoadUserListView mLoadUserListView, UseCaseGetUserList mUseCaseGetUserList) {
        this.mLoadUserListView = mLoadUserListView;
        this.mUseCaseGetUserList = mUseCaseGetUserList;
    }

    public void getUsers() {
        this.mLoadUserListView.hideRetry();
        this.mLoadUserListView.showLoading();
        this.mUseCaseGetUserList.execute(new LoadUserListSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mUseCaseGetUserList.unsubscribe();
    }

    private final class LoadUserListSubscriber extends DefaultSubscriber<List<User>> {
        @Override
        public void onCompleted() {
            mLoadUserListView.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            mLoadUserListView.hideLoading();
            mLoadUserListView.showError(e.getMessage());
            mLoadUserListView.showRetry();
        }

        @Override
        public void onNext(List<User> users) {
            mLoadUserListView.showUsers(users);
        }

    }
}
