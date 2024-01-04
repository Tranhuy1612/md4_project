package ra.project_md4_shopnoithat.dto.request;


import org.springframework.validation.Errors;
import ra.project_md4_shopnoithat.service.impl.UserService;

import javax.validation.constraints.NotNull;

public class FormRegisterDto {
    @NotNull(message = "UserName không được để trống")
    private String username;
    @NotNull(message = "Email không được để trống")

    private String email;
    @NotNull(message = "PassWord không được để trống")

    private String password;
    @NotNull(message = "confirmPassword không được để trống")

    private String confirmPassword;

    public FormRegisterDto() {
    }

    public FormRegisterDto(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void checkValidateRegister(Errors errors, UserService userService) {

        // kiểm tra trường username có để trống hay không
        if (this.username.trim().equals("")) {
            errors.rejectValue("username", "username.empty");
        } else if (this.username.length() < 6) {
            errors.rejectValue("password", "password.regex");
        } else if (!userService.checkUsername(this.username)) {
            errors.rejectValue("username", "username.duplicate");
        } else if (this.email == null || this.email.trim().isEmpty()) {
            errors.rejectValue("email", "email.empty");
        } else if (!userService.checkEmail(this.email)) {
            errors.rejectValue("email", "email.duplicate");
        } else if (this.password == null) {
            errors.rejectValue("password", "password.empty");
        }
    }
}
