/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wbc.supervisor.server.dashboardutilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author intravue
 */
public class SHAHelper {

        public static String SHA1(String text) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-1");
                byte[] sha1hash = new byte[40];
                md.update(text.getBytes("iso-8859-1"), 0, text.length());
                sha1hash = md.digest();
            return convertToHex(sha1hash);
        } 

        private static String convertToHex(byte[] data) { 
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < data.length; i++) { 
                int halfbyte = (data[i] >>> 4) & 0x0F;
                int two_halfs = 0;
                do { 
                    if ((0 <= halfbyte) && (halfbyte <= 9)) 
                        buf.append((char) ('0' + halfbyte));
                    else 
                        buf.append((char) ('a' + (halfbyte - 10)));
                    halfbyte = data[i] & 0x0F;
                } while(two_halfs++ < 1);
            } 
            return buf.toString();
        }

        public static byte[] digest(byte[] input, String algorithm) throws NoSuchAlgorithmException {
            MessageDigest md;
            md = MessageDigest.getInstance(algorithm);
            byte[] result = md.digest(input);
            return result;
        }

        public static String bytesToHex(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }

}
