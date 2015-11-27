package com.krescruz.restapitest.presentation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.krescruz.restapitest.R;
import com.krescruz.restapitest.data.model.User;
import com.krescruz.restapitest.data.repository.UserDataRepository;
import com.krescruz.restapitest.domain.executor.JobExecutor;
import com.krescruz.restapitest.domain.executor.UIThread;
import com.krescruz.restapitest.domain.usecase.UseCaseAddUser;
import com.krescruz.restapitest.domain.usecase.UseCaseDeleteUser;
import com.krescruz.restapitest.domain.usecase.UseCaseUpdateUser;
import com.krescruz.restapitest.presentation.presenter.DeleteUserPresenter;
import com.krescruz.restapitest.presentation.presenter.RegisterUserPresenter;
import com.krescruz.restapitest.presentation.presenter.UpdateUserPresenter;
import com.krescruz.restapitest.presentation.view.DeleteUserView;
import com.krescruz.restapitest.presentation.view.RegisterUserView;
import com.krescruz.restapitest.presentation.view.UpdateUserView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityUserForm extends AppCompatActivity implements RegisterUserView, UpdateUserView, DeleteUserView {

    public static final String INTENT_KEY_ACTION = "action";
    public static final String INTENT_KEY_ACTION_UPDATE = "update";
    public static final String INTENT_KEY_ACTION_ADD = "add";
    public static final String INTENT_KEY_ID = "id";
    public static final String INTENT_KEY_NAME = "name";
    public static final String INTENT_KEY_EMAIL = "email";

    @Bind(R.id.etName)
    EditText editTextName;
    @Bind(R.id.etEmail)
    EditText editTextEmail;
    @Bind(R.id.btnSubmit)
    Button btnSubmit;
    @Bind(R.id.btnDelete)
    Button btnDelete;

    private MaterialDialog progressDialog;

    private String ACTION;
    private User user;
    private RegisterUserPresenter mRegisterUserPresenter;
    private UpdateUserPresenter mUpdateUserPresenter;
    private DeleteUserPresenter mDeleteUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        ButterKnife.bind(this);
        getIntentData();
        setUpUI();
    }

    private void getIntentData() {
        ACTION = getIntent().getStringExtra(INTENT_KEY_ACTION);

        if (ACTION.equals(INTENT_KEY_ACTION_UPDATE)) {
            user = new User();
            user.setUserId(getIntent().getIntExtra(INTENT_KEY_ID, 0));
            user.setUserName(getIntent().getStringExtra(INTENT_KEY_NAME));
            user.setUserEmail(getIntent().getStringExtra(INTENT_KEY_EMAIL));
        }
    }

    private void setUpUI() {

        UserDataRepository mUserDataRepository = new UserDataRepository();
        JobExecutor mJobExecutor = new JobExecutor();
        UIThread mUIThread = new UIThread();

        this.mRegisterUserPresenter = new RegisterUserPresenter(this, new UseCaseAddUser(mUserDataRepository, mJobExecutor, mUIThread));
        this.mUpdateUserPresenter = new UpdateUserPresenter(this, new UseCaseUpdateUser(mUserDataRepository, mJobExecutor, mUIThread));
        this.mDeleteUserPresenter = new DeleteUserPresenter(this, new UseCaseDeleteUser(mUserDataRepository, mJobExecutor, mUIThread));

        switch (ACTION) {
            case INTENT_KEY_ACTION_ADD:
                btnSubmit.setText(R.string.action_register);
                break;
            case INTENT_KEY_ACTION_UPDATE:
                this.editTextName.setText(user.getUserName());
                this.editTextEmail.setText(user.getUserEmail());
                btnSubmit.setText(R.string.action_update);
                btnDelete.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick(R.id.btnSubmit)
    void onBtnSubmitClick() {

        switch (ACTION) {
            case INTENT_KEY_ACTION_ADD:
                mRegisterUserPresenter.registerUser(user);
                break;
            case INTENT_KEY_ACTION_UPDATE:
                User user = new User();
                user.setUserId(this.user.getUserId());
                user.setUserName(editTextName.getText().toString());
                user.setUserEmail(editTextEmail.getText().toString());
                mUpdateUserPresenter.registerUser(user);
                break;
        }
    }


    @OnClick(R.id.btnDelete)
    void onBtnDeleteClick() {
        mDeleteUserPresenter.deleteUser(user);
    }

    @Override
    public void deletedUser() {
        new MaterialDialog.Builder(this)
                .content(R.string.users_correctly_deleted)
                .progress(true, 0)
                .cancelable(false)
                .progressIndeterminateStyle(false)
                .show();
    }

    @Override
    public void userRegistered(User user) {
        new MaterialDialog.Builder(this)
                .content(R.string.users_correctly_registered)
                .progress(true, 0)
                .cancelable(false)
                .progressIndeterminateStyle(false)
                .show();
    }


    @Override
    public void updatedUser(User user) {
        new MaterialDialog.Builder(this)
                .content(R.string.users_correctly_updated)
                .progress(true, 0)
                .cancelable(false)
                .progressIndeterminateStyle(false)
                .show();

    }

    @Override
    public void showLoading() {
        progressDialog = new MaterialDialog.Builder(this)
                .content(R.string.please_wait)
                .progress(true, 0)
                .cancelable(false)
                .progressIndeterminateStyle(false)
                .show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return null;
    }

}
