# Playground

This app includes some features that showcase different Android libraries, jetpack components, and architecture practices. So far, it has: 

- ImagesPaginationActivity: This activity allows to user to search for images using some keywords. It queries the Unsplash API for images, and implements pagination allowing the user to scroll continuously to keep querying for more images. The code is organized in MVVM. It requires the access key from [Unsplash API](https://unsplash.com/developers).

- NewsRoomActivity: This activity allows to user to see the recent top headlines in the news using the News API and refreshes the data on a swipe-down gesture. It loads cached data from a Room database if opened before 3 minutes have elapsed since the last api query, otherwise it queries the api and updates the db. If the user swipes down, it queries the api for fresh data. The code is organized in MVVM. It requires the key from [News Api](https://newsapi.org).


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


