package com.krescruz.restapitest.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.krescruz.restapitest.R;
import com.krescruz.restapitest.data.model.User;
import com.krescruz.restapitest.data.repository.UserDataRepository;
import com.krescruz.restapitest.domain.executor.JobExecutor;
import com.krescruz.restapitest.domain.executor.UIThread;
import com.krescruz.restapitest.domain.usecase.UseCaseGetUserList;
import com.krescruz.restapitest.presentation.adapter.UsersAdapter;
import com.krescruz.restapitest.presentation.presenter.LoadUserListPresenter;
import com.krescruz.restapitest.presentation.view.LoadUserListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoadUserListView {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    LoadUserListPresenter mLoadUserListPresenter;
    private MaterialDialog progressDialog;
    private UsersAdapter mUsersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this, ActivityUserForm.class);
                intent.putExtra(ActivityUserForm.INTENT_KEY_ACTION, ActivityUserForm.INTENT_KEY_ACTION_ADD);
                startActivity(intent);
            }
        });
        ButterKnife.bind(this);
        setUpUI();
    }

    private void setUpUI() {
        // Set up the users adapter
        this.mUsersAdapter = new UsersAdapter(new ArrayList<User>());
        this.mUsersAdapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onPostItemClicked(User user) {
                Intent intent = new Intent(MainActivity.this, ActivityUserForm.class);
                intent.putExtra(ActivityUserForm.INTENT_KEY_ACTION, ActivityUserForm.INTENT_KEY_ACTION_UPDATE);
                intent.putExtra(ActivityUserForm.INTENT_KEY_ID, user.getUserId());
                intent.putExtra(ActivityUserForm.INTENT_KEY_NAME, user.getUserName());
                intent.putExtra(ActivityUserForm.INTENT_KEY_EMAIL, user.getUserEmail());
                startActivity(intent);
            }
        });

        // Set up the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(layoutManager);
        this.mRecyclerView.setAdapter(mUsersAdapter);

        // Set up loadUserList presenter
        this.mLoadUserListPresenter = new LoadUserListPresenter(this, new UseCaseGetUserList(new UserDataRepository(), new JobExecutor(), new UIThread()));
        this.mLoadUserListPresenter.getUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            mUsersAdapter.clear();
            mLoadUserListPresenter.getUsers();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showUsers(List<User> users) {
        this.mUsersAdapter.addUsers(users);

    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new MaterialDialog.Builder(this)
                    .content(R.string.please_wait)
                    .progress(true, 0)
                    .cancelable(false)
                    .progressIndeterminateStyle(false)
                    .show();
        } else {
            progressDialog.show();
        }
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
