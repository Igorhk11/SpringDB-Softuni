import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeTownNamesCasing {
    private final static String GET_TOWNS_FROM_COUNTRY = "SELECT name FROM towns WHERE COUNTRY = ?";
    private final static String UPDATE_TOWN_NAMES = "UPDATE towns SET name = UPPER(name) WHERE COUNTRY = ?";

    public static void main(String[] args) throws SQLException {
        Connection sqlConnection = Utils.getSQLConnection();
        Scanner scanner = new Scanner(System.in);

        String countryName = scanner.nextLine();

        PreparedStatement selectStatement = sqlConnection.prepareStatement(GET_TOWNS_FROM_COUNTRY);
        selectStatement.setString(1, countryName);

        ResultSet result = selectStatement.executeQuery();

        List<String> towns = new ArrayList<>();
        while (result.next()) {
            towns.add(result.getString("name"));
        }

        int townsAffected = 0;
        if (!towns.isEmpty()) {
            PreparedStatement updateStatement = sqlConnection.prepareStatement(UPDATE_TOWN_NAMES);
            updateStatement.setString(1, countryName);
            townsAffected = updateStatement.executeUpdate();
        }

        if (townsAffected == 0) {
            System.out.println("No town names were affected");
        } else {
            System.out.printf("%d town names were affected.%n", townsAffected);

            // Retrieve the updated town names
            towns.clear();
            result = selectStatement.executeQuery();
            while (result.next()) {
                towns.add(result.getString("name"));
            }

            System.out.println("[" + String.join(",", towns) + "]");
        }

        sqlConnection.close();
    }
}