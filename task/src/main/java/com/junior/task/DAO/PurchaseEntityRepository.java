package com.junior.task.DAO;

import com.junior.task.model.PurchaseEntity;
import org.springframework.stereotype.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PurchaseEntityRepository extends JpaRepository<PurchaseEntity, Long>{

}

