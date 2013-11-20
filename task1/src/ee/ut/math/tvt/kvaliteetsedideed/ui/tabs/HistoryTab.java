package ee.ut.math.tvt.kvaliteetsedideed.ui.tabs;

import ee.ut.math.tvt.kvaliteetsedideed.domain.data.Purchase;
import ee.ut.math.tvt.kvaliteetsedideed.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.kvaliteetsedideed.ui.model.SalesSystemModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {

  private final SalesSystemModel model;
  private final PurchaseInfoTableModel purchaseInfoTableModel;

  public HistoryTab(SalesSystemModel model) {
    this.model = model;
    purchaseInfoTableModel = new PurchaseInfoTableModel();
  }

  public Component draw() {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    GridBagLayout gb = new GridBagLayout();
    GridBagConstraints gc = new GridBagConstraints();
    panel.setLayout(gb);

    gc.fill = GridBagConstraints.BOTH;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 1d;
    panel.add(drawPurchaseListPane(), gc);

    gc.weighty = 1.0;
    gc.fill = GridBagConstraints.BOTH;
    panel.add(drawPurchaseDetailViewPane(), gc);

    return panel;
  }

  private Component drawPurchaseListPane() {
    JPanel panel = new JPanel();
    final JTable table = new JTable(model.getPurchaseHistoryTableModel());

    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      @Override
      public void valueChanged(ListSelectionEvent e) {
        Integer selectedIndex = table.getSelectedRow();
        if (selectedIndex == -1) {
          selectedIndex = e.getLastIndex();
          table.setRowSelectionInterval(selectedIndex, selectedIndex);
        }
        Purchase selectedItem = model.getPurchaseHistoryTableModel().getTableRows().get(selectedIndex);
        purchaseInfoTableModel.populateWithData(selectedItem.getSoldItems());
        purchaseInfoTableModel.fireTableDataChanged();

      }
    });
    JTableHeader header = table.getTableHeader();
    header.setReorderingAllowed(false);

    JScrollPane scrollPane = new JScrollPane(table);

    GridBagConstraints gc = new GridBagConstraints();
    GridBagLayout gb = new GridBagLayout();
    gc.fill = GridBagConstraints.BOTH;
    gc.weightx = 1.0;
    gc.weighty = 1.0;

    panel.setLayout(gb);
    panel.add(scrollPane, gc);
    panel.setBorder(BorderFactory.createTitledBorder("Purchase history"));
    return panel;

  }

  private Component drawPurchaseDetailViewPane() {
    JPanel panel = new JPanel();
    JTable table = new JTable(purchaseInfoTableModel);
    JTableHeader header = table.getTableHeader();
    header.setReorderingAllowed(false);
    JScrollPane scrollPane = new JScrollPane(table);

    GridBagConstraints gc = new GridBagConstraints();
    GridBagLayout gb = new GridBagLayout();
    gc.fill = GridBagConstraints.BOTH;
    gc.weightx = 1.0;
    gc.weighty = 1.0;
    panel.setLayout(gb);
    panel.add(scrollPane, gc);
    panel.setBorder(BorderFactory.createTitledBorder("Order details"));

    return panel;
  }
}