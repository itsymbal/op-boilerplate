package com.orangepenguin.boilerplate.di;

public class ComponentUtil {
    // private constructor to force static usage
    private ComponentUtil() {
        throw new NoSuchMethodError();
    }

    /**
     * Create a TestPresenterComponent, which includes a TestPresenter module. Module will provide mocks initialized
     * within. Mocks are then accessible through the returned TestPresenterModule, for tests to further configure.
     */

    // TODO: need to figure out a way to handle a case of a Component including multiple Modules. Maybe return a
    // map of modules by class?
    public static TestPresenterModule setUpTestPresenterModule() {

        TestPresenterModule testPresenterModule = new TestPresenterModule();

        TestPresenterComponent testPresenterComponent =
                DaggerTestPresenterComponent
                        .builder()
                        .testPresenterModule(testPresenterModule)
                        .build();
        Injector.setPresenterComponent(testPresenterComponent);

        return testPresenterModule;
    }

    public static TestActivityModule setUpTestActivityModule() {

        TestActivityModule testActivityModule = new TestActivityModule();

        TestActivityComponent testActivityComponent =
                DaggerTestActivityComponent
                        .builder()
                        .testActivityModule(testActivityModule)
                        .build();

        Injector.setActivityComponent(testActivityComponent);
        return testActivityModule;
    }

    public static TestRepositoryModule setUpTestRepositoryModule() {

        TestRepositoryModule testRepositoryModule = new TestRepositoryModule();

        TestRepositoryComponent testRepositoryComponent =
                DaggerTestRepositoryComponent
                        .builder()
                        .testRepositoryModule(testRepositoryModule)
                        .build();

        Injector.setRepositoryComponent(testRepositoryComponent);
        return testRepositoryModule;
    }
}
