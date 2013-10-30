package ee.ut.math.tvt.kvaliteetsedideed.domain.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseItem implements Cloneable, DisplayableItem {

  private Long id;
  private List<SoldItem> soldItems;
  private Date purchaseDate;
  private Double totalPrice;
  private boolean confirmed;

  @Override
  public Long getId() {
    return id;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Date purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public List<SoldItem> getSoldItems() {
    if (soldItems == null) {
      return new ArrayList<SoldItem>();
    }
    return soldItems;
  }

  public void setSoldItems(List<SoldItem> soldItems) {
    this.soldItems = soldItems;
  }

  public void calculateTotal() {
    totalPrice = new Double(0);
    for (SoldItem item : soldItems) {
      totalPrice += item.getSum();
    }
  }

  public void addSoldItem(SoldItem soldItem) {
    if (soldItems == null) {
      soldItems = new ArrayList<>();
    }
    soldItems.add(soldItem);
  }

  public void confirm() {
    this.confirmed = true;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

}
