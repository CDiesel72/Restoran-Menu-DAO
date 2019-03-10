package CDiesel72;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Diesel on 09.03.2019.
 */
public class DishDAO extends EntityDAO<Dish> {

    private final Scanner sc;

    private final String QUERY = "SELECT d FROM Dish d";

    public DishDAO(EntityManager em, Scanner sc) {
        super(em);
        this.sc = sc;
    }

    private String inString() {
        String st = null;
        while ((st == null) || (st.isEmpty())) {
            st = sc.nextLine();
        }
        return st;
    }

    private int inInt() {
        int i = 0;
        boolean flag = true;
        while (flag) {
            try {
                String st = inString();
                i = Integer.valueOf(st);
                flag = false;
            } catch (Exception ex) {
            }
        }

        return i;
    }

    private double inDouble() {
        double d = 0;
        boolean flag = true;
        while (flag) {
            try {
                String st = inString();
                d = Double.valueOf(st);
                flag = false;
            } catch (Exception ex) {
            }
        }

        return d;
    }

    private boolean inBool() {
        boolean bl = false;
        boolean flag = true;
        while (flag) {
            String st = inString();
            if ("y".equalsIgnoreCase(st)) {
                bl = true;
                flag = false;
            } else if ("n".equalsIgnoreCase(st)) {
                bl = false;
                flag = false;
            }
        }

        return bl;
    }

    private Dish newDish() {
        System.out.println("Введите данные о блюде:");
        System.out.print("название: ");
        String name = inString();
        System.out.print("цена: ");
        double price = inDouble();
        System.out.print("вес: ");
        double weight = inDouble();
        System.out.print("наличие скидки (Y/N): ");
        boolean discount = inBool();
        return new Dish(name, price, weight, discount);
    }

    public void addDish() {
        add(newDish());
    }

    public void getMenu() {
        Query query = super.getEm().createQuery(QUERY, Dish.class);
        getSelect(query, "Меню:");
    }

    public void getMenuPrice() {
        System.out.println("Введите минимальную и максимальную цены блюда:");
        System.out.print("минимальная: ");
        double min = inDouble();
        System.out.print("максимальная: ");
        double max = inDouble();

        Query query = super.getEm().createQuery(QUERY + " WHERE d.price BETWEEN :min AND :max", Dish.class);
        query.setParameter("min", min);
        query.setParameter("max", max);

        getSelect(query, "Меню - блюда по цене от " + min + " до " + max + ":");
    }

    public void getMenuDisc() {
        Query query = super.getEm().createQuery(QUERY + " WHERE d.discount = :bool", Dish.class);
        query.setParameter("bool", true);

        getSelect(query, "Меню - блюда со скидкой:");
    }

    public void getMenuWeight() {
        List<Dish> dishes = new ArrayList<>();
        Double sumW = 0.0;
        while (sumW <= 1) {
            System.out.print("Введите ID блюда:");
            int idD = inInt();

            Dish dish = super.getEm().find(Dish.class, idD);
            if (dish != null) {
                System.out.println(dish + "   Общий вес " + (String.format("%.3f", sumW + dish.getWeight())) + " кг");
                sumW += dish.getWeight();
                if (sumW <= 1) {
                    dishes.add(dish);
                }
            }
        }

        toScreen("Меню - выбранные блюда:", dishes);
    }

}
