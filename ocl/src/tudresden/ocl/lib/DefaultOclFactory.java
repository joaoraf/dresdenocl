/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OCL Compiler                                                      *
 * Copyright (C) 1999, 2000 Frank Finger (frank@finger.org).         *
 * All rights reserved.                                              *
 *                                                                   *
 * This work was done as a diploma project at the Chair for Software *
 * Technology, Dresden University Of Technology, Germany             *
 * (http://www-st.inf.tu-dresden.de).  It is understood that any     *
 * modification not identified as such is not covered by the         *
 * preceding statement.                                              *
 *                                                                   *
 * This work is free software; you can redistribute it and/or        *
 * modify it under the terms of the GNU Library General Public       *
 * License as published by the Free Software Foundation; either      *
 * version 2 of the License, or (at your option) any later version.  *
 *                                                                   *
 * This work is distributed in the hope that it will be useful,      *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU *
 * Library General Public License for more details.                  *
 *                                                                   *
 * You should have received a copy of the GNU Library General Public *
 * License along with this library; if not, write to the             *
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,      *
 * Boston, MA  02111-1307, USA.                                      *
 *                                                                   *
 * To submit a bug report, send a comment, or get the latest news on *
 * this project and other projects, please visit the web site:       *
 * http://www-st.inf.tu-dresden.de/ (Chair home page) or             *
 * http://www-st.inf.tu-dresden.de/ocl/ (project home page)          *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
// FILE: d:/java/classes/de/tudresden/ocl/DefaultOclFactory.java

package tudresden.ocl.lib;

import java.util.*;
import java.util.Enumeration;

/** This class is the default implementation of the <CODE>OclFactory</CODE>
 *  interface. It is suitable for Java code generated by Argo and Rational
 *  Rose.
 *
 *  @author Frank Finger
 */
public class DefaultOclFactory implements OclFactory {

  public OclRoot getOclRepresentationFor(Object o) {
    if (o==null) return new OclString(null);
    if (o instanceof String)
      return new OclString( (String)o );
    else if (o instanceof java.util.Collection) {
      java.util.Collection oc=(java.util.Collection)o;
      if(o instanceof java.util.Set || (Ocl.TAKE_VECTORS_AS_SET && (o instanceof java.util.Vector)))
      {
        HashSet set=new HashSet();
        for(Iterator i=oc.iterator(); i.hasNext(); ) 
          set.add(getOclRepresentationFor(i.next()));
        return new OclSet(set);
      }
      else if(o instanceof java.util.List)
      {
        ArrayList list=new ArrayList();
        for(Iterator i=oc.iterator(); i.hasNext(); ) 
          list.add(getOclRepresentationFor(i.next()));
        return new OclSequence(list);
      }
      throw new OclException("encountered a java.util.Collection, which is neither Set or List.");
    } else if (o instanceof java.util.Map) {
      java.util.Map om=(java.util.Map)o;
      HashSet set=new HashSet();
      for(Iterator i=om.values().iterator(); i.hasNext(); ) 
        set.add(getOclRepresentationFor(i.next()));

      // in UML association features are sets (no duplicates).
      // since maps are used to represent qualified associations,
      // and java.util.Map does not enforce unique values,
      // I added an appropriate check here.
      if(om.size()!=set.size())
        throw new OclException("map values in feature "+o+" are not unique.");
      
      return new OclSet(set);
    } else if (o.getClass().isArray()) {
      Object[] oa=(Object[])o;
      ArrayList list=new ArrayList();
      for(int i=0; i<oa.length; i++) 
        list.add(getOclRepresentationFor(oa[i]));
      return new OclSequence(list);
    } 
    else if (o instanceof Boolean)
      return getOclRepresentationFor( ((Boolean)o).booleanValue() );
    else if (o instanceof Byte)
      return getOclRepresentationFor( ((Byte)o).byteValue() );
    else if (o instanceof Short)
      return getOclRepresentationFor( ((Short)o).shortValue() );
    else if (o instanceof Integer)
      return getOclRepresentationFor( ((Integer)o).intValue() );
    else if (o instanceof Long)
      return getOclRepresentationFor( ((Long)o).longValue() );
    else if (o instanceof Float)
      return getOclRepresentationFor( ((Float)o).floatValue() );
    else if (o instanceof Double)
      return getOclRepresentationFor( ((Double)o).doubleValue() );
    else if (o instanceof Character)
      return getOclRepresentationFor( ((Character)o).charValue() );
    else
      return new OclAnyImpl(o);
  }

	/**
		Return an OCL representation of the null value of
		the given type. These are usually empty values, such
		as empty strings or collections.
		Returns null, if a suitable null value is not known.
	*/
	public OclRoot getOclRepresentationForNull(Class c)
	{
		if(java.lang.String.class==c)
		{
			return new OclString(null);
		}
		else if(Collection.class.isAssignableFrom(c))
		{
			if(Set.class.isAssignableFrom(c) || (Ocl.TAKE_VECTORS_AS_SET && (Vector.class.isAssignableFrom(c))))
			{
				return new OclSet(new HashSet());
			}
			else if(List.class.isAssignableFrom(c))
			{
				return new OclSequence(new ArrayList());
			}
			throw new OclException("encountered a java.util.Collection, which is neither Set or List.");
		}
		else
			return null;
	}


  // factory methods for basic values:

  public OclBoolean getOclRepresentationFor(boolean b) {
    if (b)
      return OclBoolean.TRUE;
    else
      return OclBoolean.FALSE;
  }

  /** @return an <CODE>OclInteger</CODE> object */
  public OclRoot getOclRepresentationFor(byte b) {
    return new OclInteger((long)b);
  }

  /** @return an <CODE>OclInteger</CODE> object */
  public OclRoot getOclRepresentationFor(short s) {
    return new OclInteger((long)s);
  }

  /** @return an <CODE>OclInteger</CODE> object */
  public OclRoot getOclRepresentationFor(int i) {
    return new OclInteger(i);
  }

  /** @return an <CODE>OclInteger</CODE> object */
  public OclRoot getOclRepresentationFor(long l) {
    return new OclInteger(l);
  }

  /** @return an <CODE>OclReal</CODE> object
   */
  public OclRoot getOclRepresentationFor(float f) {
    return new OclReal((double)f);
  }

  /** @return an <CODE>OclReal</CODE> object
   */
  public OclRoot getOclRepresentationFor(double d) {
    return new OclReal(d);
  }

  /** @return an <CODE>OclString</CODE> object
   */
  public OclRoot getOclRepresentationFor(char c) {
    return new OclString(new Character(c).toString());
  }

  /** @return an OclState object with s as state description; this method
   *          might be implemented differently to increase performance with
   *          frequent state queries
   *
   *  @see OclStateAdapter
   */
  public OclState getOclStateFor(String s) {
    return new OclState(s);
  }

  public Object reconvert(Class targetType, OclRoot oclObject) {
    if (oclObject instanceof OclString) {
      return ((OclString)oclObject).getString();
    } else if (oclObject instanceof OclCollection) {
      OclCollection oc=(OclCollection)oclObject;
      Vector v=new Vector();
      OclIterator iter=oc.getIterator();
      while (iter.hasNext()) {
        iter.next();
        OclRoot obj=iter.getValue();
        v.add( reconvert(null, obj) );
      }
      return v;
    } else if (oclObject instanceof OclBoolean) {
      return new Boolean( ((OclBoolean)oclObject).isTrue() );
    } else if (oclObject instanceof OclInteger) {
      return new Integer( ((OclInteger)oclObject).getInt() );
    } else if (oclObject instanceof OclReal) {
      return new Float( (float) ((OclReal)oclObject).getDouble() );
    } else if (oclObject instanceof OclAnyImpl) {
      OclAnyImpl impl=(OclAnyImpl)oclObject;
      return impl.getObject();
    }
    return null;
  }

  public OclSequence getOclSequenceFor(Object o) {
    if (o instanceof Vector) {
      ArrayList list=new ArrayList();
      Enumeration enum=((Vector)o).elements();
      while (enum.hasMoreElements()) {
        list.add(Ocl.getOclRepresentationFor(enum.nextElement()));
      }
      return new OclSequence(list);
    } else if (o instanceof List) {
      return new OclSequence((List)o);
    } else {
      return new OclSequence(0,"failed cast to OclSequence");
    }
  }


} /* end class DefaultOclFactory */

