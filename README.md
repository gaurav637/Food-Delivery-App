# FOOD DELIVERY APP(*FoodMart*)


This Spring Boot-based food delivery application offers a streamlined solution for ordering food from local restaurants. Featuring a user-friendly interface and a seamless ordering experience, our app aims to revolutionize the way you enjoy your favorite meals. With just a few clicks, users can explore a diverse range of restaurants, customize their orders, and track their delivery in real-time. Our secure payment system ensures peace of mind during transactions, while the order history feature allows users to easily reorder their favorite dishes. Whether you're craving pizza, sushi, or something in between, our app brings delicious meals straight to your doorstep, making dining in as convenient as it is enjoyable.


## ER Diagram ->

<img width="1053" alt="Screenshot 2024-04-24 at 11 40 07 PM" src="https://github.com/gaurav637/Food-Delivery-App/assets/141955844/f33d0db6-5ada-4429-8967-f0db94c123df">

## **Getting Started**
To run the Food Delivery Application locally, follow these steps:

### 1. Clone the repository: 
   ```
   https://github.com/gaurav637/Food-Delivery-App
   ```
**2.** Navigate to the project directory: cd food-delivery-app

**3.** Open the project in your IDE: Eclipse (recommended) or IntelliJ IDEA
       If you are using Eclipse, make sure the IDE opens project as Maven and recognizes the project as a Spring Boot project.
       
**4.** Configure the database connection in application.properties:
       MySQL can be used as the database for this project. The database connection can be configured in the application.properties file, with the appropriate 
       values for the following properties: (you'd better use another username not root)
       
       ```
       db.url=jdbc:mysql://[ip address of db]:[port of db]/database_name
       db.username=[username]
       db.password=[password, if any]
       ```
       
**5.** Build the project:``` mvn clean package```

**6.** Run the application:``` java -jar target/food-delivery-app.jar```

**7.** Access the application in your web browser at: http://localhost:9192

class model -> 

<img width="1184" alt="Screenshot 2024-04-26 at 10 04 00 PM" src="https://github.com/gaurav637/Food-Delivery-App/assets/141955844/b99a740c-d2bd-4c44-8c6e-64a72fcac41d">


## Contributing
Contributions are welcome! If you'd like to contribute to the project, please follow these steps:

1. Fork the repository
2. Create your feature branch: git checkout -b feature-name
3. Commit your changes: git commit -m 'Add some feature'
4. Push to the branch: git push origin feature-name
5. Submit a pull request

 
 ## Contact
For any inquiries or feedback, please contact: [LinkedIn](https://www.linkedin.com/in/gauravnegi91/)
