## Biljke Desktop CRUD DBconnected App
### This is a desktop application connected to a database. It is has all CRUD operations and it is running if connection with database is established, if not there is window displaying that there is no connection. 
### Application is written in Serbian language but comments are modified in English. It was created to show possibilities of swing and JFrame, it has transparent screens and no default frame, graphic design isn't the best since I am not a graphical designer but it shows all various graphical possibilities of an desktop application.
### There is a lot of code that can be reduced and better organized, data base connection in many cases can be handheld better and it can be encrypted. 
### Application is working fine this way, I had no need to continue developing and improving it further so it was published like this.

### Running Biljke

#### In order to test or use application download code and run it within java compiler 
#### In src folder there is class [ConnectionString.java](Biljke/src/osnovneKlase/ConnectionString.java) where You should put your own database name, ysername and password.
#### Also You need to create your own database there is sql code in [biljke.sql](biljke.sql) file for creating it.

##### Required prerequisites:
- **Java Runtime Environment (JRE):** Ensure you have Java installed on your machine.
- **Java Version:** This application requires Java 17.0 or later. It is recommended to use the LTS release for long-term support. You can download Java 17 from [AdoptOpenJDK](https://adoptopenjdk.net/) or [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html).


### Adding a plant:
#### Here is shown how to add a new plant. Plant is added to the database and it has to have all elements name, instruction, start and end date for watering, and number of days for watering. If any of the elements is missing there will window with warning that you need to fill in all the elements.
#### Plant name has to be unique. 
#### Here we can se different colors of date fields black red and orange, they represent notification if black you are a day late for watering the plant, if red it is watering day and if the color is orange the next day is watering day.
#### When the button "Zaliveno" is pressed the dates move,  first date to the todays date, and second is calculated by adding the number of the days that are in database for watering for that plant. If you press the button and the date is today it wont respond since you can't water plant in the future time. 

https://github.com/andrea6666/Biljke_Desktop_CRUD_DBconnection_App/assets/45822712/430c4b4f-f243-4913-a16c-44e490aa37c6

### Deleting a plant:
#### Here you can see how to delete the plant. Plants are read in the dropdown list from a database each time. When you delete a plant there is window asking if you are sure, if you don't select plant there is window asking you to select plant and if you delete the plant list doesn't show the plant any longer. 

https://github.com/andrea6666/Biljke_Desktop_CRUD_DBconnection_App/assets/45822712/0619df79-0947-4d33-8d66-e6596b09fc57

### Editing a plant:
#### Here you can edit an existing plant, it should be selected from dropdown list, and than any field can be changed, if the plant isn't selected there is warning window that will tell you that you didn't select the plant. There is no need to change all fields just required once and plant name has to be unique it can't exist in database.

https://github.com/andrea6666/Biljke_Desktop_CRUD_DBconnection_App/assets/45822712/64fa8b78-d57e-4c15-a0e8-ff9e293a31cd

### No connection window:
#### Here is the window that pops up if there is no database connection. It runs instead of main application window, when closed the program stops running.

https://github.com/andrea6666/Biljke_Desktop_CRUD_DBconnection_App/assets/45822712/35f74a86-8ea5-4366-a0c2-6fcdc86d3831




