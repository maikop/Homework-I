package ee.ut.math.tvt.kvaliteetsedideed.ui.model;

import ee.ut.math.tvt.kvaliteetsedideed.domain.data.Purchase;
import java.util.Calendar;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseHistoryTableModelTest {

  @Test
  public void dateFormatDate() {
    PurchaseHistoryTableModel model = new PurchaseHistoryTableModel();
    Purchase purchase = new Purchase();
    Calendar calendar = Calendar.getInstance();
    calendar.set(2013, 10, 20, 23, 40);
    purchase.setPurchaseDate(calendar.getTime());

    Assert.assertEquals("2013/11/20 23:40", model.getColumnValue(purchase, 0));
  }
}
