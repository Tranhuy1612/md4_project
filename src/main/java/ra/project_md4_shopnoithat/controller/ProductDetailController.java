package ra.project_md4_shopnoithat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.project_md4_shopnoithat.model.Product;
import ra.project_md4_shopnoithat.service.impl.ProductService;

@Controller
@RequestMapping("/singleproduct")
public class ProductDetailController {
    @Autowired
    private ProductService productService;
    @GetMapping("/eye/{id}")
    public String eye(@PathVariable("id") int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "singleproduct";
    }
}
