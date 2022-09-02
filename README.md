# CarRentalManagement

This is a program used to manage car rentals

How to run:

#1: Download Mssql Management studio and Inteliji

#2:

-- Clone this repository to your local folder

-- Run maven

-- Ctrl + alt + s --> Path Variables --> Create PATH_TO_FX variable and the value set the directory javafx-sdk-18.0.1/lib in src package --> apply

-- Run Main --> it will show an error --> Edit Configurations --> Modify options --> Add VM options --> set --module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml --> apply

#3:

-- Open SQL Server 2019 Configuration Management --> ......Network Configurations --> set TCP/IP enable

-- Create account for Mssql Management studio

-- Go to DbConnection.java set with your account:

        ds.setServerName(serverName);
        ds.setUser(userName);
        ds.setPassword(password);
-- Run file CarRentalDB.sql in project with Mssql Management studio

-- Run Main then try to log in
