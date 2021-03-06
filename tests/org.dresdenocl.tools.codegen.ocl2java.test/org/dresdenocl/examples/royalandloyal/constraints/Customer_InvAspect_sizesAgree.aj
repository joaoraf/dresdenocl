package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect Customer_InvAspect_sizesAgree {

    declare parents: org.dresdenocl.examples.royalandloyal.Customer implements org.dresdenocl.tools.codegen.ocl2java.types.OclCheckable;

    public void org.dresdenocl.examples.royalandloyal.Customer.checkInvariants() {
        /* Remains empty. Is only filled with behavior by advice(s). */
    }

    /**
     * <p>Pointcut for all calls on {@link org.dresdenocl.examples.royalandloyal.Customer#checkInvariants()}.</p>
     */
    protected pointcut checkInvariantsCaller(org.dresdenocl.examples.royalandloyal.Customer aClass):
    	call(void checkInvariants())
    	&& target(aClass);

    /**
     * <p><code>Checks an invariant on the class Customer defined by the constraint
     * <code>context Customer
     *       inv sizesAgree:     programs->size() = cards->select( valid = true )->size()</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.Customer aClass) : checkInvariantsCaller(aClass) {
        /* Disable this constraint for subclasses of Customer. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.Customer")) {
        java.util.HashSet<org.dresdenocl.examples.royalandloyal.CustomerCard> result1;
        result1 = new java.util.HashSet<org.dresdenocl.examples.royalandloyal.CustomerCard>();

        /* Iterator Select: Select all elements which fulfill the condition. */
        for (org.dresdenocl.examples.royalandloyal.CustomerCard anElement1 : aClass.cards) {
            if (((Object) anElement1.valid).equals(new Boolean(true))) {
                result1.add(anElement1);
            }
            // no else
        }

        if (!((Object) org.dresdenocl.tools.codegen.ocl2java.types.util.OclCollections.size(aClass.programs)).equals(org.dresdenocl.tools.codegen.ocl2java.types.util.OclCollections.size(result1))) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'sizesAgree' (inv sizesAgree:     programs->size() = cards->select( valid = true )->size()) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}