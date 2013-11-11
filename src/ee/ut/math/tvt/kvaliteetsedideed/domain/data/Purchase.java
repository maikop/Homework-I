package ee.ut.math.tvt.kvaliteetsedideed.domain.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "PURCHASE")
public class Purchase implements Cloneable, DisplayableItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "purchase")
  private List<SoldItem> soldItems;

  @Column(name = "DATE")
  private Date purchaseDate;

  @Transient
  private Double totalPrice;
  @Transient
  private boolean confirmed;

  @Override
  public Long getId() {
    return id;
  }

  public Purchase() {

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
