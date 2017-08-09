package org.formation.order.bo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import org.formation.order.bo.exception.BOException;
import org.formation.order.dao.OrderDAO;
import org.formation.order.dto.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.ThrowsException;

public class OrderBOImplTest {

	private static final int ORDER_ID = 123;
	
	@Mock
	OrderDAO dao;
	private OrderBOImpl bo;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		bo = new OrderBOImpl();
		bo.setDao(dao);
	}
	
	@Test
	public void placeOrder_Should_Create_An_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.create(order)).thenReturn(new Integer(1));
		boolean result = bo.placeOrder(order);
		assertTrue(result);
		verify(dao).create(order);
	}
	
	@Test
	public void placeOrder_Should_Not_Create_An_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.create(order)).thenReturn(new Integer(0));
		boolean result = bo.placeOrder(order);
		assertFalse(result);
		verify(dao).create(order);		
	}
	
	@Test
	public void cancelOrder_Should_Cancel_The_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(anyInt())).thenReturn(order);
		when(dao.update(order)).thenReturn(new Integer(1));
		boolean result = bo.cancelOrder(ORDER_ID);
		assertTrue(result);
		verify(dao).read(anyInt());
		verify(dao).update(order);
	}
	
	@Test
	public void cancelOrder_Should_NOT_Cancel_The_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(anyInt())).thenReturn(order);
		when(dao.update(order)).thenReturn(new Integer(0));
		boolean result = bo.cancelOrder(anyInt());
		assertFalse(result);
		verify(dao).read(anyInt());
		verify(dao).update(order);
	}
	
	@Test
	public void deleteOrder_Should_Deletes_The_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.delete(anyInt())).thenReturn(new Integer(1));
		boolean result = bo.deleteOrder(anyInt());
		assertTrue(result);
		verify(dao).delete(anyInt());
	}
	
	@Test
	public void deleteOrder_Should_NOT_Deletes_The_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.delete(anyInt())).thenReturn(new Integer(0));
		boolean result = bo.deleteOrder(anyInt());
		assertFalse(result);
		verify(dao).delete(anyInt());
	}
	
	@Test(expected = BOException.class)
	public void placeOrder_Should_Throw_BOException() throws SQLException, BOException {
		Order order = new Order();
		when(dao.create(order)).thenThrow(SQLException.class);
		boolean result = bo.placeOrder(order);
		assertEquals(BOException.class, result);
		verify(dao).create(order);
	}
	
	@Test(expected = BOException.class)
	public void cancelOrder_Should_Throw_A_BOExceptionOnRead() throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(anyInt())).thenThrow(SQLException.class);
		boolean result = bo.cancelOrder(anyInt());
		assertEquals(BOException.class, result);
		verify(dao).read(anyInt());
	}
	
	@Test(expected = BOException.class)
	public void cancelOrder_Should_Throw_A_BOExceptionOnUpadte() throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(anyInt())).thenReturn(order);
		when(dao.update(order)).thenThrow(SQLException.class);
		boolean result = bo.cancelOrder(anyInt());
		assertEquals(BOException.class, result);
		verify(dao).read(anyInt());
		verify(dao).update(order);
	}

}
