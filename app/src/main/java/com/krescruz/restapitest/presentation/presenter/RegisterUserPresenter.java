package com.krescruz.restapitest.presentation.presenter;

import com.krescruz.restapitest.data.model.User;
import com.krescruz.restapitest.domain.usecase.DefaultSubscriber;
import com.krescruz.restapitest.domain.usecase.UseCaseAddUser;
import com.krescruz.restapitest.presentation.view.RegisterUserView;


public class RegisterUserPresenter implements Presenter {

    private final UseCaseAddUser mUseCaseAddUser;
    private final RegisterUserView mRegisterUserView;


    public RegisterUserPresenter(RegisterUserView mRegisterUserView, UseCaseAddUser mUseCaseAddUser) {
        this.mUseCaseAddUser = mUseCaseAddUser;
        this.mRegisterUserView = mRegisterUserView;
    }

    public void registerUser(User user) {
        this.mUseCaseAddUser.setUser(user);
        this.mRegisterUserView.hideRetry();
        this.mRegisterUserView.showLoading();
        this.mUseCaseAddUser.execute(new RegisterUserSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mUseCaseAddUser.unsubscribe();
    }

    private final class RegisterUserSubscriber extends DefaultSubscriber<User> {
        @Override
        public void onCompleted() {
            mRegisterUserView.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            mRegisterUserView.hideLoading();
            mRegisterUserView.showError(e.getMessage());
            mRegisterUserView.showRetry();
        }

        @Override
        public void onNext(User user) {
            mRegisterUserView.userRegistered(user);
        }

    }

}