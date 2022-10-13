package exercise;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Map;

// BEGIN
abstract class Tag {
    String tagName;
    Map<String, String> attributes = new HashMap<>();

    public Tag(String tagName, Map<String, String> attributes) {
        this.tagName = tagName;
        this.attributes = attributes;
    }

    @Override
    public abstract String toString();
}
// END
