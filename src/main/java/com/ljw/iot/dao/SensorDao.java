package com.ljw.iot.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ljw.iot.model.Sensor;

@Repository
public class SensorDao {
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public List<Sensor> getSensorList() {
		return sqlSessionTemplate.selectList("getSensorList");
	}
}