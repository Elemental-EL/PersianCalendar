package com.example.persiancalendar;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.PersianCalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class HelloController implements Initializable {
    private PersianCalendar dateFocus;
    private PersianCalendar today;

    private String[] persianMonthNames = {"فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = new PersianCalendar();
        today = new PersianCalendar();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus.add(PersianCalendar.MONTH, -1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus.add(PersianCalendar.MONTH, 1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar(){
        year.setText(String.valueOf(dateFocus.get(PersianCalendar.YEAR)));
        int currentMonth = dateFocus.get(PersianCalendar.MONTH);
        String monthName = persianMonthNames[currentMonth];
        month.setText(monthName);
        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();
        int monthMaxDate = dateFocus.getActualMaximum(PersianCalendar.DAY_OF_MONTH);
        PersianCalendar firstDayOfMonth = new PersianCalendar();
        firstDayOfMonth.setTimeInMillis(dateFocus.getTimeInMillis());
        firstDayOfMonth.set(PersianCalendar.DAY_OF_MONTH, 1);
        int dateOffset = firstDayOfMonth.get(PersianCalendar.DAY_OF_WEEK) ;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.WHITE);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);
                int calculatedDate = (j - dateOffset) + 1 + (7 * i);
                if (calculatedDate >= 1 && calculatedDate <= monthMaxDate) {
                    StackPane textContainer = new StackPane();
                    textContainer.setPrefSize(rectangleWidth, rectangleHeight);
                    Text date = new Text(String.valueOf(calculatedDate));
                    date.setTextAlignment(TextAlignment.CENTER);
                    date.setFont(Font.font(22));
                    date.setFill(Color.WHITE);
                    textContainer.getChildren().add(date);
                    stackPane.getChildren().add(textContainer);
                    if (today.get(PersianCalendar.YEAR) == dateFocus.get(PersianCalendar.YEAR) && today.get(PersianCalendar.MONTH) == dateFocus.get(PersianCalendar.MONTH) && today.get(PersianCalendar.DAY_OF_MONTH) == calculatedDate) {
                        rectangle.setStroke(Color.web("#436850"));
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }
}