package org.springsource.examples.crm.services.jpa;

import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.crm.model.Purchase;
import org.springsource.examples.crm.services.CustomerOrderService;

public class JpaDatabaseCustomerOrderService implements CustomerOrderService {



    @Transactional
    public Purchase createPurchase(long customerId) {
        return null;
    }


    @Transactional
    public void addProductToPurchase(long purchaseId, long productId) {
    }

     @Transactional
    public void checkout(long purchaseId) {
    }
}
