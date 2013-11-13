package ee.ut.math.tvt.kvaliteetsedideed.domain.controller.impl;

import ee.ut.math.tvt.kvaliteetsedideed.domain.controller.SalesDomainController;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.Purchase;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.SoldItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.StockItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.kvaliteetsedideed.service.POSDataService;
import ee.ut.math.tvt.kvaliteetsedideed.util.HibernateUtil;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the sales domain controller.
 */
@SuppressWarnings("unchecked")
public class SalesDomainControllerImpl implements SalesDomainController {

  private POSDataService posDataService = new POSDataService();

  @Override
  public Purchase createPurchaseItem(List<SoldItem> goods) {
    Purchase purchaseItem = new Purchase();
    purchaseItem.setSoldItems(goods);
    purchaseItem.setPurchaseDate(new Date());
    purchaseItem.calculateTotal();
    return purchaseItem;
  }

  public void cancelCurrentPurchase() throws VerificationFailedException {
  }

  public void startNewPurchase() throws VerificationFailedException {
  }

  public List<StockItem> loadWarehouseState() {
    return posDataService.getStockItems();
  }

  @Override
  public List<Purchase> loadPurchaseHistory() {
    return posDataService.getPurchases();
  }

  @Override
  public void addStockItem(StockItem item) {
    posDataService.save(item);
  }

  @Override
  public void endSession() {
    HibernateUtil.closeSession();
  }

  @Override
  public void submitCurrentPurchase(Purchase purchase) throws VerificationFailedException {
    for (SoldItem soldItem : purchase.getSoldItems()) {
      soldItem.getStockItem().decreaseStock(soldItem.getQuantity());
    }
    posDataService.save(purchase);
    for (SoldItem soldItem : purchase.getSoldItems()) {
      soldItem.setPurchase(purchase);
      posDataService.save(soldItem);
    }
  }

}
