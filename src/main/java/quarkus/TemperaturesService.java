package quarkus;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TemperaturesService {

    private List<Temperature> values = new ArrayList<>();

    public void  addTemperature(Temperature t) {
        values.add(t);
    }

    public List<Temperature> getTemperatures() {
        return Collections.unmodifiableList(values);
    }

    public boolean isEmpty(){
        return values.isEmpty();
    }

    public int maximum(){
        return values.stream().mapToInt(Temperature::getMaximum).max().getAsInt();
    }

    public Optional<Temperature> takeOutTemperature(String city){
        return values.stream()
                .filter(t -> t.getCity().equals(city))
                .findAny();
    }

}
