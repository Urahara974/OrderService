package org.formation.order.bo;

import org.formation.order.bo.exception.BOException;
import org.formation.order.dto.Order;

public interface OrderBO {
	
	public Boolean placeOrder(Order order) throws BOException;
	public Boolean cancelOrder(int id) throws BOException;
	public Boolean deleteOrder(int id) throws BOException;

}
