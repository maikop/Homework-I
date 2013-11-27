package ee.ut.math.tvt.kvaliteetsedideed.domain.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class PurchaseTest {

  private StockItem item;
  private List<SoldItem> soldItems;
  private SoldItem s;
  private SoldItem s2;
  private Double totalPrice;

  private Double getTotalPrice() {
    if (totalPrice == null) {
      calculateTotal();
    }
    return totalPrice;
  }

  private void calculateTotal() {
    totalPrice = new Double(0);
    for (SoldItem item : soldItems) {
      totalPrice += item.getSum();
    }
  }

  private void addSoldItem(SoldItem soldItem) {
    if (soldItems == null) {
      soldItems = new ArrayList();
    }
    soldItems.add(soldItem);
  }

  @Before
  public void setUp() {
    item = new StockItem((long) 100, "test", "test", 100, 100);
    s = new SoldItem(item, 1);
    s2 = new SoldItem(item, 2);
  }

  @Test
  public void testAddSoldItem() {
    addSoldItem(s);
    assertEquals(s, soldItems.get(0));
  }

  @Test
  // ///////////////////////// TODO
  public void testGetTotalPriceWithNoItems() {
    totalPrice = new Double(0);
    assertEquals(getTotalPrice(), 0.0, 0.001);
  }

  @Test
  public void testGetSumWithOneItem() {
    addSoldItem(s);
    assertEquals(getTotalPrice(), 100, 0.001);
  }

  @Test
  public void testGetSumWithMultipleItem() {
    addSoldItem(s);
    addSoldItem(s2);
    assertEquals(getTotalPrice(), 300, 0.001);
  }

}