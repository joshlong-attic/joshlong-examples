package org.springsource.examples.crm.services.jpa;

import org.springsource.examples.crm.model.Purchase;
import org.springsource.examples.crm.services.CustomerOrderService;

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
