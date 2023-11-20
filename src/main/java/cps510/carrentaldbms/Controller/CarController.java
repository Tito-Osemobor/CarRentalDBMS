package cps510.carrentaldbms.Controller;

import cps510.carrentaldbms.Entity.Car;
import cps510.carrentaldbms.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrentaldbms/tables/cars")
@CrossOrigin(origins = "*")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.fetchAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.fetchCarById(id));
    }

    @GetMapping("/mileage")
    public ResponseEntity<List<Car>> getCarsByMileageInRange(@RequestParam("maxMileage") Long maxMileage, @RequestParam("minMileage") Long minMileage) {
        return ResponseEntity.ok(carService.fetchCarsByMileageInRange(maxMileage, minMileage));
    }

    @PostMapping
    public ResponseEntity<?> createNewCar(@RequestBody Car newCar) {
        return ResponseEntity.ok(carService.createNewCar(newCar));
    }

    @PutMapping
    public ResponseEntity<?> updateCar(@RequestBody Car updatedCar) {
        return ResponseEntity.ok(carService.updateCar(updatedCar));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCar(@RequestParam Long id) {
        return ResponseEntity.ok(carService.deleteCar(id));
    }
}
