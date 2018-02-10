<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Caterina
  Date: 4/21/2016
  Time: 4:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%-- <meta http-equiv="refresh" content="10" />--%>
    <title>${userName}</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/avengersFav.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/userStyle.css">
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.min.css" />
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.min.css" />
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <script src = "${pageContext.request.contextPath}/scripts/userHomeScript.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">${userName}</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><%--<a href="#">My Projects</a>--%>
        <div class="btn-group navbar-btn">
          <button class="btn btn-primary dropdown-toggle btn-lg" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            My Projects

            <!-- Display icon in button -->
            <span class="caret"></span>
          </button>
          <ul class="dropdown-menu">

            <!-- Unselectable header -->
            <li class="dropdown-header">Ongoing</li><br>
            <c:forEach items="${projectNames}" var="selectedProject">
              <li class="list-group-item" style="border: none"><a href="<c:url value="/UserHomeController?projectName=${selectedProject}&user=${userName}&role=${userRole}"/>">${selectedProject}</a></li><br>
            </c:forEach>
          </ul>
        </div>
      </li>
      <li class="active">
        <div class="btn-group navbar-btn" id="edit">
          <button class="btn btn-primary dropdown-toggle btn-lg" type="button" id="dropdownMenu2" data-toggle="dropdown" style="background-color: blueviolet" aria-haspopup="true" aria-expanded="true">
            Edit Account

            <!-- Display icon in button -->
            <span class="caret"></span>
          </button>
          <ul class="dropdown-menu">
            <form id="editAccount">
              <div class="form-group">
                <div class="input-group">
                <div class="input-group-addon">Username</div>
                <input id="editUsername" type="text" class="form-control" id="exampleInputName2" placeholder="John Doe">
                </div>
              </div>
              <div class="form-group">
                <div class="input-group">
                  <div class="input-group-addon">Username</div>
                <input id="editPassword" type="password" class="form-control" id="exampleInputPassword2" placeholder="Enter password">
                </div>
              </div>
              <input type="button" id = "editSubmit" class="btn btn-primary" value="Edit account">
            </form>
          </ul>
        </div>
      </li>
      <%--<li>--%>
      <c:if test="${userRole == 'Program Manager'}">
      <li class="active">
        <div class="btn-group navbar-btn" id="createProject">
          <button class="btn btn-primary dropdown-toggle btn-lg" type="button" id="dropdownMenu3" data-toggle="dropdown" style="background-color: blueviolet" aria-haspopup="true" aria-expanded="true">
            Create Project

            <!-- Display icon in button -->
            <span class="caret"></span>
          </button>
          <ul class="dropdown-menu" style="width: 8cm">
            <form id="createProjectForm" action="UserHomeController" method="post">
              <div class="form-group">
                <div class="input-group">
                  <div class="input-group-addon">Project Name</div>
                  <input name="projectName" type="text" class="form-control" placeholder="New Project">
                </div>
              </div>
              <div class="form-group">
                <div class="input-group">
                  <div class="input-group-addon">Project Type</div>
                  <input name="projectType" type="text" class="form-control" placeholder="Project type">
                </div>
              </div>
              <div class="form-group">
                <div class="input-group">
                  <div class="input-group-addon">Deadline</div>
                  <input id="datepicker" name="projectDeadline" <%--class="form-control"--%> type="text" placeholder="Deadline" style="width: 5.8cm;height:0.72cm;">
                </div>
              </div>
              <input type="hidden" id="currentUsername2" name="currentUsername" value="${userName}">
              <input type="hidden" id="currentUserrole" name="currentUserrole" value="${userRole}">
              <input type="submit" name = "createProject" class="btn btn-primary" value="Create project">
            </form>
          </ul>
        </div>
      </li>
      </c:if>

      <li>
        <div class="btn-group navbar-btn">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button">
            <span class="glyphicon glyphicon-globe"></span>
          </a>
          </button>
          <ul class="dropdown-menu">
          </ul>
        </div>
      </li>
    </ul>
  </div>
</nav>

<div id="updateResultGood" style="display:none;">

</div>
<div id="accountEdit" style="display:none">
</div>
<div id="newsFeed">
</div>
<input type="hidden" id="currentUsername" name="currentUsername" value="${userName}">
</body>
</html>
