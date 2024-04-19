package com.fooddeliveryapp.ServicesImple;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fooddeliveryapp.DTO.RestaurantDto;
import com.fooddeliveryapp.Exceptions.ResourceNotFoundException;
import com.fooddeliveryapp.Model.Address;
import com.fooddeliveryapp.Model.Contact;
import com.fooddeliveryapp.Model.Restaurant;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.AddressRepository;
import com.fooddeliveryapp.Repository.ContactRepository;
import com.fooddeliveryapp.Repository.RestaurantRepository;
import com.fooddeliveryapp.Repository.userRepository;
import com.fooddeliveryapp.Request.CreateRestaurantRequest;
import com.fooddeliveryapp.Services.restaurantServices;

@Service
public class RestaurantServiceImple implements restaurantServices {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private userRepository uRepository;
	
	@Autowired
	private ContactRepository cRepository;
	
	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		Address address = addressRepository.save(req.getAddress());
		Contact	contact = cRepository.save(req.getContactInfo());	
		Restaurant rest = new Restaurant();
		rest.setOwners(req.getOwners());
		rest.setOpeningHours(req.getOpeningHours());
		rest.setName(req.getName());
		rest.setImages(req.getImages());
		rest.setDescription(req.getDescription());
		rest.setCuisineType(req.getCuisineType());
		rest.setContactInfo(contact);		
		rest.setAddress(address);
		rest.setRegistrationDate(LocalDateTime.now());		
		Restaurant newRestaurant = restaurantRepository.save(rest);	
		return newRestaurant;
	}

	@Override
	public Restaurant updateRestaurant(int id, CreateRestaurantRequest updatedRestaurant) {
		Restaurant update = restaurantRepository.findById(id).orElseThrow(()-> new  ResourceNotFoundException("Restaurant","Id",id));	
		update.setOwners(updatedRestaurant.getOwners());
		update.setOpeningHours(updatedRestaurant.getOpeningHours());
		update.setName(updatedRestaurant.getName());
		update.setImages(updatedRestaurant.getImages());
		update.setDescription(updatedRestaurant.getDescription());	
		update.setCuisineType(updatedRestaurant.getCuisineType());
		update.setContactInfo(updatedRestaurant.getContactInfo());	
		update.setAddress(updatedRestaurant.getAddress());
		update.setRegistrationDate(LocalDateTime.now());		
		Restaurant newRestaurant = restaurantRepository.save(update);
		return newRestaurant;
	}

	@Override
	public void deleteRestaurant(int id) {
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Restaurant","Id",id));
		restaurantRepository.delete(restaurant);
	}

	@Override
	public List<Restaurant> getAllRestaurant()throws Exception {
		List<Restaurant> allRestaurants = restaurantRepository.findAll();
		if(allRestaurants.size()==0) {
			throw new Exception("Restaurant is not Exist !");
		}
	    return allRestaurants;
	}

	@Override
	public List<Restaurant> searchRestaurant(String name)throws Exception {
		List<Restaurant> allRestaurants = restaurantRepository.getAllSearchRestaurants(name);
		if(allRestaurants.size()==0) {
			throw new Exception("Not Found any Restaurant with name: "+name);
		}
		return allRestaurants;
	}

	@Override
	public Restaurant getRestaurantById(int id) {
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Restaurant","Id",id));
		return restaurant;
	}

	@Override
	public Restaurant getRestaurantByUserId(int id)throws Exception {
		Restaurant restaurant = restaurantRepository.findByOwnersId(id);
		if(restaurant==null) {
			throw new Exception("Restaurant is not found with owner id :"+id);
		}
		
	   return restaurant;
	}

	@Override
	public RestaurantDto addFavoritesRestaurant(int restId,User user) {
		Restaurant restaurant = restaurantRepository.findById(restId).orElseThrow(()-> new ResourceNotFoundException("Restaurant","Id",restId));
        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setId(restaurant.getId());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        if(user.getFavorite().contains(dto)) {
        	user.getFavorite().remove(dto);      
       	}else {
       		user.getFavorite().add(dto);
       	}
		uRepository.save(user);
		return dto;
	}

	@Override
	public Restaurant updateRestaurantStatus(int id) {
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Restaurant","Id",id));
		restaurant.setOpen(!restaurant.isOpen());		
		return restaurantRepository.save(restaurant);
	}

}
