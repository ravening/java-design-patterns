package com.iluwatar.asyncrequestresponse.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
  private String message;
  private String jobId;
  private HttpStatus httpStatus;
  private LocalDateTime createdAt;
  private LocalDateTime completedAt;
  private URI statusCheckUrl;
  private JobStatus jobStatus;
  private boolean createResource;
}
