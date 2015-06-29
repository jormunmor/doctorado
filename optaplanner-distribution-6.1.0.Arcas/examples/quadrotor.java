import JSHOP2.*;

class Precondition52 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition52(Term[] unifier)
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

class Operator14 extends Operator
{
	public Operator14()
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

		p = (new Precondition52(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition53 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition53(Term[] unifier)
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

class Operator15 extends Operator
{
	public Operator15()
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

		p = (new Precondition53(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition54 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition54(Term[] unifier)
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

class Operator16 extends Operator
{
	public Operator16()
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

		p = (new Precondition54(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition55 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition55(Term[] unifier)
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

class Operator17 extends Operator
{
	public Operator17()
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

		p = (new Precondition55(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition56 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition56(Term[] unifier)
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

class Operator18 extends Operator
{
	public Operator18()
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

		p = (new Precondition56(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition57 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition57(Term[] unifier)
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

class Precondition58 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition58(Term[] unifier)
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
		p[10] = new PreconditionForAll(new Precondition57(unifier), new PreconditionAtomic(new Predicate(15, 14, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier), 14);
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

class Operator19 extends Operator
{
	public Operator19()
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

		p = (new Precondition58(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition59 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition59(Term[] unifier)
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

class Precondition60 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition60(Term[] unifier)
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
		p[15] = new PreconditionForAll(new Precondition59(unifier), new PreconditionAtomic(new Predicate(15, 16, new TermList(TermVariable.getVariable(8), TermList.NIL)), unifier), 16);
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

class Operator20 extends Operator
{
	public Operator20()
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

		p = (new Precondition60(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition61 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition61(Term[] unifier)
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

class Operator21 extends Operator
{
	public Operator21()
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

		p = (new Precondition61(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition62 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition62(Term[] unifier)
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

class Operator22 extends Operator
{
	public Operator22()
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

		p = (new Precondition62(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition63 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition63(Term[] unifier)
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

class Operator23 extends Operator
{
	public Operator23()
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

		p = (new Precondition63(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition64 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition64(Term[] unifier)
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

class Operator24 extends Operator
{
	public Operator24()
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

		p = (new Precondition64(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Operator25 extends Operator
{
	public Operator25()
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

class Operator26 extends Operator
{
	public Operator26()
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

class Operator27 extends Operator
{
	public Operator27()
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

class Precondition65 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition65(Term[] unifier)
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

class Precondition66 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition66(Term[] unifier)
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

class Method17 extends Method
{
	public Method17()
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
				p = (new Precondition65(unifier)).setComparator(null);
			break;
			case 1:
				p = (new Precondition66(unifier)).setComparator(null);
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

class Precondition67 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition67(Term[] unifier)
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

class Precondition68 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition68(Term[] unifier)
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
		p[10] = new PreconditionForAll(new Precondition67(unifier), new PreconditionAtomic(new Predicate(15, 8, new TermList(TermVariable.getVariable(7), TermList.NIL)), unifier), 8);
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

class Method18 extends Method
{
	public Method18()
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
				p = (new Precondition68(unifier)).setComparator(null);
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

class Precondition69 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition69(Term[] unifier)
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

class Precondition70 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition70(Term[] unifier)
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
		p[12] = new PreconditionForAll(new Precondition69(unifier), new PreconditionAtomic(new Predicate(15, 9, new TermList(TermVariable.getVariable(8), TermList.NIL)), unifier), 9);
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

class Method19 extends Method
{
	public Method19()
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
				p = (new Precondition70(unifier)).setComparator(null);
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

class Precondition71 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition71(Term[] unifier)
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

class Method20 extends Method
{
	public Method20()
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
				p = (new Precondition71(unifier)).setComparator(null);
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

class Precondition72 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition72(Term[] unifier)
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

class Precondition73 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition73(Term[] unifier)
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
		p[10] = new PreconditionForAll(new Precondition72(unifier), new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(5), TermList.NIL)), unifier), 11);
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

class Method21 extends Method
{
	public Method21()
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
				p = (new Precondition73(unifier)).setComparator(null);
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

class Precondition74 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition74(Term[] unifier)
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

class Precondition75 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition75(Term[] unifier)
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
		p[15] = new PreconditionForAll(new Precondition74(unifier), new PreconditionAtomic(new Predicate(15, 13, new TermList(TermVariable.getVariable(6), TermList.NIL)), unifier), 13);
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

class Method22 extends Method
{
	public Method22()
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
				p = (new Precondition75(unifier)).setComparator(null);
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

class Precondition76 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition76(Term[] unifier)
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

class Method23 extends Method
{
	public Method23()
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
				p = (new Precondition76(unifier)).setComparator(null);
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

class Precondition77 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition77(Term[] unifier)
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

class Method24 extends Method
{
	public Method24()
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
				p = (new Precondition77(unifier)).setComparator(null);
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

class Precondition78 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition78(Term[] unifier)
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

class Precondition79 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition79(Term[] unifier)
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

class Method25 extends Method
{
	public Method25()
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
				p = (new Precondition78(unifier)).setComparator(null);
			break;
			case 1:
				p = (new Precondition79(unifier)).setComparator(null);
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

class Precondition80 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition80(Term[] unifier)
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

class Method26 extends Method
{
	public Method26()
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
				p = (new Precondition80(unifier)).setComparator(null);
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

class Precondition81 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition81(Term[] unifier)
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

class Method27 extends Method
{
	public Method27()
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
				p = (new Precondition81(unifier)).setComparator(null);
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

class Precondition82 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition82(Term[] unifier)
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

class Method28 extends Method
{
	public Method28()
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
				p = (new Precondition82(unifier)).setComparator(null);
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

class Precondition83 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition83(Term[] unifier)
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

class Method29 extends Method
{
	public Method29()
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
				p = (new Precondition83(unifier)).setComparator(null);
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

class Precondition84 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition84(Term[] unifier)
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

class Method30 extends Method
{
	public Method30()
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
				p = (new Precondition84(unifier)).setComparator(null);
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

class Precondition85 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition85(Term[] unifier)
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

class Precondition86 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition86(Term[] unifier)
	{
		p = new Precondition[13];
		p[1] = new PreconditionAtomic(new Predicate(0, 10, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(11, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier), 10);
		p[4] = new PreconditionAtomic(new Predicate(14, 10, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[5] = new PreconditionForAll(new Precondition85(unifier), new PreconditionAtomic(new Predicate(15, 10, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier), 10);
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

class Precondition87 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition87(Term[] unifier)
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

class Precondition88 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition88(Term[] unifier)
	{
		p = new Precondition[13];
		p[1] = new PreconditionAtomic(new Predicate(0, 10, new TermList(TermVariable.getVariable(0), TermList.NIL)), unifier);
		p[2] = new PreconditionAtomic(new Predicate(11, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 10, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier), 10);
		p[4] = new PreconditionAtomic(new Predicate(14, 10, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier);
		p[5] = new PreconditionForAll(new Precondition87(unifier), new PreconditionAtomic(new Predicate(15, 10, new TermList(TermVariable.getVariable(3), TermList.NIL)), unifier), 10);
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

class Method31 extends Method
{
	public Method31()
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
				p = (new Precondition86(unifier)).setComparator(null);
			break;
			case 1:
				p = (new Precondition88(unifier)).setComparator(null);
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

class Precondition89 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition89(Term[] unifier)
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

class Precondition90 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition90(Term[] unifier)
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

class Method32 extends Method
{
	public Method32()
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
				p = (new Precondition89(unifier)).setComparator(null);
			break;
			case 1:
				p = (new Precondition90(unifier)).setComparator(null);
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

class Precondition91 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition91(Term[] unifier)
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

class Precondition92 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition92(Term[] unifier)
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

class Precondition93 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition93(Term[] unifier)
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

class Precondition94 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition94(Term[] unifier)
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

class Precondition95 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition95(Term[] unifier)
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

class Precondition96 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition96(Term[] unifier)
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
		p[9] = new PreconditionForAll(new Precondition95(unifier), new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier), 11);
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

class Precondition97 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition97(Term[] unifier)
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

class Precondition98 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition98(Term[] unifier)
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
		p[9] = new PreconditionForAll(new Precondition97(unifier), new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier), 11);
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

class Precondition99 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition99(Term[] unifier)
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

class Precondition100 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition100(Term[] unifier)
	{
		p = new Precondition[17];
		p[1] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(18), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[2] = new PreconditionAtomic(new Predicate(16, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[4] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 11);
		p[6] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionForAll(new Precondition99(unifier), new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier), 11);
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

class Precondition101 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition101(Term[] unifier)
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

class Precondition102 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition102(Term[] unifier)
	{
		p = new Precondition[12];
		p[1] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(18), TermList.NIL)), StdLib.equal, "StdLib.equal"), unifier);
		p[2] = new PreconditionAtomic(new Predicate(16, 11, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier);
		p[3] = new PreconditionCall(new TermCall(new List(TermVariable.getVariable(1), new TermList(new TermNumber(0.0), TermList.NIL)), StdLib.more, "StdLib.more"), unifier);
		p[4] = new PreconditionAtomic(new Predicate(11, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier);
		p[5] = new PreconditionNegation(new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(2), TermList.NIL)), unifier), 11);
		p[6] = new PreconditionAtomic(new Predicate(14, 11, new TermList(TermVariable.getVariable(2), new TermList(TermVariable.getVariable(3), TermList.NIL))), unifier);
		p[7] = new PreconditionForAll(new Precondition101(unifier), new PreconditionAtomic(new Predicate(15, 11, new TermList(TermVariable.getVariable(10), TermList.NIL)), unifier), 11);
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

class Precondition103 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition103(Term[] unifier)
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

class Method33 extends Method
{
	public Method33()
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
				p = (new Precondition91(unifier)).setComparator(null);
			break;
			case 1:
				p = (new Precondition92(unifier)).setComparator(null);
			break;
			case 2:
				p = (new Precondition93(unifier)).setComparator(null);
			break;
			case 3:
				p = (new Precondition94(unifier)).setComparator(null);
			break;
			case 4:
				p = (new Precondition96(unifier)).setComparator(null);
			break;
			case 5:
				p = (new Precondition98(unifier)).setComparator(null);
			break;
			case 6:
				p = (new Precondition100(unifier)).setComparator(null);
			break;
			case 7:
				p = (new Precondition102(unifier)).setComparator(null);
			break;
			case 8:
				p = (new Precondition103(unifier)).setComparator(null);
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
		methods[0][0] = new Method25();
		methods[0][1] = new Method26();

		methods[1] = new Method[1];
		methods[1][0] = new Method20();

		methods[2] = new Method[2];
		methods[2][0] = new Method17();
		methods[2][1] = new Method33();

		methods[3] = new Method[1];
		methods[3][0] = new Method31();

		methods[4] = new Method[2];
		methods[4][0] = new Method23();
		methods[4][1] = new Method24();

		methods[5] = new Method[2];
		methods[5][0] = new Method21();
		methods[5][1] = new Method22();

		methods[6] = new Method[2];
		methods[6][0] = new Method18();
		methods[6][1] = new Method19();

		methods[7] = new Method[1];
		methods[7][0] = new Method32();

		methods[8] = new Method[1];
		methods[8][0] = new Method29();

		methods[9] = new Method[2];
		methods[9][0] = new Method27();
		methods[9][1] = new Method28();

		methods[10] = new Method[1];
		methods[10][0] = new Method30();


		ops = new Operator[11][];

		ops[0] = new Operator[1];
		ops[0][0] = new Operator14();

		ops[1] = new Operator[1];
		ops[1][0] = new Operator15();

		ops[2] = new Operator[2];
		ops[2][0] = new Operator16();
		ops[2][1] = new Operator17();

		ops[3] = new Operator[1];
		ops[3][0] = new Operator18();

		ops[4] = new Operator[2];
		ops[4][0] = new Operator19();
		ops[4][1] = new Operator20();

		ops[5] = new Operator[2];
		ops[5][0] = new Operator21();
		ops[5][1] = new Operator22();

		ops[6] = new Operator[1];
		ops[6][0] = new Operator23();

		ops[7] = new Operator[1];
		ops[7][0] = new Operator24();

		ops[8] = new Operator[1];
		ops[8][0] = new Operator25();

		ops[9] = new Operator[1];
		ops[9][0] = new Operator26();

		ops[10] = new Operator[1];
		ops[10][0] = new Operator27();

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