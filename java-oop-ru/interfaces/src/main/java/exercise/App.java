package exercise;

import java.util.Comparator;
import java.util.List;
// BEGIN
class App {
    public static List<String> buildAppartmentsList(List<Home> propertyList, int count) {
        List<String> resultString = propertyList.stream()
                .sorted(Comparator.comparing(h -> h.getArea()))
                .limit(count)
                .map(h -> h.toString())
                .toList();
        return resultString;
    }
}
// END
