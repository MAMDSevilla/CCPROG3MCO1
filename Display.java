import java.util.ArrayList;
import java.util.List;

/*
  Represents a product display in the supermarket
  Displays can be different types (Table, Shelf, Fridge, Chilled) and holds products
*/

public class Display {
  private String id;
  private String type;
  private int capacity;
  private List<Product> products;

  /*
    Constructs the Display object with the given parameters.
    @param id        - unique identifier of the display
    @param type      - display type
    @param capacity  - number of products this display can hold
  */
  public Display(String id, String type, int capacity){
    this.id = id;
    this.type = type;
    this.capacity = capacity;
    this.products = new ArrayList<>();
  }

  // Adds a product to the display if capacity allows
  public boolean addProduct(Product p){
    if(products.size() < capacity){
      products.add(p);
      return true;
    }
    return false;
  }

  // Removes products by serial code
  public Product removeProduct(String Serial){
    for (Product p : products){
      if(p.getserial().equals(serial)){
        product.remove(p);
        return p;
      }
    }
    return null;
  }

  // Returns all product to display
  public List<Product> listProducts(){
    return products;
  }

  // Returns display detials into string
  public String toString(){
    return "Display " + id + " [" + type + "] (" + products.size() + "/" + capacity + " products)";
  }

  // Getters
  public String getId(){
    return id;
  }
  
  public String getType(){
    return type;
  }
  
  public int getCapacity(){
    return capacity;
  }
}












