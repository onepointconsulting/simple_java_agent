package com.onepointltd.client;

import com.onepointltd.model.Function;
import com.onepointltd.model.Message;
import com.onepointltd.model.Response;
import java.util.List;

public interface Client {

  Response completions(List<Message> messages, List<Function> functions);
}
