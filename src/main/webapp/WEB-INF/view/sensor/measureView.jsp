<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" type='text/css'>
<link rel="stylesheet" href="/bootstrapDatepicker/css/bootstrap-datepicker3.min.css" type='text/css'>
<link rel='stylesheet' href='/js/nwagon/Nwagon.css' type='text/css'>
<style>
.tooltip {
	filter: alpha(opacity = 1) !important;
	opacity: 1 !important;
}
</style>

<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-1 col-md-1"></div>
			<div class="col-sm-10 col-md-10 main">
				<h1 class="page-header">Dashboard</h1>
				<div class="input-group date">
					<input id="datepicker" type="text" class="form-control" readonly><span
						class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
				</div>

				<div class="row placeholders">
					<div class="col-xs-12 col-sm-4 placeholder">
						<h4>월간</h4>
						<div class="chart" id="monthly"></div>
					</div>

					<div class="col-xs-12 col-sm-4 placeholder">
						<h4>일간</h4>
						<div class="chart" id="daily"></div>
					</div>

					<div class="col-xs-12 col-sm-4 placeholder">
						<h4>시간별</h4>
						<div class="chart" id="timely"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-1 col-md-1"></div>
		</div>
		<div class="row">
			<div class="col-sm-1 col-md-1"></div>
			<div class="col-sm-10 col-md-10">단위 : pcs/283ml(=0.01cf) particle size : 1μm</div>
			<div class="col-sm-1 col-md-1"></div>
		</div>
	</div>
</body>

