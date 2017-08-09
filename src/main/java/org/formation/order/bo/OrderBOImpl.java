package org.formation.order.bo;

import java.sql.SQLException;

import org.formation.order.bo.exception.BOException;
import org.formation.order.dao.OrderDAO;
import org.formation.order.dto.Order;

public class OrderBOImpl implements OrderBO {

	private OrderDAO dao;
	
	
	public OrderDAO getDao() {
		return dao;
	}

	public void setDao(OrderDAO dao) {
		this.dao = dao;
	}

	@Override
	public Boolean placeOrder(Order order) throws BOException {
		try {
			int result = dao.create(order);
			if (result == 0) {
				return false;
			}
		} catch (SQLException e) {
			throw new BOException(e);
		}
		return true;
	}

	@Override
	public Boolean cancelOrder(int id) throws BOException {
		try {
			Order order = dao.read(id);
			order.setStatus("cancelled");
			int result = dao.update(order);
			if(result == 0) {
				return false;
			}
		} catch (SQLException e) {
			throw new BOException(e);
		}
		return true;
	}

	@Override
	public Boolean deleteOrder(int id) throws BOException {
		try {
			int result = dao.delete(id);
			if(result == 0) {
				return false;
			}
		} catch (SQLException e) {
			throw new BOException(e);
		}
		return true;
	}

}
