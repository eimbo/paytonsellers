package com.paytonsellersbooks.services;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.paytonsellersbooks.action.ViewAction;
import com.paytonsellersbooks.model.Book;

@Path("book")
public class BookResource {
    @GET
    @Path("/jersey")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello Jersey";
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Book getBook(@PathParam("id") int id){
    	ViewAction viewAction = new ViewAction();
    	Book b = viewAction.getBookById(id);
    	return b;
    }
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<Book> getBook(@PathParam("category") String cat){
    	ViewAction viewAction = new ViewAction();
    	ArrayList<Book> list = viewAction.getAllBooks();
    	return list;
    }
    
}