import JSHOP2.*;

public class Ceil implements Calculate {
  public Term call(List l)
  {

		TermNumber num = (TermNumber) l.getHead();
    
		double double_value = num.getNumber();
		
		return new TermNumber((int) Math.ceil(double_value));


  }
}
