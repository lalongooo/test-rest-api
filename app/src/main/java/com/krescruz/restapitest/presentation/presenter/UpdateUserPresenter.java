package com.krescruz.restapitest.presentation.presenter;

import com.krescruz.restapitest.data.model.User;
import com.krescruz.restapitest.domain.usecase.DefaultSubscriber;
import com.krescruz.restapitest.domain.usecase.UseCaseUpdateUser;
import com.krescruz.restapitest.presentation.view.UpdateUserView;


public class UpdateUserPresenter implements Presenter {

    private final UseCaseUpdateUser mCaseUpdateUser;
    private final UpdateUserView mUpdateUserView;


    public UpdateUserPresenter(UpdateUserView mUpdateUserView, UseCaseUpdateUser mCaseUpdateUser) {
        this.mCaseUpdateUser = mCaseUpdateUser;
        this.mUpdateUserView = mUpdateUserView;
    }

    public void registerUser(User user) {
        this.mCaseUpdateUser.setUser(user);
        this.mUpdateUserView.hideRetry();
        this.mUpdateUserView.showLoading();
        this.mCaseUpdateUser.execute(new UpdateUserSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mCaseUpdateUser.unsubscribe();
    }

    private final class UpdateUserSubscriber extends DefaultSubscriber<User> {
        @Override
        public void onCompleted() {
            mUpdateUserView.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            mUpdateUserView.hideLoading();
            mUpdateUserView.showError(e.getMessage());
            mUpdateUserView.showRetry();
        }

        @Override
        public void onNext(User user) {
            mUpdateUserView.updatedUser(user);
        }

    }

}