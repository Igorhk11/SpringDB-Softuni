import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {
    private static final String GET_VILLAIN_NAME_BY_ID =
            "select name from minions_db.villains where id = ?";

    private static final String DELETE_MINIONS_VILLAINS_BY_VILLAIN_ID =
            "delete from minions_db.minions_villains where villain_id = ?";

    private static final String DELETE_VILLAIN_BY_ID =
            "delete from minions_db.villains where id = ?";

    private static final String PRINT_FOR_NO_VILLAIN =
            "No such villain was found";
    private static final String PRINT_VILLAIN_DELETE_FORMAT = "%s was deleted";
    private static final String PRINT_RELEASED_MINIONS_COUNT_FORMAT = "%d minions released";


    public static void main(String[] args) throws SQLException {

        Connection sqlConnection = Utils.getSQLConnection();
        Scanner scanner = new Scanner(System.in);

        int villainID = Integer.parseInt(scanner.nextLine());

        PreparedStatement getVillainStatement = sqlConnection.prepareStatement(GET_VILLAIN_NAME_BY_ID);
        getVillainStatement.setInt(1, villainID);

        ResultSet villainResultSet = getVillainStatement.executeQuery();
        if (!villainResultSet.next()) {
            System.out.println(PRINT_FOR_NO_VILLAIN);
            sqlConnection.close();
            return;
        }

        String villainName = villainResultSet.getString(1);

        PreparedStatement deleteMinionsStatement = sqlConnection.prepareStatement(DELETE_MINIONS_VILLAINS_BY_VILLAIN_ID);
        deleteMinionsStatement.setInt(1,villainID);
        int countOfMinions = deleteMinionsStatement.executeUpdate();

        PreparedStatement deleteVillainStatement = sqlConnection.prepareStatement(DELETE_VILLAIN_BY_ID);
        deleteVillainStatement.setInt(1, villainID);
        deleteVillainStatement.executeUpdate();

        System.out.printf(PRINT_VILLAIN_DELETE_FORMAT, villainName);
        System.out.println();
        System.out.printf(PRINT_RELEASED_MINIONS_COUNT_FORMAT, countOfMinions);


    }
}
