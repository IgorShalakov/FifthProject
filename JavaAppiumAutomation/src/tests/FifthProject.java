package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;

public class FifthProject extends CoreTestCase {
    @Test
    public void testSearchAndNotPresentArticles(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Apple";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
                "We found to few results!",
                amount_of_search_results > 0
        );

        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSaveTwoArticleAndRemoveOneArticle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String java_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Python");
        SearchPageObject.clickByArticleWithSubstring("General-purpose programming language");
        ArticlePageObject.waitForTitleElement();
        String python_title = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToMyActualList(name_of_folder);
        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        ArticlePageObject.closeArticle();
        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(java_title);
        //Два действия ниже удостоверяются что статья Java удалилась, а статья Python осталась.
        //Использовал ранее созданный waitForSearchResult и создал waitForSearchNoResult.
        SearchPageObject.waitForSearchNotResult("object-oriented programming language");
        SearchPageObject.waitForSearchResult("general-purpose programming language");
        SearchPageObject.clickByArticleWithSubstring("general-purpose programming language");
        String actual_python_title = ArticlePageObject.getArticleTitle();
        Assert.assertEquals(
                "Title does not match",
                python_title,
                actual_python_title);
    }

    @Test
    public void testAssertElementPresent() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String title = ArticlePageObject.titlePresent();
        Assert.assertEquals(
                "The article is missing a title",
                "Java (programming language)",
                title);
    }
}
