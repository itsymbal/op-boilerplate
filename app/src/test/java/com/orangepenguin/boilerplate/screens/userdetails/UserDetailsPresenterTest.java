package com.orangepenguin.boilerplate.screens.userdetails;

import android.os.Parcelable;

import com.orangepenguin.boilerplate.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsPresenterTest {

    UserDetailsPresenter userDetailsPresenter;
    @Mock UserDetailsView mockView;
    @Mock User mockUser;

    @Before
    public void setUp() {
        userDetailsPresenter = new UserDetailsPresenter();
        userDetailsPresenter.takeView(mockView);
    }

    @Test
    public void testSavingState() {
        userDetailsPresenter.setUser(mockUser);
        Parcelable state = userDetailsPresenter.onSaveInstanceState();
        assertEquals(mockUser, state);
    }
}
