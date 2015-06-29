import JSHOP2.*;

class Precondition0 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition0(Term[] unifier)
	{
		p = new Precondition[8];
		p[1] = new PreconditionAtomic(new Predicate(0, 7, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(1, 7, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(2, 7, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[4] = new PreconditionAtomic(new Predicate(3, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL)))), unifier);
		p[5] = new PreconditionAtomic(new Predicate(4, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL)))), unifier);
		p[6] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL)), StdLib.plus, "StdLib.plus"), unifier, 6);
		p[7] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(2), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[8][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[7] == null)
		{
			boolean b6changed = false;
			while (b[6] == null)
			{
				boolean b5changed = false;
				while (b[5] == null)
				{
					boolean b4changed = false;
					while (b[4] == null)
					{
						boolean b3changed = false;
						while (b[3] == null)
						{
							boolean b2changed = false;
							while (b[2] == null)
							{
								boolean b1changed = false;
								while (b[1] == null)
								{
									b[1] = p[1].nextBinding();
									if (b[1] == null)
										return null;
									b1changed = true;
								}
								if ( b1changed ) {
									p[2].reset();
									p[2].bind(Term.merge(b, 2));
								}
								b[2] = p[2].nextBinding();
								if (b[2] == null) b[1] = null;
								b2changed = true;
							}
							if ( b2changed ) {
								p[3].reset();
								p[3].bind(Term.merge(b, 3));
							}
							b[3] = p[3].nextBinding();
							if (b[3] == null) b[2] = null;
							b3changed = true;
						}
						if ( b3changed ) {
							p[4].reset();
							p[4].bind(Term.merge(b, 4));
						}
						b[4] = p[4].nextBinding();
						if (b[4] == null) b[3] = null;
						b4changed = true;
					}
					if ( b4changed ) {
						p[5].reset();
						p[5].bind(Term.merge(b, 5));
					}
					b[5] = p[5].nextBinding();
					if (b[5] == null) b[4] = null;
					b5changed = true;
				}
				if ( b5changed ) {
					p[6].reset();
					p[6].bind(Term.merge(b, 6));
				}
				b[6] = p[6].nextBinding();
				if (b[6] == null) b[5] = null;
				b6changed = true;
			}
			if ( b6changed ) {
				p[7].reset();
				p[7].bind(Term.merge(b, 7));
			}
			b[7] = p[7].nextBinding();
			if (b[7] == null) b[6] = null;
		}

		Term[] retVal = Term.merge(b, 8);
		b[7] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
	}
}

class Operator0 extends Operator
{
	public Operator0()
	{
		super(new Predicate(0, 7, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL)))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[4];
		delIn[0] = new DelAddAtomic(new Predicate(1, 7, new TermList(TermVariable.getVariable(0), TermList.NIL)));
		delIn[1] = new DelAddAtomic(new Predicate(2, 7, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL))));
		delIn[2] = new DelAddAtomic(new Predicate(3, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL)))));
		delIn[3] = new DelAddAtomic(new Predicate(4, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL)))));

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[4];
		addIn[0] = new DelAddAtomic(new Predicate(5, 7, new TermList(TermVariable.getVariable(0), TermList.NIL)));
		addIn[1] = new DelAddAtomic(new Predicate(2, 7, new TermList(TermVariable.getVariable(0), new TermList(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(2), TermList.NIL)), StdLib.minus, "StdLib.minus"), TermList.NIL))));
		addIn[2] = new DelAddAtomic(new Predicate(3, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL)))));
		addIn[3] = new DelAddAtomic(new Predicate(4, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL)))));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition0(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition1 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition1(Term[] unifier)
	{
		p = new Precondition[8];
		p[1] = new PreconditionAtomic(new Predicate(0, 7, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(5, 7, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(2, 7, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[4] = new PreconditionAtomic(new Predicate(3, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL)))), unifier);
		p[5] = new PreconditionAtomic(new Predicate(4, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL)))), unifier);
		p[6] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL)), StdLib.plus, "StdLib.plus"), unifier, 6);
		p[7] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(2), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[8][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[7] == null)
		{
			boolean b6changed = false;
			while (b[6] == null)
			{
				boolean b5changed = false;
				while (b[5] == null)
				{
					boolean b4changed = false;
					while (b[4] == null)
					{
						boolean b3changed = false;
						while (b[3] == null)
						{
							boolean b2changed = false;
							while (b[2] == null)
							{
								boolean b1changed = false;
								while (b[1] == null)
								{
									b[1] = p[1].nextBinding();
									if (b[1] == null)
										return null;
									b1changed = true;
								}
								if ( b1changed ) {
									p[2].reset();
									p[2].bind(Term.merge(b, 2));
								}
								b[2] = p[2].nextBinding();
								if (b[2] == null) b[1] = null;
								b2changed = true;
							}
							if ( b2changed ) {
								p[3].reset();
								p[3].bind(Term.merge(b, 3));
							}
							b[3] = p[3].nextBinding();
							if (b[3] == null) b[2] = null;
							b3changed = true;
						}
						if ( b3changed ) {
							p[4].reset();
							p[4].bind(Term.merge(b, 4));
						}
						b[4] = p[4].nextBinding();
						if (b[4] == null) b[3] = null;
						b4changed = true;
					}
					if ( b4changed ) {
						p[5].reset();
						p[5].bind(Term.merge(b, 5));
					}
					b[5] = p[5].nextBinding();
					if (b[5] == null) b[4] = null;
					b5changed = true;
				}
				if ( b5changed ) {
					p[6].reset();
					p[6].bind(Term.merge(b, 6));
				}
				b[6] = p[6].nextBinding();
				if (b[6] == null) b[5] = null;
				b6changed = true;
			}
			if ( b6changed ) {
				p[7].reset();
				p[7].bind(Term.merge(b, 7));
			}
			b[7] = p[7].nextBinding();
			if (b[7] == null) b[6] = null;
		}

		Term[] retVal = Term.merge(b, 8);
		b[7] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
	}
}

class Operator1 extends Operator
{
	public Operator1()
	{
		super(new Predicate(1, 7, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL)))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[4];
		delIn[0] = new DelAddAtomic(new Predicate(5, 7, new TermList(TermVariable.getVariable(0), TermList.NIL)));
		delIn[1] = new DelAddAtomic(new Predicate(2, 7, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL))));
		delIn[2] = new DelAddAtomic(new Predicate(3, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL)))));
		delIn[3] = new DelAddAtomic(new Predicate(4, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL)))));

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[4];
		addIn[0] = new DelAddAtomic(new Predicate(1, 7, new TermList(TermVariable.getVariable(0), TermList.NIL)));
		addIn[1] = new DelAddAtomic(new Predicate(2, 7, new TermList(TermVariable.getVariable(0), new TermList(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(2), TermList.NIL)), StdLib.minus, "StdLib.minus"), TermList.NIL))));
		addIn[2] = new DelAddAtomic(new Predicate(3, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL)))));
		addIn[3] = new DelAddAtomic(new Predicate(4, 7, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL)))));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition1(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition2 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition2(Term[] unifier)
	{
		p = new Precondition[13];
		p[1] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(5, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(7, 11, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))), unifier);
		p[6] = new PreconditionAtomic(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[7] = new PreconditionAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL)))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(4, 11, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		p[9] = new PreconditionAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(8), TermList.NIL)))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(4, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(9), TermList.NIL)))), unifier);
		p[11] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), TermList.NIL)), StdLib.plus, "StdLib.plus"), unifier, 10);
		p[12] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(4), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[13][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[12] == null)
		{
			boolean b11changed = false;
			while (b[11] == null)
			{
				boolean b10changed = false;
				while (b[10] == null)
				{
					boolean b9changed = false;
					while (b[9] == null)
					{
						boolean b8changed = false;
						while (b[8] == null)
						{
							boolean b7changed = false;
							while (b[7] == null)
							{
								boolean b6changed = false;
								while (b[6] == null)
								{
									boolean b5changed = false;
									while (b[5] == null)
									{
										boolean b4changed = false;
										while (b[4] == null)
										{
											boolean b3changed = false;
											while (b[3] == null)
											{
												boolean b2changed = false;
												while (b[2] == null)
												{
													boolean b1changed = false;
													while (b[1] == null)
													{
														b[1] = p[1].nextBinding();
														if (b[1] == null)
															return null;
														b1changed = true;
													}
													if ( b1changed ) {
														p[2].reset();
														p[2].bind(Term.merge(b, 2));
													}
													b[2] = p[2].nextBinding();
													if (b[2] == null) b[1] = null;
													b2changed = true;
												}
												if ( b2changed ) {
													p[3].reset();
													p[3].bind(Term.merge(b, 3));
												}
												b[3] = p[3].nextBinding();
												if (b[3] == null) b[2] = null;
												b3changed = true;
											}
											if ( b3changed ) {
												p[4].reset();
												p[4].bind(Term.merge(b, 4));
											}
											b[4] = p[4].nextBinding();
											if (b[4] == null) b[3] = null;
											b4changed = true;
										}
										if ( b4changed ) {
											p[5].reset();
											p[5].bind(Term.merge(b, 5));
										}
										b[5] = p[5].nextBinding();
										if (b[5] == null) b[4] = null;
										b5changed = true;
									}
									if ( b5changed ) {
										p[6].reset();
										p[6].bind(Term.merge(b, 6));
									}
									b[6] = p[6].nextBinding();
									if (b[6] == null) b[5] = null;
									b6changed = true;
								}
								if ( b6changed ) {
									p[7].reset();
									p[7].bind(Term.merge(b, 7));
								}
								b[7] = p[7].nextBinding();
								if (b[7] == null) b[6] = null;
								b7changed = true;
							}
							if ( b7changed ) {
								p[8].reset();
								p[8].bind(Term.merge(b, 8));
							}
							b[8] = p[8].nextBinding();
							if (b[8] == null) b[7] = null;
							b8changed = true;
						}
						if ( b8changed ) {
							p[9].reset();
							p[9].bind(Term.merge(b, 9));
						}
						b[9] = p[9].nextBinding();
						if (b[9] == null) b[8] = null;
						b9changed = true;
					}
					if ( b9changed ) {
						p[10].reset();
						p[10].bind(Term.merge(b, 10));
					}
					b[10] = p[10].nextBinding();
					if (b[10] == null) b[9] = null;
					b10changed = true;
				}
				if ( b10changed ) {
					p[11].reset();
					p[11].bind(Term.merge(b, 11));
				}
				b[11] = p[11].nextBinding();
				if (b[11] == null) b[10] = null;
				b11changed = true;
			}
			if ( b11changed ) {
				p[12].reset();
				p[12].bind(Term.merge(b, 12));
			}
			b[12] = p[12].nextBinding();
			if (b[12] == null) b[11] = null;
		}

		Term[] retVal = Term.merge(b, 13);
		b[12] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}
}

class Operator2 extends Operator
{
	public Operator2()
	{
		super(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), TermList.NIL)))))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[6];
		delIn[0] = new DelAddAtomic(new Predicate(7, 11, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))));
		delIn[1] = new DelAddAtomic(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL))));
		delIn[2] = new DelAddAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL)))));
		delIn[3] = new DelAddAtomic(new Predicate(4, 11, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(7), TermList.NIL)))));
		delIn[4] = new DelAddAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(8), TermList.NIL)))));
		delIn[5] = new DelAddAtomic(new Predicate(4, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(9), TermList.NIL)))));

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[6];
		addIn[0] = new DelAddAtomic(new Predicate(7, 11, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(2), TermList.NIL))));
		addIn[1] = new DelAddAtomic(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), new TermList(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(4), TermList.NIL)), StdLib.minus, "StdLib.minus"), TermList.NIL))));
		addIn[2] = new DelAddAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(10), TermList.NIL)))));
		addIn[3] = new DelAddAtomic(new Predicate(4, 11, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(10), TermList.NIL)))));
		addIn[4] = new DelAddAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(10), TermList.NIL)))));
		addIn[5] = new DelAddAtomic(new Predicate(4, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(10), TermList.NIL)))));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition2(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition3 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition3(Term[] unifier)
	{
		p = new Precondition[22];
		p[1] = new PreconditionAtomic(new Predicate(0, 17, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(5, 17, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(0, 17, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(5, 17, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(6, 17, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[6] = new PreconditionAtomic(new Predicate(6, 17, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[7] = new PreconditionAtomic(new Predicate(7, 17, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(7, 17, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[9] = new PreconditionAtomic(new Predicate(2, 17, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		p[11] = new PreconditionAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(8), TermList.NIL)))), unifier);
		p[12] = new PreconditionAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(9), TermList.NIL)))), unifier);
		p[13] = new PreconditionAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(10), TermList.NIL)))), unifier);
		p[14] = new PreconditionAtomic(new Predicate(2, 17, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(11), TermList.NIL))), unifier);
		p[15] = new PreconditionAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(12), TermList.NIL)))), unifier);
		p[16] = new PreconditionAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(13), TermList.NIL)))), unifier);
		p[17] = new PreconditionAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(14), TermList.NIL)))), unifier);
		p[18] = new PreconditionAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(15), TermList.NIL)))), unifier);
		p[19] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.plus, "StdLib.plus"), unifier, 16);
		p[20] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		p[21] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(11), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[22][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
		b[20] = null;
		b[21] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[21] == null)
		{
			boolean b20changed = false;
			while (b[20] == null)
			{
				boolean b19changed = false;
				while (b[19] == null)
				{
					boolean b18changed = false;
					while (b[18] == null)
					{
						boolean b17changed = false;
						while (b[17] == null)
						{
							boolean b16changed = false;
							while (b[16] == null)
							{
								boolean b15changed = false;
								while (b[15] == null)
								{
									boolean b14changed = false;
									while (b[14] == null)
									{
										boolean b13changed = false;
										while (b[13] == null)
										{
											boolean b12changed = false;
											while (b[12] == null)
											{
												boolean b11changed = false;
												while (b[11] == null)
												{
													boolean b10changed = false;
													while (b[10] == null)
													{
														boolean b9changed = false;
														while (b[9] == null)
														{
															boolean b8changed = false;
															while (b[8] == null)
															{
																boolean b7changed = false;
																while (b[7] == null)
																{
																	boolean b6changed = false;
																	while (b[6] == null)
																	{
																		boolean b5changed = false;
																		while (b[5] == null)
																		{
																			boolean b4changed = false;
																			while (b[4] == null)
																			{
																				boolean b3changed = false;
																				while (b[3] == null)
																				{
																					boolean b2changed = false;
																					while (b[2] == null)
																					{
																						boolean b1changed = false;
																						while (b[1] == null)
																						{
																							b[1] = p[1].nextBinding();
																							if (b[1] == null)
																								return null;
																							b1changed = true;
																						}
																						if ( b1changed ) {
																							p[2].reset();
																							p[2].bind(Term.merge(b, 2));
																						}
																						b[2] = p[2].nextBinding();
																						if (b[2] == null) b[1] = null;
																						b2changed = true;
																					}
																					if ( b2changed ) {
																						p[3].reset();
																						p[3].bind(Term.merge(b, 3));
																					}
																					b[3] = p[3].nextBinding();
																					if (b[3] == null) b[2] = null;
																					b3changed = true;
																				}
																				if ( b3changed ) {
																					p[4].reset();
																					p[4].bind(Term.merge(b, 4));
																				}
																				b[4] = p[4].nextBinding();
																				if (b[4] == null) b[3] = null;
																				b4changed = true;
																			}
																			if ( b4changed ) {
																				p[5].reset();
																				p[5].bind(Term.merge(b, 5));
																			}
																			b[5] = p[5].nextBinding();
																			if (b[5] == null) b[4] = null;
																			b5changed = true;
																		}
																		if ( b5changed ) {
																			p[6].reset();
																			p[6].bind(Term.merge(b, 6));
																		}
																		b[6] = p[6].nextBinding();
																		if (b[6] == null) b[5] = null;
																		b6changed = true;
																	}
																	if ( b6changed ) {
																		p[7].reset();
																		p[7].bind(Term.merge(b, 7));
																	}
																	b[7] = p[7].nextBinding();
																	if (b[7] == null) b[6] = null;
																	b7changed = true;
																}
																if ( b7changed ) {
																	p[8].reset();
																	p[8].bind(Term.merge(b, 8));
																}
																b[8] = p[8].nextBinding();
																if (b[8] == null) b[7] = null;
																b8changed = true;
															}
															if ( b8changed ) {
																p[9].reset();
																p[9].bind(Term.merge(b, 9));
															}
															b[9] = p[9].nextBinding();
															if (b[9] == null) b[8] = null;
															b9changed = true;
														}
														if ( b9changed ) {
															p[10].reset();
															p[10].bind(Term.merge(b, 10));
														}
														b[10] = p[10].nextBinding();
														if (b[10] == null) b[9] = null;
														b10changed = true;
													}
													if ( b10changed ) {
														p[11].reset();
														p[11].bind(Term.merge(b, 11));
													}
													b[11] = p[11].nextBinding();
													if (b[11] == null) b[10] = null;
													b11changed = true;
												}
												if ( b11changed ) {
													p[12].reset();
													p[12].bind(Term.merge(b, 12));
												}
												b[12] = p[12].nextBinding();
												if (b[12] == null) b[11] = null;
												b12changed = true;
											}
											if ( b12changed ) {
												p[13].reset();
												p[13].bind(Term.merge(b, 13));
											}
											b[13] = p[13].nextBinding();
											if (b[13] == null) b[12] = null;
											b13changed = true;
										}
										if ( b13changed ) {
											p[14].reset();
											p[14].bind(Term.merge(b, 14));
										}
										b[14] = p[14].nextBinding();
										if (b[14] == null) b[13] = null;
										b14changed = true;
									}
									if ( b14changed ) {
										p[15].reset();
										p[15].bind(Term.merge(b, 15));
									}
									b[15] = p[15].nextBinding();
									if (b[15] == null) b[14] = null;
									b15changed = true;
								}
								if ( b15changed ) {
									p[16].reset();
									p[16].bind(Term.merge(b, 16));
								}
								b[16] = p[16].nextBinding();
								if (b[16] == null) b[15] = null;
								b16changed = true;
							}
							if ( b16changed ) {
								p[17].reset();
								p[17].bind(Term.merge(b, 17));
							}
							b[17] = p[17].nextBinding();
							if (b[17] == null) b[16] = null;
							b17changed = true;
						}
						if ( b17changed ) {
							p[18].reset();
							p[18].bind(Term.merge(b, 18));
						}
						b[18] = p[18].nextBinding();
						if (b[18] == null) b[17] = null;
						b18changed = true;
					}
					if ( b18changed ) {
						p[19].reset();
						p[19].bind(Term.merge(b, 19));
					}
					b[19] = p[19].nextBinding();
					if (b[19] == null) b[18] = null;
					b19changed = true;
				}
				if ( b19changed ) {
					p[20].reset();
					p[20].bind(Term.merge(b, 20));
				}
				b[20] = p[20].nextBinding();
				if (b[20] == null) b[19] = null;
				b20changed = true;
			}
			if ( b20changed ) {
				p[21].reset();
				p[21].bind(Term.merge(b, 21));
			}
			b[21] = p[21].nextBinding();
			if (b[21] == null) b[20] = null;
		}

		Term[] retVal = Term.merge(b, 22);
		b[21] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		p[16].reset();
		p[17].reset();
		p[18].reset();
		p[19].reset();
		p[20].reset();
		p[21].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
		b[20] = null;
		b[21] = null;
	}
}

class Operator3 extends Operator
{
	public Operator3()
	{
		super(new Predicate(2, 17, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL))))))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[12];
		delIn[0] = new DelAddAtomic(new Predicate(7, 17, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(2), TermList.NIL))));
		delIn[1] = new DelAddAtomic(new Predicate(7, 17, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))));
		delIn[2] = new DelAddAtomic(new Predicate(2, 17, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL))));
		delIn[3] = new DelAddAtomic(new Predicate(2, 17, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(11), TermList.NIL))));
		delIn[4] = new DelAddAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(7), TermList.NIL)))));
		delIn[5] = new DelAddAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(8), TermList.NIL)))));
		delIn[6] = new DelAddAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(9), TermList.NIL)))));
		delIn[7] = new DelAddAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(10), TermList.NIL)))));
		delIn[8] = new DelAddAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(12), TermList.NIL)))));
		delIn[9] = new DelAddAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(13), TermList.NIL)))));
		delIn[10] = new DelAddAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(14), TermList.NIL)))));
		delIn[11] = new DelAddAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(15), TermList.NIL)))));

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[12];
		addIn[0] = new DelAddAtomic(new Predicate(7, 17, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL))));
		addIn[1] = new DelAddAtomic(new Predicate(7, 17, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(3), TermList.NIL))));
		addIn[2] = new DelAddAtomic(new Predicate(2, 17, new TermList(TermVariable.getVariable(0), new TermList(new TermCall(new List(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.minus, "StdLib.minus"), TermList.NIL))));
		addIn[3] = new DelAddAtomic(new Predicate(2, 17, new TermList(TermVariable.getVariable(1), new TermList(new TermCall(new List(TermVariable.getVariable(11), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.minus, "StdLib.minus"), TermList.NIL))));
		addIn[4] = new DelAddAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(16), TermList.NIL)))));
		addIn[5] = new DelAddAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(16), TermList.NIL)))));
		addIn[6] = new DelAddAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(16), TermList.NIL)))));
		addIn[7] = new DelAddAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(16), TermList.NIL)))));
		addIn[8] = new DelAddAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(16), TermList.NIL)))));
		addIn[9] = new DelAddAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(16), TermList.NIL)))));
		addIn[10] = new DelAddAtomic(new Predicate(3, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(16), TermList.NIL)))));
		addIn[11] = new DelAddAtomic(new Predicate(4, 17, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(16), TermList.NIL)))));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition3(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition4 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition4(Term[] unifier)
	{
		p = new Precondition[12];
		p[1] = new PreconditionAtomic(new Predicate(0, 12, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(5, 12, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(6, 12, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(7, 12, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[5] = new PreconditionAtomic(new Predicate(2, 12, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(6), TermList.NIL))), unifier);
		p[6] = new PreconditionAtomic(new Predicate(3, 12, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		p[7] = new PreconditionAtomic(new Predicate(4, 12, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(8), TermList.NIL)))), unifier);
		p[8] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.plus, "StdLib.plus"), unifier, 9);
		p[9] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		p[10] = new PreconditionAtomic(new Predicate(8, 12, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(10), new TermList(TermVariable.getVariable(11), TermList.NIL))))), unifier);
		p[11] = new PreconditionAtomic(new Predicate(9, 12, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		b = new Term[12][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[11] == null)
		{
			boolean b10changed = false;
			while (b[10] == null)
			{
				boolean b9changed = false;
				while (b[9] == null)
				{
					boolean b8changed = false;
					while (b[8] == null)
					{
						boolean b7changed = false;
						while (b[7] == null)
						{
							boolean b6changed = false;
							while (b[6] == null)
							{
								boolean b5changed = false;
								while (b[5] == null)
								{
									boolean b4changed = false;
									while (b[4] == null)
									{
										boolean b3changed = false;
										while (b[3] == null)
										{
											boolean b2changed = false;
											while (b[2] == null)
											{
												boolean b1changed = false;
												while (b[1] == null)
												{
													b[1] = p[1].nextBinding();
													if (b[1] == null)
														return null;
													b1changed = true;
												}
												if ( b1changed ) {
													p[2].reset();
													p[2].bind(Term.merge(b, 2));
												}
												b[2] = p[2].nextBinding();
												if (b[2] == null) b[1] = null;
												b2changed = true;
											}
											if ( b2changed ) {
												p[3].reset();
												p[3].bind(Term.merge(b, 3));
											}
											b[3] = p[3].nextBinding();
											if (b[3] == null) b[2] = null;
											b3changed = true;
										}
										if ( b3changed ) {
											p[4].reset();
											p[4].bind(Term.merge(b, 4));
										}
										b[4] = p[4].nextBinding();
										if (b[4] == null) b[3] = null;
										b4changed = true;
									}
									if ( b4changed ) {
										p[5].reset();
										p[5].bind(Term.merge(b, 5));
									}
									b[5] = p[5].nextBinding();
									if (b[5] == null) b[4] = null;
									b5changed = true;
								}
								if ( b5changed ) {
									p[6].reset();
									p[6].bind(Term.merge(b, 6));
								}
								b[6] = p[6].nextBinding();
								if (b[6] == null) b[5] = null;
								b6changed = true;
							}
							if ( b6changed ) {
								p[7].reset();
								p[7].bind(Term.merge(b, 7));
							}
							b[7] = p[7].nextBinding();
							if (b[7] == null) b[6] = null;
							b7changed = true;
						}
						if ( b7changed ) {
							p[8].reset();
							p[8].bind(Term.merge(b, 8));
						}
						b[8] = p[8].nextBinding();
						if (b[8] == null) b[7] = null;
						b8changed = true;
					}
					if ( b8changed ) {
						p[9].reset();
						p[9].bind(Term.merge(b, 9));
					}
					b[9] = p[9].nextBinding();
					if (b[9] == null) b[8] = null;
					b9changed = true;
				}
				if ( b9changed ) {
					p[10].reset();
					p[10].bind(Term.merge(b, 10));
				}
				b[10] = p[10].nextBinding();
				if (b[10] == null) b[9] = null;
				b10changed = true;
			}
			if ( b10changed ) {
				p[11].reset();
				p[11].bind(Term.merge(b, 11));
			}
			b[11] = p[11].nextBinding();
			if (b[11] == null) b[10] = null;
		}

		Term[] retVal = Term.merge(b, 12);
		b[11] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
	}
}

