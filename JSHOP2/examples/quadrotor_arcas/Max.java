import JSHOP2.*;

public class Max implements Calculate {
  public Term call(List l)
  {

	TermNumber max = (TermNumber) l.getHead();
	l = l.getRest();
	TermNumber candidate;

	while (l != null)
	{
		candidate = (TermNumber) l.getHead();
		if( candidate.getNumber() > max.getNumber() ){
			max = candidate;
		}
		l = l.getRest();
	}

	return max;


  }
}
