package ee.ut.math.tvt.kvaliteetsedideed.ui.model;

import ee.ut.math.tvt.kvaliteetsedideed.domain.data.StockItem;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StockTableModelTest {

  StockTableModel model;
  StockItem item1;

  @Before
  public void setUp() {
    model = new StockTableModel();
    item1 = new StockItem(1L, "test1", "descr1", 10.0, 5);
    model.addItem(item1);
    model.addItem(new StockItem(2L, "test2", "descr2", 50.0, 3));

  }

  @Test
  public void testValidateNameUniqueness() {
    Assert.assertTrue(model.validateNameUniqueness("test100"));
    Assert.assertFalse(model.validateNameUniqueness("test1"));
  }

  @Test
  public void testHasEnoughInStock() {
    Assert.assertTrue(model.hasEnoughInStock(item1, 5));
    Assert.assertFalse(model.hasEnoughInStock(item1, 6));
  }

  @Test
  public void testGetItemByIdWhenItemExists() {
    Assert.assertEquals(item1, model.getItemById(1L));
  }

  @Test(expected = NoSuchElementException.class)
  public void testGetItemByIdWhenThrowsException() {
    Assert.assertEquals(item1, model.getItemById(3L));
  }

}
