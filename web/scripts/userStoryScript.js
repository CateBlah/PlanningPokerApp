/**
 * Created by Caterina on 5/5/2016.
 */

var LINE_CHART = {
        getVisualizationData: function (jsondata) {
                var point1, dataArray = [],
                    data = new google.visualization.DataTable();
                data.addColumn('number', 'Day');
                data.addColumn({type: 'string', role: 'tooltip', 'p': {'html': true}});
                data.addColumn('number', 'Effort');
                data.addColumn({type: 'string', role: 'tooltip', 'p': {'html': true}});


                $.each(jsondata, function (i, progress)
                {
                        //alert(progress.dayNumber + " " + progress.completedPercentage);
                        point1 = "Day : " + progress.dayNumber + "";
                        point2 = "Effort : " + progress.completedPercentage + "";
                        dataArray.push([progress.dayNumber, LINE_CHART.returnToolTip(point1, point2), progress.completedPercentage, LINE_CHART.returnToolTip(point1, point2)]);
                });

                data.addRows(dataArray);

                return data;
        },

        getOptionForLineChart : function(){
                var options = {
                        animation:{
                                duration: 2000,
                                easing: 'out'
                        },

                        hAxis: {
                                baselineColor: '#ccc',
                                title: 'Day'
                        },

                        vAxis: {
                                baselineColor: '#ccc',
                                gridlineColor: '#fff',
                                title: 'Effort'
                        },

                        isStacked: false,
                        height: 300,//400,
                        width: 500,
                        backgroundColor: '#fff',
                        colors: ["#3b5998"],
                        fontName: 'roboto',
                        fontSize: 20,
                        legend: {
                                position: 'top',
                                alignment: 'end',
                                textStyle: {
                                        color: '#3b5998',
                                        fontName: 'roboto',
                                        fontSize: 14
                                }
                        },
                        tooltip: {
                                isHtml: true,
                                showColorCode: true,
                                isStacked: true
                        }
                };

                return options;
        },

        drawLineChart: function(inputData){
                var lineOptions = LINE_CHART.getOptionForLineChart(),
                    data = LINE_CHART.getVisualizationData(inputData),
                    chart =  new google.visualization.LineChart(document.getElementById("line-chart"));

                chart.draw(data,lineOptions);

                $(window).resize(function(){
                        chart.draw(data, lineOptions);
                });
        },

        returnToolTip: function(dataPoint1,dataPoint2){
                return "<div style='height:30px;width:150px;font:12px roboto;padding:15px 5px 5px 5px;border-radius:3px;'>"+
                            "<span style='color:purple;font:12px roboto;padding-right:20px;'>"+dataPoint1+
                    "</span>"+
                    "<span style='color:#3b5998;font:12px roboto;'>"+dataPoint2+"</span></div>";
        },

        getProgressData: function() {
                $.ajax({
                        url: "ProgressJsonServlet",
                        dataType: "JSON",
                        success: function(data){
                                LINE_CHART.drawLineChart(data);
                        }
                });
        }

};

google.load("visualization","1",{packages:["corechart"]});

$(function() {
        $("#datepicker").datepicker({dateFormat: 'yy-mm-dd'});
});

$(document).ready(function(){
        $('.time').timepicker( {
                hourText: 'Hours',
                minuteText: 'Minutes',
                amPmText: ['AM', 'PM'],
                timeSeparator: ':',
                showLeadingZero: false,
                nowButtonText: 'Now',
                showNowButton: true,
                closeButtonText: 'Close',
                showCloseButton: true
        });

        loadEstimations();
        function loadEstimations(){
                //var cUsername = $('#cUSername').val();
                //alert(cUsername);

                $.ajax({
                        url: 'UserStoryController',
                        method: 'GET',
                        data: {
                                action: 'getEstimations'
                        },
                        success: function(responseJson){
                                $('#estimations').empty();

                                $.each(responseJson,function(index,estimation){
                                        //alert(estimation.userName + " " + estimation.pokerPlanningValue);
                                        $('#estimations').append("<div style=\"float:left;margin-left: 0.5cm;\"><h4>"+estimation.userName +"</h4><p>"+estimation.pokerPlanningValue+"</p></div>");
                                      //  alert(estimation.userName);

                                });
                        }
                });
        }
        $('#invitation').click(function(){
               // alert("HERE!");
                var pokerSessionDate = document.getElementById("datepicker").value;
                var pokerSessionStartTime = document.getElementById("startTime").value;
                var storyPointType = $('.storyPointType:checked').val();
                var userStoryName = $("#userStoryName").val();
                $.ajax({
                        url: "UserStoryController",
                        method: "POST",
                        data: {
                                action: "invite",
                                pokerSessionDate: pokerSessionDate,
                                pokerSessionStartTime: pokerSessionStartTime,
                                storyPointType: storyPointType,
                                userStoryName: userStoryName
                        },
                        success: function(responseJson){
                                alert("Your invitation has been sent!");
                                $('#currentStoryPointType').val(storyPointType);
                               // loadStoryPointValues();
                        }
                });
        });

        $('.tshirtButton').click(function(){
                var userEstimation = $(this).attr("alt");
                var cUsername = $('#cUsername').val();

                $.ajax({
                        url: 'UserStoryController',
                        method: 'POST',
                        data: {
                                action: 'addEstimation',
                                userEstimation: userEstimation,
                                cUsername: cUsername
                        },
                        success: function(responseJson){
                                   alert('Thank you!');
                                   $('#estimations').empty();
                                   $('#tShirtValues').fadeOut();
                                //$('#estimations').append("<div><p>" + cUsername + "</p><br><p>" + userEstimation + "</p></div>");
                        }
                });
        });


       // setInterval(loadEstimations,2000);
        LINE_CHART.getProgressData();


        $('#addProgress').click(function(){
                var dayNumber = document.getElementById("dayNumber").value;
                var effortPercent = document.getElementById("effortPercent").value;
            //    alert('here');
                $.ajax({
                        url: "ProgressJsonServlet",
                        dataType: "JSON",
                        method: "POST",
                        data: {
                                action: "addProgress",
                                dayNumber: dayNumber,
                                effortPercent: effortPercent
                        },
                        success: function(jsonResponse){
                                //alert("made It" + jsonResponse.dayNumber + " " + jsonResponse.completedPercentage);
                                $("line-chart").empty();
                                LINE_CHART.getProgressData();
                        }
                });
        });

        $('#loadChart').click(function(){
              //  $('#line-graph').show();

                $('#updateChart').fadeIn();
                $('#closeChart').fadeIn();
        });

        $('#closeChart').click(function(){
                $('#line-graph').hide();
                $('#updateChart').fadeOut();
                $('#closeChart').fadeOut();
        });


        $('#planningPokerValues').css("margin-top","1cm");


        $('#loadPokerValues').click(function(){
                var pokerSessionId = $('#pokerSessionId').val();
               alert('Poker session id: ' + pokerSessionId);
                if(pokerSessionId === '0'){
                        alert('There is no ongoing poker session!');
                }else{
                        $('#planningPokerValues').fadeIn();
                }
        });
});






