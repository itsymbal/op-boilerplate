package com.orangepenguin.boilerplate.screens.userdetails;

import android.os.Parcelable;

import com.orangepenguin.boilerplate.ApplicationInterface;
import com.orangepenguin.boilerplate.di.TestPresenterModule;
import com.orangepenguin.boilerplate.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.orangepenguin.boilerplate.di.ComponentUtil.setUpPresenterDependencies;
import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsPresenterTest {

    UserDetailsPresenter userDetailsPresenter;
    @Mock UserDetailsContract.View mockView;
    @Mock ApplicationInterface mockApplication;
    @Mock User mockUser;

    @Before
    public void setUp() {
        setUpDependencies();
        userDetailsPresenter = new UserDetailsPresenter();
    }

    @Test
    public void testSavingState() {
        userDetailsPresenter.setView(mockView, mockUser);

        Parcelable state = userDetailsPresenter.onSaveState();

        assertEquals(mockUser, state);


    }

    private void setUpDependencies() {
        setUpPresenterDependencies(
                TestPresenterModule
                        .builder()
                        .baseApplication(mockApplication)
                        .build());
    }

}