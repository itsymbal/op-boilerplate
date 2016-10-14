# op-boilerplate

This is a Bolierplate Android app.
It is using the MVP (Model-View-Presenter) pattern.

#This implementation has the following features:

1. Presenters survive configuration change of the View. A new Presenter is created when a screen is first created, and destroyed when screen is destroyed. It persists across config changes.
1. Presenters keep state. When a View gets recreated upon configuration change it inherits the presenter from the previous View. Presenter updates View state to that of the previously existing View. For example, if a View sends an async request, and a new View is created, the request is not re-sent. The new View gets the callback when the async request returns. If the async request has returned in one view, and new veiew is created, new view gets the data delivered to it immediately
1. Presenters are injected into Views using Dagger 2. That makes it easy to inject mock Presenters into Views, and mock dependencies into Presenters.
1. Code is fully tested and comes with a test suite setup. Presenters are tested using plain jUnit tests. Views are tested using Robolectric.

### Crashlytics integration

Add your Fabric keys to app/fabric.properties. They are necessary to log to Crashlytics in Prod build.

# Project integrates the following libraries:
1. Dagger 2 dependency injection
1. ButterKnife view injection
1. Timber logging
1. RetroLambda for Java 8 Lambdas
1. LeakCanary memory leak detector
1. ConstraintLayout 
1. Saripaar user input validation
1. RxJava
1. Victor SVG to PNG image conversion library

# Testing via
1. jUnit 4.12
1. Robolectric UI testing
1. Mockito mocking
1. PowerMock mocking (for mocking Final classes in Rx, e.g. Subscription)
1. AssertJ Java assertions
1. AssertJ Android View assertions

# Build Variants
Project has two Build Variants - Release and Debug 

# TODO
 in no particular order
1. Add images and deploy project to Google Play
1. Add auto-deployment configuration, possibly via Fastlane
1. Set up project generation utility 
1. Add Checkstyle plugin
1. Add CI project configuration via TravisCI or CircleCI
1. Retrieve and display a list, to figure out integration with lists/ adapters
1. Enable caching in Repository. Retrieve from Network, populate memory cache
1. Enable local DB caching, probably via Realm DB.
1. Add Espresso integration testing
1. Add automated performance testing - start time, screen load time, APK size, method count
1. Example of a multi-module Component and its use (Component that uses multiple Modules - and creating test version of it)
1. Example of functionality composition. Reusable presenter components?
 multiple sections per screen - multiple fragments?
1. Master / detail flow implementation. Fragments as Views instead of Activities
1. Integrate third party SDK, e.g. Branch. Presenter integration via shim?
1. Signup / login flow example. Possibly using Firebase Authentication?
1. Error handling of network / user errors
1. Handle 'don't have network' state (airplane mode / network off / network not connected)
1. Rx network call with exponential backoff
1. Integrate View Binding with a View Model for setting data on View

License
--------

    Copyright 2016 Orange Penguin, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

