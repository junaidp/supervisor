/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wbc.supervisor.shared;


/**
 *
 * @author Intravue
 */
public class IpAddress implements Comparable<IpAddress> {
    private int[] parts = new int[4];
    private String ip;
    
//    public IpAddress( String address )  {
//        ip = address;
//        String[] s = address.split( "\\." );
//        if ( s.length != 4 ) {
//            return;
//        }
//        for (int i=0; i<4; i++) {
//            parts[i] = Integer.parseInt( s[i] );
//        }
//    }

    public IpAddress( String address )  {
        ip = address;
        String[] s = new String[4];
        try {
            splitIp(address, s);

        for (int i=0; i<4; i++) {
            parts[i] = Integer.parseInt( s[i] );
        }
        }catch (Exception ex){
            return;
        }
    }

    private void splitIp(String address, String[] s) {
        int i0 = address.indexOf('.');
        int i1 = address.indexOf('.', i0+1);
        int i2 = address.lastIndexOf('.');

        s[0] = address.substring(0,i0);
        s[1] = address.substring(i0+1,i1);
        s[2] = address.substring(i1+1,i2);
        s[3] = address.substring(i2 + 1);
    }

    public boolean isValid() 
    {
        boolean valid = false;
        String[] s = ip.split( "\\." );
        if ( s.length != 4 ) {
            return false;
        }
        // Note: EVERY text entry will still have parts.length == 4 because it is intialized that way.
        if ( parts.length == 4 )
        {
            for ( int i=0; i<4; i++ ) 
            {
                if ( parts[i] < 0 || parts[i] > 255 ) return false;
            }
            valid = true;
        }
        return valid;
    }
    
    public String toString() {
        return ip;
    }
    public int compareTo(IpAddress ip) {
        for (int i=0; i<4; i++) {
            if (i == 3) {
                if (parts[3] == ip.parts[3]) {
                    return 0;
                }
            }
            if (this.parts[i] > ip.parts[i]) {
                return 1;
            } else if (this.parts[i] < ip.parts[i]) {
                return -1;
            }
        }
        // should NEVER get here
        return 0;
    }
    

}
