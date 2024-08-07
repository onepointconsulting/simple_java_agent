package com.onepointltd.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.onepointltd.model.provider.FunctionProvider;
import org.junit.jupiter.api.Test;

class FunctionTest {

  @Test
  public void whenSerialize_ShouldBeValidJson() {
    Function function = FunctionProvider.createFunction();
    String json = Serializer.toJson(function);
    assertNotNull(json);
    System.out.println(json);
  }
}
