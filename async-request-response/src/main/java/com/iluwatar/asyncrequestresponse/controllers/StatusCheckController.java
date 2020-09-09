package com.iluwatar.asyncrequestresponse.controllers;

import com.iluwatar.asyncrequestresponse.models.Message;
import com.iluwatar.asyncrequestresponse.services.AsyncOperationStatusChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatusCheckController {
  private final AsyncOperationStatusChecker statusChecker;

  @GetMapping("/submitjob/status/{jobid}")
  public Message getJobStatus(@PathVariable("jobid") String jobid) {
    return statusChecker.getJobStatus(jobid);
  }

  @GetMapping("/redirect/{jobid}")
  public ResponseEntity<String> getRedirectedJobStatus(@PathVariable("jobid") String jobid) {
    HttpStatus status = statusChecker.checkJobCompletion(jobid) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    return ResponseEntity.status(status).build();
  }
}
