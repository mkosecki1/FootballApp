# FootballApp <img src="https://i.ibb.co/GsP1vY7/ic-launcher.png" width=50>

The application show statistics of top five european football leagues: Premier League (England), La Liga (Spain), Bundesliga (Germany), Serie A (Italy). App focus on top scorers and table statistics.

<b>API source:</b> https://www.football-data.org/documentation/api

# Technology:
<b>Dependency Injection:</b> <i>Koin</i>
  
<b>Architecture pattern:</b> <i>MVVM</i>

<b>User authentication:</b> <i>Firebase Authentication</i>

<b>Libraries:</b><i>
- Retrofit 2
- OkHttp
- Navigation component
- Epoxy
- Glide
- GlideToVectorYou</i>

# Login screen:
Screen where user can log in to application or go to registration screen. Application check validation of login and password and if it's not in database, show message (snackbar).

<img src="https://i.ibb.co/mRGP0qJ/login.png" height=500 width=281>

# Registration screen:
Screen where user can register, using his email as login and choosing his password. After proper register user is automatically moved to scoresrs screen. User have to login only for the first time. Until he logout, every time he lunch application, it will moved user to  screen sith scorers.

<img src="https://i.ibb.co/vLss09m/register.png" height=500 width=281>

# Scorers screen
Screen where user can check top scorers in actual season. Clicking on league name will expand the list with other leagues. Clicking on buttons at bottom bar user can switch between scorers screen (left button) and table screen (middle button) or logout (right button). 

<img src="https://i.ibb.co/hg9rpGz/scorers.png" height=500 width=281>   <img src="https://i.ibb.co/qksHnnH/scorers-spinner.png" height=500 width=281>

# Table screen
Screen where user can check actual season table. Clicking on league name will expand the list with other leagues.

<img src="https://i.ibb.co/Sd0C0J1/standings.png" height=500 width=281>   <img src="https://i.ibb.co/xJn2y7W/standings-spinner.png" height=500 width=281>

# Application diagram
<img src="https://i.ibb.co/x5VQtYy/Footbal-App-Diagram.png">

# Developed By
- Maciej Kosecki
