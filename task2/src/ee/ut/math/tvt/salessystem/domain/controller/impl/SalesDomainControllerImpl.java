package ee.ut.math.tvt.salessystem.domain.controller.impl;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Client;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.SalesSystemException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

	private static final Logger log = Logger.getLogger(SalesDomainControllerImpl.class);
	private SalesSystemModel model;
	private final Session session = HibernateUtil.currentSession();

	@Override
	@SuppressWarnings("unchecked")
	public List<StockItem> getAllStockItems() {
		List<StockItem> result = session.createQuery("from StockItem").list();

		log.info(result.size() + " items loaded from disk");
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Sale> getAllSales() {
		List<Sale> result = session.createQuery("from Sale").list();
		log.info(result.size() + " Sales loaded from disk");

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Client> getAllClients() {
		List<Client> clients = session.createQuery("from Client").list();
		log.info(clients.size() + " clients loaded from disk");

		return clients;
	}

	@Override
	public Client getClient(long id) {
		return (Client) session.get(Client.class, id);
	}

	private StockItem getStockItem(long id) {
		return (StockItem) session.get(StockItem.class, id);
	}

	@Override
	public void registerSale(Sale sale) {
		Transaction tx = session.beginTransaction();
		sale.setSellingTime(new Date());

		for (SoldItem item : sale.getSoldItems()) {
			StockItem stockItem = getStockItem(item.getStockItem().getId());
			stockItem.setQuantity(stockItem.getQuantity() - item.getQuantity());
			session.save(stockItem);
		}
		session.save(sale);
		tx.commit();
	}

	@Override
	public SoldItem verifyEnoughInStock(StockItem stockItem, int quantity)
	    throws SalesSystemException {
		SoldItem existingItem = model.getActiveSale().getForStockItem(stockItem.getId());
		int existingQuantity = 0;
		if (existingItem != null) {
			existingQuantity = existingItem.getQuantity();
		}
		if (stockItem.getQuantity() < existingQuantity + quantity) {
			log.info(" -- not enough in stock!");
			throw new SalesSystemException();
		}
		return existingItem;
	}

	@Override
	public void createStockItem(StockItem stockItem) {
		Transaction tx = session.beginTransaction();
		session.save(stockItem);
		tx.commit();
		log.info("Added new stockItem : " + stockItem);
	}

	@Override
	public void cancelCurrentPurchase() {
		model.setActiveSale(null);
		log.info("Current purchase canceled");
	}

	@Override
	public void startNewPurchase(Client client) {
		model.setActiveSale(new Sale(client));
		log.info("New purchase started");
	}

	@Override
	public void setModel(SalesSystemModel model) {
		this.model = model;
	}

	public Sale getSale(Long id) {
		return (Sale) session.get(Sale.class, id);
	}

	@Override
	public void endSession() {
		HibernateUtil.closeSession();
	}

	@Override
	public Sale getActiveSale() {
		return model.getActiveSale();
	}

	@Override
	public void addSoldItemToActiveSale(SoldItem soldItem) {
		model.getActiveSale().addSoldItem(soldItem);
	}
}
