<%--
  Created by IntelliJ IDEA.
  User: Caterina
  Date: 4/17/2016
  Time: 2:55 PM
  To change this template use File | Settings | File Templates.
--%>

<%--ReCAPTCHA Site Key: 6LcDrR0TAAAAAI6L3RPWH5SoVDPhYVusdVKpV9Ny
ReCAPTCHA Secret Key: 6LcDrR0TAAAAAMt-DEsvLk8Vc5iRuHnZB0q0XG3R--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head lang="en">
  <meta charset="UTF-8">
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/avengersFav.png">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/scripts/scripts.js"></script>
  <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/reset.css">--%>
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
  <%--<script src='http://codepen.io/andytran/pen/vLmRVp.js'></script>--%>
  <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
  <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
  <title>Agile Estimation</title>
  <script src='https://www.google.com/recaptcha/api.js'></script>
  <script>
    $(document).ready(function(){
      $('.toggle').click(function(){
        // alert("I get here");
        // Switches the Icon
        $(this).children('i').toggleClass('fa-pencil');
        // Switches the forms
        $('.form').animate({
          height: "toggle",
          'padding-top': 'toggle',
          'padding-bottom': 'toggle',
          opacity: "toggle"
        }, "slow");
        if($('#noEmpty').length){
          $('#noEmpty').show();
        }
      });
    });
  </script>
</head>
<body>

<div class="pen-title">

  <h1>Agile Estimation</h1>
</div>

<div class="module form-module">
  <div class="toggle"><i class="fa fa-times fa-pencil"></i>
    <div class="tooltip">Click Me</div>
  </div>
  <div class="form">
    <h2>Login</h2>
    <form <%--id = "loginForm"--%> action="LoginController" method="post">
      <input <%--id = "usernameLogin--%> type="text" name="username" placeholder="Enter username" onfocus="this.placeholder = '';this.style.color='black';this.style.borderColor='lightgray'" onblur="this.placeholder = 'Enter username';this.style.color='darkgray';this.style.borderColor='lightgray'"><br>
      <input <%--id = "passwordLogin"--%> type="password" name="password" placeholder="Enter password" onfocus="this.placeholder = '';this.style.color='black';this.style.borderColor='lightgray'" onblur="this.placeholder = 'Enter username';this.style.color='darkgray';this.style.borderColor='lightgray'"><br>
      <input id = "loginButton" type="submit" name="login" value="Login">
      <c:if test="${invalidUsername != null}">
        <p id="invalidUsername">Invalid username or password!</p>
      </c:if>
      <c:if test = "${invalidPassword != null}">
        <p id = "invalidPassword">Invalid password! Please try again.</p>
      </c:if>
      <c:if test = "${invalidUsernameAndPassword != null}">
        <p id = "invalidUsernameAndPassword">Invalid username or password!</p>
      </c:if>
    </form>
  </div>
  <div class="form">
    <h2>Sign up</h2>
    <form <%--id="registerForm"--%> action="RegisterController" method="post">

      <input <%--class="registerTextField"--%> type="text" name="email" placeholder="Enter e-mail address" onfocus="this.placeholder='';this.style.color='black';this.style.borderColor='lightgrey'" onblur="this.placeholder='Enter e-mail address';this.style.color='darkgray'"><br>
      <input <%--class="registerTextField"--%> type="text" name="name" placeholder="Enter username" onfocus="this.placeholder='';this.style.color='black';this.style.borderColor='lightgrey'" onblur="this.placeholder='Enter username';this.style.color='darkgray'"><br>
      <input <%--class="registerTextField"--%> type="password" name="passwordCreate" placeholder="Enter password" onfocus="this.placeholder='';this.style.color='black';this.style.borderColor='lightgrey'" onblur="this.placeholder='Enter password';this.style.color='darkgray'">
      <%--<input class="registerTextField" type="text" name="position" placeholder="Enter role" onfocus="this.placeholder='';this.style.color='black'" onblur="this.placeholder='Enter position';this.style.color='lightgray'"><br>--%>
      <input <%--class="registerRole"--%> type="radio" name="userRole" value="Program Manager">Program Manager<br>
      <input <%--class="registerRole"--%> type="radio" name="userRole" value="Architect">Architect<br>
      <input <%--class="registerRole"--%> type="radio" name="userRole" value="Product Developer">Product Developer<br>
      <input <%--class="registerRole"--%> type="radio" name="userRole" value="QA">QA<br>
      <input <%--class="registerRole"--%> type="radio" name="userRole" value="UED">UED<br><%--this person is responsible with the GUI--%>
      <input id = "registerButton" type="submit" name="signUp" value="Create Account" disabled>
      <div class="g-recaptcha" data-callback="recaptchaCallback" data-sitekey="6LcDrR0TAAAAAI6L3RPWH5SoVDPhYVusdVKpV9Ny"></div>
      <c:if test="${errorusername != null}">
        <p id="usernameExists"> Username is taken! Try again!</p>
      </c:if>
      <c:if test="${erroremail != null}">
        <p id="emailExists"> E-mail address is already taken!</p>
      </c:if>
      <c:if test="${errorusernameandemail != null}">
        <p id="emailAndUserNameExist"> Username and email are already taken!</p>
      </c:if>
      <c:if test="${noEmpty != null}">
        <p id="noEmpty" hidden> Empty fields are not allowed!</p>
      </c:if>
      <c:if test="${errorRole != null}">
        <p id="noRole">${errorRole}</p>
      </c:if>
    </form>
  </div>
</div>
</body>
</html>
