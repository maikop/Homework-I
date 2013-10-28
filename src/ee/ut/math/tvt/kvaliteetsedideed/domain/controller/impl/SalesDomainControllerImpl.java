package ee.ut.math.tvt.kvaliteetsedideed.domain.controller.impl;

import ee.ut.math.tvt.kvaliteetsedideed.domain.controller.SalesDomainController;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.PurchaseItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.SoldItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.StockItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.exception.VerificationFailedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

  private List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();

  public void submitCurrentPurchase(List<SoldItem> goods) throws VerificationFailedException {

    PurchaseItem purchaseItem = new PurchaseItem();
    purchaseItem.setSoldItems(goods);
    purchaseItem.confirmPurchase();
    purchaseItems.add(purchaseItem);

    // Let's assume we have checked and found out that the buyer is underaged
    // and
    // cannot buy chupa-chups
    // throw new VerificationFailedException("Underaged!");
    // XXX - Save purchase

  }

  public void cancelCurrentPurchase() throws VerificationFailedException {
    // XXX - Cancel current purchase
  }

  public void startNewPurchase() throws VerificationFailedException {
    // XXX - Start new purchase
  }

  public List<StockItem> loadWarehouseState() {
    // XXX mock implementation
    List<StockItem> dataset = new ArrayList<StockItem>();

    StockItem chips = new StockItem(1l, "Lays chips", "Potato chips", 11.0, 5);
    StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets", 8.0, 8);
    StockItem frankfurters = new StockItem(3l, "Frankfurters", "Beer sauseges", 15.0, 12);
    StockItem beer = new StockItem(4l, "Free Beer", "Student's delight", 0.0, 100);

    dataset.add(chips);
    dataset.add(chupaChups);
    dataset.add(frankfurters);
    dataset.add(beer);

    return dataset;
  }

  @Override
  public List<PurchaseItem> loadPurchaseHistory() {
    return purchaseItems;
  }

}
