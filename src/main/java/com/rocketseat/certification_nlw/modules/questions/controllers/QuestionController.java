package com.rocketseat.certification_nlw.modules.questions.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketseat.certification_nlw.modules.questions.dto.AlternativesResultDTO;
import com.rocketseat.certification_nlw.modules.questions.dto.QuestionResultDTO;
import com.rocketseat.certification_nlw.modules.questions.entities.AlternativeEntity;
import com.rocketseat.certification_nlw.modules.questions.entities.QuestionEntity;
import com.rocketseat.certification_nlw.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {

  @Autowired
  private QuestionRepository questionsRepository;

  @GetMapping("/technology/{technology}")
  public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
    System.out.println("TECH === " + technology);
    var result = this.questionsRepository.findByTechnology(technology);

    var toMap = result.stream().map(question -> mapQuestionToDTO(question))
        .collect(Collectors.toList());
    return toMap;
  }

  static QuestionResultDTO mapQuestionToDTO(QuestionEntity questions) {
    var questionResultTDO = QuestionResultDTO.builder()
        .id(questions.getId())
        .technology(questions.getTechnology())
        .description(questions.getDescription()).build();

    List<AlternativesResultDTO> alternativesResultDTOs = questions.getAlternatives()
        .stream().map(alternative -> mapAlternativeResultDTO(alternative))
        .collect(Collectors.toList());

    questionResultTDO.setAlternatives(alternativesResultDTOs);
    return questionResultTDO;
  }

  static AlternativesResultDTO mapAlternativeResultDTO(AlternativeEntity alternativesResultDTO) {
    return AlternativesResultDTO.builder()
        .id(alternativesResultDTO.getId())
        .description(alternativesResultDTO.getDescription()).build();
  }
}
