package exercise.daytime;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class Day implements Daytime {
    private String name = "day";

    public String getName() {
        return name;
    }

    // BEGIN
    @PostConstruct
    void one() {
        System.out.println("§§§§§§Day was created");
    }
    @PreDestroy
    void two(){
        System.out.println("§§§§§§Day was destroyed");
    }
    // END
}
