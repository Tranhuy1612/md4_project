package ra.project_md4_shopnoithat.service.impl;

import org.springframework.stereotype.Service;
import ra.project_md4_shopnoithat.model.Catalog;
import ra.project_md4_shopnoithat.model.Product;
import ra.project_md4_shopnoithat.service.IGenericService;
import ra.project_md4_shopnoithat.untils.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService implements IGenericService<Product, Integer> {
    private final String FINDALL = "SELECT * FROM PRODUCT";
    private final String FINDBYID = "SELECT * FROM PRODUCT WHERE id = ?";
    private final String INSERT = "INSERT INTO  PRODUCT(id_catalog, name, price, image_avatar,description, stock, status) values(?,?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE PRODUCT SET id_catalog = ?, name= ?, price= ?,image_avatar = ?,description= ?,stock=?,status=? where id = ?";
    private final String DELETE = "DELETE  FROM PRODUCT WHERE id = ?";

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall(FINDALL);
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setCatalog_id(rs.getInt("id_catalog"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setImg_url(rs.getString("image_avatar"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
                // Add the product to the list
                list.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }

        return list;
    }


    @Override
    public Product findById(Integer id) {
        Connection conn = null;
        Product product = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall(FINDBYID);
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setCatalog_id(rs.getInt("id_catalog"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setImg_url(rs.getString("image_avatar"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }

        return product;
    }


    @Override
    public void save(Product product) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            if (product.getId() == 0) {
                CallableStatement callSt = conn.prepareCall(INSERT);
                callSt.setInt(1, product.getCatalog_id());
                callSt.setString(2, product.getName());
                callSt.setDouble(3, product.getPrice());
                callSt.setString(4, product.getImg_url());
                callSt.setString(5, product.getDescription());
                callSt.setInt(6, product.getStock());
                callSt.setBoolean(7, product.isStatus());
                callSt.executeUpdate();
            } else {
                // If product ID is not null, update the existing product
                PreparedStatement updateSt = conn.prepareStatement(UPDATE);
                updateSt.setInt(1, product.getCatalog_id());
                updateSt.setString(2, product.getName());
                updateSt.setDouble(3, product.getPrice());
                updateSt.setString(4, product.getImg_url());
                updateSt.setString(5, product.getDescription());
                updateSt.setInt(6, product.getStock());
                updateSt.setBoolean(7, product.isStatus());
                updateSt.setLong(8, product.getId());
                updateSt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall(DELETE);
            callSt.setInt(1, id);
            callSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }
    public List<Product> searchProduct(String keyWord) {
        Connection connection = null;
        List<Product> listSearch = new ArrayList<>();
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call searchProduct(?)}");
            callableStatement.setString(1, keyWord);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setImg_url(rs.getString("image_avatar"));
                product.setStock(rs.getInt("stock"));
                product.setCatalog_id(rs.getInt("id_catalog"));
                product.setStatus(rs.getBoolean("status"));
                listSearch.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return listSearch;
    }
    public List<Product> getPageProducts(int id) {
        List<Product> products = new ArrayList<>();
        Connection conn =null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt =conn.prepareCall("{call GetPagedProduct(?)}");
            callSt.setInt(1,id);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setImg_url(rs.getString("image_avatar"));
                product.setStock(rs.getInt("stock"));
                product.setCatalog_id(rs.getInt("id_catalog"));
                product.setStatus(rs.getBoolean("status"));
                products.add(product);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
        return products;
    }
    public List<Product> getSortProduct(int id) {
        List<Product> products = new ArrayList<>();
        Connection conn =null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt =conn.prepareCall("{call GetSort(?)}");
            callSt.setInt(1,id);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setImg_url(rs.getString("image_avatar"));
                product.setStock(rs.getInt("stock"));
                product.setCatalog_id(rs.getInt("id_catalog"));
                product.setStatus(rs.getBoolean("status"));
                products.add(product);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
        return products;
    }

}
