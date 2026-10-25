import com.shivam151990.lld.food_ordering.model.Order;
import com.shivam151990.lld.food_ordering.model.Restaurant;
import com.shivam151990.lld.food_ordering.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestaurantOrderTest {

    private OrderService svc;
    private String user1, user2;

    @BeforeEach
    public void beforeEach() {
        svc = new OrderService();
        Restaurant r1 = new Restaurant("r1", Map.of("dish1", 5, "dish2", 5, "dish3", 5), 10);
        Restaurant r2 = new Restaurant("r2", Map.of("dish1", 5, "dish4", 5, "dish5", 5), 10);
        svc.addRestaurant(r1);
        svc.addRestaurant(r2);
        user1 = "user1";
        user2 = "user2";
    }

    @Test
    public void successOrderItemsTest() {
        Map<String, Integer> items = Map.of("dish1", 2, "dish2", 2);
        Order o = svc.order(user1, "r1", items);
        assertEquals(items, o.getItems());
    }

    @Test
    public void failedOrderTest() {
        Map<String, Integer> items = Map.of("dish1", 6, "dish2", 2);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> svc.order(user1, "r1", items));
        assertEquals("Item does not exits", ex.getMessage());
    }
}
