package com.hsd.desgin.visitor;

public interface CorporateSlaveVisitor {

    void visit(Programmer programmer);

    void visit(HumanSource humanSource);

    void visit(Tester tester);
}
