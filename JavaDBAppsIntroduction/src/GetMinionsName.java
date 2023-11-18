import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionsName {
    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_AGE = "age";
    private static final String GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID =
                    "select m.name, m.age " +
                    "FROM minions as m " +
                    "join minions_villains as mv on m.id = mv.minion_id " +
                    "where villain_id = ?";
    private static final String GET_VILLAIN_NAME_BY_ID = "select name from villains " +
            "where id = ?";
    private static final String PRINT_VILLAIN_FORMAT = "Villain: %s\n";
    private static final String PRINT_MINIONS_FORMAT = "%d.: %s %d\n";
    private static final String PRINT_NO_VILLAIN_FOUND_FORMAT = "No villain with id %d exists in the database\n";

    public static void main(String[] args) throws SQLException {

        final Connection sqlConnection = Utils.getSQLConnection();
        final int villainID = new Scanner(System.in).nextInt();


        final PreparedStatement statementForVillain = sqlConnection.prepareStatement(GET_VILLAIN_NAME_BY_ID);
        statementForVillain.setInt(1, villainID);

        final ResultSet villainResultSet = statementForVillain.executeQuery();
        if (!villainResultSet.next()) {
            System.out.printf(PRINT_NO_VILLAIN_FOUND_FORMAT, villainID);
            sqlConnection.close();
            return;
        }


        final PreparedStatement statementForMinions = sqlConnection.prepareStatement(GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID);
        statementForMinions.setInt(1, villainID);

        final ResultSet minionsResultSet = statementForMinions.executeQuery();

        print(villainResultSet,minionsResultSet);

        sqlConnection.close();
    }

    private static void print(ResultSet villains, ResultSet minions) throws SQLException {
        final String villainName = villains.getString(COLUMN_LABEL_NAME);
        System.out.printf(PRINT_VILLAIN_FORMAT, villainName);


        for (int index = 1; minions.next(); index++) {
            final String minionName = minions.getString(COLUMN_LABEL_NAME);
            final int minionAge = minions.getInt(COLUMN_LABEL_AGE);

            System.out.printf(PRINT_MINIONS_FORMAT, index, minionName , minionAge);

        }


    }
}
