package com.orangepenguin.boilerplate.mvp.userdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.orangepenguin.boilerplate.BaseActivity;
import com.orangepenguin.boilerplate.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailsActivity extends BaseActivity<UserDetailsContract.Presenter>
        implements UserDetailsContract.View {

    private static final String USERNAME_INTENT_PARAM = "USERNAME_INTENT_PARAM";

    @BindView(R.id.contents) EditText contents;
    @Inject UserDetailsContract.Presenter presenter;

    public static Intent makeIntent(Context context, String username) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(USERNAME_INTENT_PARAM, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        if (presenter == null) {
            UserDetailsInjector.getUserDetailsComponent().inject(this);
        }

        String username = getIntent().getStringExtra(USERNAME_INTENT_PARAM);
        presenter.setView(this, username);

        contents.setText("this be details activity for user " + username);
    }

    @Override
    protected UserDetailsContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void setPresenter(UserDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
