package com.onepointltd;

import com.onepointltd.agent.AgentExecutor;
import com.onepointltd.client.Groq;
import com.onepointltd.config.Config;
import com.onepointltd.tools.DuckDuckGo;
import com.onepointltd.tools.Tool;
import com.onepointltd.tools.Wikipedia;

public class Main {

  public static void main(String[] args) {

    if (args.length == 0) {
      System.out.println("Please provide a question as an argument in quotes");
      System.out.println("Example: java -jar <jar-file> \"Who is the UK prime minister?\"");
      return;
    }

    String question = args[0];
    Config config = new Config();
    System.out.printf("Model: %s%n", config.getModelName());
    Groq client = new Groq(config);
    DuckDuckGo duckDuckGo = new DuckDuckGo(config);
    AgentExecutor agentExecutor = new AgentExecutor(client, new Tool[]{duckDuckGo, new Wikipedia(config)}, 5);
    String answer = agentExecutor.execute(question);

    System.out.println("*****************************");
    System.out.println(answer);
    System.out.println("*****************************");
  }
}