/* This file was generated by SableCC (http://www.sablecc.org/). */

package tudresden.ocl.codegen.decl.treegen.node;

import java.util.*;
import tudresden.ocl.codegen.decl.treegen.analysis.*;

public final class AEmptyUnaryExpression extends PUnaryExpression
{

    public AEmptyUnaryExpression()
    {
    }
    public Object clone()
    {
        return new AEmptyUnaryExpression();
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAEmptyUnaryExpression(this);
    }

    public String toString()
    {
        return "";
    }

    void removeChild(Node child)
    {
    }

    void replaceChild(Node oldChild, Node newChild)
    {
    }
}