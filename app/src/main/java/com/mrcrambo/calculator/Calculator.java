package com.mrcrambo.calculator;

import java.math.BigDecimal;

/**
 * Created by ilnarkadyrov on 12/9/15.
 */
public class Calculator{
    private Double mOperand = Double.valueOf(0);
    private Double mWaitingOperand = Double.valueOf(0);
    private Double mCalcMemory = Double.valueOf(0);
    private String mWaitOperator = "";

    //operator types
    public static final String ADD = "+";
    public static final String SUB = "-";
    public static final String DIV = "/";
    public static final String MUL = "*";

    public static final String CLEAR = "C";
    public static final String CLEARMEM = "MC";
    public static final String ADDTOMEM = "M+";
    public static final String DELFROMMEM = "M-";
    public static final String CALLMEM = "MR";
    public static final String CHANGESIGN = "+/-";

    public void setOperand(double operand){
        this.mOperand = operand;
    }

    public Double getOperand(){
        return mOperand;
    }

    public Double getWaitingOperand() {
        return mWaitingOperand;
    }

    public String getWaitOperator() {
        return mWaitOperator;
    }

    public void setWaitingOperand(double mWaitingOperand) {
        this.mWaitingOperand = mWaitingOperand;
    }

    public void setWaitOperator(String mWaitOperator) {
        this.mWaitOperator = mWaitOperator;
    }

    public void setCalcMemory(double memory){
        mCalcMemory = memory;
    }

    public Double getCalcMemory() {
        return mCalcMemory;
    }

    @Override
    public String toString() {
        return String.valueOf(mOperand);
    }

    private String toString(BigDecimal mOperand) {
        return mOperand.toString();
    }

    protected Double Operation(String operator){
        if (operator.equals(CLEAR)){
            mOperand = Double.valueOf(0);
            mWaitOperator = "";
            mWaitingOperand = Double.valueOf(0);
        } else if(operator.equals(CLEARMEM)){
            mCalcMemory = Double.valueOf(0);
        } else if (operator.equals(ADDTOMEM)){
            //add current number to memory
            mCalcMemory += mOperand;
        } else if (operator.equals(DELFROMMEM)){
            //sub current number from memory
            mCalcMemory -= mOperand;
        } else if (operator.equals(CALLMEM)){
            mOperand = mCalcMemory;
        } else if (operator.equals(CHANGESIGN)){
            mOperand = -mOperand;
        } else {
            calcOperation();
            mWaitingOperand = mOperand;
            mWaitOperator = operator;
        }
        return mOperand;
    }

    protected void calcOperation(){
        if (mWaitOperator.equals(ADD)){
            mOperand += mWaitingOperand;
        } else if (mWaitOperator.equals(SUB)){
            mOperand = mWaitingOperand - mOperand;
        } else if (mWaitOperator.equals(MUL)){
            mOperand = mWaitingOperand * mOperand;
        } else if (mWaitOperator.equals(DIV)){
            if (!mOperand.equals(Double.valueOf(0))) {
                mOperand = mWaitingOperand / mOperand;
            }
        }
    }
}