class Operator4 extends Operator
{
	public Operator4()
	{
		super(new Predicate(3, 12, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL))))))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[4];
		delIn[0] = new DelAddAtomic(new Predicate(2, 12, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(6), TermList.NIL))));
		delIn[1] = new DelAddAtomic(new Predicate(3, 12, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(7), TermList.NIL)))));
		delIn[2] = new DelAddAtomic(new Predicate(4, 12, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(8), TermList.NIL)))));
		delIn[3] = new DelAddAtomic(new Predicate(9, 12, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))));

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[4];
		addIn[0] = new DelAddAtomic(new Predicate(2, 12, new TermList(TermVariable.getVariable(1), new TermList(new TermCall(new List(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.minus, "StdLib.minus"), TermList.NIL))));
		addIn[1] = new DelAddAtomic(new Predicate(3, 12, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(9), TermList.NIL)))));
		addIn[2] = new DelAddAtomic(new Predicate(4, 12, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(9), TermList.NIL)))));
		addIn[3] = new DelAddAtomic(new Predicate(10, 12, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition4(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition5 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition5(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 14, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(6), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition6 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition6(Term[] unifier)
	{
		p = new Precondition[18];
		p[1] = new PreconditionAtomic(new Predicate(0, 14, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(11, 14, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(5, 14, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(6, 14, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(7, 14, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[6] = new PreconditionAtomic(new Predicate(12, 14, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[7] = new PreconditionAtomic(new Predicate(7, 14, new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(1), TermList.NIL))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(13, 14, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[9] = new PreconditionAtomic(new Predicate(14, 14, new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(6), TermList.NIL))), unifier);
		p[10] = new PreconditionForAll(new Precondition5(unifier), new PreconditionAtomic(new Predicate(15, 14, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier), 14);
		p[11] = new PreconditionAtomic(new Predicate(3, 14, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(8), TermList.NIL)))), unifier);
		p[12] = new PreconditionAtomic(new Predicate(3, 14, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(9), TermList.NIL)))), unifier);
		p[13] = new PreconditionAtomic(new Predicate(4, 14, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(10), TermList.NIL)))), unifier);
		p[14] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.plus, "StdLib.plus"), unifier, 11);
		p[15] = new PreconditionAtomic(new Predicate(16, 14, new TermList(TermVariable.getVariable(12), TermList.NIL)), unifier);
		p[16] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(12), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[17] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(12), new TermList(new TermNumber(1.0), TermList.NIL)), StdLib.minus, "StdLib.minus"), unifier, 13);
		b = new Term[18][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[17] == null)
		{
			boolean b16changed = false;
			while (b[16] == null)
			{
				boolean b15changed = false;
				while (b[15] == null)
				{
					boolean b14changed = false;
					while (b[14] == null)
					{
						boolean b13changed = false;
						while (b[13] == null)
						{
							boolean b12changed = false;
							while (b[12] == null)
							{
								boolean b11changed = false;
								while (b[11] == null)
								{
									boolean b10changed = false;
									while (b[10] == null)
									{
										boolean b9changed = false;
										while (b[9] == null)
										{
											boolean b8changed = false;
											while (b[8] == null)
											{
												boolean b7changed = false;
												while (b[7] == null)
												{
													boolean b6changed = false;
													while (b[6] == null)
													{
														boolean b5changed = false;
														while (b[5] == null)
														{
															boolean b4changed = false;
															while (b[4] == null)
															{
																boolean b3changed = false;
																while (b[3] == null)
																{
																	boolean b2changed = false;
																	while (b[2] == null)
																	{
																		boolean b1changed = false;
																		while (b[1] == null)
																		{
																			b[1] = p[1].nextBinding();
																			if (b[1] == null)
																				return null;
																			b1changed = true;
																		}
																		if ( b1changed ) {
																			p[2].reset();
																			p[2].bind(Term.merge(b, 2));
																		}
																		b[2] = p[2].nextBinding();
																		if (b[2] == null) b[1] = null;
																		b2changed = true;
																	}
																	if ( b2changed ) {
																		p[3].reset();
																		p[3].bind(Term.merge(b, 3));
																	}
																	b[3] = p[3].nextBinding();
																	if (b[3] == null) b[2] = null;
																	b3changed = true;
																}
																if ( b3changed ) {
																	p[4].reset();
																	p[4].bind(Term.merge(b, 4));
																}
																b[4] = p[4].nextBinding();
																if (b[4] == null) b[3] = null;
																b4changed = true;
															}
															if ( b4changed ) {
																p[5].reset();
																p[5].bind(Term.merge(b, 5));
															}
															b[5] = p[5].nextBinding();
															if (b[5] == null) b[4] = null;
															b5changed = true;
														}
														if ( b5changed ) {
															p[6].reset();
															p[6].bind(Term.merge(b, 6));
														}
														b[6] = p[6].nextBinding();
														if (b[6] == null) b[5] = null;
														b6changed = true;
													}
													if ( b6changed ) {
														p[7].reset();
														p[7].bind(Term.merge(b, 7));
													}
													b[7] = p[7].nextBinding();
													if (b[7] == null) b[6] = null;
													b7changed = true;
												}
												if ( b7changed ) {
													p[8].reset();
													p[8].bind(Term.merge(b, 8));
												}
												b[8] = p[8].nextBinding();
												if (b[8] == null) b[7] = null;
												b8changed = true;
											}
											if ( b8changed ) {
												p[9].reset();
												p[9].bind(Term.merge(b, 9));
											}
											b[9] = p[9].nextBinding();
											if (b[9] == null) b[8] = null;
											b9changed = true;
										}
										if ( b9changed ) {
											p[10].reset();
											p[10].bind(Term.merge(b, 10));
										}
										b[10] = p[10].nextBinding();
										if (b[10] == null) b[9] = null;
										b10changed = true;
									}
									if ( b10changed ) {
										p[11].reset();
										p[11].bind(Term.merge(b, 11));
									}
									b[11] = p[11].nextBinding();
									if (b[11] == null) b[10] = null;
									b11changed = true;
								}
								if ( b11changed ) {
									p[12].reset();
									p[12].bind(Term.merge(b, 12));
								}
								b[12] = p[12].nextBinding();
								if (b[12] == null) b[11] = null;
								b12changed = true;
							}
							if ( b12changed ) {
								p[13].reset();
								p[13].bind(Term.merge(b, 13));
							}
							b[13] = p[13].nextBinding();
							if (b[13] == null) b[12] = null;
							b13changed = true;
						}
						if ( b13changed ) {
							p[14].reset();
							p[14].bind(Term.merge(b, 14));
						}
						b[14] = p[14].nextBinding();
						if (b[14] == null) b[13] = null;
						b14changed = true;
					}
					if ( b14changed ) {
						p[15].reset();
						p[15].bind(Term.merge(b, 15));
					}
					b[15] = p[15].nextBinding();
					if (b[15] == null) b[14] = null;
					b15changed = true;
				}
				if ( b15changed ) {
					p[16].reset();
					p[16].bind(Term.merge(b, 16));
				}
				b[16] = p[16].nextBinding();
				if (b[16] == null) b[15] = null;
				b16changed = true;
			}
			if ( b16changed ) {
				p[17].reset();
				p[17].bind(Term.merge(b, 17));
			}
			b[17] = p[17].nextBinding();
			if (b[17] == null) b[16] = null;
		}

		Term[] retVal = Term.merge(b, 18);
		b[17] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		p[16].reset();
		p[17].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
	}
}

class Operator5 extends Operator
{
	public Operator5()
	{
		super(new Predicate(4, 14, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL))))))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[7];
		delIn[0] = new DelAddAtomic(new Predicate(3, 14, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(8), TermList.NIL)))));
		delIn[1] = new DelAddAtomic(new Predicate(3, 14, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(9), TermList.NIL)))));
		delIn[2] = new DelAddAtomic(new Predicate(4, 14, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(10), TermList.NIL)))));
		delIn[3] = new DelAddAtomic(new Predicate(12, 14, new TermList(TermVariable.getVariable(3), TermList.NIL)));
		delIn[4] = new DelAddAtomic(new Predicate(7, 14, new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(1), TermList.NIL))));
		delIn[5] = new DelAddAtomic(new Predicate(13, 14, new TermList(TermVariable.getVariable(1), TermList.NIL)));
		delIn[6] = new DelAddAtomic(new Predicate(16, 14, new TermList(TermVariable.getVariable(12), TermList.NIL)));

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[6];
		addIn[0] = new DelAddAtomic(new Predicate(3, 14, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(11), TermList.NIL)))));
		addIn[1] = new DelAddAtomic(new Predicate(3, 14, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(11), TermList.NIL)))));
		addIn[2] = new DelAddAtomic(new Predicate(4, 14, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(11), TermList.NIL)))));
		addIn[3] = new DelAddAtomic(new Predicate(7, 14, new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(2), TermList.NIL))));
		addIn[4] = new DelAddAtomic(new Predicate(15, 14, new TermList(TermVariable.getVariable(3), TermList.NIL)));
		addIn[5] = new DelAddAtomic(new Predicate(16, 14, new TermList(TermVariable.getVariable(13), TermList.NIL)));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition6(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition7 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition7(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 16, new TermList(TermVariable.getVariable(8), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(8), new TermList(TermVariable.getVariable(7), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition8 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition8(Term[] unifier)
	{
		p = new Precondition[24];
		p[1] = new PreconditionAtomic(new Predicate(0, 16, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(0, 16, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(11, 16, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(5, 16, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(5, 16, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[6] = new PreconditionAtomic(new Predicate(6, 16, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[7] = new PreconditionAtomic(new Predicate(7, 16, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(7, 16, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[9] = new PreconditionAtomic(new Predicate(12, 16, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[10] = new PreconditionAtomic(new Predicate(7, 16, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(1), TermList.NIL))), unifier);
		p[11] = new PreconditionAtomic(new Predicate(7, 16, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[12] = new PreconditionAtomic(new Predicate(13, 16, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[13] = new PreconditionAtomic(new Predicate(13, 16, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[14] = new PreconditionAtomic(new Predicate(14, 16, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(7), TermList.NIL))), unifier);
		p[15] = new PreconditionForAll(new Precondition7(unifier), new PreconditionAtomic(new Predicate(15, 16, new TermList(TermVariable.getVariable(8), TermList.NIL)), unifier), 16);
		p[16] = new PreconditionAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(9), TermList.NIL)))), unifier);
		p[17] = new PreconditionAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(10), TermList.NIL)))), unifier);
		p[18] = new PreconditionAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(11), TermList.NIL)))), unifier);
		p[19] = new PreconditionAtomic(new Predicate(4, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(12), TermList.NIL)))), unifier);
		p[20] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), TermList.NIL)), StdLib.plus, "StdLib.plus"), unifier, 13);
		p[21] = new PreconditionAtomic(new Predicate(16, 16, new TermList(TermVariable.getVariable(14), TermList.NIL)), unifier);
		p[22] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(14), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[23] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(14), new TermList(new TermNumber(1.0), TermList.NIL)), StdLib.minus, "StdLib.minus"), unifier, 15);
		b = new Term[24][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
		b[20] = null;
		b[21] = null;
		b[22] = null;
		b[23] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[23] == null)
		{
			boolean b22changed = false;
			while (b[22] == null)
			{
				boolean b21changed = false;
				while (b[21] == null)
				{
					boolean b20changed = false;
					while (b[20] == null)
					{
						boolean b19changed = false;
						while (b[19] == null)
						{
							boolean b18changed = false;
							while (b[18] == null)
							{
								boolean b17changed = false;
								while (b[17] == null)
								{
									boolean b16changed = false;
									while (b[16] == null)
									{
										boolean b15changed = false;
										while (b[15] == null)
										{
											boolean b14changed = false;
											while (b[14] == null)
											{
												boolean b13changed = false;
												while (b[13] == null)
												{
													boolean b12changed = false;
													while (b[12] == null)
													{
														boolean b11changed = false;
														while (b[11] == null)
														{
															boolean b10changed = false;
															while (b[10] == null)
															{
																boolean b9changed = false;
																while (b[9] == null)
																{
																	boolean b8changed = false;
																	while (b[8] == null)
																	{
																		boolean b7changed = false;
																		while (b[7] == null)
																		{
																			boolean b6changed = false;
																			while (b[6] == null)
																			{
																				boolean b5changed = false;
																				while (b[5] == null)
																				{
																					boolean b4changed = false;
																					while (b[4] == null)
																					{
																						boolean b3changed = false;
																						while (b[3] == null)
																						{
																							boolean b2changed = false;
																							while (b[2] == null)
																							{
																								boolean b1changed = false;
																								while (b[1] == null)
																								{
																									b[1] = p[1].nextBinding();
																									if (b[1] == null)
																										return null;
																									b1changed = true;
																								}
																								if ( b1changed ) {
																									p[2].reset();
																									p[2].bind(Term.merge(b, 2));
																								}
																								b[2] = p[2].nextBinding();
																								if (b[2] == null) b[1] = null;
																								b2changed = true;
																							}
																							if ( b2changed ) {
																								p[3].reset();
																								p[3].bind(Term.merge(b, 3));
																							}
																							b[3] = p[3].nextBinding();
																							if (b[3] == null) b[2] = null;
																							b3changed = true;
																						}
																						if ( b3changed ) {
																							p[4].reset();
																							p[4].bind(Term.merge(b, 4));
																						}
																						b[4] = p[4].nextBinding();
																						if (b[4] == null) b[3] = null;
																						b4changed = true;
																					}
																					if ( b4changed ) {
																						p[5].reset();
																						p[5].bind(Term.merge(b, 5));
																					}
																					b[5] = p[5].nextBinding();
																					if (b[5] == null) b[4] = null;
																					b5changed = true;
																				}
																				if ( b5changed ) {
																					p[6].reset();
																					p[6].bind(Term.merge(b, 6));
																				}
																				b[6] = p[6].nextBinding();
																				if (b[6] == null) b[5] = null;
																				b6changed = true;
																			}
																			if ( b6changed ) {
																				p[7].reset();
																				p[7].bind(Term.merge(b, 7));
																			}
																			b[7] = p[7].nextBinding();
																			if (b[7] == null) b[6] = null;
																			b7changed = true;
																		}
																		if ( b7changed ) {
																			p[8].reset();
																			p[8].bind(Term.merge(b, 8));
																		}
																		b[8] = p[8].nextBinding();
																		if (b[8] == null) b[7] = null;
																		b8changed = true;
																	}
																	if ( b8changed ) {
																		p[9].reset();
																		p[9].bind(Term.merge(b, 9));
																	}
																	b[9] = p[9].nextBinding();
																	if (b[9] == null) b[8] = null;
																	b9changed = true;
																}
																if ( b9changed ) {
																	p[10].reset();
																	p[10].bind(Term.merge(b, 10));
																}
																b[10] = p[10].nextBinding();
																if (b[10] == null) b[9] = null;
																b10changed = true;
															}
															if ( b10changed ) {
																p[11].reset();
																p[11].bind(Term.merge(b, 11));
															}
															b[11] = p[11].nextBinding();
															if (b[11] == null) b[10] = null;
															b11changed = true;
														}
														if ( b11changed ) {
															p[12].reset();
															p[12].bind(Term.merge(b, 12));
														}
														b[12] = p[12].nextBinding();
														if (b[12] == null) b[11] = null;
														b12changed = true;
													}
													if ( b12changed ) {
														p[13].reset();
														p[13].bind(Term.merge(b, 13));
													}
													b[13] = p[13].nextBinding();
													if (b[13] == null) b[12] = null;
													b13changed = true;
												}
												if ( b13changed ) {
													p[14].reset();
													p[14].bind(Term.merge(b, 14));
												}
												b[14] = p[14].nextBinding();
												if (b[14] == null) b[13] = null;
												b14changed = true;
											}
											if ( b14changed ) {
												p[15].reset();
												p[15].bind(Term.merge(b, 15));
											}
											b[15] = p[15].nextBinding();
											if (b[15] == null) b[14] = null;
											b15changed = true;
										}
										if ( b15changed ) {
											p[16].reset();
											p[16].bind(Term.merge(b, 16));
										}
										b[16] = p[16].nextBinding();
										if (b[16] == null) b[15] = null;
										b16changed = true;
									}
									if ( b16changed ) {
										p[17].reset();
										p[17].bind(Term.merge(b, 17));
									}
									b[17] = p[17].nextBinding();
									if (b[17] == null) b[16] = null;
									b17changed = true;
								}
								if ( b17changed ) {
									p[18].reset();
									p[18].bind(Term.merge(b, 18));
								}
								b[18] = p[18].nextBinding();
								if (b[18] == null) b[17] = null;
								b18changed = true;
							}
							if ( b18changed ) {
								p[19].reset();
								p[19].bind(Term.merge(b, 19));
							}
							b[19] = p[19].nextBinding();
							if (b[19] == null) b[18] = null;
							b19changed = true;
						}
						if ( b19changed ) {
							p[20].reset();
							p[20].bind(Term.merge(b, 20));
						}
						b[20] = p[20].nextBinding();
						if (b[20] == null) b[19] = null;
						b20changed = true;
					}
					if ( b20changed ) {
						p[21].reset();
						p[21].bind(Term.merge(b, 21));
					}
					b[21] = p[21].nextBinding();
					if (b[21] == null) b[20] = null;
					b21changed = true;
				}
				if ( b21changed ) {
					p[22].reset();
					p[22].bind(Term.merge(b, 22));
				}
				b[22] = p[22].nextBinding();
				if (b[22] == null) b[21] = null;
				b22changed = true;
			}
			if ( b22changed ) {
				p[23].reset();
				p[23].bind(Term.merge(b, 23));
			}
			b[23] = p[23].nextBinding();
			if (b[23] == null) b[22] = null;
		}

		Term[] retVal = Term.merge(b, 24);
		b[23] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		p[16].reset();
		p[17].reset();
		p[18].reset();
		p[19].reset();
		p[20].reset();
		p[21].reset();
		p[22].reset();
		p[23].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
		b[20] = null;
		b[21] = null;
		b[22] = null;
		b[23] = null;
	}
}

class Operator6 extends Operator
{
	public Operator6()
	{
		super(new Predicate(4, 16, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), TermList.NIL)))))))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[10];
		delIn[0] = new DelAddAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(9), TermList.NIL)))));
		delIn[1] = new DelAddAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(10), TermList.NIL)))));
		delIn[2] = new DelAddAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(11), TermList.NIL)))));
		delIn[3] = new DelAddAtomic(new Predicate(4, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(12), TermList.NIL)))));
		delIn[4] = new DelAddAtomic(new Predicate(12, 16, new TermList(TermVariable.getVariable(4), TermList.NIL)));
		delIn[5] = new DelAddAtomic(new Predicate(7, 16, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(1), TermList.NIL))));
		delIn[6] = new DelAddAtomic(new Predicate(7, 16, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), TermList.NIL))));
		delIn[7] = new DelAddAtomic(new Predicate(13, 16, new TermList(TermVariable.getVariable(1), TermList.NIL)));
		delIn[8] = new DelAddAtomic(new Predicate(13, 16, new TermList(TermVariable.getVariable(2), TermList.NIL)));
		delIn[9] = new DelAddAtomic(new Predicate(16, 16, new TermList(TermVariable.getVariable(14), TermList.NIL)));

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[7];
		addIn[0] = new DelAddAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(13), TermList.NIL)))));
		addIn[1] = new DelAddAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(13), TermList.NIL)))));
		addIn[2] = new DelAddAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(13), TermList.NIL)))));
		addIn[3] = new DelAddAtomic(new Predicate(4, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(13), TermList.NIL)))));
		addIn[4] = new DelAddAtomic(new Predicate(7, 16, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(3), TermList.NIL))));
		addIn[5] = new DelAddAtomic(new Predicate(15, 16, new TermList(TermVariable.getVariable(4), TermList.NIL)));
		addIn[6] = new DelAddAtomic(new Predicate(16, 16, new TermList(TermVariable.getVariable(15), TermList.NIL)));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition8(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition9 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition9(Term[] unifier)
	{
		p = new Precondition[13];
		p[1] = new PreconditionAtomic(new Predicate(0, 9, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(11, 9, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(5, 9, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(6, 9, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(7, 9, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[6] = new PreconditionAtomic(new Predicate(7, 9, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[7] = new PreconditionNegation(new PreconditionAtomic(new Predicate(12, 9, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier), 9);
		p[8] = new PreconditionNegation(new PreconditionAtomic(new Predicate(13, 9, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier), 9);
		p[9] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL)), StdLib.plus, "StdLib.plus"), unifier, 5);
		p[10] = new PreconditionAtomic(new Predicate(3, 9, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL)))), unifier);
		p[11] = new PreconditionAtomic(new Predicate(3, 9, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		p[12] = new PreconditionAtomic(new Predicate(4, 9, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(8), TermList.NIL)))), unifier);
		b = new Term[13][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[12] == null)
		{
			boolean b11changed = false;
			while (b[11] == null)
			{
				boolean b10changed = false;
				while (b[10] == null)
				{
					boolean b9changed = false;
					while (b[9] == null)
					{
						boolean b8changed = false;
						while (b[8] == null)
						{
							boolean b7changed = false;
							while (b[7] == null)
							{
								boolean b6changed = false;
								while (b[6] == null)
								{
									boolean b5changed = false;
									while (b[5] == null)
									{
										boolean b4changed = false;
										while (b[4] == null)
										{
											boolean b3changed = false;
											while (b[3] == null)
											{
												boolean b2changed = false;
												while (b[2] == null)
												{
													boolean b1changed = false;
													while (b[1] == null)
													{
														b[1] = p[1].nextBinding();
														if (b[1] == null)
															return null;
														b1changed = true;
													}
													if ( b1changed ) {
														p[2].reset();
														p[2].bind(Term.merge(b, 2));
													}
													b[2] = p[2].nextBinding();
													if (b[2] == null) b[1] = null;
													b2changed = true;
												}
												if ( b2changed ) {
													p[3].reset();
													p[3].bind(Term.merge(b, 3));
												}
												b[3] = p[3].nextBinding();
												if (b[3] == null) b[2] = null;
												b3changed = true;
											}
											if ( b3changed ) {
												p[4].reset();
												p[4].bind(Term.merge(b, 4));
											}
											b[4] = p[4].nextBinding();
											if (b[4] == null) b[3] = null;
											b4changed = true;
										}
										if ( b4changed ) {
											p[5].reset();
											p[5].bind(Term.merge(b, 5));
										}
										b[5] = p[5].nextBinding();
										if (b[5] == null) b[4] = null;
										b5changed = true;
									}
									if ( b5changed ) {
										p[6].reset();
										p[6].bind(Term.merge(b, 6));
									}
									b[6] = p[6].nextBinding();
									if (b[6] == null) b[5] = null;
									b6changed = true;
								}
								if ( b6changed ) {
									p[7].reset();
									p[7].bind(Term.merge(b, 7));
								}
								b[7] = p[7].nextBinding();
								if (b[7] == null) b[6] = null;
								b7changed = true;
							}
							if ( b7changed ) {
								p[8].reset();
								p[8].bind(Term.merge(b, 8));
							}
							b[8] = p[8].nextBinding();
							if (b[8] == null) b[7] = null;
							b8changed = true;
						}
						if ( b8changed ) {
							p[9].reset();
							p[9].bind(Term.merge(b, 9));
						}
						b[9] = p[9].nextBinding();
						if (b[9] == null) b[8] = null;
						b9changed = true;
					}
					if ( b9changed ) {
						p[10].reset();
						p[10].bind(Term.merge(b, 10));
					}
					b[10] = p[10].nextBinding();
					if (b[10] == null) b[9] = null;
					b10changed = true;
				}
				if ( b10changed ) {
					p[11].reset();
					p[11].bind(Term.merge(b, 11));
				}
				b[11] = p[11].nextBinding();
				if (b[11] == null) b[10] = null;
				b11changed = true;
			}
			if ( b11changed ) {
				p[12].reset();
				p[12].bind(Term.merge(b, 12));
			}
			b[12] = p[12].nextBinding();
			if (b[12] == null) b[11] = null;
		}

		Term[] retVal = Term.merge(b, 13);
		b[12] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}
}

class Operator7 extends Operator
{
	public Operator7()
	{
		super(new Predicate(5, 9, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[4];
		delIn[0] = new DelAddAtomic(new Predicate(3, 9, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL)))));
		delIn[1] = new DelAddAtomic(new Predicate(3, 9, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(7), TermList.NIL)))));
		delIn[2] = new DelAddAtomic(new Predicate(4, 9, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(8), TermList.NIL)))));
		delIn[3] = new DelAddAtomic(new Predicate(7, 9, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL))));

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[6];
		addIn[0] = new DelAddAtomic(new Predicate(13, 9, new TermList(TermVariable.getVariable(0), TermList.NIL)));
		addIn[1] = new DelAddAtomic(new Predicate(12, 9, new TermList(TermVariable.getVariable(1), TermList.NIL)));
		addIn[2] = new DelAddAtomic(new Predicate(7, 9, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(0), TermList.NIL))));
		addIn[3] = new DelAddAtomic(new Predicate(3, 9, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL)))));
		addIn[4] = new DelAddAtomic(new Predicate(3, 9, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(5), TermList.NIL)))));
		addIn[5] = new DelAddAtomic(new Predicate(4, 9, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(5), TermList.NIL)))));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition9(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition10 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition10(Term[] unifier)
	{
		p = new Precondition[18];
		p[1] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(5, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(5, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[6] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(5), TermList.NIL)), unifier);
		p[7] = new PreconditionAtomic(new Predicate(7, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(7, 11, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[9] = new PreconditionAtomic(new Predicate(7, 11, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[10] = new PreconditionNegation(new PreconditionAtomic(new Predicate(12, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 11);
		p[11] = new PreconditionNegation(new PreconditionAtomic(new Predicate(13, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier), 11);
		p[12] = new PreconditionNegation(new PreconditionAtomic(new Predicate(13, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier), 11);
		p[13] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), TermList.NIL)), StdLib.plus, "StdLib.plus"), unifier, 6);
		p[14] = new PreconditionAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		p[15] = new PreconditionAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(8), TermList.NIL)))), unifier);
		p[16] = new PreconditionAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(9), TermList.NIL)))), unifier);
		p[17] = new PreconditionAtomic(new Predicate(4, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(10), TermList.NIL)))), unifier);
		b = new Term[18][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[17] == null)
		{
			boolean b16changed = false;
			while (b[16] == null)
			{
				boolean b15changed = false;
				while (b[15] == null)
				{
					boolean b14changed = false;
					while (b[14] == null)
					{
						boolean b13changed = false;
						while (b[13] == null)
						{
							boolean b12changed = false;
							while (b[12] == null)
							{
								boolean b11changed = false;
								while (b[11] == null)
								{
									boolean b10changed = false;
									while (b[10] == null)
									{
										boolean b9changed = false;
										while (b[9] == null)
										{
											boolean b8changed = false;
											while (b[8] == null)
											{
												boolean b7changed = false;
												while (b[7] == null)
												{
													boolean b6changed = false;
													while (b[6] == null)
													{
														boolean b5changed = false;
														while (b[5] == null)
														{
															boolean b4changed = false;
															while (b[4] == null)
															{
																boolean b3changed = false;
																while (b[3] == null)
																{
																	boolean b2changed = false;
																	while (b[2] == null)
																	{
																		boolean b1changed = false;
																		while (b[1] == null)
																		{
																			b[1] = p[1].nextBinding();
																			if (b[1] == null)
																				return null;
																			b1changed = true;
																		}
																		if ( b1changed ) {
																			p[2].reset();
																			p[2].bind(Term.merge(b, 2));
																		}
																		b[2] = p[2].nextBinding();
																		if (b[2] == null) b[1] = null;
																		b2changed = true;
																	}
																	if ( b2changed ) {
																		p[3].reset();
																		p[3].bind(Term.merge(b, 3));
																	}
																	b[3] = p[3].nextBinding();
																	if (b[3] == null) b[2] = null;
																	b3changed = true;
																}
																if ( b3changed ) {
																	p[4].reset();
																	p[4].bind(Term.merge(b, 4));
																}
																b[4] = p[4].nextBinding();
																if (b[4] == null) b[3] = null;
																b4changed = true;
															}
															if ( b4changed ) {
																p[5].reset();
																p[5].bind(Term.merge(b, 5));
															}
															b[5] = p[5].nextBinding();
															if (b[5] == null) b[4] = null;
															b5changed = true;
														}
														if ( b5changed ) {
															p[6].reset();
															p[6].bind(Term.merge(b, 6));
														}
														b[6] = p[6].nextBinding();
														if (b[6] == null) b[5] = null;
														b6changed = true;
													}
													if ( b6changed ) {
														p[7].reset();
														p[7].bind(Term.merge(b, 7));
													}
													b[7] = p[7].nextBinding();
													if (b[7] == null) b[6] = null;
													b7changed = true;
												}
												if ( b7changed ) {
													p[8].reset();
													p[8].bind(Term.merge(b, 8));
												}
												b[8] = p[8].nextBinding();
												if (b[8] == null) b[7] = null;
												b8changed = true;
											}
											if ( b8changed ) {
												p[9].reset();
												p[9].bind(Term.merge(b, 9));
											}
											b[9] = p[9].nextBinding();
											if (b[9] == null) b[8] = null;
											b9changed = true;
										}
										if ( b9changed ) {
											p[10].reset();
											p[10].bind(Term.merge(b, 10));
										}
										b[10] = p[10].nextBinding();
										if (b[10] == null) b[9] = null;
										b10changed = true;
									}
									if ( b10changed ) {
										p[11].reset();
										p[11].bind(Term.merge(b, 11));
									}
									b[11] = p[11].nextBinding();
									if (b[11] == null) b[10] = null;
									b11changed = true;
								}
								if ( b11changed ) {
									p[12].reset();
									p[12].bind(Term.merge(b, 12));
								}
								b[12] = p[12].nextBinding();
								if (b[12] == null) b[11] = null;
								b12changed = true;
							}
							if ( b12changed ) {
								p[13].reset();
								p[13].bind(Term.merge(b, 13));
							}
							b[13] = p[13].nextBinding();
							if (b[13] == null) b[12] = null;
							b13changed = true;
						}
						if ( b13changed ) {
							p[14].reset();
							p[14].bind(Term.merge(b, 14));
						}
						b[14] = p[14].nextBinding();
						if (b[14] == null) b[13] = null;
						b14changed = true;
					}
					if ( b14changed ) {
						p[15].reset();
						p[15].bind(Term.merge(b, 15));
					}
					b[15] = p[15].nextBinding();
					if (b[15] == null) b[14] = null;
					b15changed = true;
				}
				if ( b15changed ) {
					p[16].reset();
					p[16].bind(Term.merge(b, 16));
				}
				b[16] = p[16].nextBinding();
				if (b[16] == null) b[15] = null;
				b16changed = true;
			}
			if ( b16changed ) {
				p[17].reset();
				p[17].bind(Term.merge(b, 17));
			}
			b[17] = p[17].nextBinding();
			if (b[17] == null) b[16] = null;
		}

		Term[] retVal = Term.merge(b, 18);
		b[17] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		p[16].reset();
		p[17].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
	}
}

