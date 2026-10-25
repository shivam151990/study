package com.shivam151990.lld.food_ordering.service;

import com.shivam151990.lld.food_ordering.model.Order;
import com.shivam151990.lld.food_ordering.model.Restaurant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderService {

    private Map<String, Map<String, Order>> orders;
    private Map<String, Restaurant> restaurantMp;

    public OrderService() {
        orders = new HashMap<>();
        restaurantMp = new HashMap<>();
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurantMp.put(restaurant.getId(), restaurant);
        orders.put(restaurant.getId(), new HashMap<>());
    }

    public Order order(String userId, String restaurant, Map<String, Integer> items) {
        if (!restaurantMp.containsKey(restaurant)) {
            throw new RuntimeException("Orders does not exist");
        }
        if(orders.get(restaurant).size() > restaurantMp.get(restaurant).getMaxOrders()) {
            throw new RuntimeException("Max orders reached for restaurant");
        }
        Map<String, Integer> restaurantItems = restaurantMp.get(restaurant).getItems();
        for (Map.Entry<String, Integer> entry: items.entrySet()) {
            String item = entry.getKey();
            int qty = entry.getValue();
            if (!restaurantItems.containsKey(item) || restaurantItems.get(item) < qty) {
                throw new RuntimeException("Item does not exits");
            }
            restaurantItems.put(item, restaurantItems.get(item) - qty);
        }
        Order o = new Order(UUID.randomUUID().toString().substring(1, 6), items);
        orders.get(restaurant).put(userId, o);
        return o;
    }

    public void free(String restaurant, String user) {
        if (!orders.containsKey(restaurant)) {
            throw new RuntimeException("Orders does not exist");
        }
        if (orders.containsKey(restaurant) && !orders.get(restaurant).containsKey(user)) {
            throw new RuntimeException("No Orders exist for user");
        }
        orders.get(restaurant).remove(user);
    }

    public List<String> search(String dish) {
        return restaurantMp.values().stream()
                .flatMap(r -> r.getItems().keySet().stream())
                .filter(s -> s.contains(dish))
                .toList();
    }


}
