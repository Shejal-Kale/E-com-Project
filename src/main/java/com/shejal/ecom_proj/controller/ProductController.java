package com.shejal.ecom_proj.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shejal.ecom_proj.model.Product;
import com.shejal.ecom_proj.service.ProductService;

import jakarta.websocket.server.PathParam;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
		@Autowired
		private ProductService service;
	
		@GetMapping("/products")
		public ResponseEntity<List<Product>> getAllProduct(){
			return new ResponseEntity<>(service.getAllProduct(), HttpStatus.OK);
		}
		
		@GetMapping("/product/{id}")
		public ResponseEntity<Product> getProduct(@PathVariable int id) {
			Product product=service.getProduct(id);
			if (product!=null)
				return new ResponseEntity<>(product, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		@PostMapping("/product")
		public ResponseEntity<?> addProduct(@RequestPart Product product,
											@RequestPart MultipartFile imageFile){
			try {
			Product product1=service.addProduct(product,imageFile);
		//	System.out.println(product1.get);
			return new ResponseEntity<>(product1,HttpStatus.CREATED);
			}catch(Exception e){
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		}
		
		@GetMapping("/product/{id}/image")
		public ResponseEntity<byte[]> getImageById(@PathVariable int id){
			Product p=service.getProduct(id);
			byte[] imageFile=p.getImageData();
			return ResponseEntity.ok().contentType(MediaType.valueOf(p.getImageType())).body(imageFile) ;
		}
		
		@PutMapping("/product/{id}")
		public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
				@RequestPart MultipartFile imageFile) throws IOException{
			Product product1=service.updateProduct(id,product,imageFile);
			if(product1!=null) {
				return new ResponseEntity<>("Updated",HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
			}
		}
		
		@DeleteMapping("/product/{id}")
		public ResponseEntity<String> deleteProduct(@PathVariable int id){
			Product product=service.getProduct(id);
			if(product!=null) {
				service.deleteProduct(id);
				return new ResponseEntity<>("Deleted", HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
			}
		}
		
		@GetMapping("/products/search")
		public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
			System.out.println("Searching with"+keyword);
			List<Product> products=service.searchProducts(keyword);
			return new ResponseEntity<>(products,HttpStatus.OK);
		}
}

