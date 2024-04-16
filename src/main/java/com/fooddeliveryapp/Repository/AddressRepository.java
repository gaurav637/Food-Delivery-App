package com.fooddeliveryapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fooddeliveryapp.Model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
