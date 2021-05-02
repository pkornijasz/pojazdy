package pl.kornijasz.pojazdy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CarWeb {
    private List<Car> allCars;
    private final CarRepository carMap;

    @Autowired
    public CarWeb(CarRepository carMap) {
        this.carMap = carMap;
    }

    @GetMapping("/cars")
    public String getCars(Model model) {
        allCars = carMap.findAllCars();
        model.addAttribute("cars", allCars);
        model.addAttribute("newCar", new Car());
        return "cars";
    }

    @GetMapping("/cars/{id}")
    public String getCar(@PathVariable Long id, Model model) {
        Optional<Car> carById = carMap.findCarById(id);
        if (carById.isPresent()) {
            model.addAttribute("car", carById.get());
            return "car";
        } else {
            model.addAttribute("car", null);
            return "car";
        }
    }

    @PostMapping("/add-car")
    public String addCar(@ModelAttribute Car car) {
        System.out.println(car);
        carMap.addCar(car);
        return "redirect:/cars";
    }

    @GetMapping("/remove-car/{id}")
    public String removeCar(@PathVariable Long id) {
        System.out.println(id);
        carMap.removeCarById(id);
        return "redirect:/cars";
    }

    @PostMapping("/modify-car/{id}")
    public String modifyCar(@PathVariable Long id, @ModelAttribute Car car) {
        System.out.println(id);
        System.out.println(car);
        carMap.modifyCarById(id, car);
        return "redirect:/cars";
    }
}
