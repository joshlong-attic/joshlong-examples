package org.springsource.examples.crm.services;

import org.springsource.examples.crm.model.Purchase;

/**
 * provides facilities to manage a {@link org.springsource.examples.crm.model.Customer}'s shopping cart
 *
 * @author Josh Long
 */
public interface CustomerOrderService {

    Purchase createPurchase(long customerId);

    void addProductToPurchase(long purchaseId, long productId);

    void checkout(long purchaseId);

    Purchase getPurchaseById(long purchaseId);

}
