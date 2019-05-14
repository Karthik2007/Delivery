# Delivery
Delivery is an Android application which is created for showcasing the updated way of doing things in Android. On the shell, it is a two page app to show list of location items and map to locate. This MVVM based application uses wide variety of components across various layers for segregation, data flow consitency and scalability. 

-> MVVM (Base arch and data flow model)

-> Dagger2 (Dependency Injection)

-> Android Architecture Components

-> Data binding

-> Room (persistence storage library)

-> Retrofit (Networking)

This aplication is completely written in kotlin using ModelViewViewModel architecture.


ScreenShot
-----------
![Dark](screenshots/Screenshot_dark.png)

Features
--------
1) Delivery List - List shows items to deliver
    a) Pagination
    b) Response Caching to have offline data
2) Delivery Location on Google Map 

Branches
--------
1) Master - Black themed app
2) white_theme


Pre-requsites
- Add Google Maps API_KEY in the AndroidManifest.xml to use Google Map.

IDE:
- Android studio 3.2.1

Language:
- Kotlin


Architecture
-----------
![Architecture](screenshots/arch_diagram.png)

Libraries
----------
- Retrofit2
- Dagger2
- Android Architecture Components
- Data Binding




Light theme
-----------
![Light](screenshots/Screenshot_white.png)
