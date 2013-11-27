package ee.ut.math.tvt.kvaliteetsedideed.domain.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SoldItemTest {

  private StockItem item;
  private Purchase p;

  @Before
  public void setUp() {
    item = new StockItem((long) 100, "test", "test", 100, 100);
  }

  @Test
  public void testGetSum() {
    SoldItem s = new SoldItem(item, 2);

    assertEquals(s.getSum(), 200, 0.0001);
  }

  @Test
  public void testGetSumWithZeroQuantity() {
    SoldItem s = new SoldItem(item, 0);

    assertEquals(s.getSum(), 0, 0.001);
  }

  @Test
  public void testGetPurchase() {
    SoldItem s = new SoldItem(item, 0);

    assertEquals(s.getPurchase(), p);
  }
}
