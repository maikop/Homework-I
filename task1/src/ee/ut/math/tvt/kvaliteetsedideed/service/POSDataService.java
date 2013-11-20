package ee.ut.math.tvt.kvaliteetsedideed.service;

import ee.ut.math.tvt.kvaliteetsedideed.domain.data.DisplayableItem;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.Purchase;
import ee.ut.math.tvt.kvaliteetsedideed.domain.data.StockItem;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

@SuppressWarnings("unchecked")
public class POSDataService {

  private Session session = ee.ut.math.tvt.kvaliteetsedideed.util.HibernateUtil.currentSession();

  public List<StockItem> getStockItems() {
    return session.createQuery("from StockItem").list();
  }

  public List<Purchase> getPurchases() {
    return session.createQuery("from Purchase").list();
  }

  public void save(DisplayableItem entity) {
    Transaction transaction = session.beginTransaction();
    if (entity.getId() == null) {
      session.save(entity);
    } else {
      session.update(entity);
    }
    transaction.commit();
  }
}