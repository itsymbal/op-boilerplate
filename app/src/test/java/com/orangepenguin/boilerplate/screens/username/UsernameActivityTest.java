package com.orangepenguin.boilerplate.screens.username;

import com.orangepenguin.boilerplate.BaseRobolectricTest;
import com.orangepenguin.boilerplate.di.Injector;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static com.orangepenguin.boilerplate.fixtures.ActivityFixtures.buildAndStartActivity;

public class UsernameActivityTest extends BaseRobolectricTest {

    private UsernameActivity usernameActivity;
    private UsernameContract.Presenter mockPresenter = Mockito.mock(UsernameContract.Presenter.class);

    @Before
    public void setUp() throws Exception {
        setupTestDependencies();
    }

    @Test
    public void sampleTest() {
        usernameActivity = buildAndStartActivity(UsernameActivity.class);
    }

    private void setupTestDependencies() {
        Injector.setPresenter(UsernameContract.Presenter.class, mockPresenter);
    }
}
