import com.system.util.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Attempting to connect to the database...");

        // Calling our utility class
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println("We are ready to start building the DAOs!");
        } else {
            System.out.println("Connection failed. Let's fix the errors above.");
        }
    }
}