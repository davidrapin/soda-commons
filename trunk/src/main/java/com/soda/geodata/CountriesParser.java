package com.soda.geodata;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * @author david rapin
 */
class CountriesParser
{
    private static final Logger log = Logger.getLogger(CountriesParser.class);

    private enum Iso3166XmlDescriptor
    {
        FR("setNameFr", "iso_3166-1_list_fr.xml", "ISO_3166-1_Entry", "ISO_3166-1_Country_name", "ISO_3166-1_Alpha-2_code"),
        EN("setNameEn", "iso_3166-1_list_en.xml", "ISO_3166-1_Entry", "ISO_3166-1_Country_name", "ISO_3166-1_Alpha-2_Code_element");

        private Iso3166XmlDescriptor(String setMethodName, String filename, String entryElement, String nameElement, String codeElement)
        {
            this.filename = filename;
            this.entryElement = entryElement;
            this.codeElement = codeElement;
            this.nameElement = nameElement;
            try
            {
                nameSetMethod = Country.class.getMethod(setMethodName, String.class);
            }
            catch (NoSuchMethodException e)
            {
                log.error("could not find public method '" + setMethodName + "(String)' for class '" + Country.class.getSimpleName() + "'", e);
            }
        }

        protected String filename;
        protected String entryElement;
        protected String codeElement;
        protected String nameElement;
        protected Method nameSetMethod;
    }

    protected static void parseCountriesList(Map<String, Country> countries)
    {
        for (Iso3166XmlDescriptor descriptor : Iso3166XmlDescriptor.values())
        {

            InputStream countryFile = CountriesParser.class.getResourceAsStream(descriptor.filename);

            try
            {
                Document document = parse(countryFile);
                Element root = document.getRootElement();

                Iterator<Element> i = root.elementIterator(descriptor.entryElement);
                while (i.hasNext())
                {
                    Element countryElement = i.next();
                    Element countryCode = countryElement.element(descriptor.codeElement);
                    Element countryName = countryElement.element(descriptor.nameElement);

                    String name = countryName.getText();
                    String code = countryCode.getText();

                    // add if code is not known
                    if (!countries.containsKey(code))
                    {
                        countries.put(code, new Country(code));
                    }

                    // record name for code
                    descriptor.nameSetMethod.invoke(countries.get(code), name);
                }
            }
            catch (DocumentException e)
            {
                log.error("Could not parse XML file '" + descriptor.filename + "'", e);
            }
            catch (InvocationTargetException e)
            {
                log.error("Could not call country name setter (probably the method does not have the expected argument count and/or type)", e);
            }
            catch (IllegalAccessException e)
            {
                log.error("Could not call country name setter (probably because the method is not public)", e);
            }
        }
    }

    private static Document parse(InputStream inputStream) throws DocumentException
    {
        SAXReader reader = new SAXReader();
        return reader.read(inputStream);
    }


}
