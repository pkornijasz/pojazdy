package pl.kornijasz.pojazdy;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CarRepository {
    private final Map<Long, Car> carMap = new HashMap<>();
    private final AtomicLong ID_NEXT_VALUE = new AtomicLong(1L);

    public CarRepository() {
        long i = nextId();
        carMap.put(i, new Car(i, "Volvo", "XC70", "Zielony"));
        i = nextId();
        carMap.put(i, new Car(i, "Citroen", "C4", "Brązowy"));
        i = nextId();
        carMap.put(i, new Car(i, "Citroen", "Berlingo", "Biały"));
        i = nextId();
        carMap.put(i, new Car(i, "Citroen", "Nemo", "Biały"));
    }

    public List<Car> findAllCars() {
        return new ArrayList<>(carMap.values());
    }

    public Optional<Car> findCarById(Long id) {
        return Optional.ofNullable(carMap.get(id));
    }

    public Car addCar(Car car) {
//        if (car.getId() != null) {
//            carMap.put(car.getId(), car);
//        } else {
            long nextId = nextId();
            car.setId(nextId);
            return carMap.put(nextId, car);
//        }
//        return car;
    }

    public Car modifyCarById(Long id, Car car) {
        return carMap.replace(id, car);
    }

    public void removeCarById(Long id) {
        carMap.remove(id);
    }

    private long nextId() {
        return ID_NEXT_VALUE.getAndIncrement();
    }

}
