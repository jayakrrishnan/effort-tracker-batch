/**
 * 
 */
package com.efforttracker.batch.bean;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import com.efforttracker.batch.entity.Effort;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JaY
 *
 */
@Component
@Slf4j
public class EffortTrackerBean {

  public void buildKafkaMessage(Exchange exchange, @Body Effort effort) {
    log.info("list:",effort.toString());
  }
}
