package examples.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

// 레이어드 아키텍처에서 Controller가 사용하는 Beanㄷ르에 대해 설정을 한다.
// dao, service를 컴포넌트 스캔하여 찾도록 한다.
// 어노테이션으로 트랜잭션을 관리하기 위해 @EnableTransactionManagement를 설정하였다.
@Configuration
@ComponentScan(basePackages = {"examples.springmvc.dao", "examples.springmvc.service"})
@EnableTransactionManagement
@Import({ MyBatisConfig.class })// MyBatis설정을 읽어들인다.
public class ApplicationConfig {

//	@Bean
//	public DataSource dataSource() {
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/testdb?serverTimezone=UTC");
//		dataSource.setUsername("testdbadm");
//		dataSource.setPassword("testdbadm");
//		dataSource.setDefaultAutoCommit(false);
//		return dataSource;
//	}

	// 어플리케이션이 종료될때 EmbeddedDatabase의 shutdown메소드가 사용된다.
	// 어플리케이션은 kill -9 프로세스id 형태로 강제로 죽이면 안된다.
	// 우아한 종료(graceful shutdown)를 할때는 kill 프로세스id를 하면 된다. SIGTERM 으로 종료하면 graceful하게 종료되는데 kill명령의 기본값이 SIGTERM이기 때문이다.
	// 우아한 종료를 하게 되면 shutdown메소드가 실행된다.
	// H2 임베디드 Database를 실행한다. Database가 실행될 때 create-db.sql, insert-data.sql을 실행하여 데이터베이스를 초기화 하게 된다.
	@Bean(destroyMethod = "shutdown")
	public EmbeddedDatabase embeddedDatabase() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
				.setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
				.setScriptEncoding("UTF-8")
				.addScript("db/sql/create-db.sql")
				.addScript("db/sql/insert-data.sql")
				.build();
		return db;
	}

	// DataSource를 설정한다.
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(org.h2.Driver.class);
		dataSource.setUsername("sa");
		dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
		dataSource.setPassword("");
		return dataSource;
	}

	// 트랜잭션 관리자를 생성한다. 해당 Bean을 생성하면 @Transaction 애노테이션을 이용해 트랜잭션을 처리할 수 있다.
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	// h2에 내장된 웹 관리 도구를 활성화 한다. http://localhost:8082 로 접속하면 h2에 접속할 수 있다.
	// 이때 username은 "sa", url엔 "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE" 를 입력하고 암호에는 아무것도 입력하지 않고 접속을 실행하면 된다.
	@Bean(initMethod="start", destroyMethod="stop")
	public org.h2.tools.Server h2WebServer() throws Exception{
		return org.h2.tools.Server.createWebServer(
				"-web", "-webAllowOthers", "-webPort", "8082"
		);
	}


}
