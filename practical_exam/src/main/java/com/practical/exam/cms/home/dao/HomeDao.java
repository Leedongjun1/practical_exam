package com.practical.exam.cms.home.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomeDao {
	
	Integer passPercent(String userId);
	
	List<Map<String, Object>> barChart(String userId);

}
