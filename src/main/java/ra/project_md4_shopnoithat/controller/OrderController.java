package ra.project_md4_shopnoithat.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ra.project_md4_shopnoithat.dto.request.FormOrderDto;
import ra.project_md4_shopnoithat.model.*;
import ra.project_md4_shopnoithat.service.impl.BillingService;
import ra.project_md4_shopnoithat.service.impl.OrderService;
import ra.project_md4_shopnoithat.service.impl.ProductService;


import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {
    @Autowired
    private BillingService billingService;
    @Autowired
    private OrderService orderService;
//    @Autowired
//    private HistoryService historyService;
    @Autowired
    private ProductService productService;

    @PostMapping("/create_order")
    public String createOrder(HttpSession session, @ModelAttribute("order_form") FormOrderDto formOrderDto){
        User userlogin = (User) session.getAttribute("userlogin");
        List<CartItem> carts = (List<CartItem>) session.getAttribute("carts");

        Billing billing = new Billing();
        billing.setUser_id(userlogin.getId());
        billing.setReceiver(formOrderDto.getReceiver());
        billing.setZipcode(formOrderDto.getZipcode());
        billing.setAddress(formOrderDto.getAddress());
        billing.setPhone(formOrderDto.getPhone());
        billing.setOrtherInfo(formOrderDto.getOrtherInfo());
        billingService.save(billing);

        Order order = new Order();
        order.setUserId(userlogin.getId());
        orderService.insertOrder(order,carts);

        for (CartItem cart : carts) {
            Product product = productService.findById(cart.getProduct().getId());
            product.setStock(product.getStock() - cart.getQuantity());
            productService.save(product);
        }
        session.removeAttribute("carts");
        session.removeAttribute("total");
        return "redirect:/";
    }

//    @GetMapping("/order")
//    public String getOrder(Model model) {
//        model.addAttribute("orders",historyService.findAll());
//        return "admin/order";
//    }

    @GetMapping("/confirm/{id}")
    public String lock(@PathVariable("id") int id) {
        orderService.toggleOrder(id);
        return "redirect:/order";
    }

}
