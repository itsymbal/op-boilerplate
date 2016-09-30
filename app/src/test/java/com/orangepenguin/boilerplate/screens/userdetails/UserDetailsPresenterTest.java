package com.orangepenguin.boilerplate.screens.userdetails;

import android.os.Parcelable;

import com.orangepenguin.boilerplate.di.ComponentUtil;
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
    @Mock UserDetailsContract.View mockView;
    @Mock User mockUser;

    @Before
    public void setUp() {
        ComponentUtil.setUpTestPresenterModule();
        userDetailsPresenter = new UserDetailsPresenter();
    }

    @Test
    public void testSavingState() {
        userDetailsPresenter.setView(mockView, mockUser);
        Parcelable state = userDetailsPresenter.onSaveState();
        assertEquals(mockUser, state);
    }
}