class Operator8 extends Operator
{
	public Operator8()
	{
		super(new Predicate(5, 11, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), TermList.NIL)))))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[5];
		delIn[0] = new DelAddAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(7), TermList.NIL)))));
		delIn[1] = new DelAddAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(8), TermList.NIL)))));
		delIn[2] = new DelAddAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(9), TermList.NIL)))));
		delIn[3] = new DelAddAtomic(new Predicate(4, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(10), TermList.NIL)))));
		delIn[4] = new DelAddAtomic(new Predicate(7, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(5), TermList.NIL))));

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[9];
		addIn[0] = new DelAddAtomic(new Predicate(13, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)));
		addIn[1] = new DelAddAtomic(new Predicate(13, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)));
		addIn[2] = new DelAddAtomic(new Predicate(12, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)));
		addIn[3] = new DelAddAtomic(new Predicate(7, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(0), TermList.NIL))));
		addIn[4] = new DelAddAtomic(new Predicate(7, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(1), TermList.NIL))));
		addIn[5] = new DelAddAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL)))));
		addIn[6] = new DelAddAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(6), TermList.NIL)))));
		addIn[7] = new DelAddAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(6), TermList.NIL)))));
		addIn[8] = new DelAddAtomic(new Predicate(4, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(6), TermList.NIL)))));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition10(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition11 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition11(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL)), StdLib.plus, "StdLib.plus"), unifier, 3);
		p[2] = new PreconditionAtomic(new Predicate(3, 5, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL)))), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Operator9 extends Operator
{
	public Operator9()
	{
		super(new Predicate(6, 5, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL)))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[1];
		delIn[0] = new DelAddAtomic(new Predicate(3, 5, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL)))));

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[1];
		addIn[0] = new DelAddAtomic(new Predicate(3, 5, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL)))));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition11(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition12 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition12(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL)), StdLib.plus, "StdLib.plus"), unifier, 4);
		p[2] = new PreconditionAtomic(new Predicate(3, 6, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL)))), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Operator10 extends Operator
{
	public Operator10()
	{
		super(new Predicate(7, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[1];
		delIn[0] = new DelAddAtomic(new Predicate(3, 6, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL)))));

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[1];
		addIn[0] = new DelAddAtomic(new Predicate(3, 6, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL)))));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition12(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Operator11 extends Operator
{
	public Operator11()
	{
		super(new Predicate(8, 0, TermList.NIL), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[0];

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionAtomic(new Predicate(16, 0, new TermList(new TermNumber(0.0), TermList.NIL)), unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Operator12 extends Operator
{
	public Operator12()
	{
		super(new Predicate(9, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[0];

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionAtomic(new Predicate(11, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Operator13 extends Operator
{
	public Operator13()
	{
		super(new Predicate(10, 0, TermList.NIL), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[0];

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionNil(0)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition13 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition13(Term[] unifier)
	{
		p = new Precondition[11];
		p[1] = new PreconditionAtomic(new Predicate(0, 7, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(6, 7, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[3] = new PreconditionNegation(new PreconditionAtomic(new Predicate(7, 7, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier), 7);
		p[4] = new PreconditionAtomic(new Predicate(0, 7, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[5] = new PreconditionNegation(new PreconditionAtomic(new Predicate(7, 7, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier), 7);
		p[6] = new PreconditionAtomic(new Predicate(17, 7, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[7] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL)), StdLib.notEq, "StdLib.notEq"), unifier);
		p[8] = new PreconditionAssign(new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier, 5);
		p[9] = new PreconditionAtomic(new Predicate(0, 7, new TermList(TermVariable.getVariable(6), TermList.NIL)), unifier);
		p[10] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[11][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[10] == null)
		{
			boolean b9changed = false;
			while (b[9] == null)
			{
				boolean b8changed = false;
				while (b[8] == null)
				{
					boolean b7changed = false;
					while (b[7] == null)
					{
						boolean b6changed = false;
						while (b[6] == null)
						{
							boolean b5changed = false;
							while (b[5] == null)
							{
								boolean b4changed = false;
								while (b[4] == null)
								{
									boolean b3changed = false;
									while (b[3] == null)
									{
										boolean b2changed = false;
										while (b[2] == null)
										{
											boolean b1changed = false;
											while (b[1] == null)
											{
												b[1] = p[1].nextBinding();
												if (b[1] == null)
													return null;
												b1changed = true;
											}
											if ( b1changed ) {
												p[2].reset();
												p[2].bind(Term.merge(b, 2));
											}
											b[2] = p[2].nextBinding();
											if (b[2] == null) b[1] = null;
											b2changed = true;
										}
										if ( b2changed ) {
											p[3].reset();
											p[3].bind(Term.merge(b, 3));
										}
										b[3] = p[3].nextBinding();
										if (b[3] == null) b[2] = null;
										b3changed = true;
									}
									if ( b3changed ) {
										p[4].reset();
										p[4].bind(Term.merge(b, 4));
									}
									b[4] = p[4].nextBinding();
									if (b[4] == null) b[3] = null;
									b4changed = true;
								}
								if ( b4changed ) {
									p[5].reset();
									p[5].bind(Term.merge(b, 5));
								}
								b[5] = p[5].nextBinding();
								if (b[5] == null) b[4] = null;
								b5changed = true;
							}
							if ( b5changed ) {
								p[6].reset();
								p[6].bind(Term.merge(b, 6));
							}
							b[6] = p[6].nextBinding();
							if (b[6] == null) b[5] = null;
							b6changed = true;
						}
						if ( b6changed ) {
							p[7].reset();
							p[7].bind(Term.merge(b, 7));
						}
						b[7] = p[7].nextBinding();
						if (b[7] == null) b[6] = null;
						b7changed = true;
					}
					if ( b7changed ) {
						p[8].reset();
						p[8].bind(Term.merge(b, 8));
					}
					b[8] = p[8].nextBinding();
					if (b[8] == null) b[7] = null;
					b8changed = true;
				}
				if ( b8changed ) {
					p[9].reset();
					p[9].bind(Term.merge(b, 9));
				}
				b[9] = p[9].nextBinding();
				if (b[9] == null) b[8] = null;
				b9changed = true;
			}
			if ( b9changed ) {
				p[10].reset();
				p[10].bind(Term.merge(b, 10));
			}
			b[10] = p[10].nextBinding();
			if (b[10] == null) b[9] = null;
		}

		Term[] retVal = Term.merge(b, 11);
		b[10] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
	}
}

class Precondition14 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition14(Term[] unifier)
	{
		p = new Precondition[11];
		p[1] = new PreconditionAtomic(new Predicate(0, 7, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(6, 7, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(7, 7, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[4] = new PreconditionAtomic(new Predicate(0, 7, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(7, 7, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[6] = new PreconditionAtomic(new Predicate(17, 7, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[7] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL)), StdLib.notEq, "StdLib.notEq"), unifier);
		p[8] = new PreconditionAssign(new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier, 5);
		p[9] = new PreconditionAtomic(new Predicate(0, 7, new TermList(TermVariable.getVariable(6), TermList.NIL)), unifier);
		p[10] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[11][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[10] == null)
		{
			boolean b9changed = false;
			while (b[9] == null)
			{
				boolean b8changed = false;
				while (b[8] == null)
				{
					boolean b7changed = false;
					while (b[7] == null)
					{
						boolean b6changed = false;
						while (b[6] == null)
						{
							boolean b5changed = false;
							while (b[5] == null)
							{
								boolean b4changed = false;
								while (b[4] == null)
								{
									boolean b3changed = false;
									while (b[3] == null)
									{
										boolean b2changed = false;
										while (b[2] == null)
										{
											boolean b1changed = false;
											while (b[1] == null)
											{
												b[1] = p[1].nextBinding();
												if (b[1] == null)
													return null;
												b1changed = true;
											}
											if ( b1changed ) {
												p[2].reset();
												p[2].bind(Term.merge(b, 2));
											}
											b[2] = p[2].nextBinding();
											if (b[2] == null) b[1] = null;
											b2changed = true;
										}
										if ( b2changed ) {
											p[3].reset();
											p[3].bind(Term.merge(b, 3));
										}
										b[3] = p[3].nextBinding();
										if (b[3] == null) b[2] = null;
										b3changed = true;
									}
									if ( b3changed ) {
										p[4].reset();
										p[4].bind(Term.merge(b, 4));
									}
									b[4] = p[4].nextBinding();
									if (b[4] == null) b[3] = null;
									b4changed = true;
								}
								if ( b4changed ) {
									p[5].reset();
									p[5].bind(Term.merge(b, 5));
								}
								b[5] = p[5].nextBinding();
								if (b[5] == null) b[4] = null;
								b5changed = true;
							}
							if ( b5changed ) {
								p[6].reset();
								p[6].bind(Term.merge(b, 6));
							}
							b[6] = p[6].nextBinding();
							if (b[6] == null) b[5] = null;
							b6changed = true;
						}
						if ( b6changed ) {
							p[7].reset();
							p[7].bind(Term.merge(b, 7));
						}
						b[7] = p[7].nextBinding();
						if (b[7] == null) b[6] = null;
						b7changed = true;
					}
					if ( b7changed ) {
						p[8].reset();
						p[8].bind(Term.merge(b, 8));
					}
					b[8] = p[8].nextBinding();
					if (b[8] == null) b[7] = null;
					b8changed = true;
				}
				if ( b8changed ) {
					p[9].reset();
					p[9].bind(Term.merge(b, 9));
				}
				b[9] = p[9].nextBinding();
				if (b[9] == null) b[8] = null;
				b9changed = true;
			}
			if ( b9changed ) {
				p[10].reset();
				p[10].bind(Term.merge(b, 10));
			}
			b[10] = p[10].nextBinding();
			if (b[10] == null) b[9] = null;
		}

		Term[] retVal = Term.merge(b, 11);
		b[10] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
	}
}

class Method0 extends Method
{
	public Method0()
	{
		super(new Predicate(2, 7, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))))));
		TaskList[] subsIn = new TaskList[2];

		subsIn[0] = createTaskList0();
		subsIn[1] = createTaskList1();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(0, 7, new TermList(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(2), TermList.NIL))), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(1, 7, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))))), false, false));

		return retVal;
	}

	TaskList createTaskList1()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(1, 7, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))))), false, false));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition13(unifier)).setComparator(null);
			break;
			case 1:
				p = (new Precondition14(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method0Branch0";
			case 1: return "Method0Branch1";
			default: return null;
		}
	}
}

class Precondition15 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition15(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 8, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(6), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition16 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition16(Term[] unifier)
	{
		p = new Precondition[11];
		p[1] = new PreconditionAtomic(new Predicate(0, 8, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(11, 8, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(6, 8, new TermList(TermVariable.getVariable(5), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(7, 8, new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[5] = new PreconditionAtomic(new Predicate(6, 8, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[6] = new PreconditionNegation(new PreconditionAtomic(new Predicate(13, 8, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier), 8);
		p[7] = new PreconditionNegation(new PreconditionAtomic(new Predicate(12, 8, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier), 8);
		p[8] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 8, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier), 8);
		p[9] = new PreconditionAtomic(new Predicate(14, 8, new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(6), TermList.NIL))), unifier);
		p[10] = new PreconditionForAll(new Precondition15(unifier), new PreconditionAtomic(new Predicate(15, 8, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier), 8);
		b = new Term[11][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[10] == null)
		{
			boolean b9changed = false;
			while (b[9] == null)
			{
				boolean b8changed = false;
				while (b[8] == null)
				{
					boolean b7changed = false;
					while (b[7] == null)
					{
						boolean b6changed = false;
						while (b[6] == null)
						{
							boolean b5changed = false;
							while (b[5] == null)
							{
								boolean b4changed = false;
								while (b[4] == null)
								{
									boolean b3changed = false;
									while (b[3] == null)
									{
										boolean b2changed = false;
										while (b[2] == null)
										{
											boolean b1changed = false;
											while (b[1] == null)
											{
												b[1] = p[1].nextBinding();
												if (b[1] == null)
													return null;
												b1changed = true;
											}
											if ( b1changed ) {
												p[2].reset();
												p[2].bind(Term.merge(b, 2));
											}
											b[2] = p[2].nextBinding();
											if (b[2] == null) b[1] = null;
											b2changed = true;
										}
										if ( b2changed ) {
											p[3].reset();
											p[3].bind(Term.merge(b, 3));
										}
										b[3] = p[3].nextBinding();
										if (b[3] == null) b[2] = null;
										b3changed = true;
									}
									if ( b3changed ) {
										p[4].reset();
										p[4].bind(Term.merge(b, 4));
									}
									b[4] = p[4].nextBinding();
									if (b[4] == null) b[3] = null;
									b4changed = true;
								}
								if ( b4changed ) {
									p[5].reset();
									p[5].bind(Term.merge(b, 5));
								}
								b[5] = p[5].nextBinding();
								if (b[5] == null) b[4] = null;
								b5changed = true;
							}
							if ( b5changed ) {
								p[6].reset();
								p[6].bind(Term.merge(b, 6));
							}
							b[6] = p[6].nextBinding();
							if (b[6] == null) b[5] = null;
							b6changed = true;
						}
						if ( b6changed ) {
							p[7].reset();
							p[7].bind(Term.merge(b, 7));
						}
						b[7] = p[7].nextBinding();
						if (b[7] == null) b[6] = null;
						b7changed = true;
					}
					if ( b7changed ) {
						p[8].reset();
						p[8].bind(Term.merge(b, 8));
					}
					b[8] = p[8].nextBinding();
					if (b[8] == null) b[7] = null;
					b8changed = true;
				}
				if ( b8changed ) {
					p[9].reset();
					p[9].bind(Term.merge(b, 9));
				}
				b[9] = p[9].nextBinding();
				if (b[9] == null) b[8] = null;
				b9changed = true;
			}
			if ( b9changed ) {
				p[10].reset();
				p[10].bind(Term.merge(b, 10));
			}
			b[10] = p[10].nextBinding();
			if (b[10] == null) b[9] = null;
		}

		Term[] retVal = Term.merge(b, 11);
		b[10] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
	}
}

class Method1 extends Method
{
	public Method1()
	{
		super(new Predicate(6, 8, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), TermList.NIL)))))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(5, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(0, 8, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(5), TermList.NIL))), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(3, 8, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(3), TermList.NIL))), false, false));
		retVal.subtasks[2] = new TaskList(new TaskAtom(new Predicate(4, 8, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(3), TermList.NIL))), false, false));
		retVal.subtasks[3] = new TaskList(new TaskAtom(new Predicate(0, 8, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), false, false));
		retVal.subtasks[4] = new TaskList(new TaskAtom(new Predicate(5, 8, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))))), false, false));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition16(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method1Branch0";
			default: return null;
		}
	}
}

class Precondition17 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition17(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 9, new TermList(TermVariable.getVariable(8), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(8), new TermList(TermVariable.getVariable(7), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition18 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition18(Term[] unifier)
	{
		p = new Precondition[13];
		p[1] = new PreconditionAtomic(new Predicate(0, 9, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(0, 9, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(11, 9, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(6, 9, new TermList(TermVariable.getVariable(6), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(7, 9, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(6), TermList.NIL))), unifier);
		p[6] = new PreconditionAtomic(new Predicate(6, 9, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[7] = new PreconditionNegation(new PreconditionAtomic(new Predicate(13, 9, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier), 9);
		p[8] = new PreconditionNegation(new PreconditionAtomic(new Predicate(13, 9, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 9);
		p[9] = new PreconditionNegation(new PreconditionAtomic(new Predicate(12, 9, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier), 9);
		p[10] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 9, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier), 9);
		p[11] = new PreconditionAtomic(new Predicate(14, 9, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(7), TermList.NIL))), unifier);
		p[12] = new PreconditionForAll(new Precondition17(unifier), new PreconditionAtomic(new Predicate(15, 9, new TermList(TermVariable.getVariable(8), TermList.NIL)), unifier), 9);
		b = new Term[13][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[12] == null)
		{
			boolean b11changed = false;
			while (b[11] == null)
			{
				boolean b10changed = false;
				while (b[10] == null)
				{
					boolean b9changed = false;
					while (b[9] == null)
					{
						boolean b8changed = false;
						while (b[8] == null)
						{
							boolean b7changed = false;
							while (b[7] == null)
							{
								boolean b6changed = false;
								while (b[6] == null)
								{
									boolean b5changed = false;
									while (b[5] == null)
									{
										boolean b4changed = false;
										while (b[4] == null)
										{
											boolean b3changed = false;
											while (b[3] == null)
											{
												boolean b2changed = false;
												while (b[2] == null)
												{
													boolean b1changed = false;
													while (b[1] == null)
													{
														b[1] = p[1].nextBinding();
														if (b[1] == null)
															return null;
														b1changed = true;
													}
													if ( b1changed ) {
														p[2].reset();
														p[2].bind(Term.merge(b, 2));
													}
													b[2] = p[2].nextBinding();
													if (b[2] == null) b[1] = null;
													b2changed = true;
												}
												if ( b2changed ) {
													p[3].reset();
													p[3].bind(Term.merge(b, 3));
												}
												b[3] = p[3].nextBinding();
												if (b[3] == null) b[2] = null;
												b3changed = true;
											}
											if ( b3changed ) {
												p[4].reset();
												p[4].bind(Term.merge(b, 4));
											}
											b[4] = p[4].nextBinding();
											if (b[4] == null) b[3] = null;
											b4changed = true;
										}
										if ( b4changed ) {
											p[5].reset();
											p[5].bind(Term.merge(b, 5));
										}
										b[5] = p[5].nextBinding();
										if (b[5] == null) b[4] = null;
										b5changed = true;
									}
									if ( b5changed ) {
										p[6].reset();
										p[6].bind(Term.merge(b, 6));
									}
									b[6] = p[6].nextBinding();
									if (b[6] == null) b[5] = null;
									b6changed = true;
								}
								if ( b6changed ) {
									p[7].reset();
									p[7].bind(Term.merge(b, 7));
								}
								b[7] = p[7].nextBinding();
								if (b[7] == null) b[6] = null;
								b7changed = true;
							}
							if ( b7changed ) {
								p[8].reset();
								p[8].bind(Term.merge(b, 8));
							}
							b[8] = p[8].nextBinding();
							if (b[8] == null) b[7] = null;
							b8changed = true;
						}
						if ( b8changed ) {
							p[9].reset();
							p[9].bind(Term.merge(b, 9));
						}
						b[9] = p[9].nextBinding();
						if (b[9] == null) b[8] = null;
						b9changed = true;
					}
					if ( b9changed ) {
						p[10].reset();
						p[10].bind(Term.merge(b, 10));
					}
					b[10] = p[10].nextBinding();
					if (b[10] == null) b[9] = null;
					b10changed = true;
				}
				if ( b10changed ) {
					p[11].reset();
					p[11].bind(Term.merge(b, 11));
				}
				b[11] = p[11].nextBinding();
				if (b[11] == null) b[10] = null;
				b11changed = true;
			}
			if ( b11changed ) {
				p[12].reset();
				p[12].bind(Term.merge(b, 12));
			}
			b[12] = p[12].nextBinding();
			if (b[12] == null) b[11] = null;
		}

		Term[] retVal = Term.merge(b, 13);
		b[12] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}
}

class Method2 extends Method
{
	public Method2()
	{
		super(new Predicate(6, 9, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL))))))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(8, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(0, 9, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(6), TermList.NIL))), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(0, 9, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(6), TermList.NIL))), false, false));
		retVal.subtasks[2] = new TaskList(new TaskAtom(new Predicate(7, 9, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), false, false));
		retVal.subtasks[3] = new TaskList(new TaskAtom(new Predicate(3, 9, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL))), false, false));
		retVal.subtasks[4] = new TaskList(new TaskAtom(new Predicate(3, 9, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(4), TermList.NIL))), false, false));
		retVal.subtasks[5] = new TaskList(new TaskAtom(new Predicate(4, 9, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(4), TermList.NIL)))), false, false));
		retVal.subtasks[6] = new TaskList(new TaskAtom(new Predicate(0, 9, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL)))), false, false));
		retVal.subtasks[7] = new TaskList(new TaskAtom(new Predicate(5, 9, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), TermList.NIL)))))), false, false));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition18(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method2Branch0";
			default: return null;
		}
	}
}

