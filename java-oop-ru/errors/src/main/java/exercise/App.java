package exercise;

// BEGIN
class App {
    public static void printSquare(Circle circle) {
        try {
            System.out.println(Math.round(circle.getSquare()));
        } catch (NegativeRadiusException e) {
            System.out.println("Не удалось посчитать площадь"
            //        + e.getErrorDescription()
            );
        } finally {
            System.out.println("Вычисление окончено");
        }
    }
}
// END
