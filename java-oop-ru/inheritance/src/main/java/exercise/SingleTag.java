package exercise;

import java.util.Map;

// BEGIN
class SingleTag extends Tag {

    public SingleTag(String tagName, Map<String, String> attributes) {
        super(tagName, attributes);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("<" + tagName);
        for (String key : attributes.keySet()) {
            result.append(" " + key + "=\"" + attributes.get(key) + "\"");
        }
        result.append(">");
        return result.toString();
    }
}
// END
