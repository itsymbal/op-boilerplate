package com.orangepenguin.boilerplate.screens.username;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.orangepenguin.boilerplate.BaseActivity;
import com.orangepenguin.boilerplate.R;
import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.screens.userdetails.UserDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

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
    @BindView(R.id.username_edit_text) EditText usernameEditText;
    @BindView(R.id.remember_check_box) CheckBox rememberCheckBox;

    private Validator validator = new Validator(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: ponder initializing this in a Presenter?
        // discuss with twitter folks
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.username_activity);

        unbinder = ButterKnife.bind(this);
        validator.setValidationListener(this);
        presenter.setView(this);
    }

    @Override
    public void startDetailsActivity(User user) {
        startActivity(UserDetailsActivity.makeIntent(this, user));
    }

    @Override
    public void setUsername(String s) {
        usernameEditText.setText(s);
    }

    @Override
    public void checkRememberCheckbox() {
        rememberCheckBox.setChecked(true);
    }

    @Override
    public void setUsernameError(String error) {
        usernameEditText.setError(error);
    }

    @OnClick(R.id.view_user_button)
    void userButtonClick() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() { // if all validation rules pass
        presenter.showUserButtonPressed(usernameEditText.getText().toString(), rememberCheckBox.isChecked());
    }

    /**
     * Saripaar input validation can only be tested on device either by hand or using Espresso, but not using
     * Robolectric. This is because it does not run
     * validation if the View in question is not visible, which under Robolectric it never is (method isVisible()
     * traverses up the hierarchy and finds that the root view is not attached to any screen)
     *
     * @param errors
     */
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

    @OnClick(R.id.username_edit_text)
    void onUserNameClicked() {

    }
}
