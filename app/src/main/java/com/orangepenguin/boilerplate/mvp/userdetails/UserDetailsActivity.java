package com.orangepenguin.boilerplate.mvp.userdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.orangepenguin.boilerplate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailsActivity extends AppCompatActivity {

    private static final String USERNAME_INTENT_PARAM = "USERNAME_INTENT_PARAM";

    @BindView(R.id.contents) EditText contents;

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

        String username = getIntent().getStringExtra(USERNAME_INTENT_PARAM);
        contents.setText("this be details activity for user "+username);
    }


}
