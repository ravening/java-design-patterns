package com.iluwatar.asyncrequestresponse.controllers;

import com.iluwatar.asyncrequestresponse.models.JobStatus;
import com.iluwatar.asyncrequestresponse.models.Message;
import com.iluwatar.asyncrequestresponse.models.MessageDto;
import com.iluwatar.asyncrequestresponse.services.AsyncProcessingWorkAcceptor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import static com.iluwatar.asyncrequestresponse.utils.Constants.JOB_SUBMIT_URL;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CreateJobController {
  private final AsyncProcessingWorkAcceptor asyncProcessingWorkAcceptor;
  private static final Logger logger = LoggerFactory.getLogger(CreateJobController.class);

  @PostMapping(value = "/submitjob", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Message> submitJob(@RequestBody @Validated MessageDto messageDto) throws URISyntaxException {
    if (messageDto == null || messageDto.getMessage() == null || messageDto.getMessage().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Message message = asyncProcessingWorkAcceptor.submitJob(messageDto);
    return new ResponseEntity<>(message, message.getHttpStatus());
  }
}
