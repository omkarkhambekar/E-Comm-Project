package com.omkar.E_Comm_Proj.repository;

import com.omkar.E_Comm_Proj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

}
