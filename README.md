<p align="center">
  <img src="/images/banner.png">
</p>

![Run lints and compile](https://github.com/xmartlabs/gong/workflows/Run%20lints%20and%20compile/badge.svg)
[![codebeat badge](https://codebeat.co/badges/a92c68f9-c5e1-4e9f-8f67-ace7e51371d1)](https://codebeat.co/projects/github-com-xmartlabs-gong-master)

**Gong** is [Xmartlabs](https://xmartlabs.com/)' official Android template project, written in Kotlin, and focused on providing a solid app architecture.
One of the main objectives of this project is to supply a good starting point for all new android apps, which lets you move forward fast using the latest Android Components and libraries.
We're using "clean architecture" to structure, decouple, expand, and maintain the code.

## Architecture
There are 4 layers within the application:
- Domain layer - contains high-level abstraction of the application domain (like repositories, data access) and the use cases, which contain all of the application's business logic & domain rules.
- Data layer - implements domain layer abstractions, the `DataSources`, related to data persistence, REST calls, etc. 
- Device layer - implements domain layer abstractions that are not related to data persistence or user interface but are specific to the android platform: android services, cloud messaging, and many others.
- Presentation (UI) layer - all the functionality related to the Android user interface: built on top of [Jetpack Compose](https://developer.android.com/jetpack/compose).

<p align="center">
  <img height="250" src="/images/arch.png" >
</p>

## Overview
In order to understand how this work together, it's important to talk about each component's role inside the presentation layer.

First of all, the architecture. When jetpack compose is used, it's convenient to use an architecture based on an state. In this case, a combination of MVI and Redux patterns was chosen.
The MVI pattern has three main components: Intent, Model, and View.
The intent refers to the intention to change the state of the app, so in Gong's case, it would be the actions, delivered then to ViewModel.
ViewModel holds the model component of the pattern. It is responsible of creation of a new state, which is an immutable data structure.
At any given moment, there is only one state in the app, which represents a single source of truth.
The only way to change the state is to create a new one, triggered by the actions. But when and how is the new state created?
The Redux pattern comes up at this point. Redux is a pattern and library for managing and updating application state, using events called "actions".
More precisely, the main components of Redux are State, Action, and Reducer.
In Gong the composables communicate actions to the viewModels so they can manage and emit the state back to view.
This ensure state can only be updated in a predictable way.
Then, inside the model, the reducer is called with a proper action and the latest state and forward its result as an output value of the model.
Reducer is a function that takes the previous state and action and creates a new state, and in Gong this role is played by the function `processAction`, located in viewModels.


To continue with the insight of the project, let's see how this is done.
With the shared flow, actions are broadcast to an unknown number (zero or more) of subscribers.
In the absence of a subscriber, any posted action is immediately dropped. It is a design pattern to use for actions that must be processed immediately or not at all.


The ViewModel handles each action in the `processAction` method. Whenever an action is added to the "contract", it also has to be added here.
So all actions can be managed from the same place.
With the `channel`, each event is delivered to a single subscriber.
An attempt to post an event without subscribers will suspend as soon as the channel buffer becomes full, waiting for a subscriber to appear. Posted events are never dropped by default.
Then to handle this Ui effects and all things that should be displayed only once, "oneShotEvents" are used. Because Channels are hot and it is not necessary to show side effect again when orientation changed or UI become visible again.


Finally, for handling `UiState`, `StateFlow` is used.
`StateFlow` is a state-holder observable flow that emits the current and new state updates to its collectors, similar to a `LiveData` but with an initial value.
So a state is always present.
It's also a kind of SharedFlow. It's always expected to receive last view state when UI become visible.


Gong's Workflow example:

<p align="center">
  <img height="250" src="/images/workFlow.png" >
</p>

### Layers components and roles:

Now, as a way to give you an overview of the other layers and how the interaction with the presentation layer is done, let's review it's components.

To the presentation layer, the `UseCases` are the ones who resolve each invocation from the ViewModels, and they both interact using [coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) library.
A `UseCase` is a reusable component that might be used from different `ViewModels`.
The same goes for `Repositories`, a repository can stand on its own without the ViewModel and be re-used from different use cases.
All these classes exist with a clear goal and purpose. The logic is split sensibly.
It is worth to say that these repositories refer to those of the repository pattern.
Repository design pattern facilitates de-coupling of the business logic and the data access layers in your application with the former not having to have any knowledge on how data persistence would actually take place.
Repositories have the function of communication between Domain Layer and Data Layer.
More precisely, with coroutines help, they have to implement the necessary logic so they can call Remote and Local sources methods.

At the end of the chain, as mentioned, Data Layer is found. It is responsible for persisting and obtaining all the data required for the model using different sources.
`Repositories` use the store library to combine those different sources.
The remote sources are the ones who manage interaction with the different endpoints.
The local sources manage data base logic.

The core library for the communication between layer components is: [**Coroutines**](https://kotlinlang.org/docs/reference/coroutines-overview.html), used to perform all background tasks.

## Core Libraries
The main libraries that we are using are:
- [Android Architecture Components - Jetpack](https://developer.android.com/topic/libraries/architecture):
  - [Jetpack Compose](https://developer.android.com/jetpack/compose) which is the library used by the UI with all its composables.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) which allows you navigate between composables while taking advantage of the Navigation component’s infrastructure and features.
  - [Android Navigation Component](https://developer.android.com/guide/navigation) used to navigate across different pieces of content within your app.
  - [Room](https://developer.android.com/topic/libraries/architecture/room), a SQLite object mapping library.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) for asynchronous programming
- [Coil](https://coil-kt.github.io/coil/), an image loading library for Android backed by Kotlin Coroutines.
- [Koin](https://insert-koin.io/), a lightweight dependency injection framework for Kotlin.
- [OkHttp](https://square.github.io/okhttp/) and [Retrofit](https://square.github.io/retrofit/) for network communication.
- [AndroidSwissKnife](https://github.com/xmartlabs/AndroidSwissKnife) a set of extensions, helpers, and useful classes.
- [Timber](https://github.com/JakeWharton/timber) one of the most popular loggers on Android.
- [Stetho](http://facebook.github.io/stetho/), a sophisticated debug bridge for Android applications.
- [LeakCanary](https://square.github.io/leakcanary/), a memory leak detection library for Android.
- [AndroidSnapshotPublisher](https://github.com/xmartlabs/android-snapshot-publisher), one of the most important tools used in the QA process, it's a Gradle plugin that prepares and distributes deliverable versions easily when they are ready to test.

## Setup

To use this template, you can use the [gong_setup.sh](/gong_setup.sh) script that automatizes the setup process.
You can run it remotely executing the following command:
```bash
bash <(curl -s https://raw.githubusercontent.com/xmartlabs/gong/master/gong_setup.sh)
```

It will clone and setup all variables that you need.
If you prefer to do it manually, you have to follow these steps:
- Clone the project
- Update the `applicationId` in the app's build gradle file.
- Change the package structure based on your application id.

## Configuration and secrets
The app's version name is defined in the project's [Gradle file](/build.gradle). 
The app's version code is autogenerated based on the app's version name.

You have two files to define your constants:
`config.properties` which stores all of the app's configuration, like the backend's base URL, for example.
`secrets/keys.properties` which contains all of the secrets in your app, like a given API key for a third party service.
That environment's variables are injected in the app's [build.gradle](app/build.gradle), and they are accessible via the `BuildConfig` generated file.
The app access to that variables using the [Config](app/src/main/java/com/xmartlabs/gong/Config.kt) file.

The keystores are stored in the `secrets` folder, which is not tracked in git.

The library versions are managed in a [versions Gradle file](https://github.com/xmartlabs/GongAndroidBaseProject/blob/master/versions.gradle)

### Product Flavors

The app uses two flavors, one for production (`prod`) and another for development (`dev`) build.

Each flavor defines and application class (App.kt), that is used to define custom configurations in each one.
For example, the navigation logger listener is defined only for [development builds](app/src/dev/java/com/xmartlabs/gong/App.kt).

## What's next?
For an answer to this question you can check the [current project status](https://github.com/xmartlabs/GongAndroidBaseProject/projects/1) and if you happen to come up with a new idea you can always open a new issue!

## About
Made with ❤️ by [XMARTLABS](http://xmartlabs.com)
