# kaliyev_timur_tinkoff_fintech_2023
Laboratory Work Tinkoff Fintech 2023 Android Application
Application implemented by Kaliyev Timur.
Application was written using Kotlin programming language 
using AAC (Fragments, ViewModels, Navigation, LiveData) and Room, Retrofit, Picasso libraries

Application architecture divided into 2 main parts: UI and Data Layer and uses helper layers.
Used architecture pattern is MVVM
ViewModels are used to hold and change UI state
Kotlin coroutines are used in purpose to perform long-performing operations such as network requests and database interaction
Application code is written using clean code and SOLID principles

Application gets list of popular films from Kinopoisk unofficial API and represents it in UI
User can check film details (Select interesting film -> Tap it card)
User can add film from the top films list to favourites list by long click film card
User sees a list of favourites films in appropriate section in main page
Favourite films' cards are stored in local database, so user can see them without internet connection
All network responses from server are cached, what increases application performance

Basic laboratory work requirements are implemented in application:
1. List of top films at application main page
2. Each film card contains information about film (name, preview, release year)
3. Details are shown by clicking film card (poster, description, genres, countries)
4. User is notificated if network is not available

Additional requirements implemented in application:
2. Sections Popular and Favourites. Long click to card leads marking film as favourite and saving it into application database
3. Favourite films are marked in general list of films
4. Search is available

Also:
1. 100% Kotlin project
2. Performance well optimized
3. Progress bars are shown
4. Response caching

Application tested using different screen size virtual devices (3,7 WVGA, Nexus One, Pixel 6 Pro) and Developer's physical device
