package parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Runner {

    public static void main (String[] args) {
        String url = "http://catalog.iastate.edu/azcourses/cpr_e/";

        
        Elements content = ParseWebsite.getElementsfromURL(url);

        for (Element element : content)
        {
            ParseWebsite.ElementToCourse(element.toString());
        }


//        ParseWebsite.ElementToCourse("<div class=\"courseblocktitle\"><a href=\"http://catalog.iastate.edu/azcourses/cpr_e/#\" class=\"toggle-accordion courseblocklink\"><strong>CPR&nbsp;E&nbsp;697: Engineering Internship</strong><span></span></a></div><div class=\"courseblockdesc accordion-content\"><p class=\"credits noindent\">\r\n" +
//                "(Cross-listed with E E).  Cr. R.\r\n" +
//                "  Repeatable.    \r\n" +
//                "</p><p class=\"prereq\"><br>One semester and one summer maximum per academic year professional work period.\r\n" +
//                "  Offered on a satisfactory-fail basis only.</p></div>\r\n" +
//                "</div>");
    }
}
