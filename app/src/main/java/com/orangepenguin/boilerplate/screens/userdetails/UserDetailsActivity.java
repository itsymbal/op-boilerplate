package com.orangepenguin.boilerplate.screens.userdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.orangepenguin.boilerplate.BaseActivity;
import com.orangepenguin.boilerplate.R;
import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.util.ImageFetcher;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

public class UserDetailsActivity extends BaseActivity<UserDetailsPresenter>
        implements UserDetailsView {

    private static final String USER_INTENT_PARAM = "USER_INTENT_PARAM";
    @Inject @Named("fitCenterCropWhitePlaceholder")
    ImageFetcher<String, ImageView> imageFetcher;
    @Inject UserDetailsPresenter presenter;
    @BindView(R.id.avatar_imageview) ImageView avatarImageView;
    @BindView(R.id.username_textview) TextView usernameTextView;
    @BindView(R.id.name_textview) TextView nameTextView;
    @BindView(R.id.company_textview) TextView companyTextView;
    @BindView(R.id.repos_textview) TextView reposTextView;
    private User user;

    public static Intent makeIntent(Context context, User user) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(USER_INTENT_PARAM, user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // super.onCreate() has to be called *after* retrieving component. Android tries to reinitialize any Fragments
        // during super.onCreate() and those would rely on viewComponent already having been created.
        getViewComponent().inject(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_details);

        user = getIntent().getParcelableExtra(USER_INTENT_PARAM);
        presenter.initialize(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.takeView(this);
        presenter.setUser(user);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.dropView();
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
        imageFetcher.fetchAndSetImage(avatarUrl, avatarImageView);
    }

    @Override
    protected UserDetailsPresenter getPresenter() {
        return presenter;
    }
}
