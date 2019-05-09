# Pouloum

## Prerequesite

- Install NetBeans (8.2 if possible, earlier versions may work)
- On NetBeans, install an apache TOMCAT server.
  - Download apache-tomcat-8.5.40.zip from https://tomcat.apache.org/download-80.cgi
  - Select the downloaded file and unzip it.
  - On NetBeans, on the Services tab, right click on "Servers" and click on "Add Server...".
  - Select "Apache Tomcat".
  - Specify the location of your apache-tomcat-8.5.40 folder.
  - Check "Use private configuration Folder.
  - Put "tomcat" as the login and the password.
  - Click on finish.
- On NetBeans, create a database for this project.
  - On the Services tab, click on "Databases", right click on "Java DB" and click on "Create Database...".
  - Specify "Pouloum-DB" as the Dabase name.
  - Put "pouloum" as the login and the password.
  - Select the location of your database.
  - Click on OK.
  

## Installation

- Open Pouloum-Project in NetBeans.
- Clean and build Pouloum-Project.
- Don't forget to connect your previously created database.
- Run SetupDB in Pouloum-DASI/Sources Packages/com.mycompany.pouloum.setup to fill the database.
- Run Pouloum-IHM-WEB to deploy the GUI.

Ready to go!
