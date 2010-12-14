package com.soda.utils;

import java.text.Normalizer;
import java.util.List;

/**
 * @author david rapin
 */
public class StringUtils
{

    protected StringUtils() {}

    /**
     * @param str the string to read from
     * @return a new string in which all accentuated characters from <code>str</code> have been replaced
     *         by their unaccentuated equivalent.
     */
    public static String removeAccentuation(String str)
    {
        // trivial cases (null, empty or simple string)
        if (str == null || str.isEmpty() || str.matches("[\\w\\s\\.\\,\\;\\:\\!\\?\\+]*")) return str;

        /**
         * first split compounded characters into their canonical decomposition,
         * then, remove all characters that are diacritiques.
         *
         * @see http://glaforge.free.fr/weblog/index.php?itemid=115
         * @see http://www.unicode.org/fr/charts/PDF/U0300.pdf
         */
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[\u0300-\u036f]", "");
    }

    /**
     * @param str     the string to truncate (may be null of empty)
     * @param maxSize the maximum size the result string should have. must be greater than zero.
     * @return a new string truncated a <code>maxSize</code> characters if necessary
     */
    public static String truncate(String str, int maxSize)
    {
        if (str != null && maxSize >= 0 && str.length() > maxSize)
        {
            return str.substring(0, maxSize);
        }
        else
        {
            return str;
        }
    }

    /**
     * @param str   the string to repeat (may be numm or empty).
     * @param times the number of times <code>str</code> must be repeated
     * @return a new string consisting of <code>times</code> concatenations of <code>str</code>
     */
    public static String repeat(String str, int times)
    {
        // trivial cases
        if (str == null || str.isEmpty()) return str;

        String result = "";
        for (int i = 0; i < times; i++) result += str;
        return result;
    }

    /**
     * @param str         the string to read from
     * @param replacement the string used to replace <code>str</code> in case of nullness or emptyness.
     * @return <code>replacement</code> if <code>str</code> is null or empty,<br>
     *         <code>str</code> otherwise.
     */
    public static String replaceNullOrEmpty(String str, String replacement)
    {
        return str == null || str.isEmpty() ? replacement : str;
    }

    /**
     * Very naive XML escaping function.
     *
     * @param str a string (may be null or empty)
     * @return the content of <code>str</code> escaped for XML ('<', '>' and '&' escaped)
     */
    public static String escapeXML(String str)
    {
        if (str == null || str.isEmpty()) return str;
        return str
            //.replaceAll("&(?!#\\d{2,3}|[A-Za-z0-9]{2,6})", "&amp;");
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;");
    }

    /**
     * @param email a string representing an email
     * @return an HTML string where some characters from <code>email</code> have been replaced
     *         by HTML entities, in hope of fooling naive spambots.
     */
    public static String obfuscateHtmlEmail(String email)
    {
        if (email == null || email.isEmpty()) return email;
        return email
            .replaceAll("[:]", "&#58;")
            .replaceAll("[-]", "&#45;")
            .replaceAll("[.]", "&#46;")
            .replaceAll("@", "&#64;")
            .replaceAll("a", "&#97;")
            .replaceAll("e", "&#101;")
            .replaceAll("i", "&#105;")
            .replaceAll("o", "&#111;");
    }

    /**
     *
     * @param xml a string potentially containing XML tags
     * @return the given string with all XML tags removed
     */
    public static String stripXML(String xml)
    {
        if (xml == null || xml.isEmpty()) return xml;
        return xml.replaceAll("<[Bb][Rr][\\s]*[/]?>", "\r\n").replaceAll("<[^>]*>", "");
    }

    /**
     * @param str the string we are removing the last char from
     * @return <code>str</code> with the last char removed. if <code>str</code> is <code>null</code> or empty, chop(String) has no effect.
     */
    public static String chop(String str)
    {
        return (str != null && str.length() > 0) ? str.substring(0, str.length() - 1) : str;
    }

    /**
     * @param str the string to shorten by one char
     * @param expectedEnd the expected end of <code>str</code>
     * @return str with the last char chopped off if <code>str</code> ends with <code>expectedEnd</code>, <code>str</code> itself otherwise
     */
    public static String chop(String str, char expectedEnd)
    {
        return (str != null && str.length() > 0 && str.endsWith(String.valueOf(expectedEnd))) ? str.substring(0, str.length() - 1) : str;
    }

    /**
     * @param s the string to modify
     * @return <code>s</code> with the first letter capitalized
     */
    public static String capitalizeFirstLetter(String s)
    {
        if (s == null || s.isEmpty()) return s;
        return s.length() == 1 ? s.toUpperCase() : s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * @param strings an array of strings
     * @return all strings in the given array concatenated.
     *         null if the array is null or empty.
     */
    public static String join(String[] strings)
    {
        return join(strings, null);
    }

    /**
     * @param strings an array of strings
     * @param joinString a string (null or empty for nothing)
     * @return all strings in the given array concatenated, with <code>joinString</code> between each.
     *         null if the array is null or empty.
     */
    public static String join(String[] strings, String joinString)
    {
        if (strings == null || strings.length == 0) return null;
        StringBuffer b = new StringBuffer();
        for (String str : strings)
        {
            if (str != null)
            {
                b.append(str);
                if (joinString != null) b.append(joinString);
            }
        }
        return b.toString();
    }

    /**
     * @param strings a list of strings
     * @param joinString a string (null or empty for nothing)
     * @return all strings in the given array concatenated, with <code>joinString</code> between each.
     *         null if the array is null or empty.
     */
    public static String join(List<String> strings, String joinString)
    {
        if (strings == null || strings.size() == 0) return null;
        StringBuffer b = new StringBuffer();
        for (String str : strings)
        {
            if (str != null)
            {
                b.append(str);
                if (joinString != null) b.append(joinString);
            }
        }
        return b.toString();
    }
}
