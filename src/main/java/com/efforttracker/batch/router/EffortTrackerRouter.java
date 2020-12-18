/**
 * 
 */
package com.efforttracker.batch.router;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.efforttracker.batch.bean.EffortTrackerBean;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JaY
 *
 */
@Component
@Slf4j
public class EffortTrackerRouter extends RouteBuilder{

  @Value("${cron-schedule}")
  private String cronTimer;
  
  @Autowired
  private EffortTrackerBean effortBean;
  
  @Override
  public void configure() throws Exception {
    
    onException(Exception.class)
    .handled(true)
    .log(LoggingLevel.ERROR,log,"Error on Route: ${exception.stacktrace}")
    .end();
    
    from(cronTimer).routeId("cron_schedule_route")
    .log(LoggingLevel.INFO,log,"Cron schedule STARTED")
    .to("sql:SELECT user_id,effort_date,project_id,project_task_hr,project_meeting_hr,training_hr," + 
        "vdi_unavail_hr,leave_hr,other_hr,wfo_hr,wfh_hr,rework_hr,adhoc_hr,reason_for_not_working," + 
        "comment_for_not_working,reason_for_wfh,comments,last_change_ts" + 
        " FROM daily_effort?dataSource=datasource-et&outputType=SelectList&outputClass=com.efforttracker.batch.entity.Effort")
    .log(LoggingLevel.INFO,log,"${body}")
    .split().body().parallelProcessing()
    .to("direct:KAFKA_ROUTE");
    
    from("direct:KAFKA_ROUTE").routeId("kafka_producer_route")
    .bean(effortBean,"buildKafkaMessage")
    .setHeader(KafkaConstants.KAFKA_DEFAULT_DESERIALIZER,constant("org.apache.kafka.commom.serialization.StringSerializer"))
    .setHeader("content-type",constant("application/json"))
    .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
    //.to("kafka:effortTracker?brokers=localhost:9092")
    .to("http4:localhost:8080/student/save")
    .log(LoggingLevel.INFO,log,"KAFKA producer route END")
    .end();
    
  }

}
