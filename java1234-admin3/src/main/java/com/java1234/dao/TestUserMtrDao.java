package com.java1234.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.java1234.dao.entity.TestUserMtrEnt;

@Mapper
public interface TestUserMtrDao {

	@Select(""
			+ "SELECT T1.*\r\n"
			+ "  FROM test_user_mtr T1\r\n"
			+ " WHERE 1 = 1 ")
	List<TestUserMtrEnt> findAll();
	
	@Select(""
			+ "SELECT T1.*\r\n"
			+ "  FROM test_user_mtr T1\r\n"
			+ " WHERE 1 = 1 \r\n"
			+ "   AND T1.USER_NAME = #{userName}")
	TestUserMtrEnt findByUserName(@Param("userName") String userName);
	
	
	
	
}
