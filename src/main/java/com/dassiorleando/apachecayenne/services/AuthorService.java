package com.dassiorleando.apachecayenne.services;

import com.dassiorleando.apachecayenne.models.Article;
import com.dassiorleando.apachecayenne.models.Author;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SQLTemplate;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * ArticleService
 * Some basic CRUD operations applied to Author class(table)
 *
 * @author dassiorleando
 */
public class AuthorService {

    /**
     * Apache Cayenne DB context, required for some queries
     */
    private ObjectContext context;

    public AuthorService(ObjectContext context) {
        Objects.requireNonNull(context, "The DB context is required");
        this.context = context;
    }

    /**
     * Save an author
     * @param name
     */
    public void save(String name) {
        // Save a single author
        Author author = this.context.newObject(Author.class);
        author.setName(name);

        context.commitChanges();
    }

    /**
     * Find an author by its ID
     * @param id    the author's ID
     * @return      the matched author or null if not existing
     */
    public Author findById(int id) {
        // Find an author by it’s ID
        Author author = Cayenne.objectForPK(context, Author.class, id);
        return author;
    }

    /**
     * Looking fo an author by name
     * @param name  the name to look up with
     * @return      the first matched author or null if not existing
     */
    public Author findByName(String name) {
        // Looking for an author by name
        Author foundAuthor = ObjectSelect.query(Author.class)
                .where(Author.NAME.eq(name))
                .selectOne(this.context);

        return foundAuthor;
    }

    /**
     * Find authors by name starting with(like%)
     * @param partName expected name part
     * @return         list of authors
     */
    public List<Author> findByNameLike(String partName) {
        // Let's apply a case-insensitive LIKE on the Author’s name column
        // We get all the authors with their name starting with "partName"
        List<Author> authorsLike = ObjectSelect.query(Author.class)
                .where(Author.NAME.likeIgnoreCase(partName + "%"))
                .select(context);

        return authorsLike;
    }

    /**
     * Find authors by name ending with
     * @param partName expected name part
     * @return         list of authors
     */
    public List<Author> findByNameEndWith(String partName) {
        // All authors with names ending with "partName"
        List<Author> authorsEnd = ObjectSelect.query(Author.class)
                .where(Author.NAME.endsWith("aul"))
                .select(context);

        return authorsEnd;
    }

    /**
     * Get all the authors list
     * @return list of authors
     */
    public List<Author> findAll() {
        // Looking for all authors
        List<Author> authors = ObjectSelect
                .query(Author.class)
                .select(this.context);
        return authors;
    }

    /**
     * Update an author
     * @param id        the author's ID
     * @param newName   the new name to set
     * @return          true for a successful operation and false for bad data provided
     */
    public boolean update(int id, String newName) {
        if (StringUtils.isEmpty(newName)) return false;

        // Get the author to update
        Author author = this.findById(id);

        if (author == null) return false;

        // Set its name
        author.setName(newName);
        context.commitChanges();
        return true;
    }

    /**
     * Attach a fake article to the author
     * @param id    the author's ID
     * @return      true for a successful operation and false for bad data provided
     */
    public boolean attachArticle(int id) {
        // Get the author to link with
        Author author = this.findById(id);

        if (author == null) return false;

        // Create a fake article and link it to the current author
        Article article = context.newObject(Article.class);
        article.setTitle("My post title");
        article.setContent("The content");
        article.setAuthor(author);

        context.commitChanges();

        // Get author's linked data (articles)
        List<Article> articles = author.getArticles();

        return true;
    }

    /**
     * Delete an author
     * @param id author's ID
     * @return   true for a successful operation and false for bad data provided
     */
    public boolean delete(int id) {
        // Get the author to delete
        Author author = this.findById(id);

        if (author != null) {
            context.deleteObjects(author);
            context.commitChanges();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Clear all authors
     */
    public void clearAuthors() {
        // SQL delete queries for author deletion
        SQLTemplate deleteAuthors = new SQLTemplate(Author.class, "delete from author");

        // Applying the query
        context.performGenericQuery(deleteAuthors);
    }

}
