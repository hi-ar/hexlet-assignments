package exercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Article;
import exercise.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping(path = "")
    public Iterable<Article> getArticles() {
        return this.articleRepository.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArticle(@PathVariable long id) {
        this.articleRepository.deleteById(id);
    }

    // BEGIN
    @PostMapping(path = "")
    public void createArticle(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Article art = objectMapper.readValue(json, Article.class);
        this.articleRepository.save(art);
    }

    @PatchMapping(path = "/{id}")
    public void modifyArticle(@PathVariable long id, @RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Article actualArticle = objectMapper.readValue(json, Article.class);
        this.articleRepository.findById(id).setName(actualArticle.getName());
        this.articleRepository.findById(id).setBody(actualArticle.getBody());
        this.articleRepository.findById(id).setCategory(actualArticle.getCategory());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public String getArticle(@PathVariable long id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this.articleRepository.findById(id));
    }
    // END
}
