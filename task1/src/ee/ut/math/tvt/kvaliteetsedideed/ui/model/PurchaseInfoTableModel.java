package ee.ut.math.tvt.kvaliteetsedideed.ui.model;

import ee.ut.math.tvt.kvaliteetsedideed.domain.data.SoldItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.exception.VerificationFailedException;
import org.apache.log4j.Logger;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
  private static final long serialVersionUID = 1L;

  private static final Logger log = Logger.getLogger(PurchaseInfoTableModel.class);
  private SalesSystemModel model;

  public PurchaseInfoTableModel(SalesSystemModel model) {
    this();
    this.model = model;
  }

  public PurchaseInfoTableModel() {
    super(new String[] { "Id", "Name", "Price", "Quantity", "Sum" });
  }

  @Override
  protected Object getColumnValue(SoldItem item, int columnIndex) {
    switch (columnIndex) {
    case 0:
      return item.getStockItem().getId();
    case 1:
      return item.getName();
    case 2:
      return item.getPrice();
    case 3:
      return item.getQuantity();
    case 4:
      return (double) Math.round((item.getSum()) * 100) / 100;
    }
    throw new IllegalArgumentException("Column index out of range");
  }

  @Override
  public String toString() {
    final StringBuffer buffer = new StringBuffer();

    for (int i = 0; i < headers.length; i++)
      buffer.append(headers[i] + "\t");
    buffer.append("\n");

    for (final SoldItem item : rows) {
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
  public void addItem(final SoldItem item) throws VerificationFailedException {
    Integer wantedQuantity = item.getQuantity();
    SoldItem existingItem = null;

    for (final SoldItem row : rows) {
      if (row.getStockItem().getId().equals(item.getStockItem().getId())) {
        wantedQuantity += row.getQuantity();
        existingItem = row;
        break;
      }
    }
    if (model.getWarehouseTableModel().hasEnoughInStock(item.getStockItem(), wantedQuantity)) {
      if (existingItem != null) {
        existingItem.setQuantity(wantedQuantity);
      } else {
        rows.add(item);
      }
    } else {
      throw new VerificationFailedException("Your desired quantity exceeds the amount in stock!");
    }
    fireTableDataChanged();
  }

  public SoldItem getItemByStockItemId(Long id) {
    for (SoldItem soldItem : rows) {
      if (id.equals(soldItem.getStockItem().getId())) {
        return soldItem;
      }
    }
    return null;
  }
}
