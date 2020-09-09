package com.iluwatar.asyncrequestresponse.controllers;

import com.iluwatar.asyncrequestresponse.models.Message;
import com.iluwatar.asyncrequestresponse.models.MessageDto;
import com.iluwatar.asyncrequestresponse.services.AsyncProcessingWorkAcceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CreateJobController {
  private final AsyncProcessingWorkAcceptor asyncProcessingWorkAcceptor;

  @PostMapping(value = "/submitjob", consumes = "application/json")
  @ResponseBody
  public ResponseEntity<Message> submitJob(@RequestBody @Validated MessageDto messageDto) {
    if (messageDto == null || messageDto.getMessage() == null || messageDto.getMessage().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Message message = asyncProcessingWorkAcceptor.submitJob(messageDto);
    return new ResponseEntity<>(message, message.getHttpStatus());
  }
}
