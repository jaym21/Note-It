# List-It
List It is an android app in Kotlin, which helps the user to note the things they want to remember to do.

![main_page](https://user-images.githubusercontent.com/48640844/103473518-7ad7fe00-4dbf-11eb-823e-1af6308e2a13.png)

This app is made to understand the implementation of Android Architecture Components.
- This app uses Room Database which has entity which defines the schema of database and DAO(Data Access Object) which is used to communicate with database and maps SQL queries to functions
- Repository is a simple class which is used to provide cleaner API to ViewModel to communicate with DAO.
- ViewModel is used as communication between UI and Repository.It acts as observer on LiveData and only updates the UI when data is actually changed.
- This app uses LiveData, which is basically a data holder class which can be observed.It always contains the latest version of data and notifys the observer about data changes.

![Android Architecture Components](https://user-images.githubusercontent.com/48640844/103457142-875a4900-4d22-11eb-8a79-f9b328f806ee.png)

# Dependencies
implementation "androidx.appcompat:appcompat:1.2.0"\
implementation "androidx.activity:activity-ktx:1.1.0"\
implementation 'com.android.support:design:28.0.0'

## Room Components
implementation "androidx.room:room-ktx:2.2.6"\
kapt "androidx.room:room-compiler:2.2.6"\
androidTestImplementation "androidx.room:room-testing:2.2.6"

## Lifecycle Components
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"\
implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"\
implementation "androidx.lifecycle:lifecycle-common-java8:2.2.0"

## UI
implementation "androidx.constraintlayout:constraintlayout:2.0.4"\
implementation "com.google.android.material:material:1.2.1"

## Testing
testImplementation "junit:junit:4.13.1"\
androidTestImplementation "androidx.arch.core:core-testing:2.1.0"\
androidTestImplementation ("androidx.test.espresso:espresso-core:3.1.0", {\
    exclude group: 'com.android.support', module: 'support-annotations'\
})\
androidTestImplementation "androidx.test.ext:junit:1.1.2"
