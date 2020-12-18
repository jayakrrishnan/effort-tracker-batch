/**
 * 
 */
package com.efforttracker.batch.entity;

import java.sql.Date;
import lombok.Data;

/**
 * @author JaY
 *
 */
@Data
public class Effort {

  private String userId;
  private Date effortDate;
  private Integer projectId;
  private Integer projectTaskHr;
  private Integer projectMeetingHr;
  private Integer trainingHr;
  private Integer vdiUnavailHr;
  private Integer leaveHr;
  private Integer otherHr;
  private Integer wfoHr;
  private Integer wfhHr;
  private Integer reworkHr;
  private Integer adhocHr;
  private String resonForNotWorking;
  private String commentForNotWorking;
  private String reasonForWfh;
  private String comments;
  private Date lastChangeTs;
}
