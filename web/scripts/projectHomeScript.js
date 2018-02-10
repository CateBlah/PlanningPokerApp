/**
 * Created by Caterina on 4/21/2016.
 */

$(document).ready(function(){
    onLoad();
    //loadMembers();
    $('#loadForm').click(function (){
       // alert("HERE");
        $('#addMemberForm').fadeIn();
    });

    $('#addMemberButton').click(function() {
            $('#addMemberForm').fadeOut();
    });

    $('#loadForm2').click(function(){
        $('#addTaskForm').fadeIn();
    });

    $('#addTaskButton').click(function(){
        $('#addTaskForm').fadeOut();
    });

    $('#addTaskButton').click(function(){
        var userStoryName = $('#userStoryName').val();
        var userStoryDescription = $('#userStoryDescription').val();
        var userStoryPriority = $('.userStoryPriority:checked').val();
        var currentUsername = $("#currentUsername").val();
        var assigneeName = $('#assigneeName').val();
       // alert(userStoryName + " " + userStoryDescription + " " + userStoryPriority)
        $.ajax({
            type: 'POST',
            url: 'ProjectAJAXServlet',
            data: {
                userStoryName : userStoryName,
                userStoryDescription: userStoryDescription,
                userStoryPriority: userStoryPriority,
                assigneeName: assigneeName,
                action: 'addUserStory'},
            success: function(responseJson){
                $('#userStories').append("<li><a href=\"ProjectHomeController?userStoryId="+responseJson.userStoryId + "&username="+ currentUsername +"\">"+ responseJson.userStoryName + "</a></li>");
            }
        });
    });

    $("#addMemberButton").click(function(){
        var addedUserEmail = $("#addedUserEmail").val();
        $.ajax({
            type: 'POST',
            url: 'ProjectAJAXServlet',
            data: {
                addedUserEmail: addedUserEmail,
                action: 'addProjectMember'
            },
            success: function(responseJson){
              //  alert('success')
            },
            error: function(errorJson){
                $('#addMemberError').fadeIn();
                $('#addMemberError').html("<strong>Error!</strong>"+"<p>No user is associated with the e-mail address "+ addedUserEmail +"!</p>");
            }
        });
    });

    $(document).click(function(){
        $('#addMemberError').fadeOut("fast");
    });

    function onLoad() {
        var currentUsername = $("#currentUsername").val();
        var currentUserrole = $("#currentUserrole").val();
        // alert('here');
        $.ajax({
            type: 'GET',
            url: 'ProjectAJAXServlet',
            data: {
                action: 'getUserStories'
            },
            success: function (responseJson) {
                // alert('made it!');
                $('#userStories').empty();
                $.each(responseJson, function (index, userStory) { //iterate through json
                    $('#userStories').append("<li><a href=\"ProjectHomeController?userStoryId=" + userStory.userStoryId + "&username=" + currentUsername + "\">" + userStory.userStoryName + "</a></li>");

                });
            }
        });
    }

    function loadMembers(){
        var currentUserrole = $("#currentUserrole").val();
        $.ajax({
            type: 'GET',
            url: 'ProjectAJAXServlet',
            data: {
                action: 'getMembers'
            },
            success: function(responseJson){
                $.each(responseJson,function(index,member){
                    //alert(member);
                    if(currentUserrole === "Program Manager") {
                        $('#projectMembers').append("<li class=\"members list-group-item\">" + member.userName + "<button style=\"background-color:#3B5998;width: 1.1cm;margin-left:20px;float:right;\" class=\"removeM btn btn-primary\" name=\"" + member.userName + "\">" + "<span class=\"glyphicon glyphicon-trash\"></span></button></li>");
                    } else {
                        $('#projectMembers').append("<li class=\"members list-group-item\">" + member.userName + "</li>");
                    }
                });
            }
        });
    }

    $('#projectMembers').on("click", '.removeM',function(){

        $.ajax({
            type: 'POST',
            url: 'ProjectAJAXServlet',
            data: {
                action: 'removeMember',
                memberToDelete: $(this).attr("name")
            },
            success: function(myJsonResponse){
                //alert(memberToDelete);
                $("#projectMembers").empty();
                //alert(myJsonResponse + ' i want to delete this');
                loadMembers();
            }
        });
    });

    $('#loadMemberList').click(function(){
        $('#projectMembers').empty();
        loadMembers();
    });
});



/*function getUserStory(){
    $('#userStoryContent').load('userStory.jsp');
}*/



