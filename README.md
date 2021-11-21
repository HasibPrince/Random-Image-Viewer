# Random-Image-Viewer
The app functions very simple, It just shows random image on each button click. Images are fetched from https://picsum.photos. The last fetched image is saved on local storage. Initially app shows the image from this local storage if available.

## Used Libraries / Frameworks
**Image Processing Library**: Glide

**Dependency Injection Framework**: Hilt

**Jetpack Libraries**: ViewModel, LiveData etc.

**Unit Testing Libraries**: JUnit, Mockito, kotlinx-coroutines-test, androidX-junit etc.

## Architecture
The app is Developed in **MVVM+Repository Pattern** architecture.

![this is an image](https://i.ibb.co/35KPqqr/MVVM-Diagram-1.png)

**View** represents User interfaces / User Interactions

**ViewModel** Manages actions against interactions from users and provides observable field for view to be updated

**Repository** Provides necessary data for viewmodel and works as abstraction layer for data source

**Data Source** Actual Source of Data

