package ra.project_md4_shopnoithat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ra.project_md4_shopnoithat.dto.request.FormLoginDto;
import ra.project_md4_shopnoithat.dto.request.FormRegisterDto;

import ra.project_md4_shopnoithat.model.Product;
import ra.project_md4_shopnoithat.model.User;
import ra.project_md4_shopnoithat.service.impl.CatalogService;
import ra.project_md4_shopnoithat.service.impl.ProductService;
import ra.project_md4_shopnoithat.service.impl.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/"})
public class HomeController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/products")
    public ModelAndView displayProduct() {
        List<Product> product = productService.findAll();
        ModelAndView modelAndView = new ModelAndView("products", "products", product);
        return modelAndView;
    }
    @PostMapping("/search")
    public ModelAndView getSearchName(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("catalog", catalogService.findAll());
        return new ModelAndView("product", "product", productService.searchProduct(keyword));
    }
    @GetMapping("/pageProduct")
    public String pageProduct(@RequestParam("page") int id, Model model) {
        model.addAttribute("catalog", catalogService.findAll());
        model.addAttribute("product", productService.getPageProducts(id));
        return "/product";
    }

    @GetMapping("/sort")
    public String sortProduct(@RequestParam("sort") int id, Model model ){
        model.addAttribute("product",productService.getSortProduct(id));
        return "/products";
    }
    @GetMapping({"/index", "/", "/h"})
    public String index() {
        return "/index";
    }

    @GetMapping("/singleproduct")
    public String singleproduct() {
        return "/singleproduct";
    }

    @GetMapping("/store")
    public String store() {
        return "/store";
    }

    //    ADmin
    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/product")
    public String product(Model model,@RequestParam(defaultValue = "1") int page) {
        List<Product> product = productService.getPageProducts(page);
        model.addAttribute("product", product);
        return "/product";
    }

    //    ------------ order ------------
    @GetMapping("/order")
    public String order() {
        return "/order";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    //    Add sản phẩm mới
    @GetMapping("/newProduct")
    public ModelAndView getProductList() {
        return new ModelAndView("newProduct", "product", new Product());
    }

    //------------Đăng kí Đăng nhập---------------
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login", "login_form", new FormLoginDto());
    }

    @PostMapping("/handle-login")
    public String handleLogin(HttpSession session, @ModelAttribute("login_form") FormLoginDto formLoginDto, BindingResult errors, Model model) {
        // checkk validate
        formLoginDto.checkValidate(errors,userService);
        // tao mois user
        User user = userService.login(formLoginDto);

        if (user == null) {
            model.addAttribute("check", "tài khoản mật khât không đúng");
            return "redirect:/login";
        }
        if (user.isStatus()== false) {
            model.addAttribute("checkStatus", "tài khoản của bạn bị khóa");
            return "redirect:/login";
        }else {
            session.setAttribute("userlogin", user);
            if (user.getRoleId() == 1) {
                System.out.println("Welcome to the admin page");
                return "redirect:/views";
            }
            session.setAttribute("store", new ArrayList<>());
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userlogin");
        session.setAttribute("cart", null);
        return "redirect:/";
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register", "register_form", new FormRegisterDto());
    }

    @PostMapping("/handle-register")
    public String handleRegister(HttpSession session, @ModelAttribute("register") FormRegisterDto formRegisterDto,BindingResult errors, Model model) {
        // checkk validate
        formRegisterDto.checkValidateRegister(errors, userService);
        if (errors.hasErrors()) {
            return "redirect:/register";
        }
        userService.save(formRegisterDto);
        return "redirect:/login";
    }
    //   ---------  catalog------------

    @GetMapping("/catalog")
    public String category() {
        return "/catalog";
    }

    @GetMapping("/addCatalog")
    public String addCatalog() {
        return "/addCatalog";
    }

    @GetMapping("/editCatalog")
    public String editCategory() {
        return "/editCatalog";
    }

}
