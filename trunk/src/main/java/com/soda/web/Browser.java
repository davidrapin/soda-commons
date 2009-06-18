package com.soda.web;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auhor david
 */
public class Browser
{
    private static final Logger log = Logger.getLogger(Browser.class);

    protected final String userAgent;
    protected final Type type;
    protected final Integer majorVersion;

    public Browser(String userAgent)
    {
        this.userAgent = userAgent;
        type = Type.detect(userAgent);
        majorVersion = type.getMajorVersion(userAgent);
    }

    public String getUserAgent()
    {
        return userAgent;
    }

    public Type getType()
    {
        return type;
    }

    public Integer getMajorVersion()
    {
        return majorVersion;
    }

    public enum Type
    {
        FIREFOX(
            "Firefox",
            "Firefox[/(]",
            "(:? Camino/| Safari/| Opera )",
            " Firefox[/(](\\d+)"
        ),
        IE(
            "Internet Explorer",
            "(?:^Microsoft Internet Explorer/|[ (;]MSIE )",
            "Opera",
            "(?:[ (;]MSIE |Microsoft Internet Explorer/)(\\d+)"
        ),
        SAFARI(
            "Safari",
            "(:? Safari/[\\d.]+| iPhone)$",
            "Chrome",
            " Version/(\\d+)"
        ),
        OPERA(
            "Opera",
            "(?:^Opera[/ ]|[\\s)]Opera(:?\\s|$))",
            null,
            "(?:^Opera[/ ]| ?Opera )(\\d+)"
        ),
        CHROME(
            "Chrome",
            "\\sChrome/.*$",
            null,
            " Chrome/(\\d+)"
        ),
        KONQUEROR(
            "Konqueror",
            "\\sKonqueror/",
            null,
            "; Konqueror/(\\d+)"
        ),
        SEAMONKEY(
            "SeaMonkey",
            "\\sSeaMonkey/",
            null,
            " SeaMonkey/(\\d+)"
        ),
        CAMINO(
            "Camino",
            " Camino/\\d",
            null,
            " Camino/(\\d+)"
        ),
        LYNX(
            "Lynx",
            "Lynxy?/\\d",
            null,
            "Lynxy?/(\\d+)"
        ),
        GOOGLEBOT(
            "Googlebot",
            "Googlebot(?:-Image)?/\\d",
            null,
            "Googlebot(?:-Image)?/(\\d+)"
        ),
        W3C_VALIDATOR(
            "W3C Validator",
            "W3C_Validator/",
            null,
            "W3C_Validator/(\\d+)"
        ),

        OTHER("Other", null, null, null);

        Type(String displayName, String userAgentInclusionRegex, String userAgentExclusionRegex, String majorVersionRegex)
        {
            this.displayName = displayName;
            userAgentInclusionPattern = userAgentInclusionRegex == null ? null : Pattern.compile(userAgentInclusionRegex);
            userAgentExclusionPattern = userAgentExclusionRegex == null ? null : Pattern.compile(userAgentExclusionRegex);
            majorVersionPattern = majorVersionRegex == null ? null : Pattern.compile(majorVersionRegex);
        }

        private String displayName;
        private Pattern userAgentInclusionPattern;
        private Pattern userAgentExclusionPattern;
        private Pattern majorVersionPattern;

        public String getDisplayName()
        {
            return displayName;
        }

        public Integer getMajorVersion(String userAgent)
        {
            if (majorVersionPattern != null && userAgent != null)
            {
                Matcher m = majorVersionPattern.matcher(userAgent);
                if (m.find() && m.groupCount() == 1)
                {
                    return Integer.valueOf(m.group(1));
                    // could not convert the matching string to integer (will not happen is regex are good)
                }
            }

            // no match
            log.info("Failed to detect Browser Major Version for User-Agent string: '" + userAgent + "'");
            return null;
        }

        public static Type detect(String userAgent)
        {
            for (Type b : Type.values())
            {
                if (
                    // inclusion pattern is not null AND matches
                    b.userAgentInclusionPattern != null && b.userAgentInclusionPattern.matcher(userAgent).find()

                    &&

                    // exclusion pattern is null OR does not match
                    (b.userAgentExclusionPattern == null || !b.userAgentExclusionPattern.matcher(userAgent).find())
                    )
                {
                    return b;
                }
            }

            // nothing has matched
            log.info("Failed to detect Browser for User-Agent string: '" + userAgent + "'");
            return OTHER;
        }
    }

    public String toString()
    {
        return getType() + (getMajorVersion() == null ? "" : "v" + getMajorVersion());
    }
}