class Precondition19 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition19(Term[] unifier)
	{
		p = new Precondition[14];
		p[1] = new PreconditionAtomic(new Predicate(0, 13, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(6, 13, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(7, 13, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[4] = new PreconditionAtomic(new Predicate(2, 13, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[5] = new PreconditionAtomic(new Predicate(3, 13, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(5), TermList.NIL)))), unifier);
		p[6] = new PreconditionAtomic(new Predicate(4, 13, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(6), TermList.NIL)))), unifier);
		p[7] = new PreconditionAtomic(new Predicate(3, 13, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(4, 13, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(8), TermList.NIL)))), unifier);
		p[9] = new PreconditionAtomic(new Predicate(8, 13, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(9), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(10), TermList.NIL))))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(9, 13, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(9), TermList.NIL))), unifier);
		p[11] = new PreconditionAssign(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(10), new TermList(new TermNumber(1.0), TermList.NIL)), StdLib.div, "StdLib.div"), TermList.NIL), quadrotor.calculateCeil, "quadrotor.calculateCeil"), unifier, 11);
		p[12] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(8), TermList.NIL)))), quadrotor.calculateMax, "quadrotor.calculateMax"), unifier, 12);
		p[13] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(11), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[14][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[13] == null)
		{
			boolean b12changed = false;
			while (b[12] == null)
			{
				boolean b11changed = false;
				while (b[11] == null)
				{
					boolean b10changed = false;
					while (b[10] == null)
					{
						boolean b9changed = false;
						while (b[9] == null)
						{
							boolean b8changed = false;
							while (b[8] == null)
							{
								boolean b7changed = false;
								while (b[7] == null)
								{
									boolean b6changed = false;
									while (b[6] == null)
									{
										boolean b5changed = false;
										while (b[5] == null)
										{
											boolean b4changed = false;
											while (b[4] == null)
											{
												boolean b3changed = false;
												while (b[3] == null)
												{
													boolean b2changed = false;
													while (b[2] == null)
													{
														boolean b1changed = false;
														while (b[1] == null)
														{
															b[1] = p[1].nextBinding();
															if (b[1] == null)
																return null;
															b1changed = true;
														}
														if ( b1changed ) {
															p[2].reset();
															p[2].bind(Term.merge(b, 2));
														}
														b[2] = p[2].nextBinding();
														if (b[2] == null) b[1] = null;
														b2changed = true;
													}
													if ( b2changed ) {
														p[3].reset();
														p[3].bind(Term.merge(b, 3));
													}
													b[3] = p[3].nextBinding();
													if (b[3] == null) b[2] = null;
													b3changed = true;
												}
												if ( b3changed ) {
													p[4].reset();
													p[4].bind(Term.merge(b, 4));
												}
												b[4] = p[4].nextBinding();
												if (b[4] == null) b[3] = null;
												b4changed = true;
											}
											if ( b4changed ) {
												p[5].reset();
												p[5].bind(Term.merge(b, 5));
											}
											b[5] = p[5].nextBinding();
											if (b[5] == null) b[4] = null;
											b5changed = true;
										}
										if ( b5changed ) {
											p[6].reset();
											p[6].bind(Term.merge(b, 6));
										}
										b[6] = p[6].nextBinding();
										if (b[6] == null) b[5] = null;
										b6changed = true;
									}
									if ( b6changed ) {
										p[7].reset();
										p[7].bind(Term.merge(b, 7));
									}
									b[7] = p[7].nextBinding();
									if (b[7] == null) b[6] = null;
									b7changed = true;
								}
								if ( b7changed ) {
									p[8].reset();
									p[8].bind(Term.merge(b, 8));
								}
								b[8] = p[8].nextBinding();
								if (b[8] == null) b[7] = null;
								b8changed = true;
							}
							if ( b8changed ) {
								p[9].reset();
								p[9].bind(Term.merge(b, 9));
							}
							b[9] = p[9].nextBinding();
							if (b[9] == null) b[8] = null;
							b9changed = true;
						}
						if ( b9changed ) {
							p[10].reset();
							p[10].bind(Term.merge(b, 10));
						}
						b[10] = p[10].nextBinding();
						if (b[10] == null) b[9] = null;
						b10changed = true;
					}
					if ( b10changed ) {
						p[11].reset();
						p[11].bind(Term.merge(b, 11));
					}
					b[11] = p[11].nextBinding();
					if (b[11] == null) b[10] = null;
					b11changed = true;
				}
				if ( b11changed ) {
					p[12].reset();
					p[12].bind(Term.merge(b, 12));
				}
				b[12] = p[12].nextBinding();
				if (b[12] == null) b[11] = null;
				b12changed = true;
			}
			if ( b12changed ) {
				p[13].reset();
				p[13].bind(Term.merge(b, 13));
			}
			b[13] = p[13].nextBinding();
			if (b[13] == null) b[12] = null;
		}

		Term[] retVal = Term.merge(b, 14);
		b[13] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
	}
}

class Method3 extends Method
{
	public Method3()
	{
		super(new Predicate(1, 13, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(3, 13, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(9), new TermList(TermVariable.getVariable(12), new TermList(TermVariable.getVariable(11), TermList.NIL))))))), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition19(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method3Branch0";
			default: return null;
		}
	}
}

class Precondition20 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition20(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(5), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(4), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition21 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition21(Term[] unifier)
	{
		p = new Precondition[16];
		p[1] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(5, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(7, 11, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[6] = new PreconditionAtomic(new Predicate(12, 11, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[7] = new PreconditionAtomic(new Predicate(7, 11, new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(1), TermList.NIL))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(13, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[9] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[10] = new PreconditionForAll(new Precondition20(unifier), new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(5), TermList.NIL)), unifier), 11);
		p[11] = new PreconditionAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(6), TermList.NIL)))), unifier);
		p[12] = new PreconditionAtomic(new Predicate(3, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		p[13] = new PreconditionAtomic(new Predicate(4, 11, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(8), TermList.NIL)))), unifier);
		p[14] = new PreconditionAssign(new TermNumber(20.0), unifier, 9);
		p[15] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(8), TermList.NIL))), quadrotor.calculateMax, "quadrotor.calculateMax"), unifier, 10);
		b = new Term[16][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[15] == null)
		{
			boolean b14changed = false;
			while (b[14] == null)
			{
				boolean b13changed = false;
				while (b[13] == null)
				{
					boolean b12changed = false;
					while (b[12] == null)
					{
						boolean b11changed = false;
						while (b[11] == null)
						{
							boolean b10changed = false;
							while (b[10] == null)
							{
								boolean b9changed = false;
								while (b[9] == null)
								{
									boolean b8changed = false;
									while (b[8] == null)
									{
										boolean b7changed = false;
										while (b[7] == null)
										{
											boolean b6changed = false;
											while (b[6] == null)
											{
												boolean b5changed = false;
												while (b[5] == null)
												{
													boolean b4changed = false;
													while (b[4] == null)
													{
														boolean b3changed = false;
														while (b[3] == null)
														{
															boolean b2changed = false;
															while (b[2] == null)
															{
																boolean b1changed = false;
																while (b[1] == null)
																{
																	b[1] = p[1].nextBinding();
																	if (b[1] == null)
																		return null;
																	b1changed = true;
																}
																if ( b1changed ) {
																	p[2].reset();
																	p[2].bind(Term.merge(b, 2));
																}
																b[2] = p[2].nextBinding();
																if (b[2] == null) b[1] = null;
																b2changed = true;
															}
															if ( b2changed ) {
																p[3].reset();
																p[3].bind(Term.merge(b, 3));
															}
															b[3] = p[3].nextBinding();
															if (b[3] == null) b[2] = null;
															b3changed = true;
														}
														if ( b3changed ) {
															p[4].reset();
															p[4].bind(Term.merge(b, 4));
														}
														b[4] = p[4].nextBinding();
														if (b[4] == null) b[3] = null;
														b4changed = true;
													}
													if ( b4changed ) {
														p[5].reset();
														p[5].bind(Term.merge(b, 5));
													}
													b[5] = p[5].nextBinding();
													if (b[5] == null) b[4] = null;
													b5changed = true;
												}
												if ( b5changed ) {
													p[6].reset();
													p[6].bind(Term.merge(b, 6));
												}
												b[6] = p[6].nextBinding();
												if (b[6] == null) b[5] = null;
												b6changed = true;
											}
											if ( b6changed ) {
												p[7].reset();
												p[7].bind(Term.merge(b, 7));
											}
											b[7] = p[7].nextBinding();
											if (b[7] == null) b[6] = null;
											b7changed = true;
										}
										if ( b7changed ) {
											p[8].reset();
											p[8].bind(Term.merge(b, 8));
										}
										b[8] = p[8].nextBinding();
										if (b[8] == null) b[7] = null;
										b8changed = true;
									}
									if ( b8changed ) {
										p[9].reset();
										p[9].bind(Term.merge(b, 9));
									}
									b[9] = p[9].nextBinding();
									if (b[9] == null) b[8] = null;
									b9changed = true;
								}
								if ( b9changed ) {
									p[10].reset();
									p[10].bind(Term.merge(b, 10));
								}
								b[10] = p[10].nextBinding();
								if (b[10] == null) b[9] = null;
								b10changed = true;
							}
							if ( b10changed ) {
								p[11].reset();
								p[11].bind(Term.merge(b, 11));
							}
							b[11] = p[11].nextBinding();
							if (b[11] == null) b[10] = null;
							b11changed = true;
						}
						if ( b11changed ) {
							p[12].reset();
							p[12].bind(Term.merge(b, 12));
						}
						b[12] = p[12].nextBinding();
						if (b[12] == null) b[11] = null;
						b12changed = true;
					}
					if ( b12changed ) {
						p[13].reset();
						p[13].bind(Term.merge(b, 13));
					}
					b[13] = p[13].nextBinding();
					if (b[13] == null) b[12] = null;
					b13changed = true;
				}
				if ( b13changed ) {
					p[14].reset();
					p[14].bind(Term.merge(b, 14));
				}
				b[14] = p[14].nextBinding();
				if (b[14] == null) b[13] = null;
				b14changed = true;
			}
			if ( b14changed ) {
				p[15].reset();
				p[15].bind(Term.merge(b, 15));
			}
			b[15] = p[15].nextBinding();
			if (b[15] == null) b[14] = null;
		}

		Term[] retVal = Term.merge(b, 16);
		b[15] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
	}
}

class Method4 extends Method
{
	public Method4()
	{
		super(new Predicate(5, 11, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(4, 11, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(10), new TermList(TermVariable.getVariable(9), TermList.NIL))))))), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition21(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method4Branch0";
			default: return null;
		}
	}
}

class Precondition22 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition22(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 13, new TermList(TermVariable.getVariable(6), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition23 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition23(Term[] unifier)
	{
		p = new Precondition[22];
		p[1] = new PreconditionAtomic(new Predicate(0, 13, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(0, 13, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(11, 13, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(5, 13, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(5, 13, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[6] = new PreconditionAtomic(new Predicate(6, 13, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[7] = new PreconditionAtomic(new Predicate(7, 13, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(7, 13, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[9] = new PreconditionAtomic(new Predicate(12, 13, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[10] = new PreconditionAtomic(new Predicate(7, 13, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(1), TermList.NIL))), unifier);
		p[11] = new PreconditionAtomic(new Predicate(7, 13, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[12] = new PreconditionAtomic(new Predicate(13, 13, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[13] = new PreconditionAtomic(new Predicate(13, 13, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[14] = new PreconditionAtomic(new Predicate(14, 13, new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[15] = new PreconditionForAll(new Precondition22(unifier), new PreconditionAtomic(new Predicate(15, 13, new TermList(TermVariable.getVariable(6), TermList.NIL)), unifier), 13);
		p[16] = new PreconditionAtomic(new Predicate(3, 13, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		p[17] = new PreconditionAtomic(new Predicate(3, 13, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(8), TermList.NIL)))), unifier);
		p[18] = new PreconditionAtomic(new Predicate(3, 13, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(9), TermList.NIL)))), unifier);
		p[19] = new PreconditionAtomic(new Predicate(4, 13, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(10), TermList.NIL)))), unifier);
		p[20] = new PreconditionAssign(new TermNumber(30.0), unifier, 11);
		p[21] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(8), new TermList(TermVariable.getVariable(9), new TermList(TermVariable.getVariable(10), TermList.NIL)))), quadrotor.calculateMax, "quadrotor.calculateMax"), unifier, 12);
		b = new Term[22][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
		b[20] = null;
		b[21] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[21] == null)
		{
			boolean b20changed = false;
			while (b[20] == null)
			{
				boolean b19changed = false;
				while (b[19] == null)
				{
					boolean b18changed = false;
					while (b[18] == null)
					{
						boolean b17changed = false;
						while (b[17] == null)
						{
							boolean b16changed = false;
							while (b[16] == null)
							{
								boolean b15changed = false;
								while (b[15] == null)
								{
									boolean b14changed = false;
									while (b[14] == null)
									{
										boolean b13changed = false;
										while (b[13] == null)
										{
											boolean b12changed = false;
											while (b[12] == null)
											{
												boolean b11changed = false;
												while (b[11] == null)
												{
													boolean b10changed = false;
													while (b[10] == null)
													{
														boolean b9changed = false;
														while (b[9] == null)
														{
															boolean b8changed = false;
															while (b[8] == null)
															{
																boolean b7changed = false;
																while (b[7] == null)
																{
																	boolean b6changed = false;
																	while (b[6] == null)
																	{
																		boolean b5changed = false;
																		while (b[5] == null)
																		{
																			boolean b4changed = false;
																			while (b[4] == null)
																			{
																				boolean b3changed = false;
																				while (b[3] == null)
																				{
																					boolean b2changed = false;
																					while (b[2] == null)
																					{
																						boolean b1changed = false;
																						while (b[1] == null)
																						{
																							b[1] = p[1].nextBinding();
																							if (b[1] == null)
																								return null;
																							b1changed = true;
																						}
																						if ( b1changed ) {
																							p[2].reset();
																							p[2].bind(Term.merge(b, 2));
																						}
																						b[2] = p[2].nextBinding();
																						if (b[2] == null) b[1] = null;
																						b2changed = true;
																					}
																					if ( b2changed ) {
																						p[3].reset();
																						p[3].bind(Term.merge(b, 3));
																					}
																					b[3] = p[3].nextBinding();
																					if (b[3] == null) b[2] = null;
																					b3changed = true;
																				}
																				if ( b3changed ) {
																					p[4].reset();
																					p[4].bind(Term.merge(b, 4));
																				}
																				b[4] = p[4].nextBinding();
																				if (b[4] == null) b[3] = null;
																				b4changed = true;
																			}
																			if ( b4changed ) {
																				p[5].reset();
																				p[5].bind(Term.merge(b, 5));
																			}
																			b[5] = p[5].nextBinding();
																			if (b[5] == null) b[4] = null;
																			b5changed = true;
																		}
																		if ( b5changed ) {
																			p[6].reset();
																			p[6].bind(Term.merge(b, 6));
																		}
																		b[6] = p[6].nextBinding();
																		if (b[6] == null) b[5] = null;
																		b6changed = true;
																	}
																	if ( b6changed ) {
																		p[7].reset();
																		p[7].bind(Term.merge(b, 7));
																	}
																	b[7] = p[7].nextBinding();
																	if (b[7] == null) b[6] = null;
																	b7changed = true;
																}
																if ( b7changed ) {
																	p[8].reset();
																	p[8].bind(Term.merge(b, 8));
																}
																b[8] = p[8].nextBinding();
																if (b[8] == null) b[7] = null;
																b8changed = true;
															}
															if ( b8changed ) {
																p[9].reset();
																p[9].bind(Term.merge(b, 9));
															}
															b[9] = p[9].nextBinding();
															if (b[9] == null) b[8] = null;
															b9changed = true;
														}
														if ( b9changed ) {
															p[10].reset();
															p[10].bind(Term.merge(b, 10));
														}
														b[10] = p[10].nextBinding();
														if (b[10] == null) b[9] = null;
														b10changed = true;
													}
													if ( b10changed ) {
														p[11].reset();
														p[11].bind(Term.merge(b, 11));
													}
													b[11] = p[11].nextBinding();
													if (b[11] == null) b[10] = null;
													b11changed = true;
												}
												if ( b11changed ) {
													p[12].reset();
													p[12].bind(Term.merge(b, 12));
												}
												b[12] = p[12].nextBinding();
												if (b[12] == null) b[11] = null;
												b12changed = true;
											}
											if ( b12changed ) {
												p[13].reset();
												p[13].bind(Term.merge(b, 13));
											}
											b[13] = p[13].nextBinding();
											if (b[13] == null) b[12] = null;
											b13changed = true;
										}
										if ( b13changed ) {
											p[14].reset();
											p[14].bind(Term.merge(b, 14));
										}
										b[14] = p[14].nextBinding();
										if (b[14] == null) b[13] = null;
										b14changed = true;
									}
									if ( b14changed ) {
										p[15].reset();
										p[15].bind(Term.merge(b, 15));
									}
									b[15] = p[15].nextBinding();
									if (b[15] == null) b[14] = null;
									b15changed = true;
								}
								if ( b15changed ) {
									p[16].reset();
									p[16].bind(Term.merge(b, 16));
								}
								b[16] = p[16].nextBinding();
								if (b[16] == null) b[15] = null;
								b16changed = true;
							}
							if ( b16changed ) {
								p[17].reset();
								p[17].bind(Term.merge(b, 17));
							}
							b[17] = p[17].nextBinding();
							if (b[17] == null) b[16] = null;
							b17changed = true;
						}
						if ( b17changed ) {
							p[18].reset();
							p[18].bind(Term.merge(b, 18));
						}
						b[18] = p[18].nextBinding();
						if (b[18] == null) b[17] = null;
						b18changed = true;
					}
					if ( b18changed ) {
						p[19].reset();
						p[19].bind(Term.merge(b, 19));
					}
					b[19] = p[19].nextBinding();
					if (b[19] == null) b[18] = null;
					b19changed = true;
				}
				if ( b19changed ) {
					p[20].reset();
					p[20].bind(Term.merge(b, 20));
				}
				b[20] = p[20].nextBinding();
				if (b[20] == null) b[19] = null;
				b20changed = true;
			}
			if ( b20changed ) {
				p[21].reset();
				p[21].bind(Term.merge(b, 21));
			}
			b[21] = p[21].nextBinding();
			if (b[21] == null) b[20] = null;
		}

		Term[] retVal = Term.merge(b, 22);
		b[21] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		p[16].reset();
		p[17].reset();
		p[18].reset();
		p[19].reset();
		p[20].reset();
		p[21].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
		b[20] = null;
		b[21] = null;
	}
}

class Method5 extends Method
{
	public Method5()
	{
		super(new Predicate(5, 13, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), TermList.NIL)))))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(4, 13, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(12), new TermList(TermVariable.getVariable(11), TermList.NIL)))))))), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition23(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method5Branch0";
			default: return null;
		}
	}
}

class Precondition24 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition24(Term[] unifier)
	{
		p = new Precondition[14];
		p[1] = new PreconditionAtomic(new Predicate(0, 8, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(11, 8, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(5, 8, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(6, 8, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(7, 8, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[6] = new PreconditionAtomic(new Predicate(7, 8, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[7] = new PreconditionNegation(new PreconditionAtomic(new Predicate(13, 8, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier), 8);
		p[8] = new PreconditionAtomic(new Predicate(3, 8, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL)))), unifier);
		p[9] = new PreconditionAtomic(new Predicate(3, 8, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL)))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(4, 8, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(5), TermList.NIL)))), unifier);
		p[11] = new PreconditionNegation(new PreconditionAtomic(new Predicate(12, 8, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier), 8);
		p[12] = new PreconditionAssign(new TermNumber(20.0), unifier, 6);
		p[13] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL))), quadrotor.calculateMax, "quadrotor.calculateMax"), unifier, 7);
		b = new Term[14][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[13] == null)
		{
			boolean b12changed = false;
			while (b[12] == null)
			{
				boolean b11changed = false;
				while (b[11] == null)
				{
					boolean b10changed = false;
					while (b[10] == null)
					{
						boolean b9changed = false;
						while (b[9] == null)
						{
							boolean b8changed = false;
							while (b[8] == null)
							{
								boolean b7changed = false;
								while (b[7] == null)
								{
									boolean b6changed = false;
									while (b[6] == null)
									{
										boolean b5changed = false;
										while (b[5] == null)
										{
											boolean b4changed = false;
											while (b[4] == null)
											{
												boolean b3changed = false;
												while (b[3] == null)
												{
													boolean b2changed = false;
													while (b[2] == null)
													{
														boolean b1changed = false;
														while (b[1] == null)
														{
															b[1] = p[1].nextBinding();
															if (b[1] == null)
																return null;
															b1changed = true;
														}
														if ( b1changed ) {
															p[2].reset();
															p[2].bind(Term.merge(b, 2));
														}
														b[2] = p[2].nextBinding();
														if (b[2] == null) b[1] = null;
														b2changed = true;
													}
													if ( b2changed ) {
														p[3].reset();
														p[3].bind(Term.merge(b, 3));
													}
													b[3] = p[3].nextBinding();
													if (b[3] == null) b[2] = null;
													b3changed = true;
												}
												if ( b3changed ) {
													p[4].reset();
													p[4].bind(Term.merge(b, 4));
												}
												b[4] = p[4].nextBinding();
												if (b[4] == null) b[3] = null;
												b4changed = true;
											}
											if ( b4changed ) {
												p[5].reset();
												p[5].bind(Term.merge(b, 5));
											}
											b[5] = p[5].nextBinding();
											if (b[5] == null) b[4] = null;
											b5changed = true;
										}
										if ( b5changed ) {
											p[6].reset();
											p[6].bind(Term.merge(b, 6));
										}
										b[6] = p[6].nextBinding();
										if (b[6] == null) b[5] = null;
										b6changed = true;
									}
									if ( b6changed ) {
										p[7].reset();
										p[7].bind(Term.merge(b, 7));
									}
									b[7] = p[7].nextBinding();
									if (b[7] == null) b[6] = null;
									b7changed = true;
								}
								if ( b7changed ) {
									p[8].reset();
									p[8].bind(Term.merge(b, 8));
								}
								b[8] = p[8].nextBinding();
								if (b[8] == null) b[7] = null;
								b8changed = true;
							}
							if ( b8changed ) {
								p[9].reset();
								p[9].bind(Term.merge(b, 9));
							}
							b[9] = p[9].nextBinding();
							if (b[9] == null) b[8] = null;
							b9changed = true;
						}
						if ( b9changed ) {
							p[10].reset();
							p[10].bind(Term.merge(b, 10));
						}
						b[10] = p[10].nextBinding();
						if (b[10] == null) b[9] = null;
						b10changed = true;
					}
					if ( b10changed ) {
						p[11].reset();
						p[11].bind(Term.merge(b, 11));
					}
					b[11] = p[11].nextBinding();
					if (b[11] == null) b[10] = null;
					b11changed = true;
				}
				if ( b11changed ) {
					p[12].reset();
					p[12].bind(Term.merge(b, 12));
				}
				b[12] = p[12].nextBinding();
				if (b[12] == null) b[11] = null;
				b12changed = true;
			}
			if ( b12changed ) {
				p[13].reset();
				p[13].bind(Term.merge(b, 13));
			}
			b[13] = p[13].nextBinding();
			if (b[13] == null) b[12] = null;
		}

		Term[] retVal = Term.merge(b, 14);
		b[13] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
	}
}

class Method6 extends Method
{
	public Method6()
	{
		super(new Predicate(4, 8, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(5, 8, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(6), TermList.NIL))))), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition24(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method6Branch0";
			default: return null;
		}
	}
}

