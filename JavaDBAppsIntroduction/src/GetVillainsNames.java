import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class GetVillainsNames {
    private static final String GET_VILLAINS_NAMES = "select v.name, count(distinct mv.minion_id) as count_of_minions from villains as v" +
            " join minions_villains as mv on mv.villain_id = v.id" +
            " group by v.id" +
            " having count_of_minions > ?" +
            " order by count_of_minions desc;";

    private static final String PRINT_FORMAT = "%s %d";
    private static final String COLUMN_LABEL_COUNT_OF_MINIONS = "count_of_minions";
    private static final String COLUMN_LABEL_NAME = "name";

    public static void main(String[] args) throws SQLException {
    final Connection connection = Utils.getSQLConnection();


        final PreparedStatement statement = connection.prepareStatement(GET_VILLAINS_NAMES);
        statement.setInt(1, 15);

        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            print(resultSet);
        }
        connection.close();

    }

    private static void print(ResultSet resultSet) throws SQLException {
        final String name = resultSet.getString(COLUMN_LABEL_NAME);
        int count_of_minions = resultSet.getInt(COLUMN_LABEL_COUNT_OF_MINIONS);

        System.out.printf(PRINT_FORMAT, name , count_of_minions);
    }
}