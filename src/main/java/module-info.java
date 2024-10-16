module com.example.generaltemplate {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.generaltemplate to javafx.fxml;
    exports com.example.generaltemplate;
    exports com.example.generaltemplate.moveables;
    opens com.example.generaltemplate.moveables to javafx.fxml;
    exports com.example.generaltemplate.entities;
    opens com.example.generaltemplate.entities to javafx.fxml;
    exports com.example.generaltemplate.detectables;
    opens com.example.generaltemplate.detectables to javafx.fxml;
    exports com.example.generaltemplate.actionables;
    opens com.example.generaltemplate.actionables to javafx.fxml;
    exports com.example.generaltemplate.attacks;
    opens com.example.generaltemplate.attacks to javafx.fxml;
}