package com.onepointltd;

import static com.onepointltd.config.Logging.logger;

import com.onepointltd.agent.AgentExecutor;
import com.onepointltd.agent.FunctionalAgentExecutor;
import com.onepointltd.client.Client;
import com.onepointltd.client.Groq;
import com.onepointltd.client.OpenAI;
import com.onepointltd.config.Config;
import com.onepointltd.config.Logging;
import com.onepointltd.config.ModelProvider;
import com.onepointltd.tools.DuckDuckGo;
import com.onepointltd.tools.FunctionalCalculator;
import com.onepointltd.tools.FunctionalDuckDuckGo;
import com.onepointltd.tools.FunctionalTool;
import com.onepointltd.tools.FunctionalWikipedia;
import com.onepointltd.tools.Tool;
import com.onepointltd.tools.Wikipedia;
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

  private static final String MAX_ITERATIONS_OPTION = "m";

  public static final String MAX_ITERATIONS_LONG_OPTION = "max-iterations";

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
      if(cmd.hasOption(PRINT_CONFIG_OPTION)) {
        Config config = new Config();
        System.out.println(config);
        return;
      }
      if (cmd.hasOption(PROMPT_OPTION)) {
        logger.info("Starting agent");
        var question = cmd.getOptionValue(PROMPT_OPTION);
        var agentType = cmd.getOptionValue(AGENT_TYPE_OPTION, "plain");
        var maxIterations = Integer.parseInt(cmd.getOptionValue(MAX_ITERATIONS_OPTION, "6"));
        Config config = new Config();
        System.out.printf("Model: %s%n", config.getModelName());
        Client client = config.getProvider() == ModelProvider.OPENAI ? new OpenAI(config) : new Groq(config);
        AgentExecutor agentExecutor;
        switch (agentType) {
          case "plain" -> agentExecutor = new AgentExecutor(client, new Tool[]{new DuckDuckGo(config), new Wikipedia(config)}, maxIterations);
          case "function" -> agentExecutor = new FunctionalAgentExecutor(client,
              new FunctionalTool[]{new FunctionalDuckDuckGo(config), new FunctionalWikipedia(config), new FunctionalCalculator()}, maxIterations);
          default -> {
            System.out.println("Invalid agent type");
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
      return;
    }
  }

  private static Options initOptions() {
    var options = new Options();
    options.addOption(Option.builder(PROMPT_OPTION).longOpt(PROMPT_LONG_OPTION)
        .argName(PROMPT_LONG_OPTION)
        .hasArg()
        .required(false)
        .desc("set the question you want to ask in quotes").build());
    options.addOption(Option.builder(AGENT_TYPE_OPTION).longOpt("agent-type")
        .argName("agent-type")
        .hasArg()
        .required(false)
        .desc(String.format("sets the agent type. One of '%s', '%s' are the options", AGENT_TYPE_DEFAULT, AGENT_TYPE_FUNCTION)).build());
    options.addOption(Option.builder(MAX_ITERATIONS_OPTION).longOpt(MAX_ITERATIONS_LONG_OPTION)
        .argName(MAX_ITERATIONS_LONG_OPTION)
        .hasArg()
        .required(false)
        .desc("sets the maximum number of iterations, like e.g. 5").build());
    options.addOption(Option.builder(PRINT_CONFIG_OPTION).longOpt(PRINT_CONFIG_LONG_OPTION)
        .argName(PRINT_CONFIG_LONG_OPTION)
        .required(false)
        .desc("prints the configuration").build());
    return options;
  }

  private static void printUsage(Options options, HelpFormatter helper) {
    helper.printHelp("parameters:", "", options,"\"%JAVA_HOME%\\bin\\java\" -jar simple_agent-1.0-SNAPSHOT.jar -p \"Who is the UK prime minister?\" -t plain -m 6", true);
    System.exit(1);
  }
}