package quarkus;

import java.util.List;
import java.util.Optional;

public interface ITemperatureService {
    void addTemperature(Temperature t);

    List<Temperature> getTemperatures();

    Optional<Temperature> takeOutTemperature(String city);

    boolean isEmpty();

    int maximum();
}
