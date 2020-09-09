package com.iluwatar.asyncrequestresponse.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class Constants {
  public static final String JOB_SUBMIT_URL = "http://localhost:8082/api/submitjob";
  public static final String REDIRECT_URL = "http://localhost:8082/api/redirect/";

  public static URI getUri(String jobId, String path) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
            .path(path + "{jobId}")
            .buildAndExpand(jobId)
            .toUri();
  }
}
