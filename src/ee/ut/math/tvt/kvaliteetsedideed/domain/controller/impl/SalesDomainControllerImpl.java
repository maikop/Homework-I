package ee.ut.math.tvt.kvaliteetsedideed.domain.controller.impl;

import ee.ut.math.tvt.kvaliteetsedideed.domain.controller.SalesDomainController;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.Purchase;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.SoldItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.StockItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.kvaliteetsedideed.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;

/**
 * Implementation of the sales domain controller.
 */
@SuppressWarnings("unchecked")
public class SalesDomainControllerImpl implements SalesDomainController {

  private List<Purchase> purchaseItems = new ArrayList<Purchase>();
  private List<StockItem> stockItems = new ArrayList<StockItem>();

  private Session session = HibernateUtil.currentSession();

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
  public Purchase createPurchaseItem(List<SoldItem> goods) {
    Purchase purchaseItem = new Purchase();
    purchaseItem.setSoldItems(goods);
    purchaseItem.setPurchaseDate(new Date());
    purchaseItem.calculateTotal();
    return purchaseItem;
  }

  @Override
  public void addStockItem(StockItem item) {
    stockItems.add(item);
  }

  public void submitCurrentPurchase(Purchase purchaseItem) throws VerificationFailedException {
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
    // return stockItems;
    return session.createQuery("from StockItem").list();

  }

  @Override
  public List<Purchase> loadPurchaseHistory() {
    return session.createQuery("from Purchase").list();
  }

  @Override
  public void endSession() {
    HibernateUtil.closeSession();
  }

}
