import java.sql.*;
import java.util.Scanner;

public class IncreaseAgeWithProcedure {

    private final static String PRINT_MINION_NAME_AGE = "select minions.name,age from minions ";
    private final static String usp_get_older = " create procedure usp_get_older(IN minion_id int)" +
            "BEGIN" +
            "UPDATE minions " +
            " SET age = age + 1 " +
            "  WHERE id =minion_id" +
            " end;";


    public static void main(String[] args) throws SQLException {
        Connection sqlConnection = Utils.getSQLConnection();
        Scanner scanner = new Scanner(System.in);
        int minionId = Integer.parseInt(scanner.nextLine());

        String query = "CALL usp_get_older(?)";

        CallableStatement callableStatement = sqlConnection.prepareCall(query);
        callableStatement.setInt(1, minionId);

        ResultSet callProcedure = callableStatement.executeQuery();

        PreparedStatement statement = sqlConnection.prepareStatement(PRINT_MINION_NAME_AGE);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String name = resultSet.getString(1);
            int age = resultSet.getInt(2);

            System.out.printf("%s %d%n", name, age);
        }
        sqlConnection.close();
    }
}
