package com.primefaces.examples.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;


import org.primefaces.examples.domain.Car;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@SessionScoped
public class TableBean implements Serializable {

    private static String[] colors;
    private static String[] manufacturers;
    
    static {
        colors = new String[10];
        colors[0] = "Black";
        colors[1] = "White";
        colors[2] = "Green";
        colors[3] = "Red";
        colors[4] = "Blue";
        colors[5] = "Orange";
        colors[6] = "Silver";
        colors[7] = "Yellow";
        colors[8] = "Brown";
        colors[9] = "Maroon";

        manufacturers = new String[10];
        manufacturers[0] = "Mercedes";
        manufacturers[1] = "BMW";
        manufacturers[2] = "Volvo";
        manufacturers[3] = "Audi";
        manufacturers[4] = "Renault";
        manufacturers[5] = "Opel";
        manufacturers[6] = "Volkswagen";
        manufacturers[7] = "Chrysler";
        manufacturers[8] = "Ferrari";
        manufacturers[9] = "Ford";
    }
    
    private LazyDataModel<Car> lazyModel;
    private List<Car> cars;
    private Car selectedCar;
    private Car[] selectedCars;
    private CarDataModel mediumCarsModel;
    private SelectItem[] manufacturerOptions;
    private List<Car> filteredCars;
    private List<Car> carsSmall;

    public TableBean() {
        cars = new ArrayList<>();
        carsSmall = new ArrayList<>();
        populateRandomCars(cars, 50);
        populateRandomCars(carsSmall, 9);
        lazyModel = new LazyCarDataModel(cars);
        mediumCarsModel = new CarDataModel(cars);
        manufacturerOptions = createFilterOptions(manufacturers);
    }

    public Car[] getSelectedCars() {
        return selectedCars;
    }

    public void setSelectedCars(Car[] selectedCars) {
        this.selectedCars = selectedCars;
    }

    public LazyDataModel<Car> getLazyModel() {
        return lazyModel;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    private void populateRandomCars(List<Car> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new Car(getRandomModel(), getRandomYear(), getRandomManufacturer(), getRandomColor()));
        }
    }

    public List<Car> getFilteredCars() {
        return filteredCars;
    }

    public void setFilteredCars(List<Car> filteredCars) {
        this.filteredCars = filteredCars;
    }

    public List<Car> getCarsSmall() {
        return carsSmall;
    }

    private int getRandomYear() {
        return (int) (Math.random() * 50 + 1960);
    }

    private String getRandomColor() {
        return colors[(int) (Math.random() * 10)];
    }

    private String getRandomManufacturer() {
        return manufacturers[(int) (Math.random() * 10)];
    }

    private String getRandomModel() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private SelectItem[] createFilterOptions(String[] data) {
        SelectItem[] options = new SelectItem[data.length + 1];

        options[0] = new SelectItem("", "Select");
        for (int i = 0; i < data.length; i++) {
            options[i + 1] = new SelectItem(data[i], data[i]);
        }

        return options;
    }

    public SelectItem[] getManufacturerOptions() {
        return manufacturerOptions;
    }

    public CarDataModel getMediumCarsModel() {
        return mediumCarsModel;
    }
}