class Precondition25 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition25(Term[] unifier)
	{
		p = new Precondition[20];
		p[1] = new PreconditionAtomic(new Predicate(0, 10, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(0, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(11, 10, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(5, 10, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(5, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[6] = new PreconditionAtomic(new Predicate(6, 10, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[7] = new PreconditionAtomic(new Predicate(7, 10, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(7, 10, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[9] = new PreconditionAtomic(new Predicate(7, 10, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[10] = new PreconditionNegation(new PreconditionAtomic(new Predicate(13, 10, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier), 10);
		p[11] = new PreconditionNegation(new PreconditionAtomic(new Predicate(13, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier), 10);
		p[12] = new PreconditionAtomic(new Predicate(3, 10, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL)))), unifier);
		p[13] = new PreconditionAtomic(new Predicate(3, 10, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(5), TermList.NIL)))), unifier);
		p[14] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[15] = new PreconditionAtomic(new Predicate(3, 10, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(6), TermList.NIL)))), unifier);
		p[16] = new PreconditionAtomic(new Predicate(4, 10, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		p[17] = new PreconditionNegation(new PreconditionAtomic(new Predicate(12, 10, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 10);
		p[18] = new PreconditionAssign(new TermNumber(30.0), unifier, 8);
		p[19] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(7), TermList.NIL))), quadrotor.calculateMax, "quadrotor.calculateMax"), unifier, 9);
		b = new Term[20][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[19] == null)
		{
			boolean b18changed = false;
			while (b[18] == null)
			{
				boolean b17changed = false;
				while (b[17] == null)
				{
					boolean b16changed = false;
					while (b[16] == null)
					{
						boolean b15changed = false;
						while (b[15] == null)
						{
							boolean b14changed = false;
							while (b[14] == null)
							{
								boolean b13changed = false;
								while (b[13] == null)
								{
									boolean b12changed = false;
									while (b[12] == null)
									{
										boolean b11changed = false;
										while (b[11] == null)
										{
											boolean b10changed = false;
											while (b[10] == null)
											{
												boolean b9changed = false;
												while (b[9] == null)
												{
													boolean b8changed = false;
													while (b[8] == null)
													{
														boolean b7changed = false;
														while (b[7] == null)
														{
															boolean b6changed = false;
															while (b[6] == null)
															{
																boolean b5changed = false;
																while (b[5] == null)
																{
																	boolean b4changed = false;
																	while (b[4] == null)
																	{
																		boolean b3changed = false;
																		while (b[3] == null)
																		{
																			boolean b2changed = false;
																			while (b[2] == null)
																			{
																				boolean b1changed = false;
																				while (b[1] == null)
																				{
																					b[1] = p[1].nextBinding();
																					if (b[1] == null)
																						return null;
																					b1changed = true;
																				}
																				if ( b1changed ) {
																					p[2].reset();
																					p[2].bind(Term.merge(b, 2));
																				}
																				b[2] = p[2].nextBinding();
																				if (b[2] == null) b[1] = null;
																				b2changed = true;
																			}
																			if ( b2changed ) {
																				p[3].reset();
																				p[3].bind(Term.merge(b, 3));
																			}
																			b[3] = p[3].nextBinding();
																			if (b[3] == null) b[2] = null;
																			b3changed = true;
																		}
																		if ( b3changed ) {
																			p[4].reset();
																			p[4].bind(Term.merge(b, 4));
																		}
																		b[4] = p[4].nextBinding();
																		if (b[4] == null) b[3] = null;
																		b4changed = true;
																	}
																	if ( b4changed ) {
																		p[5].reset();
																		p[5].bind(Term.merge(b, 5));
																	}
																	b[5] = p[5].nextBinding();
																	if (b[5] == null) b[4] = null;
																	b5changed = true;
																}
																if ( b5changed ) {
																	p[6].reset();
																	p[6].bind(Term.merge(b, 6));
																}
																b[6] = p[6].nextBinding();
																if (b[6] == null) b[5] = null;
																b6changed = true;
															}
															if ( b6changed ) {
																p[7].reset();
																p[7].bind(Term.merge(b, 7));
															}
															b[7] = p[7].nextBinding();
															if (b[7] == null) b[6] = null;
															b7changed = true;
														}
														if ( b7changed ) {
															p[8].reset();
															p[8].bind(Term.merge(b, 8));
														}
														b[8] = p[8].nextBinding();
														if (b[8] == null) b[7] = null;
														b8changed = true;
													}
													if ( b8changed ) {
														p[9].reset();
														p[9].bind(Term.merge(b, 9));
													}
													b[9] = p[9].nextBinding();
													if (b[9] == null) b[8] = null;
													b9changed = true;
												}
												if ( b9changed ) {
													p[10].reset();
													p[10].bind(Term.merge(b, 10));
												}
												b[10] = p[10].nextBinding();
												if (b[10] == null) b[9] = null;
												b10changed = true;
											}
											if ( b10changed ) {
												p[11].reset();
												p[11].bind(Term.merge(b, 11));
											}
											b[11] = p[11].nextBinding();
											if (b[11] == null) b[10] = null;
											b11changed = true;
										}
										if ( b11changed ) {
											p[12].reset();
											p[12].bind(Term.merge(b, 12));
										}
										b[12] = p[12].nextBinding();
										if (b[12] == null) b[11] = null;
										b12changed = true;
									}
									if ( b12changed ) {
										p[13].reset();
										p[13].bind(Term.merge(b, 13));
									}
									b[13] = p[13].nextBinding();
									if (b[13] == null) b[12] = null;
									b13changed = true;
								}
								if ( b13changed ) {
									p[14].reset();
									p[14].bind(Term.merge(b, 14));
								}
								b[14] = p[14].nextBinding();
								if (b[14] == null) b[13] = null;
								b14changed = true;
							}
							if ( b14changed ) {
								p[15].reset();
								p[15].bind(Term.merge(b, 15));
							}
							b[15] = p[15].nextBinding();
							if (b[15] == null) b[14] = null;
							b15changed = true;
						}
						if ( b15changed ) {
							p[16].reset();
							p[16].bind(Term.merge(b, 16));
						}
						b[16] = p[16].nextBinding();
						if (b[16] == null) b[15] = null;
						b16changed = true;
					}
					if ( b16changed ) {
						p[17].reset();
						p[17].bind(Term.merge(b, 17));
					}
					b[17] = p[17].nextBinding();
					if (b[17] == null) b[16] = null;
					b17changed = true;
				}
				if ( b17changed ) {
					p[18].reset();
					p[18].bind(Term.merge(b, 18));
				}
				b[18] = p[18].nextBinding();
				if (b[18] == null) b[17] = null;
				b18changed = true;
			}
			if ( b18changed ) {
				p[19].reset();
				p[19].bind(Term.merge(b, 19));
			}
			b[19] = p[19].nextBinding();
			if (b[19] == null) b[18] = null;
		}

		Term[] retVal = Term.merge(b, 20);
		b[19] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		p[16].reset();
		p[17].reset();
		p[18].reset();
		p[19].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
	}
}

class Method7 extends Method
{
	public Method7()
	{
		super(new Predicate(4, 10, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL)))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(5, 10, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(9), new TermList(TermVariable.getVariable(8), TermList.NIL)))))), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition25(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method7Branch0";
			default: return null;
		}
	}
}

class Precondition26 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition26(Term[] unifier)
	{
		p = new Precondition[11];
		p[1] = new PreconditionAtomic(new Predicate(0, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(1, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(6, 6, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(7, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[5] = new PreconditionAtomic(new Predicate(6, 6, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[6] = new PreconditionAtomic(new Predicate(2, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(1), TermList.NIL)), quadrotor.calculateMoveCost, "quadrotor.calculateMoveCost"), unifier, 4);
		p[8] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(4), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		p[9] = new PreconditionAssign(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(4), new TermList(new TermNumber(1.0), TermList.NIL)), StdLib.div, "StdLib.div"), TermList.NIL), quadrotor.calculateCeil, "quadrotor.calculateCeil"), unifier, 5);
		p[10] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(3), new TermList(new TermCall(new List(TermVariable.getVariable(5), new TermList(new TermNumber(15.0), TermList.NIL)), StdLib.plus, "StdLib.plus"), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[11][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[10] == null)
		{
			boolean b9changed = false;
			while (b[9] == null)
			{
				boolean b8changed = false;
				while (b[8] == null)
				{
					boolean b7changed = false;
					while (b[7] == null)
					{
						boolean b6changed = false;
						while (b[6] == null)
						{
							boolean b5changed = false;
							while (b[5] == null)
							{
								boolean b4changed = false;
								while (b[4] == null)
								{
									boolean b3changed = false;
									while (b[3] == null)
									{
										boolean b2changed = false;
										while (b[2] == null)
										{
											boolean b1changed = false;
											while (b[1] == null)
											{
												b[1] = p[1].nextBinding();
												if (b[1] == null)
													return null;
												b1changed = true;
											}
											if ( b1changed ) {
												p[2].reset();
												p[2].bind(Term.merge(b, 2));
											}
											b[2] = p[2].nextBinding();
											if (b[2] == null) b[1] = null;
											b2changed = true;
										}
										if ( b2changed ) {
											p[3].reset();
											p[3].bind(Term.merge(b, 3));
										}
										b[3] = p[3].nextBinding();
										if (b[3] == null) b[2] = null;
										b3changed = true;
									}
									if ( b3changed ) {
										p[4].reset();
										p[4].bind(Term.merge(b, 4));
									}
									b[4] = p[4].nextBinding();
									if (b[4] == null) b[3] = null;
									b4changed = true;
								}
								if ( b4changed ) {
									p[5].reset();
									p[5].bind(Term.merge(b, 5));
								}
								b[5] = p[5].nextBinding();
								if (b[5] == null) b[4] = null;
								b5changed = true;
							}
							if ( b5changed ) {
								p[6].reset();
								p[6].bind(Term.merge(b, 6));
							}
							b[6] = p[6].nextBinding();
							if (b[6] == null) b[5] = null;
							b6changed = true;
						}
						if ( b6changed ) {
							p[7].reset();
							p[7].bind(Term.merge(b, 7));
						}
						b[7] = p[7].nextBinding();
						if (b[7] == null) b[6] = null;
						b7changed = true;
					}
					if ( b7changed ) {
						p[8].reset();
						p[8].bind(Term.merge(b, 8));
					}
					b[8] = p[8].nextBinding();
					if (b[8] == null) b[7] = null;
					b8changed = true;
				}
				if ( b8changed ) {
					p[9].reset();
					p[9].bind(Term.merge(b, 9));
				}
				b[9] = p[9].nextBinding();
				if (b[9] == null) b[8] = null;
				b9changed = true;
			}
			if ( b9changed ) {
				p[10].reset();
				p[10].bind(Term.merge(b, 10));
			}
			b[10] = p[10].nextBinding();
			if (b[10] == null) b[9] = null;
		}

		Term[] retVal = Term.merge(b, 11);
		b[10] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
	}
}

class Precondition27 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition27(Term[] unifier)
	{
		p = new Precondition[11];
		p[1] = new PreconditionAtomic(new Predicate(0, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(5, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(6, 6, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(7, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[5] = new PreconditionAtomic(new Predicate(6, 6, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[6] = new PreconditionAtomic(new Predicate(2, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(1), TermList.NIL)), quadrotor.calculateMoveCost, "quadrotor.calculateMoveCost"), unifier, 4);
		p[8] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(4), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		p[9] = new PreconditionAssign(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(4), new TermList(new TermNumber(1.0), TermList.NIL)), StdLib.div, "StdLib.div"), TermList.NIL), quadrotor.calculateCeil, "quadrotor.calculateCeil"), unifier, 5);
		p[10] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(5), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[11][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[10] == null)
		{
			boolean b9changed = false;
			while (b[9] == null)
			{
				boolean b8changed = false;
				while (b[8] == null)
				{
					boolean b7changed = false;
					while (b[7] == null)
					{
						boolean b6changed = false;
						while (b[6] == null)
						{
							boolean b5changed = false;
							while (b[5] == null)
							{
								boolean b4changed = false;
								while (b[4] == null)
								{
									boolean b3changed = false;
									while (b[3] == null)
									{
										boolean b2changed = false;
										while (b[2] == null)
										{
											boolean b1changed = false;
											while (b[1] == null)
											{
												b[1] = p[1].nextBinding();
												if (b[1] == null)
													return null;
												b1changed = true;
											}
											if ( b1changed ) {
												p[2].reset();
												p[2].bind(Term.merge(b, 2));
											}
											b[2] = p[2].nextBinding();
											if (b[2] == null) b[1] = null;
											b2changed = true;
										}
										if ( b2changed ) {
											p[3].reset();
											p[3].bind(Term.merge(b, 3));
										}
										b[3] = p[3].nextBinding();
										if (b[3] == null) b[2] = null;
										b3changed = true;
									}
									if ( b3changed ) {
										p[4].reset();
										p[4].bind(Term.merge(b, 4));
									}
									b[4] = p[4].nextBinding();
									if (b[4] == null) b[3] = null;
									b4changed = true;
								}
								if ( b4changed ) {
									p[5].reset();
									p[5].bind(Term.merge(b, 5));
								}
								b[5] = p[5].nextBinding();
								if (b[5] == null) b[4] = null;
								b5changed = true;
							}
							if ( b5changed ) {
								p[6].reset();
								p[6].bind(Term.merge(b, 6));
							}
							b[6] = p[6].nextBinding();
							if (b[6] == null) b[5] = null;
							b6changed = true;
						}
						if ( b6changed ) {
							p[7].reset();
							p[7].bind(Term.merge(b, 7));
						}
						b[7] = p[7].nextBinding();
						if (b[7] == null) b[6] = null;
						b7changed = true;
					}
					if ( b7changed ) {
						p[8].reset();
						p[8].bind(Term.merge(b, 8));
					}
					b[8] = p[8].nextBinding();
					if (b[8] == null) b[7] = null;
					b8changed = true;
				}
				if ( b8changed ) {
					p[9].reset();
					p[9].bind(Term.merge(b, 9));
				}
				b[9] = p[9].nextBinding();
				if (b[9] == null) b[8] = null;
				b9changed = true;
			}
			if ( b9changed ) {
				p[10].reset();
				p[10].bind(Term.merge(b, 10));
			}
			b[10] = p[10].nextBinding();
			if (b[10] == null) b[9] = null;
		}

		Term[] retVal = Term.merge(b, 11);
		b[10] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
	}
}

class Method8 extends Method
{
	public Method8()
	{
		super(new Predicate(0, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))));
		TaskList[] subsIn = new TaskList[2];

		subsIn[0] = createTaskList0();
		subsIn[1] = createTaskList1();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(8, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(9, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(5), TermList.NIL)))), false, false));

		return retVal;
	}

	TaskList createTaskList1()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(9, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(5), TermList.NIL)))), false, false));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition26(unifier)).setComparator(null);
			break;
			case 1:
				p = (new Precondition27(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method8Branch0";
			case 1: return "Method8Branch1";
			default: return null;
		}
	}
}

class Precondition28 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition28(Term[] unifier)
	{
		p = new Precondition[16];
		p[1] = new PreconditionAtomic(new Predicate(0, 8, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(5, 8, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(0, 8, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(5, 8, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(6, 8, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[6] = new PreconditionAtomic(new Predicate(7, 8, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionAtomic(new Predicate(7, 8, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(6, 8, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[9] = new PreconditionAtomic(new Predicate(2, 8, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(2, 8, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[11] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(2), TermList.NIL)), quadrotor.calculateMoveCost, "quadrotor.calculateMoveCost"), unifier, 6);
		p[12] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(6), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		p[13] = new PreconditionAssign(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(6), new TermList(new TermNumber(1.0), TermList.NIL)), StdLib.div, "StdLib.div"), TermList.NIL), quadrotor.calculateCeil, "quadrotor.calculateCeil"), unifier, 7);
		p[14] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(7), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		p[15] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(7), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[16][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[15] == null)
		{
			boolean b14changed = false;
			while (b[14] == null)
			{
				boolean b13changed = false;
				while (b[13] == null)
				{
					boolean b12changed = false;
					while (b[12] == null)
					{
						boolean b11changed = false;
						while (b[11] == null)
						{
							boolean b10changed = false;
							while (b[10] == null)
							{
								boolean b9changed = false;
								while (b[9] == null)
								{
									boolean b8changed = false;
									while (b[8] == null)
									{
										boolean b7changed = false;
										while (b[7] == null)
										{
											boolean b6changed = false;
											while (b[6] == null)
											{
												boolean b5changed = false;
												while (b[5] == null)
												{
													boolean b4changed = false;
													while (b[4] == null)
													{
														boolean b3changed = false;
														while (b[3] == null)
														{
															boolean b2changed = false;
															while (b[2] == null)
															{
																boolean b1changed = false;
																while (b[1] == null)
																{
																	b[1] = p[1].nextBinding();
																	if (b[1] == null)
																		return null;
																	b1changed = true;
																}
																if ( b1changed ) {
																	p[2].reset();
																	p[2].bind(Term.merge(b, 2));
																}
																b[2] = p[2].nextBinding();
																if (b[2] == null) b[1] = null;
																b2changed = true;
															}
															if ( b2changed ) {
																p[3].reset();
																p[3].bind(Term.merge(b, 3));
															}
															b[3] = p[3].nextBinding();
															if (b[3] == null) b[2] = null;
															b3changed = true;
														}
														if ( b3changed ) {
															p[4].reset();
															p[4].bind(Term.merge(b, 4));
														}
														b[4] = p[4].nextBinding();
														if (b[4] == null) b[3] = null;
														b4changed = true;
													}
													if ( b4changed ) {
														p[5].reset();
														p[5].bind(Term.merge(b, 5));
													}
													b[5] = p[5].nextBinding();
													if (b[5] == null) b[4] = null;
													b5changed = true;
												}
												if ( b5changed ) {
													p[6].reset();
													p[6].bind(Term.merge(b, 6));
												}
												b[6] = p[6].nextBinding();
												if (b[6] == null) b[5] = null;
												b6changed = true;
											}
											if ( b6changed ) {
												p[7].reset();
												p[7].bind(Term.merge(b, 7));
											}
											b[7] = p[7].nextBinding();
											if (b[7] == null) b[6] = null;
											b7changed = true;
										}
										if ( b7changed ) {
											p[8].reset();
											p[8].bind(Term.merge(b, 8));
										}
										b[8] = p[8].nextBinding();
										if (b[8] == null) b[7] = null;
										b8changed = true;
									}
									if ( b8changed ) {
										p[9].reset();
										p[9].bind(Term.merge(b, 9));
									}
									b[9] = p[9].nextBinding();
									if (b[9] == null) b[8] = null;
									b9changed = true;
								}
								if ( b9changed ) {
									p[10].reset();
									p[10].bind(Term.merge(b, 10));
								}
								b[10] = p[10].nextBinding();
								if (b[10] == null) b[9] = null;
								b10changed = true;
							}
							if ( b10changed ) {
								p[11].reset();
								p[11].bind(Term.merge(b, 11));
							}
							b[11] = p[11].nextBinding();
							if (b[11] == null) b[10] = null;
							b11changed = true;
						}
						if ( b11changed ) {
							p[12].reset();
							p[12].bind(Term.merge(b, 12));
						}
						b[12] = p[12].nextBinding();
						if (b[12] == null) b[11] = null;
						b12changed = true;
					}
					if ( b12changed ) {
						p[13].reset();
						p[13].bind(Term.merge(b, 13));
					}
					b[13] = p[13].nextBinding();
					if (b[13] == null) b[12] = null;
					b13changed = true;
				}
				if ( b13changed ) {
					p[14].reset();
					p[14].bind(Term.merge(b, 14));
				}
				b[14] = p[14].nextBinding();
				if (b[14] == null) b[13] = null;
				b14changed = true;
			}
			if ( b14changed ) {
				p[15].reset();
				p[15].bind(Term.merge(b, 15));
			}
			b[15] = p[15].nextBinding();
			if (b[15] == null) b[14] = null;
		}

		Term[] retVal = Term.merge(b, 16);
		b[15] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
	}
}

class Method9 extends Method
{
	public Method9()
	{
		super(new Predicate(0, 8, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL)))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(9, 8, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(7), TermList.NIL))))), false, false));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition28(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method9Branch0";
			default: return null;
		}
	}
}

class Precondition29 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition29(Term[] unifier)
	{
		p = new Precondition[13];
		p[1] = new PreconditionAtomic(new Predicate(0, 10, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(5, 10, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(6, 10, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(7, 10, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[5] = new PreconditionAtomic(new Predicate(6, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[6] = new PreconditionAtomic(new Predicate(2, 10, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[7] = new PreconditionAtomic(new Predicate(3, 10, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL)))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(4, 10, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(6), TermList.NIL)))), unifier);
		p[9] = new PreconditionAtomic(new Predicate(3, 10, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(4, 10, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(8), TermList.NIL)))), unifier);
		p[11] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(8), TermList.NIL)))), quadrotor.calculateMax, "quadrotor.calculateMax"), unifier, 9);
		p[12] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[13][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[12] == null)
		{
			boolean b11changed = false;
			while (b[11] == null)
			{
				boolean b10changed = false;
				while (b[10] == null)
				{
					boolean b9changed = false;
					while (b[9] == null)
					{
						boolean b8changed = false;
						while (b[8] == null)
						{
							boolean b7changed = false;
							while (b[7] == null)
							{
								boolean b6changed = false;
								while (b[6] == null)
								{
									boolean b5changed = false;
									while (b[5] == null)
									{
										boolean b4changed = false;
										while (b[4] == null)
										{
											boolean b3changed = false;
											while (b[3] == null)
											{
												boolean b2changed = false;
												while (b[2] == null)
												{
													boolean b1changed = false;
													while (b[1] == null)
													{
														b[1] = p[1].nextBinding();
														if (b[1] == null)
															return null;
														b1changed = true;
													}
													if ( b1changed ) {
														p[2].reset();
														p[2].bind(Term.merge(b, 2));
													}
													b[2] = p[2].nextBinding();
													if (b[2] == null) b[1] = null;
													b2changed = true;
												}
												if ( b2changed ) {
													p[3].reset();
													p[3].bind(Term.merge(b, 3));
												}
												b[3] = p[3].nextBinding();
												if (b[3] == null) b[2] = null;
												b3changed = true;
											}
											if ( b3changed ) {
												p[4].reset();
												p[4].bind(Term.merge(b, 4));
											}
											b[4] = p[4].nextBinding();
											if (b[4] == null) b[3] = null;
											b4changed = true;
										}
										if ( b4changed ) {
											p[5].reset();
											p[5].bind(Term.merge(b, 5));
										}
										b[5] = p[5].nextBinding();
										if (b[5] == null) b[4] = null;
										b5changed = true;
									}
									if ( b5changed ) {
										p[6].reset();
										p[6].bind(Term.merge(b, 6));
									}
									b[6] = p[6].nextBinding();
									if (b[6] == null) b[5] = null;
									b6changed = true;
								}
								if ( b6changed ) {
									p[7].reset();
									p[7].bind(Term.merge(b, 7));
								}
								b[7] = p[7].nextBinding();
								if (b[7] == null) b[6] = null;
								b7changed = true;
							}
							if ( b7changed ) {
								p[8].reset();
								p[8].bind(Term.merge(b, 8));
							}
							b[8] = p[8].nextBinding();
							if (b[8] == null) b[7] = null;
							b8changed = true;
						}
						if ( b8changed ) {
							p[9].reset();
							p[9].bind(Term.merge(b, 9));
						}
						b[9] = p[9].nextBinding();
						if (b[9] == null) b[8] = null;
						b9changed = true;
					}
					if ( b9changed ) {
						p[10].reset();
						p[10].bind(Term.merge(b, 10));
					}
					b[10] = p[10].nextBinding();
					if (b[10] == null) b[9] = null;
					b10changed = true;
				}
				if ( b10changed ) {
					p[11].reset();
					p[11].bind(Term.merge(b, 11));
				}
				b[11] = p[11].nextBinding();
				if (b[11] == null) b[10] = null;
				b11changed = true;
			}
			if ( b11changed ) {
				p[12].reset();
				p[12].bind(Term.merge(b, 12));
			}
			b[12] = p[12].nextBinding();
			if (b[12] == null) b[11] = null;
		}

		Term[] retVal = Term.merge(b, 13);
		b[12] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}
}

class Method10 extends Method
{
	public Method10()
	{
		super(new Predicate(9, 10, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL)))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(2, 10, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(9), new TermList(TermVariable.getVariable(2), TermList.NIL)))))), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition29(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method10Branch0";
			default: return null;
		}
	}
}

