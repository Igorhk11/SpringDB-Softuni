import entities.Author;
import entities.Book;

import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Persistence.createEntityManagerFactory("relations").createEntityManager();

        Book book = new Book();
    }

}
