package com.hsd.desgin.visitor;

import java.util.ArrayList;
import java.util.List;

public class BigHuYouCompany {
    private List<CorporateSlave> employee= new ArrayList<>();

    public BigHuYouCompany() {
        employee.add(new Programmer("王二狗"));
        employee.add(new HumanSource("上官无需"));
        employee.add(new Tester("牛翠花"));
    }

    public void startProject(CorporateSlaveVisitor visitor){
        for (CorporateSlave slave : employee) {
            slave.accept(visitor);
        }
    }
}
