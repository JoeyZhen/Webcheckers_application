
<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>${title} | Web Checkers</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
  <#--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>-->
  <#--<script src="/public/js/home.js"></script>-->

</head>
<body>
<div class="page">

  <h1>Web Checkers</h1>

  <div class="navigation">
    <a href="/">Home</a>

    <#if newPlayer>
      <a id="loginOrName" href="/signin">Login</a>
    <#else>
        <a href="/replay">Replay a game</a>
      <a href="/out?name=${playerName}">Sign Out</a>
      <span id="loginOrName">${playerName}</span>
    </#if>
  </div>

  <div class="body">
    <p class="welcome">Welcome to the world of online Checkers.<#if inGameError> ${nameToChallenge} is in a game</#if></p>


    <#if newPlayer>

      <br/>
      <p class="welcome">There are currently ${activePlayers} active players.</p>

    <#else>
        <#if message??>
        <p>${message.text}</p>
        <br>
        </#if>

      <div class="row">

        <div class="column">

          <#--<a id="matchmakeLink" href="/matchMaking?maxDiff=25" style="text-align: center">Search for a match</a>-->
          <#--<div class="slider-class">-->
            <#--<span>-->

              <#--<label for="eloSlider">Max Elo Difference</label><input type="range" min="1" max="20" value="1" class="slider" id="eloSlider">-->
              <#--<span id="eloRangeSpan">25</span>-->

              <#--<script src="/js/home.js"></script>-->

            <#--</span>-->
          <#--</div>-->

          <br>

          <a id="matchmakeLink" href="/matchmake">Search for a game</a>


          <p class="column-header"><u>Active Players</u></p>
          <#list listofplayers as player>
            <ul>
              <a href="/game?name=${player}">${player}</a>
            </ul>
          </#list>
        </div>

        <div class="column column-header">

          <p class="column-header"><u>User Info</u></p>
        <#--<br>-->
          <p>Your Rating: ${eloRating}</p>
        <#--<br>-->
          <p>Total Games Played: ${totalGames}</p>
        <#--<br>-->
          <p>Your Record: ${winsString} / ${lossesString}</p>

        </div>

      </div>


    </#if>


  </div>


</div>
</body>
</html>
