/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.productService.Controller;



import com.example.productService.Model.Product;
import com.example.productService.Repository.productRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody Product pro){
        if (pro.getId_product() == null || pro.getId_product().isEmpty() || pro.getNama_product() == null || pro.getNama_product().isEmpty() || pro.getDeskripsi_product() == null || pro.getDeskripsi_product().isEmpty() || pro.getHarga_product() == null || pro.getHarga_product().isEmpty() || pro.getStock_product() == null || pro.getStock_product().isEmpty()) {
            return ResponseEntity.badRequest().body("Gagal menambahkan produk. Harap lengkapi semua atribut.");
        }else{
            PR.insert(pro);
            return ResponseEntity.ok("Product added successfully");            
        }
    }
    
    @GetMapping("/getById/{id_product}")
    public @ResponseBody ResponseEntity<Product> getByID(@PathVariable String id_product) {
        Optional<Product> product = PR.findById(id_product);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    @PostMapping("/getByNama")
    public @ResponseBody List<Product> getByNama(@RequestParam String nama_product) {
        return PR.findByNama(nama_product);
    }
    
    @GetMapping("/selectProduct")
    public @ResponseBody Iterable<Product> getAll(){
        return PR.findAll();
    }
    
    @PutMapping("/updateProduct/{id_product}")
    public ResponseEntity<String> updateProduct(@PathVariable String id_product, @RequestBody Product proNew) {
        if(PR.existsById(id_product)){
//            user uLama=ur.findByNIK(nik).getFirst();
            Optional<Product> oldProduct = PR.findById(id_product);
            Product pLama = oldProduct.get();
            pLama.setId_product(id_product);
            pLama.setNama_product(proNew.getNama_product());
            pLama.setDeskripsi_product(proNew.getDeskripsi_product());
            pLama.setHarga_product(proNew.getHarga_product());
            pLama.setStock_product(proNew.getStock_product());
            PR.save(pLama);
            return ResponseEntity.ok("Produk berhasil diupdate");
        }
        else{
            return ResponseEntity.ok("Produk tidak berhasil diupdate");
        }
    }
    
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<String> deleteProduct(@RequestBody Product pro){
        // Memeriksa apakah produk ada sebelum menghapus
        if(PR.existsById(pro.getId_product())){
            PR.deleteById(pro.getId_product());
            return ResponseEntity.ok("Produk berhasil dihapus");
        } else {
            return ResponseEntity.ok("Produk tidak ditemukan");
        }
    }
    
}