class Precondition30 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition30(Term[] unifier)
	{
		p = new Precondition[22];
		p[1] = new PreconditionAtomic(new Predicate(0, 16, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(0, 16, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(5, 16, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[4] = new PreconditionAtomic(new Predicate(5, 16, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[5] = new PreconditionAtomic(new Predicate(6, 16, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[6] = new PreconditionAtomic(new Predicate(7, 16, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[7] = new PreconditionAtomic(new Predicate(7, 16, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[8] = new PreconditionAtomic(new Predicate(6, 16, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[9] = new PreconditionAtomic(new Predicate(2, 16, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(2, 16, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(6), TermList.NIL))), unifier);
		p[11] = new PreconditionAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		p[12] = new PreconditionAtomic(new Predicate(4, 16, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(8), TermList.NIL)))), unifier);
		p[13] = new PreconditionAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(9), TermList.NIL)))), unifier);
		p[14] = new PreconditionAtomic(new Predicate(4, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(10), TermList.NIL)))), unifier);
		p[15] = new PreconditionAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(11), TermList.NIL)))), unifier);
		p[16] = new PreconditionAtomic(new Predicate(4, 16, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(12), TermList.NIL)))), unifier);
		p[17] = new PreconditionAtomic(new Predicate(3, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(13), TermList.NIL)))), unifier);
		p[18] = new PreconditionAtomic(new Predicate(4, 16, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(14), TermList.NIL)))), unifier);
		p[19] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(8), new TermList(TermVariable.getVariable(9), new TermList(TermVariable.getVariable(10), new TermList(TermVariable.getVariable(11), new TermList(TermVariable.getVariable(12), new TermList(TermVariable.getVariable(13), new TermList(TermVariable.getVariable(14), TermList.NIL)))))))), quadrotor.calculateMax, "quadrotor.calculateMax"), unifier, 15);
		p[20] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(3), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		p[21] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(3), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[22][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
		b[20] = null;
		b[21] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[21] == null)
		{
			boolean b20changed = false;
			while (b[20] == null)
			{
				boolean b19changed = false;
				while (b[19] == null)
				{
					boolean b18changed = false;
					while (b[18] == null)
					{
						boolean b17changed = false;
						while (b[17] == null)
						{
							boolean b16changed = false;
							while (b[16] == null)
							{
								boolean b15changed = false;
								while (b[15] == null)
								{
									boolean b14changed = false;
									while (b[14] == null)
									{
										boolean b13changed = false;
										while (b[13] == null)
										{
											boolean b12changed = false;
											while (b[12] == null)
											{
												boolean b11changed = false;
												while (b[11] == null)
												{
													boolean b10changed = false;
													while (b[10] == null)
													{
														boolean b9changed = false;
														while (b[9] == null)
														{
															boolean b8changed = false;
															while (b[8] == null)
															{
																boolean b7changed = false;
																while (b[7] == null)
																{
																	boolean b6changed = false;
																	while (b[6] == null)
																	{
																		boolean b5changed = false;
																		while (b[5] == null)
																		{
																			boolean b4changed = false;
																			while (b[4] == null)
																			{
																				boolean b3changed = false;
																				while (b[3] == null)
																				{
																					boolean b2changed = false;
																					while (b[2] == null)
																					{
																						boolean b1changed = false;
																						while (b[1] == null)
																						{
																							b[1] = p[1].nextBinding();
																							if (b[1] == null)
																								return null;
																							b1changed = true;
																						}
																						if ( b1changed ) {
																							p[2].reset();
																							p[2].bind(Term.merge(b, 2));
																						}
																						b[2] = p[2].nextBinding();
																						if (b[2] == null) b[1] = null;
																						b2changed = true;
																					}
																					if ( b2changed ) {
																						p[3].reset();
																						p[3].bind(Term.merge(b, 3));
																					}
																					b[3] = p[3].nextBinding();
																					if (b[3] == null) b[2] = null;
																					b3changed = true;
																				}
																				if ( b3changed ) {
																					p[4].reset();
																					p[4].bind(Term.merge(b, 4));
																				}
																				b[4] = p[4].nextBinding();
																				if (b[4] == null) b[3] = null;
																				b4changed = true;
																			}
																			if ( b4changed ) {
																				p[5].reset();
																				p[5].bind(Term.merge(b, 5));
																			}
																			b[5] = p[5].nextBinding();
																			if (b[5] == null) b[4] = null;
																			b5changed = true;
																		}
																		if ( b5changed ) {
																			p[6].reset();
																			p[6].bind(Term.merge(b, 6));
																		}
																		b[6] = p[6].nextBinding();
																		if (b[6] == null) b[5] = null;
																		b6changed = true;
																	}
																	if ( b6changed ) {
																		p[7].reset();
																		p[7].bind(Term.merge(b, 7));
																	}
																	b[7] = p[7].nextBinding();
																	if (b[7] == null) b[6] = null;
																	b7changed = true;
																}
																if ( b7changed ) {
																	p[8].reset();
																	p[8].bind(Term.merge(b, 8));
																}
																b[8] = p[8].nextBinding();
																if (b[8] == null) b[7] = null;
																b8changed = true;
															}
															if ( b8changed ) {
																p[9].reset();
																p[9].bind(Term.merge(b, 9));
															}
															b[9] = p[9].nextBinding();
															if (b[9] == null) b[8] = null;
															b9changed = true;
														}
														if ( b9changed ) {
															p[10].reset();
															p[10].bind(Term.merge(b, 10));
														}
														b[10] = p[10].nextBinding();
														if (b[10] == null) b[9] = null;
														b10changed = true;
													}
													if ( b10changed ) {
														p[11].reset();
														p[11].bind(Term.merge(b, 11));
													}
													b[11] = p[11].nextBinding();
													if (b[11] == null) b[10] = null;
													b11changed = true;
												}
												if ( b11changed ) {
													p[12].reset();
													p[12].bind(Term.merge(b, 12));
												}
												b[12] = p[12].nextBinding();
												if (b[12] == null) b[11] = null;
												b12changed = true;
											}
											if ( b12changed ) {
												p[13].reset();
												p[13].bind(Term.merge(b, 13));
											}
											b[13] = p[13].nextBinding();
											if (b[13] == null) b[12] = null;
											b13changed = true;
										}
										if ( b13changed ) {
											p[14].reset();
											p[14].bind(Term.merge(b, 14));
										}
										b[14] = p[14].nextBinding();
										if (b[14] == null) b[13] = null;
										b14changed = true;
									}
									if ( b14changed ) {
										p[15].reset();
										p[15].bind(Term.merge(b, 15));
									}
									b[15] = p[15].nextBinding();
									if (b[15] == null) b[14] = null;
									b15changed = true;
								}
								if ( b15changed ) {
									p[16].reset();
									p[16].bind(Term.merge(b, 16));
								}
								b[16] = p[16].nextBinding();
								if (b[16] == null) b[15] = null;
								b16changed = true;
							}
							if ( b16changed ) {
								p[17].reset();
								p[17].bind(Term.merge(b, 17));
							}
							b[17] = p[17].nextBinding();
							if (b[17] == null) b[16] = null;
							b17changed = true;
						}
						if ( b17changed ) {
							p[18].reset();
							p[18].bind(Term.merge(b, 18));
						}
						b[18] = p[18].nextBinding();
						if (b[18] == null) b[17] = null;
						b18changed = true;
					}
					if ( b18changed ) {
						p[19].reset();
						p[19].bind(Term.merge(b, 19));
					}
					b[19] = p[19].nextBinding();
					if (b[19] == null) b[18] = null;
					b19changed = true;
				}
				if ( b19changed ) {
					p[20].reset();
					p[20].bind(Term.merge(b, 20));
				}
				b[20] = p[20].nextBinding();
				if (b[20] == null) b[19] = null;
				b20changed = true;
			}
			if ( b20changed ) {
				p[21].reset();
				p[21].bind(Term.merge(b, 21));
			}
			b[21] = p[21].nextBinding();
			if (b[21] == null) b[20] = null;
		}

		Term[] retVal = Term.merge(b, 22);
		b[21] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		p[16].reset();
		p[17].reset();
		p[18].reset();
		p[19].reset();
		p[20].reset();
		p[21].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
		b[20] = null;
		b[21] = null;
	}
}

class Method11 extends Method
{
	public Method11()
	{
		super(new Predicate(9, 16, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(2, 16, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(15), new TermList(TermVariable.getVariable(3), TermList.NIL))))))), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition30(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method11Branch0";
			default: return null;
		}
	}
}

class Precondition31 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition31(Term[] unifier)
	{
		p = new Precondition[9];
		p[1] = new PreconditionAtomic(new Predicate(0, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(1, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(2, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))), unifier);
		p[4] = new PreconditionAtomic(new Predicate(3, 6, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(2), TermList.NIL)))), unifier);
		p[5] = new PreconditionAtomic(new Predicate(4, 6, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL)))), unifier);
		p[6] = new PreconditionAssign(new TermNumber(15.0), unifier, 4);
		p[7] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL)), quadrotor.calculateMax, "quadrotor.calculateMax"), unifier, 5);
		p[8] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[9][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[8] == null)
		{
			boolean b7changed = false;
			while (b[7] == null)
			{
				boolean b6changed = false;
				while (b[6] == null)
				{
					boolean b5changed = false;
					while (b[5] == null)
					{
						boolean b4changed = false;
						while (b[4] == null)
						{
							boolean b3changed = false;
							while (b[3] == null)
							{
								boolean b2changed = false;
								while (b[2] == null)
								{
									boolean b1changed = false;
									while (b[1] == null)
									{
										b[1] = p[1].nextBinding();
										if (b[1] == null)
											return null;
										b1changed = true;
									}
									if ( b1changed ) {
										p[2].reset();
										p[2].bind(Term.merge(b, 2));
									}
									b[2] = p[2].nextBinding();
									if (b[2] == null) b[1] = null;
									b2changed = true;
								}
								if ( b2changed ) {
									p[3].reset();
									p[3].bind(Term.merge(b, 3));
								}
								b[3] = p[3].nextBinding();
								if (b[3] == null) b[2] = null;
								b3changed = true;
							}
							if ( b3changed ) {
								p[4].reset();
								p[4].bind(Term.merge(b, 4));
							}
							b[4] = p[4].nextBinding();
							if (b[4] == null) b[3] = null;
							b4changed = true;
						}
						if ( b4changed ) {
							p[5].reset();
							p[5].bind(Term.merge(b, 5));
						}
						b[5] = p[5].nextBinding();
						if (b[5] == null) b[4] = null;
						b5changed = true;
					}
					if ( b5changed ) {
						p[6].reset();
						p[6].bind(Term.merge(b, 6));
					}
					b[6] = p[6].nextBinding();
					if (b[6] == null) b[5] = null;
					b6changed = true;
				}
				if ( b6changed ) {
					p[7].reset();
					p[7].bind(Term.merge(b, 7));
				}
				b[7] = p[7].nextBinding();
				if (b[7] == null) b[6] = null;
				b7changed = true;
			}
			if ( b7changed ) {
				p[8].reset();
				p[8].bind(Term.merge(b, 8));
			}
			b[8] = p[8].nextBinding();
			if (b[8] == null) b[7] = null;
		}

		Term[] retVal = Term.merge(b, 9);
		b[8] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
	}
}

class Method12 extends Method
{
	public Method12()
	{
		super(new Predicate(8, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(0, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(4), TermList.NIL)))), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition31(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method12Branch0";
			default: return null;
		}
	}
}

class Precondition32 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition32(Term[] unifier)
	{
		p = new Precondition[9];
		p[1] = new PreconditionAtomic(new Predicate(0, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(5, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(2, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))), unifier);
		p[4] = new PreconditionAtomic(new Predicate(3, 6, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(2), TermList.NIL)))), unifier);
		p[5] = new PreconditionAtomic(new Predicate(4, 6, new TermList(TermConstant.getConstant(2), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), TermList.NIL)))), unifier);
		p[6] = new PreconditionAssign(new TermNumber(15.0), unifier, 4);
		p[7] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL)), quadrotor.calculateMax, "quadrotor.calculateMax"), unifier, 5);
		p[8] = new PreconditionCall(new TermCall(new List(new TermCall(new List(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(4), TermList.NIL)), StdLib.minus, "StdLib.minus"), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		b = new Term[9][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[8] == null)
		{
			boolean b7changed = false;
			while (b[7] == null)
			{
				boolean b6changed = false;
				while (b[6] == null)
				{
					boolean b5changed = false;
					while (b[5] == null)
					{
						boolean b4changed = false;
						while (b[4] == null)
						{
							boolean b3changed = false;
							while (b[3] == null)
							{
								boolean b2changed = false;
								while (b[2] == null)
								{
									boolean b1changed = false;
									while (b[1] == null)
									{
										b[1] = p[1].nextBinding();
										if (b[1] == null)
											return null;
										b1changed = true;
									}
									if ( b1changed ) {
										p[2].reset();
										p[2].bind(Term.merge(b, 2));
									}
									b[2] = p[2].nextBinding();
									if (b[2] == null) b[1] = null;
									b2changed = true;
								}
								if ( b2changed ) {
									p[3].reset();
									p[3].bind(Term.merge(b, 3));
								}
								b[3] = p[3].nextBinding();
								if (b[3] == null) b[2] = null;
								b3changed = true;
							}
							if ( b3changed ) {
								p[4].reset();
								p[4].bind(Term.merge(b, 4));
							}
							b[4] = p[4].nextBinding();
							if (b[4] == null) b[3] = null;
							b4changed = true;
						}
						if ( b4changed ) {
							p[5].reset();
							p[5].bind(Term.merge(b, 5));
						}
						b[5] = p[5].nextBinding();
						if (b[5] == null) b[4] = null;
						b5changed = true;
					}
					if ( b5changed ) {
						p[6].reset();
						p[6].bind(Term.merge(b, 6));
					}
					b[6] = p[6].nextBinding();
					if (b[6] == null) b[5] = null;
					b6changed = true;
				}
				if ( b6changed ) {
					p[7].reset();
					p[7].bind(Term.merge(b, 7));
				}
				b[7] = p[7].nextBinding();
				if (b[7] == null) b[6] = null;
				b7changed = true;
			}
			if ( b7changed ) {
				p[8].reset();
				p[8].bind(Term.merge(b, 8));
			}
			b[8] = p[8].nextBinding();
			if (b[8] == null) b[7] = null;
		}

		Term[] retVal = Term.merge(b, 9);
		b[8] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
	}
}

class Method13 extends Method
{
	public Method13()
	{
		super(new Predicate(10, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(1, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(4), TermList.NIL)))), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition32(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method13Branch0";
			default: return null;
		}
	}
}

class Precondition33 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition33(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 10, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(2), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition34 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition34(Term[] unifier)
	{
		p = new Precondition[13];
		p[1] = new PreconditionAtomic(new Predicate(0, 10, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(11, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier), 10);
		p[4] = new PreconditionAtomic(new Predicate(14, 10, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[5] = new PreconditionForAll(new Precondition33(unifier), new PreconditionAtomic(new Predicate(15, 10, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier), 10);
		p[6] = new PreconditionAtomic(new Predicate(4, 10, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL)))), unifier);
		p[7] = new PreconditionAtomic(new Predicate(3, 10, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL)))), unifier);
		p[8] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL)), quadrotor.calculateMax, "quadrotor.calculateMax"), unifier, 6);
		p[9] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(2), TermList.NIL), quadrotor.calculateLastWriteTime, "quadrotor.calculateLastWriteTime"), unifier, 7);
		p[10] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(7), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		p[11] = new PreconditionAssign(TermVariable.getVariable(6), unifier, 8);
		p[12] = new PreconditionAssign(new TermNumber(0.0), unifier, 9);
		b = new Term[13][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[12] == null)
		{
			boolean b11changed = false;
			while (b[11] == null)
			{
				boolean b10changed = false;
				while (b[10] == null)
				{
					boolean b9changed = false;
					while (b[9] == null)
					{
						boolean b8changed = false;
						while (b[8] == null)
						{
							boolean b7changed = false;
							while (b[7] == null)
							{
								boolean b6changed = false;
								while (b[6] == null)
								{
									boolean b5changed = false;
									while (b[5] == null)
									{
										boolean b4changed = false;
										while (b[4] == null)
										{
											boolean b3changed = false;
											while (b[3] == null)
											{
												boolean b2changed = false;
												while (b[2] == null)
												{
													boolean b1changed = false;
													while (b[1] == null)
													{
														b[1] = p[1].nextBinding();
														if (b[1] == null)
															return null;
														b1changed = true;
													}
													if ( b1changed ) {
														p[2].reset();
														p[2].bind(Term.merge(b, 2));
													}
													b[2] = p[2].nextBinding();
													if (b[2] == null) b[1] = null;
													b2changed = true;
												}
												if ( b2changed ) {
													p[3].reset();
													p[3].bind(Term.merge(b, 3));
												}
												b[3] = p[3].nextBinding();
												if (b[3] == null) b[2] = null;
												b3changed = true;
											}
											if ( b3changed ) {
												p[4].reset();
												p[4].bind(Term.merge(b, 4));
											}
											b[4] = p[4].nextBinding();
											if (b[4] == null) b[3] = null;
											b4changed = true;
										}
										if ( b4changed ) {
											p[5].reset();
											p[5].bind(Term.merge(b, 5));
										}
										b[5] = p[5].nextBinding();
										if (b[5] == null) b[4] = null;
										b5changed = true;
									}
									if ( b5changed ) {
										p[6].reset();
										p[6].bind(Term.merge(b, 6));
									}
									b[6] = p[6].nextBinding();
									if (b[6] == null) b[5] = null;
									b6changed = true;
								}
								if ( b6changed ) {
									p[7].reset();
									p[7].bind(Term.merge(b, 7));
								}
								b[7] = p[7].nextBinding();
								if (b[7] == null) b[6] = null;
								b7changed = true;
							}
							if ( b7changed ) {
								p[8].reset();
								p[8].bind(Term.merge(b, 8));
							}
							b[8] = p[8].nextBinding();
							if (b[8] == null) b[7] = null;
							b8changed = true;
						}
						if ( b8changed ) {
							p[9].reset();
							p[9].bind(Term.merge(b, 9));
						}
						b[9] = p[9].nextBinding();
						if (b[9] == null) b[8] = null;
						b9changed = true;
					}
					if ( b9changed ) {
						p[10].reset();
						p[10].bind(Term.merge(b, 10));
					}
					b[10] = p[10].nextBinding();
					if (b[10] == null) b[9] = null;
					b10changed = true;
				}
				if ( b10changed ) {
					p[11].reset();
					p[11].bind(Term.merge(b, 11));
				}
				b[11] = p[11].nextBinding();
				if (b[11] == null) b[10] = null;
				b11changed = true;
			}
			if ( b11changed ) {
				p[12].reset();
				p[12].bind(Term.merge(b, 12));
			}
			b[12] = p[12].nextBinding();
			if (b[12] == null) b[11] = null;
		}

		Term[] retVal = Term.merge(b, 13);
		b[12] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}
}

class Precondition35 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition35(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 10, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(2), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition36 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition36(Term[] unifier)
	{
		p = new Precondition[13];
		p[1] = new PreconditionAtomic(new Predicate(0, 10, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(11, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier), 10);
		p[4] = new PreconditionAtomic(new Predicate(14, 10, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[5] = new PreconditionForAll(new Precondition35(unifier), new PreconditionAtomic(new Predicate(15, 10, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier), 10);
		p[6] = new PreconditionAtomic(new Predicate(4, 10, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(4), TermList.NIL)))), unifier);
		p[7] = new PreconditionAtomic(new Predicate(3, 10, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), TermList.NIL)))), unifier);
		p[8] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(5), TermList.NIL)), quadrotor.calculateMax, "quadrotor.calculateMax"), unifier, 6);
		p[9] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(2), TermList.NIL), quadrotor.calculateLastWriteTime, "quadrotor.calculateLastWriteTime"), unifier, 7);
		p[10] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(7), TermList.NIL)), StdLib.less, "StdLib.less"), unifier);
		p[11] = new PreconditionAssign(TermVariable.getVariable(6), unifier, 8);
		p[12] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(6), TermList.NIL)), StdLib.minus, "StdLib.minus"), unifier, 9);
		b = new Term[13][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[12] == null)
		{
			boolean b11changed = false;
			while (b[11] == null)
			{
				boolean b10changed = false;
				while (b[10] == null)
				{
					boolean b9changed = false;
					while (b[9] == null)
					{
						boolean b8changed = false;
						while (b[8] == null)
						{
							boolean b7changed = false;
							while (b[7] == null)
							{
								boolean b6changed = false;
								while (b[6] == null)
								{
									boolean b5changed = false;
									while (b[5] == null)
									{
										boolean b4changed = false;
										while (b[4] == null)
										{
											boolean b3changed = false;
											while (b[3] == null)
											{
												boolean b2changed = false;
												while (b[2] == null)
												{
													boolean b1changed = false;
													while (b[1] == null)
													{
														b[1] = p[1].nextBinding();
														if (b[1] == null)
															return null;
														b1changed = true;
													}
													if ( b1changed ) {
														p[2].reset();
														p[2].bind(Term.merge(b, 2));
													}
													b[2] = p[2].nextBinding();
													if (b[2] == null) b[1] = null;
													b2changed = true;
												}
												if ( b2changed ) {
													p[3].reset();
													p[3].bind(Term.merge(b, 3));
												}
												b[3] = p[3].nextBinding();
												if (b[3] == null) b[2] = null;
												b3changed = true;
											}
											if ( b3changed ) {
												p[4].reset();
												p[4].bind(Term.merge(b, 4));
											}
											b[4] = p[4].nextBinding();
											if (b[4] == null) b[3] = null;
											b4changed = true;
										}
										if ( b4changed ) {
											p[5].reset();
											p[5].bind(Term.merge(b, 5));
										}
										b[5] = p[5].nextBinding();
										if (b[5] == null) b[4] = null;
										b5changed = true;
									}
									if ( b5changed ) {
										p[6].reset();
										p[6].bind(Term.merge(b, 6));
									}
									b[6] = p[6].nextBinding();
									if (b[6] == null) b[5] = null;
									b6changed = true;
								}
								if ( b6changed ) {
									p[7].reset();
									p[7].bind(Term.merge(b, 7));
								}
								b[7] = p[7].nextBinding();
								if (b[7] == null) b[6] = null;
								b7changed = true;
							}
							if ( b7changed ) {
								p[8].reset();
								p[8].bind(Term.merge(b, 8));
							}
							b[8] = p[8].nextBinding();
							if (b[8] == null) b[7] = null;
							b8changed = true;
						}
						if ( b8changed ) {
							p[9].reset();
							p[9].bind(Term.merge(b, 9));
						}
						b[9] = p[9].nextBinding();
						if (b[9] == null) b[8] = null;
						b9changed = true;
					}
					if ( b9changed ) {
						p[10].reset();
						p[10].bind(Term.merge(b, 10));
					}
					b[10] = p[10].nextBinding();
					if (b[10] == null) b[9] = null;
					b10changed = true;
				}
				if ( b10changed ) {
					p[11].reset();
					p[11].bind(Term.merge(b, 11));
				}
				b[11] = p[11].nextBinding();
				if (b[11] == null) b[10] = null;
				b11changed = true;
			}
			if ( b11changed ) {
				p[12].reset();
				p[12].bind(Term.merge(b, 12));
			}
			b[12] = p[12].nextBinding();
			if (b[12] == null) b[11] = null;
		}

		Term[] retVal = Term.merge(b, 13);
		b[12] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
	}
}

class Method14 extends Method
{
	public Method14()
	{
		super(new Predicate(3, 10, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))));
		TaskList[] subsIn = new TaskList[2];

		subsIn[0] = createTaskList0();
		subsIn[1] = createTaskList1();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(6, 10, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(8), new TermList(TermVariable.getVariable(9), TermList.NIL)))), false, true));

		return retVal;
	}

	TaskList createTaskList1()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(6, 10, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(8), new TermList(TermVariable.getVariable(9), TermList.NIL)))), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition34(unifier)).setComparator(null);
			break;
			case 1:
				p = (new Precondition36(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method14Branch0";
			case 1: return "Method14Branch1";
			default: return null;
		}
	}
}

class Precondition37 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition37(Term[] unifier)
	{
		p = new Precondition[8];
		p[1] = new PreconditionAtomic(new Predicate(0, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(0, 6, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(4, 6, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(2), TermList.NIL)))), unifier);
		p[4] = new PreconditionAtomic(new Predicate(4, 6, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(3), TermList.NIL)))), unifier);
		p[5] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL)), StdLib.moreEq, "StdLib.moreEq"), unifier);
		p[6] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL)), StdLib.minus, "StdLib.minus"), unifier, 4);
		p[7] = new PreconditionAssign(TermVariable.getVariable(3), unifier, 5);
		b = new Term[8][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[7] == null)
		{
			boolean b6changed = false;
			while (b[6] == null)
			{
				boolean b5changed = false;
				while (b[5] == null)
				{
					boolean b4changed = false;
					while (b[4] == null)
					{
						boolean b3changed = false;
						while (b[3] == null)
						{
							boolean b2changed = false;
							while (b[2] == null)
							{
								boolean b1changed = false;
								while (b[1] == null)
								{
									b[1] = p[1].nextBinding();
									if (b[1] == null)
										return null;
									b1changed = true;
								}
								if ( b1changed ) {
									p[2].reset();
									p[2].bind(Term.merge(b, 2));
								}
								b[2] = p[2].nextBinding();
								if (b[2] == null) b[1] = null;
								b2changed = true;
							}
							if ( b2changed ) {
								p[3].reset();
								p[3].bind(Term.merge(b, 3));
							}
							b[3] = p[3].nextBinding();
							if (b[3] == null) b[2] = null;
							b3changed = true;
						}
						if ( b3changed ) {
							p[4].reset();
							p[4].bind(Term.merge(b, 4));
						}
						b[4] = p[4].nextBinding();
						if (b[4] == null) b[3] = null;
						b4changed = true;
					}
					if ( b4changed ) {
						p[5].reset();
						p[5].bind(Term.merge(b, 5));
					}
					b[5] = p[5].nextBinding();
					if (b[5] == null) b[4] = null;
					b5changed = true;
				}
				if ( b5changed ) {
					p[6].reset();
					p[6].bind(Term.merge(b, 6));
				}
				b[6] = p[6].nextBinding();
				if (b[6] == null) b[5] = null;
				b6changed = true;
			}
			if ( b6changed ) {
				p[7].reset();
				p[7].bind(Term.merge(b, 7));
			}
			b[7] = p[7].nextBinding();
			if (b[7] == null) b[6] = null;
		}

		Term[] retVal = Term.merge(b, 8);
		b[7] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
	}
}

class Precondition38 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition38(Term[] unifier)
	{
		p = new Precondition[8];
		p[1] = new PreconditionAtomic(new Predicate(0, 6, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(0, 6, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionAtomic(new Predicate(4, 6, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(2), TermList.NIL)))), unifier);
		p[4] = new PreconditionAtomic(new Predicate(4, 6, new TermList(TermConstant.getConstant(7), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(3), TermList.NIL)))), unifier);
		p[5] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL)), StdLib.less, "StdLib.less"), unifier);
		p[6] = new PreconditionAssign(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermVariable.getVariable(2), TermList.NIL)), StdLib.minus, "StdLib.minus"), unifier, 4);
		p[7] = new PreconditionAssign(TermVariable.getVariable(2), unifier, 5);
		b = new Term[8][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[7] == null)
		{
			boolean b6changed = false;
			while (b[6] == null)
			{
				boolean b5changed = false;
				while (b[5] == null)
				{
					boolean b4changed = false;
					while (b[4] == null)
					{
						boolean b3changed = false;
						while (b[3] == null)
						{
							boolean b2changed = false;
							while (b[2] == null)
							{
								boolean b1changed = false;
								while (b[1] == null)
								{
									b[1] = p[1].nextBinding();
									if (b[1] == null)
										return null;
									b1changed = true;
								}
								if ( b1changed ) {
									p[2].reset();
									p[2].bind(Term.merge(b, 2));
								}
								b[2] = p[2].nextBinding();
								if (b[2] == null) b[1] = null;
								b2changed = true;
							}
							if ( b2changed ) {
								p[3].reset();
								p[3].bind(Term.merge(b, 3));
							}
							b[3] = p[3].nextBinding();
							if (b[3] == null) b[2] = null;
							b3changed = true;
						}
						if ( b3changed ) {
							p[4].reset();
							p[4].bind(Term.merge(b, 4));
						}
						b[4] = p[4].nextBinding();
						if (b[4] == null) b[3] = null;
						b4changed = true;
					}
					if ( b4changed ) {
						p[5].reset();
						p[5].bind(Term.merge(b, 5));
					}
					b[5] = p[5].nextBinding();
					if (b[5] == null) b[4] = null;
					b5changed = true;
				}
				if ( b5changed ) {
					p[6].reset();
					p[6].bind(Term.merge(b, 6));
				}
				b[6] = p[6].nextBinding();
				if (b[6] == null) b[5] = null;
				b6changed = true;
			}
			if ( b6changed ) {
				p[7].reset();
				p[7].bind(Term.merge(b, 7));
			}
			b[7] = p[7].nextBinding();
			if (b[7] == null) b[6] = null;
		}

		Term[] retVal = Term.merge(b, 8);
		b[7] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
	}
}

