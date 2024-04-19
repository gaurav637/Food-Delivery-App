package com.fooddeliveryapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddeliveryapp.Model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
