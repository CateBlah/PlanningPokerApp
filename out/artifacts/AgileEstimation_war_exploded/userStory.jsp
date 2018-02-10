<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Caterina
  Date: 4/27/2016
  Time: 6:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>${userStoryName}</title>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/avengersFav.png">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/userStoryStyle.css">
  <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/jquery-ui-1.10.0.custom.min.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/jquery.ui.timepicker.css" type="text/css" />

    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

  <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.ui.core.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.ui.widget.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.ui.tabs.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.ui.position.min.js"></script>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.ui.timepicker.js"></script>
  <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
  <script src = "${pageContext.request.contextPath}/scripts/userStoryScript.js"></script>
</head>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">${userStoryName}</a>
        </div>
        <ul class="navbar navbar-nav" style="list-style: none">
            <c:if test="${crole == 'Program Manager'}">
            <li class="active">
                <div class="btn-group navbar-btn" id="createProject">
                    <button class="btn btn-primary dropdown-toggle btn-lg" type="button" id="dropdownMenu3" data-toggle="dropdown" style="background-color: blueviolet" aria-haspopup="true" aria-expanded="true">
                        Create Poker Session

                        <!-- Display icon in button -->
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" style="width: 8cm;height:5.18cm;">
                        <form id="inviteForm" action="UserHomeController" method="post">
                            <div class="form-group">
                                <div class="input-group">
                                    <div class="input-group-addon">Date</div>
                                    <input type="text" id="datepicker" name="pokerDate" placeholder="Enter date of poker" onfocus="this.placeholder=''" onblur="this.placeholder='Enter date of poker planning'" style="width: 6.5cm;height:0.73cm;">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <div class="input-group-addon">Time</div>
                                    <input type="text" id="startTime" name = "startTime" placeholder="Enter time(hh:mm)" onfocus="this.placeholder=''" onblur="this.placeholder='Enter time(hh:mm)'" style="width:6.42cm;height:0.73cm;"><br>
                                </div>

                            <div class = "col-lg-16">
                                <div class="input-group">
                                    <div class="input-group-addon" style="height:28px;width:90px;">
                                        <input type="radio" name="userStoryPriority" class = "userStoryPriority" value="Classic">
                                    </div>
                                    <div class="input-group-addon" style="width:6.20cm;">Classic</div>
                                </div>
                                <div class="input-group">
                                    <div class="input-group-addon" style="height:28px;width:120px;">
                                        <input type="radio" name="storyPointType" class = "storyPointType" value="Fibonacci">
                                    </div>
                                    <div class="input-group-addon" style="width: 6.20cm">Fibonacci</div>
                                </div>
                                <div class="input-group">
                                    <div class="input-group-addon" style="height:28px;width:90px;">
                                        <input type="radio" name="storyPointType" class = "storyPointType" value="T-Shirt">
                                    </div>
                                    <div class="input-group-addon" style="width: 6.20cm;">Tee Shirt</div>
                                </div>
                                </div>
                            <input type="button" id="invitation" class="btn btn-primary" value="Invite">
                            </div>
                        </form>
                        </ul>
                    </div>
            </li>
            </c:if>
            <c:if test="${currentUserIsAssignee == 'true'}">
                <li class="active">
                    <div class="btn-group navbar-btn" id="loadForm">
                        <button class="btn btn-primary dropdown-toggle btn-lg" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                            Update Progress Graph

                            <!-- Display icon in button -->
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" style="width:7cm;">
                            <form id="updateChart">
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">Day</div>
                                        <input id="dayNumber" type="number" class="form-control"><br>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">Effort(?)</div>
                                        <input id="effortPercent" type="number" class="form-control"><br>
                                    </div>
                                </div>
                                <input type="button" id = "addProgress" class="btn btn-primary" name="addProgressButton" value="Update Progress">
                            </form>
                        </ul>
                    </div>
                </li>
            </c:if>
            <c:if test="${crole != 'Program Manager'}">
                <li class = "active">
                    <div class="btn-group navbar-btn">
                        <button class="btn btn-primary btn-lg" type="button" id="loadPokerValues" style="background-color: blueviolet" aria-haspopup="true" aria-expanded="true">
                            Estimate User Story
                        </button>
                    </div>
                </li>
            </c:if>
        </ul>
    </div>
