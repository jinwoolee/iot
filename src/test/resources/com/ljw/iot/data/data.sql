--SENSOR
INSERT INTO SENSOR (SENSOR_ID, SENSOR_TYPE, SENSOR_NAME, EXPLAN, REG_DT)
	VALUES (1, 'DUST', '먼지센서1', '', now());
	
		
	
--MEASURE
INSERT INTO MEASURE(MEASURE_ID, SENSOR_ID, MEASURE, REG_DT)
	VALUES(1, 1, 7.42, now());
	
INSERT INTO MEASURE(MEASURE_ID, SENSOR_ID, MEASURE, REG_DT)
	VALUES(2, 1, 1488.11, now());
	
INSERT INTO MEASURE(MEASURE_ID, SENSOR_ID, MEASURE, REG_DT)
	VALUES(3, 1, 5262.83, now());
	
INSERT INTO MEASURE(MEASURE_ID, SENSOR_ID, MEASURE, REG_DT)
	VALUES(4, 1, 1479.07, now());