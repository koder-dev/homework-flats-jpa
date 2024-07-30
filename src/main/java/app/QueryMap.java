package app;

import java.util.HashMap;
import java.util.Map;

public class QueryMap {
    public final static Map<String, String> QUERY_MAP = new HashMap<>();

    static {
        QUERY_MAP.put("idwhere<", "SELECT f FROM Flat f WHERE f.id < :value");
        QUERY_MAP.put("idwhere>", "SELECT f FROM Flat f WHERE f.id > :value");
        QUERY_MAP.put("idwhere=", "SELECT f FROM Flat f WHERE f.id = :value");
        QUERY_MAP.put("idwhere<=", "SELECT f FROM Flat f WHERE f.id <= :value");
        QUERY_MAP.put("idwhere>=", "SELECT f FROM Flat f WHERE f.id >= :value");

        QUERY_MAP.put("streetlike", "SELECT f FROM Flat f WHERE f.street LIKE :value");
        QUERY_MAP.put("citylike", "SELECT f FROM Flat f WHERE f.city LIKE :value");
        QUERY_MAP.put("streetwhere=", "SELECT f FROM Flat f WHERE f.street = :value");
        QUERY_MAP.put("citywhere=", "SELECT f FROM Flat f WHERE f.city = :value");

        QUERY_MAP.put("pricewhere<", "SELECT f FROM Flat f WHERE f.price < :value");
        QUERY_MAP.put("pricewhere>", "SELECT f FROM Flat f WHERE f.price > :value");
        QUERY_MAP.put("pricewhere=", "SELECT f FROM Flat f WHERE f.price = :value");
        QUERY_MAP.put("pricewhere<=", "SELECT f FROM Flat f WHERE f.price <= :value");
        QUERY_MAP.put("pricewhere>=", "SELECT f FROM Flat f WHERE f.price >= :value");

        QUERY_MAP.put("roomscountwhere<", "SELECT f FROM Flat f WHERE f.roomsCount < :value");
        QUERY_MAP.put("roomscountwhere>", "SELECT f FROM Flat f WHERE f.roomsCount > :value");
        QUERY_MAP.put("roomscountwhere=", "SELECT f FROM Flat f WHERE f.roomsCount = :value");
        QUERY_MAP.put("roomscountwhere<=", "SELECT f FROM Flat f WHERE f.roomsCount <= :value");
        QUERY_MAP.put("roomscountwhere>=", "SELECT f FROM Flat f WHERE f.roomsCount >= :value");
    }
}
