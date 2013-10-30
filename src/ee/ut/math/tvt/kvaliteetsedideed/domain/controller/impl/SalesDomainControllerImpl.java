package ee.ut.math.tvt.kvaliteetsedideed.domain.controller.impl;

import ee.ut.math.tvt.kvaliteetsedideed.domain.controller.SalesDomainController;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.PurchaseItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.SoldItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.StockItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.exception.VerificationFailedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

  private List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
  private List<StockItem> stockItems = new ArrayList<StockItem>();

  public SalesDomainControllerImpl() {

    StockItem chips = new StockItem(1l, "Lays chips", "Potato chips", 11.0, 5);
    StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets", 8.0, 8);
    StockItem frankfurters = new StockItem(3l, "Frankfurters", "Beer sauseges", 15.0, 12);
    StockItem beer = new StockItem(4l, "Free Beer", "Student's delight", 0.0, 100);

    stockItems.add(chips);
    stockItems.add(chupaChups);
    stockItems.add(frankfurters);
    stockItems.add(beer);

  }

  @Override
  public PurchaseItem createPurchaseItem(List<SoldItem> goods) {
    PurchaseItem purchaseItem = new PurchaseItem();
    purchaseItem.setSoldItems(goods);
    purchaseItem.setPurchaseDate(new Date());
    purchaseItem.calculateTotal();
    return purchaseItem;
  }

  @Override
  public void addStockItem(StockItem item) {
    stockItems.add(item);
  }

  public void submitCurrentPurchase(PurchaseItem purchaseItem) throws VerificationFailedException {
    for (SoldItem soldItem : purchaseItem.getSoldItems()) {
      soldItem.getStockItem().decreaseStock(soldItem.getQuantity());
    }
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
    return stockItems;
  }

  @Override
  public List<PurchaseItem> loadPurchaseHistory() {
    return purchaseItems;
  }

}
