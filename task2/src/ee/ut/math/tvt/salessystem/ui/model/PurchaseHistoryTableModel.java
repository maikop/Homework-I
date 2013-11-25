package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Purchase history model.
 */
public class PurchaseHistoryTableModel extends SalesSystemTableModel<Sale> {
	private static final long serialVersionUID = 1L;

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	private List<Sale> sales = new ArrayList<>();

	private final SalesDomainController controller;

	public PurchaseHistoryTableModel(SalesDomainController controller) {
		super(new String[] { "Id", "Time", "Sum", "Client" });
		this.controller = controller;
	}

	@Override
	protected Object getColumnValue(Sale sale, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return sale.getId();
		case 1:
			return DATE_FORMAT.format(sale.getSellingTime());
		case 2:
			return sale.getSum();
		case 3:
			return sale.getClient();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final Sale sale : sales) {
			buffer.append(sale.getId() + "\t");
			buffer.append(sale.getClient() != null ? sale.getClient().getFirstName() : "" + "\t");
			buffer.append(sale.getSum() + "\t");
			buffer.append("\n");
		}
		return buffer.toString();
	}

	@Override
	public List<Sale> getRows() {
		return sales;
	}

	@Override
	public void clearRows() {
		sales = new ArrayList<>();
	}

	@Override
	public void refresh() {
		sales = controller.getAllSales();
	}
}
