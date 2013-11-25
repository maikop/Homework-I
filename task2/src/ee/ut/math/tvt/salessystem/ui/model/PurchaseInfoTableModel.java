package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.SalesSystemException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PurchaseInfoTableModel.class);
	private Sale sale = new Sale();

	public PurchaseInfoTableModel(SalesDomainController controller) {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum" });
		this.controller = controller;
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem item : getRows()) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	/**
	 * Add new StockItem to table.
	 */
	public void addItem(final SoldItem soldItem) throws SalesSystemException {
		StockItem stockItem = soldItem.getStockItem();
		SoldItem existingItem = controller.verifyEnoughInStock(stockItem, soldItem.getQuantity());
		if (existingItem != null) {
			existingItem.increaseQuantity(soldItem.getQuantity());
			log.debug("Found existing item " + soldItem.getName() + " increased quantity by "
			    + soldItem.getQuantity());
		} else {
			controller.addSoldItemToActiveSale(soldItem);
			log.debug("Added " + soldItem.getName() + " quantity of " + soldItem.getQuantity());
		}
		refresh();
	}

	/**
	 * Replace the current contents of the table with the SoldItems of the given
	 * Sale. (Used by the history details table in the HistoryTab).
	 */
	public void showSale(Sale sale) {
		this.sale = sale;
		fireTableDataChanged();
	}

	@Override
	public List<SoldItem> getRows() {
		List<SoldItem> rows = new ArrayList<>();
		if (sale.getSoldItems() != null) {
			rows.addAll(sale.getSoldItems());
		}
		return rows;
	}

	@Override
	public void clearRows() {
		this.sale = new Sale();
	}

	@Override
	public void refresh() {
		this.sale = controller.getActiveSale();
		fireTableDataChanged();
	}

}
