module com.project.project2.controller {
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

    opens com.project.project2.controller to javafx.graphics, javafx.fxml;
    exports com.project.project2;
}
