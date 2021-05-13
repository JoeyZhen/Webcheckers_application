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
  </div>

  <div class="body">
      <form action="/signin" method="POST">
          <fieldset>
              <legend>Login Window</legend>
              <p>
                  <label>Username</label>
                  <input type = "text"
                         id = "myText"
                         name="myText"
                         value = "" />
              </p>
              <#if message??>
                  <p>${message}</p>
              </#if>
              <p>
                  <button type="submit">Login</button>
              </p>
          </fieldset>
      </form>
  </div>



</div>
</body>
</html>
