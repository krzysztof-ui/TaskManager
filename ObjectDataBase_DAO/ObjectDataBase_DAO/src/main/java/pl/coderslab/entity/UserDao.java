package pl.coderslab.entity;
import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static Connection conn;

    static {
        try {
            conn = DbUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    private static final String CREATE_USER_QUERY =

            "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

    private  static final String readById = "SELECT * FROM users where id = ? ";

    private static final String updateDataOfUser = "UPDATE users SET username = ?, email = ?, password = ? where id = ?";

    private static final String deleteById = "DELETE FROM users where id = ?";

    private static final String findByUserName = "SELECT * FROM users where LEFT(username, 1) = 'F'"; //username zaczynający się na podana literę


    public String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());

    }

    public  User create(User user) throws SQLException {

            PreparedStatement statement =

                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, user.getUserName());

            statement.setString(2, user.getEmail());

            statement.setString(3, hashPassword(user.getPassword()));

            statement.executeUpdate();

            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {

                user.setId(resultSet.getInt(1));

            }

            return user;

    }



    public User read(int userId) throws SQLException {

            User user = new User();
            PreparedStatement statement =

                    conn.prepareStatement(readById);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

        //statement.execute();
        while (rs.next()) {
            int id = rs.getInt("id");
            user.setId(id);
            String email = rs.getString("email");
            user.setEmail(email);
            String username = rs.getString("username");
            user.setUserName(username);
            String password = rs.getString("password");
            user.setPassword(password);
            // int id = rs.getInt("id");
        }
       if (user.getUserName() != null){
           return user;
       } else {
            return null;

        }
    }

    public void update(User user) throws SQLException {

        PreparedStatement statement =

                conn.prepareStatement(updateDataOfUser, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, user.getUserName());
        statement.setString(2, user.getEmail());
        statement.setString(3, hashPassword(user.getPassword()));
        statement.setInt(4, user.getId());
        statement.executeUpdate();
    }


    public void delete(int userId) throws SQLException {

        User user = new User();
        PreparedStatement statement =

                conn.prepareStatement(deleteById);

        statement.setInt(1, userId);
        statement.executeUpdate();

    }

    private User[] addToArray(User u, User[] users) {

        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.

        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.

        return tmpUsers; // Zwracamy nową tablicę.

    }

    public User[] findAll() throws SQLException {

        User user = new User();
        User[] users = new User[0];
        PreparedStatement statement =

                conn.prepareStatement(findByUserName);
        //statement.setInt(1, userId);
        ResultSet rs = statement.executeQuery();

        //statement.execute();
        while (rs.next()) {
            int id = rs.getInt("id");
            user.setId(id);
            String email = rs.getString("email");
            user.setEmail(email);
            String username = rs.getString("username");
            user.setUserName(username);
            String password = rs.getString("password");
            user.setPassword(password);
            users = addToArray(user, users);

        }

        return users;
    }


}
