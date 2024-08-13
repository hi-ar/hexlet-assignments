package exercise.daytime;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class Night implements Daytime {
    private String name = "night";

    public String getName() {
        return name;
    }

    // BEGIN
    @PostConstruct
    void one() {
        System.out.println("§§§§§§Night was created");
    }
    @PreDestroy
    void two(){
        System.out.println("§§§§§§Night was destroyed");
    }
    // END
}
