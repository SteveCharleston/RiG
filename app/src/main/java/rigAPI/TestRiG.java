package rigAPI;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class TestRiG {
    public static void main(String[] args) throws RiGException {
        RigDBAccess rig = new RigDBAccess();
        System.out.println(rig.authenticate("user1", "password1"));

        RigBand band = rig.getBand(100);
        System.out.println(band);

        System.out.println(rig.getSettings());
        System.out.println(rig.getStatistic());
        System.out.println(rig.getToplist(Day.FR));
        System.out.println(rig.searchBand("Mind"));
        System.out.println(rig.downloadFile(band.getPictures().get(0).getId()));
        System.out.println(rig.setDay(100, Day.FR));
        System.out.println(rig.setTag(100, 1));
        System.out.println(rig.vote(100, 2));
    }
}
