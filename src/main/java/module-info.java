module com.example.persiancalendar {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.ibm.icu;


    opens com.example.persiancalendar to javafx.fxml;
    exports com.example.persiancalendar;
}