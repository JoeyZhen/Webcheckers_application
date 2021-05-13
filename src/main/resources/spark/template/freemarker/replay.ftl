<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>Web Checkers</h1>

    <div class="navigation">
        <a href="/">Home</a>
        <a href="/out?name=${playername}">Sign Out</a>
        <span id="loginOrName">${playername}</span>
    </div>

    <div class="body">
    <p><u>Games Played</u></p>
    <#list listofgames as game>
    <a href="/replay/game?gamename=${game}" id="something">${game}</a>
    <script>
        let link = document.getElementById("something");
        link.setAttribute("href", "/replay/game?gameID=".concat( link.innerHTML.slice(4)));
    </script>
    </#list>
    </div>



</div>
</body>
</html>
