package com.orangepenguin.boilerplate.repository;

import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.di.UnitTestComponentFactory;
import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.rest.GitHubClientBuilder;
import com.orangepenguin.boilerplate.rx.RxTestSchedulers;
import com.orhanobut.mockwebserverplus.MockWebServerPlus;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class UserRepositoryTest {

    @Rule public MockWebServerPlus server = new MockWebServerPlus();
    UserRepository userRepository;

    @Before
    public void setUp() {
        String serverBaseUrl = server.url("/");
        Injector.setComponentFactory(new UnitTestComponentFactory());

        System.out.printf("serverUrl '" + serverBaseUrl + "'");
        userRepository = new UserRepository(GitHubClientBuilder.getGitHubClient(serverBaseUrl), new RxTestSchedulers());
    }

    @Test
    public void fetchUserSync() throws Exception {
        server.enqueue("github_user");
        User user = userRepository.fetchUserSync("itsymbal");
    }
}
