package com.orangepenguin.boilerplate.repository;

import com.orangepenguin.boilerplate.di.ComponentUtil;
import com.orangepenguin.boilerplate.di.TestRepositoryModule;
import com.orangepenguin.boilerplate.model.User;
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
        TestRepositoryModule testRepositoryModule = ComponentUtil.setUpTestRepositoryModule();
        testRepositoryModule.setGitHubUrl(serverBaseUrl);
        System.out.printf("serverUrl '" + serverBaseUrl + "'");
        userRepository = new UserRepository();
    }

    @Test
    public void fetchUserSync() throws Exception {
        server.enqueue("github_user");
        User user = userRepository.fetchUserSync("itsymbal");
    }
}