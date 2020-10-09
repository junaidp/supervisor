package com.wbc.supervisor.server.dashboardutilities;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import com.wbc.supervisor.server.supervisorServiceImpl;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Locale;
import java.util.Scanner;

import static com.wbc.supervisor.server.dashboardutilities.SHAHelper.bytesToHex;


public class AuthenticationHandler {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    //private static final Logger logger = LogManager.getLogger( AuthenticationHandler.class);

    String fullFilename = "";
    private int authCode=-1;
    private String hostPK;
    private String hostKeycode;
    boolean debug = false;
    // private Logger logger;
    private static LocalDate expiresDate;
    private static final String SEP_CHAR = ",";
    private static final int IDX_PK = 0;
    private static final int IDX_DATE = 1;
    private static final int IDX_IGNORE_VALID = 2;

    public static final int FILE_NOT_EXIST = 101;
    public static final int FILE_HASH_INVALID = 102;
    public static final int PARSE_EXCEPTION = 106;
    public static final int PARSE_PK_NOTFOUND = 107;
    public static final int PK_VALID = 1;
    public static final int PK_VALID_EXPIRED = 110;
    public static final int PK_INVALID_INTRAVUE = 111;



    public AuthenticationHandler(String fullFilename, String hostKeycode  ) {
        this.fullFilename = fullFilename;
        this.hostKeycode = hostKeycode;
        if (supervisorServiceImpl.svrProps.getProperty("authentication.debug") != null) {
            if ( supervisorServiceImpl.svrProps.getProperty("authentication.debug").toLowerCase().equals("true")) debug=true;
        }
    }

    public int getAuthorization(String pkValidOnHostCode, Logger logger)  {
        File fFile = new File(fullFilename) ;
        if ( !fFile.exists()) {
            //logger.error( "getAuthorization: file does not exist > " + fullFilename );
            authCode = FILE_NOT_EXIST;
        } else {
            if ( !isHashcodeValid( fFile )) {
                authCode = FILE_HASH_INVALID;
            } else  {
                // find the pk in the file and return a code telling the result
                authCode = getCodeFromFile( fFile, pkValidOnHostCode, logger );
            }
        }
        return authCode;
    }

    public static boolean isHashcodeValid(File dataFile)  {
        String hashInFile = getHashFromFile( dataFile );
        if (hashInFile.startsWith("error")) {
            localLogger( hashInFile );
            return false;
        }
        //if (debug) logger.debug( "isHashcodeValid: hashinFile " + hashInFile );
        String tmpString = readDatFileAsString(dataFile);
        //String hexHash;
        try {
            // pk-keycode, not using SHA1
            //  hexHash = SHAHelper.SHA1(tmpString);
            String algorithm = "SHA-256";
            byte[] shaInBytes = SHAHelper.digest(tmpString.toString().getBytes(UTF_8), algorithm);
            String hexString = bytesToHex(shaInBytes);
            //if (debug) logger.debug( "isHashcodeValid: shaInBytes " + shaInBytes );
            if ( hashInFile.equals( hexString ))
            {
                return true;
            }
        } catch (NoSuchAlgorithmException ex) {
            localLogger( "isHashcodeValid: NoSuchAlgorithmException " +  ex.getMessage() );
        }
        return false;
    }

