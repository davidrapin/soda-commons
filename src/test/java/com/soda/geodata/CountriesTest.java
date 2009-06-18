package com.soda.geodata;

import com.soda.BaseTest;
import org.testng.annotations.Test;

/**
 * @author david rapin
 */
public class CountriesTest extends BaseTest
{

    @Test
    public void fixCoverage()
    {
        new CountriesParser();
    }

    @Test
    public void getList() {
        Country.list();
    }

    @Test
    public void getMap() {
        Country.map();
    }

    @Test
    public void getFrance() {
        assert Country.get("FR").getNameFr().equalsIgnoreCase("FRANCE") || logError(Country.get("FR") + "");
    }

    @Test
    public void getCounts() {
        assert Country.list().size() == Country.map().keySet().size();
    }

    @Test
    public void checkList() {
        for (Country c : Country.list()) {
            //log.debug("[" + c.getIsoCode() + "] - " + c.getNameFr() + " - " + c.getNameEn());
            assert c.getIsoCode() != null;
            assert c.getNameEn() != null;
            assert c.getNameFr() != null;
        }
    }

    @Test
    public void chekCodeCountry() {
        int ex = 0;

        try { new Country("A");  } catch (Exception e) { ex++; }
        try { new Country("1");  } catch (Exception e) { ex++; }
        try { new Country("az"); } catch (Exception e) { ex++; }
        try { new Country("");   } catch (Exception e) { ex++; }
        try { new Country(null); } catch (Exception e) { ex++; }

        assert ex == 5;         
    }

    @Test
    public void countryEqualsAndHash() {
        Country c1 = new Country("AA");
        Country c2 = new Country("AA");

        assert c1.equals(c1);
        assert c2.equals(c2);
        assert !c1.equals(null);
        assert !c1.equals("lol");
        assert c1.equals(c2);
        assert c1.hashCode() == c2.hashCode();
    }
}
