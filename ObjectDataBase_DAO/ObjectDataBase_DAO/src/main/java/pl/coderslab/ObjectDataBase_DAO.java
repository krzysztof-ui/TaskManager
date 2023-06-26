package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.sql.SQLException;

public class ObjectDataBase_DAO {
    public static void main(String[] args) throws SQLException {

        User userAddToDB = new User();
        userAddToDB.setUserName("Krzysztof");
        userAddToDB.setEmail("zasadzinski.krzysztof@onet.pl");
        userAddToDB.setPassword("Bla bla sekretne haslo");

        //UserDao daoInsertUserName = new UserDao();
        //daoInsertUserName.create(userAddToDB);

        UserDao readById = new UserDao();
        UserDao modifierUser = new UserDao();

        User readByIdUser = new User();
        int id=2;
        if (readById.read(id)!=null) {
            readByIdUser = readById.read(id);
            System.out.println("readByIdUser.getUserName() = " + readByIdUser.getUserName());
            System.out.println("readByIdUser.getUserName() = " + readByIdUser.getEmail());
            System.out.println("readByIdUser.getUserName() = " + readByIdUser.getPassword());
        } else System.out.println("Użytkownik o podanym id nie istnieje");


        //readByIdUser.setPassword("Nowe hasło");
        //readByIdUser.setUserName("Filemon");
        //readByIdUser.setEmail("filemon@onet.pl");
       // modifierUser.update(readByIdUser);
        UserDao findByUserName = new UserDao();

        User[] users = new User[0];
        users= findByUserName.findAll();
        System.out.println("findByUserName.findAll() = " + users[0].getEmail());

        UserDao deleteUser = new UserDao();
        deleteUser.delete(id);

    }

}
