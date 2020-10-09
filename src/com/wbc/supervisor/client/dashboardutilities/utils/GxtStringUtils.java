package com.wbc.supervisor.client.dashboardutilities.utils;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.HasWidgets;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;


public class GxtStringUtils
{
    /**
     * A String for a space character.
     *
     * @since 3.2
     */
    public static final String SPACE = " ";

    /**
     * The empty String {@code ""}.
     *
     * @since 2.0
     */
    public static final String EMPTY = "";

    /**
     * A String for linefeed LF ("\n").
     *
     * @see <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF: Escape Sequences for Character and String Literals</a>
     * @since 3.2
     */
    public static final String LF = "\n";

    /**
     * A String for carriage return CR ("\r").
     *
     * @see <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF: Escape Sequences for Character and String Literals</a>
     * @since 3.2
     */
    public static final String CR = "\r";

    /**
     * Represents a failed index search.
     *
     * @since 2.1
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * <p>
     * The maximum size to which the padding constant(s) can expand.
     * </p>
     */
    private static final int PAD_LIMIT = 8192;

    public static void alignLabels(LabelAlign labelAlign, HasWidgets panel)
    {
        List<FieldLabel> labels = FormPanelHelper.getFieldLabels(panel);
        for (FieldLabel label : labels)
        {
            label.setLabelAlign(labelAlign);
        }
    }

    public static String capitalize(String value)
    {
        int length = value.length();
        StringBuilder s = new StringBuilder(length);
        for (int i = 0; i < length; i++)
        {
            char c = value.charAt(i);
            if (i == 0)
            {
                c = Character.toUpperCase(c);
            }
            else
            {
                c = Character.toLowerCase(c);
            }
            s.append(c);
        }
        return s.toString();
    }

    public static boolean isEmpty(String str)
    {
        return ((str == null) || (str.length() == 0));
    }

    public static boolean isNotEmpty(String str)
    {
        return (!(isEmpty(str)));
    }

