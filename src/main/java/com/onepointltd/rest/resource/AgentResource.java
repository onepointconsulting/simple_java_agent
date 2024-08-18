package com.onepointltd.rest.resource;

import com.codahale.metrics.annotation.Timed;
import com.onepointltd.agent.AgentExecutor;
import com.onepointltd.rest.model.WebQuestion;
import com.onepointltd.rest.model.WebResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/agent")
@Produces(MediaType.APPLICATION_JSON)
public class AgentResource {

  private final AgentExecutor agentExecutor;

  public AgentResource(AgentExecutor agentExecutor) {
    this.agentExecutor = agentExecutor;
  }

  @GET
  @Timed
  public WebResponse askQuestionGet(@QueryParam("question") String question) {
    if(question == null || question.trim().isBlank()) {
      throw new IllegalArgumentException("Question parameter is required.");
    }
    return new WebResponse(question, this.agentExecutor.execute(question));
  }

  @POST
  @Timed
  public WebResponse askQuestionPost(WebQuestion webQuestion) {
    if(webQuestion == null || webQuestion.getQuestion() == null
        || webQuestion.getQuestion().trim().isBlank()) {
      throw new IllegalArgumentException("Question parameter is required.");
    }
    return new WebResponse(webQuestion.getQuestion(), this.agentExecutor.execute(webQuestion.getQuestion()));
  }
}
