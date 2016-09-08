package com.orangepenguin.boilerplate;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.orangepenguin.boilerplate.di.Injector;

import org.assertj.android.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import butterknife.ButterKnife;

import static com.orangepenguin.boilerplate.fixtures.ActivityFixtures.buildAndStartActivity;
import static com.orangepenguin.boilerplate.fixtures.ActivityFixtures.emulateConfigurationChange;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseActivityTest extends BaseRobolectricTest {

    private TestActivity testActivity;
    private BasePresenter testPresenter;

    @Before
    public void setUp() throws Exception {
        testPresenter = mock(BasePresenter.class);
        Injector.setPresenter(BasePresenterInterface.class, testPresenter);
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

    @Test
    public void shouldSaveAndRestoreStateOnConfigurationChange() {
        Parcelable mockParcelable = mock(Parcelable.class);
        when(testPresenter.onSaveState()).thenReturn(mockParcelable);

        testActivity = emulateConfigurationChange(testActivity);

        verify(testPresenter).onSaveState();
        verify(testPresenter).restoreState(mockParcelable);
    }

    static class TestActivity extends BaseActivity<BasePresenterInterface> {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_test_activity);
        }
    }
}