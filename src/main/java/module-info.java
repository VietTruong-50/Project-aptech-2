module com.project.project2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires spring.boot.starter.data.jpa;
    requires spring.data.jpa;
    requires spring.beans;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires java.naming;
    requires static lombok;
    requires com.jfoenix;
    requires java.desktop;
    requires PDFjet;

    opens com.project.project2.controller to javafx.graphics, javafx.fxml;
    opens com.project.project2.model to javafx.base;
    exports com.project.project2;
    opens com.project.project2.service to javafx.fxml, javafx.graphics;
}
