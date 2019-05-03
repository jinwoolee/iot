/* Includes ------------------------------------------------------------------*/
#include <Arduino.h>
#include "WizFi310.h"
#include "math.h"

/* Private variables ---------------------------------------------------------*/
#define POST_DATA_INTERVAL  60000  //  1000ms = 1s(sec)
#define SERIAL_WIFI  Serial3
#define SERIAL_DEBUG Serial

char ssid[] = "ddit06";    // your network SSID (name)
char pass[] = "ddit.or.kr.206";    // your network password
int status = WL_IDLE_STATUS;       // the Wifi radio's status


const char* server_dns = "192.168.206.15";
const int port = 80;
const char* post_url = "POST /sensor/measure";  // Change YOURTHINGSNAME

//dust sensor
#define PIN_DUST_SENSOR 8
unsigned long duration;
unsigned long starttime;  
#define SENSOR_INTERVAL  60000
unsigned long lowpulseoccupancy = 0;
float ratio = 0;

WiFiClient client;

/* Private function prototypes -----------------------------------------------*/

void initSensor(void);
int calculateTemp(byte sensor_pin);
int postAnalogSensor(void);
void debugHTTPResponse();
void initWizFi310(void);
void printWifiStatus(void);

void setup() {
  SERIAL_DEBUG.begin(115200);
  
  initSensor();
  initWizFi310();
  
  starttime = millis();//get the current time  
}

void loop() {
  //연결 정상시 : 센서데이터 전송
  //연결 비정상 : 연결시도
  
  if(client.connected())
    postAnalogSensor();
  else{
    SERIAL_DEBUG.println("[Net Info] connecting to server...");
    if(client.connect(server_dns, 80)){
      SERIAL_DEBUG.print("[Net Info] Connected! Sending HTTP request to ");
      SERIAL_DEBUG.println(server_dns);  
    }
    else
      SERIAL_DEBUG.println("[Net Info] Connect Failed!");
  }
}

/**
 * @brief   Post analog sensor data
 * @detail  Post to dweet analog sensor data using HTTP.
 * @param void
 * @return  connect result
 * @throws
 */
int postAnalogSensor(void) {
  
  //dust sensor
  double concentration = 0;
  duration = pulseIn(PIN_DUST_SENSOR, LOW);
  lowpulseoccupancy = lowpulseoccupancy+duration;

  if ((millis() - starttime) > SENSOR_INTERVAL){
    SERIAL_DEBUG.print("[Dust sensor Info] time interval : ");
    SERIAL_DEBUG.println(millis() - starttime);

    ratio = lowpulseoccupancy/((millis() - starttime)*10.0);  // Integer percentage 0=>100
    concentration = 1.1*pow(ratio,3)-3.8*pow(ratio,2)+520*ratio+0.62; // using spec sheet curve

    //Assume all particles are spherical, with a density of 1.65E12 µg/m3
    double densitypm25 = 1.65 * pow(10, 12);
        
    //Assume the radius of a particle in the PM2.5 channel is .44 µm
    double rpm25 = 0.44 * pow(10, -6);
        
    //Volume of a sphere = 4/3 * pi * radius^3
    double volpm25 = (4/3) * M_PI * pow(rpm25,3);
        
    // mass = density * volume
    double masspm25 = densitypm25 * volpm25;
        
    // parts/m3 =  parts/foot3 * 3531.5
    // µg/m3 = parts/m3 * mass in µg
    double concentration_ugm3 = concentration * 3531.5 * masspm25;
        



  
    SERIAL_DEBUG.print("lowpulseoccupancy = ");
    SERIAL_DEBUG.println(lowpulseoccupancy);
    SERIAL_DEBUG.print("concentration = ");
    SERIAL_DEBUG.print(concentration);
    SERIAL_DEBUG.println(" pcs/0.01cf");
    SERIAL_DEBUG.print(concentration_ugm3);
    SERIAL_DEBUG.println(" pm2.5");

    //not trusted measured data
    if(concentration < 1){
      SERIAL_DEBUG.print("no send");
      return 0;
    }

    
    String body =  "{\"sensor_id\":1, \"measure=\":" + String(concentration) +" , \"aqi\" : " + String(concentration_ugm3) + "}";
    SERIAL_DEBUG.println(body);

    String param = "sensor_id=1&measure=" + String(concentration) + "&aqi=" + String(concentration_ugm3);

	//post 방식이 잘 안된다 ㅠ_ㅠ
	//get 방식으로 일차
	//추후 post 방식으로 수정해보자 참고 url : https://github.com/bportaluri/WiFiEsp/issues/50
	//https://help.ubidots.com/connect-your-devices/connect-an-arduino-uno-wiznet-wizfi310-to-ubidots-over-http
    client.print("GET /sensor/measure?" + param + "\r\n");
    client.print("Host: 192.168.206.15\r\n");
    client.println("Content-Type: application/x-www-form-urlencoded");
    client.print("User-Agent: Arduino-Ethernet/1.0\r\n");
    //client.print("Content-Length:");
    //client.print(String(body.length()));
    client.print("\r\n");
    
    //client.print("Connection: close\r\n");
    client.print("\r\n");
    //client.print("sensor_id=1&measure=123123&aqi=123.5");
    client.print("\r\n\r\n");
    client.flush();
    client.stop();

    SERIAL_DEBUG.println("Send Request !");
    SERIAL_DEBUG.println("\n");

    lowpulseoccupancy = 0;
    starttime = millis();
 
    return 1;
  }
  return 0;
}


