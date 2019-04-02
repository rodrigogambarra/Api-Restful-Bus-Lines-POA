package com.provatecnica.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.provatecnica.repository",
	entityManagerFactoryRef = "entityManagerFactory",
	transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class JpaConfiguration {

	@Value("${datasource.provatecnica.maxPoolSize:2}")
	private int maxPoolSize;

	@Value("${datasource.provatecnica.hibernate.dialect}")
	private String dialect;

	@Value("${datasource.provatecnica.hibernate.hbm2ddl.method}")
	private String method;

	@Value("${datasource.provatecnica.hibernate.show_sql}")
	private Boolean showSql;

	@Value("${datasource.provatecnica.hibernate.format_sql}")
	private Boolean formatSql;

	@Value("${datasource.provatecnica.defaultSchema}")
	private String defaultSchema;

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource.provatecnica")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource dataSource() {
		DataSourceProperties dataSourceProperties = dataSourceProperties();
		HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
			.create(dataSourceProperties.getClassLoader())
			.driverClassName(dataSourceProperties.getDriverClassName())
			.url(dataSourceProperties.getUrl())
			.username(dataSourceProperties.getUsername())
			.password(dataSourceProperties.getPassword())
			.type(HikariDataSource.class)
			.build();
		dataSource.setMaximumPoolSize(this.maxPoolSize);
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[]{"com.provatecnica.model"});
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(jpaProperties());
		return factoryBean;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		return hibernateJpaVendorAdapter;
	}

	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", this.dialect);
		properties.put("hibernate.hbm2ddl.auto", this.method);
		properties.put("hibernate.show_sql", this.showSql);
		properties.put("hibernate.format_sql", this.formatSql);
		if (StringUtils.isNotEmpty(this.defaultSchema)) {
			properties.put("hibernate.default_schema", this.defaultSchema);
		}
		return properties;
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}
}
