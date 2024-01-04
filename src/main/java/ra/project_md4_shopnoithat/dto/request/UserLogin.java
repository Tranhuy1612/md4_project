package ra.project_md4_shopnoithat.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLogin {
    private  int id;
    @NotEmpty(message = "UserName không được để trống")
    private String username;
    @Size(min = 6 , max = 50,message = "Mật khẩu tối thiểu phải 6 kí tự , tối đa 50 kí tự")
    private String password;
    private int role;
    private  int cartId;

    public UserLogin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
