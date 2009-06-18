package com.tentelemed.geodata;

import java.io.Serializable;
import java.util.*;

/**
 * User: DRA
 * Date: 16 juin 2009
 * Time: 16:48:02
 *
 * <p>list of all countries in the World, according to ISO 3166-1,
 * downloaded from <a href="http://www.iso.org/iso/country_codes/iso_3166_code_lists.htm">iso.org</a>
 * (last checked on 16/06/2009) 
 *
 */
public class Country implements Serializable, Comparable<Country>
{
    private static Map<String, Country> MAP = null;
    private static List<Country> LIST = null;

    private final String isoCode;
    private String nameEn;
    private String nameFr;

    /**
     * @param isoCode ISO 3166-1 alpha-2 code for this country
     */
    protected Country(String isoCode)
    {
        if (isoCode == null) throw new IllegalArgumentException("'isoCode' cannot be null");
        if (!isoCode.matches("[A-Z]{2}")) throw new IllegalArgumentException("'isoCode' must be an uppercase two letters code)");
        this.isoCode = isoCode;
    }

    /**
     * @return the name of this country is ENGLISH locale
     */
    public String getNameEn() {
        return nameEn;
    }

    /**
     *
     * @param nameEn the name of this country is ENGLISH locale
     */
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    /**
     * @return the name of this country in FRENCH locale
     */
    public String getNameFr() {
        return nameFr;
    }

    /**
     * @param nameFr the name of this country in FRENCH locale
     */
    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    /**
     * @return the ISO 3166-1 <em>alpha-2</em> code for this country
     */
    public String getIsoCode()
    {
        return isoCode;
    }

    public final boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }

        Country country = (Country) obj;

        return isoCode.equals(country.isoCode);
    }

    public final int hashCode()
    {
        return isoCode.hashCode();
    }

    public int compareTo(Country o) {
        return getIsoCode().compareTo(o.getIsoCode());
    }

    public static List<Country> list() {
        if (LIST == null) {
            LIST = new ArrayList<Country>();
            LIST.addAll(map().values());
            Collections.sort(LIST);
        }
        return LIST;
    }

    public static Map<String, Country> map() {
        if (MAP == null) {
            MAP = new HashMap<String, Country>();
            CountriesParser.parseCountriesList(MAP);
        }
        return MAP;
    }

    /**
     *
     * @param isoCode the ISO 3166-1 alpha-2 code for the desired country
     * @return the corresponding country after ISO 3166-1
     */
    public static Country get(String isoCode) {
        return map().get(isoCode);
    }
}

