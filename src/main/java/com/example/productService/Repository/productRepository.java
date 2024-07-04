/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.productService.Repository;


import com.example.productService.Model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface productRepository extends MongoRepository<Product, String>{
    
    //Mode JSON
    @Query("{'nama_product' : ?0 }")
    List<Product> findByNama(String nama_product);

    
}
