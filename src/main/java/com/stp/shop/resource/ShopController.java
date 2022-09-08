package com.stp.shop.resource;

import com.stp.shop.domain.AuthRequest;
import com.stp.shop.domain.Brand;
import com.stp.shop.domain.Product;
import com.stp.shop.service.ShopService;
import com.stp.shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
//@RequestMapping("api/v1/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return shopService.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product){
        return shopService.addProduct(product);
    }

    @PostMapping("/add")
    public void addProducts(@RequestBody Product product){
         shopService.addProducts(product);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return shopService.getAllProducts();
    }

    @GetMapping("/soldProducts")
    public List<Product> getAllSoldProducts(){
        return shopService.getAllSoldProducts();
    }

    @PutMapping("/products/{id}")
    public Product getAllProducts(@PathVariable long id, @RequestBody Product product){
        return shopService.getUpdateProductById(id,product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable long id){
        shopService.deleteProductById(id);
    }

}
