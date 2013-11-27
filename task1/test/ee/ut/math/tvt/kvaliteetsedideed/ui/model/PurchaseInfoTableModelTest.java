package ee.ut.math.tvt.kvaliteetsedideed.ui.model;

import static org.junit.Assert.assertEquals;

import ee.ut.math.tvt.kvaliteetsedideed.domain.data.SoldItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.StockItem;
import org.junit.Before;
import org.junit.Test;

public class PurchaseInfoTableModelTest {

  private PurchaseInfoTableModel pit;
  private SoldItem s;
  private StockItem si;

  @Before
  public void setup() {
    pit = new PurchaseInfoTableModel();
    si = new StockItem((long) 17, "test", "test", 1.0, 1);
    s = new SoldItem(si, 1);
    pit.addItem(s);

  }

  @Test
  public void testAddItem() {
    assertEquals(pit.getColumnValue(s, 0), si.getId());
    assertEquals(pit.getColumnValue(s, 1), si.getName());
    assertEquals(pit.getColumnValue(s, 2), si.getPrice());
    assertEquals(pit.getColumnValue(s, 3), si.getQuantity());
  }

  @Test
  public void testGetItemByStockItemId() {

    assertEquals(pit.getItemByStockItemId((long) 17), s);
  }
}
