module com.example.poissons {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.poissons to javafx.fxml;
    exports com.example.poissons;
}