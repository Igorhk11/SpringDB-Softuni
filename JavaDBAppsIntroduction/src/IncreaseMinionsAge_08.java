import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseMinionsAge_08 {

    private final static String UPDATE_AGE = "UPDATE minions_db.minions SET age = minions.age + 1 WHERE id = ? ";
    private final static String UPDATE_NAME_TO_LOWER = "UPDATE minions SET name = LOWER(name) WHERE id = ?";
    private final static String SELECT_NAME_AGE = "SELECT name, age from minions";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection sqlConnection = Utils.getSQLConnection();

        System.out.println("Enter minion IDs (separated by space): ");
        String input = scanner.nextLine();
        String[] minionIds = input.split(" ");

        updateNameAge(sqlConnection, minionIds);

        PreparedStatement selectStatement = sqlConnection.prepareStatement(SELECT_NAME_AGE);
        ResultSet result = selectStatement.executeQuery();

        while (result.next()) {
            String name = result.getString("name");
            int age = result.getInt("age");

            System.out.printf("%s %d%n", name, age);
        }

        sqlConnection.close();

    }

    private static void updateNameAge(Connection sqlConnection, String[] minionIds) throws SQLException {
        for (String minionId : minionIds) {
            PreparedStatement updateAgeStatement = sqlConnection.prepareStatement(UPDATE_AGE);
            updateAgeStatement.setInt(1, Integer.parseInt(minionId));
            updateAgeStatement.executeUpdate();

            PreparedStatement updateNameStatement = sqlConnection.prepareStatement(UPDATE_NAME_TO_LOWER);
            updateNameStatement.setInt(1, Integer.parseInt(minionId));
            updateNameStatement.executeUpdate();

        }
    }
}
