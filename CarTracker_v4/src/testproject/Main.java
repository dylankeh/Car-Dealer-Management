/*
 * Name: Mack and Phoenix
 * Assignment: final project
 * Program: Software Engineering
 * Description: This application is used to add car for sale for car deals and 
 * keep track of records including add, search, view, edit functions
 */
package testproject;

import java.awt.Desktop;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.google.common.base.Splitter;
import java.util.ArrayList;

/**
 * This class includes GUI interface and methods of the event handler
 * 
 * @author mackhope
 */
public class Main extends Application {

    //Global variables
    File carsFile;
    CarList carList = new CarList();
    String[] strColours = {
        "Black", "White", "Red", "Blue", "Green", "Yellow", "Orange",
        "Silver", "Gold", "Grey", "Custom"
    };
    ObservableList<String> cList
            = FXCollections.observableArrayList(strColours);
    Stage window;

    //Scene1: home screen
    Scene scene1;
    Button btnFindVehicle, btnCreateListing, btnViewAllCars;
    File file;
    Desktop desktop;
    Image logo = new Image("file:Resources/autolocator.png");
    ObservableList<Car> searchBox = FXCollections.observableArrayList();
    ObservableList<Car> allCarsList = FXCollections.observableArrayList();

    //Scene2: search screen
    Scene scene2;
    Button goBack2, search2;
    TextField txtID, txtMAKE, txtMODEL, txtYEAR, minPrice, maxPrice;
    ComboBox<String> cmBox;
    RadioButton automatic, manual;
    ToggleGroup tg2;

    //Scene3: creating listing screen
    Scene scene3;
    Button goBack3;
    TextField txtNewMake, txtNewModel, txtNewYear, txtNewID, txtPrice;
    TextArea txtNote;
    RadioButton automaticNew, manualNew;
    Label imgPath;
    HBox browseImageBox;
    ComboBox<String> cmNewBox;
    ToggleGroup tg3;

    //Scene4: searching result screen
    Scene scene4;
    VBox vbS4;
    Button goBack4, goToSearchScene;
    ListView<Car> listView = new ListView<>();
    ObservableList<Image> imgList;

    //Scene5: view listing screen
    Scene scene5;
    VBox vbS5, vbTopLeft, vbTopRight;
    HBox hbTop, hbBottom;
    ImageView imgViewS5;
    Label lblID, lblPrice, lblAvailable,
            lblMake, lblModel, lblYear, lblColour, lblTrans;
    TextArea txtNotes;
    Button btnPrev, btnNext, goBack5;
    Boolean scene5isShowing = false;
    int count;

    //Scene6: all cars screen
    Scene scene6;
    VBox vbS6;
    Button goBack6, goToSearchScene2;
    ListView<Car> carListView = new ListView<>();

    //Scene7: view all cars listing screen
    Scene scene7;
    VBox vbS7, vbTopLeft2, vbTopRight2;
    HBox hbTop2, hbBottom2;
    ImageView imgViewS7;
    TextField lblID2, lblPrice2,
            lblMake2, lblModel2, lblYear2, lblColour2, lblTrans2;
    TextArea txtNotes2;
    Button btnPrev2, btnNext2, btnUpdate, btnSell, goBack7;
    Boolean scene7isShowing = false;

    //alerts
    Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertError = new Alert(Alert.AlertType.ERROR);
    Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        //download car list 
        try {
            downloadFile();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        //SCENE1+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++1
        //car tracker label
        Label title = new Label("Auto Locator");
        title.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 50;"
        );

        //car tracker logo
        ImageView imgV = new ImageView();
        imgV.setImage(logo);
        imgV.setFitWidth(300);
        imgV.setPreserveRatio(true);
        imgV.setStyle("-fx-padding: 20;");
        imgV.setSmooth(true);

