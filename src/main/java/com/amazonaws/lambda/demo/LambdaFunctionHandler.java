package com.amazonaws.lambda.demo;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequest;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequestEntry;
import com.amazonaws.services.cloudwatchevents.model.PutEventsResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

public class LambdaFunctionHandler implements RequestHandler<S3Event, String> {

  @Override
  public String handleRequest(S3Event event, Context context) {
    final AmazonCloudWatchEvents cwe = AmazonCloudWatchEventsClientBuilder.defaultClient();

    final String EVENT_DETAILS = "{ \"key1\": \"value1\", \"key2\": \"value2\" }";

    PutEventsRequestEntry request_entry =
        new PutEventsRequestEntry()
            .withDetail(EVENT_DETAILS)
            .withDetailType("sampleSubmitted")
            .withResources("arn:aws:lambda:us-east-2:703218618165:function:hello-world")
            .withSource("aws-sdk-java-cloudwatch-example");

    PutEventsRequest request = new PutEventsRequest().withEntries(request_entry);

    PutEventsResult response = cwe.putEvents(request);

//    return response.toString();
    return EVENT_DETAILS;
  }
}
