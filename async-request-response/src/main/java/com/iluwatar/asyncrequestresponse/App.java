package com.iluwatar.asyncrequestresponse;

import com.iluwatar.asyncrequestresponse.models.JobStatus;
import com.iluwatar.asyncrequestresponse.models.Message;
import com.iluwatar.asyncrequestresponse.models.MessageDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

import static com.iluwatar.asyncrequestresponse.utils.Constants.JOB_SUBMIT_URL;

@SpringBootApplication
@EnableAsync
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Bean
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(10);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("AsyncJob-");
    executor.initialize();
    return executor;
  }
}

@Component
@RequiredArgsConstructor
class Client {
  private final RestTemplate restTemplate = new RestTemplate();
  private static final Logger logger = LoggerFactory.getLogger(Client.class);

  @EventListener(ApplicationReadyEvent.class)
  public void ready() {
    logger.info("Submitting a new job");
    submitMessage();
    System.exit(0);
  }

  public void submitMessage() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    MessageDto messageDto = MessageDto.builder().message("Hello world").createResource(true).build();
    HttpEntity<MessageDto> request = new HttpEntity<>(messageDto, httpHeaders);
    processResponse(restTemplate.postForEntity(JOB_SUBMIT_URL, request, Message.class));
  }

  public void processResponse(ResponseEntity<Message> responseEntity) {
    if (responseEntity.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
      logger.error("Unable to create job");
      System.exit(1);
    } else {

      Message message = responseEntity.getBody();
      while (!message.getJobStatus().equals(JobStatus.JOB_COMPLETED)) {
        message = restTemplate.getForObject(message.getStatusCheckUrl(), Message.class);

        try {
          Thread.sleep(2000);
        } catch (Exception ignored) {
        }
        logger.info("waiting for job to complete");
      }

      logger.info("Job completed. Response is: {}", message);
      if (message.getHttpStatus().is3xxRedirection()) {
        logger.info("Redirecting to {} to fetch job status", message.getStatusCheckUrl());
        ResponseEntity<String> status = restTemplate.getForEntity(message.getStatusCheckUrl(), String.class);
        logger.info("Job status : {}", status.getStatusCode());
      }
    }
  }
}

