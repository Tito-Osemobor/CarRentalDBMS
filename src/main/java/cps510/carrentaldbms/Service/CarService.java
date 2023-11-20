package cps510.carrentaldbms.Service;

import cps510.carrentaldbms.Entity.Car;
import cps510.carrentaldbms.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> fetchAllCars() {
        return carRepository.findAll();
    }

    public Car fetchCarById(Long id) {
        return carRepository.findCarById(id);
    }

    public List<Car> fetchCarsByMileageInRange(Long maxMileage, Long minMileage) {
        return carRepository.findCarsByMileageInRange(minMileage, maxMileage);
    }

    public String createNewCar(Car newCar) {
        carRepository.save(newCar);
        return "Record successfully created!";
    }

    public String updateCar(Car updatedCar) {
        Car car = carRepository.findCarById(updatedCar.getId());
        car.setMake(updatedCar.getMake());
        car.setModel(updatedCar.getModel());
        car.setYear(updatedCar.getYear());
        car.setMileage(updatedCar.getMileage());
        car.setColor(updatedCar.getColor());
        car.setImage(updatedCar.getImage());
        car.setStatus(updatedCar.getStatus());
        car.setPrice(updatedCar.getPrice());
        carRepository.save(car);
        return "Car successfully updated!";
    }

    public String deleteCar(Long id) {
        Car car = carRepository.findCarById(id);
        carRepository.delete(car);
        return "Car successfully deleted!";
    }
}
