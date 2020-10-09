package com.wbc.supervisor.shared.dto;

import java.util.ArrayList;

/**
 * Created by JIM on 2/5/2015.
 */
public class DeviceStatDetails {
    String ipaddress;
    String name;
    ArrayList<Integer> values;  // doubles, floats etc will be multiplied to become ints, then divided by divisor in the jsp
    int divisor;
    int numValues;

    public DeviceStatDetails( int numberOfValues ) {
        numValues = numberOfValues;
        ipaddress="";
        name="";
        values = new ArrayList<Integer>( numValues);
        divisor = 1;
        for (int i=0; i<numberOfValues; i++) {
            values.add(0);
        }
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values = values;
    }

    public int getDivisor() {
        return divisor;
    }

    public void setDivisor(int divisor) {
        this.divisor = divisor;
    }

    /*
    NOTE: you must multiply any doubles or floats by the divisor to get an int
    and divide by divisor if you used one to get the decimal places back for display
     */
    public void setValue( int index, int value) {
        values.set( index, value );
    }

    public int getValue( int index ) {
        return  values.get( index );
    }

    @Override
    public String toString() {
        return "DeviceStatDetails{" +
                "ipaddress='" + ipaddress + '\'' +
                ", name='" + name + '\'' +
                ", values=" + values +
                ", divisor=" + divisor +
                ", numValues=" + numValues +
                '}';
    }
}
