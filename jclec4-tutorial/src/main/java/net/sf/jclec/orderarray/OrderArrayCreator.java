package net.sf.jclec.orderarray;

import net.sf.jclec.ISpecies;
import net.sf.jclec.base.AbstractCreator;
import net.sf.jclec.util.intset.IIntegerSet;

/**
 * Creation of OrderArrayIndividual (and subclasses).
 *  
 * @author Alberto Cano
 * @author Jose Maria Luna
 * @author Juan Luis Olmo
 * @author Amelia Zafra
 * @author Sebastian Ventura
 */

public class OrderArrayCreator extends AbstractCreator 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/** Generated by Eclipse */
	
	private static final long serialVersionUID = -2638928425169895614L;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////
	
	/** Associated species */
	
	protected transient IOrderArraySpecies species;
	
	/** Genotype schema */
	
	protected transient IIntegerSet [] schema;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public OrderArrayCreator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	// java.lang.Object methods

	public boolean equals(Object other)
	{
		if (other instanceof OrderArrayCreator){
			return true;
		}
		else {
			return false;
		}
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	// AbstractCreator methods

	@Override
	protected void prepareCreation() 
	{
		ISpecies spc = context.getSpecies();
		if (spc instanceof IOrderArraySpecies) {
			// Sets individual species
			this.species = (IOrderArraySpecies) spc;
			// Sets genotype schema
			this.schema = this.species.getGenotypeSchema();
		}
		else {
			throw new IllegalStateException("Illegal species in context");
		}
	}

	@Override
	protected void createNext() 
	{
		createdBuffer.add(species.createIndividual(createGenotype()));
	}
	
	/////////////////////////////////////////////////////////////////
	// ---------------------------------------------- Private methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Create a int [] genotype, filling it randomly
	 */
	
	private final int[] createGenotype()
	{
		// Genotype length
		int gl = schema.length;
		// New genotype
		int [] genotype = new int[gl];
		
        int [] route = new int[gl];
        
		for(int i=0; i<gl; i++)
        	  route[i] = i;

		int position = randgen.choose(0, gl-1);
	
		genotype[0] = route[position];

		removeNode(route, position,0);

        for(int i=1;  i<gl;  i++)
        {
        	position = randgen.choose(i, gl-1);
        	genotype[i] = route[position];
        	removeNode(route, position, i);
       	}
		
		return genotype;
	}	
	
	private void removeNode(int [] route, int position, int index)
	{
		int aux;

		aux = route[position];
	    route[position] = route[index];
	    route[index] = aux;
	}
}