/* This file was generated by SableCC (http://www.sablecc.org/). */

package tudresden.ocl.codegen.decl.treegen.node;

import tudresden.ocl.codegen.decl.treegen.analysis.*;

public final class X2PQueryExpression extends XPQueryExpression
{
    private PQueryExpression _pQueryExpression_;

    public X2PQueryExpression()
    {
    }

    public X2PQueryExpression(
        PQueryExpression _pQueryExpression_)
    {
        setPQueryExpression(_pQueryExpression_);
    }

    public Object clone()
    {
        throw new RuntimeException("Unsupported Operation");
    }

    public void apply(Switch sw)
    {
        throw new RuntimeException("Switch not supported.");
    }

    public PQueryExpression getPQueryExpression()
    {
        return _pQueryExpression_;
    }

    public void setPQueryExpression(PQueryExpression node)
    {
        if(_pQueryExpression_ != null)
        {
            _pQueryExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _pQueryExpression_ = node;
    }

    void removeChild(Node child)
    {
        if(_pQueryExpression_ == child)
        {
            _pQueryExpression_ = null;
        }
    }

    void replaceChild(Node oldChild, Node newChild)
    {
    }

    public String toString()
    {
        return "" +
            toString(_pQueryExpression_);
    }
}