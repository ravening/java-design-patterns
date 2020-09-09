package com.iluwatar.asyncrequestresponse.services;

import com.iluwatar.asyncrequestresponse.models.JobStatus;
import com.iluwatar.asyncrequestresponse.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.iluwatar.asyncrequestresponse.utils.Constants.REDIRECT_URL;

@Component
public class AsyncProcessingBackgroundWorker {
  private static final Logger logger = LoggerFactory.getLogger(AsyncProcessingBackgroundWorker.class);
  private final Map<String, Message> messageMap = new ConcurrentHashMap<>();
  private final Set<String> completedJobs = new HashSet<>();

  @Async
  @EventListener
  public void handleJob(Message message) throws URISyntaxException {
    logger.info("Received message {}", message);
    messageMap.put(message.getJobId(), message);
    processJob(message.getJobId());
  }

  @Async
  public void processJob(String jobId) throws URISyntaxException {
    // Sleep for 10 seconds to imitate long running job
    Random random = new Random(10);
    int sleepTime = random.nextInt();
    logger.info("sleeping for {}", sleepTime);
    try {
      Thread.sleep(10000);
    } catch (Exception e) {}

    Message message = messageMap.get(jobId);
    message.setCompletedAt(LocalDateTime.now());
    message.setJobStatus(JobStatus.JOB_COMPLETED);
    message.setHttpStatus(message.isCreateResource() ? HttpStatus.FOUND : HttpStatus.OK);
    message.setStatusCheckUrl(message.isCreateResource() ? new URI(REDIRECT_URL + jobId) : message.getStatusCheckUrl());
    messageMap.put(jobId, message);
    completedJobs.add(jobId);
  }

  public Message getJobStatus(String jobId) {
    return messageMap.getOrDefault(jobId, null);
  }

  public boolean checkIfJobCompleted(String jobId) {
    return completedJobs.contains(jobId);
  }
}
