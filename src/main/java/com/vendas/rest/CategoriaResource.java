package com.vendas.rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vendas.model.Categoria;

@Path("/categoria")
@Produces(MediaType.APPLICATION_JSON) //envia um jason
@Consumes(MediaType.APPLICATION_JSON) //recebe um jason em cada objeto
public class CategoriaResource {

    @GET
    public List<Categoria> list() {
      return Categoria.listAll();   
    }
    
    @Transactional
    @POST
    public void insert(Categoria categoria) {
         categoria.persistAndFlush(); //inserir categoria no banco
      
    }
    
    @Transactional
    @PUT
    public void update(Categoria categoria) {
		Categoria localizada = Categoria.findById(categoria.getId());
    	if(localizada == null) {
    		throw new WebApplicationException(
    				"Categoria não localizada", Response.Status.NOT_FOUND);
    	}
    	localizada.setDescricao(categoria.getDescricao());
		localizada.persistAndFlush();
      
    }
    
    @Transactional
    @Path("{id}")
    @DELETE
    public void delete(@PathParam("id") long id) {
		try {
	    	Categoria localizada = Categoria.findById(id);
	    	if(localizada == null) {
	    		throw new WebApplicationException(
	    				"Categoria não localizada", Response.Status.NOT_FOUND);
	    	}
	    	localizada.delete();
		}catch (ConstraintViolationException e) {//ConstraintViolationException 
			if(e.getMessage().contains("PRODUTO FOREIGN KEY(CATEGORIA_ID)")) {
				throw new WebApplicationException(
						"Não é possível excluir a Categoria pois existem Produtos", Response.Status.CONFLICT);
				
			}
		}catch (RuntimeException e) {
			Logger.getLogger("categoria").log(Level.ALL, e.getMessage());
			throw new WebApplicationException(
					"Erro inesperado ao tentar acessar o BD",
			Response.Status.INTERNAL_SERVER_ERROR);
		}
      
    }
}