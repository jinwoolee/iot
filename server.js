const admin = require('firebase-admin');
var express = require('express');
var app = express();

app.use(express.json());                    //json 
app.use("/js", express.static('js'));       //정적 자료 위치(js)
app.use("/css", express.static('css'));     //정적 자료 위치(css)

app.set("view engine", "pug");      //template engine 사용 선언
app.set("views", "views");          //view 파일 기본 위치 default views 참고(https://expressjs.com/en/4x/api.html#app.set)
app.locals.pretty = true;           //템플릿 생성시 포맷 

var serviceAccount = require('./iot-dust-sensor-account.json');
//test
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

var db = admin.firestore();


Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};
 
String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};

app.get("/", function(req, res){
    res.render("./sensorMeasure", {});
});

app.get("/sensor/measureView", function(req, res){
    res.render("./sensorMeasure", {});
});

//미세먼지 데이터 요청 json
app.get("/sensor/getMeasure", function(req, res){
    var monthlyData = { monthly : [
        {dt:1, measure:100},
        {dt:2, measure:200},
        {dt:3, measure:300},
        {dt:4, measure:400},
        {dt:5, measure:500},
        {dt:6, measure:600},
        {dt:7, measure:700},
        {dt:8, measure:800},
        {dt:9, measure:600},
        {dt:10, measure:400},
        {dt:11, measure:200},
        {dt:12, measure:300} ] };
        res.status(200).json(monthlyData);
});

app.get('/sensor/measure', function(req, res){

    //http://localhost:3000/sensor/measure?sensor_id=1&measure=123.5&aqi=12.6
     console.log(req.query);

    var dt = new Date();

    var yyyyMMddDocRef = db.collection('dustMeasure').doc(dt.format("yyyyMMdd"));
    var hDocRef = yyyyMMddDocRef.collection(dt.format("HH")).doc(dt.format("mm"));


    var measure = {
            "sensor_id" : req.query.sensor_id,
            "measure" : req.query.measure,
            "aqi" : req.query.aqi,
            "reg_dt" : new Date(),
    };
    
    console.log("measure ", measure );
    var setDoc = hDocRef.set(measure);

    res.send('Hello World');
  });
  
  app.listen(3000);

