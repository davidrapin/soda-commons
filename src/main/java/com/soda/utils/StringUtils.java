package com.soda.utils;

import java.text.Normalizer;

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
}
