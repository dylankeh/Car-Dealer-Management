/*
 * Name: Mack & Phoenix
 * File: Car.java
 * Other files in this project: CarList.java, CarTracker.css, Main.java
 * Main class: Main.java
 */
package testproject;

import java.io.Serializable;
import javafx.scene.image.Image;

/**
 * This class includes id, make, model, year, colour, trans, price, img,
 * imgPath, and desc
 *
 * @author mackhope
 */
public class Car implements Serializable {

    private int id = 0;
    private String make = null;
    private String model = null;
    private int year = 0;
    private String colour = null;
    private String trans = null;
    private double price = 0.0;
    private Image img = null;
    private String imgPath = null;
    private String desc = null;

    public Car() {
    }

    /*
    * this constructor will set attribute to each field.
    *
     */
    public Car(String make, String model, int year, String colour,
            String trans, double price, String desc) {

        setID(id);
        setMake(make);
        setModel(model);
        setYear(year);
        setColour(colour);
        setTrans(trans);
        setPrice(price);
        setImage(img);
        setDesc(desc);
    }

    /**
     * Retrieves id
     *
     * @return id
     */
    public int getID() {
        return this.id;
    }

    /**
     * Set id to id
     *
     * @param id is generated automatically
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Set make to make
     *
     * @param make user's input for car's make
     * @throws IllegalArgumentException if make field is empty.
     */
    public void setMake(String make) {
        if (!"".equals(make)) {
            this.make = make;
        } else {
            throw new IllegalArgumentException("Please enter a make!");
        }
    }

    /**
     * Retrieves make
     *
     * @return make
     */
    public String getMake() {
        return this.make;
    }

    /**
     * Set model to model
     *
     * @param model user's input for car's model
     * @throws IllegalArgumentException if model field is empty.
     */
    public void setModel(String model) {
        if (!"".equals(model)) {
            this.model = model;
        } else {
            throw new IllegalArgumentException("Please enter a model!");
        }
    }

    /**
     * Retrieves model
     *
     * @return model
     */
    public String getModel() {
        return this.model;
    }

    /**
     * Set year to year
     *
     * @param year user's input for car's year
     * @throws IllegalArgumentException if year is not a valid number.
     */
    public void setYear(int year) {
        if (year > 1900 && year < 2020) {
            this.year = year;
        } else {
            throw new IllegalArgumentException("Please enter a valid year!");
        }
    }

    /**
     * Retrieves year
     *
     * @return year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Set colour to colour
     *
     * @param colour user's choice for car's colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * Retrieves colour
     *
     * @return colour
     */
    public String getColour() {
        return this.colour;
    }

    /**
     * Set trans to trans
     *
     * @param trans user's choice for car's transmission type
     */
    public void setTrans(String trans) {
        this.trans = trans;
    }

    /**
     * Retrieves transmission type
     *
     * @return transmission type
     */
    public String getTrans() {
        return this.trans;
    }

    /**
     * Set price to price
     *
     * @param price user's input for car's price
     * @throws IllegalArgumentException if price is negative number.
     */
    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("Enter a valid price.");
        }
    }

    /**
     * Retrieves price
     *
     * @return price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Set imgPath to img path
     *
     * @param imgPath car's image path
     */
    public void setImagePath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * Retrieves image path
     *
     * @return image path
     */
    public String getImagePath() {
        return this.imgPath;
    }

    /**
     * Set img to img field
     *
     * @param img car's img
     * @throws IllegalArgumentException if user hasn't added a picture for a
     * car.
     */
    public void setImage(Image img) {
        if (img instanceof Image) {
            this.img = img;
        } else {
            throw new IllegalArgumentException("Add an image!");
        }
    }

    /**
     * use getImagePath() method to get imgPath and create a img object and
     * assign it to the car
     *
     */
    public void setImageFromPath() {

        this.img = new Image(this.getImagePath());
    }

    /**
     * Retrieves image object
     *
     * @return image object
     */
    public Image getImage() {
        return this.img;
    }

    /**
     * Set desc to desc
     *
     * @param desc user's input for car's note field
     */
    public void setDesc(String desc) {
        
        this.desc = desc;
    }

    /**
     * Retrieves note
     *
     * @return note
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * output in a specified format
     * @return product info in a certain format
     */
    @Override
    public String toString() {
        return this.getID() + "|" + this.getMake() + "|" + this.getModel() + "|"
                + this.getYear() + "|" + this.getColour() + "|" + this.getTrans()
                + "|" + String.format("%.2f", this.getPrice()) + "|"
                + this.getImagePath() + "|" + this.getDesc();
    }
}
