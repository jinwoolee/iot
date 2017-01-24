package com.ljw.common.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ljw.iot.config.AppConfigTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfigTest.class})
public class CodeDaoTest {
	Logger logger = LoggerFactory.getLogger(CodeDaoTest.class);
	
	@Autowired
	private CodeDao codeDao;
	
	//getCode test
	@Test
	public void testGetCode() {
		//given
		
		//when
		List<Map<String, String>> codeList = codeDao.getCode();
		for(Map<String, String> map : codeList)
			logger.debug(map.toString());
		
		//then
		assertTrue(codeList.size() > 0);
	}
}