</nav>
<c:if test="${crole == 'Program Manager'}">
   ${userStoryDescription} ${crole} ${cusername}
  </c:if>

  <div id="line-chart"></div>

 <%-- <button id="loadPokerValues">Load Poker Values</button>--%>
  <c:if test="${crole != 'Program Manager'}">
      <div id="planningPokerValues" style="display:none">
    <c:if test="${storyPointType == 'T-Shirt'}">

          <input type="button" class="tshirtButton" style="background-image: url(${pageContext.request.contextPath}/resources/planning-poker-tshirt-0.png);margin-left:0.25cm;border-radius: 5px;width: 1.5cm;height: 1.8cm;background-size: 52px;background-repeat: no-repeat;" alt="0">
          <input type="button" class="tshirtButton" alt="extra small" style="width: 1.6cm;margin-left:0.25cm;height: 1.95cm;background-size: cover;background-image: url(${pageContext.request.contextPath}/resources/planning-poker-tshirt-xs.png);">
          <input type="button" class="tshirtButton" alt="small" style="border-radius: 5px;height: 2.10cm;width: 2cm;margin-left:0.25cm;background-size: cover;background-image: url(${pageContext.request.contextPath}/resources/planning-poker-tshirt-s.png);">
          <input type="button" class="tshirtButton" alt="medium" style="border-radius: 5px;height: 2.20cm;width: 2cm;margin-left:0.25cm;background-size: cover;;background-image: url(${pageContext.request.contextPath}/resources/planning-poker-tshirt-m.png);">
          <input type="button" class="tshirtButton" alt="large" style="border-radius: 5px;height: 2.40cm;width: 2cm;margin-left:0.25cm;background-size: cover;background-image: url(${pageContext.request.contextPath}/resources/planning-poker-tshirt-l.png);"><br>
          <input type="button" class="tshirtButton" alt="extra large" style="border-radius: 5px;height: 2.50cm;margin-top: 0.5cm;margin-left:0.25cm;width: 2cm;background-size: cover;background-image: url(${pageContext.request.contextPath}/resources/planning-poker-tshirt-xl.png);">
          <input type="button" class="tshirtButton" alt="2XXL" style="border-radius: 5px; height: 2.65cm;width: 2cm;margin-top: 0.5cm;margin-left:0.25cm;background-size: cover;background-image: url(${pageContext.request.contextPath}/resources/planning-poker-tshirt-2xl.png);">
          <input type="button" class="tshirtButton" alt="3XXL" style="border-radius: 5px; height: 2.75cm;width: 2cm;margin-top: 0.5cm;margin-left:0.25cm;background-size: cover; background-image: url(${pageContext.request.contextPath}/resources/planning-poker-tshirt-3xl.png);">
          <input type="button" class="tshirtButton" alt="4XXL" style="border-radius: 5px; height: 2.90cm;width: 2cm;margin-top: 0.5cm;margin-left:0.25cm;background-size: cover; background-image: url(${pageContext.request.contextPath}/resources/planning-poker-tshirt-4xl.png);">
          <input type="button" class="tshirtButton" alt="5XXL" style="border-radius: 5px; height: 3cm;width: 2cm;margin-top: 0.5cm;margin-left:0.25cm;background-size: cover; background-image: url(${pageContext.request.contextPath}/resources/planning-poker-cards-template-5xl.png);"><br>
          <input type="button" class="tshirtButton" alt="6XXL" style="border-radius: 5px; border-radius: 5px; height: 3.15cm;width: 2cm;margin-top: 0.5cm;margin-left:0.25cm;background-size: cover;background-image: url(${pageContext.request.contextPath}/resources/planning-poker-cards-template-6xl.png);">
          <input type="button" class="tshirtButton" alt="not sure" style="border-radius: 5px; height: 3.15cm;width: 2cm;margin-top: 0.5cm;margin-left:0.25cm;background-size: cover; background-image: url(${pageContext.request.contextPath}/resources/planning-poker-cards-template-ns.png);">
          <input type="button" class="tshirtButton" alt="not available" style="border-radius: 5px; height: 3.15cm;width: 2cm;margin-top: 0.5cm;margin-left:0.25cm;background-size: cover; background-image: url(${pageContext.request.contextPath}/resources/planning-poker-cards-template-na.png);">
          <input type="button" class="tshirtButton" alt="coffee" style="border-radius: 5px; height: 3.15cm;width: 2cm;margin-top: 0.5cm;margin-left:0.25cm;background-size: cover; background-image: url(${pageContext.request.contextPath}/resources/planning-poker-cards-template-cb.png);">

    </c:if>
    <c:if test="${storyPointType == 'Classic'}">
        <div id="classicValues" style="display:none">

        </div>
    </c:if>
      </div>
  </c:if>

  <c:if test="${crole == 'Program Manager'}">
    <fieldset><legend style="width:8cm">Members' Estimations</legend>
    <div id="estimations"></div></fieldset>
  </c:if>

    <input type="hidden" name="userStoryName" value="${userStoryName}" id="userStoryName">
    <input type="hidden" name="currentStoryPointType" id="currentStoryPointType" value="${currentStoryPointType}">
    <input type="hidden" id = "cUsername" name="cUsername" value="${cusername}">
    <input type="hidden" id="cRole" name="cRole" value="${crole}">
    <input type="hidden" id="assigneeName" name="assigneeName" value="${assignee}">
    <input type="hidden" id="pokerSessionId" name="pokerSessionId" value="${pokerSessionId}">
${userStoryName} ${pokerSessionId} ${assignee}
</body>
</html>

