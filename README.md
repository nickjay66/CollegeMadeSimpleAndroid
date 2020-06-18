README

College Made Simple Android 

Application used for creating and tracking terms/course/assessments. Includes study timer with mindfulnes reminder intervals and an option to take notes. Push notifications will be sent for upcoming course start dates and assessments. The main purpose is so students can have a simple place to pre-plan their term and also prioritize their studying. App keeps track of study sessions so student can track total time studying.

Getting Started
Open project files in Android Studio. Project utilizes SQLite so DB functionality is local.

Prerequisites
Install latest version of Android Studio. Minimum SDK is 15, target is 29.

Installing
- Install Android Studio 3.5.3. Clone repository and open in Android. All files, including Gradle Build are included and should ge the project up and running.

Using
1. Open app and click start. There is an option for help that will give you instructions on how to use the app.
2. Add a term by clicking the add term button, or, click on study session and view/start study sessions with minfulness intervals.
3. Once new term is added, click on the added term of choice in the recycler view and you will ba taken to the Course Activity. Here you can view courses associated with term. Add course, or click on course that is already added.
4. If you click on a course in the recycler view, you will be taken to a page that lets you edit the information of the course. There is also an option here to view/add assessments.
5. View or add assessment page that will be directly associated with the course. Push notifications will be sent for course start dates and upcoming assessments.

Deployment
App will be deployed on Google Play. For testing purposes, a Pixel 2 and Pixel 3 emulator with an SDK of 29 were used.


Built With
Jetpack - Room Persistency Library
Gradle - Dependency Management

Contributing
Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests to us.

Versioning
*

Authors
Nick Jerrems 


License
*

Acknowledgments
Thanks to those who helped me figure out Room. It was a learning curve.
