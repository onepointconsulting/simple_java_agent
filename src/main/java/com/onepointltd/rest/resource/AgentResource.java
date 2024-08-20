package com.onepointltd.rest.resource;

import com.codahale.metrics.annotation.Timed;
import com.onepointltd.agent.AgentExecutor;
import com.onepointltd.model.AgentType;
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

  private final AgentExecutor functionalAgentExecutor;

  public AgentResource(AgentExecutor agentExecutor, AgentExecutor functionalAgentExecutor) {
    this.agentExecutor = agentExecutor;
    this.functionalAgentExecutor = functionalAgentExecutor;
  }

  @GET
  @Timed
  public WebResponse askQuestionGet(@QueryParam("question") String question) {
    if (question == null || question.trim().isBlank()) {
      throw new IllegalArgumentException("Question parameter is required.");
    }
    return new WebResponse(question, this.agentExecutor.execute(question), AgentType.PLAIN, this.agentExecutor.getEndpoint());
  }

  @POST
  @Timed
  public WebResponse askQuestionPost(WebQuestion webQuestion) {
    if (webQuestion == null
        || webQuestion.getQuestion() == null
        || webQuestion.getQuestion().trim().isBlank()) {
      throw new IllegalArgumentException("Question parameter is required.");
    }
    String question = webQuestion.getQuestion();
    var agentType = webQuestion.getAgentType();
    return switch (agentType) {
      case AgentType.FUNCTION -> {
        String answer = this.functionalAgentExecutor.execute(question);
        yield new WebResponse(
            question,
            answer,
            agentType, this.functionalAgentExecutor.getEndpoint())
              .setMessages(webQuestion.getIncludeMessages() ? this.functionalAgentExecutor.getMessages() : null);
      }
      case AgentType.PLAIN -> {
        String answer = this.agentExecutor.execute(question);
        yield new WebResponse(
            question,
            answer,
            agentType, this.agentExecutor.getEndpoint())
            .setMessages(webQuestion.getIncludeMessages() ? this.functionalAgentExecutor.getMessages() : null);
      }
      default -> throw new IllegalArgumentException("Invalid agent type: " + agentType);
    };
  }
}
