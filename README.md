# Playground

This app includes some features that showcase different Android libraries, jetpack components, and architecture practices. So far, it has: 

**ImagesActivity**: This activity allows to user to search for images using some keywords. It queries the Unsplash API for images, and implements pagination allowing the user to scroll continuously to keep querying for more images. It requires the access key from [Unsplash API](https://unsplash.com/developers).

**Features**:
* RxJava
* ViewModel (MVVM)
* LiveData
* Pagination

**NewsActivity**: This activity allows to user to see the recent top headlines in the news using the News API and refreshes the data on a swipe-down gesture. It loads cached data from a Room database if opened before 3 minutes have elapsed since the last api query, otherwise it queries the api and updates the db. If the user swipes down, it queries the api for fresh data. It requires the key from [News Api](https://newsapi.org). It queries the News API for news articles, and caches the results in a database implemented using Room. If the user swipes down, it queries the api for fresh data. 

**Features**:
* RxJava
* ViewModel (MVVM)
* LiveData
* Room
* Data Binding
* SwipeRefreshLayout

**PhotoFilterActivity**: 
This activity allows the user to see WorkManager in action. The user can select an image from their device, apply the sepia filter to it to give it a brownish tinge, and then save it back to their device as a new image. This is accomplished by the chaining the following 3 tasks together using WorkManager, and running them one after the other: cleaning any previously saved image, applying the sepia filter on the newly selected image, and then saving that new image to the device. The code has some artificial `sleep()` calls to introduce some delays. We display a notification to show the progress until the work is completed. Note: most of the WorkManager code is a re-implementation of [Google's codelab on WorkManager](https://codelabs.developers.google.com/codelabs/android-workmanager-kt/).

**Features**:
* ViewModel (MVVM)
* LiveData
* Data Binding
* WorkManager

### To run
1. generate keys for whichever  apis you intend to use from the features list: 
- [Unsplash API](https://unsplash.com/developers)
- [News Api](https://newsapi.org)

2. open your local gradle properties file
- On Windows: `C:\Users\<you>\.gradle\gradle.properties`
- On Mac/Linux: `/Users/<you>/.gradle/gradle.properties`
  
3. add the keys like so:
```
Playground_UnsplashAccessKey="<your_key>"
Playground_NewsKey="<your_key>"
```
If you don't need to run one of the features, you can leave the `<your_key>` portion blank, but you do need to add all of the key names - i.e.: `Playground_UnsplashAccessKey=""`


