package com.orangepenguin.boilerplate.screens.userdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.orangepenguin.boilerplate.BaseActivity;
import com.orangepenguin.boilerplate.R;
import com.orangepenguin.boilerplate.model.User;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.orangepenguin.boilerplate.BuildConfig.DEBUG;

public class UserDetailsActivity extends BaseActivity<UserDetailsContract.Presenter>
        implements UserDetailsContract.View {

    private static final String USER_INTENT_PARAM = "USER_INTENT_PARAM";

    @Inject UserDetailsContract.Presenter presenter;
    @BindView(R.id.avatar_imageview) ImageView avatarImageview;
    @BindView(R.id.username_textview) TextView usernameTextView;
    @BindView(R.id.name_textview) TextView nameTextView;
    @BindView(R.id.company_textview) TextView companyTextView;
    @BindView(R.id.repos_textview) TextView reposTextView;
    private Picasso picasso;

    public static Intent makeIntent(Context context, User user) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(USER_INTENT_PARAM, user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        ButterKnife.bind(this);
        if (presenter == null) {
            UserDetailsInjector.getUserDetailsComponent().inject(this);
        }

        User user = (User) getIntent().getSerializableExtra(USER_INTENT_PARAM);
        presenter.setView(this, user);

        picasso = Picasso.with(this);
        if (DEBUG) picasso.setIndicatorsEnabled(true); //show cached indicators
    }

    @NonNull
    @Override
    protected UserDetailsContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void setPresenter(UserDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setUsername(String username) {
        usernameTextView.setText(username);
    }

    @Override
    public void setCompany(String company) {
        companyTextView.setText(company);
    }

    @Override
    public void setName(String name) {
        nameTextView.setText(name);
    }

    @Override
    public void setAvatarUrl(String avatarUrl) {
        picasso.load(avatarUrl).into(avatarImageview);
    }
}
