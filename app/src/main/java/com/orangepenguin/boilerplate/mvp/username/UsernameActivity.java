package com.orangepenguin.boilerplate.mvp.username;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.orangepenguin.boilerplate.BaseActivity;
import com.orangepenguin.boilerplate.R;
import com.orangepenguin.boilerplate.mvp.userdetails.UserDetailsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * this simple activity has a single text field, a checkbox, and a submit button. It is just about the simplest setup
 * for an MVP pattern. The Activity plays the role of View. The UsernamePresenter is the Presenter. The View forwards
 * user interactions to the Presenter. The presenter figures out appropriate actions to take (e.g. forward to next
 * activity).
 * The View performs the action (forwarding to next activity or saving / deleting preferences) as those are
 * Android-specific actions.
 */
public class UsernameActivity extends BaseActivity<UsernameContract.Presenter>
        implements UsernameContract.View, Validator.ValidationListener {

    // Simple input validation performed by Saripaar validation library. Validation kept out of Presenter
    @NotEmpty(sequence = 1, messageResId = R.string.username_required)
    @BindView(R.id.username_edit_text)
    EditText usernameEditText;
    @BindView(R.id.remember_check_box)
    CheckBox rememberCheckBox;

    @Inject
    UsernameContract.Presenter presenter;
    private Validator validator = new Validator(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_activity);

        ButterKnife.bind(this);
        validator.setValidationListener(this);

        // injection only happens if presenter is null. That means the only thing that can be @Inject'ed into this
        // view is the Presenter. That in turn can have anything necessary @Inject'ed, and then set in the View on
        // setView(). Alternatively, create a data structure, e.g. a Map, to contain all @Inject'ed values and
        // parametrize BaseActivity with it, and rename get/setPresener with ..NonConfigurationInstance
        if (presenter == null) {
            UsernameInjector.getUsernameComponent().inject(this);
        }

        presenter.setView(this);
        showLoadingIndicator();
    }

    @Override
    public UsernameContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(UsernameContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void startDetailsActivity(String username) {
        startActivity(UserDetailsActivity.makeIntent(this, username));
    }

    @Override
    public void setUsername(String s) {
        usernameEditText.setText(s);
    }

    @Override
    public void checkRememberCheckbox() {
        rememberCheckBox.setChecked(true);
    }

    @OnClick(R.id.view_user_button)
    void userButtonClick() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() { // if all validation rules pass
        presenter.showUserButtonPressed(usernameEditText.getText().toString(), rememberCheckBox.isChecked());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (int i = 0; i < errors.size(); i++) {
            ValidationError validationError = errors.get(i);
            View viewWithError = validationError.getView();
            if (viewWithError instanceof EditText) { // only for EditText
                Rule failedRule = validationError.getFailedRules().get(0);
                ((EditText) viewWithError).setError(failedRule.getMessage(this));
            }
        }

        if (!errors.isEmpty()) {
            errors.get(0).getView().requestFocus();
        }
    }
}
