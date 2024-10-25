import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ManageEastAsiaCountries {

    private final static Scanner in = new Scanner(System.in);
    private final static String COUNTRY_VALID = "[A-Za-z ]*";  // Chấp nhận ký tự A-Z, a-z và khoảng trắng
    private final static String COUNTRY_AREA = "[0-9]+(\\.[0-9]+)?"; // Chấp nhận số nguyên và số thực cho diện tích

    // Display menu
    public static int menu() {
        System.out.println("                               MENU                                        ");
        System.out.println("=======================================================================");
        System.out.println("1. Input the information of 11 countries in East Asia");
        System.out.println("2. Display the information of country you've just input");
        System.out.println("3. Search the information of country by user-entered name");
        System.out.println("4. Display the information of countries sorted name in ascending");
        System.out.println("5. Exit");
        System.out.println("=======================================================================");
        System.out.print("Enter your choice: ");

        int choice = checkInputIntLimit(1, 5);
        return choice;
    }

    // Check user input number limit
    public static int checkInputIntLimit(int min, int max) {
        // Loop until user input correct
        while (true) {
            try {
                int result = Integer.parseInt(in.nextLine().trim());
                if (result < min || result > max) {
                    throw new NumberFormatException();
                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Please input number in range [" + min + ", " + max + "]");
                System.out.print("Enter again: ");
            }
        }
    }

    // Check user input string
    public static String checkInputString() {
        String result;
        while (true) {
            result = in.nextLine().trim();
            if (result.isEmpty()) {
                System.err.println("Not empty");
                System.out.print("Enter again: ");
                continue;
            }
            if (result.matches(COUNTRY_VALID)) {
                return result;
            }
            System.err.println("Must be a valid country name (letters and spaces only)");
            System.out.print("Enter again: ");
        }
    }

    // Check user input double limit
    public static double checkInputDouble() {
        // Loop until user input correct
        while (true) {
            try {
                String input = in.nextLine().trim();
                if (!input.matches(COUNTRY_AREA)) {
                    throw new NumberFormatException();
                }
                double result = Double.parseDouble(input);
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Please input a valid number for area");
                System.out.print("Enter again: ");
            }
        }
    }

    // Allow user to input information of countries
    public static void inputCountry(ArrayList<Country> lc) {
        System.out.print("Enter code of country: ");
        String countryCode = checkInputString();
        // Check code country exist or not
        if (!checkCountryExist(lc, countryCode)) {
            System.err.println("Country exists.");
            return;
        }
        System.out.print("Enter name of country: ");
        String countryName = checkInputString();
        System.out.print("Enter total area: ");
        double countryArea = checkInputDouble();
        System.out.print("Enter terrain of country: ");
        String countryTerrain = checkInputString();
        lc.add(new Country(countryTerrain, countryCode, countryName, countryArea));
        System.out.println("Add successful.");
    }

    // Display information of country
    public static void printCountry(ArrayList<Country> lc) {
        if (lc.isEmpty()) {
            System.out.println("No country to display.");
            return;
        }

        System.out.printf("%-10s%-25s%-20s%-25s\n", "ID", "Name", "Total Area", "Terrain");
        Country recentCountry = lc.get(lc.size() - 1); // Lấy quốc gia cuối cùng (gần nhất)
        recentCountry.display();
    }


    // Display information sort name in ascending
    public static void printCountrySorted(ArrayList<Country> lc) {
        Collections.sort(lc);
        System.out.printf("%-10s%-25s%-20s%-25s\n", "ID", "Name", "Total Area", "Terrain");
        for (Country country : lc) {
            country.display();
        }
    }

    // Allow user search information country by name
    public static void searchByName(ArrayList<Country> lc) {
        System.out.print("Enter the name you want to search for: ");
        String countryName = checkInputString();
        System.out.printf("%-10s%-25s%-20s%-25s\n", "ID", "Name", "Total Area", "Terrain");
        boolean found = false;
        for (Country country : lc) {
            if (country.getCountryName().toLowerCase().contains(countryName)) {
                country.display();
                found = true;
            }
        }
        if (!found) {
            System.err.println("Country not found.");
        }
    }

    // Check country exist by code
    public static boolean checkCountryExist(ArrayList<Country> lc, String countryCode) {
        for (Country country : lc) {
            if (country.getCountryCode().equalsIgnoreCase(countryCode)) {
                return false; // Country exists
            }
        }
        return true; // Country does not exist
    }
}
