package com.soda.utils;

import com.soda.BaseTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author david rapin
 */
public class StringUtilsTest extends BaseTest
{

    @Test
    public void coverageFix()
    {
        new StringUtils();
    }

    @Test
    public void removeAccentuation()
    {
        // null or empty
        assert StringUtils.removeAccentuation("").equals("");
        assert StringUtils.removeAccentuation(null) == null;
        // trivial
        String trivial = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789 ,;.";
        assert StringUtils.removeAccentuation(trivial).equals(trivial);
        // tricky
        String tricky = "\u005e\u00a8\u007e\u0060\u00b4";
        assert StringUtils.removeAccentuation(tricky).equals(tricky) || logError(StringUtils.removeAccentuation(tricky));
        // other
        String other = "\u00d4! l\u00e0! le m\u00eame, mais de l'autre c\u00f4t\u00e9. le go\u00fbt ne me pla\u00eet pas..";
        assert StringUtils.removeAccentuation(other).equals("O! la! le meme, mais de l'autre cote. le gout ne me plait pas..")
               || logError(StringUtils.removeAccentuation(other));

    }

    @Test
    public void truncate()
    {
        // null and empty
        assert StringUtils.truncate("", 12).equals("");
        assert StringUtils.truncate(null, 12) == null;
        assert StringUtils.truncate(null, 0) == null;
        assert StringUtils.truncate(null, -1) == null;
        // other
        assert StringUtils.truncate("", 0).equals("");
        assert StringUtils.truncate("12345", 0).equals("");
        assert StringUtils.truncate("12345", 1).equals("1");
        assert StringUtils.truncate("12345", 2).equals("12");
        assert StringUtils.truncate("12345", -1).equals("12345");
    }

    @Test
    public void repeat()
    {
        // null and empty
        assert StringUtils.repeat("", 12).equals("");
        assert StringUtils.repeat(null, 12) == null;
        assert StringUtils.repeat(null, -1) == null;
        assert StringUtils.repeat(null, 0) == null;
        // other
        assert StringUtils.repeat("Oo", 0).equals("");
        assert StringUtils.repeat("Oo", 1).equals("Oo");
        assert StringUtils.repeat("Oo", 3).equals("OoOoOo");
        assert StringUtils.repeat("Oo", -1).equals("");
    }

    @Test
    public void replaceNullOrEmpty()
    {
        // replace by random
        assert StringUtils.replaceNullOrEmpty("", "abc").equals("abc");
        assert StringUtils.replaceNullOrEmpty(null, "abc").equals("abc");
        assert StringUtils.replaceNullOrEmpty("123", "abc").equals("123");
        // replace by null
        assert StringUtils.replaceNullOrEmpty("", null) == null;
        assert StringUtils.replaceNullOrEmpty(null, null) == null;
        assert StringUtils.replaceNullOrEmpty("123", null).equals("123");
    }

    @Test
    public void escapeXML()
    {
        // null or empty
        assert StringUtils.escapeXML("").equals("");
        assert StringUtils.escapeXML(null) == null;
        // other
        assert StringUtils.escapeXML("blop & blap").equals("blop &amp; blap");
        assert StringUtils.escapeXML("blop &amp; blap").equals("blop &amp;amp; blap");
        assert StringUtils.escapeXML("blop & <blap>").equals("blop &amp; &lt;blap&gt;");
    }

    @Test
    public void obfuscateEmail()
    {
        // null or empty
        assert StringUtils.obfuscateHtmlEmail("").equals("");
        assert StringUtils.obfuscateHtmlEmail(null) == null;
        // other
        assert !StringUtils.obfuscateHtmlEmail("jean-paul.machin@truc-chose.fr.st").contains("@");
        assert !StringUtils.obfuscateHtmlEmail("jean-paul.machin@truc-chose.fr.st").contains("-");
        assert !StringUtils.obfuscateHtmlEmail("jean-paul.machin@truc-chose.fr.st").contains(".");
    }

    @Test
    public void capitalizeFirstLetter()
    {
        assert StringUtils.capitalizeFirstLetter(null) == null;
        assert StringUtils.capitalizeFirstLetter("").equals("");
        assert StringUtils.capitalizeFirstLetter("AA").equals("AA");
        assert StringUtils.capitalizeFirstLetter("Aa").equals("Aa");
        assert StringUtils.capitalizeFirstLetter(" a").equals(" a");
        assert StringUtils.capitalizeFirstLetter("|a").equals("|a");
        assert StringUtils.capitalizeFirstLetter("ea").equals("Ea");
        assert StringUtils.capitalizeFirstLetter("i").equals("I");
    }

    @Test
    public void chop()
    {
        assert StringUtils.chop(null) == null;
        assert StringUtils.chop("").equals("");
        assert StringUtils.chop("a").equals("");
        assert StringUtils.chop("ab").equals("a");

        assert StringUtils.chop(null, ' ') == null;
        assert StringUtils.chop("a,b,", ',').equals("a,b");
        assert StringUtils.chop("z", 'z').equals("");
        assert StringUtils.chop("zzz", 'z').equals("zz");
        assert StringUtils.chop("", 'z').equals("");

    }

    @Test
    public void join()
    {
        assert StringUtils.join(null) == null;
        assert StringUtils.join((String[]) null, null) == null;
        assert StringUtils.join((String[]) null, "lulz") == null;
        assert StringUtils.join(new String[]{}, "lulz") == null;
        assert StringUtils.join(new String[]{""}, "lulz").equals("lulz");
        assert StringUtils.join(new String[]{"333"}, "lulz").equals("333lulz");
        assert StringUtils.join(new String[]{"333", "444"}, "lulz").equals("333lulz444lulz");
        assert StringUtils.join(new String[]{"333", "444"}, null).equals("333444");
        assert StringUtils.join(new String[]{"333", "444"}, "").equals("333444");
        assert StringUtils.join(new String[]{"333", "444"}).equals("333444");
        assert StringUtils.join(new String[]{"333", null, "555"}).equals("333555");
        assert StringUtils.join(new String[]{"333", null, "555"}, "j").equals("333j555j");
    }

    @Test
    public void joinList()
    {
        assert StringUtils.join((List<String>) null, null) == null;
        assert StringUtils.join((List<String>) null, "lulz") == null;
        assert StringUtils.join(new ArrayList<String>(), "lulz") == null;
        assert StringUtils.join(Arrays.asList(""), "lulz").equals("lulz");
        assert StringUtils.join(Arrays.asList("333"), "lulz").equals("333lulz");
        assert StringUtils.join(Arrays.asList("333", "444"), "lulz").equals("333lulz444lulz");
        assert StringUtils.join(Arrays.asList("333", "444"), null).equals("333444");
        assert StringUtils.join(Arrays.asList("333", "444"), "").equals("333444");
        //assert StringUtils.join(Arrays.asList("333", "444")).equals("333444");
        //assert StringUtils.join(Arrays.asList("333", null, "555")).equals("333555");
        assert StringUtils.join(Arrays.asList("333", null, "555"), "j").equals("333j555j");
    }
}
