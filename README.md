# PaytonSellersBooks
Java bookstore web application. A full e-commerce website with Spring.

##Features
* **Membership**: complete login and registration feature integrated with the server and data store
* **Customer Profile**: customers edit their profile and update the shipping address we have on file
* **Shopping Cart**: customers add products to their shopping cart and step through the checkout flow
* **Purchase History**: invoices are generated in the database during checkout. past invoices and order history
is available to the user
* **Search**: the search feature finds products by keywords in the author, title, description, etc.
* **Administration**: a complete admin area allows admins to add and edit books to the site. the whole site has a custom admin view for easily managing content
* **Image Uploads**: the admin uploads a book image to the server for total admin functionality. images are stored on the server and displayed to the user
* **Navigation**: each book is associated with a popular category of books and a specific subcategory for seemless navigation
* **Webserivces**: the complete inventory of books is available in xml

###Source File Structure
```
│   ├───src
│   │   ├───main
│   │   │   ├───java
│   │   │   │   └───com
│   │   │   │       └───paytonsellersbooks
│   │   │   │           ├───action
│   │   │   │           │       AdminAction.java
│   │   │   │           │       CustomerAction.java
│   │   │   │           │       ViewAction.java
│   │   │   │           │       
│   │   │   │           ├───controller
│   │   │   │           │       AdminController.java
│   │   │   │           │       CustomerController.java
│   │   │   │           │       NavController.java
│   │   │   │           │       ViewController.java
│   │   │   │           │       
│   │   │   │           ├───dao
│   │   │   │           │       BaseDAO.java
│   │   │   │           │       BookDAO.java
│   │   │   │           │       BookDAOImpl.java
│   │   │   │           │       CategoryDAO.java
│   │   │   │           │       CategoryDAOImpl.java
│   │   │   │           │       CustomerDAO.java
│   │   │   │           │       CustomerDAOImpl.java
│   │   │   │           │       DAO.java
│   │   │   │           │       DAOException.java
│   │   │   │           │       DAOFactory.java
│   │   │   │           │       InvoiceDAO.java
│   │   │   │           │       InvoiceDAOImpl.java
│   │   │   │           │       InvoiceDetailDAO.java
│   │   │   │           │       InvoiceDetailDAOImpl.java
│   │   │   │           │       
│   │   │   │           ├───model
│   │   │   │           │       Address.java
│   │   │   │           │       Book.java
│   │   │   │           │       Customer.java
│   │   │   │           │       Invoice.java
│   │   │   │           │       InvoiceDetail.java
│   │   │   │           │       
│   │   │   │           ├───services
│   │   │   │           │       BookResource.java
│   │   │   │           │       HelloResource.java
│   │   │   │           │       
│   │   │   │           └───utility
│   │   │   │                   CookieHelper.java

```

# Screens
![Alt text](/screens/1.jpg)
![Alt text](/screens/2.jpg)
![Alt text](/screens/6.jpg)
![Alt text](/screens/7.jpg)
![Alt text](/screens/8.jpg)
![Alt text](/screens/9.jpg)
![Alt text](/screens/10.jpg)


