package com.orangepenguin.boilerplate.screens.userdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.orangepenguin.boilerplate.BaseActivity;
import com.orangepenguin.boilerplate.R;
import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.model.User;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;

public class UserDetailsActivity extends BaseActivity<UserDetailsContract.Presenter>
        implements UserDetailsContract.View {

    private static final String USER_INTENT_PARAM = "USER_INTENT_PARAM";

    @Inject Picasso picasso;
    @BindView(R.id.avatar_imageview) ImageView avatarImageView;
    @BindView(R.id.username_textview) TextView usernameTextView;
    @BindView(R.id.name_textview) TextView nameTextView;
    @BindView(R.id.company_textview) TextView companyTextView;
    @BindView(R.id.repos_textview) TextView reposTextView;

    public static Intent makeIntent(Context context, User user) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(USER_INTENT_PARAM, user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Injector.getActivityComponent().inject(this);

        User user = (User) getIntent().getSerializableExtra(USER_INTENT_PARAM);
        presenter.setView(this, user);
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
        picasso.load(avatarUrl).into(avatarImageView);
    }
}
