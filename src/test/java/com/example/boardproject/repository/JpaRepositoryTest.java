package com.example.boardproject.repository;

import com.example.boardproject.config.JpaConfig;
import com.example.boardproject.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("testdb")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private ArticleRepository articleRepository;
    private ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                             @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select Test")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        // Given
        long articleSize=articleRepository.count();
        // When
        List<Article> articleList=articleRepository.findAll();

        // Then
        assertThat(articleList).isNotNull().hasSize(123);
    }

    @DisplayName("insert Test")
    @Test
    void givenTestData_whenInserting_thenWorksFine(){
        // Given
        long previousCount=articleRepository.count();

        // When
        articleRepository.save(Article.of("new one","is the content","#1233"));
        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount+1);
    }

    @DisplayName("Update Test")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){
        // Given
        Article article=articleRepository.findById(1L).orElseThrow();
        String updatedHashtag= "#SpringBoot";
        article.setHashtag(updatedHashtag);

        // When
        Article savedArticle = articleRepository.saveAndFlush(article);

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("Delete Test")
    @Test
    void givenTestData_whenDeleting_thenWorksFine(){
        // Given
        Article article=articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount= articleCommentRepository.count();
        int deleteCommentSize=article.getArticleComments().size();

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount-1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount-deleteCommentSize);
    }
}