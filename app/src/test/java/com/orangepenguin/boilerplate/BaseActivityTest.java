package com.orangepenguin.boilerplate;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.orangepenguin.boilerplate.mvp.BasePresenter;

import org.assertj.android.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import butterknife.ButterKnife;

import static com.orangepenguin.boilerplate.fixtures.ActivityFixtures.buildAndStartActivity;
import static com.orangepenguin.boilerplate.fixtures.ActivityFixtures.emulateConfigurationChange;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseActivityTest extends BaseRobolectricTest {

    private TestActivity testActivity;

    @Before
    public void setUp() throws Exception {
        testActivity = buildAndStartActivity(TestActivity.class);
        ButterKnife.bind(this, testActivity);
    }

    /**
     * this test verifies that Presenter survives configuration change. Presenter keeps state of the View, if View has
     * such state. Therefore this test is wicked important.
     */
    @Test
    public void shouldNotCreateNewPresenterOnConfigurationChange() {
        BasePresenterInterface originalPresenter = testActivity.presenter;
        testActivity = emulateConfigurationChange(testActivity);
        assertThat(testActivity.presenter).isEqualTo(originalPresenter);
    }

    @Test
    public void showLoadingIndicatorShouldShowIndicatorAndHideContent() {
        testActivity.showLoadingIndicator();

        Assertions.assertThat(testActivity.findViewById(R.id.loading_indicator)).isVisible();
        Assertions.assertThat(testActivity.findViewById(R.id.contents_container)).isInvisible();
    }

    @Test
    public void hideLoadingIndicatorShouldHideIndicatorAndShowContent() {
        testActivity.hideLoadingIndicator();

        Assertions.assertThat(testActivity.findViewById(R.id.loading_indicator)).isInvisible();
        Assertions.assertThat(testActivity.findViewById(R.id.contents_container)).isVisible();
    }

    static class TestActivity extends BaseActivity<BasePresenterInterface> {

        BasePresenterInterface presenter;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_test_activity);
            if (presenter == null) {
                presenter = new TestPresenter(); // in real code this would be injected by Dagger2
            }
        }

        @Override
        protected BasePresenterInterface getPresenter() {
            return presenter;
        }

        @Override
        protected void setPresenter(BasePresenterInterface testPresenter) {
            this.presenter = testPresenter;
        }
    }

    static class TestPresenter extends BasePresenter implements BasePresenterInterface {
    }
}