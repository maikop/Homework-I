package ee.ut.math.tvt.kvaliteetsedideed.ui.model;

import ee.ut.math.tvt.kvaliteetsedideed.domain.data.PurchaseItem;

public class PurchaseHistoryTableModel extends SalesSystemTableModel<PurchaseItem> {

  private static final long serialVersionUID = 1L;

  public PurchaseHistoryTableModel() {
    super(new String[] { "Time", "Total price" });
  }

  @Override
  protected Object getColumnValue(PurchaseItem item, int columnIndex) {
    switch (columnIndex) {
    case 0:
      return item.getPurchaseDate();
    case 1:
      return item.getTotalPrice();
    }
    throw new IllegalArgumentException("Column index out of range");
  }

}
