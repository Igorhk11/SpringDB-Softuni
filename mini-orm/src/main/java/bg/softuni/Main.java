package bg.softuni;

import entities.Courses;
import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        Connector.createConnection("root", "root", "soft_uni");

        Connection connection = Connector.getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);
        boolean persistResult = userEntityManager.persist(new User("u2", "p2", 12, LocalDate.now()));
        User first = userEntityManager.findFirst(User.class);
        System.out.println(first);

        System.out.println(persistResult);

        EntityManager<Courses> courseEntityManager = new EntityManager<>(connection);
        courseEntityManager.persist(new Courses("Math", 12));
        Courses first1 = courseEntityManager.findFirst(Courses.class);
        System.out.println(first1);

//        EntityManager<Department> departmentEntityManager = new EntityManager<>(connection);
//        departmentEntityManager.persist(new Department());
    }
}