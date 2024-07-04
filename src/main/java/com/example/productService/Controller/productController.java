package com.example.productService.Controller;



import com.example.productService.Model.Product;
import com.example.productService.Repository.productRepository;
import com.example.productService.responses.ResponseHandler;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/product")
public class productController {
    @Autowired
    private productRepository PR;
    
    //done
    @PostMapping("/addProduct")
    public ResponseEntity<?> addUser(@RequestBody Product pro) {
        try {
            pro.setId_product(UUID.randomUUID().toString().substring(0, 10));
            Product newPro = PR.save(pro);
            return ResponseHandler.generateResponse("Product added successfully", newPro, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseHandler.errorResponse(Collections.singletonList("Failed to add Product: "),HttpStatus.NOT_FOUND);
        }
    }
    
    //done
    @GetMapping("/selectProduct")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> pro = (List<Product>) PR.findAll();
        return ResponseEntity.ok(pro);
    }
    
    
    //done
    @GetMapping("/getById/{id_product}")
    public ResponseEntity<?> getByID(@PathVariable String id_product) {
        Optional<Product> product = PR.findById(id_product);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseHandler.errorResponse(
                    Collections.singletonList("Product not found"),
                    HttpStatus.NOT_FOUND);
        }
    }
    
    //done
    @PostMapping("/getByNama")
    public ResponseEntity<Object> getByNama(@RequestParam String nama_product) {
        List<Product> products = (List<Product>) PR.findByNama(nama_product);

        if (products.isEmpty()) {
            String message = "Gagal menemukan produk dengan nama '" + nama_product + "'";
            return ResponseHandler.errorResponse(Collections.singletonList("Product not found"),HttpStatus.NOT_FOUND);
        } else {
            String message = "Berhasil menemukan produk dengan nama '" + nama_product + "'";
            return ResponseEntity.ok(ResponseHandler.generateResponse(message, products, HttpStatus.OK).getBody());
        }
    }

    
    //done
    @PutMapping("/updateProduct/{id_product}")
    public ResponseEntity<?> updateProduct(@PathVariable String id_product, @RequestBody Product proNew) {
        Optional<Product> oldProductOptional = PR.findById(id_product);
        if (oldProductOptional.isPresent()) {
            Product pLama = oldProductOptional.get();
            pLama.setNama_product(proNew.getNama_product());
            pLama.setDeskripsi_product(proNew.getDeskripsi_product());
            pLama.setHarga_product(proNew.getHarga_product());
            pLama.setStock_product(proNew.getStock_product());
            PR.save(pLama);
            return ResponseHandler.successResponse("Produk berhasil diupdate", HttpStatus.OK);
        } else {
            return ResponseHandler.errorResponse(Collections.singletonList("Produk tidak ditemukan"), HttpStatus.NOT_FOUND);
        }
    }

    
    //done
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestBody Product pro) {
        Optional<Product> proOptional = PR.findById(pro.getId_product());
        if (proOptional.isPresent()) {
            PR.deleteById(pro.getId_product());
            return ResponseHandler.successResponse("Produk berhasil dihapus", HttpStatus.OK);
        } else {
            return ResponseHandler.errorResponse(Collections.singletonList("Produk tidak ditemukan"), HttpStatus.NOT_FOUND);
        }
    }
}