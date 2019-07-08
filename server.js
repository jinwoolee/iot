 const admin = require('firebase-admin');
//var firebase = require('firebase/app');
//var firestore = require('firebase/firestore');
var dateFormat = require('dateformat');
var express = require('express');
var app = express();

app.use(express.json());                    //json 
app.use("/js", express.static('js'));       //정적 자료 위치(js)
app.use("/css", express.static('css'));     //정적 자료 위치(css)

app.set("view engine", "pug");      //template engine 사용 선언
app.set("views", "views");          //view 파일 기본 위치 default views 참고(https://expressjs.com/en/4x/api.html#app.set)
app.locals.pretty = true;           //템플릿 생성시 포맷 

var serviceAccount = require('./iot-dust-sensor-account.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});
var db = admin.firestore();

// var config ={
//     apiKey: "AIzaSyD-tO4rJbaC-obmkY1o8DqILR1n-dCJkJQ",
//     authDomain: "iot-dust-sensor.firebaseapp.com",
//     databaseURL: "https://iot-dust-sensor.firebaseio.com",
//     projectId: "iot-dust-sensor",
//     storageBucket: "iot-dust-sensor.appspot.com",
//     messagingSenderId: "911033078729",
//     appId: "1:911033078729:web:ed3ee15b1f737629"
//     }

// firebase.initializeApp(config);

//var db = firebase.firestore();


app.get("/", function(req, res){
    res.render("./sensorMeasure", {});
});

app.get("/sensor/measureView", function(req, res){
    res.render("./sensorMeasure", {});
});

//미세먼지 데이터 요청 json
app.get("/sensor/getMeasure", function(req, res){
    //응답 데이터 초기화    
    var data = dataInit();
    
    var now = new Date();
    
    var sdt = new Date(dateFormat(now, 'yyyy') + '-01-01T00:00:00');
    var edt = new Date(dateFormat(now, 'yyyy') + '-12-31T23:59:59');
    
    //일별
    var dustColl = db.collection('dustData')
                        .where("reg_dt", "<=", edt)
                        .where("reg_dt", ">=", sdt).get()
    .then(snapshot => {
        if (snapshot.empty) {
            console.log('No matching documents.');
            return;
        }  
        
        var thisMonth = dateFormat(now, "m");
        var today = dateFormat(now, "d");
        console.log("thisMonth : ", thisMonth);
        console.log("today : ", today);

        var aqi = 0;
        snapshot.forEach(doc => {
            
            //console.log(doc.id, '=>', doc.data());
            //월별 데이터 ( [ 월, 데이터, 월 데이터 형식])
            var m = dateFormat(doc.data().reg_dt.toDate(), "m");
            var d = dateFormat(doc.data().reg_dt.toDate(), "d");
            
            console.log("thisMonth : ", thisMonth, " / m : ", m, "thisMonth == m : ", thisMonth == m);

            data.monthly[m-1].measure = data.monthly[m-1].measure + Number(doc.data().aqi);

            //일별데이터
            if(thisMonth == m)
                data.daily[d-1].measure = data.daily[d-1].measure + Number(doc.data().aqi);
            
            //시간별
            if(today == d){
                var hour = dateFormat(doc.data().reg_dt.toDate(), "H");
                console.log("hour : ", hour);
                data.timely[hour].measure = data.timely[hour].measure + Number(doc.data().aqi);
            }
        });

        console.log("data : ", data);

        //json 응답전송
        res.status(200).json(data);
    })
    .catch(err => {
        console.log('Error getting documents', err);
    });
});

//json 입력 테스트
app.post('/sensor/measure', function(req, res){

    console.log("req.body : ", req.body );
    var measure = req.body;
    
    var dt = new Date();

    console.log("dt format : ", dateFormat(dt, "yyyymmdd_HH:MM"));
    var docRef = db.collection('test').doc( dateFormat(dt, "yyyymmdd_HH:MM"));

    measure.reg_dt =  new Date();
    measure.dt = dateFormat(dt, "yyyymmdd");
    measure.hh = dateFormat(dt, "HH");
    measure.mm = dateFormat(dt, "MM");

    docRef.set(measure);
    
    console.log("measure ", measure );

    res.send('Hello World post');
});

app.get('/sensor/measure', function(req, res){

    //http://localhost:3000/sensor/measure?sensor_id=1&measure=123.5&aqi=12.6
     console.log(req.query);

    var dt = new Date();
    var testDocRef = db.collection("dustData").doc(dateFormat(dt, "yyyymmdd_HH:MM"));

    var measure = {
            "sensor_id" : req.query.sensor_id,
            "measure" : req.query.measure,
            "aqi" : req.query.aqi,
            "reg_dt" : dt
    };
    
    console.log("measure ", measure );
    var setDoc = testDocRef.set(measure);

    res.send('Hello World');
  });
  
  app.listen(3000);

  function dataInit(){
    //응답 데이터 초기화    
    var data = {monthly : [], daily : [], timely : []};

    //월별 데이터
    for(var i = 1; i <= 12; i++)
        data.monthly.push({dt : i, measure : 0});
    
    //일별데이터
    var now = new Date();
    var lastDay = new Date(now.getYear(), now.getMonth() + 1, 0).getDate();
    for(var i = 1; i <= lastDay; i++)
        data.daily.push({dt : i, measure : 0});

    //시간별
    for(var i = 0; i <= 23; i++)
        data.timely.push({dt : i, measure : 0});

    return data;
}

