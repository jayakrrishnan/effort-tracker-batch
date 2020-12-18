package com.efforttracker.batch;

import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author JaY
 *
 */
@SpringBootApplication
@Slf4j
public class EffortTrackerBatchApplication {

	public static void main(String[] args) {
	  try {
	    log.info("Starting EffortTrackerBatchApplication");
		SpringApplication.run(EffortTrackerBatchApplication.class, args);
	  }catch(Exception ex) {
	    log.error("Failed to start application: ",ex);
	  }
	}

	/**
	   * configures data source
	   * 
	   * @return {@link DataSource}
	   */
	  @Bean("datasource-et")
	  @Primary
	  @ConfigurationProperties(prefix = "spring.datasource")
	  public DataSource primaryDataSource() {
	    return DataSourceBuilder.create().build();
	  }
}
