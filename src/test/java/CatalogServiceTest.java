import com.shivam151990.lld.product_catalog.service.CatalogService;
import com.shivam151990.lld.product_catalog.exception.DuplicateCategoryException;
import com.shivam151990.lld.product_catalog.exception.InvalidCatalogOperationException;
import com.shivam151990.lld.product_catalog.exception.NodeNotFoundException;
import com.shivam151990.lld.product_catalog.exception.ParentCategoryAbsentException;
import com.shivam151990.lld.product_catalog.model.CatalogNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatalogServiceTest {

    private CatalogService service;

    @BeforeEach
    public void before() {
        service = new CatalogService();
        service.addCategory("food", "Food");
        service.addSubcategory("food", "dairy", "Dairy");
        service.addSubcategory("dairy", "cheese", "Cheese");
        service.addSubcategory("food", "fruit", "Fruit");
        service.addSubcategory("dairy", "liquid", "Liquid");
        service.addProduct("liquid", "milk", "Milk");
        service.addProduct("cheese", "cheddar", "Cheddar");
        service.addProduct("fruit", "apple", "Apple");
    }

    @Test
    public void getProductsReturnsAllDepths() {
        assertEquals(List.of("cheddar", "milk", "apple"), ids(service.getProducts("food")));
        assertEquals(List.of("cheddar", "milk"), ids(service.getProducts("dairy")));
        assertEquals(List.of("apple"), ids(service.getProducts("fruit")));
    }

    @Test
    public void getProductsOnEmptyCategoryOrProduct() {
        service.addCategory("toys", "Toys");
        assertTrue(service.getProducts("toys").isEmpty());
        assertTrue(service.getProducts("milk").isEmpty());
    }

    @Test
    public void getPathBuildsFullPath() {
        assertEquals("Food > Dairy > Liquid > Milk", service.getPath("milk"));
        assertEquals("Food > Fruit > Apple", service.getPath("apple"));
    }

    @Test
    public void getPathRejectsNonProduct() {
        assertThrows(InvalidCatalogOperationException.class, () -> service.getPath("dairy"));
    }

    @Test
    public void unknownNode() {
        assertThrows(NodeNotFoundException.class, () -> service.getProducts("nope"));
        assertThrows(NodeNotFoundException.class, () -> service.getPath("nope"));
    }

    @Test
    public void missingParent() {
        assertThrows(ParentCategoryAbsentException.class, () -> service.addSubcategory("nope", "x", "X"));
        assertThrows(ParentCategoryAbsentException.class, () -> service.addProduct("nope", "x", "X"));
    }

    @Test
    public void duplicateIdsRejectedAcrossLevels() {
        assertThrows(DuplicateCategoryException.class, () -> service.addCategory("food", "Food"));
        assertThrows(DuplicateCategoryException.class, () -> service.addCategory("milk", "Milk"));
        assertThrows(DuplicateCategoryException.class, () -> service.addSubcategory("food", "cheese", "Cheese"));
        assertThrows(DuplicateCategoryException.class, () -> service.addProduct("liquid", "apple", "Apple"));
    }

    @Test
    public void cannotMixProductsAndSubcategories() {
        assertThrows(InvalidCatalogOperationException.class, () -> service.addSubcategory("liquid", "x", "X"));
        assertThrows(InvalidCatalogOperationException.class, () -> service.addProduct("dairy", "x", "X"));
    }

    @Test
    public void cannotAddUnderProduct() {
        assertThrows(InvalidCatalogOperationException.class, () -> service.addProduct("milk", "x", "X"));
        assertThrows(InvalidCatalogOperationException.class, () -> service.addSubcategory("milk", "x", "X"));
    }

    private static List<String> ids(List<CatalogNode> nodes) {
        return nodes.stream().map(CatalogNode::getId).toList();
    }
}