        //find vehichle
        btnFindVehicle = new Button();
        btnFindVehicle.setText(" Find a Car");
        btnFindVehicle.setMinWidth(300);
        btnFindVehicle.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 30;"
                + "-fx-color: white;"
                + "-fx-background-color: blue;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-graphic: url(\"file:Resources/mag.png\");"
                + "-fx-text-align: left;"
        );

        //view all
        btnViewAllCars = new Button();
        btnViewAllCars.setText(" View All Cars");
        btnViewAllCars.setMinWidth(300);
        btnViewAllCars.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 30;"
                + "-fx-color: white;"
                + "-fx-background-color: purple;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                //+ "-fx-graphic: url(\"file:Resources/car.png\");"
                + "-fx-text-align: left;"
        );

        //create a listing button
        btnCreateListing = new Button();
        btnCreateListing.setText(" Add a Car");
        btnCreateListing.setMinWidth(300);
        btnCreateListing.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 30;"
                + "-fx-color: white;"
                + "-fx-background-color: green;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-graphic: url(\"file:Resources/car.png\");"
                + "-fx-text-align: center;"
        );

        //main VBox
        VBox vbox1 = new VBox(30);
        vbox1.setStyle("-fx-background-color:black");
        vbox1.setAlignment(Pos.CENTER);
        vbox1.getChildren().addAll(title, imgV, btnCreateListing, btnFindVehicle, btnViewAllCars);

        //events
        btnFindVehicle.setOnMouseEntered(e
                -> btnFindVehicle.setCursor(javafx.scene.Cursor.HAND));

        btnCreateListing.setOnMouseEntered(e
                -> btnCreateListing.setCursor(javafx.scene.Cursor.HAND));

        btnViewAllCars.setOnMouseEntered(e
                -> btnViewAllCars.setCursor(javafx.scene.Cursor.HAND));

        btnFindVehicle.setOnAction(e -> {
            clearSearchFields();
            window.setScene(scene2);
        });
        btnCreateListing.setOnAction(e -> {
            window.setScene(scene3);
            clearFields();
        });
        btnViewAllCars.setOnAction(e -> {
            if (carList.length() == 0) {
                alertError.setTitle("Car Tracker");
                alertError.setHeaderText("No cars in system.");
                alertError.setContentText("Add some!");
                alertError.showAndWait();
            } else {
                carListView.getItems().clear();
                loadAllCarsList(carList);
                window.setScene(scene6);
            }
        });

        scene1 = new Scene(vbox1, 320, 520);
        scene1.getStylesheets()
                .add("http://fonts.googleapis.com/css?family=Gafata");
        scene1.getStylesheets().add("/testproject/CarTracker.css");

        //SCENE 2++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++2
        Label title2 = new Label("Auto Locator");
        title2.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 30;"
        );
        title2.setAlignment(Pos.CENTER);

        ImageView imgV2 = new ImageView();
        imgV2.setImage(logo);
        imgV2.setFitWidth(200);
        imgV2.setPreserveRatio(true);
        imgV2.setStyle("-fx-padding: 20;");
        imgV2.setSmooth(true);

        // ID text box
        txtID = new TextField();
        txtID.setPromptText("Vehicle ID");
        txtID.setMaxHeight(30);
        txtID.setMaxWidth(300);

        // MAKE text box 
        txtMAKE = new TextField();
        txtMAKE.setPromptText("Make");
        txtMAKE.setMaxHeight(30);
        txtMAKE.setMaxWidth(300);

        // MODEL text box
        txtMODEL = new TextField();
        txtMODEL.setPromptText("Model");
        txtMODEL.setMaxHeight(30);
        txtMODEL.setMaxWidth(300);

        // YEAR text box
        txtYEAR = new TextField();
        txtYEAR.setPromptText("Year");
        txtYEAR.setMaxHeight(30);
        txtYEAR.setMaxWidth(300);

        //COLOUR options
        // COLOUR combobox
        cmBox = new ComboBox<>();
        cmBox.setItems(cList);
        cmBox.setPromptText("Colour");
        cmBox.setPrefSize(300, 25);

        //RADIO buttons container
        HBox transType = new HBox(30);
        transType.setPrefHeight(25);
        transType.setAlignment(Pos.CENTER);
        transType.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 15;"
                + "-fx-text-fill: white;"
        );

        automatic = new RadioButton("Automatic");
        automatic.setStyle("-fx-text-fill:White;");
        automatic.setUserData("Automatic");

        manual = new RadioButton("Manual");
        manual.setStyle("-fx-text-fill:White;");
        manual.setUserData("Manual");

        tg2 = new ToggleGroup();
        automatic.setToggleGroup(tg2);
        manual.setToggleGroup(tg2);

        transType.getChildren().addAll(automatic, manual);

        //PRICE Container
        minPrice = new TextField();
        minPrice.setPromptText("Min Price");
        minPrice.setMaxWidth(130);
        minPrice.setMaxHeight(30);

        Label lblDollars = new Label("$");
        lblDollars.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 20;"
                + "-fx-text-fill: white;"
        );
        Label lblTO = new Label("to");
        lblTO.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 15;"
                + "-fx-text-fill: white;"
        );

        maxPrice = new TextField();
        maxPrice.setPromptText("Max Price");
        maxPrice.setMaxWidth(130);
        maxPrice.setMaxHeight(30);

        HBox priceBox = new HBox(5);
        priceBox.setAlignment(Pos.CENTER);
        priceBox.setMaxHeight(30);
        priceBox.setMaxWidth(300);
        priceBox.getChildren().addAll(lblDollars, minPrice, lblTO, maxPrice);

        //search2 button
        search2 = new Button(" Search");
        search2.setMnemonicParsing(true);
        search2.setDefaultButton(true);
        search2.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 30;"
                + "-fx-color: white;"
                + "-fx-background-color: blue;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-graphic: url(\"file:Resources/mag.png\");"
                + "-fx-text-align: center;"
        );
        //home
        goBack2 = new Button("Home");
        goBack2.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 20;"
                + "-fx-color: white;"
                + "-fx-background-color: Green;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
                + "-fx-graphic: url(\"file:Resources/home.png\");"
        );
        goBack2.setAlignment(Pos.CENTER);
        goBack2.setPadding(new Insets(5, 5, 5, 5));
        goBack2.setMaxHeight(30);
        goBack2.setMaxWidth(100);
        goBack2.setOnMouseEntered(e -> goBack2.setCursor(javafx.scene.Cursor.HAND));
        goBack2.setOnMouseClicked(e -> window.setScene(scene1));

        //SCENE 2 container
        VBox vbox2 = new VBox(10);
        vbox2.setStyle(
                "-fx-background-color:black;"
        );
        vbox2.setAlignment(Pos.CENTER);
        vbox2.getChildren().addAll(goBack2, title2, imgV2, txtID, txtMAKE, txtMODEL,
                txtYEAR, cmBox, transType, priceBox, search2
        );

        scene2 = new Scene(vbox2, 320, 520);
        scene2.getStylesheets()
                .add("http://fonts.googleapis.com/css?family=Gafata");

        //scene 2 events
        search2.setOnMouseEntered(e -> search2.setCursor(javafx.scene.Cursor.HAND));

        //CREATE a search2 query Car object
        search2.setOnMouseClicked(e -> displayResult());

        //SCENE 3+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++3
        //elements on interface
        Button btnCreate = new Button("Submit");
        btnCreate.setId("s3Buttons");
        btnCreate.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 20;"
                + "-fx-color: white;"
                + "-fx-background-color: Blue;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
        );
        btnCreate.setMinWidth(120);
        btnCreate.setOnMouseEntered(e
                -> btnCreate.setCursor(javafx.scene.Cursor.HAND));

        Button btnClear = new Button("Clear");
        btnClear.setId("s3Buttons");
        btnClear.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 20;"
                + "-fx-color: white;"
                + "-fx-background-color: Blue;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
        );
        btnClear.setMinWidth(120);
        btnClear.setOnMouseEntered(e
                -> btnClear.setCursor(javafx.scene.Cursor.HAND));
        btnClear.setOnAction(e -> clearFields());

        HBox scene3Buttons = new HBox(15);
        scene3Buttons.setAlignment(Pos.CENTER);
        scene3Buttons.getChildren().addAll(btnCreate, btnClear);

        txtNewMake = new TextField();
        txtNewMake.setPromptText("Make");
        txtNewMake.setMaxHeight(30);
        txtNewMake.setMaxWidth(300);

        txtNewModel = new TextField();
        txtNewModel.setPromptText("Model");
        txtNewModel.setMaxHeight(30);
        txtNewModel.setMaxWidth(300);

        txtNewYear = new TextField();
        txtNewYear.setPromptText("Year");
        txtNewYear.setMaxHeight(30);
        txtNewYear.setMaxWidth(300);

        cmNewBox = new ComboBox<>();
        cmNewBox.setItems(cList);
        cmNewBox.setPromptText("Colour");
        cmNewBox.setMaxHeight(30);
        cmNewBox.setMaxWidth(300);

        txtNote = new TextArea();
        txtNote.setPromptText("Write a description.");
        txtNote.setMaxHeight(80);
        txtNote.setMaxWidth(300);

        txtNewID = new TextField("ID: " + carList.length() + 1);
        txtNewID.setEditable(false);
        txtNewID.setMaxHeight(30);
        txtNewID.setMaxWidth(300);

        HBox hboxRadio = new HBox(30);
        hboxRadio.setAlignment(Pos.CENTER);

        automaticNew = new RadioButton("Automatic");
        automaticNew.setStyle("-fx-text-fill:White;");
        automaticNew.setUserData("Automatic");

        manualNew = new RadioButton("Manual");
        manualNew.setStyle("-fx-text-fill:White;");
        manualNew.setUserData("Manual");

        tg3 = new ToggleGroup();
        automaticNew.setToggleGroup(tg3);
        manualNew.setToggleGroup(tg3);
        hboxRadio.getChildren().addAll(automaticNew, manualNew);

        txtPrice = new TextField();
        txtPrice.setPromptText("Price");
        txtPrice.setMaxHeight(30);
        txtPrice.setMaxWidth(300);

        //alert for invalid input
        alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Data Entry Error");
        alertError.setHeaderText("Oops! It looks like you've missed something!");

        goBack3 = new Button("Home");
        goBack3.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 20;"
                + "-fx-color: white;"
                + "-fx-background-color: Green;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
                + "-fx-graphic: url(\"file:Resources/home.png\");"
        );

        goBack3.setAlignment(Pos.CENTER);
        goBack3.setPadding(new Insets(5, 5, 5, 5));
        goBack3.setMaxHeight(30);
        goBack3.setMaxWidth(100);

        //BROWSE IMAGES
        browseImageBox = new HBox(10);
        browseImageBox.setAlignment(Pos.CENTER);
        imgPath = new Label("");

        Button browse = new Button("Add Photo");
        browse.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 15;"
                + "-fx-color: white;"
                + "-fx-background-color: Blue;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
        );
        browseImageBox.getChildren().add(browse);
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("./Resources"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );

        browse.setOnMouseEntered(e -> browse.setCursor(javafx.scene.Cursor.HAND));
        browse.setOnAction(e -> {
            File selectedFile = fc.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                File file = new File(selectedFile.getPath());
                imgPath.setMaxWidth(180);
                browseImageBox.getChildren().addAll(imgPath);
                imgPath.setText(file.getPath());

            }
        });

        VBox vbox3 = new VBox(10);
        vbox3.setAlignment(Pos.CENTER);
        vbox3.setStyle(
                "-fx-background-color:black;"
        );
        vbox3.setAlignment(Pos.CENTER);
        vbox3.getChildren().addAll(goBack3, browseImageBox, txtNewMake, txtNewModel,
                txtNewYear, cmNewBox, txtNote, txtNewID, hboxRadio, txtPrice,
                scene3Buttons);

        scene3 = new Scene(vbox3, 320, 520);
        scene3.getStylesheets()
                .add("http://fonts.googleapis.com/css?family=Gafata");

        goBack3.setOnMouseEntered(e -> goBack3.setCursor(javafx.scene.Cursor.HAND));
        goBack3.setOnAction(e -> {
            window.setScene(scene1);
        });

        //add object to list
        btnCreate.setOnAction(e -> {
            Car car = new Car();

            //create ID
            car.setID(carList.length() + 1);

            try {
                idSet(car);
                makeCheckAndSet(car);
                modelCheckAndSet(car);
                yearCheckAndSet(car);
                colorSet(car);
                radioSet(car);
                priceCheckAndSet(car);
                noteSet(car);
                imageSet(car);

                System.out.println(car.toString());
                carList.addCar(car);

                alertInfo.setTitle("Car Tracker");
                alertInfo.setHeaderText("Success!");
                alertInfo.setContentText("You have added a new car!");
                alertInfo.showAndWait();

                clearFields();
                System.out.println(carList.toString());

            } catch (IllegalArgumentException ex) {
                alertError.setContentText(ex.getMessage());
                alertError.showAndWait();
            }
        });

        //SCENE 4+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++4
        goBack4 = new Button("Home");
        goToSearchScene = new Button(" Back to Search");
        goToSearchScene.setMinWidth(180);
        goToSearchScene.setOnAction(e -> {
            window.setScene(scene2);

        });
        goToSearchScene.setOnMouseEntered(e
                -> goToSearchScene.setCursor(javafx.scene.Cursor.HAND));

        goBack4.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 20;"
                + "-fx-color: white;"
                + "-fx-background-color: Green;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
                + "-fx-graphic: url(\"file:Resources/home.png\");"
        );
        goBack4.setAlignment(Pos.TOP_CENTER);
        goBack4.setPadding(new Insets(5, 5, 5, 5));
        goBack4.setMaxHeight(30);
        goBack4.setMaxWidth(100);

        goToSearchScene.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 20;"
                + "-fx-color: white;"
                + "-fx-background-color: Blue;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
                + "-fx-graphic: url(\"file:Resources/mag.png\");"
        );
        goToSearchScene.setAlignment(Pos.TOP_CENTER);
        goToSearchScene.setPadding(new Insets(5, 5, 5, 5));
        goToSearchScene.setMaxHeight(30);
        goToSearchScene.setMaxWidth(100);

        HBox hbHeaderS4 = new HBox();
        HBox.setMargin(goBack4, new Insets(5, 0, 10, 0));
        hbHeaderS4.getChildren().add(goBack4);
        hbHeaderS4.setAlignment(Pos.CENTER);

        vbS4 = new VBox(10);
        vbS4.setMaxHeight(Double.MAX_VALUE);
        vbS4.setAlignment(Pos.CENTER);
        vbS4.getChildren().addAll(hbHeaderS4, listView, goToSearchScene);
        vbS4.setStyle("-fx-background-color: Black;");

        listView.setOnMouseClicked(e -> {
            try {
                scene5isShowing = true;
                updateListing();
                window.setScene(scene5);
            } catch (NullPointerException ex) {
            }
        });

        scene4 = new Scene(vbS4, 320, 520);
        scene4.getStylesheets()
                .add("http://fonts.googleapis.com/css?family=Gafata");

        goBack4.setOnMouseEntered(e -> goBack4.setCursor(javafx.scene.Cursor.HAND));
        goBack4.setOnAction(e -> {
            window.setScene(scene1);
        });

        //SCENE 5 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 5
        goBack5 = new Button("Home");
        goBack5.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 20;"
                + "-fx-color: white;"
                + "-fx-background-color: Green;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
                + "-fx-graphic: url(\"file:Resources/home.png\");"
        );
        goBack5.setAlignment(Pos.CENTER);
        goBack5.setPadding(new Insets(5, 5, 5, 5));
        goBack5.setMaxHeight(30);
        goBack5.setMaxWidth(100);

        goBack5.setOnMouseEntered(e -> goBack5.setCursor(javafx.scene.Cursor.HAND));
        goBack5.setOnAction(e -> {
            scene5isShowing = false;
            window.setScene(scene1);
        });

        vbTopLeft = new VBox(5);
        vbTopLeft.setAlignment(Pos.CENTER);
        vbTopRight = new VBox(5);
        vbTopRight.setAlignment(Pos.BASELINE_LEFT);

        hbTop = new HBox(10);
        hbTop.setPadding(new Insets(5, 5, 5, 5));
        hbBottom = new HBox(10);

        txtNotes = new TextArea();
        txtNotes.setEditable(false);

        imgViewS5 = new ImageView();
        imgViewS5.setFitWidth(150);
        imgViewS5.setFitHeight(150);

        lblID = new Label("ID: ");
        lblPrice = new Label("$");
        lblMake = new Label("Make:");
        lblModel = new Label("Model:");
        lblYear = new Label("Year:");
        lblColour = new Label("Colour:");
        lblTrans = new Label("Transmission:");

        lblID.setStyle("-fx-text-fill: White;");
        lblPrice.setStyle("-fx-text-fill: White;");
        lblMake.setStyle("-fx-text-fill: White;");
        lblModel.setStyle("-fx-text-fill: White;");
        lblYear.setStyle("-fx-text-fill: White;");
        lblColour.setStyle("-fx-text-fill: White;");
        lblTrans.setStyle("-fx-text-fill: White;");

        btnPrev = new Button("< Last");
        btnPrev.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 15;"
                + "-fx-color: white;"
                + "-fx-background-color: Green;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
        );
        btnPrev.setOnAction(e -> {
            try {
                if (listView.getSelectionModel().getSelectedIndex() <= 0) {
                } else {
                    listView.getSelectionModel()
                            .select(listView.getSelectionModel().getSelectedIndex() - 1);
                    updateListing();
                }
            } catch (Exception ex) {
            }
        });
        btnNext = new Button("Next >");
        btnNext.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 15;"
                + "-fx-color: white;"
                + "-fx-background-color: Green;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
        );
        btnNext.setOnAction(e -> {
            try {

                listView.getSelectionModel()
                        .select(listView.getSelectionModel().getSelectedIndex() + 1);
                updateListing();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnPrev.setOnMouseEntered(e -> btnPrev.setCursor(javafx.scene.Cursor.HAND));
        btnNext.setOnMouseEntered(e -> btnNext.setCursor(javafx.scene.Cursor.HAND));

        vbTopLeft.getChildren().addAll(imgViewS5, lblPrice);
        vbTopRight.getChildren().addAll(
                lblMake, lblModel, lblYear, lblColour, lblTrans);

        hbTop.getChildren().addAll(vbTopLeft, vbTopRight);
        hbBottom.getChildren().addAll(btnPrev, btnNext);
        hbBottom.setAlignment(Pos.CENTER);

        vbS5 = new VBox(10);
        vbS5.setAlignment(Pos.CENTER);
        vbS5.setStyle(
                "-fx-background-color:black;"
        );
        vbS5.getChildren().addAll(goBack5, hbTop, txtNotes, hbBottom);

        scene5 = new Scene(vbS5, 320, 520);
        scene5.getStylesheets()
                .add("http://fonts.googleapis.com/css?family=Gafata");

        //SCENE 6++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++6
        goBack6 = new Button("Home");
        goBack6.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 20;"
                + "-fx-color: white;"
                + "-fx-background-color: Green;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
                + "-fx-graphic: url(\"file:Resources/home.png\");"
        );
        goBack6.setAlignment(Pos.TOP_CENTER);
        goBack6.setPadding(new Insets(5, 5, 5, 5));
        goBack6.setMaxHeight(30);
        goBack6.setMaxWidth(100);

        HBox hbHeaderS6 = new HBox();
        HBox.setMargin(goBack6, new Insets(5, 0, 10, 0));
        hbHeaderS6.getChildren().add(goBack6);
        hbHeaderS6.setAlignment(Pos.CENTER);

        vbS6 = new VBox(10);
        vbS6.setMaxHeight(Double.MAX_VALUE);
        vbS6.setAlignment(Pos.CENTER);
        vbS6.getChildren().addAll(hbHeaderS6, carListView);
        vbS6.setStyle("-fx-background-color: Black;");

        scene6 = new Scene(vbS6, 320, 520);
        scene6.getStylesheets()
                .add("http://fonts.googleapis.com/css?family=Gafata");

        goBack6.setOnMouseEntered(e -> goBack6.setCursor(javafx.scene.Cursor.HAND));
        goBack6.setOnAction(e -> {
            window.setScene(scene1);
        });

        //SCENE 7 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 7
        goBack7 = new Button("Home");
        goBack7.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 20;"
                + "-fx-color: white;"
                + "-fx-background-color: Green;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
                + "-fx-graphic: url(\"file:Resources/home.png\");"
        );
        goBack7.setAlignment(Pos.CENTER);
        goBack7.setPadding(new Insets(5, 5, 5, 5));
        goBack7.setMaxHeight(30);
        goBack7.setMaxWidth(100);

        goBack7.setOnMouseEntered(e -> goBack7.setCursor(javafx.scene.Cursor.HAND));
        goBack7.setOnAction(e -> {
            scene5isShowing = false;
            window.setScene(scene1);
        });

        vbTopLeft2 = new VBox(5);
        vbTopLeft2.setAlignment(Pos.CENTER);

        vbTopRight2 = new VBox(5);
        vbTopRight2.setAlignment(Pos.BASELINE_LEFT);

        hbTop2 = new HBox(10);
        hbTop2.setPadding(new Insets(5, 5, 5, 5));
        hbBottom2 = new HBox(10);

        txtNotes2 = new TextArea();

        imgViewS7 = new ImageView();
        imgViewS7.setFitWidth(150);
        imgViewS7.setFitHeight(150);

        lblID2 = new TextField("ID: ");
        lblPrice2 = new TextField("$");
        lblMake2 = new TextField("Make:");
        lblModel2 = new TextField("Model:");
        lblYear2 = new TextField("Year:");
        lblColour2 = new TextField("Colour:");
        lblTrans2 = new TextField("Transmission:");

        lblPrice2.setStyle("-fx-control-inner-background: black;"
                + "-fx-font-size: 20;"
        );

        btnUpdate = new Button("Update");
        btnUpdate.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 15;"
                + "-fx-color: white;"
                + "-fx-background-color: Blue;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
        );
        btnPrev2 = new Button("< Last");
        btnPrev2.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 15;"
                + "-fx-color: white;"
                + "-fx-background-color: Green;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
        );
        btnPrev2.setOnAction(e -> {
            try {
                if (carListView.getSelectionModel().getSelectedIndex() <= 0) {
                } else {
                    carListView.getSelectionModel()
                            .select(carListView.getSelectionModel().getSelectedIndex() - 1);
                    updateAllCarsListing();
                }
            } catch (Exception ex) {
            }
        });
        btnNext2 = new Button("Next >");
        btnNext2.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 15;"
                + "-fx-color: white;"
                + "-fx-background-color: Green;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
        );
        btnNext2.setOnAction(e -> {
            try {

                carListView.getSelectionModel()
                        .select(carListView.getSelectionModel().getSelectedIndex() + 1);
                updateAllCarsListing();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnUpdate = new Button("Update");
        btnUpdate.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 15;"
                + "-fx-color: white;"
                + "-fx-background-color: Blue;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
        );

        //update the selected carList record
        //need to create a car object with the updated information
        //then set(index, newcar) to replace the old car 
        btnUpdate.setOnAction(e -> {

            alertConfirm.setTitle("Update Car");
            alertConfirm.setHeaderText(null);
            alertConfirm.setContentText("Are you sure you wish to update?");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK) {
                int carIndex = carListView.getSelectionModel().getSelectedIndex();
                Car car = new Car();
                car.setID(carIndex + 1);
                makeCheckAndSet(car);
                modelCheckAndSet(car);
                yearCheckAndSet(car);
                colorSet(car);
                radioSet(car);
                priceCheckAndSet(car);
                noteSet(car);
                car.setImagePath(carListView.getSelectionModel()
                        .getSelectedItem().getImagePath());
                car.setImage(carList.get(carIndex).getImage());
                carList.set(carIndex, car);

            }
        });

        btnSell = new Button("Sell");
        btnSell.setStyle(
                "-fx-font-family: Gafata;"
                + "-fx-font-size: 15;"
                + "-fx-color: white;"
                + "-fx-background-color: Blue;"
                + "-fx-border-radius: none;"
                + "-fx-text-fill: white;"
                + "-fx-text-align: center;"
        );
        btnPrev2.setOnMouseEntered(e -> btnPrev2.setCursor(javafx.scene.Cursor.HAND));
        btnNext2.setOnMouseEntered(e -> btnNext2.setCursor(javafx.scene.Cursor.HAND));
        btnUpdate.setOnMouseEntered(e -> btnUpdate.setCursor(javafx.scene.Cursor.HAND));
        btnSell.setOnMouseEntered(e -> btnSell.setCursor(javafx.scene.Cursor.HAND));

        btnSell.setOnAction(e -> {

            alertConfirm.setTitle("Sell Car");
            alertConfirm.setHeaderText(null);
            alertConfirm.setContentText("Are you sure you wish to sell this car?");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK) {

                Car car = carListView.getSelectionModel().getSelectedItem();
                car.setPrice(0);
                lblPrice.setText("SOLD");
            }
        });

        vbTopLeft2.getChildren().addAll(imgViewS7, lblPrice2);
        vbTopRight2.getChildren().addAll(
                lblMake2, lblModel2, lblYear2, lblColour2, lblTrans2);

        hbTop2.getChildren().addAll(vbTopLeft2, vbTopRight2);
        hbBottom2.getChildren().addAll(btnPrev2, btnUpdate, btnSell, btnNext2);
        hbBottom2.setAlignment(Pos.CENTER);

        vbS7 = new VBox(10);
        vbS7.setAlignment(Pos.CENTER);
        vbS7.setStyle(
                "-fx-background-color:black;"
        );
        vbS7.getChildren().addAll(goBack7, hbTop2, txtNotes2, hbBottom2);
        vbS7.setMaxWidth(320);
        vbS7.setMaxHeight(520);

        scene7 = new Scene(vbS7, 320, 520);
        scene7.getStylesheets()
                .add("http://fonts.googleapis.com/css?family=Gafata");

        carListView.setOnMouseClicked(e -> {
            try {
                scene7isShowing = true;
                updateAllCarsListing();
                window.setScene(scene7);
            } catch (NullPointerException ex) {
            }
        });

        //END OF SCENES
        //WINDOW STUFF
        window.setScene(scene1);
        window.setTitle("Auto Locator");
        window.setMinWidth(320);
        window.setMinHeight(520);
        window.setMaxWidth(320);
        window.setMaxHeight(520);
        window.getIcons().add(logo);
        window.show();
        window.setOnCloseRequest(e -> {

            System.out.println("Closing Application. Goodbye!");
            try {
                uploadFile();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    //method area+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    
    /*
    * this method is used to load carlists from txt file, carList.dat
    */
    void downloadFile() throws FileNotFoundException {
        //read
        System.out.println("Start Loading!");
        String path = "Data/";

        File inputFile = new File(path + "carlist.dat");

        Scanner scan = new Scanner(inputFile);

        while (scan.hasNext()) {
            String query = scan.nextLine();
            ArrayList<String> stringArray = new ArrayList();
            Splitter tokenizer = Splitter.on('|').omitEmptyStrings().trimResults();
            Iterable<String> tokens = tokenizer.split(query);
            for (String token : tokens) {
                stringArray.add(token);
            }
            Car car = new Car();
            for (int i = 0; i < stringArray.size(); i++) {

                switch (i) {

                    case 0:
                        car.setID(Integer.parseInt(stringArray.get(i)));
                        break;

                    case 1:
                        car.setMake(stringArray.get(i));
                        break;

                    case 2:
                        car.setModel(stringArray.get(i));
                        break;

                    case 3:
                        car.setYear(Integer.parseInt(stringArray.get(i)));
                        break;

                    case 4:
                        car.setColour(stringArray.get(i));
                        break;

                    case 5:
                        car.setTrans(stringArray.get(i));
                        break;

                    case 6:
                        car.setPrice(Double.parseDouble(stringArray.get(i)));
                        break;

                    case 7:
                        car.setImagePath(stringArray.get(i));
                        break;

                    case 8:
                        car.setDesc(stringArray.get(i));
                        break;

                }
            }
            car.setImageFromPath();
            carList.addCar(car);
        }
        scan.close();
        System.out.println("Loading Complete!");
    }

    /*
    * this method will overwrite the text file with new information
    */
    void uploadFile() throws FileNotFoundException {
        //write
        String path = "Data/";

        File fileName = new File(path + "carList.dat");

        PrintWriter outputFile = new PrintWriter(fileName);

        for (int i = 0; i < carList.length(); i++) {
            outputFile.println(carList.get(i).toString());
        }
        outputFile.close();
        System.out.println("Upload successful!");
    }

    
    /*
    * this method loads the list view with cars 
    * @param carList the carList object
    */
    void loadAllCarsList(CarList carList) {
        carListView.getItems().clear();
        for (int i = 0; i < carList.length(); i++) {
            allCarsList.add(carList.get(i));
        }
        carListView.setItems(allCarsList);
    }


    /*
    * this method loads scene 5 with selected car object
    */
    void updateListing() {

        Car selection = (Car) listView.getSelectionModel().getSelectedItem();
        if (scene5isShowing) {

            lblMake.setText(String.format(
                    "%s", selection.getMake()));
            lblModel.setText(String.format(
                    "%s", selection.getModel()));
            lblYear.setText(String.format(
                    "%s", selection.getYear()));
            lblColour.setText(String.format(
                    "%s", selection.getColour()));
            lblTrans.setText(String.format(
                    "%s", selection.getTrans()));
            txtNotes.setText(String.format(
                    "%s", selection.getDesc()));
            imgViewS5.setImage(selection.getImage());
            lblPrice.setText(String.format(
                    "$ %.2f", selection.getPrice()));
            if (selection.getPrice() > 0) {
                lblPrice.setStyle("-fx-text-fill: green;");
            } else {
                lblPrice.setStyle("-fx-text-fill: red;");
                lblPrice.setText("SOLD");
            }
        }
    }


    /*
    * this method loads scene 7 with selected car object
    */
    void updateAllCarsListing() {

        Car selection = (Car) carListView.getSelectionModel().getSelectedItem();
        if (scene7isShowing) {

            lblMake2.setText(String.format(
                    "%s", selection.getMake()));
            lblModel2.setText(String.format(
                    "%s", selection.getModel()));
            lblYear2.setText(String.format(
                    "%s", selection.getYear()));
            lblColour2.setText(String.format(
                    "%s", selection.getColour()));
            lblTrans2.setText(String.format(
                    "%s", selection.getTrans()));
            txtNotes2.setText(String.format(
                    "%s", selection.getDesc()));
            imgViewS7.setImage(selection.getImage());
            lblPrice2.setText(String.format(
                    "%.2f", selection.getPrice()));
            if (selection.getPrice() > 0) {
                lblPrice2.setStyle("-fx-text-fill: green;");
            } else {
                lblPrice2.setStyle("-fx-text-fill: red;");
                lblPrice2.setText("SOLD");
            }
        }
    }

    
    /*
    * sets the id by length plus 1 automatically
    * @param car the car object
    */
    void idSet(Car car) {
        int id = carList.length() + 1;
        car.setID(id);
    }

    /*
    * sets the make
    * @param car the car object
    */
    void makeCheckAndSet(Car car) {
        String make;
        if (scene7isShowing) {
            make = lblMake2.getText();
        } else {
            make = txtNewMake.getText();
        }
        car.setMake(make);
    }


    /*
    * sets the model
    * @param car the car object
    */
    void modelCheckAndSet(Car car) {
        String model;
        if (scene7isShowing) {
            model = lblModel2.getText();
        } else {
            model = txtNewModel.getText();
        }
        car.setModel(model);
    }

    /*
    * sets the year
    * @param car the car object
    */
    void yearCheckAndSet(Car car) {

        try {
            int year;

            if (scene7isShowing) {
                year = Integer.parseInt(lblYear2.getText());
            } else {
                year = Integer.parseInt(txtNewYear.getText());
            }
            car.setYear(year);
        } //catch exception if user enters character
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Please enter a valid year.");

        }
    }

    /*
    * sets the price
    * @param car the car object
    */
    void priceCheckAndSet(Car car) {

        try {
            double price;
            if (scene7isShowing) {
                price = Double.parseDouble(lblPrice2.getText());
            } else {
                price = Double.parseDouble(txtPrice.getText());
            }
            car.setPrice(price);
        } //catch exception if user enters character
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Please enter a valid price.");

        }
    }

    /*
    * sets the color
    * @param car the car object
    */
    void colorSet(Car car) {

        int number = cmNewBox.getSelectionModel().getSelectedIndex();
        try {
            if (scene7isShowing) {
                car.setColour(lblColour2.getText());
            } else {
                car.setColour(strColours[number]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Please choose a colour!");
        }
    }

    /*
    * sets the note
    * @param car the car object
    */
    void noteSet(Car car) {
        String note;
        if (scene7isShowing) {
            note = txtNotes2.getText();
        } else {
            note = txtNote.getText();
        }
        car.setDesc(note);

    }

    /*
    * sets the transmission choice
    * @param car the car object
    */
    void radioSet(Car car) {

        try {
            String value;
            if (scene7isShowing) {
                value = lblTrans2.getText();
            } else {
                RadioButton selectedRadioButton
                        = (RadioButton) tg3.getSelectedToggle();
                value = selectedRadioButton.getText();
            }
            car.setTrans(value);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Please choose a transmission type!");
        }
    }

    /*
    * sets the image
    * @param car the car object
    */
    void imageSet(Car car) throws StringIndexOutOfBoundsException {

        String imagePath = null;
        Image tmpImg = null;
        try {
            imagePath = "file:Resources" + imgPath.getText().substring(
                    imgPath.getText().lastIndexOf("/"));
            car.setImagePath(imagePath);
            tmpImg = new Image(imagePath);
        } catch (Exception e) {
        }
        car.setImage(tmpImg);
    }

    /*
    * clears search text fields
    */
    void clearSearchFields() {
        txtMAKE.setText("");
        txtMODEL.setText("");
        txtYEAR.setText("");
        minPrice.setText("");
        maxPrice.setText("");
        cmBox.getSelectionModel().select(-1);
        automatic.setSelected(false);
        manual.setSelected(false);
        txtID.setText("");
    }

    /*
    * clears clears text fields
    */
    void clearFields() {
        txtNewMake.setText("");
        txtNewModel.setText("");
        txtNewYear.setText("");
        txtPrice.setText("");
        cmNewBox.getSelectionModel().select(-1);
        txtNote.setText("");
        automaticNew.setSelected(false);
        manualNew.setSelected(false);
        imgPath.setText("");
        txtNewID.setText("ID: " + (carList.length() + 1));
        browseImageBox.getChildren().remove(imgPath);

    }

    /*
    * main search function
    */
    void displayResult() {

        //store objects which are matched finally
        searchBox.clear();

        if (!"".equals(txtID.getText())) { //id is not empty
            try {
                for (int i = 0; i < carList.length(); i++) {
                    if (carList.get(i).getID() == Integer.parseInt(txtID.getText())) {
                        searchBox.add(carList.get(i));
                    }
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID must be a number!");
            }

            //if user doesnt use id to search items, check from top to down(from Make to Max Price)
        } else {

            boolean firstTime = true;//if it is the first attribute which user filled in

            //check make, this one is always the first to check
            if (!"".equals(txtMAKE.getText())) { //if make is filled with info.
                for (int i = 0; i < carList.length(); i++) {
                    if (txtMAKE.getText().equalsIgnoreCase(carList.get(i).getMake())) {
                        searchBox.add(carList.get(i)); //if match, then add into searchBox
                    }
                }
                firstTime = false;
            }

            //check model
            if (!"".equals(txtMODEL.getText())) {
                if (firstTime) {  //if this is the first attribute user fills in, then we need to forloop carList
                    for (int i = 0; i < carList.length(); i++) {
                        if (txtMODEL.getText().equalsIgnoreCase(carList.get(i).getModel())) {
                            searchBox.add(carList.get(i));
                        }
                    }
                    firstTime = false;
                } else {

                    //if this is not the first attribute used filled, then we need to forloop searchBox
                    for (int i = 0; i < searchBox.size(); i++) {
                        if (!txtMODEL.getText().equalsIgnoreCase(searchBox.get(i).getModel())) {
                            searchBox.remove(searchBox.get(i)); //if doesnt match, we remove object which doesnt match, and keep matched one
                        }
                    }

                }
            }

            //check year
            if (!"".equals(txtYEAR.getText())) {
                try {
                    if (firstTime == true) {
                        for (int i = 0; i < carList.length(); i++) {
                            if (Integer.parseInt(txtYEAR.getText()) == carList.get(i).getYear()) {
                                searchBox.add(carList.get(i));
                            }
                        }
                        firstTime = false;
                    } else {

                        for (int i = 0; i < searchBox.size(); i++) {
                            if (Integer.parseInt(txtYEAR.getText()) != searchBox.get(i).getYear()) {
                                searchBox.remove(searchBox.get(i));
                            }
                        }

                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Year must be a number!");
                }
            }

            //check color
            if (cmBox.getSelectionModel().getSelectedIndex() != -1) {
                int number = cmBox.getSelectionModel().getSelectedIndex();

                if (firstTime) {
                    for (int i = 0; i < carList.length(); i++) {
                        if (strColours[number].equals(carList.get(i).getColour())) {
                            searchBox.add(carList.get(i));
                        }
                    }
                    firstTime = false;
                } else {

                    for (int i = 0; i < searchBox.size(); i++) {
                        if (!strColours[number].equals(searchBox.get(i).getColour())) {
                            searchBox.remove(searchBox.get(i));
                        }
                    }

                }
            }

            //check a or m
            if (automatic.isSelected() || manual.isSelected()) {
                RadioButton selectedRadioButton = (RadioButton) tg2.getSelectedToggle();
                String value = selectedRadioButton.getText();

                if (firstTime) {
                    for (int i = 0; i < carList.length(); i++) {
                        if (value.equals(carList.get(i).getTrans())) {
                            searchBox.add(carList.get(i));
                        }
                    }
                    firstTime = false;
                } else {

                    for (int i = 0; i < searchBox.size(); i++) {
                        if (!value.equals(searchBox.get(i).getTrans())) {
                            searchBox.remove(searchBox.get(i));
                        }
                    }

                }
            }

            //check min price
            if (!"".equals(minPrice.getText())) {
                double price = Double.parseDouble(minPrice.getText());

                if (firstTime) {
                    for (int i = 0; i < carList.length(); i++) {
                        if (carList.get(i).getPrice() >= price) {
                            searchBox.add(carList.get(i));
                        }
                    }
                    firstTime = false;
                } else {

                    for (int i = 0; i < searchBox.size(); i++) {
                        if (searchBox.get(i).getPrice() < price) {
                            searchBox.remove(searchBox.get(i));
                        }
                    }

                }
            }

            //check max price
            if (!"".equals(maxPrice.getText())) {
                double price = Double.parseDouble(maxPrice.getText());

                if (firstTime) {
                    for (int i = 0; i < carList.length(); i++) {
                        if (carList.get(i).getPrice() <= price) {
                            searchBox.add(carList.get(i));
                        }
                    }
                } else {

                    for (int i = 0; i < searchBox.size(); i++) {
                        if (searchBox.get(i).getPrice() > price) {
                            searchBox.remove(searchBox.get(i));
                        }
                    }

                }
            }

        }
        if (carList.length() == 0) {
            alertError.setTitle("Car Tracker");
            alertError.setHeaderText("Search Results");
            alertError.setContentText("Sorry, you haven't added any items in system.");
            alertError.showAndWait();
        } else if (searchBox.isEmpty()) {

            alertInfo.setTitle("Car Tracker");
            alertInfo.setHeaderText("Search Results");
            alertInfo.setContentText("Sorry, no results matched your query.");
            alertInfo.showAndWait();

        } else {
            window.setScene(scene4);
            listView.setItems(searchBox);
        }
    }
}
