package app;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;
    
    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("FlatUnit");
        em = emf.createEntityManager();
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("1: add flat");
                System.out.println("2: delete flat");
                System.out.println("3: change flat");
                System.out.println("4: view  flats");
                System.out.println("5: view flats by parameters");
                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        addFlat(sc);
                        break;
                    case "2":
                        deleteFlat(sc);
                        break;
                    case "3":
                        changeFlat(sc);
                        break;
                    case "4":
                        viewFlats();
                        break;
                    case "5":
                        viewFlatsByParameters(sc);
                        break;
                    default:
                        return;
                }
            }
        } finally {
            em.close();
            emf.close();
        }


    }

    public static <T> T performTransaction(Callable<T> action) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            T result = action.call();
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Transaction failed, please try again\nReason: " + e.getMessage());
        }
    }

    public static void addFlat(Scanner sc) {
        System.out.print("Enter flat street: ");
        String street = sc.nextLine();
        System.out.print("Enter flats city: ");
        String city = sc.nextLine();
        System.out.print("Enter flat price: ");
        int price = sc.nextInt();
        System.out.print("Enter flat squares: ");
        int squares = sc.nextInt();
        System.out.print("Enter number of rooms: ");
        int roomsCount = sc.nextInt();
        sc.nextLine();

        Flat createdFlat = performTransaction(() -> {
            Flat flat = new Flat(street, city, price, squares, roomsCount);
            em.persist(flat);
            return flat;
        });
        System.out.println("Flat with id: " + createdFlat.getId());
    }

    public static void deleteFlat(Scanner sc) {
        System.out.print("Enter flat ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        performTransaction(() -> {
            Flat flat = em.find(Flat.class, id);
            em.remove(flat);
            return null;
        });

        System.out.println("Flat with id: " + id + " successfully deleted.");

    }

    public static void changeFlat(Scanner sc) {
        System.out.print("Enter flat ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter flats new street or leave it empty: ");
        String newStreet = sc.nextLine();
        System.out.print("Enter flats new city or leave it empty: ");
        String newCity = sc.nextLine();
        System.out.print("Enter flats new price or leave it empty: ");
        int newPrice = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter flats new squares count or leave it empty: ");
        int newSquares = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter flats new rooms count or leave it empty: ");
        int newRoomsCount = sc.nextInt();
        sc.nextLine();

        Flat changedFlat = performTransaction(() -> {
            Flat flat = em.find(Flat.class, id);
            if (!newStreet.isEmpty()) flat.setStreet(newStreet);
            if (!newCity.isEmpty()) flat.setCity(newCity);
            if (newPrice > 0) flat.setPrice(newPrice);
            if (newSquares > 0) flat.setSquares(newSquares);
            if (newRoomsCount > 0) flat.setRoomsCount(newRoomsCount);
            em.persist(flat);
            return flat;
        });

        System.out.println("Flat with id: " + changedFlat.getId() + "successfully changed");
    }

    public static void viewFlatsByParameters(Scanner sc) {
        System.out.println("Choose between this parameters: id, street, city, price, roomsCount");
        String parameter = sc.nextLine();
        System.out.println("Do you want perform WHERE or LIKE search?\nPlease enter one of this: ");
        String searchType = sc.nextLine();
        System.out.println("If you choose WHERE, enter one of this operation which you want to perform: ");
        System.out.println("'<', '>', '=', '<=', '>='");
        System.out.println("Please remember - comparative operators acceptable only for numbers type, only '=' you can use with string type");
        System.out.println("If you choose LIKE leave this line empty, you can use '%' in start or end of the value to specify which part you know : ");
        String operation = sc.nextLine();
        System.out.println("Enter value: ");
        String value = sc.nextLine();
        String searchParams = parameter + searchType + operation;
        String queryParams = QueryMap.QUERY_MAP.get(searchParams.toLowerCase());
        List<Flat> flatsList = performTransaction(() -> {
            TypedQuery<Flat> flatsQuery = em.createQuery(queryParams, Flat.class);
            flatsQuery.setParameter("value", value);
            return flatsQuery.getResultList();
        });
        renderTable(flatsList);
    }

    public static void viewFlats() {
        List<Flat> flatsList = performTransaction(() -> {
            TypedQuery<Flat> flatsQuery = em.createQuery("select f from Flat f", Flat.class);
            return flatsQuery.getResultList();
        });
        renderTable(flatsList);
    }

    private static void renderTable(List<Flat> flatsList) {
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Id", "Street", "City", "Squares", "Price", "Rooms Count");
        asciiTable.addRule();
        for (Flat flat : flatsList) {
            asciiTable.addRow(flat.getId(), flat.getStreet(), flat.getCity(), flat.getSquares(), flat.getPrice(), flat.getRoomsCount());
            asciiTable.addRule();
        }
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        String table = asciiTable.render();
        System.out.println(table);
    }
}
