---
layout: pattern
title: Asynchronous Request Reply pattern
folder: async-request-response
permalink: /patterns/async-request-response/
categories: Concurrency
tags:
 - Gang of Four
---

## Intent

Decouple backend processing from a frontend host, where backend processing needs to be asynchronous,
but the frontend still needs a clear response without waiting for backend to complete.

## Explanation

In modern application development, usually client applications depend on remote
API's to get data. These API calls take place over HTTP(S) protocol and follow REST semantics.

In some cases, the API can response within few milliseconds but in some scenarios, the work
done by the backend may take minutes to several hours and as a result client side wont
get the response from API immediately. In that case, it isn't feasible to wait for the work
to complete before responding to the request.

This is where Asynchronous request reply pattern comes handy. Its a pattern where the client
application makes a synchronous call to the API, triggering a long-running operation on the backend.
The API responds synchronously as quickly as possible acknowledging that the request has been
received for processing. The response holds a location reference pointing to an endpoint that
the client can poll to check for the result of the long running operation. Once the work is
complete, the API endpoint can either return a resource that indicates completion,
or redirect to another resource URL which client can make another HTTP(S) request to get the
response.

Real world example

> The client sends a POST request and receives an HTTP 202 (Accepted) response.
> The client sends an HTTP GET request to the status endpoint. The work is still pending,
> so this call also returns HTTP 202. It sleeps for some time and then tries again.
> At some point, the work is complete and the status endpoint returns 302 (Found)
> redirecting to the resource.
> The client fetches the resource at the specified URL to get the job status

**Programmatic Example**

Here's the client code which submits a POST request to an API endpoint.

```java
class Client {
    public void submitMessage() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        MessageDto messageDto = MessageDto.builder().message("Hello world").createResource(true).build();
        HttpEntity<MessageDto> request = new HttpEntity<>(messageDto, httpHeaders);
        processResponse(restTemplate.postForEntity(JOB_SUBMIT_URL, request, Message.class));
      }
}
```
# Class diagram
![alt text](./etc/async-method-invocation.png "Async Method Invocation")

## Applicability
Use async method invocation pattern when

* you have multiple independent tasks that can run in parallel
* you need to improve the performance of a group of sequential tasks
* you have limited amount of processing capacity or long running tasks and the
  caller should not wait the tasks to be ready

## Real world examples

* [FutureTask](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/FutureTask.html), [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) and [ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html) (Java)
* [Task-based Asynchronous Pattern](https://msdn.microsoft.com/en-us/library/hh873175.aspx) (.NET)
