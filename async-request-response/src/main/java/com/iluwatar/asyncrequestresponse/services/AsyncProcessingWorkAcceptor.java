package com.iluwatar.asyncrequestresponse.services;

import com.iluwatar.asyncrequestresponse.models.JobStatus;
import com.iluwatar.asyncrequestresponse.models.Message;
import com.iluwatar.asyncrequestresponse.models.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.iluwatar.asyncrequestresponse.utils.Constants.getUri;

@Service
public class AsyncProcessingWorkAcceptor {
  private static final Logger logger = LoggerFactory.getLogger(AsyncProcessingWorkAcceptor.class);
  private final Lock lock = new ReentrantLock();
  private final ApplicationEventPublisher applicationEventPublisher;

  AsyncProcessingWorkAcceptor(ApplicationEventPublisher eventPublisher) {
    this.applicationEventPublisher = eventPublisher;
  }

  public Message submitJob(MessageDto msg) {
    String jobId;
    try {
      lock.lock();
      jobId = UUID.randomUUID().toString();
    } finally {
      lock.unlock();
    }

    URI location = getUri(jobId, "/status/");

    Message message = Message.builder()
            .message(msg.getMessage())
            .httpStatus(HttpStatus.ACCEPTED)
            .jobId(jobId)
            .createdAt(LocalDateTime.now())
            .statusCheckUrl(location)
            .createResource(msg.isCreateResource())
            .jobStatus(JobStatus.JOB_PENDING).build();

    applicationEventPublisher.publishEvent(message);
    logger.info("event published : {}" ,message);
    return message;
  }
}