    // Empty checks
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Checks if a CharSequence is empty ("") or null.
     * </p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the CharSequence. That functionality is available in isBlank().
     * </p>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(final CharSequence cs)
    {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>
     * Checks if a CharSequence is not empty ("") and not null.
     * </p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(final CharSequence cs)
    {
        return !isEmpty(cs);
    }

    // Trim
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Removes control characters (char &lt;= 32) from both ends of this String, handling {@code null} by returning {@code null}.
     * </p>
     *
     * <p>
     * The String is trimmed using {@link String#trim()}. Trim removes start and end characters &lt;= 32. To strip whitespace use {@link #strip(String)}.
     * </p>
     *
     * <p>
     * To trim your choice of characters, use the {@link #strip(String, String)} methods.
     * </p>
     *
     * <pre>
     * StringUtils.trim(null)          = null
     * StringUtils.trim("")            = ""
     * StringUtils.trim("     ")       = ""
     * StringUtils.trim("abc")         = "abc"
     * StringUtils.trim("    abc    ") = "abc"
     * </pre>
     *
     * @param str
     *            the String to be trimmed, may be null
     * @return the trimmed string, {@code null} if null String input
     */
    public static String trim(final String str)
    {
        return str == null ? null : str.trim();
    }

    /**
     * <p>
     * Removes control characters (char &lt;= 32) from both ends of this String returning {@code null} if the String is empty ("") after the trim or if it is
     * {@code null}.
     *
     * <p>
     * The String is trimmed using {@link String#trim()}. Trim removes start and end characters &lt;= 32. To strip whitespace use {@link #stripToNull(String)}.
     * </p>
     *
     * <pre>
     * StringUtils.trimToNull(null)          = null
     * StringUtils.trimToNull("")            = null
     * StringUtils.trimToNull("     ")       = null
     * StringUtils.trimToNull("abc")         = "abc"
     * StringUtils.trimToNull("    abc    ") = "abc"
     * </pre>
     *
     * @param str
     *            the String to be trimmed, may be null
     * @return the trimmed String, {@code null} if only chars &lt;= 32, empty or null String input
     * @since 2.0
     */
    public static String trimToNull(final String str)
    {
        final String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * <p>
     * Removes control characters (char &lt;= 32) from both ends of this String returning an empty String ("") if the String is empty ("") after the trim or if
     * it is {@code null}.
     *
     * <p>
     * The String is trimmed using {@link String#trim()}. Trim removes start and end characters &lt;= 32. To strip whitespace use {@link #stripToEmpty(String)}.
     * </p>
     *
     * <pre>
     * StringUtils.trimToEmpty(null)          = ""
     * StringUtils.trimToEmpty("")            = ""
     * StringUtils.trimToEmpty("     ")       = ""
     * StringUtils.trimToEmpty("abc")         = "abc"
     * StringUtils.trimToEmpty("    abc    ") = "abc"
     * </pre>
     *
     * @param str
     *            the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if {@code null} input
     * @since 2.0
     */
    public static String trimToEmpty(final String str)
    {
        return str == null ? EMPTY : str.trim();
    }

    // ContainsNone
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Checks that the CharSequence does not contain certain characters.
     * </p>
     *
     * <p>
     * A {@code null} CharSequence will return {@code true}. A {@code null} invalid character array will return {@code true}. An empty CharSequence (length()=0)
     * always returns true.
     * </p>
     *
     * <pre>
     * StringUtils.containsNone(null, *)       = true
     * StringUtils.containsNone(*, null)       = true
     * StringUtils.containsNone("", *)         = true
     * StringUtils.containsNone("ab", '')      = true
     * StringUtils.containsNone("abab", 'xyz') = true
     * StringUtils.containsNone("ab1", 'xyz')  = true
     * StringUtils.containsNone("abz", 'xyz')  = false
     * </pre>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @param searchChars
     *            an array of invalid chars, may be null
     * @return true if it contains none of the invalid chars, or is null
     * @since 2.0
     * @since 3.0 Changed signature from containsNone(String, char[]) to containsNone(CharSequence, char...)
     */
    public static boolean containsNone(final CharSequence cs, final char... searchChars)
    {
        if (cs == null || searchChars == null) { return true; }
        final int csLen = cs.length();
        final int csLast = csLen - 1;
        final int searchLen = searchChars.length;
        final int searchLast = searchLen - 1;
        for (int i = 0; i < csLen; i++)
        {
            final char ch = cs.charAt(i);
            for (int j = 0; j < searchLen; j++)
            {
                if (searchChars[j] == ch)
                {
                    if (Character.isHighSurrogate(ch))
                    {
                        if (j == searchLast)
                        {
                            // missing low surrogate, fine, like String.indexOf(String)
                            return false;
                        }
                        if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) { return false; }
                    }
                    else
                    {
                        // ch is in the Basic Multilingual Plane
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks that the CharSequence does not contain certain characters.
     * </p>
     *
     * <p>
     * A {@code null} CharSequence will return {@code true}. A {@code null} invalid character array will return {@code true}. An empty String ("") always
     * returns true.
     * </p>
     *
     * <pre>
     * StringUtils.containsNone(null, *)       = true
     * StringUtils.containsNone(*, null)       = true
     * StringUtils.containsNone("", *)         = true
     * StringUtils.containsNone("ab", "")      = true
     * StringUtils.containsNone("abab", "xyz") = true
     * StringUtils.containsNone("ab1", "xyz")  = true
     * StringUtils.containsNone("abz", "xyz")  = false
     * </pre>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @param invalidChars
     *            a String of invalid chars, may be null
     * @return true if it contains none of the invalid chars, or is null
     * @since 2.0
     * @since 3.0 Changed signature from containsNone(String, String) to containsNone(CharSequence, String)
     */
    public static boolean containsNone(final CharSequence cs, final String invalidChars)
    {
        if (cs == null || invalidChars == null) { return true; }
        return containsNone(cs, invalidChars.toCharArray());
    }

    // Substring
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Gets a substring from the specified String avoiding exceptions.
     * </p>
     *
     * <p>
     * A negative start position can be used to start {@code n} characters from the end of the String.
     * </p>
     *
     * <p>
     * A {@code null} String will return {@code null}. An empty ("") String will return "".
     * </p>
     *
     * <pre>
     * StringUtils.substring(null, *)   = null
     * StringUtils.substring("", *)     = ""
     * StringUtils.substring("abc", 0)  = "abc"
     * StringUtils.substring("abc", 2)  = "c"
     * StringUtils.substring("abc", 4)  = ""
     * StringUtils.substring("abc", -2) = "bc"
     * StringUtils.substring("abc", -4) = "abc"
     * </pre>
     *
     * @param str
     *            the String to get the substring from, may be null
     * @param start
     *            the position to start from, negative means count back from the end of the String by this many characters
     * @return substring from start position, {@code null} if null String input
     */
    public static String substring(final String str, int start)
    {
        if (str == null) { return null; }

        // handle negatives, which means last n characters
        if (start < 0)
        {
            start = str.length() + start; // remember start is negative
        }

        if (start < 0)
        {
            start = 0;
        }
        if (start > str.length()) { return EMPTY; }

        return str.substring(start);
    }

    /**
     * <p>
     * Gets a substring from the specified String avoiding exceptions.
     * </p>
     *
     * <p>
     * A negative start position can be used to start/end {@code n} characters from the end of the String.
     * </p>
     *
     * <p>
     * The returned substring starts with the character in the {@code start} position and ends before the {@code end} position. All position counting is
     * zero-based -- i.e., to start at the beginning of the string use {@code start = 0}. Negative start and end positions can be used to specify offsets
     * relative to the end of the String.
     * </p>
     *
     * <p>
     * If {@code start} is not strictly to the left of {@code end}, "" is returned.
     * </p>
     *
     * <pre>
     * StringUtils.substring(null, *, *)    = null
     * StringUtils.substring("", * ,  *)    = "";
     * StringUtils.substring("abc", 0, 2)   = "ab"
     * StringUtils.substring("abc", 2, 0)   = ""
     * StringUtils.substring("abc", 2, 4)   = "c"
     * StringUtils.substring("abc", 4, 6)   = ""
     * StringUtils.substring("abc", 2, 2)   = ""
     * StringUtils.substring("abc", -2, -1) = "b"
     * StringUtils.substring("abc", -4, 2)  = "ab"
     * </pre>
     *
     * @param str
     *            the String to get the substring from, may be null
     * @param start
     *            the position to start from, negative means count back from the end of the String by this many characters
     * @param end
     *            the position to end at (exclusive), negative means count back from the end of the String by this many characters
     * @return substring from start position to end position, {@code null} if null String input
     */
    public static String substring(final String str, int start, int end)
    {
        if (str == null) { return null; }

        // handle negatives
        if (end < 0)
        {
            end = str.length() + end; // remember end is negative
        }
        if (start < 0)
        {
            start = str.length() + start; // remember start is negative
        }

        // check length next
        if (end > str.length())
        {
            end = str.length();
        }

        // if start is greater than end, return ""
        if (start > end) { return EMPTY; }

        if (start < 0)
        {
            start = 0;
        }
        if (end < 0)
        {
            end = 0;
        }

        return str.substring(start, end);
    }

    // Left/Right/Mid
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Gets the leftmost {@code len} characters of a String.
     * </p>
     *
     * <p>
     * If {@code len} characters are not available, or the String is {@code null}, the String will be returned without an exception. An empty String is returned
     * if len is negative.
     * </p>
     *
     * <pre>
     * StringUtils.left(null, *)    = null
     * StringUtils.left(*, -ve)     = ""
     * StringUtils.left("", *)      = ""
     * StringUtils.left("abc", 0)   = ""
     * StringUtils.left("abc", 2)   = "ab"
     * StringUtils.left("abc", 4)   = "abc"
     * </pre>
     *
     * @param str
     *            the String to get the leftmost characters from, may be null
     * @param len
     *            the length of the required String
     * @return the leftmost characters, {@code null} if null String input
     */
    public static String left(final String str, final int len)
    {
        if (str == null) { return null; }
        if (len < 0) { return EMPTY; }
        if (str.length() <= len) { return str; }
        return str.substring(0, len);
    }

    /**
     * <p>
     * Gets the rightmost {@code len} characters of a String.
     * </p>
     *
     * <p>
     * If {@code len} characters are not available, or the String is {@code null}, the String will be returned without an an exception. An empty String is
     * returned if len is negative.
     * </p>
     *
     * <pre>
     * StringUtils.right(null, *)    = null
     * StringUtils.right(*, -ve)     = ""
     * StringUtils.right("", *)      = ""
     * StringUtils.right("abc", 0)   = ""
     * StringUtils.right("abc", 2)   = "bc"
     * StringUtils.right("abc", 4)   = "abc"
     * </pre>
     *
     * @param str
     *            the String to get the rightmost characters from, may be null
     * @param len
     *            the length of the required String
     * @return the rightmost characters, {@code null} if null String input
     */
    public static String right(final String str, final int len)
    {
        if (str == null) { return null; }
        if (len < 0) { return EMPTY; }
        if (str.length() <= len) { return str; }
        return str.substring(str.length() - len);
    }

    /**
     * <p>
     * Gets {@code len} characters from the middle of a String.
     * </p>
     *
     * <p>
     * If {@code len} characters are not available, the remainder of the String will be returned without an exception. If the String is {@code null},
     * {@code null} will be returned. An empty String is returned if len is negative or exceeds the length of {@code str}.
     * </p>
     *
     * <pre>
     * StringUtils.mid(null, *, *)    = null
     * StringUtils.mid(*, *, -ve)     = ""
     * StringUtils.mid("", 0, *)      = ""
     * StringUtils.mid("abc", 0, 2)   = "ab"
     * StringUtils.mid("abc", 0, 4)   = "abc"
     * StringUtils.mid("abc", 2, 4)   = "c"
     * StringUtils.mid("abc", 4, 2)   = ""
     * StringUtils.mid("abc", -2, 2)  = "ab"
     * </pre>
     *
     * @param str
     *            the String to get the characters from, may be null
     * @param pos
     *            the position to start from, negative treated as zero
     * @param len
     *            the length of the required String
     * @return the middle characters, {@code null} if null String input
     */
    public static String mid(final String str, int pos, final int len)
    {
        if (str == null) { return null; }
        if (len < 0 || pos > str.length()) { return EMPTY; }
        if (pos < 0)
        {
            pos = 0;
        }
        if (str.length() <= pos + len) { return str.substring(pos); }
        return str.substring(pos, pos + len);
    }

    // SubStringAfter/SubStringBefore
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Gets the substring before the first occurrence of a separator. The separator is not returned.
     * </p>
     *
     * <p>
     * A {@code null} string input will return {@code null}. An empty ("") string input will return the empty string. A {@code null} separator will return the
     * input string.
     * </p>
     *
     * <p>
     * If nothing is found, the string input is returned.
     * </p>
     *
     * <pre>
     * StringUtils.substringBefore(null, *)      = null
     * StringUtils.substringBefore("", *)        = ""
     * StringUtils.substringBefore("abc", "a")   = ""
     * StringUtils.substringBefore("abcba", "b") = "a"
     * StringUtils.substringBefore("abc", "c")   = "ab"
     * StringUtils.substringBefore("abc", "d")   = "abc"
     * StringUtils.substringBefore("abc", "")    = ""
     * StringUtils.substringBefore("abc", null)  = "abc"
     * </pre>
     *
     * @param str
     *            the String to get a substring from, may be null
     * @param separator
     *            the String to search for, may be null
     * @return the substring before the first occurrence of the separator, {@code null} if null String input
     * @since 2.0
     */
    public static String substringBefore(final String str, final String separator)
    {
        if (isEmpty(str) || separator == null) { return str; }
        if (separator.isEmpty()) { return EMPTY; }
        final int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) { return str; }
        return str.substring(0, pos);
    }

    /**
     * <p>
     * Gets the substring after the first occurrence of a separator. The separator is not returned.
     * </p>
     *
     * <p>
     * A {@code null} string input will return {@code null}. An empty ("") string input will return the empty string. A {@code null} separator will return the
     * empty string if the input string is not {@code null}.
     * </p>
     *
     * <p>
     * If nothing is found, the empty string is returned.
     * </p>
     *
     * <pre>
     * StringUtils.substringAfter(null, *)      = null
     * StringUtils.substringAfter("", *)        = ""
     * StringUtils.substringAfter(*, null)      = ""
     * StringUtils.substringAfter("abc", "a")   = "bc"
     * StringUtils.substringAfter("abcba", "b") = "cba"
     * StringUtils.substringAfter("abc", "c")   = ""
     * StringUtils.substringAfter("abc", "d")   = ""
     * StringUtils.substringAfter("abc", "")    = "abc"
     * </pre>
     *
     * @param str
     *            the String to get a substring from, may be null
     * @param separator
     *            the String to search for, may be null
     * @return the substring after the first occurrence of the separator, {@code null} if null String input
     * @since 2.0
     */
    public static String substringAfter(final String str, final String separator)
    {
        if (isEmpty(str)) { return str; }
        if (separator == null) { return EMPTY; }
        final int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) { return EMPTY; }
        return str.substring(pos + separator.length());
    }

    /**
     * <p>
     * Gets the substring before the last occurrence of a separator. The separator is not returned.
     * </p>
     *
     * <p>
     * A {@code null} string input will return {@code null}. An empty ("") string input will return the empty string. An empty or {@code null} separator will
     * return the input string.
     * </p>
     *
     * <p>
     * If nothing is found, the string input is returned.
     * </p>
     *
     * <pre>
     * StringUtils.substringBeforeLast(null, *)      = null
     * StringUtils.substringBeforeLast("", *)        = ""
     * StringUtils.substringBeforeLast("abcba", "b") = "abc"
     * StringUtils.substringBeforeLast("abc", "c")   = "ab"
     * StringUtils.substringBeforeLast("a", "a")     = ""
     * StringUtils.substringBeforeLast("a", "z")     = "a"
     * StringUtils.substringBeforeLast("a", null)    = "a"
     * StringUtils.substringBeforeLast("a", "")      = "a"
     * </pre>
     *
     * @param str
     *            the String to get a substring from, may be null
     * @param separator
     *            the String to search for, may be null
     * @return the substring before the last occurrence of the separator, {@code null} if null String input
     * @since 2.0
     */
    public static String substringBeforeLast(final String str, final String separator)
    {
        if (isEmpty(str) || isEmpty(separator)) { return str; }
        final int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND) { return str; }
        return str.substring(0, pos);
    }

    /**
     * <p>
     * Gets the substring after the last occurrence of a separator. The separator is not returned.
     * </p>
     *
     * <p>
     * A {@code null} string input will return {@code null}. An empty ("") string input will return the empty string. An empty or {@code null} separator will
     * return the empty string if the input string is not {@code null}.
     * </p>
     *
     * <p>
     * If nothing is found, the empty string is returned.
     * </p>
     *
     * <pre>
     * StringUtils.substringAfterLast(null, *)      = null
     * StringUtils.substringAfterLast("", *)        = ""
     * StringUtils.substringAfterLast(*, "")        = ""
     * StringUtils.substringAfterLast(*, null)      = ""
     * StringUtils.substringAfterLast("abc", "a")   = "bc"
     * StringUtils.substringAfterLast("abcba", "b") = "a"
     * StringUtils.substringAfterLast("abc", "c")   = ""
     * StringUtils.substringAfterLast("a", "a")     = ""
     * StringUtils.substringAfterLast("a", "z")     = ""
     * </pre>
     *
     * @param str
     *            the String to get a substring from, may be null
     * @param separator
     *            the String to search for, may be null
     * @return the substring after the last occurrence of the separator, {@code null} if null String input
     * @since 2.0
     */
    public static String substringAfterLast(final String str, final String separator)
    {
        if (isEmpty(str)) { return str; }
        if (isEmpty(separator)) { return EMPTY; }
        final int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND || pos == str.length() - separator.length()) { return EMPTY; }
        return str.substring(pos + separator.length());
    }

    // Substring between
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Gets the String that is nested in between two instances of the same String.
     * </p>
     *
     * <p>
     * A {@code null} input String returns {@code null}. A {@code null} tag returns {@code null}.
     * </p>
     *
     * <pre>
     * StringUtils.substringBetween(null, *)            = null
     * StringUtils.substringBetween("", "")             = ""
     * StringUtils.substringBetween("", "tag")          = null
     * StringUtils.substringBetween("tagabctag", null)  = null
     * StringUtils.substringBetween("tagabctag", "")    = ""
     * StringUtils.substringBetween("tagabctag", "tag") = "abc"
     * </pre>
     *
     * @param str
     *            the String containing the substring, may be null
     * @param tag
     *            the String before and after the substring, may be null
     * @return the substring, {@code null} if no match
     * @since 2.0
     */
    public static String substringBetween(final String str, final String tag)
    {
        return substringBetween(str, tag, tag);
    }

    /**
     * <p>
     * Gets the String that is nested in between two Strings. Only the first match is returned.
     * </p>
     *
     * <p>
     * A {@code null} input String returns {@code null}. A {@code null} open/close returns {@code null} (no match). An empty ("") open and close returns an
     * empty string.
     * </p>
     *
     * <pre>
     * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
     * StringUtils.substringBetween(null, *, *)          = null
     * StringUtils.substringBetween(*, null, *)          = null
     * StringUtils.substringBetween(*, *, null)          = null
     * StringUtils.substringBetween("", "", "")          = ""
     * StringUtils.substringBetween("", "", "]")         = null
     * StringUtils.substringBetween("", "[", "]")        = null
     * StringUtils.substringBetween("yabcz", "", "")     = ""
     * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
     * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     *
     * @param str
     *            the String containing the substring, may be null
     * @param open
     *            the String before the substring, may be null
     * @param close
     *            the String after the substring, may be null
     * @return the substring, {@code null} if no match
     * @since 2.0
     */
    public static String substringBetween(final String str, final String open, final String close)
    {
        if (str == null || open == null || close == null) { return null; }
        final int start = str.indexOf(open);
        if (start != INDEX_NOT_FOUND)
        {
            final int end = str.indexOf(close, start + open.length());
            if (end != INDEX_NOT_FOUND) { return str.substring(start + open.length(), end); }
        }
        return null;
    }

    // Remove
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Removes a substring only if it is at the beginning of a source string, otherwise returns the source string.
     * </p>
     *
     * <p>
     * A {@code null} source string will return {@code null}. An empty ("") source string will return the empty string. A {@code null} search string will return
     * the source string.
     * </p>
     *
     * <pre>
     * StringUtils.removeStart(null, *)      = null
     * StringUtils.removeStart("", *)        = ""
     * StringUtils.removeStart(*, null)      = *
     * StringUtils.removeStart("www.domain.com", "www.")   = "domain.com"
     * StringUtils.removeStart("domain.com", "www.")       = "domain.com"
     * StringUtils.removeStart("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeStart("abc", "")    = "abc"
     * </pre>
     *
     * @param str
     *            the source String to search, may be null
     * @param remove
     *            the String to search for and remove, may be null
     * @return the substring with the string removed if found, {@code null} if null String input
     * @since 2.1
     */
    public static String removeStart(final String str, final String remove)
    {
        if (isEmpty(str) || isEmpty(remove)) { return str; }
        if (str.startsWith(remove)) { return str.substring(remove.length()); }
        return str;
    }

    /**
     * <p>
     * Removes a substring only if it is at the end of a source string, otherwise returns the source string.
     * </p>
     *
     * <p>
     * A {@code null} source string will return {@code null}. An empty ("") source string will return the empty string. A {@code null} search string will return
     * the source string.
     * </p>
     *
     * <pre>
     * StringUtils.removeEnd(null, *)      = null
     * StringUtils.removeEnd("", *)        = ""
     * StringUtils.removeEnd(*, null)      = *
     * StringUtils.removeEnd("www.domain.com", ".com.")  = "www.domain.com"
     * StringUtils.removeEnd("www.domain.com", ".com")   = "www.domain"
     * StringUtils.removeEnd("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeEnd("abc", "")    = "abc"
     * </pre>
     *
     * @param str
     *            the source String to search, may be null
     * @param remove
     *            the String to search for and remove, may be null
     * @return the substring with the string removed if found, {@code null} if null String input
     * @since 2.1
     */
    public static String removeEnd(final String str, final String remove)
    {
        if (isEmpty(str) || isEmpty(remove)) { return str; }
        if (str.endsWith(remove)) { return str.substring(0, str.length() - remove.length()); }
        return str;
    }

    /**
     * <p>
     * Removes all occurrences of a substring from within the source string.
     * </p>
     *
     * <p>
     * A {@code null} source string will return {@code null}. An empty ("") source string will return the empty string. A {@code null} remove string will return
     * the source string. An empty ("") remove string will return the source string.
     * </p>
     *
     * <pre>
     * StringUtils.remove(null, *)        = null
     * StringUtils.remove("", *)          = ""
     * StringUtils.remove(*, null)        = *
     * StringUtils.remove(*, "")          = *
     * StringUtils.remove("queued", "ue") = "qd"
     * StringUtils.remove("queued", "zz") = "queued"
     * </pre>
     *
     * @param str
     *            the source String to search, may be null
     * @param remove
     *            the String to search for and remove, may be null
     * @return the substring with the string removed if found, {@code null} if null String input
     * @since 2.1
     */
    public static String remove(final String str, final String remove)
    {
        if (isEmpty(str) || isEmpty(remove)) { return str; }
        return replace(str, remove, EMPTY, -1);
    }

    /**
     * <p>
     * Removes all occurrences of a character from within the source string.
     * </p>
     *
     * <p>
     * A {@code null} source string will return {@code null}. An empty ("") source string will return the empty string.
     * </p>
     *
     * <pre>
     * StringUtils.remove(null, *)       = null
     * StringUtils.remove("", *)         = ""
     * StringUtils.remove("queued", 'u') = "qeed"
     * StringUtils.remove("queued", 'z') = "queued"
     * </pre>
     *
     * @param str
     *            the source String to search, may be null
     * @param remove
     *            the char to search for and remove, may be null
     * @return the substring with the char removed if found, {@code null} if null String input
     * @since 2.1
     */
    public static String remove(final String str, final char remove)
    {
        if (isEmpty(str) || str.indexOf(remove) == INDEX_NOT_FOUND) { return str; }
        final char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++)
        {
            if (chars[i] != remove)
            {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    // Replacing
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Replaces a String with another String inside a larger String, once.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op.
     * </p>
     *
     * <pre>
     * StringUtils.replaceOnce(null, *, *)        = null
     * StringUtils.replaceOnce("", *, *)          = ""
     * StringUtils.replaceOnce("any", null, *)    = "any"
     * StringUtils.replaceOnce("any", *, null)    = "any"
     * StringUtils.replaceOnce("any", "", *)      = "any"
     * StringUtils.replaceOnce("aba", "a", null)  = "aba"
     * StringUtils.replaceOnce("aba", "a", "")    = "ba"
     * StringUtils.replaceOnce("aba", "a", "z")   = "zba"
     * </pre>
     *
     * @see #replace(String text, String searchString, String replacement, int max)
     * @param text
     *            text to search and replace in, may be null
     * @param searchString
     *            the String to search for, may be null
     * @param replacement
     *            the String to replace with, may be null
     * @return the text with any replacements processed, {@code null} if null String input
     */
    public static String replaceOnce(final String text, final String searchString, final String replacement)
    {
        return replace(text, searchString, replacement, 1);
    }

    /**
     * <p>
     * Replaces all occurrences of a String within another String.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op.
     * </p>
     *
     * <pre>
     * StringUtils.replace(null, *, *)        = null
     * StringUtils.replace("", *, *)          = ""
     * StringUtils.replace("any", null, *)    = "any"
     * StringUtils.replace("any", *, null)    = "any"
     * StringUtils.replace("any", "", *)      = "any"
     * StringUtils.replace("aba", "a", null)  = "aba"
     * StringUtils.replace("aba", "a", "")    = "b"
     * StringUtils.replace("aba", "a", "z")   = "zbz"
     * </pre>
     *
     * @see #replace(String text, String searchString, String replacement, int max)
     * @param text
     *            text to search and replace in, may be null
     * @param searchString
     *            the String to search for, may be null
     * @param replacement
     *            the String to replace it with, may be null
     * @return the text with any replacements processed, {@code null} if null String input
     */
    public static String replace(final String text, final String searchString, final String replacement)
    {
        return replace(text, searchString, replacement, -1);
    }

    /**
     * <p>
     * Replaces a String with another String inside a larger String, for the first {@code max} values of the search String.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op.
     * </p>
     *
     * <pre>
     * StringUtils.replace(null, *, *, *)         = null
     * StringUtils.replace("", *, *, *)           = ""
     * StringUtils.replace("any", null, *, *)     = "any"
     * StringUtils.replace("any", *, null, *)     = "any"
     * StringUtils.replace("any", "", *, *)       = "any"
     * StringUtils.replace("any", *, *, 0)        = "any"
     * StringUtils.replace("abaa", "a", null, -1) = "abaa"
     * StringUtils.replace("abaa", "a", "", -1)   = "b"
     * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
     * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
     * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
     * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
     * </pre>
     *
     * @param text
     *            text to search and replace in, may be null
     * @param searchString
     *            the String to search for, may be null
     * @param replacement
     *            the String to replace it with, may be null
     * @param max
     *            maximum number of values to replace, or {@code -1} if no maximum
     * @return the text with any replacements processed, {@code null} if null String input
     */
    public static String replace(final String text, final String searchString, final String replacement, int max)
    {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) { return text; }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == INDEX_NOT_FOUND) { return text; }
        final int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = increase < 0 ? 0 : increase;
        increase *= max < 0 ? 16 : max > 64 ? 64 : max;
        final StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != INDEX_NOT_FOUND)
        {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0)
            {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * <p>
     * Replaces all occurrences of Strings within another String.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op, or if any "search string" or "string to replace" is null, that replace will be ignored. This
     * will not repeat. For repeating replaces, call the overloaded method.
     * </p>
     *
     * <pre>
     *  StringUtils.replaceEach(null, *, *)        = null
     *  StringUtils.replaceEach("", *, *)          = ""
     *  StringUtils.replaceEach("aba", null, null) = "aba"
     *  StringUtils.replaceEach("aba", new String[0], null) = "aba"
     *  StringUtils.replaceEach("aba", null, new String[0]) = "aba"
     *  StringUtils.replaceEach("aba", new String[]{"a"}, null)  = "aba"
     *  StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""})  = "b"
     *  StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"})  = "aba"
     *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"})  = "wcte"
     *  (example of how it does not repeat)
     *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"})  = "dcte"
     * </pre>
     *
     * @param text
     *            text to search and replace in, no-op if null
     * @param searchList
     *            the Strings to search for, no-op if null
     * @param replacementList
     *            the Strings to replace them with, no-op if null
     * @return the text with any replacements processed, {@code null} if null String input
     * @throws IllegalArgumentException
     *             if the lengths of the arrays are not the same (null is ok, and/or size 0)
     * @since 2.4
     */
    public static String replaceEach(final String text, final String[] searchList, final String[] replacementList)
    {
        return replaceEach(text, searchList, replacementList, false, 0);
    }

    /**
     * <p>
     * Replaces all occurrences of Strings within another String.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op, or if any "search string" or "string to replace" is null, that replace will be ignored.
     * </p>
     *
     * <pre>
     *  StringUtils.replaceEach(null, *, *, *) = null
     *  StringUtils.replaceEach("", *, *, *) = ""
     *  StringUtils.replaceEach("aba", null, null, *) = "aba"
     *  StringUtils.replaceEach("aba", new String[0], null, *) = "aba"
     *  StringUtils.replaceEach("aba", null, new String[0], *) = "aba"
     *  StringUtils.replaceEach("aba", new String[]{"a"}, null, *) = "aba"
     *  StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""}, *) = "b"
     *  StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"}, *) = "aba"
     *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}, *) = "wcte"
     *  (example of how it repeats)
     *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, false) = "dcte"
     *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, true) = "tcte"
     *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, true) = IllegalStateException
     *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, false) = "dcabe"
     * </pre>
     *
     * @param text
     *            text to search and replace in, no-op if null
     * @param searchList
     *            the Strings to search for, no-op if null
     * @param replacementList
     *            the Strings to replace them with, no-op if null
     * @return the text with any replacements processed, {@code null} if null String input
     * @throws IllegalStateException
     *             if the search is repeating and there is an endless loop due to outputs of one being inputs to another
     * @throws IllegalArgumentException
     *             if the lengths of the arrays are not the same (null is ok, and/or size 0)
     * @since 2.4
     */
    public static String replaceEachRepeatedly(final String text, final String[] searchList, final String[] replacementList)
    {
        // timeToLive should be 0 if not used or nothing to replace, else it's
        // the length of the replace array
        final int timeToLive = searchList == null ? 0 : searchList.length;
        return replaceEach(text, searchList, replacementList, true, timeToLive);
    }

    /**
     * <p>
     * Replaces all occurrences of Strings within another String.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op, or if any "search string" or "string to replace" is null, that replace will be ignored.
     * </p>
     *
     * <pre>
     *  StringUtils.replaceEach(null, *, *, *) = null
     *  StringUtils.replaceEach("", *, *, *) = ""
     *  StringUtils.replaceEach("aba", null, null, *) = "aba"
     *  StringUtils.replaceEach("aba", new String[0], null, *) = "aba"
     *  StringUtils.replaceEach("aba", null, new String[0], *) = "aba"
     *  StringUtils.replaceEach("aba", new String[]{"a"}, null, *) = "aba"
     *  StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""}, *) = "b"
     *  StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"}, *) = "aba"
     *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}, *) = "wcte"
     *  (example of how it repeats)
     *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, false) = "dcte"
     *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, true) = "tcte"
     *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, *) = IllegalStateException
     * </pre>
     *
     * @param text
     *            text to search and replace in, no-op if null
     * @param searchList
     *            the Strings to search for, no-op if null
     * @param replacementList
     *            the Strings to replace them with, no-op if null
     * @param repeat
     *            if true, then replace repeatedly until there are no more possible replacements or timeToLive < 0
     * @param timeToLive
     *            if less than 0 then there is a circular reference and endless loop
     * @return the text with any replacements processed, {@code null} if null String input
     * @throws IllegalStateException
     *             if the search is repeating and there is an endless loop due to outputs of one being inputs to another
     * @throws IllegalArgumentException
     *             if the lengths of the arrays are not the same (null is ok, and/or size 0)
     * @since 2.4
     */
    private static String replaceEach(final String text, final String[] searchList, final String[] replacementList, final boolean repeat, final int timeToLive)
    {

        // mchyzer Performance note: This creates very few new objects (one major goal)
        // let me know if there are performance requests, we can create a harness to measure

        if (text == null || text.isEmpty() || searchList == null || searchList.length == 0 || replacementList == null || replacementList.length == 0) { return text; }

        // if recursing, this shouldn't be less than 0
        if (timeToLive < 0) { throw new IllegalStateException("Aborting to protect against StackOverflowError - " + "output of one loop is the input of another"); }

        final int searchLength = searchList.length;
        final int replacementLength = replacementList.length;

        // make sure lengths are ok, these need to be equal
        if (searchLength != replacementLength) { throw new IllegalArgumentException("Search and Replace array lengths don't match: " + searchLength + " vs " + replacementLength); }

        // keep track of which still have matches
        final boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];

        // index on index that the match was found
        int textIndex = -1;
        int replaceIndex = -1;
        int tempIndex = -1;

        // index of replace array that will replace the search string found
        // NOTE: logic duplicated below START
        for (int i = 0; i < searchLength; i++)
        {
            if (noMoreMatchesForReplIndex[i] || searchList[i] == null || searchList[i].isEmpty() || replacementList[i] == null)
            {
                continue;
            }
            tempIndex = text.indexOf(searchList[i]);

            // see if we need to keep searching for this
            if (tempIndex == -1)
            {
                noMoreMatchesForReplIndex[i] = true;
            }
            else
            {
                if (textIndex == -1 || tempIndex < textIndex)
                {
                    textIndex = tempIndex;
                    replaceIndex = i;
                }
            }
        }
        // NOTE: logic mostly below END

        // no search strings found, we are done
        if (textIndex == -1) { return text; }

        int start = 0;

        // get a good guess on the size of the result buffer so it doesn't have to double if it goes over a bit
        int increase = 0;

        // count the replacement text elements that are larger than their corresponding text being replaced
        for (int i = 0; i < searchList.length; i++)
        {
            if (searchList[i] == null || replacementList[i] == null)
            {
                continue;
            }
            final int greater = replacementList[i].length() - searchList[i].length();
            if (greater > 0)
            {
                increase += 3 * greater; // assume 3 matches
            }
        }
        // have upper-bound at 20% increase, then let Java take over
        increase = Math.min(increase, text.length() / 5);

        final StringBuilder buf = new StringBuilder(text.length() + increase);

        while (textIndex != -1)
        {

            for (int i = start; i < textIndex; i++)
            {
                buf.append(text.charAt(i));
            }
            buf.append(replacementList[replaceIndex]);

            start = textIndex + searchList[replaceIndex].length();

            textIndex = -1;
            replaceIndex = -1;
            tempIndex = -1;
            // find the next earliest match
            // NOTE: logic mostly duplicated above START
            for (int i = 0; i < searchLength; i++)
            {
                if (noMoreMatchesForReplIndex[i] || searchList[i] == null || searchList[i].isEmpty() || replacementList[i] == null)
                {
                    continue;
                }
                tempIndex = text.indexOf(searchList[i], start);

                // see if we need to keep searching for this
                if (tempIndex == -1)
                {
                    noMoreMatchesForReplIndex[i] = true;
                }
                else
                {
                    if (textIndex == -1 || tempIndex < textIndex)
                    {
                        textIndex = tempIndex;
                        replaceIndex = i;
                    }
                }
            }
            // NOTE: logic duplicated above END

        }
        final int textLength = text.length();
        for (int i = start; i < textLength; i++)
        {
            buf.append(text.charAt(i));
        }
        final String result = buf.toString();
        if (!repeat) { return result; }

        return replaceEach(result, searchList, replacementList, repeat, timeToLive - 1);
    }

    // Replace, character based
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Replaces all occurrences of a character in a String with another. This is a null-safe version of {@link String#replace(char, char)}.
     * </p>
     *
     * <p>
     * A {@code null} string input returns {@code null}. An empty ("") string input returns an empty string.
     * </p>
     *
     * <pre>
     * StringUtils.replaceChars(null, *, *)        = null
     * StringUtils.replaceChars("", *, *)          = ""
     * StringUtils.replaceChars("abcba", 'b', 'y') = "aycya"
     * StringUtils.replaceChars("abcba", 'z', 'y') = "abcba"
     * </pre>
     *
     * @param str
     *            String to replace characters in, may be null
     * @param searchChar
     *            the character to search for, may be null
     * @param replaceChar
     *            the character to replace, may be null
     * @return modified String, {@code null} if null string input
     * @since 2.0
     */
    public static String replaceChars(final String str, final char searchChar, final char replaceChar)
    {
        if (str == null) { return null; }
        return str.replace(searchChar, replaceChar);
    }

    /**
     * <p>
     * Replaces multiple characters in a String in one go. This method can also be used to delete characters.
     * </p>
     *
     * <p>
     * For example:<br>
     * <code>replaceChars(&quot;hello&quot;, &quot;ho&quot;, &quot;jy&quot;) = jelly</code>.
     * </p>
     *
     * <p>
     * A {@code null} string input returns {@code null}. An empty ("") string input returns an empty string. A null or empty set of search characters returns
     * the input string.
     * </p>
     *
     * <p>
     * The length of the search characters should normally equal the length of the replace characters. If the search characters is longer, then the extra search
     * characters are deleted. If the search characters is shorter, then the extra replace characters are ignored.
     * </p>
     *
     * <pre>
     * StringUtils.replaceChars(null, *, *)           = null
     * StringUtils.replaceChars("", *, *)             = ""
     * StringUtils.replaceChars("abc", null, *)       = "abc"
     * StringUtils.replaceChars("abc", "", *)         = "abc"
     * StringUtils.replaceChars("abc", "b", null)     = "ac"
     * StringUtils.replaceChars("abc", "b", "")       = "ac"
     * StringUtils.replaceChars("abcba", "bc", "yz")  = "ayzya"
     * StringUtils.replaceChars("abcba", "bc", "y")   = "ayya"
     * StringUtils.replaceChars("abcba", "bc", "yzx") = "ayzya"
     * </pre>
     *
     * @param str
     *            String to replace characters in, may be null
     * @param searchChars
     *            a set of characters to search for, may be null
     * @param replaceChars
     *            a set of characters to replace, may be null
     * @return modified String, {@code null} if null string input
     * @since 2.0
     */
    public static String replaceChars(final String str, final String searchChars, String replaceChars)
    {
        if (isEmpty(str) || isEmpty(searchChars)) { return str; }
        if (replaceChars == null)
        {
            replaceChars = EMPTY;
        }
        boolean modified = false;
        final int replaceCharsLength = replaceChars.length();
        final int strLength = str.length();
        final StringBuilder buf = new StringBuilder(strLength);
        for (int i = 0; i < strLength; i++)
        {
            final char ch = str.charAt(i);
            final int index = searchChars.indexOf(ch);
            if (index >= 0)
            {
                modified = true;
                if (index < replaceCharsLength)
                {
                    buf.append(replaceChars.charAt(index));
                }
            }
            else
            {
                buf.append(ch);
            }
        }
        if (modified) { return buf.toString(); }
        return str;
    }

    // Overlay
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Overlays part of a String with another String.
     * </p>
     *
     * <p>
     * A {@code null} string input returns {@code null}. A negative index is treated as zero. An index greater than the string length is treated as the string
     * length. The start index is always the smaller of the two indices.
     * </p>
     *
     * <pre>
     * StringUtils.overlay(null, *, *, *)            = null
     * StringUtils.overlay("", "abc", 0, 0)          = "abc"
     * StringUtils.overlay("abcdef", null, 2, 4)     = "abef"
     * StringUtils.overlay("abcdef", "", 2, 4)       = "abef"
     * StringUtils.overlay("abcdef", "", 4, 2)       = "abef"
     * StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
     * StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
     * StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
     * StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
     * StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
     * StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
     * </pre>
     *
     * @param str
     *            the String to do overlaying in, may be null
     * @param overlay
     *            the String to overlay, may be null
     * @param start
     *            the position to start overlaying at
     * @param end
     *            the position to stop overlaying before
     * @return overlayed String, {@code null} if null String input
     * @since 2.0
     */
    public static String overlay(final String str, String overlay, int start, int end)
    {
        if (str == null) { return null; }
        if (overlay == null)
        {
            overlay = EMPTY;
        }
        final int len = str.length();
        if (start < 0)
        {
            start = 0;
        }
        if (start > len)
        {
            start = len;
        }
        if (end < 0)
        {
            end = 0;
        }
        if (end > len)
        {
            end = len;
        }
        if (start > end)
        {
            final int temp = start;
            start = end;
            end = temp;
        }
        return new StringBuilder(len + start - end + overlay.length() + 1).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
    }

    // Conversion
    // -----------------------------------------------------------------------

    // Padding
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Repeat a String {@code repeat} times to form a new String.
     * </p>
     *
     * <pre>
     * StringUtils.repeat(null, 2) = null
     * StringUtils.repeat("", 0)   = ""
     * StringUtils.repeat("", 2)   = ""
     * StringUtils.repeat("a", 3)  = "aaa"
     * StringUtils.repeat("ab", 2) = "abab"
     * StringUtils.repeat("a", -2) = ""
     * </pre>
     *
     * @param str
     *            the String to repeat, may be null
     * @param repeat
     *            number of times to repeat str, negative treated as zero
     * @return a new String consisting of the original String repeated, {@code null} if null String input
     */
    public static String repeat(final String str, final int repeat)
    {
        // Performance tuned for 2.0 (JDK1.4)

        if (str == null) { return null; }
        if (repeat <= 0) { return EMPTY; }
        final int inputLength = str.length();
        if (repeat == 1 || inputLength == 0) { return str; }
        if (inputLength == 1 && repeat <= PAD_LIMIT) { return repeat(str.charAt(0), repeat); }

        final int outputLength = inputLength * repeat;
        switch (inputLength)
        {
            case 1:
                return repeat(str.charAt(0), repeat);
            case 2:
                final char ch0 = str.charAt(0);
                final char ch1 = str.charAt(1);
                final char[] output2 = new char[outputLength];
                for (int i = repeat * 2 - 2; i >= 0; i--, i--)
                {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                }
                return new String(output2);
            default:
                final StringBuilder buf = new StringBuilder(outputLength);
                for (int i = 0; i < repeat; i++)
                {
                    buf.append(str);
                }
                return buf.toString();
        }
    }

    /**
     * <p>
     * Repeat a String {@code repeat} times to form a new String, with a String separator injected each time.
     * </p>
     *
     * <pre>
     * StringUtils.repeat(null, null, 2) = null
     * StringUtils.repeat(null, "x", 2)  = null
     * StringUtils.repeat("", null, 0)   = ""
     * StringUtils.repeat("", "", 2)     = ""
     * StringUtils.repeat("", "x", 3)    = "xxx"
     * StringUtils.repeat("?", ", ", 3)  = "?, ?, ?"
     * </pre>
     *
     * @param str
     *            the String to repeat, may be null
     * @param separator
     *            the String to inject, may be null
     * @param repeat
     *            number of times to repeat str, negative treated as zero
     * @return a new String consisting of the original String repeated, {@code null} if null String input
     * @since 2.5
     */
    public static String repeat(final String str, final String separator, final int repeat)
    {
        if (str == null || separator == null) { return repeat(str, repeat); }
        // given that repeat(String, int) is quite optimized, better to rely on it than try and splice this into it
        final String result = repeat(str + separator, repeat);
        return removeEnd(result, separator);
    }

    /**
     * <p>
     * Returns padding using the specified delimiter repeated to a given length.
     * </p>
     *
     * <pre>
     * StringUtils.repeat('e', 0)  = ""
     * StringUtils.repeat('e', 3)  = "eee"
     * StringUtils.repeat('e', -2) = ""
     * </pre>
     *
     * <p>
     * Note: this method doesn't not support padding with <a href="http://www.unicode.org/glossary/#supplementary_character">Unicode Supplementary
     * Characters</a> as they require a pair of {@code char}s to be represented. If you are needing to support full I18N of your applications consider using
     * {@link #repeat(String, int)} instead.
     * </p>
     *
     * @param ch
     *            character to repeat
     * @param repeat
     *            number of times to repeat char, negative treated as zero
     * @return String with repeated character
     * @see #repeat(String, int)
     */
    public static String repeat(final char ch, final int repeat)
    {
        final char[] buf = new char[repeat];
        for (int i = repeat - 1; i >= 0; i--)
        {
            buf[i] = ch;
        }
        return new String(buf);
    }

    /**
     * <p>
     * Right pad a String with spaces (' ').
     * </p>
     *
     * <p>
     * The String is padded to the size of {@code size}.
     * </p>
     *
     * <pre>
     * StringUtils.rightPad(null, *)   = null
     * StringUtils.rightPad("", 3)     = "   "
     * StringUtils.rightPad("bat", 3)  = "bat"
     * StringUtils.rightPad("bat", 5)  = "bat  "
     * StringUtils.rightPad("bat", 1)  = "bat"
     * StringUtils.rightPad("bat", -1) = "bat"
     * </pre>
     *
     * @param str
     *            the String to pad out, may be null
     * @param size
     *            the size to pad to
     * @return right padded String or original String if no padding is necessary, {@code null} if null String input
     */
    public static String rightPad(final String str, final int size)
    {
        return rightPad(str, size, ' ');
    }

    /**
     * <p>
     * Right pad a String with a specified character.
     * </p>
     *
     * <p>
     * The String is padded to the size of {@code size}.
     * </p>
     *
     * <pre>
     * StringUtils.rightPad(null, *, *)     = null
     * StringUtils.rightPad("", 3, 'z')     = "zzz"
     * StringUtils.rightPad("bat", 3, 'z')  = "bat"
     * StringUtils.rightPad("bat", 5, 'z')  = "batzz"
     * StringUtils.rightPad("bat", 1, 'z')  = "bat"
     * StringUtils.rightPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str
     *            the String to pad out, may be null
     * @param size
     *            the size to pad to
     * @param padChar
     *            the character to pad with
     * @return right padded String or original String if no padding is necessary, {@code null} if null String input
     * @since 2.0
     */
    public static String rightPad(final String str, final int size, final char padChar)
    {
        if (str == null) { return null; }
        final int pads = size - str.length();
        if (pads <= 0) { return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) { return rightPad(str, size, String.valueOf(padChar)); }
        return str.concat(repeat(padChar, pads));
    }

    /**
     * <p>
     * Right pad a String with a specified String.
     * </p>
     *
     * <p>
     * The String is padded to the size of {@code size}.
     * </p>
     *
     * <pre>
     * StringUtils.rightPad(null, *, *)      = null
     * StringUtils.rightPad("", 3, "z")      = "zzz"
     * StringUtils.rightPad("bat", 3, "yz")  = "bat"
     * StringUtils.rightPad("bat", 5, "yz")  = "batyz"
     * StringUtils.rightPad("bat", 8, "yz")  = "batyzyzy"
     * StringUtils.rightPad("bat", 1, "yz")  = "bat"
     * StringUtils.rightPad("bat", -1, "yz") = "bat"
     * StringUtils.rightPad("bat", 5, null)  = "bat  "
     * StringUtils.rightPad("bat", 5, "")    = "bat  "
     * </pre>
     *
     * @param str
     *            the String to pad out, may be null
     * @param size
     *            the size to pad to
     * @param padStr
     *            the String to pad with, null or empty treated as single space
     * @return right padded String or original String if no padding is necessary, {@code null} if null String input
     */
    public static String rightPad(final String str, final int size, String padStr)
    {
        if (str == null) { return null; }
        if (isEmpty(padStr))
        {
            padStr = SPACE;
        }
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) { return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) { return rightPad(str, size, padStr.charAt(0)); }

        if (pads == padLen)
        {
            return str.concat(padStr);
        }
        else if (pads < padLen)
        {
            return str.concat(padStr.substring(0, pads));
        }
        else
        {
            final char[] padding = new char[pads];
            final char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++)
            {
                padding[i] = padChars[i % padLen];
            }
            return str.concat(new String(padding));
        }
    }

    /**
     * <p>
     * Left pad a String with spaces (' ').
     * </p>
     *
     * <p>
     * The String is padded to the size of {@code size}.
     * </p>
     *
     * <pre>
     * StringUtils.leftPad(null, *)   = null
     * StringUtils.leftPad("", 3)     = "   "
     * StringUtils.leftPad("bat", 3)  = "bat"
     * StringUtils.leftPad("bat", 5)  = "  bat"
     * StringUtils.leftPad("bat", 1)  = "bat"
     * StringUtils.leftPad("bat", -1) = "bat"
     * </pre>
     *
     * @param str
     *            the String to pad out, may be null
     * @param size
     *            the size to pad to
     * @return left padded String or original String if no padding is necessary, {@code null} if null String input
     */
    public static String leftPad(final String str, final int size)
    {
        return leftPad(str, size, ' ');
    }

    /**
     * <p>
     * Left pad a String with a specified character.
     * </p>
     *
     * <p>
     * Pad to a size of {@code size}.
     * </p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)     = null
     * StringUtils.leftPad("", 3, 'z')     = "zzz"
     * StringUtils.leftPad("bat", 3, 'z')  = "bat"
     * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
     * StringUtils.leftPad("bat", 1, 'z')  = "bat"
     * StringUtils.leftPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str
     *            the String to pad out, may be null
     * @param size
     *            the size to pad to
     * @param padChar
     *            the character to pad with
     * @return left padded String or original String if no padding is necessary, {@code null} if null String input
     * @since 2.0
     */
    public static String leftPad(final String str, final int size, final char padChar)
    {
        if (str == null) { return null; }
        final int pads = size - str.length();
        if (pads <= 0) { return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) { return leftPad(str, size, String.valueOf(padChar)); }
        return repeat(padChar, pads).concat(str);
    }

    /**
     * <p>
     * Left pad a String with a specified String.
     * </p>
     *
     * <p>
     * Pad to a size of {@code size}.
     * </p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)      = null
     * StringUtils.leftPad("", 3, "z")      = "zzz"
     * StringUtils.leftPad("bat", 3, "yz")  = "bat"
     * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
     * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
     * StringUtils.leftPad("bat", 1, "yz")  = "bat"
     * StringUtils.leftPad("bat", -1, "yz") = "bat"
     * StringUtils.leftPad("bat", 5, null)  = "  bat"
     * StringUtils.leftPad("bat", 5, "")    = "  bat"
     * </pre>
     *
     * @param str
     *            the String to pad out, may be null
     * @param size
     *            the size to pad to
     * @param padStr
     *            the String to pad with, null or empty treated as single space
     * @return left padded String or original String if no padding is necessary, {@code null} if null String input
     */
    public static String leftPad(final String str, final int size, String padStr)
    {
        if (str == null) { return null; }
        if (isEmpty(padStr))
        {
            padStr = SPACE;
        }
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) { return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) { return leftPad(str, size, padStr.charAt(0)); }

        if (pads == padLen)
        {
            return padStr.concat(str);
        }
        else if (pads < padLen)
        {
            return padStr.substring(0, pads).concat(str);
        }
        else
        {
            final char[] padding = new char[pads];
            final char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++)
            {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }

    /**
     * Gets a CharSequence length or {@code 0} if the CharSequence is {@code null}.
     *
     * @param cs
     *            a CharSequence or {@code null}
     * @return CharSequence length or {@code 0} if the CharSequence is {@code null}.
     * @since 2.4
     * @since 3.0 Changed signature from length(String) to length(CharSequence)
     */
    public static int length(final CharSequence cs)
    {
        return cs == null ? 0 : cs.length();
    }

    // Centering
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Centers a String in a larger String of size {@code size} using the space character (' ').
     * </p>
     *
     * <p>
     * If the size is less than the String length, the String is returned. A {@code null} String returns {@code null}. A negative size is treated as zero.
     * </p>
     *
     * <p>
     * Equivalent to {@code center(str, size, " ")}.
     * </p>
     *
     * <pre>
     * StringUtils.center(null, *)   = null
     * StringUtils.center("", 4)     = "    "
     * StringUtils.center("ab", -1)  = "ab"
     * StringUtils.center("ab", 4)   = " ab "
     * StringUtils.center("abcd", 2) = "abcd"
     * StringUtils.center("a", 4)    = " a  "
     * </pre>
     *
     * @param str
     *            the String to center, may be null
     * @param size
     *            the int size of new String, negative treated as zero
     * @return centered String, {@code null} if null String input
     */
    public static String center(final String str, final int size)
    {
        return center(str, size, ' ');
    }

    /**
     * <p>
     * Centers a String in a larger String of size {@code size}. Uses a supplied character as the value to pad the String with.
     * </p>
     *
     * <p>
     * If the size is less than the String length, the String is returned. A {@code null} String returns {@code null}. A negative size is treated as zero.
     * </p>
     *
     * <pre>
     * StringUtils.center(null, *, *)     = null
     * StringUtils.center("", 4, ' ')     = "    "
     * StringUtils.center("ab", -1, ' ')  = "ab"
     * StringUtils.center("ab", 4, ' ')   = " ab "
     * StringUtils.center("abcd", 2, ' ') = "abcd"
     * StringUtils.center("a", 4, ' ')    = " a  "
     * StringUtils.center("a", 4, 'y')    = "yayy"
     * </pre>
     *
     * @param str
     *            the String to center, may be null
     * @param size
     *            the int size of new String, negative treated as zero
     * @param padChar
     *            the character to pad the new String with
     * @return centered String, {@code null} if null String input
     * @since 2.0
     */
    public static String center(String str, final int size, final char padChar)
    {
        if (str == null || size <= 0) { return str; }
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) { return str; }
        str = leftPad(str, strLen + pads / 2, padChar);
        str = rightPad(str, size, padChar);
        return str;
    }

    /**
     * <p>
     * Centers a String in a larger String of size {@code size}. Uses a supplied String as the value to pad the String with.
     * </p>
     *
     * <p>
     * If the size is less than the String length, the String is returned. A {@code null} String returns {@code null}. A negative size is treated as zero.
     * </p>
     *
     * <pre>
     * StringUtils.center(null, *, *)     = null
     * StringUtils.center("", 4, " ")     = "    "
     * StringUtils.center("ab", -1, " ")  = "ab"
     * StringUtils.center("ab", 4, " ")   = " ab "
     * StringUtils.center("abcd", 2, " ") = "abcd"
     * StringUtils.center("a", 4, " ")    = " a  "
     * StringUtils.center("a", 4, "yz")   = "yayz"
     * StringUtils.center("abc", 7, null) = "  abc  "
     * StringUtils.center("abc", 7, "")   = "  abc  "
     * </pre>
     *
     * @param str
     *            the String to center, may be null
     * @param size
     *            the int size of new String, negative treated as zero
     * @param padStr
     *            the String to pad the new String with, must not be null or empty
     * @return centered String, {@code null} if null String input
     * @throws IllegalArgumentException
     *             if padStr is {@code null} or empty
     */
    public static String center(String str, final int size, String padStr)
    {
        if (str == null || size <= 0) { return str; }
        if (isEmpty(padStr))
        {
            padStr = SPACE;
        }
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) { return str; }
        str = leftPad(str, strLen + pads / 2, padStr);
        str = rightPad(str, size, padStr);
        return str;
    }

    // Case conversion
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Converts a String to upper case as per {@link String#toUpperCase()}.
     * </p>
     *
     * <p>
     * A {@code null} input String returns {@code null}.
     * </p>
     *
     * <pre>
     * StringUtils.upperCase(null)  = null
     * StringUtils.upperCase("")    = ""
     * StringUtils.upperCase("aBc") = "ABC"
     * </pre>
     *
     * <p>
     * <strong>Note:</strong> As described in the documentation for {@link String#toUpperCase()}, the result of this method is affected by the current locale.
     * For platform-independent case transformations, the method {@link #lowerCase(String, Locale)} should be used with a specific locale (e.g.
     * {@link Locale#ENGLISH}).
     * </p>
     *
     * @param str
     *            the String to upper case, may be null
     * @return the upper cased String, {@code null} if null String input
     */
    public static String upperCase(final String str)
    {
        if (str == null) { return null; }
        return str.toUpperCase();
    }

    /**
     * <p>
     * Converts a String to lower case as per {@link String#toLowerCase()}.
     * </p>
     *
     * <p>
     * A {@code null} input String returns {@code null}.
     * </p>
     *
     * <pre>
     * StringUtils.lowerCase(null)  = null
     * StringUtils.lowerCase("")    = ""
     * StringUtils.lowerCase("aBc") = "abc"
     * </pre>
     *
     * <p>

     * </p>
     *
     * @param str
     *            the String to lower case, may be null
     * @return the lower cased String, {@code null} if null String input
     */
    public static String lowerCase(final String str)
    {
        if (str == null) { return null; }
        return str.toLowerCase();
    }

    /**
     * <p>
     * Uncapitalizes a String changing the first letter to title case as per {@link Character#toLowerCase(char)}. No other letters are changed.
     * </p>
     *
     * <p>
     * </p>
     *
     * <pre>
     * StringUtils.uncapitalize(null)  = null
     * StringUtils.uncapitalize("")    = ""
     * StringUtils.uncapitalize("Cat") = "cat"
     * StringUtils.uncapitalize("CAT") = "cAT"
     * </pre>
     *
     * @param str
     *            the String to uncapitalize, may be null
     * @return the uncapitalized String, {@code null} if null String input
     * @see org.apache.commons.lang3.text.WordUtils#uncapitalize(String)
     * @see #capitalize(String)
     * @since 2.0
     */
    public static String uncapitalize(final String str)
    {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) { return str; }

        final char firstChar = str.charAt(0);
        if (Character.isLowerCase(firstChar))
        {
            // already uncapitalized
            return str;
        }

        return new StringBuilder(strLen).append(Character.toLowerCase(firstChar)).append(str.substring(1)).toString();
    }

    /**
     * <p>
     * Counts how many times the char appears in the given string.
     * </p>
     *
     * <p>
     * A {@code null} or empty ("") String input returns {@code 0}.
     * </p>
     *
     * <pre>
     * StringUtils.countMatches(null, *)       = 0
     * StringUtils.countMatches("", *)         = 0
     * StringUtils.countMatches("abba", 0)  = 0
     * StringUtils.countMatches("abba", 'a')   = 2
     * StringUtils.countMatches("abba", 'b')  = 2
     * StringUtils.countMatches("abba", 'x') = 0
     * </pre>
     *
     * @param str
     *            the CharSequence to check, may be null
     * @param ch
     *            the char to count
     * @return the number of occurrences, 0 if the CharSequence is {@code null}
     * @since 3.4
     */
    public static int countMatches(final CharSequence str, final char ch)
    {
        if (isEmpty(str)) { return 0; }
        int count = 0;
        // We could also call str.toCharArray() for faster look ups but that would generate more garbage.
        for (int i = 0; i < str.length(); i++)
        {
            if (ch == str.charAt(i))
            {
                count++;
            }
        }
        return count;
    }

    // Character Tests
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Checks if the CharSequence contains only Unicode letters.
     * </p>
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.isAlpha(null)   = false
     * StringUtils.isAlpha("")     = false
     * StringUtils.isAlpha("  ")   = false
     * StringUtils.isAlpha("abc")  = true
     * StringUtils.isAlpha("ab2c") = false
     * StringUtils.isAlpha("ab-c") = false
     * </pre>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if only contains letters, and is non-null
     * @since 3.0 Changed signature from isAlpha(String) to isAlpha(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    public static boolean isAlpha(final CharSequence cs)
    {
        if (isEmpty(cs)) { return false; }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++)
        {
            if (Character.isLetter(cs.charAt(i)) == false) { return false; }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the CharSequence contains only Unicode letters and space (' ').
     * </p>
     *
     * <p>
     * {@code null} will return {@code false} An empty CharSequence (length()=0) will return {@code true}.
     * </p>
     *
     * <pre>
     * StringUtils.isAlphaSpace(null)   = false
     * StringUtils.isAlphaSpace("")     = true
     * StringUtils.isAlphaSpace("  ")   = true
     * StringUtils.isAlphaSpace("abc")  = true
     * StringUtils.isAlphaSpace("ab c") = true
     * StringUtils.isAlphaSpace("ab2c") = false
     * StringUtils.isAlphaSpace("ab-c") = false
     * </pre>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if only contains letters and space, and is non-null
     * @since 3.0 Changed signature from isAlphaSpace(String) to isAlphaSpace(CharSequence)
     */
    public static boolean isAlphaSpace(final CharSequence cs)
    {
        if (cs == null) { return false; }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++)
        {
            if (Character.isLetter(cs.charAt(i)) == false && cs.charAt(i) != ' ') { return false; }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the CharSequence contains only Unicode letters or digits.
     * </p>
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = false
     * StringUtils.isAlphanumeric("  ")   = false
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = false
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if only contains letters or digits, and is non-null
     * @since 3.0 Changed signature from isAlphanumeric(String) to isAlphanumeric(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    public static boolean isAlphanumeric(final CharSequence cs)
    {
        if (isEmpty(cs)) { return false; }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++)
        {
            if (Character.isLetterOrDigit(cs.charAt(i)) == false) { return false; }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the CharSequence contains only Unicode letters, digits or space ({@code ' '}).
     * </p>
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code true}.
     * </p>
     *
     * <pre>
     * StringUtils.isAlphanumericSpace(null)   = false
     * StringUtils.isAlphanumericSpace("")     = true
     * StringUtils.isAlphanumericSpace("  ")   = true
     * StringUtils.isAlphanumericSpace("abc")  = true
     * StringUtils.isAlphanumericSpace("ab c") = true
     * StringUtils.isAlphanumericSpace("ab2c") = true
     * StringUtils.isAlphanumericSpace("ab-c") = false
     * </pre>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if only contains letters, digits or space, and is non-null
     * @since 3.0 Changed signature from isAlphanumericSpace(String) to isAlphanumericSpace(CharSequence)
     */
    public static boolean isAlphanumericSpace(final CharSequence cs)
    {
        if (cs == null) { return false; }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++)
        {
            if (Character.isLetterOrDigit(cs.charAt(i)) == false && cs.charAt(i) != ' ') { return false; }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the CharSequence contains only Unicode digits. A decimal point is not a Unicode digit and returns false.
     * </p>
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code false}.
     * </p>
     *
     * <p>
     * Note that the method does not allow for a leading sign, either positive or negative. Also, if a String passes the numeric test, it may still generate a
     * NumberFormatException when parsed by Integer.parseInt or Long.parseLong, e.g. if the value is outside the range for int or long respectively.
     * </p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * StringUtils.isNumeric("-123") = false
     * StringUtils.isNumeric("+123") = false
     * </pre>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if only contains digits, and is non-null
     * @since 3.0 Changed signature from isNumeric(String) to isNumeric(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    public static boolean isNumeric(final CharSequence cs)
    {
        if (isEmpty(cs)) { return false; }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++)
        {
            if (Character.isDigit(cs.charAt(i)) == false) { return false; }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the CharSequence contains only Unicode digits or space ({@code ' '}). A decimal point is not a Unicode digit and returns false.
     * </p>
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code true}.
     * </p>
     *
     * <pre>
     * StringUtils.isNumericSpace(null)   = false
     * StringUtils.isNumericSpace("")     = true
     * StringUtils.isNumericSpace("  ")   = true
     * StringUtils.isNumericSpace("123")  = true
     * StringUtils.isNumericSpace("12 3") = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("\u0967\u0968 \u0969")  = true
     * StringUtils.isNumericSpace("ab2c") = false
     * StringUtils.isNumericSpace("12-3") = false
     * StringUtils.isNumericSpace("12.3") = false
     * </pre>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if only contains digits or space, and is non-null
     * @since 3.0 Changed signature from isNumericSpace(String) to isNumericSpace(CharSequence)
     */
    public static boolean isNumericSpace(final CharSequence cs)
    {
        if (cs == null) { return false; }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++)
        {
            if (Character.isDigit(cs.charAt(i)) == false && cs.charAt(i) != ' ') { return false; }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the CharSequence contains only lowercase characters.
     * </p>
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.isAllLowerCase(null)   = false
     * StringUtils.isAllLowerCase("")     = false
     * StringUtils.isAllLowerCase("  ")   = false
     * StringUtils.isAllLowerCase("abc")  = true
     * StringUtils.isAllLowerCase("abC") = false
     * </pre>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if only contains lowercase characters, and is non-null
     * @since 2.5
     * @since 3.0 Changed signature from isAllLowerCase(String) to isAllLowerCase(CharSequence)
     */
    public static boolean isAllLowerCase(final CharSequence cs)
    {
        if (cs == null || isEmpty(cs)) { return false; }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++)
        {
            if (Character.isLowerCase(cs.charAt(i)) == false) { return false; }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the CharSequence contains only uppercase characters.
     * </p>
     *
     * <p>
     * {@code null} will return {@code false}. An empty String (length()=0) will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.isAllUpperCase(null)   = false
     * StringUtils.isAllUpperCase("")     = false
     * StringUtils.isAllUpperCase("  ")   = false
     * StringUtils.isAllUpperCase("ABC")  = true
     * StringUtils.isAllUpperCase("aBC") = false
     * </pre>
     *
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if only contains uppercase characters, and is non-null
     * @since 2.5
     * @since 3.0 Changed signature from isAllUpperCase(String) to isAllUpperCase(CharSequence)
     */
    public static boolean isAllUpperCase(final CharSequence cs)
    {
        if (cs == null || isEmpty(cs)) { return false; }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++)
        {
            if (Character.isUpperCase(cs.charAt(i)) == false) { return false; }
        }
        return true;
    }

    // Defaults
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Returns either the passed in String, or if the String is {@code null}, an empty String ("").
     * </p>
     *
     * <pre>
     * StringUtils.defaultString(null)  = ""
     * StringUtils.defaultString("")    = ""
     * StringUtils.defaultString("bat") = "bat"
     * </pre>
     *
     *
     * @see String#valueOf(Object)
     * @param str
     *            the String to check, may be null
     * @return the passed in String, or the empty String if it was {@code null}
     */
    public static String defaultString(final String str)
    {
        return str == null ? EMPTY : str;
    }

    /**
     * <p>
     * Returns either the passed in String, or if the String is {@code null}, the value of {@code defaultStr}.
     * </p>
     *
     * <pre>
     * StringUtils.defaultString(null, "NULL")  = "NULL"
     * StringUtils.defaultString("", "NULL")    = ""
     * StringUtils.defaultString("bat", "NULL") = "bat"
     * </pre>
     *
     *
     * @see String#valueOf(Object)
     * @param str
     *            the String to check, may be null
     * @param defaultStr
     *            the default String to return if the input is {@code null}, may be null
     * @return the passed in String, or the default if it was {@code null}
     */
    public static String defaultString(final String str, final String defaultStr)
    {
        return str == null ? defaultStr : str;
    }

    /**
     * <p>
     * Returns either the passed in CharSequence, or if the CharSequence is empty or {@code null}, the value of {@code defaultStr}.
     * </p>
     *
     * <pre>
     * StringUtils.defaultIfEmpty(null, "NULL")  = "NULL"
     * StringUtils.defaultIfEmpty("", "NULL")    = "NULL"
     * StringUtils.defaultIfEmpty(" ", "NULL")   = " "
     * StringUtils.defaultIfEmpty("bat", "NULL") = "bat"
     * StringUtils.defaultIfEmpty("", null)      = null
     * </pre>
     *
     * @param <T>
     *            the specific kind of CharSequence
     * @param str
     *            the CharSequence to check, may be null
     * @param defaultStr
     *            the default CharSequence to return if the input is empty ("") or {@code null}, may be null
     * @return the passed in CharSequence, or the default
     *
     */
    public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr)
    {
        return isEmpty(str) ? defaultStr : str;
    }

    // Reversing
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Reverses a String as per {@link StringBuilder#reverse()}.
     * </p>
     *
     * <p>
     * A {@code null} String returns {@code null}.
     * </p>
     *
     * <pre>
     * StringUtils.reverse(null)  = null
     * StringUtils.reverse("")    = ""
     * StringUtils.reverse("bat") = "tab"
     * </pre>
     *
     * @param str
     *            the String to reverse, may be null
     * @return the reversed String, {@code null} if null String input
     */
    public static String reverse(final String str)
    {
        if (str == null) { return null; }
        return new StringBuilder(str).reverse().toString();
    }

    // Abbreviating
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Abbreviates a String using ellipses. This will turn "Now is the time for all good men" into "Now is the time for..."
     * </p>
     *
     * <p>
     * Specifically:
     * </p>
     * <ul>
     * <li>If {@code str} is less than {@code maxWidth} characters long, return it.</li>
     * <li>Else abbreviate it to {@code (substring(str, 0, max-3) + "...")}.</li>
     * <li>If {@code maxWidth} is less than {@code 4}, throw an {@code IllegalArgumentException}.</li>
     * <li>In no case will it return a String of length greater than {@code maxWidth}.</li>
     * </ul>
     *
     * <pre>
     * StringUtils.abbreviate(null, *)      = null
     * StringUtils.abbreviate("", 4)        = ""
     * StringUtils.abbreviate("abcdefg", 6) = "abc..."
     * StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
     * StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
     * StringUtils.abbreviate("abcdefg", 4) = "a..."
     * StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException
     * </pre>
     *
     * @param str
     *            the String to check, may be null
     * @param maxWidth
     *            maximum length of result String, must be at least 4
     * @return abbreviated String, {@code null} if null String input
     * @throws IllegalArgumentException
     *             if the width is too small
     * @since 2.0
     */
    public static String abbreviate(final String str, final int maxWidth)
    {
        return abbreviate(str, 0, maxWidth);
    }

    /**
     * <p>
     * Abbreviates a String using ellipses. This will turn "Now is the time for all good men" into "...is the time for..."
     * </p>
     *
     * <p>
     * Works like {@code abbreviate(String, int)}, but allows you to specify a "left edge" offset. Note that this left edge is not necessarily going to be the
     * leftmost character in the result, or the first character following the ellipses, but it will appear somewhere in the result.
     *
     * <p>
     * In no case will it return a String of length greater than {@code maxWidth}.
     * </p>
     *
     * <pre>
     * StringUtils.abbreviate(null, *, *)                = null
     * StringUtils.abbreviate("", 0, 4)                  = ""
     * StringUtils.abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
     * StringUtils.abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
     * StringUtils.abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
     * StringUtils.abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
     * StringUtils.abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
     * StringUtils.abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
     * StringUtils.abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
     * StringUtils.abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
     * StringUtils.abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
     * StringUtils.abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
     * StringUtils.abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
     * </pre>
     *
     * @param str
     *            the String to check, may be null
     * @param offset
     *            left edge of source String
     * @param maxWidth
     *            maximum length of result String, must be at least 4
     * @return abbreviated String, {@code null} if null String input
     * @throws IllegalArgumentException
     *             if the width is too small
     * @since 2.0
     */
    public static String abbreviate(final String str, int offset, final int maxWidth)
    {
        if (str == null) { return null; }
        if (maxWidth < 4) { throw new IllegalArgumentException("Minimum abbreviation width is 4"); }
        if (str.length() <= maxWidth) { return str; }
        if (offset > str.length())
        {
            offset = str.length();
        }
        if (str.length() - offset < maxWidth - 3)
        {
            offset = str.length() - (maxWidth - 3);
        }
        final String abrevMarker = "...";
        if (offset <= 4) { return str.substring(0, maxWidth - 3) + abrevMarker; }
        if (maxWidth < 7) { throw new IllegalArgumentException("Minimum abbreviation width with offset is 7"); }
        if (offset + maxWidth - 3 < str.length()) { return abrevMarker + abbreviate(str.substring(offset), maxWidth - 3); }
        return abrevMarker + str.substring(str.length() - (maxWidth - 3));
    }

    /**
     * <p>
     * Abbreviates a String to the length passed, replacing the middle characters with the supplied replacement String.
     * </p>
     *
     * <p>
     * This abbreviation only occurs if the following criteria is met:
     * </p>
     * <ul>
     * <li>Neither the String for abbreviation nor the replacement String are null or empty</li>
     * <li>The length to truncate to is less than the length of the supplied String</li>
     * <li>The length to truncate to is greater than 0</li>
     * <li>The abbreviated String will have enough room for the length supplied replacement String and the first and last characters of the supplied String for
     * abbreviation</li>
     * </ul>
     * <p>
     * Otherwise, the returned String will be the same as the supplied String for abbreviation.
     * </p>
     *
     * <pre>
     * StringUtils.abbreviateMiddle(null, null, 0)      = null
     * StringUtils.abbreviateMiddle("abc", null, 0)      = "abc"
     * StringUtils.abbreviateMiddle("abc", ".", 0)      = "abc"
     * StringUtils.abbreviateMiddle("abc", ".", 3)      = "abc"
     * StringUtils.abbreviateMiddle("abcdef", ".", 4)     = "ab.f"
     * </pre>
     *
     * @param str
     *            the String to abbreviate, may be null
     * @param middle
     *            the String to replace the middle characters with, may be null
     * @param length
     *            the length to abbreviate {@code str} to.
     * @return the abbreviated String if the above criteria is met, or the original String supplied for abbreviation.
     * @since 2.5
     */
    public static String abbreviateMiddle(final String str, final String middle, final int length)
    {
        if (isEmpty(str) || isEmpty(middle)) { return str; }

        if (length >= str.length() || length < middle.length() + 2) { return str; }

        final int targetSting = length - middle.length();
        final int startOffset = targetSting / 2 + targetSting % 2;
        final int endOffset = str.length() - targetSting / 2;

        final StringBuilder builder = new StringBuilder(length);
        builder.append(str.substring(0, startOffset));
        builder.append(middle);
        builder.append(str.substring(endOffset));

        return builder.toString();
    }

    // Difference
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Compares two Strings, and returns the portion where they differ. More precisely, return the remainder of the second String, starting from where it's
     * different from the first. This means that the difference between "abc" and "ab" is the empty String and not "c".
     * </p>
     *
     * <p>
     * For example, {@code difference("i am a machine", "i am a robot") -> "robot"}.
     * </p>
     *
     * <pre>
     * StringUtils.difference(null, null) = null
     * StringUtils.difference("", "") = ""
     * StringUtils.difference("", "abc") = "abc"
     * StringUtils.difference("abc", "") = ""
     * StringUtils.difference("abc", "abc") = ""
     * StringUtils.difference("abc", "ab") = ""
     * StringUtils.difference("ab", "abxyz") = "xyz"
     * StringUtils.difference("abcde", "abxyz") = "xyz"
     * StringUtils.difference("abcde", "xyz") = "xyz"
     * </pre>
     *
     * @param str1
     *            the first String, may be null
     * @param str2
     *            the second String, may be null
     * @return the portion of str2 where it differs from str1; returns the empty String if they are equal
     * @see #indexOfDifference(CharSequence,CharSequence)
     * @since 2.0
     */
    public static String difference(final String str1, final String str2)
    {
        if (str1 == null) { return str2; }
        if (str2 == null) { return str1; }
        final int at = indexOfDifference(str1, str2);
        if (at == INDEX_NOT_FOUND) { return EMPTY; }
        return str2.substring(at);
    }

    /**
     * <p>
     * Compares two CharSequences, and returns the index at which the CharSequences begin to differ.
     * </p>
     *
     * <p>
     * For example, {@code indexOfDifference("i am a machine", "i am a robot") -> 7}
     * </p>
     *
     * <pre>
     * StringUtils.indexOfDifference(null, null) = -1
     * StringUtils.indexOfDifference("", "") = -1
     * StringUtils.indexOfDifference("", "abc") = 0
     * StringUtils.indexOfDifference("abc", "") = 0
     * StringUtils.indexOfDifference("abc", "abc") = -1
     * StringUtils.indexOfDifference("ab", "abxyz") = 2
     * StringUtils.indexOfDifference("abcde", "abxyz") = 2
     * StringUtils.indexOfDifference("abcde", "xyz") = 0
     * </pre>
     *
     * @param cs1
     *            the first CharSequence, may be null
     * @param cs2
     *            the second CharSequence, may be null
     * @return the index where cs1 and cs2 begin to differ; -1 if they are equal
     * @since 2.0
     * @since 3.0 Changed signature from indexOfDifference(String, String) to indexOfDifference(CharSequence, CharSequence)
     */
    public static int indexOfDifference(final CharSequence cs1, final CharSequence cs2)
    {
        if (cs1 == cs2) { return INDEX_NOT_FOUND; }
        if (cs1 == null || cs2 == null) { return 0; }
        int i;
        for (i = 0; i < cs1.length() && i < cs2.length(); ++i)
        {
            if (cs1.charAt(i) != cs2.charAt(i))
            {
                break;
            }
        }
        if (i < cs2.length() || i < cs1.length()) { return i; }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>
     * Compares all CharSequences in an array and returns the index at which the CharSequences begin to differ.
     * </p>
     *
     * <p>
     * For example, <code>indexOfDifference(new String[] {"i am a machine", "i am a robot"}) -&gt; 7</code>
     * </p>
     *
     * <pre>
     * StringUtils.indexOfDifference(null) = -1
     * StringUtils.indexOfDifference(new String[] {}) = -1
     * StringUtils.indexOfDifference(new String[] {"abc"}) = -1
     * StringUtils.indexOfDifference(new String[] {null, null}) = -1
     * StringUtils.indexOfDifference(new String[] {"", ""}) = -1
     * StringUtils.indexOfDifference(new String[] {"", null}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", null, null}) = 0
     * StringUtils.indexOfDifference(new String[] {null, null, "abc"}) = 0
     * StringUtils.indexOfDifference(new String[] {"", "abc"}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", ""}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", "abc"}) = -1
     * StringUtils.indexOfDifference(new String[] {"abc", "a"}) = 1
     * StringUtils.indexOfDifference(new String[] {"ab", "abxyz"}) = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "abxyz"}) = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "xyz"}) = 0
     * StringUtils.indexOfDifference(new String[] {"xyz", "abcde"}) = 0
     * StringUtils.indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
     * </pre>
     *
     * @param css
     *            array of CharSequences, entries may be null
     * @return the index where the strings begin to differ; -1 if they are all equal
     * @since 2.4
     * @since 3.0 Changed signature from indexOfDifference(String...) to indexOfDifference(CharSequence...)
     */
    public static int indexOfDifference(final CharSequence... css)
    {
        if (css == null || css.length <= 1) { return INDEX_NOT_FOUND; }
        boolean anyStringNull = false;
        boolean allStringsNull = true;
        final int arrayLen = css.length;
        int shortestStrLen = Integer.MAX_VALUE;
        int longestStrLen = 0;

        // find the min and max string lengths; this avoids checking to make
        // sure we are not exceeding the length of the string each time through
        // the bottom loop.
        for (int i = 0; i < arrayLen; i++)
        {
            if (css[i] == null)
            {
                anyStringNull = true;
                shortestStrLen = 0;
            }
            else
            {
                allStringsNull = false;
                shortestStrLen = Math.min(css[i].length(), shortestStrLen);
                longestStrLen = Math.max(css[i].length(), longestStrLen);
            }
        }

        // handle lists containing all nulls or all empty strings
        if (allStringsNull || longestStrLen == 0 && !anyStringNull) { return INDEX_NOT_FOUND; }

        // handle lists containing some nulls or some empty strings
        if (shortestStrLen == 0) { return 0; }

        // find the position with the first difference across all strings
        int firstDiff = -1;
        for (int stringPos = 0; stringPos < shortestStrLen; stringPos++)
        {
            final char comparisonChar = css[0].charAt(stringPos);
            for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++)
            {
                if (css[arrayPos].charAt(stringPos) != comparisonChar)
                {
                    firstDiff = stringPos;
                    break;
                }
            }
            if (firstDiff != -1)
            {
                break;
            }
        }

        if (firstDiff == -1 && shortestStrLen != longestStrLen)
        {
            // we compared all of the characters up to the length of the
            // shortest string and didn't find a match, but the string lengths
            // vary, so return the length of the shortest string.
            return shortestStrLen;
        }
        return firstDiff;
    }

    /**
     * <p>
     * Compares all Strings in an array and returns the initial sequence of characters that is common to all of them.
     * </p>
     *
     * <p>
     * For example, <code>getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) -&gt; "i am a "</code>
     * </p>
     *
     * <pre>
     * StringUtils.getCommonPrefix(null) = ""
     * StringUtils.getCommonPrefix(new String[] {}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc"}) = "abc"
     * StringUtils.getCommonPrefix(new String[] {null, null}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", ""}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", null}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", null, null}) = ""
     * StringUtils.getCommonPrefix(new String[] {null, null, "abc"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", "abc"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", ""}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", "abc"}) = "abc"
     * StringUtils.getCommonPrefix(new String[] {"abc", "a"}) = "a"
     * StringUtils.getCommonPrefix(new String[] {"ab", "abxyz"}) = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"}) = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "xyz"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"xyz", "abcde"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) = "i am a "
     * </pre>
     *
     * @param strs
     *            array of String objects, entries may be null
     * @return the initial sequence of characters that are common to all Strings in the array; empty String if the array is null, the elements are all null or
     *         if there is no common prefix.
     * @since 2.4
     */
    public static String getCommonPrefix(final String... strs)
    {
        if (strs == null || strs.length == 0) { return EMPTY; }
        final int smallestIndexOfDiff = indexOfDifference(strs);
        if (smallestIndexOfDiff == INDEX_NOT_FOUND)
        {
            // all strings were identical
            if (strs[0] == null) { return EMPTY; }
            return strs[0];
        }
        else if (smallestIndexOfDiff == 0)
        {
            // there were no common initial characters
            return EMPTY;
        }
        else
        {
            // we found a common initial character sequence
            return strs[0].substring(0, smallestIndexOfDiff);
        }
    }

    // Misc
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Find the Levenshtein distance between two Strings.
     * </p>
     *
     * <p>
     * This is the number of changes needed to change one String into another, where each change is a single character modification (deletion, insertion or
     * substitution).
     * </p>
     *
     * <p>
     * The previous implementation of the Levenshtein distance algorithm was from <a
     * href="http://www.merriampark.com/ld.htm">http://www.merriampark.com/ld.htm</a>
     * </p>
     *
     * <p>
     * Chas Emerick has written an implementation in Java, which avoids an OutOfMemoryError which can occur when my Java implementation is used with very large
     * strings.<br>
     * This implementation of the Levenshtein distance algorithm is from <a
     * href="http://www.merriampark.com/ldjava.htm">http://www.merriampark.com/ldjava.htm</a>
     * </p>
     *
     * <pre>
     * StringUtils.getLevenshteinDistance(null, *)             = IllegalArgumentException
     * StringUtils.getLevenshteinDistance(*, null)             = IllegalArgumentException
     * StringUtils.getLevenshteinDistance("","")               = 0
     * StringUtils.getLevenshteinDistance("","a")              = 1
     * StringUtils.getLevenshteinDistance("aaapppp", "")       = 7
     * StringUtils.getLevenshteinDistance("frog", "fog")       = 1
     * StringUtils.getLevenshteinDistance("fly", "ant")        = 3
     * StringUtils.getLevenshteinDistance("elephant", "hippo") = 7
     * StringUtils.getLevenshteinDistance("hippo", "elephant") = 7
     * StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz") = 8
     * StringUtils.getLevenshteinDistance("hello", "hallo")    = 1
     * </pre>
     *
     * @param s
     *            the first String, must not be null
     * @param t
     *            the second String, must not be null
     * @return result distance
     * @throws IllegalArgumentException
     *             if either String input {@code null}
     * @since 3.0 Changed signature from getLevenshteinDistance(String, String) to getLevenshteinDistance(CharSequence, CharSequence)
     */
    public static int getLevenshteinDistance(CharSequence s, CharSequence t)
    {
        if (s == null || t == null) { throw new IllegalArgumentException("Strings must not be null"); }

        /*
         * The difference between this impl. and the previous is that, rather than creating and retaining a matrix of size s.length() + 1 by t.length() + 1, we
         * maintain two single-dimensional arrays of length s.length() + 1. The first, d, is the 'current working' distance array that maintains the newest
         * distance cost counts as we iterate through the characters of String s. Each time we increment the index of String t we are comparing, d is copied to
         * p, the second int[]. Doing so allows us to retain the previous cost counts as required by the algorithm (taking the minimum of the cost count to the
         * left, up one, and diagonally up and to the left of the current cost count being calculated). (Note that the arrays aren't really copied anymore, just
         * switched...this is clearly much better than cloning an array or doing a System.arraycopy() each time through the outer loop.)
         *
         * Effectively, the difference between the two implementations is this one does not cause an out of memory condition when calculating the LD over two
         * very large strings.
         */

        int n = s.length(); // length of s
        int m = t.length(); // length of t

        if (n == 0)
        {
            return m;
        }
        else if (m == 0) { return n; }

        if (n > m)
        {
            // swap the input strings to consume less memory
            final CharSequence tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }

        int p[] = new int[n + 1]; // 'previous' cost array, horizontally
        int d[] = new int[n + 1]; // cost array, horizontally
        int _d[]; // placeholder to assist in swapping p and d

        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t

        char t_j; // jth character of t

        int cost; // cost

        for (i = 0; i <= n; i++)
        {
            p[i] = i;
        }

        for (j = 1; j <= m; j++)
        {
            t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++)
            {
                cost = s.charAt(i - 1) == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return p[n];
    }

    /**
     * <p>
     * Find the Levenshtein distance between two Strings if it's less than or equal to a given threshold.
     * </p>
     *
     * <p>
     * This is the number of changes needed to change one String into another, where each change is a single character modification (deletion, insertion or
     * substitution).
     * </p>
     *
     * <p>
     * This implementation follows from Algorithms on Strings, Trees and Sequences by Dan Gusfield and Chas Emerick's implementation of the Levenshtein distance
     * algorithm from <a href="http://www.merriampark.com/ld.htm">http://www.merriampark.com/ld.htm</a>
     * </p>
     *
     * <pre>
     * StringUtils.getLevenshteinDistance(null, *, *)             = IllegalArgumentException
     * StringUtils.getLevenshteinDistance(*, null, *)             = IllegalArgumentException
     * StringUtils.getLevenshteinDistance(*, *, -1)               = IllegalArgumentException
     * StringUtils.getLevenshteinDistance("","", 0)               = 0
     * StringUtils.getLevenshteinDistance("aaapppp", "", 8)       = 7
     * StringUtils.getLevenshteinDistance("aaapppp", "", 7)       = 7
     * StringUtils.getLevenshteinDistance("aaapppp", "", 6))      = -1
     * StringUtils.getLevenshteinDistance("elephant", "hippo", 7) = 7
     * StringUtils.getLevenshteinDistance("elephant", "hippo", 6) = -1
     * StringUtils.getLevenshteinDistance("hippo", "elephant", 7) = 7
     * StringUtils.getLevenshteinDistance("hippo", "elephant", 6) = -1
     * </pre>
     *
     * @param s
     *            the first String, must not be null
     * @param t
     *            the second String, must not be null
     * @param threshold
     *            the target threshold, must not be negative
     * @return result distance, or {@code -1} if the distance would be greater than the threshold
     * @throws IllegalArgumentException
     *             if either String input {@code null} or negative threshold
     */
    public static int getLevenshteinDistance(CharSequence s, CharSequence t, final int threshold)
    {
        if (s == null || t == null) { throw new IllegalArgumentException("Strings must not be null"); }
        if (threshold < 0) { throw new IllegalArgumentException("Threshold must not be negative"); }

        /*
         * This implementation only computes the distance if it's less than or equal to the threshold value, returning -1 if it's greater. The advantage is
         * performance: unbounded distance is O(nm), but a bound of k allows us to reduce it to O(km) time by only computing a diagonal stripe of width 2k + 1
         * of the cost table. It is also possible to use this to compute the unbounded Levenshtein distance by starting the threshold at 1 and doubling each
         * time until the distance is found; this is O(dm), where d is the distance.
         *
         * One subtlety comes from needing to ignore entries on the border of our stripe eg. p[] = |#|#|#|* d[] = *|#|#|#| We must ignore the entry to the left
         * of the leftmost member We must ignore the entry above the rightmost member
         *
         * Another subtlety comes from our stripe running off the matrix if the strings aren't of the same size. Since string s is always swapped to be the
         * shorter of the two, the stripe will always run off to the upper right instead of the lower left of the matrix.
         *
         * As a concrete example, suppose s is of length 5, t is of length 7, and our threshold is 1. In this case we're going to walk a stripe of length 3. The
         * matrix would look like so:
         *
         * 1 2 3 4 5 1 |#|#| | | | 2 |#|#|#| | | 3 | |#|#|#| | 4 | | |#|#|#| 5 | | | |#|#| 6 | | | | |#| 7 | | | | | |
         *
         * Note how the stripe leads off the table as there is no possible way to turn a string of length 5 into one of length 7 in edit distance of 1.
         *
         * Additionally, this implementation decreases memory usage by using two single-dimensional arrays and swapping them back and forth instead of
         * allocating an entire n by m matrix. This requires a few minor changes, such as immediately returning when it's detected that the stripe has run off
         * the matrix and initially filling the arrays with large values so that entries we don't compute are ignored.
         *
         * See Algorithms on Strings, Trees and Sequences by Dan Gusfield for some discussion.
         */

        int n = s.length(); // length of s
        int m = t.length(); // length of t

        // if one string is empty, the edit distance is necessarily the length of the other
        if (n == 0)
        {
            return m <= threshold ? m : -1;
        }
        else if (m == 0) { return n <= threshold ? n : -1; }

        if (n > m)
        {
            // swap the two strings to consume less memory
            final CharSequence tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }

        int p[] = new int[n + 1]; // 'previous' cost array, horizontally
        int d[] = new int[n + 1]; // cost array, horizontally
        int _d[]; // placeholder to assist in swapping p and d

        // fill in starting table values
        final int boundary = Math.min(n, threshold) + 1;
        for (int i = 0; i < boundary; i++)
        {
            p[i] = i;
        }
        // these fills ensure that the value above the rightmost entry of our
        // stripe will be ignored in following loop iterations
        Arrays.fill(p, boundary, p.length, Integer.MAX_VALUE);
        Arrays.fill(d, Integer.MAX_VALUE);

        // iterates through t
        for (int j = 1; j <= m; j++)
        {
            final char t_j = t.charAt(j - 1); // jth character of t
            d[0] = j;

            // compute stripe indices, constrain to array size
            final int min = Math.max(1, j - threshold);
            final int max = (j > Integer.MAX_VALUE - threshold) ? n : Math.min(n, j + threshold);

            // the stripe may lead off of the table if s and t are of different sizes
            if (min > max) { return -1; }

            // ignore entry left of leftmost
            if (min > 1)
            {
                d[min - 1] = Integer.MAX_VALUE;
            }

            // iterates through [min, max] in s
            for (int i = min; i <= max; i++)
            {
                if (s.charAt(i - 1) == t_j)
                {
                    // diagonally left and up
                    d[i] = p[i - 1];
                }
                else
                {
                    // 1 + minimum of cell to the left, to the top, diagonally left and up
                    d[i] = 1 + Math.min(Math.min(d[i - 1], p[i]), p[i - 1]);
                }
            }

            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        // if p[n] is greater than the threshold, there's no guarantee on it being the correct
        // distance
        if (p[n] <= threshold) { return p[n]; }
        return -1;
    }

    /**
     * <p>
     * Find the Jaro Winkler Distance which indicates the similarity score between two Strings.
     * </p>
     *
     * <p>
     * The Jaro measure is the weighted sum of percentage of matched characters from each file and transposed characters. Winkler increased this measure for
     * matching initial characters.
     * </p>
     *
     * <p>
     * This implementation is based on the Jaro Winkler similarity algorithm from <a
     * href="http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance</a>.
     * </p>
     *
     * <pre>
     * StringUtils.getJaroWinklerDistance(null, null)          = IllegalArgumentException
     * StringUtils.getJaroWinklerDistance("","")               = 0.0
     * StringUtils.getJaroWinklerDistance("","a")              = 0.0
     * StringUtils.getJaroWinklerDistance("aaapppp", "")       = 0.0
     * StringUtils.getJaroWinklerDistance("frog", "fog")       = 0.93
     * StringUtils.getJaroWinklerDistance("fly", "ant")        = 0.0
     * StringUtils.getJaroWinklerDistance("elephant", "hippo") = 0.44
     * StringUtils.getJaroWinklerDistance("hippo", "elephant") = 0.44
     * StringUtils.getJaroWinklerDistance("hippo", "zzzzzzzz") = 0.0
     * StringUtils.getJaroWinklerDistance("hello", "hallo")    = 0.88
     * StringUtils.getJaroWinklerDistance("ABC Corporation", "ABC Corp") = 0.91
     * StringUtils.getJaroWinklerDistance("D N H Enterprises Inc", "D &amp; H Enterprises, Inc.") = 0.93
     * StringUtils.getJaroWinklerDistance("My Gym Children's Fitness Center", "My Gym. Childrens Fitness") = 0.94
     * StringUtils.getJaroWinklerDistance("PENNSYLVANIA", "PENNCISYLVNIA")    = 0.9
     * </pre>
     *
     * @param first
     *            the first String, must not be null
     * @param second
     *            the second String, must not be null
     * @return result distance
     * @throws IllegalArgumentException
     *             if either String input {@code null}
     * @since 3.3
     */
    public static double getJaroWinklerDistance(final CharSequence first, final CharSequence second)
    {
        final double DEFAULT_SCALING_FACTOR = 0.1;

        if (first == null || second == null) { throw new IllegalArgumentException("Strings must not be null"); }

        final double jaro = score(first, second);
        final int cl = commonPrefixLength(first, second);
        final double matchScore = Math.round((jaro + (DEFAULT_SCALING_FACTOR * cl * (1.0 - jaro))) * 100.0) / 100.0;

        return matchScore;
    }

    /**
     * This method returns the Jaro-Winkler score for string matching.
     *
     * @param first
     *            the first string to be matched
     * @param second
     *            the second string to be machted
     * @return matching score without scaling factor impact
     */
    private static double score(final CharSequence first, final CharSequence second)
    {
        String shorter;
        String longer;

        // Determine which String is longer.
        if (first.length() > second.length())
        {
            longer = first.toString().toLowerCase();
            shorter = second.toString().toLowerCase();
        }
        else
        {
            longer = second.toString().toLowerCase();
            shorter = first.toString().toLowerCase();
        }

        // Calculate the half length() distance of the shorter String.
        final int halflength = (shorter.length() / 2) + 1;

        // Find the set of matching characters between the shorter and longer strings. Note that
        // the set of matching characters may be different depending on the order of the strings.
        final String m1 = getSetOfMatchingCharacterWithin(shorter, longer, halflength);
        final String m2 = getSetOfMatchingCharacterWithin(longer, shorter, halflength);

        // If one or both of the sets of common characters is empty, then
        // there is no similarity between the two strings.
        if (m1.length() == 0 || m2.length() == 0) { return 0.0; }

        // If the set of common characters is not the same size, then
        // there is no similarity between the two strings, either.
        if (m1.length() != m2.length()) { return 0.0; }

        // Calculate the number of transposition between the two sets
        // of common characters.
        final int transpositions = transpositions(m1, m2);

        // Calculate the distance.
        final double dist = (m1.length() / ((double) shorter.length()) + m2.length() / ((double) longer.length()) + (m1.length() - transpositions) / ((double) m1.length())) / 3.0;
        return dist;
    }

    /**
     * Gets a set of matching characters between two strings.
     *
     * <p>
     * <Two characters from the first string and the second string are considered matching if the character's respective positions are no farther than the limit
     * value.
     * </p>
     *
     * @param first
     *            The first string.
     * @param second
     *            The second string.
     * @param limit
     *            The maximum distance to consider.
     * @return A string contain the set of common characters.
     */
    private static String getSetOfMatchingCharacterWithin(final CharSequence first, final CharSequence second, final int limit)
    {
        final StringBuilder common = new StringBuilder();
        final StringBuilder copy = new StringBuilder(second);

        for (int i = 0; i < first.length(); i++)
        {
            final char ch = first.charAt(i);
            boolean found = false;

            // See if the character is within the limit positions away from the original position of that character.
            for (int j = Math.max(0, i - limit); !found && j < Math.min(i + limit, second.length()); j++)
            {
                if (copy.charAt(j) == ch)
                {
                    found = true;
                    common.append(ch);
                    copy.setCharAt(j, '*');
                }
            }
        }
        return common.toString();
    }

    /**
     * Calculates the number of transposition between two strings.
     *
     * @param first
     *            The first string.
     * @param second
     *            The second string.
     * @return The number of transposition between the two strings.
     */
    private static int transpositions(final CharSequence first, final CharSequence second)
    {
        int transpositions = 0;
        for (int i = 0; i < first.length(); i++)
        {
            if (first.charAt(i) != second.charAt(i))
            {
                transpositions++;
            }
        }
        return transpositions / 2;
    }

    /**
     * Calculates the number of characters from the beginning of the strings that match exactly one-to-one, up to a maximum of four (4) characters.
     *
     * @param first
     *            The first string.
     * @param second
     *            The second string.
     * @return A number between 0 and 4.
     */
    private static int commonPrefixLength(final CharSequence first, final CharSequence second)
    {
        final int result = getCommonPrefix(first.toString(), second.toString()).length();

        // Limit the result to 4.
        return result > 4 ? 4 : result;
    }

    /**
     * <p>
     * Wraps a string with a char.
     * </p>
     *
     * <pre>
     * StringUtils.wrap(null, *)        = null
     * StringUtils.wrap("", *)          = ""
     * StringUtils.wrap("ab", '\0')     = "ab"
     * StringUtils.wrap("ab", 'x')      = "xabx"
     * StringUtils.wrap("ab", '\'')     = "'ab'"
     * StringUtils.wrap("\"ab\"", '\"') = "\"\"ab\"\""
     * </pre>
     *
     * @param str
     *            the string to be wrapped, may be {@code null}
     * @param wrapWith
     *            the char that will wrap {@code str}
     * @return the wrapped string, or {@code null} if {@code str==null}
     * @since 3.4
     */
    public static String wrap(final String str, final char wrapWith)
    {

        if (isEmpty(str) || wrapWith == '\0') { return str; }

        return wrapWith + str + wrapWith;
    }

    /**
     * <p>
     * Wraps a String with another String.
     * </p>
     *
     * <p>
     * A {@code null} input String returns {@code null}.
     * </p>
     *
     * <pre>
     * StringUtils.wrap(null, *)   = null
     * StringUtils.wrap("", *)     = ""
     * StringUtils.wrap("ab", null)   = "ab"
     * StringUtils.wrap("ab", "x")  = "xabx"
     * StringUtils.wrap("ab", "\"")   = "\"ab\""
     * StringUtils.wrap("\"ab\"", "\"") = "\"\"ab\"\""
     * StringUtils.wrap("ab", "'")   = "'ab'"
     * StringUtils.wrap("'abcd'", "'") = "''abcd''"
     * StringUtils.wrap("\"abcd\"", "'") = "'\"abcd\"'"
     * StringUtils.wrap("'abcd'", "\"") = "\"'abcd'\""
     * </pre>
     *
     * @param str
     *            the String to be wrapper, may be null
     * @param wrapWith
     *            the String that will wrap str
     * @return wrapped String, {@code null} if null String input
     * @since 3.4
     */
    public static String wrap(final String str, final String wrapWith)
    {

        if (isEmpty(str) || isEmpty(wrapWith)) { return str; }

        return wrapWith.concat(str).concat(wrapWith);
    }

    public static boolean equals(String str1, String str2)
    {
        return ((str1 == null) ? false : (str2 == null) ? true : str1.equals(str2));
    }

    public static boolean containsIgnoreCase(String str, String searchStr)
    {
        if (str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0) return true;

        for (int i = str.length() - length; i >= 0; i--)
        {
            if (str.regionMatches(true, i, searchStr, 0, length)) return true;
        }
        return false;
    }


}

