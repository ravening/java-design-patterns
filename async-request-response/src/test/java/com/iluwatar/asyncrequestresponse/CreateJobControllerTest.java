package com.iluwatar.asyncrequestresponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iluwatar.asyncrequestresponse.controllers.CreateJobController;
import com.iluwatar.asyncrequestresponse.models.JobStatus;
import com.iluwatar.asyncrequestresponse.models.Message;
import com.iluwatar.asyncrequestresponse.models.MessageDto;
import com.iluwatar.asyncrequestresponse.services.AsyncProcessingWorkAcceptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.iluwatar.asyncrequestresponse.utils.Constants.JOB_SUBMIT_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@RunWith(MockitoJUnitRunner.class)
//@WebMvcTest(CreateJobController.class)
//@SpringBootTest
@WebMvcTest(CreateJobController.class)
@ActiveProfiles("test")
public class CreateJobControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AsyncProcessingWorkAcceptor asyncProcessingWorkAcceptor;

  @MockBean
  CreateJobController createJobController;

  private ObjectMapper objectMapper= new ObjectMapper();

  @Test
  public void test() throws Exception {
    String exampleCourseJson = "{\"message\":\"test\",\"createResource\":\"true\"}";
    MessageDto messageDto = MessageDto.builder().message("test").createResource(true).build();
    Message message = Message.builder().message("ok test").httpStatus(HttpStatus.ACCEPTED).jobId("1234").jobStatus(JobStatus.JOB_PENDING).build();


    Mockito.when(asyncProcessingWorkAcceptor.submitJob(messageDto))
            .thenReturn(message);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post(JOB_SUBMIT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(messageDto))
            .characterEncoding("utf-8")
            .param("key", "value")
            .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isAccepted());
//    MockHttpServletResponse response = result.getResponse();
//    System.out.println(response.getStatus());
  }

  @Test
  public void second() throws Exception {
    String exampleCourseJson = "{\"message\":\"test\",\"createResource\":\"true\"}";
    MessageDto messageDto = MessageDto.builder().message("test").createResource(true).build();
    Message message = Message.builder().message("ok test").httpStatus(HttpStatus.ACCEPTED).jobId("1234").jobStatus(JobStatus.JOB_PENDING).build();


    Mockito.when(asyncProcessingWorkAcceptor.submitJob(messageDto))
            .thenReturn(message);

    RequestBuilder requestBuilder = get("/api/test")
            .characterEncoding("utf-8")
            .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
    MockHttpServletResponse response = result.getResponse();
    System.out.println(response.getContentAsString());
    System.out.println(response.getStatus());
  }

  @Test
  public void first() throws Exception {
    this.mockMvc.perform(get("/api/test")).andDo(print()).andReturn();
  }
}
