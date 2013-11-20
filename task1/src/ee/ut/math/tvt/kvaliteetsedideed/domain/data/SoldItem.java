package ee.ut.math.tvt.kvaliteetsedideed.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving
 * history.
 */
@Entity
@Table(name = "SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "STOCKITEM_ID", nullable = false)
  private StockItem stockItem;

  @Column(name = "QUANTITY")
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "PURCHASE_ID", nullable = false)
  private Purchase purchase;

  public SoldItem(StockItem stockItem, int quantity) {
    this.stockItem = stockItem;
    this.quantity = quantity;
  }

  public SoldItem() {

  }

  @Override
  public Long getId() {
    return id;
  }

  public String getName() {
    return stockItem.getName();
  }

  public double getPrice() {
    return stockItem.getPrice();
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public double getSum() {
    return stockItem.getPrice() * ((double) quantity);
  }

  public StockItem getStockItem() {
    return stockItem;
  }

  public void setStockItem(StockItem stockItem) {
    this.stockItem = stockItem;
  }

  public Purchase getPurchase() {
    return purchase;
  }

  public void setPurchase(Purchase purchase) {
    this.purchase = purchase;
  }

}
