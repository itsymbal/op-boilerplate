package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.BaseApplication;
import com.orangepenguin.boilerplate.repository.UserRepo;
import com.orangepenguin.boilerplate.rest.GitHubClient;

import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

public class ComponentUtil {
    // private constructor to force static usage
    private ComponentUtil() {
        throw new NoSuchMethodError();
    }

    /**
     * Create a TestPresenterComponent, which includes a supplied TestPresenter module. Set TestPresenterComponent in
     * Injector
     *
     * @return
     */
    public static void setUpPresenterDependencies(TestPresenterModule testPresenterModule) {
        TestPresenterComponent testPresenterComponent =
                DaggerTestPresenterComponent
                        .builder()
                        .testPresenterModule(testPresenterModule)
                        .build();
        Injector.setPresenterComponent(testPresenterComponent);
    }

    /**
     * Create a TestPresenterComponent, which includes a TestPresenter module. Module will provide mocks initialized
     * here. Mocks are then accessible through the returned TestPresenterModule, for tests to further configure. Set
     * TestPresenterComponent in Injector
     *
     * @return
     */
    public static TestPresenterModule setUpPresenterDependencies() {

        BaseApplication mockApplication = mock(BaseApplication.class);
        GitHubClient mockGitHubClient = mock(GitHubClient.class);
        UserRepo mockUserRepo = mock(UserRepo.class);

        TestPresenterModule testPresenterModule =
                TestPresenterModule
                        .builder()
                        .baseApplication(mockApplication)
                        .gitHubClient(mockGitHubClient)
                        .observeOnScheduler(Schedulers.immediate())
                        .userRepo(mockUserRepo)
                        .build();

        TestPresenterComponent testPresenterComponent =
                DaggerTestPresenterComponent
                        .builder()
                        .testPresenterModule(testPresenterModule)
                        .build();
        Injector.setPresenterComponent(testPresenterComponent);

        return testPresenterModule;
    }
}
