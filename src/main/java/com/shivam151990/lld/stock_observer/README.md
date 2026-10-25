1. addStock(productId, qty) and removeStock(productId, qty). Removing more than is available fails.
2. Each product has a reorder threshold: setThreshold(productId, n).
3. When stock falls to or below the threshold after a removal, notify all registered listeners once, not on every later removal.
4. When stock rises back above the threshold, the alert re-arms.
5. getStock(productId).