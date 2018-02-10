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
  <title>${projectName}</title>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/avengersFav.png">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/projectHomeStyle.css">
  <script src="${pageContext.request.contextPath}/scripts/jquery-1.12.3.js"></script>
  <script src = "${pageContext.request.contextPath}/scripts/projectHomeScript.js"></script>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">${projectName}</a>
    </div>
    <ul class="nav navbar-nav">
      <c:if test="${currentUserrole == 'Program Manager'}">
      <li class="active">
        <div class="btn-group navbar-btn" id="loadForm">
          <button class="btn btn-primary dropdown-toggle btn-lg" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            Add Member

            <!-- Display icon in button -->
            <span class="caret"></span>
          </button>
          <ul class="dropdown-menu" style="width:7cm;">
            <form id="addMemberForm">
              <div class="form-group">
                <div class="input-group">
                  <div class="input-group-addon">E-mail</div>
                  <input id="addedUserEmail" type="text" class="form-control" name="addedUserEmail" placeholder="johndoe@yahoo.com">
                </div>
              </div>
              <input type="button" id = "addMemberButton" class="btn btn-primary" name="addMemberButton" value="Add Member">
            </form>
          </ul>
        </div>
      </li>
        <li class="active">
          <div class="btn-group navbar-btn" id="loadForm2">
            <button class="btn btn-primary dropdown-toggle btn-lg" type="button" id="dropdownMenu3" data-toggle="dropdown" style="margin-left: 0.50cm;" aria-haspopup="true" aria-expanded="true">
              Add User Story

              <!-- Display icon in button -->
              <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" style="width:7cm;margin-left:0.50cm;">
              <form id="addTaskForm">
                <div class="form-group">
                  <div class="input-group">
                    <div class="input-group-addon">Name</div>
                    <input id="userStoryName" type="text" class="form-control" name="userStoryName"  placeholder="Name">
                  </div>
                  <div class="input-group">
                    <div class="input-group-addon">Description</div>
                    <input id="userStoryDescription" type="text" class="form-control" name="userStoryDescription"  placeholder="Description">
                  </div>
                  <div class="input-group">
                    <div class="input-group-addon">Assignee</div>
                    <input id="assigneeName" type="text" class="form-control" name="assigneeName"  placeholder="Assignee name">
                  </div>
                  <div class = "col-lg-16">
                  <div class="input-group">
                    <div class="input-group-addon" style="height:28px;width:90px;">
                      <input type="radio" name="userStoryPriority" class = "userStoryPriority" value="LOW">
                    </div>
                    <div class="input-group-addon" style="width:5cm;">LOW</div>
                    </div>
                    <div class="input-group">
                    <div class="input-group-addon" style="height:28px;width:120px;">
                      <input type="radio" name="userStoryPriority" class = "userStoryPriority" value="MEDIUM">
                    </div>
                    <div class="input-group-addon" style="width: 6.20cm">MEDIUM</div>
                    </div>
                  <div class="input-group">
                  <div class="input-group-addon" style="height:28px;width:90px;">
                      <input type="radio" name="userStoryPriority" class = "userStoryPriority" value="HIGH">
                    </div>
                    <div class="input-group-addon" style="width: 5cm;">HIGH</div>
                  </div>
                  </div>
                  <input type="button" id = "addTaskButton" class="btn btn-primary" name="addTaskButton" value="Add User Story">
                  </div>
              </form>
              </ul>
          </div>
        </li>
      </c:if>
      <li class="active">
        <div class="btn-group navbar-btn" id="loadMembers">
          <button class="btn btn-primary dropdown-toggle btn-lg" type="button" id="loadMemberList" data-toggle="dropdown" style="background-color:#3B5998;margin-left: 18.62cm;width: 4.73cm;" aria-haspopup="true" aria-expanded="true">
            Team Members

            <!-- Display icon in button -->
            <span class="glyphicon glyphicon-user"></span>
          </button>
          <ul class="dropdown-menu" id="projectMembers" style="margin-left: 18.62cm;">
          </ul>
        </div>
      </li>
    </ul>
    </div>
</nav>
<div class="alert alert-danger fade in" id="addMemberError">
</div>
${currentUsername} ${currentUserrole}


<div>
  <h4>User Stories</h4>
  <ul id="userStories">
  </ul>
</div>
<div class="jumbotron">
  <p>Jumbotron test</p>
  <p>Some test</p>
</div>
<input type="hidden" value="${currentUsername}" id="currentUsername">
<input type="hidden" value="${currentUserrole}" id="currentUserrole">
</body>
</html>
