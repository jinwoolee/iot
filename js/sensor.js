$(document).ready(function() {
});
google.charts.load('current', {
    'packages' : [ 'line' ]
});
google.charts.setOnLoadCallback(getData);
function getData() {
    var std_ym = "";
    if (std_ym == "")
        std_ym = moment(new Date()).format("YYYY-MM-DD");
    std_ym = std_ym.replace(/-/g, "");
    console.log("std_ym : " + std_ym);
    // var param = "method=dashBoard&st_dt=" + std_ym + "&ed_dt="
    //         + moment(std_ym).add(1, "days").format("YYYYMMDD")
    //         + "&sensor_id=1";

    var param = "method=dashBoard&st_dt=20190617&ed_dt=20190618&sensor_id=1";
    console.log("param : " + param);
    $.ajax({
        method : "GET",
        //url: "./data.json",
        url : "/sensor/getMeasure",
        data : param,
        async : false,
        complete : function(msg) {
            var jsonObj = JSON.parse(msg.responseText);
            console.log(jsonObj);
            drawMonthlyChart(jsonObj.monthly);
            //drawDailyChart(jsonObj.daily);
            //drawTimelyChart(jsonObj.timely);
        }
    });
}
function drawMonthlyChart(monthlyData) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'month');
    data.addColumn('number', 'dust');
    var dataArr = [];
    for (var i = 1; i <= 12; i++)
        dataArr.push([ i.toString(), 1 ]);
    for (var i = 0; i < dataArr.length; i++) {
        for (var month = 0; month < monthlyData.length; month++) {
            console.log("i : " + i);
            if (monthlyData[month].dt == dataArr[i][0]) {
                dataArr[i][1] = monthlyData[month].measure;
                break;
            }
        }
    }
    console.log("monthly : " + dataArr);
    data.addRows(dataArr);
    var options = {
        chart : {
            title : 'dust sensor'
        },
        vAxis : {
            title : 'dust',
            ticks : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                    "11", "12" ]
        },
        width : 1200,
        height : 400
    };
    drawChart("monthly", data, options);
}
function drawDailyChart(dailyData) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'daily');
    data.addColumn('number', 'dust');
    var now = new Date();
    var lastDay = new Date(now.getYear(), now.getMonth() + 1, 0).getDate();
    var dataArr = [];
    for (var i = 1; i <= lastDay; i++)
        dataArr.push([ i.toString(), 1 ]);
    for (var i = 0; i < dataArr.length; i++) {
        for (var day = 0; day < dailyData.length; day++) {
            if (dailyData[day].dt == dataArr[i][0]) {
                dataArr[i][1] = dailyData[day].measure;
                break;
            }
        }
    }
    var options = {
        chart : {
            title : 'dust sensor'
        },
        vAxis : {
            title : 'dust',
            ticks : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                    "11", "12", "13", "14", "15", "16", "17", "18", "19",
                    "20", "21", "22", "23", "24", "25", "26", "27", "28",
                    "29", "30", "31" ]
        },
        width : 1200,
        height : 400
    };
    console.log("dailyData : " + dataArr);
    data.addRows(dataArr);
    drawChart("daily", data, options);
}
function drawTimelyChart(timelyData) {
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'time');
    data.addColumn('number', 'dust');
    var dataArr = [];
    for (var i = 0; i <= 23; i++)
        dataArr.push([ i, 1 ]);
    for (var i = 0; i < dataArr.length; i++) {
        for (var hour = 0; hour < timelyData.length; hour++) {
            if (timelyData[hour].dt == dataArr[i][0]) {
                dataArr[i][1] = timelyData[hour].measure;
                break;
            }
        }
    }
    console.log("timelyData : " + dataArr);
    data.addRows(dataArr);
    var options = {
        chart : {
            title : 'dust sensor'
        },
        hAxis : {
            title : 'dust',
// 				ticks : [ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
// 					"16", "17", "18", "19", "20", "21", "22", "23" ]
            
            ticks : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                16, 17, 18, 19, 20, 21, 22, 23 ]
        },
        width : 1200,
        height : 400
    };
    drawChart("timely", data, options);
}
function drawChart(divId, data, options) {
    var chart = new google.charts.Line(document.getElementById(divId));
    chart.draw(data, google.charts.Line.convertOptions(options));
}