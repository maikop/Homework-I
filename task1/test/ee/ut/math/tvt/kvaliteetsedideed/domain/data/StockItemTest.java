package ee.ut.math.tvt.kvaliteetsedideed.domain.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StockItemTest {

  private StockItem item;

  @Before
  public void setUp() {
    item = new StockItem((long) 100, "test", "test", 100, 100);
  }

  @Test
  public void testClone() {
    StockItem sc = (StockItem) item.clone();
    assertEquals(item.getId(), sc.getId());
    assertEquals(item.getName(), sc.getName());
    assertEquals(item.getDescription(), sc.getDescription());
    assertEquals(item.getPrice(), sc.getPrice(), 0.0001);
    assertEquals(item.getQuantity(), sc.getQuantity());
  }

  @Test
  public void testGetColumn() {
    assertEquals(item.getId(), item.getColumn(0));
    assertEquals(item.getName(), item.getColumn(1));
    assertEquals(item.getPrice(), item.getColumn(2));
    assertEquals(item.getQuantity(), item.getColumn(3));

  }

  @Test
  public void testDecreaseStock() {
    item.decreaseStock(1);

    assertEquals(item.getQuantity(), 99);
  }
}
