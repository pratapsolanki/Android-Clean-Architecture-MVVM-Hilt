# Android Clean Architecture with MVVM using Jetpack component


## 💡 What is Clean Architecture?

Clean architecture is a category of software design pattern for software architecture that follows the concepts of clean code and implements [SOLID principles](https://codersee.com/solid-principles-with-kotlin-examples/)

It’s essentially a collection of best practice design principles that help you keep business logic, or domain logic, together and minimize the dependencies within the system.

Clean architecture is a method of software development in which you should be able to identify what a program performs merely by looking at its source code. Robert C. Martin, also known as Uncle Bob, came up with the Clean Architecture concept in the year 2012.

![alt tag](https://github.com/jbsolutions2008/Medium_Clone_Conduit/blob/pratap/results/clean_architecture_software.jpg)

## 💡 Why Clean Architecture?

Separation of Concerns — Separation of code in different modules or sections with specific responsibilities making it easier for maintenance and further modification.
Loose coupling — flexible code anything can be easily be changed without changing the system
Easily Testable


## 💡 Layers of clean architecture

- **Presentation or UI:**
  A layer that interacts with the UI, mainly Android Stuff like Activities, Fragments, ViewModel, etc. It is dependent on Use Cases.
- **Domain:** 
  Contains the business logic of the application. It is the individual and innermost module.
- **Data:**
  It includes the domain layer. It would implement the interface exposed by domain layer and dispenses data to app

![alt tag](https://github.com/jbsolutions2008/Medium_Clone_Conduit/blob/pratap/results/clean_architecture.jpg)

## 💡 Advantages of Using Clean Architecture
- Easily testable.
- Scalable.
- Your team can add new features even more quickly.
- The project is even easier to maintain.

## 💡 Tech stack & Modern Library Tools

- [Kotlin, Coroutines + Flow for asynchronous](https://developer.android.com/kotlin/coroutines)
- [Dependency injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Jetpack Navigation](https://developer.android.com/guide/navigation/)
- [ViewModel - UI related data holder, lifecycle aware](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Room Persistence - local database](https://developer.android.com/training/data-storage/room)
- [MVVM Architecture (View - DataBinding - ViewModel - Model)](https://developer.android.com/topic/libraries/view-binding)
- [Glide - loading images](https://github.com/bumptech/glide)
- [Retrofit2 & OkHttp3 - construct the REST APIs and paging network data](https://square.github.io/retrofit/)
- [Gson - JSON representation](https://github.com/google/gson)
- [Material-Components - Material design components](https://material.io/design)
- [Leak Canary - memory leak detection library for Android](https://github.com/square/leakcanary)


## 💡 Project: Medium Clone

This is a sample presentation of Clean Architecture with MVVM in Android.

WebSite [Conduit](https://demo.realworld.io/#/)

API Collection [Conduit](https://demo.realworld.io/#/)


##  💡 Features

**Authentication**

- Regsiter 
- Login
- Get Current User
- Update User
- Logout

**Articles**

- All Articles
- Articles by Author
- Articles Favorited by Username
- Articles by Tag
- All Tags


**Articles, Favorite, Comments**

- Create Article 
- Feed
- All Articles
- All Articles with auth
- Articles by Author
- Articles by Author with auth
- Single Article by slug
- Articles by Tag
- Update Article
- Favorite Article
- rticles Favorited by Username
- ticles Favorited by Username with auth
- favorite Article
- Create Comment for Article
- All Comments for Article
- All Comments for Article
- Delete Comment for Article
- Delete Article


**Profiles**

- Register Celeb
- Profile
- Follow Profile
- Unfollow Profile
