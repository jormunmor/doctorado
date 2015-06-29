package JSHOP2;

/**
 *
 * @author jorge
 */
public class Vertex {
  final private String id;
  final private String name;
	final private int number;
  
  
  public Vertex(String id, String name, int number) {
    this.id = id;
    this.name = name;
		this.number = number;
  }

  public String getId() {
    return id;

  }

  public String getName() {
    return name;

  }
  
	public int getNumber() {
    return number;

  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Vertex other = (Vertex) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return name;
  }
  
} 
