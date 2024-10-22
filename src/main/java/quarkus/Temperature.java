package quarkus;

import java.util.Objects;

public class Temperature {

    private String city;

    private int minimum;

    private int maximum;

    public Temperature() {
    }

    public Temperature(String city, int minimum, int maximum) {
        this.city = city;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temperature that = (Temperature) o;
        return minimum == that.minimum && maximum == that.maximum && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, minimum, maximum);
    }

    @Override
    public String toString() {
        return "Temperature [city=" + city + ", minimum=" + minimum + ", maximum=" + maximum + "]";
    }
}
