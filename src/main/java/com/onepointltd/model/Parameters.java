package com.onepointltd.model;

import java.util.List;
import java.util.Map;

public record Parameters(String type, Map<String, PropertyValue> properties, List<String> required) {
}
