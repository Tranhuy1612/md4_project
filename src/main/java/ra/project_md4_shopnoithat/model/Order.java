package ra.project_md4_shopnoithat.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private  int id ;
    private int userId;
    private  double totalPrice;
    private  String phoneNumber;
    private  String address;
    private boolean status = true ;
    private Date buyDate = new Date();
    private List<CartItem> orderDetail = new ArrayList<>();

    public Order() {
    }

    public Order(int id, int userId, double totalPrice, String phoneNumber, String address, boolean status, Date buyDate, List<CartItem> orderDetail) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.buyDate = buyDate;
        this.orderDetail = orderDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public List<CartItem> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<CartItem> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
