package com.iluwatar.asyncrequestresponse;

import com.iluwatar.asyncrequestresponse.controllers.CreateJobController;
import com.iluwatar.asyncrequestresponse.models.Message;
import com.iluwatar.asyncrequestresponse.models.MessageDto;
import com.iluwatar.asyncrequestresponse.services.AsyncProcessingWorkAcceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.iluwatar.asyncrequestresponse.utils.Constants.JOB_SUBMIT_URL;

@SpringBootTest
//@WebMvcTest(CreateJobController.class)
//@RunWith(MockitoJUnitRunner.class)
class AsyncRequestResponseApplicationTests {

//  @Autowired
//  private MockMvc mockMvc;

  @MockBean
  AsyncProcessingWorkAcceptor asyncProcessingWorkAcceptor;

  @MockBean
  CreateJobController createJobController;

  private final RestTemplate restTemplate = new RestTemplate();

  @Test
  void contextLoads() throws Exception {
    URI uri = new URI(JOB_SUBMIT_URL);
    MessageDto messageDto = MessageDto.builder().message("test").createResource(true).build();
    HttpEntity<MessageDto> request = new HttpEntity<>(messageDto);
    ResponseEntity<Message> response = restTemplate.postForEntity(uri, request, Message.class);
    System.out.println(response.getStatusCode());
    System.out.println(response.getBody());
  }

//  @Test
//  void secondTest() throws Exception {
//    String exampleCourseJson = "{\"message\":\"test\",\"createResource\":\"true\"}";
//    RequestBuilder requestBuilder = MockMvcRequestBuilders
//            .post(JOB_SUBMIT_URL)
//            .accept(MediaType.APPLICATION_JSON)
//            .content(exampleCourseJson)
//            .contentType(MediaType.APPLICATION_JSON);
//
//    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//    MockHttpServletResponse response = result.getResponse();
//    System.out.println(response.getStatus());
//    System.out.println(response.getContentAsString());
//  }
}
