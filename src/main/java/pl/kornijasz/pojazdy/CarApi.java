package pl.kornijasz.pojazdy;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarApi {
    private final List<Car> carList;

    public CarApi() {
        this.carList = new ArrayList<>();
        carList.add(new Car(1L, "Volvo", "XC70", "Zielony"));
        carList.add(new Car(2L, "Citroen", "C4", "Brązowy"));
        carList.add(new Car(3L, "Citroen", "Nemo", "Biały"));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCarList() {
        return new ResponseEntity<>(carList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCarById(@PathVariable Long id) {
        Optional<Car> carById = carList.stream().filter(car -> car.getId().equals(id)).findFirst();
        return carById.map(car -> new ResponseEntity(car, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarListByColor(@PathVariable String color) {
        List<Car> carsByColor = carList.stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());
        return new ResponseEntity<>(carsByColor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car car) {
        if (carList.add(car)) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity modifyCar(@RequestBody Car newCar) {
        Optional<Car> carById = carList.stream().filter(car -> car.getId().equals(newCar.getId())).findFirst();
        if (carById.isPresent()) {
            carList.remove(carById.get());
            carList.add(newCar);
            return new ResponseEntity(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchCarById(@PathVariable Long id, @RequestBody Car newCar) {
        Optional<Car> carById = carList.stream().filter(car -> car.getId().equals(id)).findFirst();
        if (carById.isPresent()) {
            if (newCar.getMark() != null) carById.get().setMark(newCar.getMark());
            if (newCar.getModel() != null) carById.get().setModel(newCar.getModel());
            if (newCar.getColor() != null) carById.get().setColor(newCar.getColor());
            return new ResponseEntity(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCarById(@PathVariable Long id) {
        Optional<Car> carById = carList.stream().filter(car -> car.getId().equals(id)).findFirst();
        if (carById.isPresent()) {
            carList.remove(carById.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
