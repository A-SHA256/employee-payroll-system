import java.io.Serializable;

public record Employee(String name, String position, int salary) implements Serializable {
}
