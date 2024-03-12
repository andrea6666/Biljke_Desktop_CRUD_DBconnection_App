package osnovneKlase;

import java.time.LocalDateTime;

public class RacunanjeBrojaDanaZaBoju {
	
	/**
	 * Created by: Andrea
	 * 
	 * This class calculates the number of days delayed, current date, or one day earlier for watering,
	 * which determines the color through the "MojRenderer" class
	 * It calculates the number of days from the current date and subtracts the year, month, and day from the current date.
	 * It also calculates the exact number of days in the month and accounts for leap years (modulus 4 - %4) because February has 29 days.
	 * The class returns the number of days for the color: orange, red, black.
	 */

	public RacunanjeBrojaDanaZaBoju() {

	}

	public static int razdvajanjeDatumaNaDMG(String aktuelniDatum) {
		int brojDanaIzmedju = 0;
		int daysInMonths1 = 0;
		int daysInMonths2 = 0;
		String date = aktuelniDatum;
		LocalDateTime date3 = LocalDateTime.now();
		String date2 = date3.toString();

		String day1 = null;
		String month1 = null;
		String year1 = null;
		String day2 = null;
		String month2 = null;
		String year2 = null;
		
		int[] brDanaUmjesecuRegularnaGod = { 
				31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 
				30, 31 };
		int[] brDanaUmjesecuPrestupnaGod = { 
				31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 
				30, 31 };
		
		for (int i = 0; i < date.length(); i++) {
			day1 = date.substring(0,2);
			month1 = date.substring(3, 5);
			year1 = date.substring(6,10);
		} 
		for (int j = 0; j < date2.length(); j++) {
			year2 = date2.substring(0, 4);
			month2 = date2.substring(5, 7);
			day2 = date2.substring(8, 10);
		} 
		int daysInyear1 = Integer.parseInt(year1) * 365;
		if (Integer.parseInt(year1) % 4 == 0)
			for (int k = 0; k < Integer.parseInt(month1) - 1; k++)
				daysInMonths1 += brDanaUmjesecuPrestupnaGod[k];  
		if (Integer.parseInt(year1) % 4 > 0)
			for (int k = 0; k < Integer.parseInt(month1) - 1; k++)
				daysInMonths1 += brDanaUmjesecuRegularnaGod[k];  
		int days1 = Integer.parseInt(day1);
		int daysInyear2 = Integer.parseInt(year2) * 365;
		if (Integer.parseInt(year2) % 4 == 0)
			for (int k = 0; k < Integer.parseInt(month2) - 1; k++)
				daysInMonths2 += brDanaUmjesecuPrestupnaGod[k];  
		if (Integer.parseInt(year2) % 4 > 0)
			for (int k = 0; k < Integer.parseInt(month2) - 1; k++)
				daysInMonths2 += brDanaUmjesecuRegularnaGod[k];  
		int days2 = Integer.parseInt(day2);
		int year = daysInyear1 - daysInyear2;
		int month = daysInMonths1 - daysInMonths2;
		int days = days1 - days2;
		brojDanaIzmedju = year + month + days;
		
		return brojDanaIzmedju;
	}
}
