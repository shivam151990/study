package com.shivam151990.lld.product_catalog.service;

import com.shivam151990.lld.product_catalog.exception.DuplicateCategoryException;
import com.shivam151990.lld.product_catalog.exception.InvalidCatalogOperationException;
import com.shivam151990.lld.product_catalog.exception.NodeNotFoundException;
import com.shivam151990.lld.product_catalog.exception.ParentCategoryAbsentException;
import com.shivam151990.lld.product_catalog.model.CatalogNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CatalogService {

    private static final String PATH_SEPARATOR = " > ";

    private final Map<String, CatalogNode> nodeMapping = new HashMap<>();

    public void addCategory(String id, String name) {
        ensureUniqueId(id);
        nodeMapping.put(id, new CatalogNode(id, name, false, null));
    }

    public void addSubcategory(String parent, String id, String name) {
        CatalogNode parentNode = getCategory(parent);
        if (parentNode.hasProductChildren()) {
            throw new InvalidCatalogOperationException("Category " + parent + " already holds products");
        }
        ensureUniqueId(id);
        CatalogNode subCat = new CatalogNode(id, name, false, parentNode);
        parentNode.addChild(subCat);
        nodeMapping.put(id, subCat);
    }

    public void addProduct(String parent, String id, String name) {
        CatalogNode parentNode = getCategory(parent);
        if (parentNode.hasCategoryChildren()) {
            throw new InvalidCatalogOperationException("Category " + parent + " already holds subcategories");
        }
        ensureUniqueId(id);
        CatalogNode product = new CatalogNode(id, name, true, parentNode);
        parentNode.addChild(product);
        nodeMapping.put(id, product);
    }

    /** Every product beneath the node at any depth; empty when called on a product. */
    public List<CatalogNode> getProducts(String nodeId) {
        CatalogNode node = getNode(nodeId);
        List<CatalogNode> products = new ArrayList<>();
        collectProducts(node, products);
        return products;
    }

    /** Path from the root category down to the product, e.g. "Food > Dairy > Milk". */
    public String getPath(String productId) {
        CatalogNode node = getNode(productId);
        if (!node.isProduct()) {
            throw new InvalidCatalogOperationException(productId + " is not a product");
        }
        LinkedList<String> names = new LinkedList<>();
        for (CatalogNode current = node; current != null; current = current.getParent()) {
            names.addFirst(current.getName());
        }
        return String.join(PATH_SEPARATOR, names);
    }

    private void collectProducts(CatalogNode node, List<CatalogNode> products) {
        for (CatalogNode child : node.getChildren()) {
            if (child.isProduct()) {
                products.add(child);
            } else {
                collectProducts(child, products);
            }
        }
    }

    private void ensureUniqueId(String id) {
        if (nodeMapping.containsKey(id)) {
            throw new DuplicateCategoryException("Node with id " + id + " already exists");
        }
    }

    private CatalogNode getCategory(String id) {
        CatalogNode node = nodeMapping.get(id);
        if (node == null) {
            throw new ParentCategoryAbsentException("Parent category " + id + " absent");
        }
        if (node.isProduct()) {
            throw new InvalidCatalogOperationException("Cannot add children under product " + id);
        }
        return node;
    }

    private CatalogNode getNode(String id) {
        CatalogNode node = nodeMapping.get(id);
        if (node == null) {
            throw new NodeNotFoundException("Node " + id + " not found");
        }
        return node;
    }
}
