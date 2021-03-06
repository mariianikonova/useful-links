package com.github.vedenin.thirdpartylib.htmlparser;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Molecule;
import org.jsoup.parser.Tag;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/19/2016.
 */
@Atom(Tag.class)
@Molecule({ElementAtom.class})
public class TagAtom {
    private final Tag tag;

    public static TagAtom valueOf(String tagString) {
        return new TagAtom(Tag.valueOf(tagString));
    }


    // Just boilerplate code for Atom
    @Override
    @BoilerPlate
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagAtom tagAtom = (TagAtom) o;

        return !(tag != null ? !tag.equals(tagAtom.tag) : tagAtom.tag != null);

    }

    @Override
    @BoilerPlate
    public int hashCode() {
        return tag != null ? tag.hashCode() : 0;
    }

    @BoilerPlate
    private TagAtom(Tag tag) {
        this.tag = tag;
    }

    @BoilerPlate
    static TagAtom getAtom(Tag tag) {
        return new TagAtom(tag);
    }
}
