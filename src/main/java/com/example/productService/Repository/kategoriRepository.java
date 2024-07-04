
package com.example.productService.Repository;


import com.example.productService.Model.Kategori;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author ASUS
 */
public interface kategoriRepository extends MongoRepository<Kategori, String>{
    //Mode JSON
    @Query("{'nama_kategori' : ?0 }")
    List<Kategori> findByNama(String nama_kategori);
}
