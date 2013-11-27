package ee.ut.math.tvt.kvaliteetsedideed.domain.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PurchaseTest {

  private StockItem item;
  private SoldItem s1;
  private SoldItem s2;
  private SoldItem s3;
  private Purchase p;

  @Before
  public void setUp() {
    item = new StockItem(100L, "test", "test", 100, 100);
    s1 = new SoldItem(item, 0);
    s2 = new SoldItem(item, 1);
    s3 = new SoldItem(item, 2);
    p = new Purchase();
  }

  @Test
  public void testAddSoldItem() {
    p.addSoldItem(s2);
    assertEquals(s2, p.getSoldItems().get(0));
  }

  @Test
  public void testGetTotalPriceWithNoItems() {
    p.addSoldItem(s1);
    assertEquals(p.getTotalPrice(), 0.0, 0.0001);
  }

  @Test
  public void testGetSumWithOneItem() {
    p.addSoldItem(s2);
    assertEquals(p.getTotalPrice(), 100, 0.0001);
  }

  @Test
  public void testGetSumWithMultipleItems() {
    p.addSoldItem(s2);
    p.addSoldItem(s3);
    assertEquals(p.getTotalPrice(), 300, 0.0001);
  }

}