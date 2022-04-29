package com.example.sp.drobskaep.peaksbord.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.sp.drobskaep.peaksbord.interceptor.AppInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer{
	@Autowired
	private AppInterceptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {	
		// adiciona o interceptor na aplicação 
		registry.addInterceptor(interceptor);
	}
	
	// O Bean é um objeto instanciado, que instancia um DataSource
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3307/peaksbord");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter(){
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
		adapter.setPrepareConnection(true);
		// cria as tabelas
		adapter.setGenerateDdl(true);
		// informa o que foor setado no banco, no console
		adapter.setShowSql(true);
		return adapter;
	}
}