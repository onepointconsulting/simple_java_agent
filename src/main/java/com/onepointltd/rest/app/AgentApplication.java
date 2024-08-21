package com.onepointltd.rest.app;

import com.onepointltd.agent.AgentExecutor;
import com.onepointltd.agent.AgentExecutorFactory;
import com.onepointltd.agent.FunctionalAgentExecutor;
import com.onepointltd.agent.FunctionalAgentExecutorFactory;
import com.onepointltd.client.Client;
import com.onepointltd.config.ClientFactory;
import com.onepointltd.config.Config;
import com.onepointltd.rest.config.AgentConfiguration;
import com.onepointltd.rest.resource.AgentResource;
import com.onepointltd.rest.resource.ApplicationHealthCheck;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class AgentApplication extends Application<AgentConfiguration> {

  private AgentExecutor agentExecutor;

  private FunctionalAgentExecutor functionalAgentExecutor;

  public static void main(String[] args) throws Exception {
    new AgentApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<AgentConfiguration> bootstrap) {
    Config config = new Config();
    Client client = ClientFactory.createClient(config);
    agentExecutor = AgentExecutorFactory.createDefaultAgentExecutor(config, client);
    functionalAgentExecutor =
        FunctionalAgentExecutorFactory.createDefaultAgentExecutor(config, client);
  }

  @Override
  public void run(AgentConfiguration configuration, Environment environment) {
    var resource = new AgentResource(agentExecutor, functionalAgentExecutor);
    environment.healthChecks().register("application", new ApplicationHealthCheck());
    environment.jersey().register(resource);
  }
}
