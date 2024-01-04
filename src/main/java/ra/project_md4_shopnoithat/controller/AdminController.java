package ra.project_md4_shopnoithat.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import ra.project_md4_shopnoithat.dto.request.ProductDto;
import ra.project_md4_shopnoithat.model.Catalog;
import ra.project_md4_shopnoithat.model.Product;
import ra.project_md4_shopnoithat.model.User;
import ra.project_md4_shopnoithat.service.impl.AccountService;
import ra.project_md4_shopnoithat.service.impl.CatalogService;
import ra.project_md4_shopnoithat.service.impl.ProductService;


import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/views")
@PropertySource("classpath:update.properties")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CatalogService catalogService;
    @Value("${upload-path}")
    private String pathUpload;

    @GetMapping()
    public String listProduct(Model model) {
        List<Product> product = productService.findAll();
        List<Catalog> list = catalogService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("listCatalog", list);
        return "product";
    }


    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") int id) {
        // Xóa sản phẩm dựa trên ID
        productService.delete(id);
        return "redirect:/views";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Product product = productService.findById(id);
        List<Catalog> list = catalogService.findAll();
        model.addAttribute("listCatalog", list);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/editProduct")
    public String update(@ModelAttribute("product") ProductDto productDto) {
        File file = new File(pathUpload);
        if (!file.exists()) {
            // chưa tồn tại folder , khởi tạo 1 folder mới
            file.mkdirs();
        }
        String originalFileName = productDto.getImg_url().getOriginalFilename();
        // Lấy tên tệp gốc từ đối tượng Img_url trong ProductDto

        String fileName = (originalFileName != null && !originalFileName.isEmpty()) ? originalFileName : String.valueOf(productDto.getId());
        // Kiểm tra nếu tên tệp gốc tồn tại và không rỗng, thì sử dụng tên tệp gốc.
        // Nếu không, sử dụng giá trị id của sản phẩm để tạo tên tệp duy nhất.


        try {
            if (originalFileName != null && !originalFileName.isEmpty()) {
                FileCopyUtils.copy(productDto.getImg_url().getBytes(), new File(pathUpload + fileName));
            } else {
                // Lấy ảnh cũ nếu không có file mới được cung cấp
                Product existingProduct = productService.findById(productDto.getId()); // Gọi phương thức tương ứng để lấy sản phẩm hiện tại từ database
                if (existingProduct != null) {
                    fileName = existingProduct.getImg_url();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Product newProduct = new Product();
        newProduct.setId(productDto.getId());
        newProduct.setImg_url(fileName);
        newProduct.setName(productDto.getName());
        newProduct.setCatalog_id(productDto.getCatalog_id());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setStock(productDto.getStock());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setStatus(productDto.isStatus());
        productService.save(newProduct);
        return "redirect:/product";
    }

    @GetMapping("/addProduct")
    public String upload() {
        return "/product";
    }

    @PostMapping("/addProduct")
    public String doUpload(@ModelAttribute ProductDto productDto) {
        // upload file
        File file = new File(pathUpload);
        if (!file.exists()) {
            // chưa tồn tại folder , khởi tạo 1 folder mới
            file.mkdirs();
        }
        String fileName = productDto.getImg_url().getOriginalFilename();
        try {
            FileCopyUtils.copy(productDto.getImg_url().getBytes(), new File(pathUpload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Product newProduct = new Product();
        newProduct.setId(productDto.getId());
        newProduct.setImg_url(fileName);
        newProduct.setName(productDto.getName());
        newProduct.setCatalog_id(productDto.getCatalog_id());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setStock(productDto.getStock());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setStatus(productDto.isStatus());
        productService.save(newProduct);
        return "redirect:/product";
    }


    //    ********************  Phần quản lý tài khoản  ******************


    @GetMapping("/user")
    public ModelAndView listAcc() {
        List<User> users = accountService.findAll();
        ModelAndView modelAndView = new ModelAndView("/user", "account", users);
        return modelAndView;
    }

    @GetMapping("/unlock_acc/{id}")
    public String unlockAcc(@PathVariable("id") Integer id) {
        accountService.updateStatusAcc(id, true);
        return "redirect:/views/user";
    }

    @GetMapping("/block_acc/{id}")
    public String blockAcc(@PathVariable("id") Integer id) {
        accountService.updateStatusAcc(id, false);
        return "redirect:/views/user";
    }


    //        ------------------ Quản lý Catalog  --------------------


        @GetMapping("/catalog")
    public ModelAndView listCatalog() {
        List<Catalog> catalogs = catalogService.findAll();
        ModelAndView modelAndView = new ModelAndView("/catalog", "catalogs", catalogs);
        return modelAndView;
    }


    @GetMapping("/addCatalog")
    public ModelAndView add() {
        // Tạo ModelAndView để hiển thị form thêm mới trong view "/views/addCatalog"
        ModelAndView modelAndView = new ModelAndView("/catalog", "catalog", new Catalog());
        return modelAndView;
    }

    @PostMapping("/addCatalog")
    public String add(@ModelAttribute("catalog") Catalog catalog) {
        // Lưu thông tin công việc mới
        catalogService.save(catalog);
        // Chuyển hướng về trang danh sách công việc
        return "redirect:/views/catalog";
    }

    @GetMapping("/editCatalog/{id}")
    public ModelAndView edit_catalog(@PathVariable("id") int id) {
        // Lấy công việc cần chỉnh sửa dựa trên ID
        Catalog catalogEdit = catalogService.findById(id);
        // Tạo ModelAndView để hiển thị form chỉnh sửa trong view "/add_catalog"
        ModelAndView modelAndView = new ModelAndView("/editCatalog", "catalog", catalogEdit);
        return modelAndView;
    }

    @PostMapping("/editCatalog")
    public String update_catalog(@ModelAttribute("catalog") Catalog catalog) {
        // Lưu thông tin công việc đã cập nhật
        catalogService.save(catalog);
        // Chuyển hướng về trang danh sách công việc
        return "redirect:/views/catalog";
    }

    @GetMapping("/deleteCatalog/{id}")
    public String deleteCatalog(@PathVariable("id") int id) {
        // xóa sản phẩm dựa trên id
        catalogService.delete(id);
        return "redirect:/views/catalog";
    }

}