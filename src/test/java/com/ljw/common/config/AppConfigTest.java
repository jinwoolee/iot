package com.ljw.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.web.bind.annotation.ControllerAdvice;

@MapperScan("com.ljw.iot.dao")
@ComponentScan(basePackages = {"com.ljw.common"}, useDefaultFilters = false, 
								includeFilters = {@Filter(Repository.class),
												  @Filter(Service.class),
												  @Filter(Controller.class),
												  @Filter(ControllerAdvice.class)})
@Configuration
@PropertySource(value={"classpath:com/ljw/common/config/dbTest.properties"})
public class AppConfigTest implements TransactionManagementConfigurer  {

	@Autowired
    private Environment env;
	
	@Bean
	public DataSource dataSource() {
		/*return new EmbeddedDatabaseBuilder()
				.setName("sensordb") // DB 이름 설정
				.setType(EmbeddedDatabaseType.H2) // DB 종류 설정
				.addScript("com/ljw/iot/data/schema.sql") // 스키마 스크립트 추가
				.addScript("com/ljw/iot/data/data.sql") // 데이터 스크립트 추가
				.build();
				*/
		
		BasicDataSource ds = new BasicDataSource();
		
		ds.setDriverClassName(env.getProperty("db.driver"));
		ds.setUrl(env.getProperty("db.url"));
		ds.setUsername(env.getProperty("db.user"));
		ds.setPassword(env.getProperty("db.password"));
		return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setConfigLocation(new ClassPathResource("com/ljw/iot/dao/alias.xml"));
		return sessionFactory.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception{
		return new SqlSessionTemplate(sqlSessionFactory());
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager () {
		return transactionManager(); // reference the existing @Bean method above
	}
}