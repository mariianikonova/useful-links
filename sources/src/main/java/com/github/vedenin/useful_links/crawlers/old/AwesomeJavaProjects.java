package com.github.vedenin.useful_links.crawlers.old;

import com.github.vedenin.useful_links.common.containers.ProjectContainer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.vedenin.useful_links.common.Constants.GIT_HUB_URL;
import static com.github.vedenin.useful_links.common.utils.DownloadUtils.*;

/**
 * Download all projects from AwesomeJava
 * <p>
 * Created by vedenin on 07.04.16.
 */
public class AwesomeJavaProjects {
    private static final String GITHUB_STAR = "github's star";

    public static void main(String[] s) throws IOException {
        AwesomeJavaProjects thisCls = new AwesomeJavaProjects();
        Map<String, ProjectContainer> projects = thisCls.getProjects("https://github.com/akullpp/awesome-java/blob/master/README.md");
        projects.values().stream().forEach(System.out::println);
    }

    /**
     * Return list of java projects from:
     * - https://github.com/Vedenin/useful-java-links/blob/master/readme.md
     * - https://github.com/Vedenin/useful-java-links/blob/master/link-rus/readme.md
     *
     * @param url - url's from useful-java-links
     * @return java projects
     * @throws IOException
     */
    public Map<String, ProjectContainer> getProjects(String url) throws IOException {
        System.out.println("Start downloading");
        Document doc = getPage(url);
        Elements div = doc.select("#readme");
        Map<String, ProjectContainer> result = parserProjects(div, "", null, "");
        System.out.println("End downloading");
        System.out.println();
        return result;
    }



    private static Map<String, ProjectContainer> parserProjects(Elements elements, String currentCategory, ProjectContainer container, String description) {
        Map<String, ProjectContainer> result = new LinkedHashMap<>(elements.size());
        for (Element element : elements) {
            Tag tag = element.tag();
            if (isHeader(tag)) {
                currentCategory = element.text();
                container = null;
                if("Communities".equals(currentCategory)) {
                    return result;
                }
            } else if (isEnum(tag)) {
                description = getDescription(element);
            } else if (isLink(tag)) {
                String link = element.attr("href");
                if (isProjectLink(element, link)) {
                    if (isSite(element, link)) {
                        saveSite(container, link);
                    } else {
                        container = getProjectContainer(currentCategory, description, element, link);
                        result.put(container.url, container);
                    }
                }
            }
            result.putAll(parserProjects(element.children(), currentCategory, container, description));
        }
        return result;
    }

    private static String getDescription(Element element) {
        return element.ownText().replace("License:", "").replace("stackoverflow - more", "").replaceAll("  ", " ");
    }

    private static String getDescription(String text, String replacedText) {
        String result = text.replace(replacedText , "").
                replace(". and","").
                replaceAll("  ", " ").
                replace(" .",".").
                replace(" ,",",").
                replace(".,",".").
                replace(",.",".").
                replace("src/main",".").
                trim();

        return result.startsWith("-") ? result.substring(1).trim() : result;
    }

    private static ProjectContainer getProjectContainer(String currentCategory, String text, Element element, String link) {
        ProjectContainer container;
        container = ProjectContainer.create();
        container.category = currentCategory;
        container.name = element.text();
        saveUrlAndGithub(link, container);
        saveStarAndText(container, text);
        return container;
    }

    private static void saveUrlAndGithub(String link, ProjectContainer container) {
        if(link.contains(GIT_HUB_URL)) {
            container.github = link;
            container.url = link;
        } else {
            container.url = link;
        }
    }

    private static void saveStarAndText(ProjectContainer container, String text) {
        int i1 = text.indexOf(GITHUB_STAR);
        if(i1 > -1) {
            int i2 = min(text.indexOf(".", i1), text.indexOf(",", i1));
            String starText = text.substring(i1, i2);
            container.star = getInteger(text.substring(i1 + GITHUB_STAR.length(), i2));
            container.description = getDescription(text, starText);
        } else {
            container.description = getDescription(text, "");
        }
        container.allText = container.description;
    }

    private static void saveSite(ProjectContainer container, String link) {
        if (container != null) {
            container.site = link;
        }
    }

    private static boolean isSite(Element element, String link) {
        return link.equals(element.text().trim());
    }

    private static boolean isProjectLink(Element element, String link) {
        return !link.startsWith("#") &&
                !link.contains("/awesome") &&
                !link.contains("/akullpp/");
    }

    private static boolean isUserGuide(Element element) {
        return "User guide".equals(element.text());
    }


}
