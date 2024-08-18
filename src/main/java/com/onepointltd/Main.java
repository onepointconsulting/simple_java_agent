package com.onepointltd;

import static com.onepointltd.config.Logging.logger;

import com.onepointltd.agent.AgentExecutor;
import com.onepointltd.agent.AgentExecutorFactory;
import com.onepointltd.agent.FunctionalAgentExecutorFactory;
import com.onepointltd.client.Client;
import com.onepointltd.client.Groq;
import com.onepointltd.client.OpenAI;
import com.onepointltd.config.ClientFactory;
import com.onepointltd.config.Config;
import com.onepointltd.config.Logging;
import com.onepointltd.config.ModelProvider;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

  private static final String PROMPT_OPTION = "p";

  private static final String PROMPT_LONG_OPTION = "prompt";

  private static final String AGENT_TYPE_OPTION = "t";

  private static final String AGENT_TYPE_DEFAULT = "plain";

  private static final String AGENT_TYPE_FUNCTION = "function";

  private static final String PRINT_CONFIG_OPTION = "c";

  public static final String PRINT_CONFIG_LONG_OPTION = "print-config";

  public static void main(String[] args) {

    Logging.initLogging();
    Options options = initOptions();

    // define parser
    CommandLine cmd;
    var parser = new DefaultParser();
    var helper = new HelpFormatter();

    try {
      cmd = parser.parse(options, args);
      if (cmd.hasOption(PRINT_CONFIG_OPTION)) {
        Config config = new Config();
        System.out.println(config);
        return;
      }
      if (cmd.hasOption(PROMPT_OPTION)) {
        logger.info("Starting agent");
        var question = cmd.getOptionValue(PROMPT_OPTION);
        logger.info("Prompt: " + question);
        var agentType = cmd.getOptionValue(AGENT_TYPE_OPTION, "plain");
        Config config = new Config();
        logger.info(String.format("Model: %s%n", config.getModelName()));
        Client client = ClientFactory.createClient(config);
        AgentExecutor agentExecutor;
        switch (agentType) {
          case "plain" ->
              agentExecutor = AgentExecutorFactory.createDefaultAgentExecutor(config, client);
          case "function" ->
              agentExecutor =
                  FunctionalAgentExecutorFactory.createDefaultAgentExecutor(config, client);
          default -> {
            logger.info("Invalid agent type");
            printUsage(options, helper);
            return;
          }
        }

        String answer = agentExecutor.execute(question);

        System.out.println();
        System.out.println("*****************************");
        System.out.println(answer);
        System.out.println("*****************************");
        return;
      }
      printUsage(options, helper);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      printUsage(options, helper);
    }

    if (args.length == 0) {
      System.out.println("Please provide a question as an argument in quotes");
      System.out.println("Example: java -jar <jar-file> \"Who is the UK prime minister?\"");
    }
  }

  private static Options initOptions() {
    var options = new Options();
    options.addOption(
        Option.builder(PROMPT_OPTION)
            .longOpt(PROMPT_LONG_OPTION)
            .argName(PROMPT_LONG_OPTION)
            .hasArg()
            .required(false)
            .desc("set the question you want to ask in quotes")
            .build());
    options.addOption(
        Option.builder(AGENT_TYPE_OPTION)
            .longOpt("agent-type")
            .argName("agent-type")
            .hasArg()
            .required(false)
            .desc(
                String.format(
                    "sets the agent type. One of '%s', '%s' are the options",
                    AGENT_TYPE_DEFAULT, AGENT_TYPE_FUNCTION))
            .build());
    options.addOption(
        Option.builder(PRINT_CONFIG_OPTION)
            .longOpt(PRINT_CONFIG_LONG_OPTION)
            .argName(PRINT_CONFIG_LONG_OPTION)
            .required(false)
            .desc("prints the configuration")
            .build());
    return options;
  }

  private static void printUsage(Options options, HelpFormatter helper) {
    helper.printHelp(
        "parameters:",
        "",
        options,
        "\"%JAVA_HOME%\\bin\\java\" -jar simple_agent-1.0-SNAPSHOT.jar -p \"Who is the UK prime minister?\" -t plain -m 6",
        true);
    System.exit(1);
  }
}
