package ee.ut.math.tvt.kvaliteetsedideed.domain.controller;

import ee.ut.math.tvt.kvaliteetsedideed.domain.data.Purchase;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.SoldItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.StockItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.exception.VerificationFailedException;
import java.util.List;

/**
 * Sales domain controller is responsible for the domain specific business
 * processes.
 */
public interface SalesDomainController {

  /**
   * Load the current state of the warehouse.
   * 
   * @return List of ${link ee.ut.math.tvt.salessystem.domain.data.StockItem}s.
   */
  public List<StockItem> loadWarehouseState();

  public List<Purchase> loadPurchaseHistory();

  // business processes
  /**
   * Initiate new business transaction - purchase of the goods.
   * 
   * @throws VerificationFailedException
   */
  public void startNewPurchase() throws VerificationFailedException;

  /**
   * Rollback business transaction - purchase of goods.
   * 
   * @throws VerificationFailedException
   */
  public void cancelCurrentPurchase() throws VerificationFailedException;

  /**
   * Commit business transaction - purchase of goods.
   * 
   * @param goods
   *          Goods that the buyer has chosen to buy.
   * @throws VerificationFailedException
   */
  public void submitCurrentPurchase(Purchase purchaseItem) throws VerificationFailedException;

  public Purchase createPurchaseItem(List<SoldItem> goods);

  public void addStockItem(StockItem item);

  public void endSession();

}
