package pl.kornijasz.pojazdy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarWeb {
    private final List<Car> cars;

    public CarWeb() {
        this.cars = new ArrayList<>();
        cars.add(new Car(1L, "Volvo", "XC70", "Zielony"));
        cars.add(new Car(2L, "Citroen", "C4", "Brązowy"));
        cars.add(new Car(3L, "Citroen", "Nemo", "Biały"));
    }

    @GetMapping("/cars")
    public String getCars(Model model) {
        model.addAttribute("cars", cars);
        return "cars";
    }

    @GetMapping("/cars/{id}")
    public String getCar(@PathVariable Long id, Model model) {
        Optional<Car> carById = cars.stream().filter(car -> car.getId().equals(id)).findAny();
        if (carById.isPresent()) {
            model.addAttribute("car", carById.get());
            return "car";
        } else {
            Car emptyCar = cars.get(0);
            model.addAttribute("car", emptyCar);
            return "car";
        }
    }
}
