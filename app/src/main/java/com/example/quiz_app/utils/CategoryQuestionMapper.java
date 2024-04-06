package com.example.quiz_app.utils;

import com.example.quiz_app.common.enumerate.TypeQuestionEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryQuestionMapper {

    // Map pour stocker la correspondance entre les catégories et les listes de types de questions
    private static final Map<String, List<TypeQuestionEnum>> categoryQuestionMap = new HashMap<>();

    // Méthode pour initialiser la correspondance entre les catégories et les listes de types de questions
    static {
        List<TypeQuestionEnum> cinemaTypes = new ArrayList<>();
        cinemaTypes.add(TypeQuestionEnum.MULTIPLE_CHOICE);
        cinemaTypes.add(TypeQuestionEnum.YES_NO);
        categoryQuestionMap.put("cinema", cinemaTypes);

        // Catégorie musique
        List<TypeQuestionEnum> musicTypes = new ArrayList<>();
        musicTypes.add(TypeQuestionEnum.MULTIPLE_CHOICE);
        musicTypes.add(TypeQuestionEnum.YES_NO);
        categoryQuestionMap.put("music", musicTypes);

        // Catégorie knowledge
        List<TypeQuestionEnum> knowledgeTypes = new ArrayList<>();
        knowledgeTypes.add(TypeQuestionEnum.YES_NO);
        knowledgeTypes.add(TypeQuestionEnum.MULTIPLE_CHOICE);
        categoryQuestionMap.put("knowledge", knowledgeTypes);

        // Catégorie sport
        List<TypeQuestionEnum> sportTypes = new ArrayList<>();
        sportTypes.add(TypeQuestionEnum.MULTIPLE_CHOICE);
        //sportTypes.add(TypeQuestionEnum.TRUE_FALSE);
        categoryQuestionMap.put("sport", sportTypes);
    }

    // Méthode pour obtenir la liste des types de questions en fonction de la catégorie
    public static List<TypeQuestionEnum> getTypeQuestionsForCategory(String category) {
        return categoryQuestionMap.get(category.toLowerCase());
    }
}
