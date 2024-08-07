package com.onepointltd.model;

import java.util.List;

public class MessageListProvider {

  public static List<Message> provide() {
    return List.of(
        new Message(Roles.SYSTEM, "Hello, system!"),
        new Message(Roles.USER, "Hello, assistant!"),
        new Message(Roles.ASSISTANT, "How are you today?"),
        new Message(Roles.USER, "I'm fine, thank you!"),
        new Message(Roles.ASSISTANT, "Good to hear that!"));
  }
}
