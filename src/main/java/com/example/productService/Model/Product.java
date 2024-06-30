/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.productService.Model;

import org.springframework.data.annotation.Id;


public class Product {
    @Id
    private String id_product;
    private String nama_product;
    private String deskripsi_product;
    private String harga_product;
    private String stock_product;

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getNama_product() {
        return nama_product;
    }

    public void setNama_product(String nama_product) {
        this.nama_product = nama_product;
    }

    public String getDeskripsi_product() {
        return deskripsi_product;
    }

    public void setDeskripsi_product(String deskripsi_product) {
        this.deskripsi_product = deskripsi_product;
    }

    public String getHarga_product() {
        return harga_product;
    }

    public void setHarga_product(String harga_product) {
        this.harga_product = harga_product;
    }

    public String getStock_product() {
        return stock_product;
    }

    public void setStock_product(String stock_product) {
        this.stock_product = stock_product;
    }
    
    
    
}
