package exercise;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

// BEGIN
class PairedTag extends Tag {

    private String body;
    private List<Tag> children = new ArrayList<>();

    public PairedTag(String tagName, Map<String, String> attributes, String body, List<Tag> children) {
        super(tagName, attributes);
        this.body = body;
        this.children = children;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("<" + tagName);
        for (String key : attributes.keySet()) {
            result.append(" " + key + "=\"" + attributes.get(key) + "\"");
        }
        result.append(">");
        result.append(body == null || body.equals("") ? "" : body);

//        if (!children.isEmpty()) {
//            for (Tag child : children) {
//                result.append(child.toString());
//            }
//        }

        String tagsInLine = children.stream()
                .map(tag -> tag.toString())
                .collect(Collectors.joining());
        result.append(tagsInLine);

        result.append("</" + tagName + ">");

        return result.toString();
    }
}
// END