<script src="/js/jquery-3.1.1.js"></script>
<script src="/js/moment.js"></script>
<script src="/bootstrap/js/bootstrap.js"></script>
<script src="/bootstrapDatepicker/js/bootstrap-datepicker.min.js"></script>
<script src="/js/nwagon/Nwagon.js"></script>
<script>
	$(document).ready(function() {
		var std_ym = "";

		if (std_ym == "")
			std_ym = moment(new Date()).format("YYYY-MM-DD");
		dashBoard.set("std_ym", std_ym);

		setDatepicker();

		setDashBoard();
	});

	var dashBoard = {
			options : {
					'legend' : {
						names : [ '00', '01', '02', '03', '04', '05', '06', '07', '08',
								'09', '10', '11', '12', '13', '14', '15', '16', '17',
								'18', '19', '20', '21', '22', '23' ]
					},
					'dataset' : {
						title : 'Playing time per day',
						/*values: [[56,76], [58,66], [60,62], [58,70], [85, 76], [86,83], [82, 73], [77,66], [87,66], [49,56], [58,76], [85, 76], [56,83], [56, 83], [45, 34]],
						colorset: ['#0072b2', '#cc79a7'],
						fields:['Company A', 'Company B']*/

						/*values: [[56], [58], [60], [70], [76], [83], [73], [66], [66], [56], [76], [76], [83], [83], [34], ],*/
						colorset : [ '#0072b2' ],
						fields : [ '' ]

					},
					'chartDiv' : 'timely',
					'chartType' : 'line',
					//'leftOffsetValue': 100,
					//'bottomOffsetValue': 60,
					'chartSize' : {
						width : 600,
						height : 400
					},
					'minValue' : 0,
					'maxValue' : 8000,
					'increment' : 500
				/*,
							'isGuideLineNeeded' : true //default set to false*/
				},
				
		property : {},

		set : function(key, val) {
			this.property[key] = val;
		},
		get : function(key, val) {
			return this.property[key];
		},
		
		makeTimelyNames : function(){
			this.options.legend.names =  [ '00', '01', '02', '03', '04', '05', '06', '07', '08',
				'09', '10', '11', '12', '13', '14', '15', '16', '17',
				'18', '19', '20', '21', '22', '23' ]; 
		},
		
		makeDailyNames : function(){
			
			var lastDate		=	moment(this.get("std_ym")).endOf('month').date();
			var daysArray	=	new Array();
			for(var i = 1; i <= lastDate; i++)
				daysArray.push(i);
			
			this.options.legend.names =  daysArray; 
		},
		makeMonthlyNames : function(){
			this.options.legend.names =  [ '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']; 
		}
		
	}

	//datepicker 설정
	function setDatepicker() {
		console.log(dashBoard.get("std_ym"));
		$("#datepicker").datepicker({
			format : "yyyy-mm-dd",
			autoclose : true,
			language : "ko"
		});

		$('#datepicker').datepicker('update', dashBoard.get("std_ym"));

		$('#datepicker').datepicker().on("changeDate", function(e) {
			dashBoard.set("std_ym", moment(e.date).format("YYYYMMDD"));
			setDashBoard();
		});
	}

	//dashboard 
	function setDashBoard() {

		console.log("method=timely&st_dt="
				+ dashBoard.get("std_ym").replace(/-/g, "")
				+ "&ed_dt="
				+ moment(dashBoard.get("std_ym")).add(1, "days").format(
						"YYYYMMDD") + "&sensor_id=1");
		$.ajax({
			method : "GET",
			//url: "./data.json",
			url : "/sensor/getMeasure",
			data : "method=dashBoard&st_dt="
					+ dashBoard.get("std_ym").replace(/-/g, "")
					+ "&ed_dt="
					+ moment(dashBoard.get("std_ym")).add(1, "days").format(
							"YYYYMMDD") + "&sensor_id=1",
			async : false,
			complete : function(msg) {

				var jsonObj = JSON.parse(msg.responseText);
				console.log(jsonObj);

				setTimelyDashboard(jsonObj.timely);
				setDailyDashboard(jsonObj.daily);
				setMonthlyDashboard(jsonObj.monthly);
				removeChartFileds();
			}
		});
	}

	function setMonthlyDashboard(monthly) {
		$("#monthly").empty();
		
		dashBoard.makeMonthlyNames();
		var arr = new Array();
		var idx = 0;
		var monthIndex = dashBoard.options.legend.names.length;
		for (var i = 0; i <= monthIndex && idx <= monthIndex; i++) {

			var measureObj = monthly[idx];
			var mArr = new Array();

			if (measureObj == undefined)
				;
			else if (dashBoard.options.legend.names[i] == measureObj.dt) {
				mArr.push(measureObj.measure);
				idx++;
			} else
				mArr.push(0);

			arr.push(mArr);
		}
		dashBoard.options.dataset.values = arr;
		
		dashBoard.options.chartDiv = "monthly";
		Nwagon.chart(dashBoard.options);
	}

	function setTimelyDashboard(timely) {
		var values = "";
		var idx = 0;

		dashBoard.makeTimelyNames();
		var arr = new Array();
		var timeIndex =  dashBoard.options.legend.names.length;
		for (var i = 0; i < timeIndex && idx <= timeIndex; i++) {

			var measureObj = timely[idx];
			var mArr = new Array();

			if (measureObj == undefined)
				;
			else if (dashBoard.options.legend.names[i] == measureObj.dt) {
				mArr.push(measureObj.measure);
				idx++;
			} else
				mArr.push(0);

			arr.push(mArr);
		}
		dashBoard.options.dataset.values = arr;
		
		$("#timely").empty();

		dashBoard.options.chartDiv = "timely";
		Nwagon.chart(dashBoard.options);
	}

	function setDailyDashboard(daily) {
		$("#daily").empty();
		
		dashBoard.makeDailyNames();
		var arr = new Array();
		var idx = 0;
		var dayIndex = dashBoard.options.legend.names.length;
		for (var i = 0; i < dayIndex && idx < dayIndex; i++) {

			var measureObj = daily[idx];
			var mArr = new Array();

			if (measureObj == undefined)
				;
			else if (dashBoard.options.legend.names[i] == measureObj.dt) {
				mArr.push(measureObj.measure);
				idx++;
			} else
				mArr.push(0);

			arr.push(mArr);
		}
		dashBoard.options.dataset.values = arr;
		
		dashBoard.options.chartDiv = "daily";
		Nwagon.chart(dashBoard.options);
	}

	//nwagon chart filed 제거
	function removeChartFileds() {

		$(".chart").find(".fields").remove()
	}
</script>
</html>