package com.iluwatar.asyncrequestresponse.services;

import com.iluwatar.asyncrequestresponse.models.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncOperationStatusChecker {
  private final AsyncProcessingBackgroundWorker asyncProcessingBackgroundWorker;

  public Message getJobStatus(String jobId) {
    return asyncProcessingBackgroundWorker.getJobStatus(jobId);
  }

  public boolean checkJobCompletion(String jobId) {
    return asyncProcessingBackgroundWorker.checkIfJobCompleted(jobId);
  }
}
