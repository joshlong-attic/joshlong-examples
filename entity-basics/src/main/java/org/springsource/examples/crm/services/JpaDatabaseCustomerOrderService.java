package org.springsource.examples.crm.services;

import org.springsource.examples.crm.model.Purchase;

public class JpaDatabaseCustomerOrderService implements CustomerOrderService {
    @Override
    public Purchase createPurchase(long customerId) {
        return null;
    }

    @Override
    public void addProductToPurchase(long purchaseId, long productId) {
    }

    @Override
    public void checkout(long purchaseId) {
    }
}
