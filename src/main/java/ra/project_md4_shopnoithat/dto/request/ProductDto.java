package ra.project_md4_shopnoithat.dto.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductDto {
    private int id;
    @NotNull(message = "Tên sản phẩm không được để trống")
    private String name;
    private double price;
    private String description;
    private MultipartFile img_url;
    private int stock;
    @NotNull(message = "Danh mục không được để trống ")
    private int catalog_id;
    private boolean status = true;

    public ProductDto() {
    }

    public ProductDto(int id, String name, double price, String description, MultipartFile img_url, int stock, int catalog_id, boolean status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.img_url = img_url;
        this.stock = stock;
        this.catalog_id = catalog_id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImg_url() {
        return img_url;
    }

    public void setImg_url(MultipartFile img_url) {
        this.img_url = img_url;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(int catalog_id) {
        this.catalog_id = catalog_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
