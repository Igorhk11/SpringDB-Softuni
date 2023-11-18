import entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hibernate-jpa");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Student student = new Student("Teo", 10);
        em.persist(student);
//
//        student.setName("TeoMasina");
//        em.persist(student);

//        Student findStudent = em.find(Student.class, 3);
//        em.remove(findStudent);
//
//        System.out.println(findStudent.getName());

        em.getTransaction().commit();
    }
}