class Method15 extends Method
{
	public Method15()
	{
		super(new Predicate(7, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))));
		TaskList[] subsIn = new TaskList[2];

		subsIn[0] = createTaskList0();
		subsIn[1] = createTaskList1();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(7, 6, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(4), TermList.NIL))))), false, true));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(7, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), new TermList(new TermNumber(0.0), TermList.NIL))))), false, true));

		return retVal;
	}

	TaskList createTaskList1()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(7, 6, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(4), TermList.NIL))))), false, true));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(7, 6, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(3), new TermList(new TermNumber(0.0), TermList.NIL))))), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition37(unifier)).setComparator(null);
			break;
			case 1:
				p = (new Precondition38(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method15Branch0";
			case 1: return "Method15Branch1";
			default: return null;
		}
	}
}

class Precondition39 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition39(Term[] unifier)
	{
		p = new Precondition[20];
		p[1] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(18), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[2] = new PreconditionAtomic(new Predicate(16, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[4] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 11);
		p[6] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermList.NIL, TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[8] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[9] = new PreconditionAtomic(new Predicate(19, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(5), TermList.NIL)), unifier);
		p[11] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(6), TermList.NIL)), unifier);
		p[12] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), TermList.NIL)), StdLib.notEq, "StdLib.notEq"), unifier);
		p[13] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[14] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(6), TermList.NIL))), unifier);
		p[15] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier);
		p[16] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(7), TermList.NIL)), StdLib.notEq, "StdLib.notEq"), unifier);
		p[17] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier), 11);
		p[18] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(8), TermList.NIL))), unifier);
		p[19] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(8), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[20][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[19] == null)
		{
			boolean b18changed = false;
			while (b[18] == null)
			{
				boolean b17changed = false;
				while (b[17] == null)
				{
					boolean b16changed = false;
					while (b[16] == null)
					{
						boolean b15changed = false;
						while (b[15] == null)
						{
							boolean b14changed = false;
							while (b[14] == null)
							{
								boolean b13changed = false;
								while (b[13] == null)
								{
									boolean b12changed = false;
									while (b[12] == null)
									{
										boolean b11changed = false;
										while (b[11] == null)
										{
											boolean b10changed = false;
											while (b[10] == null)
											{
												boolean b9changed = false;
												while (b[9] == null)
												{
													boolean b8changed = false;
													while (b[8] == null)
													{
														boolean b7changed = false;
														while (b[7] == null)
														{
															boolean b6changed = false;
															while (b[6] == null)
															{
																boolean b5changed = false;
																while (b[5] == null)
																{
																	boolean b4changed = false;
																	while (b[4] == null)
																	{
																		boolean b3changed = false;
																		while (b[3] == null)
																		{
																			boolean b2changed = false;
																			while (b[2] == null)
																			{
																				boolean b1changed = false;
																				while (b[1] == null)
																				{
																					b[1] = p[1].nextBinding();
																					if (b[1] == null)
																						return null;
																					b1changed = true;
																				}
																				if ( b1changed ) {
																					p[2].reset();
																					p[2].bind(Term.merge(b, 2));
																				}
																				b[2] = p[2].nextBinding();
																				if (b[2] == null) b[1] = null;
																				b2changed = true;
																			}
																			if ( b2changed ) {
																				p[3].reset();
																				p[3].bind(Term.merge(b, 3));
																			}
																			b[3] = p[3].nextBinding();
																			if (b[3] == null) b[2] = null;
																			b3changed = true;
																		}
																		if ( b3changed ) {
																			p[4].reset();
																			p[4].bind(Term.merge(b, 4));
																		}
																		b[4] = p[4].nextBinding();
																		if (b[4] == null) b[3] = null;
																		b4changed = true;
																	}
																	if ( b4changed ) {
																		p[5].reset();
																		p[5].bind(Term.merge(b, 5));
																	}
																	b[5] = p[5].nextBinding();
																	if (b[5] == null) b[4] = null;
																	b5changed = true;
																}
																if ( b5changed ) {
																	p[6].reset();
																	p[6].bind(Term.merge(b, 6));
																}
																b[6] = p[6].nextBinding();
																if (b[6] == null) b[5] = null;
																b6changed = true;
															}
															if ( b6changed ) {
																p[7].reset();
																p[7].bind(Term.merge(b, 7));
															}
															b[7] = p[7].nextBinding();
															if (b[7] == null) b[6] = null;
															b7changed = true;
														}
														if ( b7changed ) {
															p[8].reset();
															p[8].bind(Term.merge(b, 8));
														}
														b[8] = p[8].nextBinding();
														if (b[8] == null) b[7] = null;
														b8changed = true;
													}
													if ( b8changed ) {
														p[9].reset();
														p[9].bind(Term.merge(b, 9));
													}
													b[9] = p[9].nextBinding();
													if (b[9] == null) b[8] = null;
													b9changed = true;
												}
												if ( b9changed ) {
													p[10].reset();
													p[10].bind(Term.merge(b, 10));
												}
												b[10] = p[10].nextBinding();
												if (b[10] == null) b[9] = null;
												b10changed = true;
											}
											if ( b10changed ) {
												p[11].reset();
												p[11].bind(Term.merge(b, 11));
											}
											b[11] = p[11].nextBinding();
											if (b[11] == null) b[10] = null;
											b11changed = true;
										}
										if ( b11changed ) {
											p[12].reset();
											p[12].bind(Term.merge(b, 12));
										}
										b[12] = p[12].nextBinding();
										if (b[12] == null) b[11] = null;
										b12changed = true;
									}
									if ( b12changed ) {
										p[13].reset();
										p[13].bind(Term.merge(b, 13));
									}
									b[13] = p[13].nextBinding();
									if (b[13] == null) b[12] = null;
									b13changed = true;
								}
								if ( b13changed ) {
									p[14].reset();
									p[14].bind(Term.merge(b, 14));
								}
								b[14] = p[14].nextBinding();
								if (b[14] == null) b[13] = null;
								b14changed = true;
							}
							if ( b14changed ) {
								p[15].reset();
								p[15].bind(Term.merge(b, 15));
							}
							b[15] = p[15].nextBinding();
							if (b[15] == null) b[14] = null;
							b15changed = true;
						}
						if ( b15changed ) {
							p[16].reset();
							p[16].bind(Term.merge(b, 16));
						}
						b[16] = p[16].nextBinding();
						if (b[16] == null) b[15] = null;
						b16changed = true;
					}
					if ( b16changed ) {
						p[17].reset();
						p[17].bind(Term.merge(b, 17));
					}
					b[17] = p[17].nextBinding();
					if (b[17] == null) b[16] = null;
					b17changed = true;
				}
				if ( b17changed ) {
					p[18].reset();
					p[18].bind(Term.merge(b, 18));
				}
				b[18] = p[18].nextBinding();
				if (b[18] == null) b[17] = null;
				b18changed = true;
			}
			if ( b18changed ) {
				p[19].reset();
				p[19].bind(Term.merge(b, 19));
			}
			b[19] = p[19].nextBinding();
			if (b[19] == null) b[18] = null;
		}

		Term[] retVal = Term.merge(b, 20);
		b[19] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		p[16].reset();
		p[17].reset();
		p[18].reset();
		p[19].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
	}
}

class Precondition40 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition40(Term[] unifier)
	{
		p = new Precondition[15];
		p[1] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(18), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[2] = new PreconditionAtomic(new Predicate(16, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[4] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 11);
		p[6] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermList.NIL, TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[8] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[9] = new PreconditionAtomic(new Predicate(19, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(5), TermList.NIL)), unifier);
		p[11] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(6), TermList.NIL)), unifier);
		p[12] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), TermList.NIL)), StdLib.notEq, "StdLib.notEq"), unifier);
		p[13] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[14] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(6), TermList.NIL))), unifier);
		b = new Term[15][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[14] == null)
		{
			boolean b13changed = false;
			while (b[13] == null)
			{
				boolean b12changed = false;
				while (b[12] == null)
				{
					boolean b11changed = false;
					while (b[11] == null)
					{
						boolean b10changed = false;
						while (b[10] == null)
						{
							boolean b9changed = false;
							while (b[9] == null)
							{
								boolean b8changed = false;
								while (b[8] == null)
								{
									boolean b7changed = false;
									while (b[7] == null)
									{
										boolean b6changed = false;
										while (b[6] == null)
										{
											boolean b5changed = false;
											while (b[5] == null)
											{
												boolean b4changed = false;
												while (b[4] == null)
												{
													boolean b3changed = false;
													while (b[3] == null)
													{
														boolean b2changed = false;
														while (b[2] == null)
														{
															boolean b1changed = false;
															while (b[1] == null)
															{
																b[1] = p[1].nextBinding();
																if (b[1] == null)
																	return null;
																b1changed = true;
															}
															if ( b1changed ) {
																p[2].reset();
																p[2].bind(Term.merge(b, 2));
															}
															b[2] = p[2].nextBinding();
															if (b[2] == null) b[1] = null;
															b2changed = true;
														}
														if ( b2changed ) {
															p[3].reset();
															p[3].bind(Term.merge(b, 3));
														}
														b[3] = p[3].nextBinding();
														if (b[3] == null) b[2] = null;
														b3changed = true;
													}
													if ( b3changed ) {
														p[4].reset();
														p[4].bind(Term.merge(b, 4));
													}
													b[4] = p[4].nextBinding();
													if (b[4] == null) b[3] = null;
													b4changed = true;
												}
												if ( b4changed ) {
													p[5].reset();
													p[5].bind(Term.merge(b, 5));
												}
												b[5] = p[5].nextBinding();
												if (b[5] == null) b[4] = null;
												b5changed = true;
											}
											if ( b5changed ) {
												p[6].reset();
												p[6].bind(Term.merge(b, 6));
											}
											b[6] = p[6].nextBinding();
											if (b[6] == null) b[5] = null;
											b6changed = true;
										}
										if ( b6changed ) {
											p[7].reset();
											p[7].bind(Term.merge(b, 7));
										}
										b[7] = p[7].nextBinding();
										if (b[7] == null) b[6] = null;
										b7changed = true;
									}
									if ( b7changed ) {
										p[8].reset();
										p[8].bind(Term.merge(b, 8));
									}
									b[8] = p[8].nextBinding();
									if (b[8] == null) b[7] = null;
									b8changed = true;
								}
								if ( b8changed ) {
									p[9].reset();
									p[9].bind(Term.merge(b, 9));
								}
								b[9] = p[9].nextBinding();
								if (b[9] == null) b[8] = null;
								b9changed = true;
							}
							if ( b9changed ) {
								p[10].reset();
								p[10].bind(Term.merge(b, 10));
							}
							b[10] = p[10].nextBinding();
							if (b[10] == null) b[9] = null;
							b10changed = true;
						}
						if ( b10changed ) {
							p[11].reset();
							p[11].bind(Term.merge(b, 11));
						}
						b[11] = p[11].nextBinding();
						if (b[11] == null) b[10] = null;
						b11changed = true;
					}
					if ( b11changed ) {
						p[12].reset();
						p[12].bind(Term.merge(b, 12));
					}
					b[12] = p[12].nextBinding();
					if (b[12] == null) b[11] = null;
					b12changed = true;
				}
				if ( b12changed ) {
					p[13].reset();
					p[13].bind(Term.merge(b, 13));
				}
				b[13] = p[13].nextBinding();
				if (b[13] == null) b[12] = null;
				b13changed = true;
			}
			if ( b13changed ) {
				p[14].reset();
				p[14].bind(Term.merge(b, 14));
			}
			b[14] = p[14].nextBinding();
			if (b[14] == null) b[13] = null;
		}

		Term[] retVal = Term.merge(b, 15);
		b[14] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
	}
}

class Precondition41 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition41(Term[] unifier)
	{
		p = new Precondition[17];
		p[1] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(18), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[2] = new PreconditionAtomic(new Predicate(16, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[4] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 11);
		p[6] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermList.NIL, TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[8] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(9), TermList.NIL)), unifier);
		p[9] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(9), TermList.NIL))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[11] = new PreconditionAtomic(new Predicate(19, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[12] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier);
		p[13] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(7), TermList.NIL)), StdLib.notEq, "StdLib.notEq"), unifier);
		p[14] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier), 11);
		p[15] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(8), TermList.NIL))), unifier);
		p[16] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(8), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[17][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[16] == null)
		{
			boolean b15changed = false;
			while (b[15] == null)
			{
				boolean b14changed = false;
				while (b[14] == null)
				{
					boolean b13changed = false;
					while (b[13] == null)
					{
						boolean b12changed = false;
						while (b[12] == null)
						{
							boolean b11changed = false;
							while (b[11] == null)
							{
								boolean b10changed = false;
								while (b[10] == null)
								{
									boolean b9changed = false;
									while (b[9] == null)
									{
										boolean b8changed = false;
										while (b[8] == null)
										{
											boolean b7changed = false;
											while (b[7] == null)
											{
												boolean b6changed = false;
												while (b[6] == null)
												{
													boolean b5changed = false;
													while (b[5] == null)
													{
														boolean b4changed = false;
														while (b[4] == null)
														{
															boolean b3changed = false;
															while (b[3] == null)
															{
																boolean b2changed = false;
																while (b[2] == null)
																{
																	boolean b1changed = false;
																	while (b[1] == null)
																	{
																		b[1] = p[1].nextBinding();
																		if (b[1] == null)
																			return null;
																		b1changed = true;
																	}
																	if ( b1changed ) {
																		p[2].reset();
																		p[2].bind(Term.merge(b, 2));
																	}
																	b[2] = p[2].nextBinding();
																	if (b[2] == null) b[1] = null;
																	b2changed = true;
																}
																if ( b2changed ) {
																	p[3].reset();
																	p[3].bind(Term.merge(b, 3));
																}
																b[3] = p[3].nextBinding();
																if (b[3] == null) b[2] = null;
																b3changed = true;
															}
															if ( b3changed ) {
																p[4].reset();
																p[4].bind(Term.merge(b, 4));
															}
															b[4] = p[4].nextBinding();
															if (b[4] == null) b[3] = null;
															b4changed = true;
														}
														if ( b4changed ) {
															p[5].reset();
															p[5].bind(Term.merge(b, 5));
														}
														b[5] = p[5].nextBinding();
														if (b[5] == null) b[4] = null;
														b5changed = true;
													}
													if ( b5changed ) {
														p[6].reset();
														p[6].bind(Term.merge(b, 6));
													}
													b[6] = p[6].nextBinding();
													if (b[6] == null) b[5] = null;
													b6changed = true;
												}
												if ( b6changed ) {
													p[7].reset();
													p[7].bind(Term.merge(b, 7));
												}
												b[7] = p[7].nextBinding();
												if (b[7] == null) b[6] = null;
												b7changed = true;
											}
											if ( b7changed ) {
												p[8].reset();
												p[8].bind(Term.merge(b, 8));
											}
											b[8] = p[8].nextBinding();
											if (b[8] == null) b[7] = null;
											b8changed = true;
										}
										if ( b8changed ) {
											p[9].reset();
											p[9].bind(Term.merge(b, 9));
										}
										b[9] = p[9].nextBinding();
										if (b[9] == null) b[8] = null;
										b9changed = true;
									}
									if ( b9changed ) {
										p[10].reset();
										p[10].bind(Term.merge(b, 10));
									}
									b[10] = p[10].nextBinding();
									if (b[10] == null) b[9] = null;
									b10changed = true;
								}
								if ( b10changed ) {
									p[11].reset();
									p[11].bind(Term.merge(b, 11));
								}
								b[11] = p[11].nextBinding();
								if (b[11] == null) b[10] = null;
								b11changed = true;
							}
							if ( b11changed ) {
								p[12].reset();
								p[12].bind(Term.merge(b, 12));
							}
							b[12] = p[12].nextBinding();
							if (b[12] == null) b[11] = null;
							b12changed = true;
						}
						if ( b12changed ) {
							p[13].reset();
							p[13].bind(Term.merge(b, 13));
						}
						b[13] = p[13].nextBinding();
						if (b[13] == null) b[12] = null;
						b13changed = true;
					}
					if ( b13changed ) {
						p[14].reset();
						p[14].bind(Term.merge(b, 14));
					}
					b[14] = p[14].nextBinding();
					if (b[14] == null) b[13] = null;
					b14changed = true;
				}
				if ( b14changed ) {
					p[15].reset();
					p[15].bind(Term.merge(b, 15));
				}
				b[15] = p[15].nextBinding();
				if (b[15] == null) b[14] = null;
				b15changed = true;
			}
			if ( b15changed ) {
				p[16].reset();
				p[16].bind(Term.merge(b, 16));
			}
			b[16] = p[16].nextBinding();
			if (b[16] == null) b[15] = null;
		}

		Term[] retVal = Term.merge(b, 17);
		b[16] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		p[16].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
	}
}

class Precondition42 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition42(Term[] unifier)
	{
		p = new Precondition[12];
		p[1] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(18), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[2] = new PreconditionAtomic(new Predicate(16, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[4] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 11);
		p[6] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(3), new TermList(TermList.NIL, TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[8] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(9), TermList.NIL)), unifier);
		p[9] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(9), TermList.NIL))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[11] = new PreconditionAtomic(new Predicate(19, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		b = new Term[12][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[11] == null)
		{
			boolean b10changed = false;
			while (b[10] == null)
			{
				boolean b9changed = false;
				while (b[9] == null)
				{
					boolean b8changed = false;
					while (b[8] == null)
					{
						boolean b7changed = false;
						while (b[7] == null)
						{
							boolean b6changed = false;
							while (b[6] == null)
							{
								boolean b5changed = false;
								while (b[5] == null)
								{
									boolean b4changed = false;
									while (b[4] == null)
									{
										boolean b3changed = false;
										while (b[3] == null)
										{
											boolean b2changed = false;
											while (b[2] == null)
											{
												boolean b1changed = false;
												while (b[1] == null)
												{
													b[1] = p[1].nextBinding();
													if (b[1] == null)
														return null;
													b1changed = true;
												}
												if ( b1changed ) {
													p[2].reset();
													p[2].bind(Term.merge(b, 2));
												}
												b[2] = p[2].nextBinding();
												if (b[2] == null) b[1] = null;
												b2changed = true;
											}
											if ( b2changed ) {
												p[3].reset();
												p[3].bind(Term.merge(b, 3));
											}
											b[3] = p[3].nextBinding();
											if (b[3] == null) b[2] = null;
											b3changed = true;
										}
										if ( b3changed ) {
											p[4].reset();
											p[4].bind(Term.merge(b, 4));
										}
										b[4] = p[4].nextBinding();
										if (b[4] == null) b[3] = null;
										b4changed = true;
									}
									if ( b4changed ) {
										p[5].reset();
										p[5].bind(Term.merge(b, 5));
									}
									b[5] = p[5].nextBinding();
									if (b[5] == null) b[4] = null;
									b5changed = true;
								}
								if ( b5changed ) {
									p[6].reset();
									p[6].bind(Term.merge(b, 6));
								}
								b[6] = p[6].nextBinding();
								if (b[6] == null) b[5] = null;
								b6changed = true;
							}
							if ( b6changed ) {
								p[7].reset();
								p[7].bind(Term.merge(b, 7));
							}
							b[7] = p[7].nextBinding();
							if (b[7] == null) b[6] = null;
							b7changed = true;
						}
						if ( b7changed ) {
							p[8].reset();
							p[8].bind(Term.merge(b, 8));
						}
						b[8] = p[8].nextBinding();
						if (b[8] == null) b[7] = null;
						b8changed = true;
					}
					if ( b8changed ) {
						p[9].reset();
						p[9].bind(Term.merge(b, 9));
					}
					b[9] = p[9].nextBinding();
					if (b[9] == null) b[8] = null;
					b9changed = true;
				}
				if ( b9changed ) {
					p[10].reset();
					p[10].bind(Term.merge(b, 10));
				}
				b[10] = p[10].nextBinding();
				if (b[10] == null) b[9] = null;
				b10changed = true;
			}
			if ( b10changed ) {
				p[11].reset();
				p[11].bind(Term.merge(b, 11));
			}
			b[11] = p[11].nextBinding();
			if (b[11] == null) b[10] = null;
		}

		Term[] retVal = Term.merge(b, 12);
		b[11] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
	}
}

class Precondition43 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition43(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(10), new TermList(TermVariable.getVariable(3), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition44 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition44(Term[] unifier)
	{
		p = new Precondition[20];
		p[1] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(18), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[2] = new PreconditionAtomic(new Predicate(16, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[4] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 11);
		p[6] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[8] = new PreconditionAtomic(new Predicate(19, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[9] = new PreconditionForAll(new Precondition43(unifier), new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier), 11);
		p[10] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(5), TermList.NIL)), unifier);
		p[11] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(6), TermList.NIL)), unifier);
		p[12] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), TermList.NIL)), StdLib.notEq, "StdLib.notEq"), unifier);
		p[13] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[14] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(6), TermList.NIL))), unifier);
		p[15] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier);
		p[16] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(7), TermList.NIL)), StdLib.notEq, "StdLib.notEq"), unifier);
		p[17] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier), 11);
		p[18] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(8), TermList.NIL))), unifier);
		p[19] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(8), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[20][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[19] == null)
		{
			boolean b18changed = false;
			while (b[18] == null)
			{
				boolean b17changed = false;
				while (b[17] == null)
				{
					boolean b16changed = false;
					while (b[16] == null)
					{
						boolean b15changed = false;
						while (b[15] == null)
						{
							boolean b14changed = false;
							while (b[14] == null)
							{
								boolean b13changed = false;
								while (b[13] == null)
								{
									boolean b12changed = false;
									while (b[12] == null)
									{
										boolean b11changed = false;
										while (b[11] == null)
										{
											boolean b10changed = false;
											while (b[10] == null)
											{
												boolean b9changed = false;
												while (b[9] == null)
												{
													boolean b8changed = false;
													while (b[8] == null)
													{
														boolean b7changed = false;
														while (b[7] == null)
														{
															boolean b6changed = false;
															while (b[6] == null)
															{
																boolean b5changed = false;
																while (b[5] == null)
																{
																	boolean b4changed = false;
																	while (b[4] == null)
																	{
																		boolean b3changed = false;
																		while (b[3] == null)
																		{
																			boolean b2changed = false;
																			while (b[2] == null)
																			{
																				boolean b1changed = false;
																				while (b[1] == null)
																				{
																					b[1] = p[1].nextBinding();
																					if (b[1] == null)
																						return null;
																					b1changed = true;
																				}
																				if ( b1changed ) {
																					p[2].reset();
																					p[2].bind(Term.merge(b, 2));
																				}
																				b[2] = p[2].nextBinding();
																				if (b[2] == null) b[1] = null;
																				b2changed = true;
																			}
																			if ( b2changed ) {
																				p[3].reset();
																				p[3].bind(Term.merge(b, 3));
																			}
																			b[3] = p[3].nextBinding();
																			if (b[3] == null) b[2] = null;
																			b3changed = true;
																		}
																		if ( b3changed ) {
																			p[4].reset();
																			p[4].bind(Term.merge(b, 4));
																		}
																		b[4] = p[4].nextBinding();
																		if (b[4] == null) b[3] = null;
																		b4changed = true;
																	}
																	if ( b4changed ) {
																		p[5].reset();
																		p[5].bind(Term.merge(b, 5));
																	}
																	b[5] = p[5].nextBinding();
																	if (b[5] == null) b[4] = null;
																	b5changed = true;
																}
																if ( b5changed ) {
																	p[6].reset();
																	p[6].bind(Term.merge(b, 6));
																}
																b[6] = p[6].nextBinding();
																if (b[6] == null) b[5] = null;
																b6changed = true;
															}
															if ( b6changed ) {
																p[7].reset();
																p[7].bind(Term.merge(b, 7));
															}
															b[7] = p[7].nextBinding();
															if (b[7] == null) b[6] = null;
															b7changed = true;
														}
														if ( b7changed ) {
															p[8].reset();
															p[8].bind(Term.merge(b, 8));
														}
														b[8] = p[8].nextBinding();
														if (b[8] == null) b[7] = null;
														b8changed = true;
													}
													if ( b8changed ) {
														p[9].reset();
														p[9].bind(Term.merge(b, 9));
													}
													b[9] = p[9].nextBinding();
													if (b[9] == null) b[8] = null;
													b9changed = true;
												}
												if ( b9changed ) {
													p[10].reset();
													p[10].bind(Term.merge(b, 10));
												}
												b[10] = p[10].nextBinding();
												if (b[10] == null) b[9] = null;
												b10changed = true;
											}
											if ( b10changed ) {
												p[11].reset();
												p[11].bind(Term.merge(b, 11));
											}
											b[11] = p[11].nextBinding();
											if (b[11] == null) b[10] = null;
											b11changed = true;
										}
										if ( b11changed ) {
											p[12].reset();
											p[12].bind(Term.merge(b, 12));
										}
										b[12] = p[12].nextBinding();
										if (b[12] == null) b[11] = null;
										b12changed = true;
									}
									if ( b12changed ) {
										p[13].reset();
										p[13].bind(Term.merge(b, 13));
									}
									b[13] = p[13].nextBinding();
									if (b[13] == null) b[12] = null;
									b13changed = true;
								}
								if ( b13changed ) {
									p[14].reset();
									p[14].bind(Term.merge(b, 14));
								}
								b[14] = p[14].nextBinding();
								if (b[14] == null) b[13] = null;
								b14changed = true;
							}
							if ( b14changed ) {
								p[15].reset();
								p[15].bind(Term.merge(b, 15));
							}
							b[15] = p[15].nextBinding();
							if (b[15] == null) b[14] = null;
							b15changed = true;
						}
						if ( b15changed ) {
							p[16].reset();
							p[16].bind(Term.merge(b, 16));
						}
						b[16] = p[16].nextBinding();
						if (b[16] == null) b[15] = null;
						b16changed = true;
					}
					if ( b16changed ) {
						p[17].reset();
						p[17].bind(Term.merge(b, 17));
					}
					b[17] = p[17].nextBinding();
					if (b[17] == null) b[16] = null;
					b17changed = true;
				}
				if ( b17changed ) {
					p[18].reset();
					p[18].bind(Term.merge(b, 18));
				}
				b[18] = p[18].nextBinding();
				if (b[18] == null) b[17] = null;
				b18changed = true;
			}
			if ( b18changed ) {
				p[19].reset();
				p[19].bind(Term.merge(b, 19));
			}
			b[19] = p[19].nextBinding();
			if (b[19] == null) b[18] = null;
		}

		Term[] retVal = Term.merge(b, 20);
		b[19] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		p[16].reset();
		p[17].reset();
		p[18].reset();
		p[19].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
		b[17] = null;
		b[18] = null;
		b[19] = null;
	}
}

