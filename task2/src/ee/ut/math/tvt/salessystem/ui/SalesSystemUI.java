package ee.ut.math.tvt.salessystem.ui;

import com.jgoodies.looks.windows.WindowsLookAndFeel;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.ClientTab;
import ee.ut.math.tvt.salessystem.ui.tabs.HistoryTab;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;
import ee.ut.math.tvt.salessystem.ui.tabs.SalesSystemTab;
import ee.ut.math.tvt.salessystem.ui.tabs.StockTab;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.log4j.Logger;

/**
 * Graphical user interface of the sales system.
 */
public class SalesSystemUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SalesSystemUI.class);

	private final SalesSystemModel model;
	private final SalesDomainController domainController;

	private final List<SalesSystemTab> tabs;

	/**
	 * Constructs sales system GUI.
	 * 
	 * @param domainController
	 *          Sales domain controller.
	 */
	public SalesSystemUI(SalesDomainController domainController) {
		this.domainController = domainController;
		this.model = new SalesSystemModel(domainController);
		domainController.setModel(model);

		tabs = new ArrayList<>();
		tabs.add(new PurchaseTab(domainController, model, this));
		tabs.add(new StockTab(model, domainController));
		tabs.add(new HistoryTab(model, domainController));
		tabs.add(new ClientTab(model));

		setTitle("Sales system");

		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			log.warn(e1.getMessage());
		}

		drawWidgets();

		int width = 600;
		int height = 400;
		setSize(width, height);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - width) / 2, (screen.height - height) / 2);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SalesSystemUI.this.domainController.endSession();
				log.info("SalesSystem closed");
				System.exit(0);
			}
		});
	}

	private void drawWidgets() {
		JTabbedPane tabbedPane = new JTabbedPane();

		for (SalesSystemTab tab : tabs) {
			tabbedPane.add(tab.getName(), tab.draw());
		}

		getContentPane().add(tabbedPane);

		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Integer tabIndex = ((JTabbedPane) e.getSource()).getSelectedIndex();
				tabs.get(tabIndex).refreshContents();
			}
		});
	}
}
