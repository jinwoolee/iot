package com.ljw.common.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

@Repository
public class CodeDao {

	/**
	  * @FileName : CodeDao.java
	  * @Project : iot
	  * @Date : 2017. 1. 20.
	  * @작성자 : jw
	  * @변경이력 :
	  * @return
	  * @프로그램 설명 :
	  */
	public List<Map<String, String>> getCode() {
		List<Map<String, String>> codeList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new TreeMap<String, String>();
		map.put("test", "testMessage");
		
		codeList.add(map);
		
		return codeList;
	}	
}