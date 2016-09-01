package com.orangepenguin.boilerplate.screens.username;

import com.orangepenguin.boilerplate.BaseRobolectricTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

import static com.orangepenguin.boilerplate.fixtures.ActivityFixtures.buildAndStartActivity;

public class UsernameActivityTest extends BaseRobolectricTest {

    private UsernameActivity usernameActivity;
    private UsernameContract.Presenter mockPresenter = Mockito.mock(UsernameContract.Presenter.class);

    @Before
    public void setUp() throws Exception {
        setupTestDependencies();
        usernameActivity = buildAndStartActivity(UsernameActivity.class);
    }

    @Test
    public void sampleTest() {

    }

    private void setupTestDependencies() {
        UsernameInjector.setPresenter(mockPresenter);
    }
}
