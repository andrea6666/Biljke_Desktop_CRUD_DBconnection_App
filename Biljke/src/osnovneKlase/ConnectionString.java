package osnovneKlase;

/**
 * Created by: Andrea
 * 
 * This class is used to replace all connection Strings wherever in the program is needed, onse changed here it will work in whole application
 * In order to create database and table you need to follow next in sql:
 * --
--Create Database: `biljke`
-- --------------------------------------------------------
-- Table structure for table `table_biljke`
--
DROP TABLE IF EXISTS `table_biljke`;
CREATE TABLE IF NOT EXISTS `table_biljke` (
  `RB` int NOT NULL AUTO_INCREMENT,
  `ime_biljke` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `datum_poslednjeg_zalivanja` date NOT NULL,
  `datum_sledeceg_zalivanja` date NOT NULL,
  `br_dana_na_koji_se_zaliva` int NOT NULL,
  `uputstvo` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `slika` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`RB`),
  UNIQUE KEY `ime_biljke` (`ime_biljke`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

 */

public class ConnectionString {
	
	protected String connectionString;

	public ConnectionString() {
		 
		 
		// in order to connect to the database you need to replace your_host, your_username, your_password with your own 
		  connectionString = "jdbc:mysql://your_host/biljke?user=your_username&password=your_password";

		  
		  
		 

	}
}
