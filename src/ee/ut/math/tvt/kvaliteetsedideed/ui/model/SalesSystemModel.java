package ee.ut.math.tvt.kvaliteetsedideed.ui.model;

import ee.ut.math.tvt.kvaliteetsedideed.domain.controller.SalesDomainController;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.StockItem;
import java.util.List;
import javax.swing.DefaultComboBoxModel;


/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {

  // Warehouse model
  private StockTableModel warehouseTableModel;

  // Current shopping cart model
  private PurchaseInfoTableModel currentPurchaseTableModel;

  // Purchase history model
  private PurchaseHistoryTableModel purchaseHistoryTableModel;
  private DefaultComboBoxModel<StockItem> stockComboBoxModel;

  /**
   * Construct application model.
   * 
   * @param domainController
   *          Sales domain controller.
   */
  public SalesSystemModel(SalesDomainController domainController) {
    warehouseTableModel = new StockTableModel();
    currentPurchaseTableModel = new PurchaseInfoTableModel();

    purchaseHistoryTableModel = new PurchaseHistoryTableModel();

    // populate stock model with data from the warehouse
    warehouseTableModel.populateWithData(domainController.loadWarehouseState());
    purchaseHistoryTableModel.populateWithData(domainController.loadPurchaseHistory());
    List<StockItem> stockItems = domainController.loadWarehouseState();
    stockComboBoxModel = new DefaultComboBoxModel<StockItem>(stockItems.toArray(new StockItem[stockItems.size()]));
  }

  public DefaultComboBoxModel<StockItem> getStockComboBoxModel() {
    return stockComboBoxModel;
  }

  public StockTableModel getWarehouseTableModel() {
    return warehouseTableModel;
  }

  public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
    return currentPurchaseTableModel;
  }

  public PurchaseHistoryTableModel getPurchaseHistoryTableModel() {
    return purchaseHistoryTableModel;
  }
}
