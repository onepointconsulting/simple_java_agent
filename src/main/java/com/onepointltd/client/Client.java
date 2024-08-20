package com.onepointltd.client;

import com.onepointltd.model.Message;
import com.onepointltd.model.Response;
import com.onepointltd.model.ToolField;
import java.util.List;

public interface Client {

  Response completions(List<Message> messages, List<ToolField> tools);

  String getEndpoint();
}
