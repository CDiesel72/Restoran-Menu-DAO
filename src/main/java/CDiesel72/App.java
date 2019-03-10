package CDiesel72;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Scanner sc = null;
        try {
            emf = Persistence.createEntityManagerFactory("JPAMenuRest");
            em = emf.createEntityManager();
            sc = new Scanner(System.in);
            DishDAO dDAO = new DishDAO(em, sc);

            while (true) {
                System.out.println("1: Добавить блюдо");
                System.out.println("2: Полное меню");
                System.out.println("3: Меню по цене блюда");
                System.out.println("4: Меню блюда со скидкой");
                System.out.println("5: Выбрать блюда общим весом не более 1 кг");
                System.out.print("-> ");

                String s = sc.nextLine();
                System.out.println("======");
                switch (s) {
                    case "1":
                        dDAO.addDish();
                        break;
                    case "2":
                        dDAO.getMenu();
                        break;
                    case "3":
                        dDAO.getMenuPrice();
                        break;
                    case "4":
                        dDAO.getMenuDisc();
                        break;
                    case "5":
                        dDAO.getMenuWeight();
                        break;
                    default:
                        return;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
    }
}