    private int getCodeFromFile(File datFile, String pkValidOnHostCode, Logger logger)  {
        int code=-1;
        Scanner scanner = null;
        int currentSection = -1;
        try {
            scanner = new Scanner( datFile );
            // parse whole file to end, a BASIC pk may be superceded by a PRO pk
            boolean matchFound = false;
            if (debug) logger.debug("getCodeFromFile: looking for [" + hostKeycode + "]");
            while ( scanner.hasNextLine() ) {
                String line = scanner.nextLine();
                if (line.startsWith("hash=")) {
                    continue;
                }
                byte[] base64decodedBytes = Base64.getDecoder().decode(line);
                line = new String(base64decodedBytes, "utf-8");
                if (line.startsWith("dummy")) {
                    // pk-keycode
                    // dbpks is filled to 76 entries with dummies as needed
                    // if dummy is encountered we are at 'end' of file.
                    break;
                }
                if (debug) logger.debug("getCodeFromFile: reading line > " + line );
                if (line.startsWith( hostKeycode )) {
                    if (debug) logger.debug("getCodeFromFile: match");
                    matchFound = true;
                    // now get expiration
                    LocalDate today = LocalDate.now();
                    String[] tokens = line.split(SEP_CHAR);
                    if (tokens.length > 0) {
                        String dateString = tokens[IDX_DATE];
                        String allowInvalid = "0";
                        if (tokens.length > 2) {
                            allowInvalid = tokens[IDX_IGNORE_VALID];
                        }
                        LocalDate expires = LocalDate.parse( dateString);
                        expiresDate = expires;
                        if ( expires.isAfter(today) ) {
                            // keycode's expire date is after today
                            // "pk" below really means keycode, Panduit made all PKs the same, change to using keycode 7/20/20
                            if ( pkValidOnHostCode.equals("1")) {
                                // 1 means the host is running and PK + keycode = auth code
                                code= PK_VALID;
                            } else {
                                if (allowInvalid.equals("1")) {
                                    code= PK_VALID;
                                } else {
                                    code = PK_INVALID_INTRAVUE;
                                }
                            }
                        } else {
                            // found pk but expired
                            //logger.info(String.format("Valid PK has expired, expire date %s", expires.toString()));
                            code= PK_VALID_EXPIRED;
                        }

                    }
                }
                if (matchFound) break;
            }
            if ( !matchFound ) {
                code = PARSE_PK_NOTFOUND;
            }
        }
        catch( FileNotFoundException fnf )  {
            logger.error( "getCodeFromFile: FileNotFoundException " + datFile + " " + fnf.getMessage() );
            code = FILE_NOT_EXIST;
        }
        catch( Exception e1 )       {
            logger.error( "getCodeFromFile: Exception: " + datFile + " " + e1.getMessage() );
            code = PARSE_EXCEPTION;
        }
        finally {
            //ensure the underlying stream is always closed
            if (scanner != null)
                scanner.close();
        }
        return code;
    }


    private static String getHashFromFile( File datFile )  {
        Scanner scanner = null;
        String hash = "";
        try {
            scanner = new Scanner( datFile );
            // parse whole file to end, a BASIC pk may be superceded by a PRO pk
            boolean matchFound = false;
            while ( scanner.hasNextLine() && !matchFound ) {
                String line = scanner.nextLine();
                // if (debug) logger.debug (" reading line > " + line );
                if ( line.startsWith("hash=") ) {
                    hash = line.substring(5);
                    matchFound = true;
                    break;
                }
            }
        }
        catch( FileNotFoundException fnf )  {
            hash = "error: getHashFromFile: " + datFile + " " + fnf.getMessage() ;
        }
        catch( Exception e1 )       {
            hash = "error: getHashFromFile: " + datFile + " " + e1.getMessage() ;
        }
        finally {
            //ensure the underlying stream is always closed
            if (scanner != null)
                scanner.close();
        }
        return hash;
    }

    private static String readDatFileAsString( File file )
    {
        Scanner scanner = null;
        StringBuilder filecontents = new StringBuilder();
        try {
            scanner = new Scanner( file );
            // parse whole file to end, a BASIC pk may be superceded by a PRO pk
            while ( scanner.hasNextLine() ) {
                String line = scanner.nextLine();
                if ( line.startsWith("hash") ) {
                    continue;
                }
                filecontents.append( line );
                // JWM-7/25/20  pk-keycode change
                // new dbpks.txt file has linefeeds written at end, need to add linefeed to filecontents.
                filecontents.append(System.lineSeparator());
            }
        }
        catch( Exception e1 )       {
            localLogger( "readDatFileAsString: Exception " + file + " " + e1.getMessage() );
            filecontents.setLength(0);
        }
        finally {
            //ensure the underlying stream is always closed
            if (scanner != null)
                scanner.close();
        }
        return filecontents.toString();
    }

