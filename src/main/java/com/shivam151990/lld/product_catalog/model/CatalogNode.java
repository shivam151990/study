package com.shivam151990.lld.product_catalog.model;

import lombok.Getter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class CatalogNode {

    private final String id;
    private final String name;
    private final boolean product;
    private final CatalogNode parent;
    // LinkedHashMap keeps insertion order so traversal results are deterministic
    private final Map<String, CatalogNode> children = new LinkedHashMap<>();

    public CatalogNode(String id, String name, boolean product, CatalogNode parent) {
        this.id = id;
        this.name = name;
        this.product = product;
        this.parent = parent;
    }

    public Collection<CatalogNode> getChildren() {
        return children.values();
    }

    public void addChild(CatalogNode child) {
        children.put(child.getId(), child);
    }

    public boolean hasProductChildren() {
        return children.values().stream().anyMatch(CatalogNode::isProduct);
    }

    public boolean hasCategoryChildren() {
        return children.values().stream().anyMatch(child -> !child.isProduct());
    }
}
