package com.panda.bean;

import lombok.Data;

/**
 * @author GXB
 */
@Data
public class ShipAction {

    public final static String A = "A";
    public final static String B = "B";
    public final static String C = "C";
    public final static String D = "D";

    private String name;
    private int status;

    public ShipAction(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public static ShipAction getActionA(){
        return new ShipAction(A,1);
    }

    public static ShipAction getActionB(){
        return new ShipAction(B,1);
    }

    public static ShipAction getActionC(){
        return new ShipAction(C,1);
    }

    public static ShipAction getActionD(){
        return new ShipAction(D,1);
    }
}
