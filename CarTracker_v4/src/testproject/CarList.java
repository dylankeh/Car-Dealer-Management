/*
 * Name: Phoenix and Mack
 * File: CarList.java
 * Other files in this project: Car.java, Main.java, CarTracker.css
 * Main class: Main.java
 */
package testproject;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * This class includes inventoryList, get(), addCar(), length(), searchList,
 * toString()
 *
 * @author mackhope
 */
public class CarList extends ArrayList {

    ArrayList<Car> carList;

    /**
     * This constructor creates an ArrayList
     *
     */
    public CarList() {
        this.carList = new ArrayList();
    }

    /**
     * Set index
     *
     * @param index the index of element
     * @param car the car object
     */
    public void set(int index, Car car) {

        this.carList.set(index, car);

    }

    /**
     * Retrieves index
     *
     * @param index the index of element
     * @return return element of specific index
     */
    public Car get(int index) {

        return this.carList.get(index);
    }

    /**
     * add Car object to ArrayList
     *
     * @param car car object
     */
    public void addCar(Car car) {
        carList.add(car);
        System.out.println("a new record added! Yay!!");
    }

    /**
     * Find length of ArrayList
     *
     * @return return length
     */
    public int length() {
        return this.carList.size();
    }


    /**
     * output in a specified format
     * @return return info in a certain format
     */
    public String toString() {

        //create a format  
        String format = "";

        //use a loop to combine all elements' information
        for (int i = 0; i < carList.size(); i++) {
            format += carList.get(i).getMake() + " "
                    + carList.get(i).getModel() + " "
                    + carList.get(i).getYear() + " "
                    + carList.get(i).getColour() + " "
                    + carList.get(i).getID() + " "
                    + carList.get(i).getDesc() + " "
                    + carList.get(i).getPrice() + "\n";
        }

        //return formatted output
        return format;
    }
}
