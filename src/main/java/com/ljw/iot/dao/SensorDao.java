package com.ljw.iot.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ljw.iot.model.Sensor;

@Repository
public class SensorDao {
	
	public List<Sensor> getSensorList() {
		return new ArrayList<Sensor>();
	}
}