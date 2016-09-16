package com.github.vedenin.core;

import com.github.vedenin.core.classificator.WordsClassificator;
import com.github.vedenin.core.resources.Resources;
import com.github.vedenin.project_parser.containers.ProjectContainer;
import com.github.vedenin.project_parser.storeresult.CSVStoreManager;
import com.github.vedenin.project_parser.storeresult.StoreManager;
import org.junit.Test;

import java.util.List;

/**
 * Created by vedenin on 16.07.16.
 */
public class WordsClassificatorTest {
    @Test
    public void test() {
        WordsClassificator wordsClassificator = new WordsClassificator();
        Resources resources = new Resources();
        StoreManager storeManager = new CSVStoreManager();
        List<String> files = resources.getFiles("/exports");
        for(String file: files) {
            List<ProjectContainer> projects = storeManager.readProjects(file);
        }
    }
}