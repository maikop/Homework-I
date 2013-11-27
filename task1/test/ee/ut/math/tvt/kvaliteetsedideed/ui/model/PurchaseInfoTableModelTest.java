package ee.ut.math.tvt.kvaliteetsedideed.ui.model;

import static org.junit.Assert.assertEquals;

import ee.ut.math.tvt.kvaliteetsedideed.domain.data.SoldItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.StockItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.exception.VerificationFailedException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class PurchaseInfoTableModelTest {

  private PurchaseInfoTableModel pit;
  private SoldItem s;
  private StockItem si;

  @Before
  public void setup() throws VerificationFailedException {
    pit = new PurchaseInfoTableModel();
    si = new StockItem(170L, "test", "test", 1.0, 2);
    s = new SoldItem(si, 1);
    List<SoldItem> list = new ArrayList<SoldItem>();
    list.add(s);
    pit.populateWithData(list);

  }

  @Test
  public void testAddItem() {
    assertEquals(pit.getColumnValue(s, 0), si.getId());
    assertEquals(pit.getColumnValue(s, 1), si.getName());
    assertEquals(pit.getColumnValue(s, 2), si.getPrice());
    assertEquals(pit.getColumnValue(s, 3), s.getQuantity());
  }

  @Test
  public void testGetItemByStockItemId() {
    assertEquals(pit.getItemByStockItemId(170L), s);
  }
}
