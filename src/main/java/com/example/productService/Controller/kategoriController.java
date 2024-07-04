/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.productService.Controller;

import com.example.productService.Model.Kategori;
import com.example.productService.Model.Product;
import com.example.productService.Repository.kategoriRepository;
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

/**
 *
 * @author ASUS
 */
@Controller
@RequestMapping(path="/kategori")
public class kategoriController {
    @Autowired
    private kategoriRepository KR;
    
    //done
    @PostMapping("/addKategori")
    public ResponseEntity<?> addKategori(@RequestBody Kategori kat){
        try {
            kat.setId_kategori(UUID.randomUUID().toString().substring(0, 10));
            Kategori newKat = KR.save(kat);
            return ResponseHandler.generateResponse("Kategori added successfully", newKat, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseHandler.errorResponse(Collections.singletonList("Failed to add Kategori: "),HttpStatus.NOT_FOUND);
        }
    }
    
    //done
    @GetMapping("/findKategori")
    public ResponseEntity<List<Kategori>> getAllKategori() {
        List<Kategori> pro = (List<Kategori>) KR.findAll();
        return ResponseEntity.ok(pro);
    }
    
    
    //done
    @GetMapping("/getById/{id_kategori}")
    public ResponseEntity<?> getByID(@PathVariable String id_kategori) {
        Optional<Kategori> kategori = KR.findById(id_kategori);
        if (kategori.isPresent()) {
            return ResponseEntity.ok(kategori.get());
        } else {
            return ResponseHandler.errorResponse(
                    Collections.singletonList("Kategori not found"),
                    HttpStatus.NOT_FOUND);
        }
    }
    
    //done
    @PostMapping("/getByNama")
    public ResponseEntity<Object> getByNama(@RequestParam String nama_kategori) {
        List<Kategori> kategori = (List<Kategori>) KR.findByNama(nama_kategori);

        if (kategori.isEmpty()) {
            String message = "Gagal menemukan Kategori dengan nama '" + nama_kategori + "'";
            return ResponseHandler.errorResponse(Collections.singletonList("Kategori not found"),HttpStatus.NOT_FOUND);
        } else {
            String message = "Berhasil menemukan Kategori dengan nama '" + nama_kategori + "'";
            return ResponseEntity.ok(ResponseHandler.generateResponse(message, kategori, HttpStatus.OK).getBody());
        }
    }

    
    //done
    @PutMapping("/updateKategori/{id_kategori}")
    public ResponseEntity<?> updateProduct(@PathVariable String id_kategori, @RequestBody Kategori katNew) {
        Optional<Kategori> oldKategoriOptional = KR.findById(id_kategori);
        if (oldKategoriOptional.isPresent()) {
            Kategori kLama = oldKategoriOptional.get();
            kLama.setNama_kategori(katNew.getNama_kategori());
            KR.save(kLama);
            return ResponseHandler.successResponse("Kategori berhasil diupdate", HttpStatus.OK);
        } else {
            return ResponseHandler.errorResponse(Collections.singletonList("Kategori tidak ditemukan"), HttpStatus.NOT_FOUND);
        }
    }

    
    //done
    @DeleteMapping("/deleteKategori")
    public ResponseEntity<?> deleteProduct(@RequestBody Kategori kat) {
        Optional<Kategori> katOptional = KR.findById(kat.getId_kategori());
        if (katOptional.isPresent()) {
            KR.deleteById(kat.getId_kategori());
            return ResponseHandler.successResponse("Kategori berhasil dihapus", HttpStatus.OK);
        } else {
            return ResponseHandler.errorResponse(Collections.singletonList("Kategori tidak ditemukan"), HttpStatus.NOT_FOUND);
        }
    }
    
}
