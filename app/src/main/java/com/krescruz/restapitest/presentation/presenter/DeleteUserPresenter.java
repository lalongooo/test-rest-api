package com.krescruz.restapitest.presentation.presenter;

import com.krescruz.restapitest.data.model.User;
import com.krescruz.restapitest.domain.usecase.DefaultSubscriber;
import com.krescruz.restapitest.domain.usecase.UseCaseDeleteUser;
import com.krescruz.restapitest.presentation.view.DeleteUserView;


public class DeleteUserPresenter implements Presenter {

    private final UseCaseDeleteUser mUseCaseDeleteUser;
    private final DeleteUserView mDeleteUserView;


    public DeleteUserPresenter(DeleteUserView mDeleteUserView, UseCaseDeleteUser mUseCaseDeleteUser) {
        this.mUseCaseDeleteUser = mUseCaseDeleteUser;
        this.mDeleteUserView = mDeleteUserView;
    }

    public void deleteUser(User user) {
        this.mUseCaseDeleteUser.setUser(user);
        this.mDeleteUserView.hideRetry();
        this.mDeleteUserView.showLoading();
        this.mUseCaseDeleteUser.execute(new DeleteUserSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mUseCaseDeleteUser.unsubscribe();
    }

    private final class DeleteUserSubscriber extends DefaultSubscriber<User> {
        @Override
        public void onCompleted() {
            mDeleteUserView.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            mDeleteUserView.hideLoading();
            mDeleteUserView.showError(e.getMessage());
            mDeleteUserView.showRetry();
        }

        @Override
        public void onNext(User user) {
            mDeleteUserView.deletedUser();
        }

    }

}