int dataLen(char* variable) {
  uint8_t dataLen = 0;
  for (int i = 0; i <= 250; i++) {
    if (variable[i] != '\0') {
      dataLen++;
    } else {
      break;
    }
  }
  return dataLen;
}

/**
 * @brief   Debug HTTP Response
 * @detail  After request, check HTTP respose about request
 * @param void
 * @return  void
 * @throws
 */
void debugHTTPResponse() {
  delay(500);  // If need receive delay, use this.
  while (client.connected()) {
    if (client.available())
      SERIAL_DEBUG.print((char) client.read());
  }
  SERIAL_DEBUG.println();
  SERIAL_DEBUG.println();
}

/**
 * @brief   Initialize sensor
 * @detail  Initialize each sensor
 * @param void
 * @return  void
 * @throws
 */
void initSensor(void) {
  pinMode(PIN_DUST_SENSOR,INPUT);
}

/**
 * @brief   Initialize WizFi310
 * @detail  Function to initialize WizFi310 chip to use WIFI
 * @param void
 * @return  void
 * @throws
 */
void initWizFi310() {
  SERIAL_WIFI.begin(115200);

  WiFi.init(&SERIAL_WIFI);

  // check for the presence of the shield
  if (WiFi.status() == WL_NO_SHIELD) {
    SERIAL_DEBUG.println("[WIFI] WiFi shield not present");
    // don't continue
    while (true);
  }

  // attempt to connect to WiFi network
  while (status != WL_CONNECTED) {
    SERIAL_DEBUG.print("[WIFI] Attempting to connect to WPA SSID: ");
    SERIAL_DEBUG.println(ssid);
    // Connect to WPA/WPA2 network
    status = WiFi.begin(ssid, pass);
  }

  printWifiStatus();
}

/**
 * @brief   Print Wifi Status
 * @detail  Print Wifi Status using Serial : SSID, Allocated IP Address, Signal Strength
 * @param void
 * @return  void
 * @throws
 */
void printWifiStatus() {
  // print the SSID of the network you're attached to
  SERIAL_DEBUG.print("[WIFI] SSID: ");
  SERIAL_DEBUG.println(WiFi.SSID());

  // print your WiFi shield's IP address
  IPAddress ip = WiFi.localIP();
  SERIAL_DEBUG.print("[WIFI] IP Address: ");
  SERIAL_DEBUG.println(ip);

  // print the received signal strength
  long rssi = WiFi.RSSI();
  SERIAL_DEBUG.print("[WIFI] Signal strength (RSSI):");
  SERIAL_DEBUG.print(rssi);
  SERIAL_DEBUG.println(" dBm");
}