package ge.tbc.tbcitacademy.Models.Responses.Bookstore;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BooksResponse{

	@JsonProperty("books")
	private List<BooksItem> books;

	public void setBooks(List<BooksItem> books){
		this.books = books;
	}

	public List<BooksItem> getBooks(){
		return books;
	}
}