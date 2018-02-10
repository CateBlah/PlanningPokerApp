/**
 * Created by Caterina on 4/20/2016.
 */

    $(function () {
        $("#datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    });

    /* When the user clicks on the button,
     toggle between hiding and showing the dropdown content */
    function myFunction() {
        document.getElementById("myDropdown").classList.toggle("show");
    }

// Close the dropdown if the user clicks outside of it
    window.onclick = function (e) {
        if (!e.target.matches('.dropbtn')) {

            var dropdowns = document.getElementsByClassName("dropdown-content");
            for (var d = 0; d < dropdowns.length; d++) {
                var openDropdown = dropdowns[d];
                if (openDropdown.classList.contains('show')) {
                    openDropdown.classList.remove('show');
                }
            }
        }
    }
    $(document).ready(function () {
        $('#accountSettings').click(function () {
            $('#options').fadeIn();
        });
        $('#edit').click(function () {
            $('#accountEdit').fadeIn();
            //$('#accountEdit').css("background-color","#3B5998");
            //$('#createProjectForm').css("background-color","lightgray");
            $('#createProjectForm').hide();

        });

        $('#editSubmit').click(function () {
            $('#accountEdit').fadeOut();
        });
        $(document).mouseup(function (e) {
            var container = $("#accountEdit");

            if (!container.is(e.target) // if the target of the click isn't the container...
                && container.has(e.target).length === 0) // ... nor a descendant of the container
            {
                container.hide();
                $('#createProjectForm').fadeIn();
            }
        });

        $('#editSubmit').click(function () {
            var userName = $('#editUserName').val();
            var password = $('#editPassword').val();
            $.ajax({
                type: 'POST',
                url: 'AccountSettingsController',
                data: {
                    userName: userName,
                    password: password,
                    action: 'edit'
                },
                success: function (result) {
                    $('#welcomeTag').html("Welcome, " + userName)
                    $('#updateResultGood').html(result);
                    $('#updateResultGood').fadeIn();
                }
            });
        });


$(document).ready(function() {
    onLoad();
    function onLoad(){

        var currentUserName = $("#currentUsername").val();
       // alert(currentUserName)
        $.ajax({
            type: 'GET',
            url: 'AccountSettingsController',
            data: {
                action: 'loadNewsFeed',
                currentUsername: currentUserName
            },
            success: function (responseJson) {
                //alert('here!')
                $("#newsFeed").empty();
                $.each(responseJson,function (responseJson, item) {
                    //alert('Made it!');
                    $("#newsFeed").prepend("<div class=\"newsFeedItem\">" + item  + "</div>");
                });
            }
        });
    }
    //setInterval(onLoad,8000);


});
});

