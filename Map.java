import java.util.ArrayList;
import java.util.List;

/*
  Represents the floor map
  Handles all the display placed within a grid
*/
public class Map{
  private int rows;
  private int columns;
  private List<Display> displays;

  // Creates the map
  public Map(int rows, int cols){
    this.rows = rows;
    this.cols = cols;
    this.display = new ArrayList<>();
  }

  // Adds display to the map
  public void addDisplay(Display d){
    displays.add(d)
  }

  // Finds display by its ID
  public Display findDisplayById(String id){
    for (Display d : displays){
      if (d.getId().equals(id)){
        return d;
      }
    }
    return null;
  }

  // Returns all display
  public List<Display> getDisplays(){
    return displays;
  }

  // Shows the summary of all displays on the map
  public void showMap(){
    System.out.println("Supermarket Map("+ rows + "x" + cols + ")");
    for (Display d : displays){
      System.out.println("-" + d);
    }
  }

}




