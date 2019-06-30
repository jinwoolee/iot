$(document).ready(function() {
    google.charts.load('current', {
        'packages' : [ 'line' ]
    });
    google.charts.setOnLoadCallback(getData);
});

function getData() {
    var std_ym = "";
    if (std_ym == "")
        std_ym = moment(new Date()).format("YYYY-MM-DD");
    std_ym = std_ym.replace(/-/g, "");
    console.log("std_ym : " + std_ym);
    
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
            drawDailyChart(jsonObj.daily);
            drawTimelyChart(jsonObj.timely);
        }
    });
}
function drawMonthlyChart(monthlyData) {
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'month');
    data.addColumn('number', 'dust');

    var dataArr = [];
    monthlyData.forEach(element => {
        dataArr.push([ element.dt, element.measure]);
    });
    
    console.log("dataArr : ", dataArr);

    data.addRows(dataArr);
    var options = {
        chart : {
            title : 'dust sensor'
        },
        vAxis : {
            title : 'dust',
            
        },
        hAxis : { 
            title : 'month2',
            ticks : data.getDistinctValues(0),
            viewWindow : {
                min : 1,
                max : 12
            },
            showTextEvery : 1
        },
        width : 1300,
        height : 400
    };
    drawChart("monthly", data, options);
}
function drawDailyChart(dailyData) {
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'daily');
    data.addColumn('number', 'dust');
    var now = new Date();
    var lastDay = new Date(now.getYear(), now.getMonth() + 1, 0).getDate();
    var dataArr = [];
    dailyData.forEach(data => {
        dataArr.push([data.dt, data.measure]);
    });

    console.log("daily dataArr", dataArr);

    var options = {
        chart : {
            title : 'dust sensor'
        },
        vAxis : {
            title : 'dust',
            viewWindow : {
                min : 1
            }
        },
        hAxis : {
            title : 'day',
            ticks : data.getDistinctValues[0],
            viewWindow : {
                min : 1,
                max : 31
            }
        },
        width : 1300,
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
    timelyData.forEach( data => {
        dataArr.push([data.dt, data.measure]);
    });
    
    console.log("timelyData : " + dataArr);
    data.addRows(dataArr);
    var options = {
        chart : {
            title : 'dust sensor'
        },
        hAxis : {
            title : 'time',
            ticks : data.getDistinctValues[0],
            viewWindow : {
                min : 0,
                max : 23
            }
        },
        width : 1300,
        height : 400
    };
    drawChart("timely", data, options);
}
function drawChart(divId, data, options) {
    var chart = new google.charts.Line(document.getElementById(divId));
    chart.draw(data, google.charts.Line.convertOptions(options));
}