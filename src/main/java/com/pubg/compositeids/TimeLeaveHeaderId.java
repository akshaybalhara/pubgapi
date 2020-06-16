package com.pubg.compositeids;

import java.io.Serializable;


public class TimeLeaveHeaderId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int unitCode;
	 
    private int documentNumber;
    
    /**
   	 * Default parameter less constructor. (DO NOT REMOVE IT)
   	 */
    public TimeLeaveHeaderId() {    }
    
    /**
   	 * Parameterized constructor to set values while using these composite ids as primary key.
   	 */
    public TimeLeaveHeaderId(int unitCode, int documentNumber) {
        this.unitCode = unitCode;
        this.documentNumber = documentNumber;
    }
    
    @Override
    public boolean equals(Object o) {
    	
    	if (o == this) return true;
    	
    	if (!(o instanceof TimeLeaveHeaderId)) {
            return false;
        }
    	
    	TimeLeaveHeaderId workflowLeaveHeaderId = (TimeLeaveHeaderId) o;
    	return workflowLeaveHeaderId.unitCode == unitCode &&	workflowLeaveHeaderId.documentNumber == documentNumber;
    }

	/**
	 * @return the unitCode
	 */
	public int getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(int unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * @return the documentNumber
	 */
	public int getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @param documentNumber the documentNumber to set
	 */
	public void setDocumentNumber(int documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Override
	public String toString() {
		return "TimeLeaveHeaderId [unitCode=" + unitCode + ", documentNumber=" + documentNumber + "]";
	}

	/*
	 * @Override public int hashCode() {
	 * 
	 * return 0; }
	 */
    

}
