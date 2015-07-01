import java.util.LinkedList;
import JSHOP2.*;

public class problem
{
	private static String[] defineConstants()
	{
		String[] problemConstants = new String[15];

		problemConstants[0] = "uav1";
		problemConstants[1] = "uav2";
		problemConstants[2] = "uav3";
		problemConstants[3] = "uav4";
		problemConstants[4] = "box001-lib";
		problemConstants[5] = "box002-lib";
		problemConstants[6] = "box003-lib";
		problemConstants[7] = "box004-lib";
		problemConstants[8] = "box005-lib";
		problemConstants[9] = "box006-lib";
		problemConstants[10] = "box007-lib";
		problemConstants[11] = "box008-lib";
		problemConstants[12] = "box009-lib";
		problemConstants[13] = "box010-lib";
		problemConstants[14] = "box011-lib";

		return problemConstants;
	}

	private static void createState0(State s)	{
		s.add(new Predicate(0, 0, new TermList(TermConstant.getConstant(20), TermList.NIL)));
		s.add(new Predicate(0, 0, new TermList(TermConstant.getConstant(21), TermList.NIL)));
		s.add(new Predicate(0, 0, new TermList(TermConstant.getConstant(22), TermList.NIL)));
		s.add(new Predicate(0, 0, new TermList(TermConstant.getConstant(23), TermList.NIL)));
		s.add(new Predicate(11, 0, new TermList(TermConstant.getConstant(24), TermList.NIL)));
		s.add(new Predicate(11, 0, new TermList(TermConstant.getConstant(25), TermList.NIL)));
		s.add(new Predicate(11, 0, new TermList(TermConstant.getConstant(26), TermList.NIL)));
		s.add(new Predicate(11, 0, new TermList(TermConstant.getConstant(27), TermList.NIL)));
		s.add(new Predicate(11, 0, new TermList(TermConstant.getConstant(28), TermList.NIL)));
		s.add(new Predicate(11, 0, new TermList(TermConstant.getConstant(29), TermList.NIL)));
		s.add(new Predicate(11, 0, new TermList(TermConstant.getConstant(30), TermList.NIL)));
		s.add(new Predicate(11, 0, new TermList(TermConstant.getConstant(31), TermList.NIL)));
		s.add(new Predicate(11, 0, new TermList(TermConstant.getConstant(32), TermList.NIL)));
		s.add(new Predicate(11, 0, new TermList(TermConstant.getConstant(33), TermList.NIL)));
		s.add(new Predicate(11, 0, new TermList(TermConstant.getConstant(34), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(1.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(2.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(3.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(4.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(5.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(6.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(7.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(8.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(9.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(10.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(11.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(12.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(13.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(14.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(15.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(16.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(17.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(18.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(19.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(20.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(21.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(22.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(23.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(24.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(25.0), TermList.NIL)));
		s.add(new Predicate(6, 0, new TermList(new TermNumber(26.0), TermList.NIL)));
		s.add(new Predicate(19, 0, new TermList(TermConstant.getConstant(24), new TermList(new TermNumber(2.0), TermList.NIL))));
		s.add(new Predicate(19, 0, new TermList(TermConstant.getConstant(25), new TermList(new TermNumber(4.0), TermList.NIL))));
		s.add(new Predicate(19, 0, new TermList(TermConstant.getConstant(26), new TermList(new TermNumber(6.0), TermList.NIL))));
		s.add(new Predicate(19, 0, new TermList(TermConstant.getConstant(27), new TermList(new TermNumber(8.0), TermList.NIL))));
		s.add(new Predicate(19, 0, new TermList(TermConstant.getConstant(28), new TermList(new TermNumber(10.0), TermList.NIL))));
		s.add(new Predicate(19, 0, new TermList(TermConstant.getConstant(29), new TermList(new TermNumber(12.0), TermList.NIL))));
		s.add(new Predicate(19, 0, new TermList(TermConstant.getConstant(30), new TermList(new TermNumber(14.0), TermList.NIL))));
		s.add(new Predicate(19, 0, new TermList(TermConstant.getConstant(31), new TermList(new TermNumber(16.0), TermList.NIL))));
		s.add(new Predicate(19, 0, new TermList(TermConstant.getConstant(32), new TermList(new TermNumber(18.0), TermList.NIL))));
		s.add(new Predicate(19, 0, new TermList(TermConstant.getConstant(33), new TermList(new TermNumber(20.0), TermList.NIL))));
		s.add(new Predicate(19, 0, new TermList(TermConstant.getConstant(34), new TermList(new TermNumber(22.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(24), new TermList(new TermNumber(1.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(25), new TermList(new TermNumber(3.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(26), new TermList(new TermNumber(5.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(27), new TermList(new TermNumber(7.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(28), new TermList(new TermNumber(9.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(29), new TermList(new TermNumber(11.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(30), new TermList(new TermNumber(13.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(31), new TermList(new TermNumber(15.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(32), new TermList(new TermNumber(17.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(33), new TermList(new TermNumber(19.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(34), new TermList(new TermNumber(21.0), TermList.NIL))));
		s.add(new Predicate(14, 0, new TermList(TermConstant.getConstant(24), new TermList(TermList.NIL, TermList.NIL))));
		s.add(new Predicate(14, 0, new TermList(TermConstant.getConstant(25), new TermList(TermList.NIL, TermList.NIL))));
		s.add(new Predicate(14, 0, new TermList(TermConstant.getConstant(26), new TermList(new TermList(TermConstant.getConstant(31), new TermList(TermConstant.getConstant(32), new TermList(TermConstant.getConstant(33), new TermList(TermConstant.getConstant(34), TermList.NIL)))), TermList.NIL))));
		s.add(new Predicate(14, 0, new TermList(TermConstant.getConstant(27), new TermList(new TermList(TermConstant.getConstant(34), new TermList(TermConstant.getConstant(33), TermList.NIL)), TermList.NIL))));
		s.add(new Predicate(14, 0, new TermList(TermConstant.getConstant(28), new TermList(new TermList(TermConstant.getConstant(34), new TermList(TermConstant.getConstant(33), TermList.NIL)), TermList.NIL))));
		s.add(new Predicate(14, 0, new TermList(TermConstant.getConstant(29), new TermList(new TermList(TermConstant.getConstant(31), new TermList(TermConstant.getConstant(32), TermList.NIL)), TermList.NIL))));
		s.add(new Predicate(14, 0, new TermList(TermConstant.getConstant(30), new TermList(new TermList(TermConstant.getConstant(31), new TermList(TermConstant.getConstant(32), TermList.NIL)), TermList.NIL))));
		s.add(new Predicate(14, 0, new TermList(TermConstant.getConstant(31), new TermList(new TermList(TermConstant.getConstant(24), TermList.NIL), TermList.NIL))));
		s.add(new Predicate(14, 0, new TermList(TermConstant.getConstant(32), new TermList(new TermList(TermConstant.getConstant(24), TermList.NIL), TermList.NIL))));
		s.add(new Predicate(14, 0, new TermList(TermConstant.getConstant(33), new TermList(new TermList(TermConstant.getConstant(25), TermList.NIL), TermList.NIL))));
		s.add(new Predicate(14, 0, new TermList(TermConstant.getConstant(34), new TermList(new TermList(TermConstant.getConstant(25), TermList.NIL), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(25), new TermList(TermConstant.getConstant(20), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(26), new TermList(TermConstant.getConstant(20), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(34), new TermList(TermConstant.getConstant(20), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(25), new TermList(TermConstant.getConstant(21), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(27), new TermList(TermConstant.getConstant(21), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(30), new TermList(TermConstant.getConstant(21), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(31), new TermList(TermConstant.getConstant(21), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(24), new TermList(TermConstant.getConstant(22), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(26), new TermList(TermConstant.getConstant(22), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(28), new TermList(TermConstant.getConstant(22), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(32), new TermList(TermConstant.getConstant(22), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(33), new TermList(TermConstant.getConstant(22), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(24), new TermList(TermConstant.getConstant(23), TermList.NIL))));
		s.add(new Predicate(10, 0, new TermList(TermConstant.getConstant(29), new TermList(TermConstant.getConstant(23), TermList.NIL))));
		s.add(new Predicate(2, 0, new TermList(TermConstant.getConstant(20), new TermList(new TermNumber(1200.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(20), new TermList(new TermNumber(23.0), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(20), TermList.NIL)));
		s.add(new Predicate(2, 0, new TermList(TermConstant.getConstant(21), new TermList(new TermNumber(1200.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(21), new TermList(new TermNumber(24.0), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(21), TermList.NIL)));
		s.add(new Predicate(2, 0, new TermList(TermConstant.getConstant(22), new TermList(new TermNumber(1200.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(22), new TermList(new TermNumber(25.0), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(22), TermList.NIL)));
		s.add(new Predicate(2, 0, new TermList(TermConstant.getConstant(23), new TermList(new TermNumber(1200.0), TermList.NIL))));
		s.add(new Predicate(7, 0, new TermList(TermConstant.getConstant(23), new TermList(new TermNumber(26.0), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(23), TermList.NIL)));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(20), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(20), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(2), new TermList(TermConstant.getConstant(20), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(2), new TermList(TermConstant.getConstant(20), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(21), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(21), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(2), new TermList(TermConstant.getConstant(21), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(2), new TermList(TermConstant.getConstant(21), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(22), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(22), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(2), new TermList(TermConstant.getConstant(22), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(2), new TermList(TermConstant.getConstant(22), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(23), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(23), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(2), new TermList(TermConstant.getConstant(23), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(2), new TermList(TermConstant.getConstant(23), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(24), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(24), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(25), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(25), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(26), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(26), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(27), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(27), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(28), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(28), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(29), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(29), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(30), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(30), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(31), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(31), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(32), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(32), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(33), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(33), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(4, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(34), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(3, 0, new TermList(TermConstant.getConstant(7), new TermList(TermConstant.getConstant(34), new TermList(new TermNumber(0.0), TermList.NIL)))));
		s.add(new Predicate(16, 0, new TermList(new TermNumber(11.0), TermList.NIL)));
	}

	public static LinkedList<Plan> getPlans()
	{
		LinkedList<Plan> returnedPlans = new LinkedList<Plan>();
		TermConstant.initialize(35);

		Domain d = new quadrotor();

		d.setProblemConstants(defineConstants());

		State s = new State(20, d.getAxioms());

		JSHOP2.initialize(d, s);

		TaskList tl;
		SolverThread thread;

		createState0(s);

		tl = new TaskList(1, true);
		tl.subtasks[0] = new TaskList(new TaskAtom(new Predicate(2, 0, new TermList(TermConstant.getConstant(18), TermList.NIL)), false, false));

		thread = new SolverThread(tl, 1);
		thread.start();

		try {
			while (thread.isAlive())
				Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		returnedPlans.addAll( thread.getPlans() );

		return returnedPlans;
	}

	public static LinkedList<Predicate> getFirstPlanOps() {
		return getPlans().getFirst().getOps();
	}
}