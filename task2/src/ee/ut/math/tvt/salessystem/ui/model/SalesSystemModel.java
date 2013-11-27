package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Client;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import java.util.List;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {

	// Warehouse model
	private final StockTableModel warehouseTableModel;

	// Current shopping cart model
	private final PurchaseInfoTableModel currentPurchaseTableModel;
	// Puchase history model
	private PurchaseHistoryTableModel purchaseHistoryTableModel;

	private final ClientTableModel clientTableModel;

	/**
	 * Construct application model.
	 * 
	 * @param domainController
	 *          Sales domain controller.
	 */
	public SalesSystemModel(SalesDomainController domainController) {

		warehouseTableModel = new StockTableModel(domainController);
		currentPurchaseTableModel = new PurchaseInfoTableModel(this);
		purchaseHistoryTableModel = new PurchaseHistoryTableModel(domainController);
		clientTableModel = new ClientTableModel(domainController);

		// Load data from the database

		List<StockItem> stockItems = domainController.getAllStockItems();
		warehouseTableModel.populateWithData(stockItems);

		List<Client> clients = domainController.getAllClients();
		clientTableModel.populateWithData(clients);

		List<Sale> sales = domainController.getAllSales();
		purchaseHistoryTableModel.populateWithData(sales);

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

	public ClientTableModel getClientTableModel() {
		return clientTableModel;
	}

	public void setPurchaseHistoryTableModel(PurchaseHistoryTableModel purchaseHistoryTableModel) {
		this.purchaseHistoryTableModel = purchaseHistoryTableModel;
	}

}
