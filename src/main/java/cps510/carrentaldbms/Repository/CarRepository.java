package cps510.carrentaldbms.Repository;

import cps510.carrentaldbms.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* This interface (repository) is used to define methods that query our Car Table
*/
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    //Query our Car table for a particular car with a matching ID
    Car findCarById(Long id);

    //Query our Car table for a list of cars with a matching MAKE
    List<Car> findCarsByMake(String make);

    //Query our Car table for a list of cars with a matching MAKE and MODEL
    List<Car> findCarsByMakeAndModel(String Make, String Model);

    //Query our Car table for a list of cars with a matching YEAR
    List<Car> findCarsByYear(Integer year);

    //Query our Car table for a list of cars with a matching MaxMileage > MILEAGE > MinMileage
    @Query("SELECT c FROM Car c WHERE CAST(SUBSTRING(c.mileage, 1, LENGTH(c.mileage) - 2) AS int) BETWEEN :minMileage AND :maxMileage")
    List<Car> findCarsByMileageInRange(@Param("minMileage") Long minMileage, @Param("maxMileage") Long maxMileage);

}
