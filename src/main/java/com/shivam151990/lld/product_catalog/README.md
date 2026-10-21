Category, Subcategory and Product Catalog

1. addCategory(id, name) adds a root category.
2. addSubcategory(parentId, id, name) adds a subcategory under a category or subcategory.
3. addProduct(parentId, id, name) adds a product under a subcategory.
4. A node holds either subcategories or products, never both. Violating this throws an error.
5. getProducts(nodeId) returns every product beneath that node at any depth.
6. getPath(productId) returns a path like Food > Dairy > Milk.