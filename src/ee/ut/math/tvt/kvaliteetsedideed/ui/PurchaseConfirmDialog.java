package ee.ut.math.tvt.kvaliteetsedideed.ui;

import ee.ut.math.tvt.kvaliteetsedideed.domain.data.PurchaseItem;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PurchaseConfirmDialog extends JDialog {

  private static final long serialVersionUID = 1L;
  private GridBagConstraints gc;
  private PurchaseItem purchaseItem;
  private JTextField paidAmountTextField;
  private JTextField returnAmountTextField;
  private JButton confirmButton;

  public PurchaseConfirmDialog(PurchaseItem purchaseItem, Component parent) {
    getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
    setModal(true);
    setLocationRelativeTo(parent);
    this.purchaseItem = purchaseItem;
    setLayout(new GridBagLayout());
    gc = new GridBagConstraints();
    setResizable(false);
    setTitle("Confirm purchase");

    addTitle();
    addDateAndTime();
    addTotal();
    addPaidAmount();
    addReturnAmount();
    addButtons();

    pack();
    setVisible(true);
  }

  private void addTitle() {
    gc.gridx = 0;
    gc.gridy = 0;
    gc.ipadx = 5;
    gc.ipady = 5;
    gc.fill = GridBagConstraints.BOTH;
    gc.gridwidth = 1;
    gc.insets = new Insets(0, 0, 10, 0);
    JLabel titleLabel = new JLabel("Confirm purchase");
    titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
    add(titleLabel, gc);
    gc.insets = new Insets(0, 0, 0, 0);
  }

  private JLabel getBoldLabel(String content) {
    JLabel boldLabel = new JLabel(content);
    boldLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
    return boldLabel;
  }

  private void addDateAndTime() {
    gc.gridx = 0;
    gc.gridwidth = 1;
    gc.gridy = 1;
    add(getBoldLabel("Date and time"), gc);

    gc.gridx = 1;
    JLabel dateAndTime = new JLabel(new SimpleDateFormat("yyyy/MM/dd 'at' HH:mm").format(purchaseItem.getPurchaseDate()));
    add(dateAndTime, gc);
  }

  private void addTotal() {
    gc.gridx = 0;
    gc.gridy = 2;

    add(getBoldLabel("Total:"), gc);

    gc.gridx = 1;
    JLabel total = new JLabel(purchaseItem.getTotalPrice().toString());
    add(total, gc);
  }

  private void addPaidAmount() {
    gc.gridx = 0;
    gc.gridy = 3;
    add(getBoldLabel("Paid amount:"), gc);

    gc.gridx = 1;
    paidAmountTextField = new JTextField();
    paidAmountTextField.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void removeUpdate(DocumentEvent e) {
        updateReturnAmount();
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        updateReturnAmount();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        updateReturnAmount();
      }
    });
    add(paidAmountTextField, gc);
  }

  private void updateReturnAmount() {
    Double returnAmount;
    try {
      Double paidAmount = Double.parseDouble(paidAmountTextField.getText());
      returnAmount = paidAmount - purchaseItem.getTotalPrice();
      if (returnAmount < 0) {
        confirmButton.setText("Paid amount too small");
        confirmButton.setEnabled(false);
        returnAmount = 0d;
      } else {
        confirmButton.setText("Confirm purchase");
        confirmButton.setEnabled(true);
      }
    } catch (NumberFormatException e) {
      confirmButton.setText("Paid amount too small");
      confirmButton.setEnabled(false);
      returnAmount = 0d;
    }
    returnAmountTextField.setText(String.valueOf(returnAmount));
  }

  private void addReturnAmount() {
    gc.gridx = 0;
    gc.gridy = 4;
    add(getBoldLabel("To return:"), gc);

    gc.gridx = 1;
    returnAmountTextField = new JTextField();
    returnAmountTextField.setEnabled(false);
    add(returnAmountTextField, gc);
  }

  private void addButtons() {
    confirmButton = new JButton("Paid amount too small");
    confirmButton.setEnabled(false);
    confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        purchaseItem.confirm();
        dispose();
      }
    });
    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });

    gc.gridx = 0;
    gc.gridy = 5;
    add(confirmButton, gc);

    gc.gridx = 1;
    add(cancelButton, gc);
  }
}
