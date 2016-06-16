package com.github.vedenin.useful_links;

import com.github.vedenin.useful_links.containers.ProjectContainer;
import com.github.vedenin.useful_links.crawlers.old.JavaUsefulProjects;
import com.github.vedenin.useful_links.store.CSVStoreManager;
import com.github.vedenin.useful_links.store.StoreManager;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by vvedenin on 6/16/2016.
 */
public class CSVStoreManagerTests {
    @Test
    public void testCSVTest() throws IOException {
        URL url = this.getClass().getResource("/useful-java-links.html");
        JavaUsefulProjects thisCls = new JavaUsefulProjects();
        Map<String, ProjectContainer> projects = thisCls.getProjects(url.toString());
        assertEquals(732, projects.size());
        StoreManager storeManager = new CSVStoreManager();
        storeManager.writeProjects("useful-java-links.csv", projects.values());
    }
}