class Precondition45 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition45(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(10), new TermList(TermVariable.getVariable(3), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition46 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition46(Term[] unifier)
	{
		p = new Precondition[15];
		p[1] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(18), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[2] = new PreconditionAtomic(new Predicate(16, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[4] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 11);
		p[6] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[8] = new PreconditionAtomic(new Predicate(19, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[9] = new PreconditionForAll(new Precondition45(unifier), new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier), 11);
		p[10] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(5), TermList.NIL)), unifier);
		p[11] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(6), TermList.NIL)), unifier);
		p[12] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), TermList.NIL)), StdLib.notEq, "StdLib.notEq"), unifier);
		p[13] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(5), TermList.NIL))), unifier);
		p[14] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(6), TermList.NIL))), unifier);
		b = new Term[15][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[14] == null)
		{
			boolean b13changed = false;
			while (b[13] == null)
			{
				boolean b12changed = false;
				while (b[12] == null)
				{
					boolean b11changed = false;
					while (b[11] == null)
					{
						boolean b10changed = false;
						while (b[10] == null)
						{
							boolean b9changed = false;
							while (b[9] == null)
							{
								boolean b8changed = false;
								while (b[8] == null)
								{
									boolean b7changed = false;
									while (b[7] == null)
									{
										boolean b6changed = false;
										while (b[6] == null)
										{
											boolean b5changed = false;
											while (b[5] == null)
											{
												boolean b4changed = false;
												while (b[4] == null)
												{
													boolean b3changed = false;
													while (b[3] == null)
													{
														boolean b2changed = false;
														while (b[2] == null)
														{
															boolean b1changed = false;
															while (b[1] == null)
															{
																b[1] = p[1].nextBinding();
																if (b[1] == null)
																	return null;
																b1changed = true;
															}
															if ( b1changed ) {
																p[2].reset();
																p[2].bind(Term.merge(b, 2));
															}
															b[2] = p[2].nextBinding();
															if (b[2] == null) b[1] = null;
															b2changed = true;
														}
														if ( b2changed ) {
															p[3].reset();
															p[3].bind(Term.merge(b, 3));
														}
														b[3] = p[3].nextBinding();
														if (b[3] == null) b[2] = null;
														b3changed = true;
													}
													if ( b3changed ) {
														p[4].reset();
														p[4].bind(Term.merge(b, 4));
													}
													b[4] = p[4].nextBinding();
													if (b[4] == null) b[3] = null;
													b4changed = true;
												}
												if ( b4changed ) {
													p[5].reset();
													p[5].bind(Term.merge(b, 5));
												}
												b[5] = p[5].nextBinding();
												if (b[5] == null) b[4] = null;
												b5changed = true;
											}
											if ( b5changed ) {
												p[6].reset();
												p[6].bind(Term.merge(b, 6));
											}
											b[6] = p[6].nextBinding();
											if (b[6] == null) b[5] = null;
											b6changed = true;
										}
										if ( b6changed ) {
											p[7].reset();
											p[7].bind(Term.merge(b, 7));
										}
										b[7] = p[7].nextBinding();
										if (b[7] == null) b[6] = null;
										b7changed = true;
									}
									if ( b7changed ) {
										p[8].reset();
										p[8].bind(Term.merge(b, 8));
									}
									b[8] = p[8].nextBinding();
									if (b[8] == null) b[7] = null;
									b8changed = true;
								}
								if ( b8changed ) {
									p[9].reset();
									p[9].bind(Term.merge(b, 9));
								}
								b[9] = p[9].nextBinding();
								if (b[9] == null) b[8] = null;
								b9changed = true;
							}
							if ( b9changed ) {
								p[10].reset();
								p[10].bind(Term.merge(b, 10));
							}
							b[10] = p[10].nextBinding();
							if (b[10] == null) b[9] = null;
							b10changed = true;
						}
						if ( b10changed ) {
							p[11].reset();
							p[11].bind(Term.merge(b, 11));
						}
						b[11] = p[11].nextBinding();
						if (b[11] == null) b[10] = null;
						b11changed = true;
					}
					if ( b11changed ) {
						p[12].reset();
						p[12].bind(Term.merge(b, 12));
					}
					b[12] = p[12].nextBinding();
					if (b[12] == null) b[11] = null;
					b12changed = true;
				}
				if ( b12changed ) {
					p[13].reset();
					p[13].bind(Term.merge(b, 13));
				}
				b[13] = p[13].nextBinding();
				if (b[13] == null) b[12] = null;
				b13changed = true;
			}
			if ( b13changed ) {
				p[14].reset();
				p[14].bind(Term.merge(b, 14));
			}
			b[14] = p[14].nextBinding();
			if (b[14] == null) b[13] = null;
		}

		Term[] retVal = Term.merge(b, 15);
		b[14] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
	}
}

class Precondition47 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition47(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(10), new TermList(TermVariable.getVariable(3), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition48 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition48(Term[] unifier)
	{
		p = new Precondition[17];
		p[1] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(18), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[2] = new PreconditionAtomic(new Predicate(16, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[4] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 11);
		p[6] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionForAll(new Precondition47(unifier), new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier), 11);
		p[8] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(9), TermList.NIL)), unifier);
		p[9] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(9), TermList.NIL))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[11] = new PreconditionAtomic(new Predicate(19, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		p[12] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier);
		p[13] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(7), TermList.NIL)), StdLib.notEq, "StdLib.notEq"), unifier);
		p[14] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier), 11);
		p[15] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(7), new TermList(TermVariable.getVariable(8), TermList.NIL))), unifier);
		p[16] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(8), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[17][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[16] == null)
		{
			boolean b15changed = false;
			while (b[15] == null)
			{
				boolean b14changed = false;
				while (b[14] == null)
				{
					boolean b13changed = false;
					while (b[13] == null)
					{
						boolean b12changed = false;
						while (b[12] == null)
						{
							boolean b11changed = false;
							while (b[11] == null)
							{
								boolean b10changed = false;
								while (b[10] == null)
								{
									boolean b9changed = false;
									while (b[9] == null)
									{
										boolean b8changed = false;
										while (b[8] == null)
										{
											boolean b7changed = false;
											while (b[7] == null)
											{
												boolean b6changed = false;
												while (b[6] == null)
												{
													boolean b5changed = false;
													while (b[5] == null)
													{
														boolean b4changed = false;
														while (b[4] == null)
														{
															boolean b3changed = false;
															while (b[3] == null)
															{
																boolean b2changed = false;
																while (b[2] == null)
																{
																	boolean b1changed = false;
																	while (b[1] == null)
																	{
																		b[1] = p[1].nextBinding();
																		if (b[1] == null)
																			return null;
																		b1changed = true;
																	}
																	if ( b1changed ) {
																		p[2].reset();
																		p[2].bind(Term.merge(b, 2));
																	}
																	b[2] = p[2].nextBinding();
																	if (b[2] == null) b[1] = null;
																	b2changed = true;
																}
																if ( b2changed ) {
																	p[3].reset();
																	p[3].bind(Term.merge(b, 3));
																}
																b[3] = p[3].nextBinding();
																if (b[3] == null) b[2] = null;
																b3changed = true;
															}
															if ( b3changed ) {
																p[4].reset();
																p[4].bind(Term.merge(b, 4));
															}
															b[4] = p[4].nextBinding();
															if (b[4] == null) b[3] = null;
															b4changed = true;
														}
														if ( b4changed ) {
															p[5].reset();
															p[5].bind(Term.merge(b, 5));
														}
														b[5] = p[5].nextBinding();
														if (b[5] == null) b[4] = null;
														b5changed = true;
													}
													if ( b5changed ) {
														p[6].reset();
														p[6].bind(Term.merge(b, 6));
													}
													b[6] = p[6].nextBinding();
													if (b[6] == null) b[5] = null;
													b6changed = true;
												}
												if ( b6changed ) {
													p[7].reset();
													p[7].bind(Term.merge(b, 7));
												}
												b[7] = p[7].nextBinding();
												if (b[7] == null) b[6] = null;
												b7changed = true;
											}
											if ( b7changed ) {
												p[8].reset();
												p[8].bind(Term.merge(b, 8));
											}
											b[8] = p[8].nextBinding();
											if (b[8] == null) b[7] = null;
											b8changed = true;
										}
										if ( b8changed ) {
											p[9].reset();
											p[9].bind(Term.merge(b, 9));
										}
										b[9] = p[9].nextBinding();
										if (b[9] == null) b[8] = null;
										b9changed = true;
									}
									if ( b9changed ) {
										p[10].reset();
										p[10].bind(Term.merge(b, 10));
									}
									b[10] = p[10].nextBinding();
									if (b[10] == null) b[9] = null;
									b10changed = true;
								}
								if ( b10changed ) {
									p[11].reset();
									p[11].bind(Term.merge(b, 11));
								}
								b[11] = p[11].nextBinding();
								if (b[11] == null) b[10] = null;
								b11changed = true;
							}
							if ( b11changed ) {
								p[12].reset();
								p[12].bind(Term.merge(b, 12));
							}
							b[12] = p[12].nextBinding();
							if (b[12] == null) b[11] = null;
							b12changed = true;
						}
						if ( b12changed ) {
							p[13].reset();
							p[13].bind(Term.merge(b, 13));
						}
						b[13] = p[13].nextBinding();
						if (b[13] == null) b[12] = null;
						b13changed = true;
					}
					if ( b13changed ) {
						p[14].reset();
						p[14].bind(Term.merge(b, 14));
					}
					b[14] = p[14].nextBinding();
					if (b[14] == null) b[13] = null;
					b14changed = true;
				}
				if ( b14changed ) {
					p[15].reset();
					p[15].bind(Term.merge(b, 15));
				}
				b[15] = p[15].nextBinding();
				if (b[15] == null) b[14] = null;
				b15changed = true;
			}
			if ( b15changed ) {
				p[16].reset();
				p[16].bind(Term.merge(b, 16));
			}
			b[16] = p[16].nextBinding();
			if (b[16] == null) b[15] = null;
		}

		Term[] retVal = Term.merge(b, 17);
		b[16] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		p[12].reset();
		p[13].reset();
		p[14].reset();
		p[15].reset();
		p[16].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
		b[12] = null;
		b[13] = null;
		b[14] = null;
		b[15] = null;
		b[16] = null;
	}
}

class Precondition49 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition49(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier);
		p[2] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(10), new TermList(TermVariable.getVariable(3), TermList.NIL)), StdLib.member, "StdLib.member"), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Precondition50 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition50(Term[] unifier)
	{
		p = new Precondition[12];
		p[1] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(18), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[2] = new PreconditionAtomic(new Predicate(16, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[4] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 11);
		p[6] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionForAll(new Precondition49(unifier), new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier), 11);
		p[8] = new PreconditionAtomic(new Predicate(0, 11, new TermList(TermVariable.getVariable(9), TermList.NIL)), unifier);
		p[9] = new PreconditionAtomic(new Predicate(10, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(9), TermList.NIL))), unifier);
		p[10] = new PreconditionAtomic(new Predicate(6, 11, new TermList(TermVariable.getVariable(4), TermList.NIL)), unifier);
		p[11] = new PreconditionAtomic(new Predicate(19, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(4), TermList.NIL))), unifier);
		b = new Term[12][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[11] == null)
		{
			boolean b10changed = false;
			while (b[10] == null)
			{
				boolean b9changed = false;
				while (b[9] == null)
				{
					boolean b8changed = false;
					while (b[8] == null)
					{
						boolean b7changed = false;
						while (b[7] == null)
						{
							boolean b6changed = false;
							while (b[6] == null)
							{
								boolean b5changed = false;
								while (b[5] == null)
								{
									boolean b4changed = false;
									while (b[4] == null)
									{
										boolean b3changed = false;
										while (b[3] == null)
										{
											boolean b2changed = false;
											while (b[2] == null)
											{
												boolean b1changed = false;
												while (b[1] == null)
												{
													b[1] = p[1].nextBinding();
													if (b[1] == null)
														return null;
													b1changed = true;
												}
												if ( b1changed ) {
													p[2].reset();
													p[2].bind(Term.merge(b, 2));
												}
												b[2] = p[2].nextBinding();
												if (b[2] == null) b[1] = null;
												b2changed = true;
											}
											if ( b2changed ) {
												p[3].reset();
												p[3].bind(Term.merge(b, 3));
											}
											b[3] = p[3].nextBinding();
											if (b[3] == null) b[2] = null;
											b3changed = true;
										}
										if ( b3changed ) {
											p[4].reset();
											p[4].bind(Term.merge(b, 4));
										}
										b[4] = p[4].nextBinding();
										if (b[4] == null) b[3] = null;
										b4changed = true;
									}
									if ( b4changed ) {
										p[5].reset();
										p[5].bind(Term.merge(b, 5));
									}
									b[5] = p[5].nextBinding();
									if (b[5] == null) b[4] = null;
									b5changed = true;
								}
								if ( b5changed ) {
									p[6].reset();
									p[6].bind(Term.merge(b, 6));
								}
								b[6] = p[6].nextBinding();
								if (b[6] == null) b[5] = null;
								b6changed = true;
							}
							if ( b6changed ) {
								p[7].reset();
								p[7].bind(Term.merge(b, 7));
							}
							b[7] = p[7].nextBinding();
							if (b[7] == null) b[6] = null;
							b7changed = true;
						}
						if ( b7changed ) {
							p[8].reset();
							p[8].bind(Term.merge(b, 8));
						}
						b[8] = p[8].nextBinding();
						if (b[8] == null) b[7] = null;
						b8changed = true;
					}
					if ( b8changed ) {
						p[9].reset();
						p[9].bind(Term.merge(b, 9));
					}
					b[9] = p[9].nextBinding();
					if (b[9] == null) b[8] = null;
					b9changed = true;
				}
				if ( b9changed ) {
					p[10].reset();
					p[10].bind(Term.merge(b, 10));
				}
				b[10] = p[10].nextBinding();
				if (b[10] == null) b[9] = null;
				b10changed = true;
			}
			if ( b10changed ) {
				p[11].reset();
				p[11].bind(Term.merge(b, 11));
			}
			b[11] = p[11].nextBinding();
			if (b[11] == null) b[10] = null;
		}

		Term[] retVal = Term.merge(b, 12);
		b[11] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		p[7].reset();
		p[8].reset();
		p[9].reset();
		p[10].reset();
		p[11].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
		b[7] = null;
		b[8] = null;
		b[9] = null;
		b[10] = null;
		b[11] = null;
	}
}

class Precondition51 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition51(Term[] unifier)
	{
		p = new Precondition[3];
		p[1] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(18), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[2] = new PreconditionAtomic(new Predicate(16, 11, new TermList(new TermNumber(0.0), TermList.NIL)), unifier);
		b = new Term[3][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[2] == null)
		{
			boolean b1changed = false;
			while (b[1] == null)
			{
				b[1] = p[1].nextBinding();
				if (b[1] == null)
					return null;
				b1changed = true;
			}
			if ( b1changed ) {
				p[2].reset();
				p[2].bind(Term.merge(b, 2));
			}
			b[2] = p[2].nextBinding();
			if (b[2] == null) b[1] = null;
		}

		Term[] retVal = Term.merge(b, 3);
		b[2] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		b[1] = null;
		b[2] = null;
	}
}

class Method16 extends Method
{
	public Method16()
	{
		super(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)));
		TaskList[] subsIn = new TaskList[9];

		subsIn[0] = createTaskList0();
		subsIn[1] = createTaskList1();
		subsIn[2] = createTaskList2();
		subsIn[3] = createTaskList3();
		subsIn[4] = createTaskList4();
		subsIn[5] = createTaskList5();
		subsIn[6] = createTaskList6();
		subsIn[7] = createTaskList7();
		subsIn[8] = createTaskList8();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(6, 11, new TermList(TermConstant.getConstant(18), new TermList(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), new TermList(new TermNumber(100.0), TermList.NIL))))))), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, false));

		return retVal;
	}

	TaskList createTaskList1()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(6, 11, new TermList(TermConstant.getConstant(18), new TermList(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), new TermList(new TermNumber(100.0), TermList.NIL))))))), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, false));

		return retVal;
	}

	TaskList createTaskList2()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(6, 11, new TermList(TermConstant.getConstant(18), new TermList(TermVariable.getVariable(9), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), new TermList(new TermNumber(100.0), TermList.NIL)))))), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, false));

		return retVal;
	}

	TaskList createTaskList3()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(6, 11, new TermList(TermConstant.getConstant(18), new TermList(TermVariable.getVariable(9), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), new TermList(new TermNumber(100.0), TermList.NIL)))))), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, false));

		return retVal;
	}

	TaskList createTaskList4()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(6, 11, new TermList(TermConstant.getConstant(18), new TermList(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), new TermList(new TermNumber(100.0), TermList.NIL))))))), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, false));

		return retVal;
	}

	TaskList createTaskList5()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(6, 11, new TermList(TermConstant.getConstant(18), new TermList(TermVariable.getVariable(5), new TermList(TermVariable.getVariable(6), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), new TermList(new TermNumber(100.0), TermList.NIL))))))), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, false));

		return retVal;
	}

	TaskList createTaskList6()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(6, 11, new TermList(TermConstant.getConstant(18), new TermList(TermVariable.getVariable(9), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), new TermList(new TermNumber(100.0), TermList.NIL)))))), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, false));

		return retVal;
	}

	TaskList createTaskList7()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(6, 11, new TermList(TermConstant.getConstant(18), new TermList(TermVariable.getVariable(9), new TermList(TermVariable.getVariable(4), new TermList(TermVariable.getVariable(2), new TermList(new TermNumber(100.0), TermList.NIL)))))), false, false));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(2, 11, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, false));

		return retVal;
	}

	TaskList createTaskList8()
	{
		TaskList retVal;

		retVal = new TaskList(1, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(8, 11, TermList.NIL), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new Precondition39(unifier)).setComparator(null);
			break;
			case 1:
				p = (new Precondition40(unifier)).setComparator(null);
			break;
			case 2:
				p = (new Precondition41(unifier)).setComparator(null);
			break;
			case 3:
				p = (new Precondition42(unifier)).setComparator(null);
			break;
			case 4:
				p = (new Precondition44(unifier)).setComparator(null);
			break;
			case 5:
				p = (new Precondition46(unifier)).setComparator(null);
			break;
			case 6:
				p = (new Precondition48(unifier)).setComparator(null);
			break;
			case 7:
				p = (new Precondition50(unifier)).setComparator(null);
			break;
			case 8:
				p = (new Precondition51(unifier)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method16Branch0";
			case 1: return "Method16Branch1";
			case 2: return "Method16Branch2";
			case 3: return "Method16Branch3";
			case 4: return "Method16Branch4";
			case 5: return "Method16Branch5";
			case 6: return "Method16Branch6";
			case 7: return "Method16Branch7";
			case 8: return "Method16Branch8";
			default: return null;
		}
	}
}

public class quadrotor extends Domain
{
	public static Ceil calculateCeil = new Ceil();

	public static Max calculateMax = new Max();

	public static MoveCost calculateMoveCost = new MoveCost();

	public static LastWriteTime calculateLastWriteTime = new LastWriteTime();

	public quadrotor()
	{
		TermVariable.initialize(17);

		constants = new String[20];
		constants[0] = "quadrotor";
		constants[1] = "landed";
		constants[2] = "battery";
		constants[3] = "read-time";
		constants[4] = "write-time";
		constants[5] = "hovering";
		constants[6] = "location";
		constants[7] = "at";
		constants[8] = "subarea";
		constants[9] = "unassigned";
		constants[10] = "assigned";
		constants[11] = "object";
		constants[12] = "transported";
		constants[13] = "transporting";
		constants[14] = "depends";
		constants[15] = "done_with";
		constants[16] = "remaining_tasks";
		constants[17] = "unused";
		constants[18] = "assemble";
		constants[19] = "assembly_location";

		compoundTasks = new String[11];
		compoundTasks[0] = "move";
		compoundTasks[1] = "try_mission";
		compoundTasks[2] = "mission";
		compoundTasks[3] = "synchro_wait";
		compoundTasks[4] = "pick_object";
		compoundTasks[5] = "try_synchro_mission";
		compoundTasks[6] = "synchro_mission";
		compoundTasks[7] = "synchro_wait_v";
		compoundTasks[8] = "takeoff";
		compoundTasks[9] = "move_mission";
		compoundTasks[10] = "land";

		primitiveTasks = new String[11];
		primitiveTasks[0] = "!takeoff";
		primitiveTasks[1] = "!land";
		primitiveTasks[2] = "!do_move";
		primitiveTasks[3] = "!do_mission";
		primitiveTasks[4] = "!do_synchro_mission";
		primitiveTasks[5] = "!pick_object";
		primitiveTasks[6] = "!synchro_wait";
		primitiveTasks[7] = "!sync";
		primitiveTasks[8] = "!assembly_plan_finished";
		primitiveTasks[9] = "!first_branch";
		primitiveTasks[10] = "!second_branch";

		methods = new Method[11][];

		methods[0] = new Method[2];
		methods[0][0] = new Method8();
		methods[0][1] = new Method9();

		methods[1] = new Method[1];
		methods[1][0] = new Method3();

		methods[2] = new Method[2];
		methods[2][0] = new Method0();
		methods[2][1] = new Method16();

		methods[3] = new Method[1];
		methods[3][0] = new Method14();

		methods[4] = new Method[2];
		methods[4][0] = new Method6();
		methods[4][1] = new Method7();

		methods[5] = new Method[2];
		methods[5][0] = new Method4();
		methods[5][1] = new Method5();

		methods[6] = new Method[2];
		methods[6][0] = new Method1();
		methods[6][1] = new Method2();

		methods[7] = new Method[1];
		methods[7][0] = new Method15();

		methods[8] = new Method[1];
		methods[8][0] = new Method12();

		methods[9] = new Method[2];
		methods[9][0] = new Method10();
		methods[9][1] = new Method11();

		methods[10] = new Method[1];
		methods[10][0] = new Method13();


		ops = new Operator[11][];

		ops[0] = new Operator[1];
		ops[0][0] = new Operator0();

		ops[1] = new Operator[1];
		ops[1][0] = new Operator1();

		ops[2] = new Operator[2];
		ops[2][0] = new Operator2();
		ops[2][1] = new Operator3();

		ops[3] = new Operator[1];
		ops[3][0] = new Operator4();

		ops[4] = new Operator[2];
		ops[4][0] = new Operator5();
		ops[4][1] = new Operator6();

		ops[5] = new Operator[2];
		ops[5][0] = new Operator7();
		ops[5][1] = new Operator8();

		ops[6] = new Operator[1];
		ops[6][0] = new Operator9();

		ops[7] = new Operator[1];
		ops[7][0] = new Operator10();

		ops[8] = new Operator[1];
		ops[8][0] = new Operator11();

		ops[9] = new Operator[1];
		ops[9][0] = new Operator12();

		ops[10] = new Operator[1];
		ops[10][0] = new Operator13();

		axioms = new Axiom[20][];

		axioms[0] = new Axiom[0];

		axioms[1] = new Axiom[0];

		axioms[2] = new Axiom[0];

		axioms[3] = new Axiom[0];

		axioms[4] = new Axiom[0];

		axioms[5] = new Axiom[0];

		axioms[6] = new Axiom[0];

		axioms[7] = new Axiom[0];

		axioms[8] = new Axiom[0];

		axioms[9] = new Axiom[0];

		axioms[10] = new Axiom[0];

		axioms[11] = new Axiom[0];

		axioms[12] = new Axiom[0];

		axioms[13] = new Axiom[0];

		axioms[14] = new Axiom[0];

		axioms[15] = new Axiom[0];

		axioms[16] = new Axiom[0];

		axioms[17] = new Axiom[0];

		axioms[18] = new Axiom[0];

		axioms[19] = new Axiom[0];

	}
}