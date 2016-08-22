# op-boilerplate

This is a Bolierplate Android app.
It is architected using the MVP (Model-View-Presenter) pattern.

#This implementation has the following features:

1. Presenters survive configuration change of the View
1. Presenters keep state. When a View gets recreated upon configuration change it inherits the presenter from the previous View. Presenter updates View state to that of the previously existing View. For example, if a View sends an async request, and a new View is created, the request is not re-sent. The new View gets the callback when the async request returns. If the async request has returned in one view, and new veiew is created, new view gets the data delivered to it immediately
1. Presenters are injected into Views using Dagger 2. That makes it easy to inject mock Presenters into Views, and mock dependencies into Presenters.
1. Code is fully tested and comes with a test suite setup. Presenters are tested using plain jUnit tests. Views are tested using Robolectric.

# Project integrates the following libraries:
1. Dagger 2 dependency injection
1. ButterKnife view injection
1. Timber logging
1. RetroLambda for Java 8 Lambdas
1. LeakCanary 
1. ConstraintLayout 
1. Saripaar input validation
1. RxJava
1. Victor SVG to PNG image conversion library

# Testing via
1. jUnit 4.12
1. Robolectric UI testing
1. Mockito mocking
1. PowerMock mocking (for mocking Final classes in Rx, e.g. Subscription)
1. AssertJ Java assertions

# Build Variants
Project has two Build Variants - Release and Debug (currently only used for different Timber implementations)

# TODO
 in no particular order

1. Finish code to call an actual backend API, saving state in the presenter, and showing results in a RecyclerView upon completion
1. Add CI project configuration via TravisCI or CircleCI
1. Add images and deploy project to Google Play
1. Add auto-deployment configuration
1. Add Checkstyle plugin
1. Add Espresso integration testing
1. Add automated performance testing
1. Add fake backend configuration using either MockWebServer or WireMock
1. Set up project generation utility 
1. Add testing for BaseActivity


