Basket Restriction Engine

1. Rules: max quantity per product, and max total items per category.
2. addItem(product, qty) is rejected with the broken rule’s message.
3. validate(basket) returns all violations.
4. A new rule type can be added without modifying existing code.