    public static String getMessageForCode(Locale locale, int code )  {
        String msg = "";
        /*
        try  {
            ResourceBundle resourceMessages = ResourceBundle.getBundle(  "DashboardAuthenticator_bundle", locale, new UTF8Control() );
            if ( code == FILE_NOT_EXIST ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "FILE_NOT_EXIST");
            }
            else if ( code == FILE_HASH_INVALID ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "FILE_HASH_INVALID");
            }
            else if ( code == PK_NO_HOST_RESPONSE ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "PK_NO_HOST_RESPONSE");
            }
            else if ( code == PK_NO_PK ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "PK_NO_PK");
            }
            else if ( code == PK_URL_EXCEPTION ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "PK_URL_EXCEPTION");
            }
            else if ( code == PARSE_EXCEPTION ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "PARSE_EXCEPTION");
            }
            else if ( code == PARSE_PK_NOTFOUND ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "PARSE_PK_NOTFOUND");
            }
            else if ( code == NO_RESPONSE_AUTHENTICATOR ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "NO_RESPONSE_AUTHENTICATOR");
            }
            else if ( code == NO_ELEMENTS_AUTHENTICATOR ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "NO_ELEMENTS_AUTHENTICATOR");
            }
            else if ( code == BASIC ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "BASIC");
            }
            else if ( code == BASIC_DEMO ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "BASIC_DEMO");
            }
            else if ( code == PRO ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "PRO");
            }
            else if ( code == PRO_DEMO ) {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "PRO_DEMO");
            }
            else  {
                msg = ResourceMessageUtil.getResourceString( resourceMessages, "UNKNOWN_CODE");
            }
        } catch ( Exception ex )  {

         */
            if ( code == FILE_NOT_EXIST ) {
                msg = "The Authentication File could not be found";
            }
            else if ( code == FILE_HASH_INVALID ) {
                msg = "The Authentication File hash code is not valid";
            }
            else if ( code == PARSE_EXCEPTION ) {
                msg = "Exception: parsing the Authentication file";
            }
            else if ( code == PARSE_PK_NOTFOUND ) {
                msg =  "The Keycode was not found in the Authentication File";
            }
            else if ( code == PK_VALID_EXPIRED ) {
                msg =  "The Keycode is valid but the support contract has expired on " + expiresDate.toString() +".  Please contact support@wbc-ins.com";
            }
            else if ( code == PK_INVALID_INTRAVUE ) {
                msg = "The Keycode is found in the authorization file, but the IntraVUE host does not have a valid registration.  WBC-INS only supports IntraVUE's with valid registrations.  Please contact support@wbc-ins.com for help.";
            }
            else  {
                msg = "UNKNOWN_CODE (" + code + ")";
            }
        /*
        }
        */
        return msg;
    }

    public ErrorInfo getExpiration()  {
        ErrorInfo errorInfo = new ErrorInfo();
        File fFile = new File(fullFilename) ;
        String expireDate = "invalid";
        if ( !fFile.exists()) {
            errorInfo.makeError();
            errorInfo.setErrorText("getExpiration: file does not exist > " + fullFilename );
        } else {
            if ( !isHashcodeValid( fFile )) {
                errorInfo.makeError();
                errorInfo.setErrorText("getExpiration: pk file has invalid hash code");
            } else  {
                errorInfo = getExpirationFromFile(fFile);
            }
        }
        return errorInfo;
    }



    /*
    Do not call this if the PK is not valid
     */
    private ErrorInfo getExpirationFromFile(File datFile )  {
        ErrorInfo errorInfo = new ErrorInfo();
        String expiresDate = "";
        int code=-1;
        Scanner scanner = null;
        int currentSection = -1;
        try {
            scanner = new Scanner( datFile );
            // parse whole file to end, a BASIC pk may be superceded by a PRO pk
            boolean matchFound = false;
            while ( scanner.hasNextLine() ) {
                String line = scanner.nextLine();
                if (line.startsWith("hash=")) continue;
                // pk-keycode change, lines are encryted
                byte[] base64decodedBytes = Base64.getDecoder().decode(line);
                line = new String(base64decodedBytes, UTF_8 );
                if (line.startsWith("dummy")) {
                    // pk-keycode
                    // dbpks is filled to 76 entries with dummies as needed
                    // if dummy is encountered we are at 'end' of file.
                    break;
                }

                // if (debug) log (" reading line > " + line );
                if (line.startsWith( hostKeycode )) {
                    matchFound = true;
                    // now get expiration
                    LocalDate today = LocalDate.now();
                    String[] tokens = line.split(SEP_CHAR);
                    if (tokens.length > IDX_PK) {
                        expiresDate = tokens[IDX_DATE];
                        errorInfo.setErrorText(expiresDate);
                    }
                }
                if (matchFound) break;
            }
            // JWM 7/25/20 noticed no error set if match not found
            if (!matchFound) {
                errorInfo.makeError();
                errorInfo.setErrorText("Match not found for " + hostKeycode );
            }
        }
        catch( FileNotFoundException fnf )  {
            errorInfo.makeError();
            errorInfo.setErrorText("The PK datea file was not found.");
        }
        catch( Exception e1 )       {
            errorInfo.makeError();
            errorInfo.setErrorText("getExpirationFromFile Exception: " + datFile + " " + e1.getMessage() );
        }
        finally {
            //ensure the underlying stream is always closed
            if (scanner != null)
                scanner.close();
        }
        return errorInfo;
    }

    private static void localLogger( String message ) {
        //TODO get better way to log messages in server
        System.out.println( message );
    }
}
