package com.github.vedenin.thirdpartylib.htmlparser;

import com.github.vedenin.atom.annotations.AtomUtils;
import com.github.vedenin.atom.annotations.Molecule;
import com.google.common.base.Charsets;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/16/2016.
 */
@AtomUtils(HTMLParserAtom.class)
@Molecule({DocumentAtom.class})
public class HTMLParserAtom {
    private static String CHARSET_NAME = Charsets.UTF_8.name();

    public static DocumentAtom parseFile(File file) throws IOException {
        return DocumentAtom.getAtom(Jsoup.parse(file, CHARSET_NAME));
    }

    public static DocumentAtom parseUrl(String url, String userAgent, int timeout) throws IOException {
        return DocumentAtom.getAtom(Jsoup.connect(url).userAgent(userAgent).timeout(timeout).get());
    }

}
