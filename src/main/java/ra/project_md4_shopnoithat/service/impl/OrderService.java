package ra.project_md4_shopnoithat.service.impl;

import org.springframework.stereotype.Service;
import ra.project_md4_shopnoithat.model.CartItem;
import ra.project_md4_shopnoithat.model.Order;
import ra.project_md4_shopnoithat.service.IGenericService;
import ra.project_md4_shopnoithat.untils.ConnectDB;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService implements IGenericService<Order, Integer> {


    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{call getAllOrders}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("id_user"));
                order.setPhoneNumber(rs.getString("phoneNumber"));
                order.setAddress(rs.getString("address"));
                order.setTotalPrice(rs.getInt("totalPrice"));
                order.setStatus(rs.getBoolean("status"));
                order.setBuyDate(rs.getDate("dateBuy"));
                orders.add(order);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return orders;
    }

    @Override
    public void save(Order order) {

    }

    @Override
    public Order findById(Integer id) {
        Connection connection = null;
        Order order = null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL findOrderById(?)}");
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("id_user"));
                order.setPhoneNumber(rs.getString("phoneNumber"));
                order.setAddress(rs.getString("address"));
                order.setTotalPrice(rs.getInt("totalPrice"));
                order.setStatus(rs.getBoolean("status"));
                order.setBuyDate(rs.getDate("dateBuy"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return order;
    }

    @Override
    public void delete(Integer id) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call deletelOrder(?)}");
            callSt.setInt(1,id);
            callSt.executeUpdate();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
    }
    public void insertOrder(Order order, List<CartItem> carts) {
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            connection.setAutoCommit(false);

            CallableStatement callSt1 = connection.prepareCall("{call insertOrders(?, ?)}");
            callSt1.setInt(1, order.getUserId());
            callSt1.registerOutParameter(2, Types.INTEGER);
            callSt1.executeUpdate();

            int order_id = callSt1.getInt(2);

            for (CartItem cart : carts) {
                try {
                    CallableStatement callSt2 = connection.prepareCall("{call insertOrders_detail(?, ?, ?, ?)}");
                    callSt2.setLong(1, cart.getProduct().getId());
                    callSt2.setInt(2, cart.getQuantity());
                    callSt2.setInt(3, order_id);
                    callSt2.executeUpdate();
                } catch (Exception e) {
                    connection.rollback();
                    throw new RuntimeException("Error while adding order detail", e);
                }
            }

            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace(); // Print specific error message
            throw new RuntimeException("Error while adding order", e);
        } finally {
            ConnectDB.closeConnection(connection);
        }
    }
    public void  toggleOrder(int id) {
        Connection conn =null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call toggleOrder(?)}");
            callSt.setInt(1,id);
            callSt.executeUpdate();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            ConnectDB.closeConnection(conn);
        }
    }

}
