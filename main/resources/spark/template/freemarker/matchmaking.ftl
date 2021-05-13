<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <#if inQueue>
    <meta http-equiv="refresh" content="3">
  </#if>
  <title>${title} | Web Checkers</title>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<link rel="stylesheet" type="text/css" href="/css/matchmaking.css">
  <#--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>-->
  <#--<script src="/public/js/home.js"></script>-->

</head>
<body>
<div class="page">

  <h1>Web Checkers</h1>

  <div class="navigation">

    <a href="/">Home</a>
    <a href="/out?name=${playerName}">Sign Out</a>
    <span id="loginOrName">${playerName}</span>

  </div>

  <div class="body">

    <#if inQueue>

    <div class="headerAndInstructions">

      <h2><u>Ranked Matchmaking</u></h2>

    </div>

      <div class="loadingAnimation">
        <div class="lds-ring"><div></div><div></div><div></div><div></div></div>
      </div>

      <p class="instructions">Searching for an opponent with a rating between ${eloFloor} and ${eloCeiling}</p>

      <div class="buttonDiv">
        <a href="/cancelMatchmaking" class="cancelButton">Cancel Matchmaking</a>
      </div>

    <div>



    </div>

    <#else>

    <div class="headerAndInstructions">

      <h2><u>Ranked Matchmaking</u></h2>

      <div class="instructions">
        <p>Set the range of skill that you wish to play against, then click the "Matchmake Me" button.</p>
        <p>You'll be matched up against someone of similar skill, and your rating will go up or down depending on the
          result of the game!</p>
        <p>If you're having trouble finding a game, try increasing the Elo range. This will allow you to be paired up
          with a wider selection of players!</p>
      </div>

    </div>

    <hr>

    <form action="/matchmake" method="POST">

      <div class="sliderDiv">
        <input type="range" name="maxDiff" min="1" max="60" value="${sliderPosition}" class="slider" id="eloSlider">
        <p class="eloRangeIndicator">Selected rating range: <span id="eloRangeSpan">25</span></p>
      </div>

      <div class="matchmakingInfo">
        <p>Your current rating is: <u id="eloRating">${eloRating}</u></p>
        <p>With the current settings, you'll be matched up against players with a rating ranging from <span
                  id="eloFloor">${initialEloFloor}</span> to
          <span id="eloCeiling">${initialEloCeiling}</span></p>
      </div>


      <div class="buttonDiv">
        <button type="submit" class="submitButton">Matchmake Me</button>
      </div>


    </form>

    <script src="/js/matchmaking.js"></script>

    </#if>


  </div>


</div>
</body>
</html>
